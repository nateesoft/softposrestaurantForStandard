package com.softpos.main.program;
import java.awt.event.KeyEvent;


public class GetVoidMsg extends javax.swing.JDialog {
    static String VoidMsg ;

    public GetVoidMsg(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        txtVoid.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtVoid = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("สาเหตุการยกเลิก");
        setFont(new java.awt.Font("Norasi", 0, 14)); // NOI18N
        setUndecorated(true);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setText("สาเหตการยกเลิก");

        txtVoid.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtVoid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtVoidKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtVoid, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtVoid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

private void txtVoidKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVoidKeyPressed
   if (evt.getKeyCode()==KeyEvent.VK_ENTER) {
      VoidMsg = txtVoid.getText() ;
      this.dispose();
   }
   if (evt.getKeyCode()==KeyEvent.VK_ESCAPE) {
      VoidMsg = "" ;
      this.dispose();
   }
}//GEN-LAST:event_txtVoidKeyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtVoid;
    // End of variables declaration//GEN-END:variables

}
