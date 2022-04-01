/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softpos.crm.pos.core.controller;

import com.softpos.pos.core.controller.ThaiUtil;
import com.softpos.pos.core.controller.Value;
import com.softpos.pos.core.model.MemberBean;
import database.ConfigFile;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import util.MSG;

/**
 *
 * @author Dell
 */
public class MemMasterController {

    MemberBean MemBean = new MemberBean();
    
    public MemberBean getData(String code) {
        MySQLConnect c = new MySQLConnect();
        try {
            c.open();
            String sql = "select * from " + Value.db_member + ".memmaster "
                    + "where Member_Code='" + code + "'";
            try (ResultSet rs = c.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                   MemBean = MemberBean.getMember(rs.getString(""));
                }
            }

        } catch (Exception e) {
            MSG.NOTICE(e.toString());
        } finally {
            c.close();
        }
        return MemBean;
    }

}
