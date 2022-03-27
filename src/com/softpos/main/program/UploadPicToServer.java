package com.softpos.main.program;

import database.ConfigFile;
import database.ConfigFileServer;

public class UploadPicToServer {

    FTPUtility ftp = new FTPUtility();

    public String checkFile() {
        String sqlUpdate = "";
        try {
            String server = ConfigFile.getProperties("ftp_server_host");
            server = "localhost";
            server = "//" + server + "";

            String localFile = ("D:/Backup/Data/retail652/branch20210802.sql");
            String filename = setFileName(localFile);
            try {
                ftp.upload(server + filename, localFile);
                System.out.println("Upload file " + localFile + "/" + server + "/" + filename + "success");
            } catch (Exception e) {
                
            }

        } catch (Exception e) {
            
//            MSG.ERR(e.toString());
        }
        return sqlUpdate;
    }

    public String upLoadFilePicToServer(String Localpath) {
        String sqlUpdate = "";
        try {
            FTPUtility ftp = new FTPUtility();
            String server = ConfigFileServer.getProperties("ftp_server_host");
            server = "" + server + "";

            String localFile = Localpath;
            String filename = setFileName(localFile);
            try {
                ftp.upload(localFile, "ftp://" + server + "/test.sql");
                System.out.println("Upload file " + localFile + "/" + server + "/" + filename + "success");
            } catch (Exception e) {
                
            }
        } catch (Exception e) {
            
//            MSG.ERR(e.toString());
        }
        return sqlUpdate;
    }

    public String setFileName(String FileName) {
        String[] strs = FileName.split("/");
        System.out.println(strs.length);
        int i = 0;
        for (String data : strs) {
            try {
                i++;
                System.out.println(strs[i-1]);
                 FileName = strs[i];  
                
            } catch (Exception e) {
                
            }

        }
        return FileName;
    }

    public static void main(String[] args) {
        UploadPicToServer upload = new UploadPicToServer();
//        upload.checkFile();
        upload.upLoadFilePicToServer("D:/Backup/652/branch20210802.sql");
    }
}
