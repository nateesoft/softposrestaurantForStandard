package com.softpos.main.program;

import com.softpos.pos.core.controller.PublicVar;
import com.softpos.pos.core.controller.ThaiUtil;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import database.MySQLConnect;
import java.sql.Statement;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import util.MSG;

public class FindAr extends javax.swing.JDialog {
     DefaultTableModel model2;

    /** Creates new form FindAr */
    public FindAr(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        initComponents();
        PublicVar.ReturnString = "";
        model2 = (DefaultTableModel) tblShow.getModel();
        tblShow.setShowGrid(true);
        tblShow.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tblShow.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblShow.setRowSelectionAllowed(true);
        tblShow.setGridColor(Color.gray);

        JTableHeader header = tblShow.getTableHeader();
        //header.setBackground(Color.yellow);
        header.setFont(new java.awt.Font("Norasi", java.awt.Font.PLAIN, 16));

        int[] ColSize = {200, 350};
        for (int i = 0; i < 2; i++) {
            //int vColIndex = 0;
            TableColumn col = tblShow.getColumnModel().getColumn(i);
            col.setPreferredWidth(ColSize[i]);
        }
        LoadDataToGrid();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblShow = new javax.swing.JTable();
        bntOK = new javax.swing.JButton();
        bntExit = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("รายการค้นหาลูกหนี้การค้า");
        setUndecorated(true);

        tblShow.setFont(new java.awt.Font("Norasi", 0, 16)); // NOI18N
        tblShow.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "AR-Code รหัสลูกหนี้", "AR-Name ชื่อลูกหนี้"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblShow.setRowHeight(30);
        tblShow.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblShowKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblShow);

        bntOK.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bntOK.setText("Enter-ตกลง (OK)");
        bntOK.setFocusable(false);
        bntOK.setMargin(new java.awt.Insets(1, 1, 1, 1));
        bntOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntOKActionPerformed(evt);
            }
        });

        bntExit.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bntExit.setText("ESC-ออก (Exit)");
        bntExit.setFocusable(false);
        bntExit.setMargin(new java.awt.Insets(1, 1, 1, 1));
        bntExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(bntOK, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bntExit, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 596, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(bntExit, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(bntOK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void tblShowKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblShowKeyPressed
if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
        GetSelectedRow() ;
    }
    if (evt.getKeyCode()==KeyEvent.VK_ESCAPE) {
        bntExitClick() ;
    }
}//GEN-LAST:event_tblShowKeyPressed

private void bntOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntOKActionPerformed
    bntOKClick();
}//GEN-LAST:event_bntOKActionPerformed

private void bntExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntExitActionPerformed
    bntExitClick();
}//GEN-LAST:event_bntExitActionPerformed

public void LoadDataToGrid() {
        String SQLQuery = "";
        int RowCount = model2.getRowCount();
        for (int i = 0; i <= RowCount - 1; i++) {
            model2.removeRow(0);
        }
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            Statement stmt =  mysql.getConnection().createStatement();
            SQLQuery = "Select *from custfile order by sp_desc";
            ResultSet rec = stmt.executeQuery(SQLQuery);
            rec.first();
            if (rec.getRow() == 0) {
            } else {
                do {
                    Object[] input = {
                        rec.getString("sp_code"),
                        ThaiUtil.ASCII2Unicode(rec.getString("sp_desc"))
                    };
                    model2.addRow(input);
                } while (rec.next());
                RowCount = model2.getRowCount();
                showCell(0, 0);

            }
            rec.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
        }finally{
            mysql.close();
        }
        
        tblShow.requestFocus();
    }
public void bntOKClick() {
        GetSelectedRow();
    }
 public void showCell(int row, int column) {
     if(row>0){
        Rectangle rect = tblShow.getCellRect(row, column, true);
        tblShow.scrollRectToVisible(rect);
        tblShow.clearSelection();
        tblShow.setRowSelectionInterval(row, row);
     }
    }
  public void GetSelectedRow() {
        int maxrow;
        int currow = 0;
        String TableSelected = "";
        maxrow = tblShow.getRowCount();
        for (int i = 0; i < maxrow; i++) {
            if (tblShow.isRowSelected(i)) {
                currow = i;
            }
        }
        TableSelected = tblShow.getValueAt(currow, 0).toString();
        PublicVar.ReturnString = TableSelected;
        this.dispose();
    }
   public void bntExitClick() {
        PublicVar.ReturnString = "";
        this.dispose();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntExit;
    private javax.swing.JButton bntOK;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblShow;
    // End of variables declaration//GEN-END:variables

}
