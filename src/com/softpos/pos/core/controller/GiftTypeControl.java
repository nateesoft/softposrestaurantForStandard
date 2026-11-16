package com.softpos.pos.core.controller;

import com.softpos.connection.database.MySQLConnect;
import com.softpos.pos.core.model.GiftTypeBean;
import com.softpos.util.AppLogUtil;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class GiftTypeControl {
    private final MySQLConnect mysqlConnect = new MySQLConnect();

    public List<GiftTypeBean> getAllData() {
        List<GiftTypeBean> listGiftType = new ArrayList<>();
        mysqlConnect.open(ProductControl.class);
        try {
            String sql = "SELECT * FROM gifttype order by GroupCode";
            ResultSet rs = mysqlConnect.executeQuery(sql);
            while(rs.next()){
                GiftTypeBean bean = new GiftTypeBean();
                bean.setGiftTypeCode(rs.getString("gtcode"));
                bean.setGiftTypeName(ThaiUtil.ASCII2Unicode(rs.getString("gtname")));
                
                listGiftType.add(bean);
            }
        } catch (SQLException e) {
            AppLogUtil.log(GiftTypeControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        
        return listGiftType;
    }
    
    public GiftTypeBean findByCode(String giftTypeCode) {
        mysqlConnect.open(GiftTypeControl.class);
        GiftTypeBean giftTypeBean = null;
        
        try {
            String sql = "SELECT * FROM gifttype where gtcode='"+giftTypeCode+"'";
            ResultSet rs = mysqlConnect.executeQuery(sql);
            if(rs.next()){
                giftTypeBean = new GiftTypeBean();
                giftTypeBean.setGiftTypeCode(rs.getString("gtcode"));
                giftTypeBean.setGiftTypeName(ThaiUtil.ASCII2Unicode(rs.getString("gtname")));
            }
        } catch (SQLException e) {
            AppLogUtil.log(GiftTypeControl.class, "error", e);
        } finally {
            mysqlConnect.closeConnection(this.getClass());
        }
        
        return giftTypeBean;
    }
}
