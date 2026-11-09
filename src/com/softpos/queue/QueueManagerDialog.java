package com.softpos.queue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Kitchen/cashier screen to move a queue through Pending -> Prepare -> Serve -> Done.
 * Moving a queue to Serve makes monitor2 call it out (3 times).
 */
public class QueueManagerDialog extends JDialog {

    private final DefaultTableModel model = new DefaultTableModel(
            new Object[]{"คิว", "สถานะ", "โต๊ะ", "ลูกค้า"}, 0) {
        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }
    };
    private final JTable table = new JTable(model);
    private final List<QueueService.Entry> rows = new ArrayList<>();
    private final Runnable refresh = this::reload;

    public QueueManagerDialog(Window owner) {
        super(owner, "จัดการคิว", ModalityType.MODELESS);
        setLayout(new BorderLayout(8, 8));
        table.setFont(new Font("Tahoma", Font.PLAIN, 20));
        table.setRowHeight(34);
        table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 16));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        buttons.add(button("กำลังเตรียม", () -> setStatus(QueueService.PREPARE)));
        buttons.add(button("พร้อมเสิร์ฟ (เรียกคิว)", () -> setStatus(QueueService.SERVE)));
        buttons.add(button("เรียกซ้ำ", this::recall));
        buttons.add(button("รับอาหารแล้ว", () -> setStatus(QueueService.DONE)));
        buttons.add(button("ยกเลิกคิว", () -> setStatus(QueueService.CANCEL)));
        add(buttons, BorderLayout.SOUTH);

        setSize(760, 520);
        setLocationRelativeTo(owner);
        QueueMonitor.getInstance().addListener(refresh);
        reload();
    }

    private JButton button(String text, Runnable action) {
        JButton b = new JButton(text);
        b.setFont(new Font("Tahoma", Font.BOLD, 15));
        b.addActionListener(e -> action.run());
        return b;
    }

    private QueueService.Entry selected() {
        int r = table.getSelectedRow();
        return r >= 0 && r < rows.size() ? rows.get(r) : null;
    }

    private void setStatus(String status) {
        QueueService.Entry e = selected();
        if (e == null) {
            return;
        }
        QueueService.setStatus(e.id, status);
        QueueMonitor.getInstance().refreshNow();
    }

    private void recall() {
        QueueService.Entry e = selected();
        if (e != null && QueueService.SERVE.equals(e.status)) {
            QueueAnnouncer.announce(e.no);
        }
    }

    private void reload() {
        int sel = table.getSelectedRow();
        rows.clear();
        rows.addAll(QueueMonitor.getInstance().getSnapshot());
        model.setRowCount(0);
        for (QueueService.Entry e : rows) {
            model.addRow(new Object[]{e.no, e.status, e.fromTable, e.customer});
        }
        if (sel >= 0 && sel < model.getRowCount()) {
            table.setRowSelectionInterval(sel, sel);
        }
    }

    /** Opens the dialog and makes sure the monitor is polling even if monitor2 is not connected. */
    public static void open(Window owner) {
        QueueMonitor.getInstance().start();
        new QueueManagerDialog(owner).setVisible(true);
    }
}
