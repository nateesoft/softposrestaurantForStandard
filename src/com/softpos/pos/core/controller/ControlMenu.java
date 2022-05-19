package com.softpos.pos.core.controller;

import com.softpos.pos.core.model.CompanyBean;
import com.softpos.pos.core.model.ProductBean;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import util.AppLogUtil;
import util.MSG;

public class ControlMenu {

    private final ArrayList<CompanyMenu> companyMenu;
    private int size;

    public ControlMenu() {
        companyMenu = new ArrayList<>();
        size = 0;
    }

    public CompanyMenu getMenu(int index) {

        return getAllMenu().get(index);
    }

    public CompanyMenu getMenu(String head) {
        int index = 0;
        switch (head) {
            case "A":
                index = 0;
                break;
            case "B":
                index = 1;
                break;
            case "C":
                index = 2;
                break;
            case "D":
                index = 3;
                break;
            default:
                break;
        }

        List<CompanyMenu> cpm = getAllMenu();
        if (cpm.isEmpty()) {
            return new CompanyMenu();
        } else {
            return cpm.get(index);
        }
    }

    //sample A01
    public List<ProductBean> getMenuItem(String item) {
        List<ProductBean> dataProduct = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select p.PCode, PGroup, PDesc, PUnit1, PPrice11, PPrice12, PPrice13,"
                    + "PPrice14, PPrice15, Code_Type, PPathName "
                    + "from menusetup m, product p "
                    + "where m.PCode=p.PCode AND Code_ID like '" + item + "E%' "
                    + "and PActive='Y' and PFix='F' "
                    + "group by PCode";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ProductBean product = new ProductBean();
                product.setPCode(rs.getString("PCode"));
                product.setPGroup(rs.getString("PGroup"));
                product.setPDesc(ThaiUtil.ASCII2Unicode(rs.getString("PDesc")));
                product.setPUnit1(ThaiUtil.ASCII2Unicode(rs.getString("PUnit1")));
                product.setPPrice11(rs.getDouble("PPrice11"));
                product.setPPrice12(rs.getDouble("PPrice12"));
                product.setPPrice13(rs.getDouble("PPrice13"));
                product.setPPrice14(rs.getDouble("PPrice14"));
                product.setPPrice15(rs.getDouble("PPrice15"));
                product.setPPathName(rs.getString("PPathName"));

                product.setPDesc2(getMenuItemAt(product.getPCode()));

                dataProduct.add(product);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(ControlMenu.class, "error", e);
        } finally {
            mysql.close();
        }

