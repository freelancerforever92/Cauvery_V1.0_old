package com.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.swing.*;
/*@author Administrator*/

public class DaoClass {
    /*SET SQL_SAFE_UPDATES=0;*/

    ResultSet rs = null;
    Statement sta = null;
    Connection con = null;
    ResultSetMetaData rsmd = null;
    private static String log_uname;
    private static String log_id;
    private static String showroom;
    private static String Ser_IPAddres = "localhost";
    static ResourceBundle rb = ResourceBundle.getBundle("dbConnection");
    private String fname = "", ext = "", fname2 = "";

    static {
        try {
            Class.forName(rb.getString("driver.name"));
        } catch (ClassNotFoundException ex) {
            System.err.println(ex);
        }
    }

    /*public void DaoClass(String Rcv_IPAddress)
     {
     Ser_IPAddres=Rcv_IPAddress;
     }*/
    public static String getLog_uname() {
        return log_uname;
    }

    public static void setLog_uname(String log_uname) {
        DaoClass.log_uname = log_uname;
    }

    public static String getLog_id() {
        return log_id;
    }

    public static void setLog_id(String log_id) {
        DaoClass.log_id = log_id;
    }

    public static String getShowroom() {
        return showroom;
    }

    public static void setShowroom(String showroom) {
        DaoClass.showroom = showroom;
    }

    public static String getStockCheckValue() {
        return rb.getString("boolean.check.stockvalue");
    }

    public static String getPlantId() {
        return rb.getString("cauvery.plantId");
    }

    public static String getSuperAdminHideId() {
        return rb.getString("super.admin.hide.id");
    }

    public static String getTestUserId() {
        /*return login test userid */
        return rb.getString("cauvery.usertypeid.test");
    }

    public static String getRestricedVendor() {
        return rb.getString("billets.vendorid");
    }

    public static String getRestricedCraftGroup() {
        return rb.getString("restricted.craftgroup");
    }

    public void DaoClass(String Rcv_IPAddress) {
        this.Ser_IPAddres = Rcv_IPAddress;
    }

    public Connection Fun_DbCon() {
        try {
//            System.out.println("-> :"+rb.getString("url.name"));
//            System.out.println("-> :"+rb.getString("db.username"));
//            System.out.println("-> :"+rb.getString("db.password"));
            con = DriverManager.getConnection(rb.getString("url.name"), rb.getString("db.username"), rb.getString("db.password"));
            sta = con.createStatement();
        } catch (Exception ex) {
            System.out.println("Exception In Creating DbConnection :  " + ex);
        }
        return con;
    }

    public Integer Fun_Updat(String rcv_qry) {
        int updat_val = 0;
        try {
            Fun_DbCon();
            updat_val = sta.executeUpdate(rcv_qry);
        } catch (Exception ex) {
            System.out.println("Exception In Update :  " + ex);
        }
        return updat_val;
    }

    public Statement fun_statement() throws ClassNotFoundException, SQLException {
        con = Fun_DbCon();
        sta = con.createStatement();
        return sta;
    }

    public ResultSet Fun_Resultset(String rcv_qry) {
        try {
            Fun_DbCon();
            rs = sta.executeQuery(rcv_qry);
        } catch (Exception ex) {
            System.out.println("Exception In Resultset :  " + ex);
        }
        return rs;
    }

    public int Fun_Int(String rcv_qry) {
        int rtn_valu = 0;
        try {
            Fun_DbCon();
            rs = sta.executeQuery(rcv_qry);
            if (rs.next()) {
                rtn_valu = rs.getInt(1);
            } else {
                rtn_valu = 0;
            }
            //closeResultSet(rs);
        } catch (Exception ex) {
            System.out.println("Exception In ReturnInteger :  " + ex);
        } finally {
            closeResultSet(rs);
        }
        return rtn_valu;
    }

    public float Fun_Float(String rcv_qry) {
        float rtn_valu = 0;
        try {
            Fun_DbCon();
            rs = sta.executeQuery(rcv_qry);
            if (rs.next()) {
                rtn_valu = rs.getFloat(1);
            } else {
                rtn_valu = 0;
            }
        } catch (Exception ex) {
            System.out.println("Exception In ReturnFloat :  " + ex);
        } finally {
            closeResultSet(rs);
        }
        return rtn_valu;
    }

    public String Fun_Str(String rcv_qry) {
        String str = "";
        try {
            Fun_DbCon();
            rs = sta.executeQuery(rcv_qry);

            if (rs.next()) {
                //rs.next();
                str = rs.getString(1);
            } else {
                str = "";
            }
        } catch (Exception ex) {
            System.out.println("Exception In ReturnString :  " + ex);
        } finally {
            closeResultSet(rs);
        }
        return str;
    }

    public String fun_check(String str) throws SQLException {
        String ss = "Not Available";
        try {
            sta = fun_statement();
            rs = sta.executeQuery(str);  // this is for name
            if (rs.next()) {
                ss = "Available";
            } else {
                ss = "Not Available";
            }
            rs.close();
            sta.close();
            con.close();
        } catch (Exception ex) {
            ss = "Please Specify Acceptable String";
        } finally {
            closeResultSet(rs);
        }

        return ss;
    }

