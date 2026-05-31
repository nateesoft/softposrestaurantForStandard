package com.softpos.util;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

public class DirectoryUtility {

    public DirectoryUtility() {
    }

    public static boolean createDir(File file) throws IOException {
        boolean success = file.mkdir();
        return success;
    }

    public static boolean deleteDir(File file) throws IOException {
        boolean success = file.delete();
        return success;
    }

    public static File getFileAndCreateDir(String pathFile) {
        try {
            StringTokenizer st = new StringTokenizer(pathFile.trim(), "/");
            String path = "";
            int countToken = st.countTokens();
            for (int i = 0; st.hasMoreTokens(); i++) {
                if (i < countToken - 1) {
                    path += "/" + st.nextToken();
                    File f = new File(path);
                    if (!f.exists()) {
                        createDir(f);
                    }
                } else {
                    st.nextToken();
                }
            }
            File file = new File(pathFile);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<File> files;

    private static void findAllFilesInDirectory(String path) {
        try {
            File pathName = new File(path);
            String[] fileNames = pathName.list();
            for (int i = 0; i < fileNames.length; i++) {
                File f = new File(pathName.getPath(), fileNames[i]);
                if (f.isDirectory()) {
                    findAllFilesInDirectory(f.getPath());
                } else if (f.isFile()) {
                    files.add(f.getCanonicalFile());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
