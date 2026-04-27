/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.softpos.crm.pos.core.controller;

import com.softpos.crm.pos.core.modal.sendMgrButtonToBorBean;
import com.softpos.crm.pos.core.modal.sendSoft_MenustupBean;
import com.softpos.floorplan.FloorPlanDialog;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import util.AppLogUtil;
import util.MSG;

/**
 *
 * @author Dell
 */
public class sendMenuButttonToBorController {

    public ArrayList<sendMgrButtonToBorBean> sendMGRButtonSetupToBor() {
        ArrayList listMGRButtonSetup = new ArrayList();
        try {
            MySQLConnect mysql = new MySQLConnect();
            mysql.open(sendMenuButttonToBorController.class);
            String sqlGetMenuLocal = "select * from mgrbuttonsetup;";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sqlGetMenuLocal);
            while (rs.next()) {
                sendMgrButtonToBorBean bean = new sendMgrButtonToBorBean();
                bean = mappingBeanMGRButton(rs);
                listMGRButtonSetup.add(bean);
            }
            rs.close();
            stmt.close();
            mysql.close();
        } catch (Exception e) {
            MSG.NOTICE(e.toString());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        }
        return listMGRButtonSetup;
    }

    private static sendMgrButtonToBorBean mappingBeanMGRButton(ResultSet rs) throws SQLException {
        sendMgrButtonToBorBean bean = new sendMgrButtonToBorBean();
        bean.setPcode(rs.getString("pcode"));
        bean.setPdesc(rs.getString("pdesc"));
        bean.setSd_pcode(rs.getString("sd_pcode"));
        bean.setSd_pdesc(rs.getString("sd_pdesc"));
        bean.setEx_pcode(rs.getString("ex_pcode"));
        bean.setEx_pdesc(rs.getString("ex_pdesc"));
        bean.setEx_uncode(rs.getString("ex_uncode"));
        bean.setEx_undesc(rs.getString("ex_undesc"));
        bean.setAuto_pcode(rs.getString("auto_pcode"));
        bean.setAuto_pdesc(rs.getString("auto_pdesc"));
        bean.setCheck_before(rs.getString("check_before"));
        bean.setCheck_qty(rs.getString("check_qty"));
        bean.setQty(rs.getInt("qty"));
        bean.setCheck_autoadd(rs.getString("check_autoadd"));
        bean.setCheck_Extra(rs.getString("check_extra"));
        return bean;
    }

    public ArrayList<sendSoft_MenustupBean> sendDataSoft_menusetupToBor() {
        ArrayList listSoft_menusetupSetup = new ArrayList();
        try {
            MySQLConnect mysql = new MySQLConnect();
            mysql.open(sendMenuButttonToBorController.class);
            String sqlGetMenuLocal = "select * from soft_menusetup;";
            Statement stmt1 = mysql.getConnection().createStatement();
            ResultSet rs1 = stmt1.executeQuery(sqlGetMenuLocal);
            while (rs1.next()) {
                sendSoft_MenustupBean bean = new sendSoft_MenustupBean();
                bean = mappingsendSoft_Menusetup(rs1);
                listSoft_menusetupSetup.add(bean);
            }
            rs1.close();
            stmt1.close();
            mysql.close();
        } catch (Exception e) {
            MSG.NOTICE(e.toString());
            AppLogUtil.log(FloorPlanDialog.class, "error", e);
        }
        return listSoft_menusetupSetup;
    }

    private static sendSoft_MenustupBean mappingsendSoft_Menusetup(ResultSet rs1) throws SQLException {
        sendSoft_MenustupBean bean = new sendSoft_MenustupBean();
        bean.setMenuCode(rs1.getString("menucode"));
        bean.setMenuType(rs1.getString("menuType"));
        bean.setOptSet(rs1.getString("optSet"));
        bean.setpSet(rs1.getString("pset"));
        bean.setpCode(rs1.getString("pcode"));
        bean.setMenuShowText(rs1.getString("MenuShowText"));
        bean.setImg(rs1.getString("img"));
        bean.setFontColor(rs1.getString("FontColor"));
        bean.setBgColor(rs1.getString("BGColor"));
        bean.setLayout(rs1.getInt("layout"));
        bean.setFontSize(rs1.getInt("FontSize"));
        bean.setFontName(rs1.getString("FontName"));
        bean.setFontAttr(rs1.getString("FontAttr"));
        bean.setM_Index(rs1.getInt("M_Index"));
        bean.setiMG_Size(rs1.getInt("IMG_SIZE"));

        return bean;
    }

    public static void main(String[] args) {
        try {
            sendMenuButttonToBorController sendBor = new sendMenuButttonToBorController();
            ArrayList list = sendBor.sendMGRButtonSetupToBor();
            ArrayList list1 = sendBor.sendDataSoft_menusetupToBor();
            System.out.println(list.size());
            System.out.println(list1.size());
        } catch (Exception e) {
            MSG.NOTICE(e.toString());
        }
    }
}
