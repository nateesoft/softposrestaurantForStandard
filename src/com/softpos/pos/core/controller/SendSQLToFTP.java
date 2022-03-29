package com.softpos.main.program;

import database.ConfigFileServer;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class SendSQLToFTP {

    public void SendSQLToFTP() {
        String server = ConfigFileServer.getProperties("ftp_server_host");
        int port = 21;
        String user = ConfigFileServer.getProperties("ftp_server_user");
        String pass = ConfigFileServer.getProperties("ftp_server_pass");

        FTPClient ftpClient = new FTPClient();
        try {
            //connect
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            //settype
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            File branchLocalFile = new File("D:/Backup/Data/branch.sql");
            String fileName = setFileName("D:/Backup/Data/branch.sql");
            InputStream inputStream = new FileInputStream(branchLocalFile);
            String secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
            byte[] bytesIn = new byte[4096];
            int read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            boolean completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The first file is uploaded successfully.");
            }
            
            
            branchLocalFile = new File("D:/Backup/Data/company.sql");
            fileName = setFileName("D:/Backup/Data/company.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The company file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/costfile.sql");
            fileName = setFileName("D:/Backup/Data/costfile.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The costfile file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/menusetup.sql");
            fileName = setFileName("D:/Backup/Data/menusetup.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The menusetup file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/posconfigsetup.sql");
            fileName = setFileName("D:/Backup/Data/posconfigsetup.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The posconfigsetup file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/poshwsetup.sql");
            fileName = setFileName("D:/Backup/Data/poshwsetup.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The poshwsetup file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/posconfigsetup.sql");
            fileName = setFileName("D:/Backup/Data/posconfigsetup.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The posconfigsetup file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/poshwsetup.sql");
            fileName = setFileName("D:/Backup/Data/poshwsetup.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The poshwsetup file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/posuser.sql");
            fileName = setFileName("D:/Backup/Data/posuser.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The posuser file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/product.sql");
            fileName = setFileName("D:/Backup/Data/product.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The product file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/pset.sql");
            fileName = setFileName("D:/Backup/Data/pset.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The pset file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/stkfile.sql");
            fileName = setFileName("D:/Backup/Data/stkfile.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The stkfile file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/usergroup.sql");
            fileName = setFileName("D:/Backup/Data/usergroup.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The usergroup file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/creditfile.sql");
            fileName = setFileName("D:/Backup/Data/creditfile.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The creditfile file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/groupfile.sql");
            fileName = setFileName("D:/Backup/Data/groupfile.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The groupfile file is uploaded successfully.");
            }
            
            branchLocalFile = new File("D:/Backup/Data/protab.sql");
            fileName = setFileName("D:/Backup/Data/protab.sql");
            inputStream = new FileInputStream(branchLocalFile);
            secondRemoteFile = ConfigFileServer.getProperties("ftp_server_dataPath") + fileName;
            inputStream = new FileInputStream(branchLocalFile);

            System.out.println("Start uploading file");
            //save to path server
            outputStream = ftpClient.storeFileStream(secondRemoteFile);
            bytesIn = new byte[4096];
            read = 0;

            while ((read = inputStream.read(bytesIn)) != -1) {
                outputStream.write(bytesIn, 0, read);
            }
            inputStream.close();
            outputStream.close();

            completed = ftpClient.completePendingCommand();
            if (completed) {
                System.out.println("The protab file is uploaded successfully.");
            }

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String setFileName(String file) {
        String data[] = file.split("/");
        String fileName = "";
        int i = 0;
        for (String strs : data) {
            fileName = data[i];
            i++;

        }
        System.out.println(fileName + " i = : " + i);
        return fileName;
    }

//    public static void main(String[] args) {
//        SendSQLToFTP send = new SendSQLToFTP();
//        send.SendSQLToFTP();
//    }
}
