package com.softpos.main.pos.view;

import com.softpos.pos.core.controller.AppContext;
import com.softpos.pos.core.controller.ProductControl;
import com.softpos.pos.core.dto.ProductSearchResponse;
import com.softpos.pos.core.model.ProductBean;
import com.softpos.util.ThaiUtil;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class FindProduct extends javax.swing.JDialog {

    private String PCode = "";
    private final ProductControl productControl = AppContext.getProductControl();

    public FindProduct(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        loadProductAll();
    }

    public String getPCode() {
        return PCode;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtSearch = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbProduct = new javax.swing.JTable();
        lbTotal = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ค้นหาสินค้า");
        setAlwaysOnTop(true);

        txtSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });

        btnFind.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnFind.setText("ค้นหา");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("รหัส/ชื่อ");

        tbProduct.setAutoCreateRowSorter(true);
        tbProduct.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Code", "Name", "Unit", "Price", "Group Code", "Group Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbProduct.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbProduct.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbProductMouseClicked(evt);
            }
        });
        tbProduct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbProductKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbProduct);

        lbTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbTotal.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnFind)
                        .addGap(170, 547, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbTotal)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 481, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbTotal)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        loadProductAll();
    }//GEN-LAST:event_btnFindActionPerformed

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                loadProductAll();
                break;
            case KeyEvent.VK_ESCAPE:
                PCode = "";
                dispose();
                break;
            case KeyEvent.VK_DOWN:
                tbProduct.requestFocus();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_txtSearchKeyPressed

    private void tbProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbProductMouseClicked
        if (evt.getClickCount() == 2) {
            int row = tbProduct.getSelectedRow();
            if (row != -1) {
                PCode = tbProduct.getValueAt(row, 0).toString();
                dispose();
            } else {
                PCode = "";
            }
        }
    }//GEN-LAST:event_tbProductMouseClicked

    private void tbProductKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbProductKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int row = tbProduct.getSelectedRow();
            if (row != -1) {
                PCode = tbProduct.getValueAt(row, 0).toString();
                dispose();
            } else {
                PCode = "";
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            PCode = "";
            dispose();
        }
    }//GEN-LAST:event_tbProductKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFind;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbTotal;
    private javax.swing.JTable tbProduct;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables

    private void loadProductAll() {
        DefaultTableModel model = (DefaultTableModel) tbProduct.getModel();
        tbProduct.setRowHeight(35);
        int size = model.getRowCount();
        for (int i = 0; i < size; i++) {
            model.removeRow(0);
        }
        JTableHeader tHeader = tbProduct.getTableHeader();
        tHeader.setFont(new Font("Tahoma", Font.BOLD, 14));

        String keyword = txtSearch.getText();
        List<ProductSearchResponse> listSearch = productControl.searchProduct(keyword);
        for (ProductSearchResponse response : listSearch) {
            model.addRow(new Object[]{
                response.getCode(),
                response.getName(),
                response.getUnit(),
                response.getPrice(),
                response.getGroup(),
                response.getGroupName()
            });
        }
        lbTotal.setText("รวมรายการ " + model.getRowCount() + " รายการ");
    }
}