        return dataProduct;
    }

    public List<ProductBean> getMenuItem2(String item) {
        List<ProductBean> dataProduct = new ArrayList<>();
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select p.PCode, PGroup, PDesc, PUnit1, PPrice11, PPrice12, PPrice13,"
                    + "PPrice14, PPrice15, Code_Type, PPathName "
                    + "from menusetup m, product p "
                    + "where m.PCode=p.PCode "
                    + "group by PCode";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ProductBean product = new ProductBean();
                product.setPCode(rs.getString("PCode"));
                product.setPGroup(rs.getString("PGroup"));
                product.setPDesc(ThaiUtil.ASCII2Unicode(rs.getString("PDesc")));
                product.setPUnit1(ThaiUtil.ASCII2Unicode(rs.getString("PUnit1")));
                product.setPPrice11(rs.getDouble("PPrice11"));
                product.setPPrice12(rs.getDouble("PPrice12"));
                product.setPPrice13(rs.getDouble("PPrice13"));
                product.setPPrice14(rs.getDouble("PPrice14"));
                product.setPPrice15(rs.getDouble("PPrice15"));
                product.setPPathName(rs.getString("PPathName"));

                dataProduct.add(product);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(ControlMenu.class, "error", e);
        } finally {
            mysql.close();
        }

        return dataProduct;
    }

    public List<MenuSetup> menuAt(String index) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        String sql = "select * from menusetup where Code_ID like '" + index + "%' "
                + "and length(Code_ID)=3 group by Code_Id order by Code_Id";
        List<MenuSetup> menuAll = new ArrayList<>();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                MenuSetup ms = new MenuSetup();
                ms.setCode_ID(rs.getString("Code_ID"));
                ms.setPCode(rs.getString("PCode"));
                ms.setCode_Type(rs.getString("Code_Type"));
                ms.setShortName(ThaiUtil.ASCII2Unicode(rs.getString("ShortName")));
                ms.setPPathName(rs.getString("PPathName"));

                menuAll.add(ms);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(ControlMenu.class, "error", e);
        } finally {
            mysql.close();
        }

        return menuAll;
    }

    public List<MenuSetup> menuItemAt(String Code_ID) {
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        String sql = "select Code_Type,PCode from menusetup where Code_ID = '" + Code_ID + "' group by Code_ID limit 1";
        try {
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                String Code_Type = rs.getString("Code_Type");
                String PCode = rs.getString("PCode");
                switch (Code_Type) {
                    case "S":
                        sql = "select Code_ID, PCode, ShortName from menusetup where Code_ID like '" + Code_ID + "%' and Code_Type='P' group by Code_ID";
                        break;
                    case "P":
                        sql = "";
                        break;
                    case "D":
                        sql = "select PCode, PDesc ShortName from product where PGroup='" + PCode + "'";
                        break;
                    case "G":
                        sql = "select Code_ID, p.PCode, PDesc ShortName from menugroup m inner join product p on m.PCode=p.PCode where Code_ID='" + Code_ID + "'";
                        break;
                    default:
                        break;
                }
            }

            rs.close();
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(ControlMenu.class, "error", e);
        }

        List<MenuSetup> menuAll = new ArrayList<>();
        try {
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            String prefix = "TEST";
            int count = 0;
            while (rs.next()) {
                count++;
                MenuSetup ms = new MenuSetup();

                String CID;
                try {
                    CID = rs.getString("Code_ID");
                } catch (SQLException e) {
                    CID = prefix + count;
                }

                ms.setCode_ID(CID);
                ms.setPCode(rs.getString("PCode"));
                ms.setShortName(ThaiUtil.ASCII2Unicode(rs.getString("ShortName")));

                menuAll.add(ms);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(ControlMenu.class, "error", e);
        } finally {
            mysql.close();
        }

        return menuAll;
    }

    public List<CompanyMenu> getAllMenu() {
        CompanyBean companyBean = PosControl.getDataCompany();
        String[] head = new String[]{
            ThaiUtil.ASCII2Unicode(companyBean.getHead1()),
            ThaiUtil.ASCII2Unicode(companyBean.getHead2()),
            ThaiUtil.ASCII2Unicode(companyBean.getHead3()),
            ThaiUtil.ASCII2Unicode(companyBean.getHead4()),};

        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql;
            CompanyMenu headMenu;
            String[] mmenu = ("A,B,C,D").split(",");
            int index = 0;

            for (String h : head) {
                if (h != null) {
                    headMenu = new CompanyMenu();
                    headMenu.setHeadName(h.trim());

                    sql = "select * from menusetup "
                            + "where code_id like '" + mmenu[index] + "%' "
                            + "and Code_Type='" + CompanyMenu.TYPE_GROUP + "' "
                            + "group by Code_ID";
                    Statement stmt1 = mysql.getConnection().createStatement();
                    ResultSet rs1 = stmt1.executeQuery(sql);
                    while (rs1.next()) {
                        MenuSetup menu = new MenuSetup();
                        menu.setCode_ID(rs1.getString("Code_ID"));
                        menu.setCode_Type(rs1.getString("Code_Type"));
                        menu.setPCode(rs1.getString("PCode"));
                        menu.setShortName(ThaiUtil.ASCII2Unicode(rs1.getString("ShortName")));
                        menu.setPPathName(ThaiUtil.ASCII2Unicode(rs1.getString("PPathName")));

                        String sqlProduct = "select * from menusetup "
                                + "where Code_Id like '" + menu.getCode_ID() + "%' "
                                + "and Code_Type='" + CompanyMenu.TYPE_PRODUCT + "' "
                                + "and shortName<>'' "
                                + "group by Code_ID";
                        Statement stmt2 = mysql.getConnection().createStatement();
                        ResultSet rs2 = stmt2.executeQuery(sqlProduct);
                        while (rs2.next()) {
                            ProductBean product = new ProductBean();
                            product.setPCode(rs2.getString("Code_ID"));
                            product.setPDesc(ThaiUtil.ASCII2Unicode(rs2.getString("ShortName")));

                            menu.addProduct(product);
                        }

                        rs2.close();
                        stmt2.close();

                        headMenu.addMenuSetup(menu);
                    }
                    rs1.close();
                    stmt1.close();

                    companyMenu.add(headMenu);
                    size++;
                }
                index++;
            }
        } catch (SQLException e) {
            MSG.ERR(null, e.getMessage());
            AppLogUtil.log(ControlMenu.class, "error", e);
        } finally {
            mysql.close();
        }

        return companyMenu;
    }

    public int size() {
        return size;
    }

    public String[] getData(String menuHead) {
        List<MenuSetup> listMenu = menuAt(menuHead);
        String[] data = new String[28];
        for (int a1 = 0; a1 < data.length; a1++) {
            data[a1] = "";
        }
        for (int a = 0; a < listMenu.size(); a++) {
            MenuSetup m = (MenuSetup) listMenu.get(a);
            int index = Integer.parseInt(m.getCode_ID().substring(1, m.getCode_ID().length()));
            data[index - 1] = m.getShortName();
        }

        return data;
    }

    public List<String> getDataArray(String menuHead) {
        List<MenuSetup> listMenu = menuAt(menuHead);
        ArrayList<String> dataArray = new ArrayList<String>();
        for (int a1 = 0; a1 < 28; a1++) {
            //data[a1] = "";
            dataArray.add("");
        }
        for (int a = 0; a < listMenu.size(); a++) {
            MenuSetup m = (MenuSetup) listMenu.get(a);
            int index = Integer.parseInt(m.getCode_ID().substring(1, m.getCode_ID().length()));

            //Sub Menu
            switch (m.getCode_Type()) {
            //Plu Code
                case "S":
                    dataArray.set(index - 1, "(S) " + m.getShortName());
                    break;
            //Group
                case "P":
                    dataArray.set(index - 1, "(P) " + m.getShortName());
                    break;
            //Dept
                case "G":
                    dataArray.set(index - 1, "(G) " + m.getShortName());
                    break;
                case "D":
                    dataArray.set(index - 1, "(D) " + m.getShortName());
                    break;
                default:
                    break;
            }
        }

        return dataArray;
    }

    public List<MenuSetup> getDataMenuSetup(String menuHead) {
        List<MenuSetup> listMenu = menuAt(menuHead);
        List<MenuSetup> dataArray = new ArrayList<>();
        for (int a1 = 0; a1 < 28; a1++) {
            //data[a1] = "";
            dataArray.add(new MenuSetup("", "", "", "", ""));
        }
        for (int a = 0; a < listMenu.size(); a++) {
            MenuSetup m = (MenuSetup) listMenu.get(a);
            int index = Integer.parseInt(m.getCode_ID().substring(1, m.getCode_ID().length()));

            dataArray.set(index - 1, m);
        }

        return dataArray;
    }

    public List<MenuSetup> getDataMenuItem(String Code_ID) {
        List<MenuSetup> listMenu = menuItemAt(Code_ID);
        return listMenu;
    }

    public String getMenuItemAt(String pCode) {
        String menuAt = "";
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select MenuItem from menulist where PLUCode='" + pCode + "' and MenuActive='Y' limit 1";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                menuAt = rs.getString("MenuItem");
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            MSG.ERR(e.getMessage());
            AppLogUtil.log(ControlMenu.class, "error", e);
        } finally {
            mysql.close();
        }

        return menuAt;
    }

}
