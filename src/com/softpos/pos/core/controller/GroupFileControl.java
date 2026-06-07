package com.softpos.pos.core.controller;

import com.softpos.connection.database.MySQLConnect;
import com.softpos.pos.core.model.GroupFileBean;
import com.softpos.util.AppLogUtil;
import java.util.List;
import java.util.Map;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GroupFileControl {
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public List<GroupFileBean> getAllData() {
        List<GroupFileBean> listGroupFile = new ArrayList<>();
        mysqlConnect.open(ProductControl.class);
        try {
            String sql = "SELECT * FROM groupfile order by GroupCode";
            ResultSet rs = mysqlConnect.executeQuery(sql);
            while(rs.next()){
                GroupFileBean bean = new GroupFileBean();
                bean.setGroupCode(rs.getString("GroupCode"));
                bean.setGroupName(ThaiUtil.ASCII2Unicode(rs.getString("GroupName")));
                
                listGroupFile.add(bean);
            }
        } catch (SQLException e) {
            AppLogUtil.log(GroupFileControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        
        return listGroupFile;
    }
}
