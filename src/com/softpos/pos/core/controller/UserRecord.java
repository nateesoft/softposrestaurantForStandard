package com.softpos.pos.core.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import database.MySQLConnect;
import java.sql.Statement;

public class UserRecord {

    //User Record
    public String UserCode = "";
    public String UserName = "";
    public String UserGroup = "";
    public String LoginTime = "";
    public String Cont0 = "N";
    public String Cont1 = "N";
    public String Cont2 = "N";
    public String Cont3 = "N";
    public String Cont4 = "N";
    public String Cont5 = "N";
    public String Cont6 = "N";
    public String Cont7 = "N";
    public String Cont8 = "N";
    public String Cont9 = "N";
    public String Cont10 = "N";
    public String Cont11 = "N";
    public String Cont12 = "N";
    public String Cont13 = "N";
    public String Cont14 = "N";
    public String Cont15 = "N";
    public String Sale1 = "N";
    public String Sale2 = "N";
    public String Sale3 = "N";
    public String Sale4 = "N";
    public String Sale5 = "N";
    public String Sale6 = "N";
    public String Sale7 = "N";
    public String Sale8 = "N";
    public String Sale9 = "N";
    public String Sale10 = "N";
    public String Sale11 = "N";
    public String Sale12 = "N";
    public String Sale13 = "N";
    public String Sale14 = "N";
    public String Sale15 = "N";
    public String Sale16 = "N";
    public String Sale17 = "N";
    public String Sale18 = "N";
    public String Sale19 = "N";
    public String Sale20 = "N";
    public String Sale21 = "N";
    public String Sale22 = "N";
    public String Sale23 = "N";
    public String Sale24 = "N";
    public String Sale25 = "N";
    public String Sale26 = "N";
    public String Sale27 = "N";
    public String Sale28 = "N";
    public String Sale29 = "N";
    public String Sale30 = "N";
    public String Sale31 = "N";
    public String Sale32 = "N";
    public String Sale33 = "N";
    public String Sale34 = "N";
    public String Sale35 = "N";
    public String Sale36 = "N";
    public String Stock0 = "N";
    public String Stock1 = "N";
    public String Stock2 = "N";
    public String Stock3 = "N";
    public String Stock4 = "N";
    public String Stock5 = "N";
    public String Stock6 = "N";
    public String Stock7 = "N";
    public String Stock8 = "N";
    public String Stock9 = "N";
    public String Stock10 = "N";
    public String Stock11 = "N";
    public String Stock12 = "N";
    public String Stock13 = "N";
    public String Stock14 = "N";
    public String Stock15 = "N";
    public String Stock16 = "N";
    public String Stock17 = "N";
    public String Stock18 = "N";
    public String Stock19 = "N";
    public String Stock20 = "N";
    public String Stock21 = "N";
    public String Stock22 = "N";
    public String Stock23 = "N";
    public String Stock24 = "N";
    public String Stock25 = "N";
    public String Stock26 = "N";
    public String Stock27 = "N";
    public String Stock28 = "N";
    public String Stock29 = "N";
    public String Stock30 = "N";
    public String Stock31 = "N";
    public String Stock32 = "N";
    public String Stock33 = "N";
    public String Stock34 = "N";
    public String Stock35 = "N";
    public String Stock36 = "N";
    public String Stock37 = "N";
    public String Stock38 = "N";
    public String Stock39 = "N";
    public String Stock40 = "N";
    public String Stock41 = "N";
    public String Stock42 = "N";
    public String Stock43 = "N";
    public String Stock44 = "N";
    public String Stock45 = "N";

