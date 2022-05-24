package com.softpos.pos.core.controller;

import java.util.List;
import java.util.Map;
import util.DatabaseUtility;

public class DbGroupfile {
    
    private final DatabaseUtility du = new DatabaseUtility();

    public List<Map<String, Object>> getAllData() {
        List<Map<String, Object>> result = du.queryList("SELECT * FROM groupfile");
        return result;
    }

    public Map<String, Object> getAtPk(String groupcode) {
        Map<String, Object> result = du.querySingle("SELECT * FROM groupfile WHERE groupcode=?", groupcode);
        return result;
    }

    public boolean seekAtPk(String groupcode) {
        boolean success = false;
        Map<String, Object> data = du.querySingle("SELECT * FROM groupfile WHERE groupcode=?", groupcode);
        if (data != null) {
            if (!data.isEmpty()) {
                success = true;
            }
        }
        return success;
    }
}
