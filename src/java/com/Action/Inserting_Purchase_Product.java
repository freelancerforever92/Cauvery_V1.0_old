package com.Action;

import com.DAO.*;
import java.sql.*;
import java.util.*;
import com.opensymphony.xwork2.ActionSupport;
import java.text.SimpleDateFormat;
import org.apache.struts2.interceptor.SessionAware;
//import org.apache.log4j.Logger;

/*@author Administrator*/
public class Inserting_Purchase_Product extends ActionSupport implements SessionAware {

    Map session;
    ResultSet rs;
    Connection con = null;
    static String ufd;
    int rtnProcessValue;
    int Dir_status = 0;
    int tempVariable = 0;
    int spHeaderresult = 0;
    int spLineItemresult = 0;
    int rePrintReportStatus;
    String salesOrderNo = "";
    ResultSetMetaData rsmd = null;
    CallableStatement pstmt = null;
    DaoClass cado = new DaoClass();
    Vector sep_packed_value, indiv_value;
    private boolean cancelledStatusFlag = false;
    AmountToString amtToText = new AmountToString();
    static java.util.Date date = new java.util.Date();
    String totPurchaseProd, pstatus = "", rePrintReport_typ, reprintBillno;
//    static final Logger log = Logger.getLogger(Inserting_Purchase_Product.class);
    private String backDatedDate;
    private String enableManualBil;
    static SimpleDateFormat datformat = new SimpleDateFormat("yyMMddHHmmssZ");
    

