package com.softpos.main.program;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.TooManyListenersException;
import javax.swing.JDialog;
import util.MSG;

public class EDCProcessDialog extends JDialog implements SerialPortEventListener {

    private SerialPort serialPort;
    private InputStream inputStream;
    private OutputStream outputStream;
    private Enumeration portList;
    private CommPortIdentifier portId;
    private boolean openStatus = false;
    private String strRead;
    private String resultStr = "";
    private String writeStr = "";
    private String cardCode = "";
    private String custName = "";
    private String appCode = "";
    private String cardType = "";
    private boolean processError = false;
    private boolean processFinish = false;

    public EDCProcessDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        writeStr = "";
        cardCode = "";
        custName = "";
        appCode = "";
        cardType = "";
        processError = false;
        processFinish = false;
        crMsg.setText("กรุณารูดบัติเครดิต..ที่เครื่องอนุมัติบัตรเครดิต");
    }

    public static void main(String[] args) {
        EDCProcessDialog edc = new EDCProcessDialog(null, true);
        edc.processEDC("COM6", 120.00);
        edc.setVisible(true);
    }

    public boolean openPort(String portName) {
        openStatus = false;
        portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(portName)) {
                    try {
                        serialPort = (SerialPort) portId.open("SimpleWriteApp", 1000);
                    } catch (PortInUseException e) {
                        
                    }

                    try {
                        outputStream = serialPort.getOutputStream();
                        inputStream = serialPort.getInputStream();
                    } catch (IOException e) {
                        
                    }

                    try {
                        serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                        serialPort.setInputBufferSize(2048);
                        serialPort.setOutputBufferSize(2048);
                        openStatus = true;
                    } catch (UnsupportedCommOperationException e) {
                        
                    }

                    break;
                }
            }
        }
        
        return openStatus;
    }

    public void closePort() {
        if (!openStatus) {
            return;
        }
        if (serialPort != null) {
            try {
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                
            }
            serialPort.close();
        }
        openStatus = false;
    }

    public void processEDC(String portName, double amount) {
        int strLength;
        String readStr;
        String msg;
        int intAmount = (int) (amount * 100);
        String strAmount = PUtility.Addzero(Integer.toString(intAmount), 12);
        String STX = "\u0002\u0000\u0035";
        String ETX = "\u0003";
        msg = "60000000001020000" + "\u001C" + "40" + "\u0000\u0012" + strAmount + "\u001C";
        writeStr = STX + msg + ETX;
        strLength = writeStr.length();
        byte byte1;
        byte byte2;
        byte tempbyte;
        char tempch = writeStr.charAt(1);
        byte1 = (byte) tempch;
        for (int i = 2; i < strLength; i++) {
            char ch = writeStr.charAt(i);
            byte2 = (byte) ch;
            byte1 = (byte) (byte1 ^ byte2);
        }
        tempbyte = byte1;
        char ch = (char) tempbyte;
        writeStr = writeStr + ch;
        if (openPort(portName)) {
            serialPort.notifyOnDataAvailable(true);
            try {
                serialPort.addEventListener(this);
            } catch (TooManyListenersException e) {
                
            }

            try {
                inputStream = serialPort.getInputStream();
            } catch (IOException e) {
                
            }
            byte[] readBuffer = new byte[2048];
            try {
                while (inputStream.available() > 0) {
                    int numBytes = inputStream.read(readBuffer);
                }
                strRead = new String(readBuffer);
            } catch (IOException e) {
                
            }
            byte chz = (byte) strRead.charAt(0);
            int timeOut = 0;
            boolean outLoop = false;
            while ((strRead.equals("") | (chz != 6)) & (!outLoop)) {
                try {
                    outputStream.write(writeStr.getBytes());
                    Thread.sleep(1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                chz = (byte) strRead.charAt(0);
                timeOut++;
                if (timeOut > 3000) {
                    outLoop = true;
                }
            }
            if (timeOut > 30000) {
                javax.swing.JOptionPane.showMessageDialog(this, "กรุณาตรวจสอบสถานะเครื่อง EDC ...");
                closePort();
                chkResult();
            }
            resultStr = writeStr;
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Port " + portName + " ไม่สามารถใช้งานได้...กรุณาตรวจสอบ");
            chkResult();
        }
    }

    public boolean getError() {
        return processError;
    }

    public void bntExitOK() {
        processFinish = true;
        processError = true;
        this.dispose();
    }

    public void chkResult() {
        String T02, TD0, T03, T04, T65, T16, TD1, T31, T50, TD3, TD4, TD5 = "";
        String T30 = "";
        String T01 = "";
        String TD2 = "";
        String chkError = resultStr.substring(3, 5);
        try {
            Thread.sleep(100);
        } catch (Exception e) {
        }
        if (chkError.equals("Y6")) {
            cardCode = "";
            appCode = "";
            custName = "";
            cardType = "";
            processFinish = true;
            processError = true;
            sendAck();
            closePort();
            this.dispose();
        }
        int totalLength = resultStr.length();
        int cur = 0;
        String responseCode = resultStr.substring(17, 19);
        if (responseCode.equals("00")) {
            if (resultStr.length() > 256) {
                totalLength = resultStr.length();
                cur = 0;
                String tmpText = "";
                while (cur < totalLength) {
                    byte chkText = (byte) resultStr.charAt(cur);
                    tmpText = tmpText + (char) chkText;
                    if (chkText == 28) {
                        if (tmpText.substring(0, 2).equals("02")) {
                            T02 = tmpText.substring(3, tmpText.length() - 1);
                        } else if (tmpText.substring(0, 2).equals("D0")) {
                            TD0 = tmpText.substring(2);
                        } else if (tmpText.substring(0, 2).equals("03")) {
                            T03 = tmpText.substring(2);
                        } else if (tmpText.substring(0, 2).equals("04")) {
                            T04 = tmpText.substring(2);
                        } else if (tmpText.substring(0, 2).equals("01")) {
                            T01 = tmpText.substring(3, tmpText.length() - 1);
                        } else if (tmpText.substring(0, 2).equals("65")) {
                            T65 = tmpText.substring(2);
                        } else if (tmpText.substring(0, 2).equals("16")) {
                            T16 = tmpText.substring(2);
                        } else if (tmpText.substring(0, 2).equals("D1")) {
                            TD1 = tmpText.substring(2);
                        } else if (tmpText.substring(0, 2).equals("30")) {
                            T30 = tmpText.substring(3, tmpText.length() - 1);
                        } else if (tmpText.substring(0, 2).equals("31")) {
                            T31 = tmpText.substring(2);
                        } else if (tmpText.substring(0, 2).equals("50")) {
                            T50 = tmpText.substring(2);
                        } else if (tmpText.substring(0, 2).equals("D3")) {
                            TD3 = tmpText.substring(2);
                        } else if (tmpText.substring(0, 2).equals("D4")) {
                            TD4 = tmpText.substring(2);
                        } else if (tmpText.substring(0, 2).equals("D5")) {
                            TD5 = tmpText.substring(3, tmpText.length() - 1);
                        } else if (tmpText.substring(0, 2).equals("D2")) {
                            TD2 = tmpText.substring(3, tmpText.length() - 1);
                        }
                        tmpText = "";
                    }
                    cur++;
                }

                cardCode = T30;
                appCode = T01;
                custName = TD5;
                cardType = TD2;
                if (!appCode.equals("")) {
                    processFinish = true;
                    processError = false;
                } else {
                    processFinish = true;
                    processError = true;
                }
                sendAck();
                closePort();
                this.dispose();
            }
        } else {
            cardCode = "";
            appCode = "";
            custName = "";
            cardType = "";
            processFinish = true;
            processError = true;
            sendAck();
            closePort();
            this.dispose();
        }

    }

    public void sendAck() {
        String ack = "\u0006";
        try {
            outputStream.write(ack.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                byte[] readBuffer = new byte[1024];
                int numBytes = 0;
                try {
                    while (inputStream.available() > 0) {
                        numBytes = inputStream.read(readBuffer);
                    }
                    strRead = new String(readBuffer);
                    int i = 0;
                    int totbyteread = strRead.length();
                    while (i < totbyteread) {
                        byte ch = (byte) readBuffer[i];
                        if (ch != 0) {
                            resultStr = resultStr + (char) ch;
                        }
                        i++;
                    }
                    if (resultStr.length() > 20) {
                        chkResult();
                    }
                } catch (IOException e) {
                    MSG.ERR(e.toString());
                }
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        crMsg = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(java.awt.Color.white);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusCycleRoot(false);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 102, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, java.awt.Color.lightGray));
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1KeyPressed(evt);
            }
        });

        crMsg.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        crMsg.setForeground(java.awt.Color.white);
        crMsg.setText("กรุณารูดบัตรเครดิต...ที่เครื่องอนุมัติบัตรเครดิต");

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crMsg, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(crMsg)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setBounds(543, 200, 519, 51);
    }// </editor-fold>//GEN-END:initComponents

private void jPanel1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1KeyPressed

}//GEN-LAST:event_jPanel1KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cardCode = "";
        appCode = "";
        custName = "";
        cardType = "";
        processFinish = true;
        processError = true;
        sendAck();
        closePort();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel crMsg;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    public boolean isOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(boolean openStatus) {
        this.openStatus = openStatus;
    }

    public String getStrRead() {
        return strRead;
    }

    public void setStrRead(String strRead) {
        this.strRead = strRead;
    }

    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public String getWriteStr() {
        return writeStr;
    }

    public void setWriteStr(String writeStr) {
        this.writeStr = writeStr;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public boolean isProcessFinish() {
        return processFinish;
    }

    public void setProcessFinish(boolean processFinish) {
        this.processFinish = processFinish;
    }

    public boolean isProcessError() {
        return processError;
    }

    public void setProcessError(boolean processError) {
        this.processError = processError;
    }
}
