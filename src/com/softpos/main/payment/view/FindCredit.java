package com.softpos.main.payment.view;

import com.softpos.crm.pos.core.modal.PublicVar;
import com.softpos.pos.core.controller.AppContext;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

public class FindCredit extends javax.swing.JDialog {

    private String DefaultBank = "";
    private String TCode;
    private double TCharge;
    private DefaultTableModel model2;

    /**
     * Creates new form FindCredit
     */
    public FindCredit(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);

        initComponents();
        model2 = (DefaultTableModel) tblShow.getModel();
        tblShow.setShowGrid(true);
        tblShow.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblShow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblShow.setRowSelectionAllowed(true);
        tblShow.setGridColor(Color.gray);

        JTableHeader header = tblShow.getTableHeader();
        //header.setBackground(Color.yellow);
        header.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 16));

        int[] ColSize = {50, 100, 250, 50, 50, 50};
        for (int i = 0; i < 6; i++) {
            //int vColIndex = 0;
            TableColumn col = tblShow.getColumnModel().getColumn(i);
            col.setPreferredWidth(ColSize[i]);
        }
        DefaultBank = LoadDefaultBank();

        LoadDataToGrid(DefaultBank);
    }

    public String getCreditCode() {
        return TCode;
    }

    public double getCreditCharge() {
        return TCharge;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblShow = new javax.swing.JTable();
        bntOK = new javax.swing.JButton();
        bntExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("ค้นหาบัตรเครดิต");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblShow.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        tblShow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bank", "CR Code", "Description", "Charge", "Redule", "Get No"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblShow.setRowHeight(30);
        tblShow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblShowMouseClicked(evt);
            }
        });
        tblShow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblShowKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblShow);

        bntOK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bntOK.setText("Enter-ตกลง (OK)");
        bntOK.setFocusable(false);
        bntOK.setMargin(new java.awt.Insets(1, 1, 1, 1));
        bntOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOKActionPerformed(evt);
            }
        });

        bntExit.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        bntExit.setText("ESC-ออก (Exit)");
        bntExit.setFocusable(false);
        bntExit.setMargin(new java.awt.Insets(1, 1, 1, 1));
        bntExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bntOK, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntExit, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bntOK, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bntExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(584, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void bntExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntExitActionPerformed
    bntExitClick();
}//GEN-LAST:event_bntExitActionPerformed

private void bntOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntOKActionPerformed
    int rowSel = tblShow.getSelectedRow();
    if (rowSel != -1) {
        TCode = tblShow.getValueAt(rowSel, 1).toString();
        TCharge = Double.parseDouble(tblShow.getValueAt(rowSel, 3).toString());
    } else {
        TCode = "";
        TCharge = 0.00;
    }

    this.setVisible(false);//dispose();
}//GEN-LAST:event_bntOKActionPerformed

private void tblShowKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblShowKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        bntOKActionPerformed(null);
    }
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
        bntExitClick();
    }
    if (evt.getKeyCode() == KeyEvent.VK_F5) {
        bntShowAllClick();
    }
}//GEN-LAST:event_tblShowKeyPressed

    private void tblShowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblShowMouseClicked
        if (evt.getClickCount() == 2) {
            bntOKActionPerformed(null);
        }
    }//GEN-LAST:event_tblShowMouseClicked

    public void bntExitClick() {
        PublicVar.ReturnString = "";
        this.setVisible(false);//dispose();
    }

    public void bntShowAllClick() {
        DefaultBank = "";
        LoadDataToGrid(DefaultBank);
    }

    public String LoadDefaultBank() {
        String creditAct = AppContext.getBranchControl().getData().getCreditAct();
        return creditAct != null ? creditAct : "";
    }

    private void LoadDataToGrid(String defaultBank) {
        int rowCount = model2.getRowCount();
        for (int i = 0; i < rowCount; i++) {
            model2.removeRow(0);
        }
        List<Object[]> rows = AppContext.getCreditFileController().findByBank(defaultBank);
        for (Object[] row : rows) {
            model2.addRow(row);
        }
        showCell(0, 0);
        tblShow.requestFocus();
    }

    public void showCell(int row, int column) {
        if (row > 0) {
            Rectangle rect = tblShow.getCellRect(row, column, true);
            tblShow.scrollRectToVisible(rect);
            tblShow.clearSelection();
            tblShow.setRowSelectionInterval(row, row);

        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntExit;
    private javax.swing.JButton bntOK;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblShow;
    // End of variables declaration//GEN-END:variables
}