    public Inserting_Purchase_Product() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String Fun_InsertPurchaseProd() {
        String Sp_SalesOrderHeader = "";
        String Sp_SalesOrderLineItem = "";
        int headerReturnValue = 0;
        Long headerSalesOrderNumber;
        try {
            con = cado.Fun_DbCon();
            con.setAutoCommit(false);
            sep_packed_value = new Vector(5);
            indiv_value = new Vector(5);

            StringTokenizer TokenPurcProd = new StringTokenizer(totPurchaseProd, ",");
            while (TokenPurcProd.hasMoreTokens()) {
                sep_packed_value.add(TokenPurcProd.nextElement());
            }

            for (int k = 0; k < sep_packed_value.size(); k++) {
                StringTokenizer TokenSepProd_Details = new StringTokenizer(sep_packed_value.elementAt(k).toString().trim(), "-");
                while (TokenSepProd_Details.hasMoreElements()) {
                    indiv_value.add(TokenSepProd_Details.nextElement());
                }
            }
            Sp_SalesOrderHeader = "";
            Sp_SalesOrderLineItem = "";
            spHeaderresult = 0;
            spLineItemresult = 0;
            //con.setAutoCommit(false);
            Sp_SalesOrderHeader = "{call sp_SalesOrderHeader(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            pstmt = con.prepareCall(Sp_SalesOrderHeader);
            pstmt.setInt("p_counterId", Integer.parseInt(session.get("LoginCounterId").toString()));
            pstmt.setString("p_empid", indiv_value.elementAt(0).toString().trim());
            pstmt.setString("p_showroom", cado.getPlantId());
            String approvalTextSingle = indiv_value.elementAt(1).toString().trim();
            if (approvalTextSingle.equals(".")) {
                pstmt.setString("p_discount_approval_txt", " ");
            } else {
                pstmt.setString("p_discount_approval_txt", indiv_value.elementAt(1).toString().trim());
            }
            pstmt.setString("p_enableManualBil", enableManualBil);
            pstmt.setFloat("p_pckCharge", Float.valueOf(indiv_value.elementAt(2).toString().trim()));
            pstmt.setFloat("p_billamt", Float.valueOf(indiv_value.elementAt(4).toString().trim()));
            pstmt.setString("p_txtBillamt", amtToText.EnglishNumber(Float.valueOf(indiv_value.elementAt(4).toString().trim()).longValue()));//BILL AMOUNT TEXT
            pstmt.setFloat("p_netamt", Float.valueOf((indiv_value.elementAt(3).toString().trim())));
            pstmt.setFloat("p_gst_percentage", Float.valueOf((indiv_value.elementAt(5).toString().trim())));
            pstmt.setFloat("p_sgst_value", Float.valueOf((indiv_value.elementAt(6).toString().trim())));
            pstmt.setFloat("p_cgst_value", Float.valueOf((indiv_value.elementAt(6).toString().trim())));
            if (!(backDatedDate.equals("0000-01-01"))) {
                pstmt.setString("p_entryDateTime", backDatedDate.trim());
                //pstmt.setString("p_backDatedBy", session.get("Login_Id").toString());
            } else if (backDatedDate.equals("0000-01-01")) {
                pstmt.setString("p_entryDateTime", "0000-00-00 00:00:00");
                //pstmt.setString("p_backDatedBy", "NoData");
            }
            pstmt.registerOutParameter("p_return_salesOrderNo", java.sql.Types.BIGINT);
            pstmt.registerOutParameter("p_return_code", java.sql.Types.INTEGER);
            spHeaderresult = pstmt.executeUpdate();
            headerReturnValue = pstmt.getInt("p_return_code");
            salesOrderNo = String.valueOf(pstmt.getLong("p_return_salesOrderNo"));
            headerSalesOrderNumber = pstmt.getLong("p_return_salesOrderNo");
             System.out.println("PACK VALUE        : "+sep_packed_value);
            if (sep_packed_value.size() == 1) {
                if (headerReturnValue == 0) {
                    Sp_SalesOrderLineItem = "{call sp_SalesOrderItems(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                    pstmt = con.prepareCall(Sp_SalesOrderLineItem);
                    pstmt.setString("p_salesorderno", String.valueOf(headerSalesOrderNumber));
                    pstmt.setInt("p_item", 1);
                    pstmt.setString("p_material", indiv_value.elementAt(7).toString().trim());//5
                    pstmt.setString("p_craftGroup", cado.Fun_Str("select craft_group from material_master_taxgroup where material_no='" + indiv_value.elementAt(7).toString().trim() + "'"));//5
                    pstmt.setString("p_description", indiv_value.elementAt(8).toString().trim()); //6
                    //if (!session.get("LoginCounterName").toString().equalsIgnoreCase("Billets/RM")) {
                    if (Integer.parseInt(session.get("LoginCounterId").toString()) != 2) {
                        pstmt.setDouble("p_qty", Integer.parseInt(indiv_value.elementAt(9).toString().trim()));//7
                        int qty = Integer.parseInt(indiv_value.elementAt(9).toString().trim());//7
                        float prc = Float.valueOf(indiv_value.elementAt(10).toString().trim());//8
                        pstmt.setFloat("p_prcvalue", Float.valueOf(qty * prc));
                    } else if (Integer.parseInt(session.get("LoginCounterId").toString()) == 2) {
                        Double sinlgeQty = new Double(indiv_value.elementAt(9).toString().trim());//7
                        pstmt.setDouble("p_qty", sinlgeQty);
                        float singleMulPrc = Float.valueOf(indiv_value.elementAt(10).toString().trim());//8
                        pstmt.setDouble("p_prcvalue", Double.valueOf(sinlgeQty * singleMulPrc));
                    }
                   
                    pstmt.setFloat("p_price", Float.valueOf(indiv_value.elementAt(10).toString().trim()));//8
                    pstmt.setFloat("p_discount_per", Float.valueOf(indiv_value.elementAt(13).toString().trim()));//11
                    pstmt.setFloat("p_discount_per_value", Float.valueOf(indiv_value.elementAt(14).toString().trim()));//12
                    pstmt.setFloat("p_vatpercentage", Float.valueOf(indiv_value.elementAt(11).toString().trim()));//9
                    pstmt.setFloat("p_vat_value", Float.valueOf(indiv_value.elementAt(12).toString().trim()));//10
                    pstmt.setFloat("p_calcu_value", Float.valueOf(indiv_value.elementAt(15).toString().trim()));//13
                    pstmt.setFloat("p_pckCharge", Float.valueOf(indiv_value.elementAt(2).toString().trim()));//2
                    pstmt.setString("p_vendor", indiv_value.elementAt(16).toString().trim());//14

                    if (!(backDatedDate.equals("0000-01-01"))) {
                        pstmt.setString("p_entryDateTime", backDatedDate.trim());
                    } else if (backDatedDate.equals("0000-01-01")) {
                        pstmt.setString("p_entryDateTime", "0000-00-00 00:00:00");
                    }

                    pstmt.setString("p_plant", cado.getPlantId());
                    pstmt.registerOutParameter("p_return_value", java.sql.Types.INTEGER);
                    spLineItemresult = pstmt.executeUpdate();
                    if (pstmt.getInt("p_return_value") == 0) {
                        pstatus = "1";
                        tempVariable = 1;
                        con.commit();
                    } else if (pstmt.getInt("p_return_value") >= 1) {
                        con.rollback();
                        pstatus = "-2";//Process failure IN INSERTING LINE ITEMS.
                    }
                } else if (pstmt.getInt("p_return_code") == - 1 || pstmt.getInt("p_return_code") == -2) {
                    con.rollback();
                    pstatus = "-1";//Process failure in INSERTING HEADER ITEMS.
                }
            } else if (indiv_value.size() > 1) {
                if (headerReturnValue == 0) {
                    //System.out.println("IN: " + headerReturnValue);
                    int itm_val = 1, h = 0, j = 1, k = 2, l = 3, m = 4, n = 5, o = 6, p = 7, q = 8, r = 9,s = 10,t = 11;
                    int temp_i = 1;
                    for (int i = 7; i <= indiv_value.size(); i++) {
                        if (temp_i <= sep_packed_value.size()) {
                            Sp_SalesOrderLineItem = "{call sp_SalesOrderItems(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                            pstmt = con.prepareCall(Sp_SalesOrderLineItem);
                            pstmt.setString("p_salesorderno", String.valueOf(headerSalesOrderNumber));
                            pstmt.setInt("p_item", itm_val);
                            
                            pstmt.setString("p_material", indiv_value.elementAt(i + h).toString().trim());
                            pstmt.setString("p_craftGroup", cado.Fun_Str("select craft_group from material_master_taxgroup where material_no='" + indiv_value.elementAt(i + h).toString().trim() + "'"));
                            pstmt.setString("p_description", indiv_value.elementAt(i + j).toString().trim());
                            //if (!session.get("LoginCounterName").toString().equalsIgnoreCase("Billets/RM")) {
                            if (Integer.parseInt(session.get("LoginCounterId").toString()) != 5) {
                                pstmt.setDouble("p_qty", Integer.parseInt(indiv_value.elementAt(i + k).toString().trim()));
                                int mulQty = Integer.parseInt(indiv_value.elementAt(i + k).toString().trim());
                                float mulPrc = Float.valueOf(indiv_value.elementAt(i + l).toString().trim());
                                pstmt.setDouble("p_prcvalue", Float.valueOf(mulQty * mulPrc));
                            } else if (Integer.parseInt(session.get("LoginCounterId").toString()) == 5) {
                                Double d = new Double(indiv_value.elementAt(i + k).toString());
                                pstmt.setDouble("p_qty", d);
                                float mulPrc = Float.valueOf(indiv_value.elementAt(i + l).toString().trim());
                                pstmt.setDouble("p_prcvalue", Double.valueOf(d * mulPrc));
                            }
                            pstmt.setFloat("p_price", Float.valueOf(indiv_value.elementAt(i + l).toString().trim()));
                            pstmt.setFloat("p_discount_per", Float.valueOf(indiv_value.elementAt(i + o).toString().trim()));
                            pstmt.setFloat("p_discount_per_value", Float.valueOf(indiv_value.elementAt(i + p).toString().trim()));
                            pstmt.setFloat("p_vatpercentage", Float.valueOf(indiv_value.elementAt(i + m).toString().trim()));
                            pstmt.setFloat("p_vat_value", Float.valueOf(indiv_value.elementAt(i + n).toString().trim()));
                            pstmt.setFloat("p_calcu_value", Float.valueOf(indiv_value.elementAt(i + q).toString().trim()));
                            pstmt.setString("p_pckCharge", indiv_value.elementAt(2).toString().trim());
                            pstmt.setString("p_vendor", indiv_value.elementAt(i + r).toString().trim());

                            if (!(backDatedDate.equals("0000-01-01"))) {
                                pstmt.setString("p_entryDateTime", backDatedDate.trim());
                            } else if (backDatedDate.equals("0000-01-01")) {
                                pstmt.setString("p_entryDateTime", "0000-00-00 00:00:00");
                            }
                            pstmt.setString("p_plant", cado.getPlantId());
                            pstmt.registerOutParameter("p_return_value", java.sql.Types.INTEGER);
                            h = h + 9;
                            j = j + 9;
                            k = k + 9;
                            l = l + 9;
                            m = m + 9;
                            n = n + 9;
                            o = o + 9;
                            p = p + 9;
                            q = q + 9;
                            r = r + 9;
                            itm_val++;
                            spLineItemresult = pstmt.executeUpdate();
                        }
                        temp_i++;
                    }
                    if (pstmt.getInt("p_return_value") == 0) {
                        pstatus = "1";
                        tempVariable = 1;
                        con.commit();
                    } else if (pstmt.getInt("p_return_value") >= 1) {
                        pstatus = "-2";//Process failure IN INSERTING LINE ITEMS.
                        con.rollback();
                    }
                } else if (pstmt.getInt("p_return_code") >= 1) {
                    pstatus = "-1";//Process failure in INSERTING HEADER ITEMS.
                    con.rollback();
                }
            }
        } catch (NumberFormatException ex) {
            System.out.println("NumberFormatException In Inserting The Purchase Product : " + ex);
            try {
                con.rollback();
            } catch (Exception e) {

            }
        } catch (SQLException ex) {
            System.out.println("SQLException In Inserting The Purchase Product : " + ex);
            try {
                con.rollback();
            } catch (Exception e) {

            }
        } catch (Exception ex) {
            System.out.println("Exception In Inserting The Purchase Product : " + ex);
            try {
                con.rollback();
            } catch (Exception e) {

            }
        } finally {
            cado.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public String Fun_ReprintBil() {
        int headerCountValue;
        rePrintReportStatus = 0;
        try {
            String isCancelQuery = "select count(*)from header where sales_orderno='" + reprintBillno.trim() + "' and cancelFlag='X'";
            int cancelledStatus = cado.Fun_Int(isCancelQuery);
            if (cancelledStatus >= 1) {
                cancelledStatusFlag = true;
            } else if (cancelledStatus <= 0) {
                cancelledStatusFlag = false;
                String headerCountQry = "select count(*)from header where sales_orderno='" + reprintBillno.trim() + "'";
                headerCountValue = cado.Fun_Int(headerCountQry);
                if (headerCountValue >= 1) {
                    rePrintReportStatus = 1;
                } else {
                    rePrintReportStatus = 0;
                }
            }
        } catch (Exception ex) {
            System.out.println("Excception In Reprinting Bill :  " + ex);
        }
        return SUCCESS;
    }

    public String getBackDatedDate() {
        return backDatedDate;
    }

    public void setBackDatedDate(String backDatedDate) {
        this.backDatedDate = backDatedDate;
    }

    public boolean isCancelledStatusFlag() {
        return cancelledStatusFlag;
    }

    public void setCancelledStatusFlag(boolean cancelledStatusFlag) {
        this.cancelledStatusFlag = cancelledStatusFlag;
    }

    public int getTempVariable() {
        return tempVariable;
    }

    public void setTempVariable(int tempVariable) {
        this.tempVariable = tempVariable;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public int getRePrintReportStatus() {
        return rePrintReportStatus;
    }

    public void setRePrintReportStatus(int rePrintReportStatus) {
        this.rePrintReportStatus = rePrintReportStatus;
    }

    public String getReprintBillno() {
        return reprintBillno;
    }

    public void setReprintBillno(String reprintBillno) {
        this.reprintBillno = reprintBillno;
    }

    public int getRtnProcessValue() {
        return rtnProcessValue;
    }

    public void setRtnProcessValue(int rtnProcessValue) {
        this.rtnProcessValue = rtnProcessValue;
    }

    public String getPstatus() {
        return pstatus;
    }

    public void setPstatus(String pstatus) {
        this.pstatus = pstatus;
    }

    public String getTotPurchaseProd() {
        return totPurchaseProd;
    }

    public void setTotPurchaseProd(String totPurchaseProd) {
        this.totPurchaseProd = totPurchaseProd;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String getEnableManualBil() {
        return enableManualBil;
    }

    public void setEnableManualBil(String enableManualBil) {
        this.enableManualBil = enableManualBil;
    }
}
