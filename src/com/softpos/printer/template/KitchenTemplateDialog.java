package com.softpos.printer.template;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Designer for the per-kitchen ticket templates: pick a kitchen (kic1..kic20), edit its template,
 * preview it with sample data, test print, save. A kitchen with "use template" ticked prints from
 * the template instead of the built-in form.
 */
public class KitchenTemplateDialog extends JDialog {

    private final JComboBox<String> kitchenBox = new JComboBox<>();
    private final JCheckBox enabledBox = new JCheckBox("ใช้เทมเพลตนี้พิมพ์ออกครัว (แทนฟอร์มเดิม)");
    private final JTextArea editor = new JTextArea();
    private final JEditorPane preview = new JEditorPane("text/html", "");
    private final Timer previewTimer;
    private boolean dirty = false;
    private boolean loading = false;
    private int currentIndex = 0;

    public KitchenTemplateDialog(Window owner) {
        super(owner, "ออกแบบใบสั่งครัว (Kitchen Template)", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout(8, 8));

        for (int i = 1; i <= KitchenTemplateService.KITCHEN_COUNT; i++) {
            kitchenBox.addItem("ครัว " + i + "  (kic" + i + ")");
        }
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 6));
        top.add(new JLabel("เลือกครัว:"));
        top.add(kitchenBox);
        top.add(enabledBox);
        add(top, BorderLayout.NORTH);

        editor.setFont(new Font("Tahoma", Font.PLAIN, 15));
        editor.setTabSize(4);
        JPanel left = new JPanel(new BorderLayout(4, 4));
        left.add(new JLabel("<html>แต่ละบรรทัดขึ้นต้นด้วย <b>[l] [c] [r]</b> = ชิดซ้าย/กลาง/ขวา, "
                + "<b>[s1]-[s7]</b> = ขนาดตัวอักษร, <b>[b]</b> = ตัวหนา</html>"), BorderLayout.NORTH);
        left.add(new JScrollPane(editor), BorderLayout.CENTER);

        preview.setEditable(false);
        JScrollPane previewScroll = new JScrollPane(preview);
        previewScroll.setPreferredSize(new Dimension(300, 400));

        JList<String> vars = new JList<>(variableLabels());
        vars.setFont(new Font("Tahoma", Font.PLAIN, 13));
        vars.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && vars.getSelectedIndex() >= 0) {
                    insertVariable(KitchenTemplatePrinter.VARIABLES[vars.getSelectedIndex()][0]);
                }
            }
        });
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("ตัวอย่าง", previewScroll);
        tabs.addTab("ตัวแปร (ดับเบิลคลิกเพื่อแทรก)", new JScrollPane(vars));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, tabs);
        split.setResizeWeight(0.62);
        add(split, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        buttons.add(button("โหลดเทมเพลตเริ่มต้น", () -> setText(KitchenTemplateService.DEFAULT_TEMPLATE, true)));
        buttons.add(button("ทดลองพิมพ์", this::testPrint));
        buttons.add(button("บันทึก", this::save));
        buttons.add(button("ปิด", this::dispose));
        add(buttons, BorderLayout.SOUTH);

        previewTimer = new Timer(400, e -> refreshPreview());
        previewTimer.setRepeats(false);
        editor.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changed();
            }
        });
        enabledBox.addActionListener(e -> dirty = true);
        kitchenBox.addActionListener(e -> switchKitchen());

        loadKitchen(0);
        setSize(980, 640);
        setLocationRelativeTo(owner);
    }

    private static String[] variableLabels() {
        String[] out = new String[KitchenTemplatePrinter.VARIABLES.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = "{" + KitchenTemplatePrinter.VARIABLES[i][0] + "}  -  " + KitchenTemplatePrinter.VARIABLES[i][1];
        }
        return out;
    }

    private JButton button(String text, Runnable action) {
        JButton b = new JButton(text);
        b.setFont(new Font("Tahoma", Font.BOLD, 14));
        b.addActionListener(e -> action.run());
        return b;
    }

    private void changed() {
        if (!loading) {
            dirty = true;
        }
        previewTimer.restart();
    }

    private String kicNo(int index) {
        return String.valueOf(index + 1);
    }

    private void insertVariable(String name) {
        // block markers go on their own line, plain variables inline
        boolean block = name.startsWith("#") || name.startsWith("/");
        editor.replaceSelection(block ? "{" + name + "}\n" : "{" + name + "}");
        editor.requestFocusInWindow();
    }

    private void switchKitchen() {
        if (loading) {
            return;
        }
        if (dirty && JOptionPane.showConfirmDialog(this,
                "ยังไม่ได้บันทึกการแก้ไขของครัวนี้ ต้องการทิ้งการแก้ไขหรือไม่?", "Kitchen Template",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            loading = true;
            kitchenBox.setSelectedIndex(currentIndex);
            loading = false;
            return;
        }
        loadKitchen(kitchenBox.getSelectedIndex());
    }

    private void loadKitchen(int index) {
        currentIndex = index;
        KitchenTemplateService.Template t = KitchenTemplateService.load(kicNo(index));
        loading = true;
        enabledBox.setSelected(t != null && t.enabled);
        setText(t != null ? t.text : KitchenTemplateService.DEFAULT_TEMPLATE, false);
        loading = false;
        dirty = false;
        refreshPreview();
    }

    private void setText(String text, boolean markDirty) {
        boolean was = loading;
        loading = !markDirty;
        editor.setText(text);
        editor.setCaretPosition(0);
        loading = was;
        if (markDirty) {
            dirty = true;
        }
    }

    private String printerName() {
        return "kic" + kicNo(kitchenBox.getSelectedIndex());
    }

    private void refreshPreview() {
        List<String> rows = KitchenTemplatePrinter.renderSample(editor.getText(), printerName());
        StringBuilder html = new StringBuilder("<html><body><table width='100%' border='0' cellpadding='0'>");
        for (String row : rows) {
            html.append("<tr><td ").append(row).append("</td></tr>");
        }
        html.append("</table></body></html>");
        preview.setText(html.toString());
        preview.setCaretPosition(0);
    }

    private void testPrint() {
        KitchenTemplatePrinter.printSample(printerName(), editor.getText());
    }

    private void save() {
        if (enabledBox.isSelected() && editor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "เทมเพลตว่างเปล่า ไม่สามารถเปิดใช้งานได้");
            return;
        }
        if (KitchenTemplateService.save(kicNo(kitchenBox.getSelectedIndex()), enabledBox.isSelected(), editor.getText())) {
            dirty = false;
            JOptionPane.showMessageDialog(this, "บันทึกเทมเพลตครัว " + kicNo(kitchenBox.getSelectedIndex()) + " แล้ว");
        } else {
            JOptionPane.showMessageDialog(this, "บันทึกไม่สำเร็จ ดู log เพื่อตรวจสอบ", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void open(Window owner) {
        new KitchenTemplateDialog(owner).setVisible(true);
    }
}