    public boolean GetUserAction(String XUserCode) {
        /**
         * * OPEN CONNECTION **
         */
        MySQLConnect mysql = new MySQLConnect();
        mysql.open();
        try {
            String sql = "select  *from posuser Where(username= '" + XUserCode + "')";
            Statement stmt = mysql.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.first();
            if (rs.getRow() == 0) {
                PUtility.showError("??????????????????????????????????????? (Username) ????????????????????????????????? (Password) ?????????????????????????????? !!! ");
                return false;
            } else {
                UserCode = rs.getString("username");
                UserName = rs.getString("name");
                UserGroup = rs.getString("usergroup");
                Cont0 = rs.getString("cont0");
                Cont1 = rs.getString("cont1");
                Cont2 = rs.getString("cont2");
                Cont3 = rs.getString("cont3");
                Cont4 = rs.getString("cont4");
                Cont5 = rs.getString("cont5");
                Cont6 = rs.getString("cont6");
                Cont7 = rs.getString("cont7");
                Cont8 = rs.getString("cont8");
                Cont9 = rs.getString("cont9");
                Cont10 = rs.getString("cont10");
                Cont11 = rs.getString("cont11");
                Cont12 = rs.getString("cont12");
                Cont13 = rs.getString("cont13");
                Cont14 = rs.getString("cont14");
                Cont15 = rs.getString("cont15");
                Sale1 = rs.getString("sale1");
                Sale2 = rs.getString("sale2");
                Sale3 = rs.getString("sale3");
                Sale4 = rs.getString("sale4");
                Sale5 = rs.getString("sale5");
                Sale6 = rs.getString("sale6");
                Sale7 = rs.getString("sale7");
                Sale8 = rs.getString("sale8");
                Sale9 = rs.getString("sale9");
                Sale10 = rs.getString("sale10");
                Sale11 = rs.getString("sale11");
                Sale12 = rs.getString("sale12");
                Sale13 = rs.getString("sale13");
                Sale14 = rs.getString("sale14");
                Sale15 = rs.getString("sale15");
                Sale16 = rs.getString("sale16");
                Sale17 = rs.getString("sale17");
                Sale18 = rs.getString("sale18");
                Sale19 = rs.getString("sale19");
                Sale20 = rs.getString("sale20");
                Sale21 = rs.getString("sale21");
                Sale22 = rs.getString("sale22");
                Sale23 = rs.getString("sale23");
                Sale24 = rs.getString("sale24");
                Sale25 = rs.getString("sale25");
                Sale26 = rs.getString("sale26");
                Sale27 = rs.getString("sale27");
                Sale28 = rs.getString("sale28");
                Sale29 = rs.getString("sale29");
                Sale30 = rs.getString("sale30");
                Sale31 = rs.getString("sale31");
                Sale32 = rs.getString("sale32");
                Sale33 = rs.getString("sale33");
                Sale34 = rs.getString("sale34");
                Sale35 = rs.getString("sale35");
                Sale36 = rs.getString("sale36");
                Stock0 = rs.getString("Stock0");
                Stock1 = rs.getString("Stock1");
                Stock2 = rs.getString("Stock2");
                Stock3 = rs.getString("Stock3");
                Stock4 = rs.getString("Stock4");
                Stock5 = rs.getString("Stock5");
                Stock6 = rs.getString("Stock6");
                Stock7 = rs.getString("Stock7");
                Stock8 = rs.getString("Stock8");
                Stock9 = rs.getString("Stock9");
                Stock10 = rs.getString("Stock10");
                Stock11 = rs.getString("Stock11");
                Stock12 = rs.getString("Stock12");
                Stock13 = rs.getString("Stock13");
                Stock14 = rs.getString("Stock14");
                Stock15 = rs.getString("Stock15");
                Stock16 = rs.getString("Stock16");
                Stock17 = rs.getString("Stock17");
                Stock18 = rs.getString("Stock18");
                Stock19 = rs.getString("Stock19");
                Stock20 = rs.getString("Stock20");
                Stock21 = rs.getString("Stock21");
                Stock22 = rs.getString("Stock22");
                Stock23 = rs.getString("Stock23");
                Stock24 = rs.getString("Stock24");
                Stock25 = rs.getString("Stock25");
                Stock26 = rs.getString("Stock26");
                Stock27 = rs.getString("Stock27");
                Stock28 = rs.getString("Stock28");
                Stock29 = rs.getString("Stock29");
                Stock30 = rs.getString("Stock30");
                Stock31 = rs.getString("Stock31");
                Stock32 = rs.getString("Stock32");
                Stock33 = rs.getString("Stock33");
                Stock34 = rs.getString("Stock34");
                Stock35 = rs.getString("Stock35");
                Stock36 = rs.getString("Stock36");
                Stock37 = rs.getString("Stock37");
                Stock38 = rs.getString("Stock38");
                Stock39 = rs.getString("Stock39");
                Stock40 = rs.getString("Stock40");
                Stock41 = rs.getString("Stock41");
                Stock42 = rs.getString("Stock42");
                Stock43 = rs.getString("Stock43");
                Stock44 = rs.getString("Stock44");
                Stock45 = rs.getString("Stock45");
                return true;
            }
        } catch (SQLException e) {
            PUtility.showError(e.getMessage());
            return false;
        }finally{
            mysql.close();
        }
    }
}
