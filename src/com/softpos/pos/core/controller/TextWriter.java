package com.softpos.pos.core.controller;

import java.io.FileWriter;
import util.AppLogUtil;
import util.DirectoryUtility;

public class TextWriter {

    public void writeToText(String pathFile, String data) {
        try (FileWriter writer = new FileWriter(DirectoryUtility.getFileAndCreateDir(pathFile), true)) {
            writer.write(data);
            writer.write(13);
            writer.write(10);
        } catch (Exception e) {
            AppLogUtil.log(TextWriter.class, "error", e);
        }
    }

    public void writeToText(String pathFile, String data, boolean overwrite) throws Exception {
        try (FileWriter writer = new FileWriter(DirectoryUtility.getFileAndCreateDir(pathFile), overwrite)) {
            writer.write(data);
            writer.write(13);
            writer.write(10);
        }
    }

}