    public void Fun_cls() {
        try {
            rs.close();
            con.close();
            sta.close();
        } catch (Exception ex) {
            System.out.println("EXCEPTION IN CLOSING CONNECTION :   " + ex);
        }
    }

    public void Comb_Fill(JComboBox obj, String RcvQry) {
        try {
            Fun_DbCon();
            rs = sta.executeQuery(RcvQry);
            obj.removeAllItems();
            while (rs.next()) {
                obj.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            System.out.println("Exception In Filling Combo :  " + ex);
        } finally {
            closeResultSet(rs);
        }
    }

    public void updatingRecord(String insertingQuery) {
        try {
            Fun_DbCon();
            sta.executeUpdate(insertingQuery);
        } catch (SQLException ex) {
            System.out.println("Exception in Updating Record :  " + ex);
        }
    }

    public String Fun_FileExtension(String File_Path) {
        int mid = 0, mid2 = 0;
        String typ = "";
        try {
            if (typ.equalsIgnoreCase("ext")) {
            }
            mid = File_Path.indexOf(".");
            fname = File_Path.substring(0, mid);
            //System.out.println("Fname 1  :  " + fname);
            ext = File_Path.substring(mid + 1, File_Path.length());
            //System.out.println("Ext  :  " + ext);
            mid2 = ext.indexOf(".");
            fname2 = ext.substring(mid2 + 1, ext.length());
            //System.out.println("Fname 2  :  " + fname2);
        } catch (Exception ex) {
            System.out.println("Exception in Returing File Ext  :  " + ex);
        }
        return ext;
    }

    // public void Fun_AutoIncr(JLabel Lbl_id, String pre_fix, String qry) {
    public void Fun_AutoIncr(String Lbl_id, String pre_fix, String qry) {
        try {
            int h = 0, cuntvalue;
            Fun_DbCon();
            rs = sta.executeQuery(qry);
            int acc_id, db_cunt = 0;
            String accno = "";
            cuntvalue = Fun_Int(qry);
            //System.out.println("Receive Querry :   " + qry);
            //System.out.println("COUNT Value :   " + cuntvalue);
            if (cuntvalue == 0) {
                acc_id = 1;
                accno = String.format("%010d", acc_id);
                //Lbl_id.setText(pre_fix + "" + accno);
                Lbl_id = pre_fix + "" + accno;
            } else if (cuntvalue > 0) {
                acc_id = db_cunt + 1;
                accno = String.format("%010d", acc_id);
                //Lbl_id.setText(pre_fix + "" + accno);
                Lbl_id = pre_fix + "" + accno;
            }

        } catch (Exception ex) {
            System.out.println("Exception In Autoid Gen :  " + ex);
        }
    }

    public String Fun_SalesOrderNo(String receivedPlandId, String receivedCounterId, String salesOrderNoQuerry, int salesOrderNoLength) {
        String accno = "";
        String returnSalesOrderNo = "";

        int maxRecordValue;
        int increRecordValue;

        Fun_DbCon();
        try {
            rs = sta.executeQuery(salesOrderNoQuerry);
            maxRecordValue = Fun_Int(salesOrderNoQuerry);
            if (maxRecordValue == 0) {
                increRecordValue = 1;
                //accno = String.format("%010d", increRecordValue);
                accno = String.format("%0" + salesOrderNoLength + "d", increRecordValue);
                //returnSalesOrderNo = accno;
                returnSalesOrderNo = receivedPlandId + "" + receivedCounterId + "" + accno;
            } else if (maxRecordValue > 0) {
                increRecordValue = maxRecordValue + 1;
                //accno = String.format("%010d", increRecordValue);
                accno = String.format("%0" + salesOrderNoLength + "d", increRecordValue);
                //returnSalesOrderNo = accno;
                returnSalesOrderNo = receivedPlandId + "" + receivedCounterId + "" + accno;
            }
        } catch (Exception ex) {
            System.out.println("Exception In Autoid Gen :  " + ex);
        } finally {
            closeResultSet(rs);
        }
        return returnSalesOrderNo;
    }
    List lst = new ArrayList();
    List lst1 = new ArrayList();

    public List Fun_List(String rcv_qry) {

        try {
            Fun_DbCon();
            rs = Fun_Resultset(rcv_qry);
            rsmd = rs.getMetaData();

            int lstlen = rsmd.getColumnCount();
            lst = new ArrayList();

            while (rs.next()) {
                lst1 = new ArrayList();
                for (int i = 1; i <= lstlen; i++) {
                    lst1.add(rs.getString(i));
                }
                lst.add(lst1);
            }
        } catch (Exception ex) {
            System.out.println("Exception In List :  " + ex);
        } finally {
            closeResultSet(rs);
        }
        return lst;
    }

    public void closeStatement(Statement stmt) {
        try {
            Connection con = null;
            if (stmt != null) {
                con = stmt.getConnection();
                if (con != null) {
                    con.close();
                    con = null;
                }
                stmt.close();
                stmt = null;
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    public void closeResultSet(ResultSet rs) {
        try {
            Connection con = null;
            Statement stmt = null;

            if (rs != null) {
                stmt = rs.getStatement();
                if (stmt != null) {
                    con = stmt.getConnection();
                    if (con != null) {
                        con.close();
                        con = null;
                    }
                    stmt.close();
                    stmt = null;
                }
                rs.close();
                rs = null;
            }

        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
