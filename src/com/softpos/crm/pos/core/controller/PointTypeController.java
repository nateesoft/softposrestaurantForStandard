package com.softpos.crm.pos.core.controller;

import com.softpos.crm.pos.core.modal.PointTypeBean;
import com.softpos.pos.core.controller.ThaiUtil;
import com.softpos.pos.core.controller.Value;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import util.DateUtil;
import util.MSG;

/**
 *
 * @author nathee
 */
public class PointTypeController {

    public static PointTypeBean getData(String pointTypeCode) {
        PointTypeBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String sql = "select * "
                    + "from " + Value.db_member + ".pointtype "
                    + "where Point_TypeCode='" + pointTypeCode + "'";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    bean = mappingBean(rs);
                }
            }
        } catch (SQLException e) {
            MSG.ERR("PointTypeController:" + e.getMessage());
        } finally {
            mysql.close();
        }

        return bean;
    }
    
    public static PointTypeBean getDataBranchPoint() {
        PointTypeBean bean = null;
        MySQLConnect mysql = new MySQLConnect();
        try {
            mysql.open();
            String EEE = DateUtil.getDateFormat(new Date(), "EEE");
            String sql = "SELECT * FROM " + Value.db_member + ".pointtype "
                        + "WHERE 1=1 "
                        + "AND curdate() BETWEEN Point_StartDateService and Point_FinishDateService "
                        + "AND (point1>0 or point2>0 or point3>0) "
                        + "AND Point_TypeDateService like '%" + EEE + "%'";
            try (ResultSet rs = mysql.getConnection().createStatement().executeQuery(sql)) {
                if (rs.next()) {
                    bean = mappingBean(rs);
                }
            }
        } catch (SQLException e) {
            MSG.ERR("PointTypeController:" + e.getMessage());
        } finally {
            mysql.close();
        }

        return bean;
    }

    private static PointTypeBean mappingBean(ResultSet rs) throws SQLException {
        PointTypeBean bean = new PointTypeBean();
        bean.setPoint_TypeCode(rs.getString("Point_TypeCode"));
        bean.setPoint_TypeDateService(rs.getString("Point_TypeDateService"));
        bean.setPoint_StartDateService(rs.getDate("Point_StartDateService"));
        bean.setPoint_FinishDateService(rs.getDate("Point_FinishDateService"));

        bean.setPoint_StartTimeService1(rs.getString("Point_StartTimeService1"));
        bean.setPoint_FinishTimeService1(rs.getString("Point_FinishTimeService1"));
        bean.setBahtRatePerPoint1(rs.getFloat("BahtRatePerPoint1"));
        bean.setPoint1(rs.getFloat("point1"));

        bean.setPoint_StartTimeService2(rs.getString("Point_StartTimeService2"));
        bean.setPoint_FinishTimeService2(rs.getString("Point_FinishTimeService2"));
        bean.setBahtRatePerPoint2(rs.getFloat("BahtRatePerPoint2"));
        bean.setPoint2(rs.getFloat("point2"));

        bean.setPoint_StartTimeService3(rs.getString("Point_StartTimeService3"));
        bean.setPoint_FinishTimeService3(rs.getString("Point_FinishTimeService3"));
        bean.setBahtRatePerPoint3(rs.getFloat("BahtRatePerPoint3"));
        bean.setPoint3(rs.getFloat("point3"));

        bean.setPoint_TypeName(ThaiUtil.ASCII2Unicode(rs.getString("Point_TypeName")));

        return bean;
    }
}
