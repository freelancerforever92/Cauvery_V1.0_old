package com.Action.CashBill;

import com.DAO.*;
import java.sql.*;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.struts2.interceptor.SessionAware;

/*@author Administrator*/
public class getCounterBillandAmt extends ActionSupport implements SessionAware {

    Map session;
    ResultSet rs = null;
    Connection con = null;
    int printFlagValue = 0;
    int counterBillnoCountValue = 0, counterBillCancelStatus = 0, spCashBill_Header_Result, spCashBill_LineItem_Result;
    String dbNetAmt;
    String dbDateTime;
    String cashBillNo = "";
    String cashBillSrlNo;
    String counterBillNo;
    String txtcounterBillNo;
    String Sp_CashBilHeader = "";
    String Sp_CashBillLineItem = "";
    String receivePackagedValue = "";
    String pushingCouponRedemptionDeatail = "";

    String pushingCashierPkID;
    String pushingBackDatedValue;

    CallableStatement pstmt = null;
    DaoClass cado = new DaoClass();
    StringBuffer stringbuf = new StringBuffer();

    private String reprintCashBillNo;
    private boolean isCashBillStatus = false;
    private boolean isCounterBillProcessedStatus = false;
    Vector separated_CashBillValue, indivCashBillValue;
    Vector vectorSeparateCoupon;
    Vector vectorIndivdualCoupon;

    public getCounterBillandAmt() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String fetchCounterBillNoAmt() {
        dbNetAmt = "";
        counterBillCancelStatus = 0;
        String getDbCounterBillCount = "";
        String getCounterBillStatusValue = "";
        String getDbCounterBillAmtQuerry = "";
        String getCounterBillCancelledStatus = "";
        try {
            stringbuf.append(txtcounterBillNo);
            getDbCounterBillCount = "select count(*) from header where sales_orderno='" + txtcounterBillNo.trim() + "'";
            counterBillnoCountValue = cado.Fun_Int(getDbCounterBillCount);
            if (counterBillnoCountValue > 0) {
                getCounterBillCancelledStatus = "select cancelFlag from header where sales_orderno='" + txtcounterBillNo.trim() + "'";
                getCounterBillStatusValue = cado.Fun_Str(getCounterBillCancelledStatus);
                if (!(getCounterBillStatusValue.equalsIgnoreCase("X"))) {
                    getDbCounterBillAmtQuerry = "select bil_amt,date_time from pos.header where sales_orderno='" + txtcounterBillNo.trim() + "'";
                    
                    //getDbCounterBillAmtQuerry = "select net_amt from pos.header where sales_orderno='" + txtcounterBillNo.trim() + "'";
                    dbNetAmt = cado.Fun_Str(getDbCounterBillAmtQuerry);
                    ResultSet res = cado.Fun_Resultset(getDbCounterBillAmtQuerry);
                    while (res.next()) {
                        dbNetAmt = res.getFloat("bil_amt") + "";
                        dbDateTime = res.getDate("date_time") + "";
                    }
                    double doubledBillvalue = Double.parseDouble(dbNetAmt);
                    long convertedBillAmount = (long) (doubledBillvalue + 0.5); // + 0.5 to round 
                    dbNetAmt = convertedBillAmount + "";
                    counterBillCancelStatus = 1;
                } else if (getCounterBillStatusValue.equalsIgnoreCase("X")) {
                    counterBillCancelStatus = 2;
                }
            }
        } catch (Exception ex) {
            stringbuf.append("Exception in fetchCounterBillNoAmt : " + ex);
        }
        return SUCCESS;
    }

    public String validDateCashBillNo() {
        try {
            String checkingCounterBillNo = "select count(*) from cashbill_header_master where cashBill_id='" + reprintCashBillNo.trim() + "'";
            if (cado.Fun_Int(checkingCounterBillNo) <= 0) {
                isCashBillStatus = false;
            } else if (cado.Fun_Int(checkingCounterBillNo) >= 1) {
                isCashBillStatus = true;
            }
        } catch (Exception ex) {
        }
        return SUCCESS;
    }

    public String cashbillBillNoFunction() {
        cashBillSrlNo = "";
        try {
            int cashBillOrderNoLength = 5;
            String cashBillSnoQuerry = "select max(cashBill_headerPk) from cashbill_header_master";
            cashBillSrlNo = cado.Fun_SalesOrderNo(cado.getPlantId(), session.get("LoginCounterId").toString(), cashBillSnoQuerry, cashBillOrderNoLength);
        } catch (Exception ex) {
            System.out.println("Exception In Creating CashbillBillNo :   " + ex);
        }
        return SUCCESS;
    }

    public String counterBillProcessedStatus() {
        try {
            if (!((counterBillNo.trim().equals("")) || (counterBillNo.trim().equalsIgnoreCase(null)))) {
                String counterBillStatus = "select count(*) from cashbill_lineitem_master where counterbill_no='" + counterBillNo.trim() + "'";
                int statusValue = cado.Fun_Int(counterBillStatus);
                if (statusValue >= 1) {
                    isCounterBillProcessedStatus = true;
                } else if (statusValue <= 0) {
                    isCounterBillProcessedStatus = false;
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception In Checking CounterBillStatus :   " + ex);
        }
        return SUCCESS;
    }

    public void couponRedemtionIns(String getCouponRedemptionData, String cashBillOrderNo) {
        try {
            vectorSeparateCoupon = new Vector(5);
            vectorIndivdualCoupon = new Vector(5);
            //String couponRedemValue[] = pushingCouponRedemptionDeatail.split(",");
            StringTokenizer tokenCouponRedemValues = new StringTokenizer(getCouponRedemptionData, ",");
            while (tokenCouponRedemValues.hasMoreTokens()) {
                vectorSeparateCoupon.add(tokenCouponRedemValues.nextElement());
            }

            for (int indivCouponLop = 0; indivCouponLop < vectorSeparateCoupon.size(); indivCouponLop++) {
                StringTokenizer indivCouponValues = new StringTokenizer(vectorSeparateCoupon.elementAt(indivCouponLop).toString(), "-");
                while (indivCouponValues.hasMoreTokens()) {
                    //System.out.println("Elements :  " + indivCouponValues.nextElement());
                    vectorIndivdualCoupon.add(indivCouponValues.nextElement());
                }
            }

            if (vectorSeparateCoupon.size() > 1) {
                int c1 = 0, c2 = 1, c3 = 2, c4 = 3;
                int tempCouponIndex = 1;
                for (int indivClop = 0; indivClop <= vectorIndivdualCoupon.size(); indivClop++) {
                    if (tempCouponIndex <= vectorSeparateCoupon.size()) {
                        con = cado.Fun_DbCon();
                        String Sp_CouponRedem = "{call sp_CouponRedemption(?,?,?,?,?,?)}";
                        pstmt = con.prepareCall(Sp_CouponRedem);
                        pstmt.setString("p_cashBilOrderNo", cashBillOrderNo);
                        pstmt.setFloat("p_couponTotalAmt", Float.valueOf(vectorIndivdualCoupon.elementAt(0).toString().trim()));
                        pstmt.setString("p_couponType", vectorIndivdualCoupon.elementAt(indivClop + c2).toString().trim());
                        pstmt.setString("p_couponNo", vectorIndivdualCoupon.elementAt(indivClop + c3).toString().trim());

                        /*FOR BACKDATED CouponRedemption*/
                        if (!((pushingBackDatedValue.equals("0000-01-01")) && (pushingCashierPkID.equals(" ")))) {
                            pstmt.setString("p_entryDateTime", pushingBackDatedValue.trim());
                            //pstmt.setString("p_backDatedBy", session.get("Login_Id").toString());
                        } else if (pushingBackDatedValue.equals("0000-01-01")) {
                            pstmt.setString("p_entryDateTime", "0000-00-00 00:00:00");
                            //pstmt.setString("p_backDatedBy", "NoData");
                        }
                        /*FOR BACKDATED CouponRedemption*/

                        pstmt.setFloat("p_amount", Float.valueOf(vectorIndivdualCoupon.elementAt(indivClop + c4).toString().trim()));
                        int couponRedemUpdateResponse = pstmt.executeUpdate();
                        c2 = c2 + 2;
                        c3 = c3 + 2;
                        c4 = c4 + 2;
                    }
                    tempCouponIndex++;
                }
            } else if (vectorSeparateCoupon.size() == 1) {
                con = cado.Fun_DbCon();
                String Sp_CouponRedem = "{call sp_CouponRedemption(?,?,?,?,?,?)}";
                pstmt = con.prepareCall(Sp_CouponRedem);
                pstmt.setString("p_cashBilOrderNo", cashBillOrderNo);
                pstmt.setFloat("p_couponTotalAmt", Float.valueOf(vectorIndivdualCoupon.elementAt(0).toString().trim()));
                pstmt.setString("p_couponType", vectorIndivdualCoupon.elementAt(1).toString().trim());
                pstmt.setString("p_couponNo", vectorIndivdualCoupon.elementAt(2).toString().trim());
                /*FOR BACKDATED CouponRedemption*/
                if (!((pushingBackDatedValue.equals("0000-01-01")) && (pushingCashierPkID.equals(" ")))) {
                    pstmt.setString("p_entryDateTime", pushingBackDatedValue.trim());
                    /*
                     TEMP-BLOCKED-15.04.2015
                     pstmt.setString("p_backDatedBy", session.get("Login_Id").toString());
                     */
                } else if (pushingBackDatedValue.equals("0000-01-01")) {
                    pstmt.setString("p_entryDateTime", "0000-00-00 00:00:00");
                    /*
                     TEMP-BLOCKED-15.04.2015
                     pstmt.setString("p_backDatedBy", "NoData");
                     */
                }
                /*FOR BACKDATED CouponRedemption*/

                pstmt.setFloat("p_amount", Float.valueOf(vectorIndivdualCoupon.elementAt(3).toString().trim()));
                int couponRedemUpdateResponse = pstmt.executeUpdate();
            }
        } catch (Exception ex) {
            System.out.println("" + ex);
        }
    }

    public String insertCashBillValuesToDb() {
        try {
            spCashBill_Header_Result = 0;
            spCashBill_LineItem_Result = 0;
            indivCashBillValue = new Vector(5);
            separated_CashBillValue = new Vector(5);

            con = cado.Fun_DbCon();
            con.setAutoCommit(false);

            int cashBillOrderNumber = 0;
            int cashBillReturnValue = 0;
            StringTokenizer TokenPurcProd = new StringTokenizer(receivePackagedValue, ",");
            while (TokenPurcProd.hasMoreTokens()) {
                separated_CashBillValue.add(TokenPurcProd.nextElement());
            }

            for (int k = 0; k < separated_CashBillValue.size(); k++) {
                StringTokenizer TokenSepProd_Details = new StringTokenizer(separated_CashBillValue.elementAt(k).toString().trim(), "-");
                while (TokenSepProd_Details.hasMoreElements()) {
                    indivCashBillValue.add(TokenSepProd_Details.nextElement());
                }
            }
            //cashBillNo = indivCashBillValue.elementAt(0).toString().trim();
            //System.out.println("CASH BILL EMP LOGIN ID : " + session.get("Login_Id").toString());
            //Sp_CashBilHeader = "";
            //Sp_CashBillLineItem = "";
            spCashBill_Header_Result = 0;
            //con = cado.Fun_DbCon();
            Sp_CashBilHeader = "{call sp_CashBillHeader(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            pstmt = con.prepareCall(Sp_CashBilHeader);
            //pstmt.setString("p_cashBillId", cashBillNo);
            //System.out.println("p_cashBillId  " + cashBillNo);
            pstmt.setString("p_paymentType", indivCashBillValue.elementAt(0).toString().trim());
            //System.out.println("p_paymentType  : " + indivCashBillValue.elementAt(0).toString().trim());

            pstmt.setString("p_cardType", indivCashBillValue.elementAt(1).toString().trim());
            //System.out.println("p_cardType : " + indivCashBillValue.elementAt(1).toString().trim());

            pstmt.setString("p_cardNumber", indivCashBillValue.elementAt(2).toString().trim());
            //System.out.println("p_cardNumber : " + indivCashBillValue.elementAt(2).toString().trim());

            pstmt.setString("p_cardExpDate", indivCashBillValue.elementAt(3).toString().trim());
            //System.out.println("p_cardExpDate : " + indivCashBillValue.elementAt(3).toString().trim());

            pstmt.setString("p_cardHolderName", indivCashBillValue.elementAt(4).toString().trim());
            //System.out.println("p_cardHolderName : " + indivCashBillValue.elementAt(4).toString().trim());

            pstmt.setString("p_currencyTypeSelected", indivCashBillValue.elementAt(5).toString().trim());
            //System.out.println("p_currencyTypeSelected : " + indivCashBillValue.elementAt(5).toString().trim());

            pstmt.setFloat("p_xChangeRate", Float.valueOf(indivCashBillValue.elementAt(6).toString().trim()));
            //System.out.println("p_xChangeRate : " + Float.valueOf(indivCashBillValue.elementAt(6).toString().trim()));

            pstmt.setFloat("p_coupnRedu", 0.0f);
            //System.out.println("p_coupnRedu : " + 0.0f);

            pstmt.setFloat("p_totalAmount", Float.valueOf(indivCashBillValue.elementAt(7).toString().trim()));
            //System.out.println("p_totalAmount : " + Float.valueOf(indivCashBillValue.elementAt(7).toString().trim()));

            pstmt.setString("p_counterBill", indivCashBillValue.elementAt(9).toString().trim());
            //System.out.println("p_counterBill : " + indivCashBillValue.elementAt(9).toString().trim());

            pstmt.setFloat("p_balaceAmount", Float.valueOf(indivCashBillValue.elementAt(8).toString().trim()));
            //System.out.println("p_balaceAmount : " + Float.valueOf(indivCashBillValue.elementAt(8).toString().trim()));

            /*FOR BACKDATED POSTING-HEADER
             TEMP-BLOCKED-15.04.2015
             if (!((pushingBackDatedValue.equals("0000-01-01")) && (pushingCashierPkID.equals(" ")))) {
             */
            if (!(pushingBackDatedValue.equals("0000-01-01"))) {
                pstmt.setString("p_entryDateTime", pushingBackDatedValue.trim());
                /*
                 TEMP-BLOCKED-15.04.2015
                 pstmt.setString("p_backDatedBy", session.get("Login_Id").toString());
                 pstmt.setString("p_userid", pushingCashierPkID.trim());
                 */
            } else if (pushingBackDatedValue.equals("0000-01-01")) {
                pstmt.setString("p_entryDateTime", "0000-00-00 00:00:00");
                /*
                 TEMP-BLOCKED-15.04.2015
                 pstmt.setString("p_backDatedBy", "NoData");
                 */
            }
            /*FOR BACKDATED POSTING-HEADER*/
            pstmt.setString("p_userid", session.get("Login_Id").toString());
            pstmt.setString("p_plantId", DaoClass.getPlantId());
            //System.out.println("plant :  " + cado.getPlantId());

            pstmt.registerOutParameter("p_return_cashBillNumber", java.sql.Types.BIGINT);
            pstmt.registerOutParameter("p_return_cashBillStatus", java.sql.Types.INTEGER);
            spCashBill_Header_Result = pstmt.executeUpdate();

            cashBillOrderNumber = pstmt.getInt("p_return_cashBillNumber");

            cashBillNo = String.valueOf(cashBillOrderNumber);
            cashBillReturnValue = pstmt.getInt("p_return_cashBillStatus");
            if (separated_CashBillValue.size() == 1) {
                if (cashBillReturnValue == 0) {
                    Sp_CashBillLineItem = "{call sp_CashBillLineItem(?,?,?,?,?,?)}";
                    pstmt = con.prepareCall(Sp_CashBillLineItem);
                    pstmt.setString("p_cashBillId", String.valueOf(cashBillOrderNumber));
                    pstmt.setString("p_counterBillNo", String.valueOf(indivCashBillValue.elementAt(9).toString().trim()));
                    pstmt.setDouble("p_cashBillAmt", Double.valueOf(indivCashBillValue.elementAt(10).toString().trim()));
                    /*
                     FOR BACKDATED POSTING-LINEITEM
                     TEMP-BLOCKED-15.04.2015
                     if (!((pushingBackDatedValue.equals("0000-01-01")) && (pushingCashierPkID.equals(" ")))) {
                     */
                    if (!(pushingBackDatedValue.equals("0000-01-01"))) {
                        pstmt.setString("p_entryDateTime", pushingBackDatedValue.trim());
                    } else if (pushingBackDatedValue.equals("0000-01-01")) {
                        pstmt.setString("p_entryDateTime", "0000-00-00 00:00:00");
                    }
                    /*FOR BACKDATED POSTING-LINEITEM*/
                    pstmt.setString("p_plantId", DaoClass.getPlantId());//SHOWROOM ID
                    pstmt.registerOutParameter("p_return_cashLineItemStatus", java.sql.Types.INTEGER);
                    spCashBill_LineItem_Result = pstmt.executeUpdate();
                    if (pstmt.getInt("p_return_cashLineItemStatus") == 0) {
                        printFlagValue = 1;
                        couponRedemtionIns(pushingCouponRedemptionDeatail, cashBillNo);
                        con.commit();
                    } else if (pstmt.getInt("p_return_cashLineItemStatus") == -1 || pstmt.getInt("p_return_cashLineItemStatus") == -2) {
                        con.rollback();
                    }
                }
            } else if (separated_CashBillValue.size() > 1) {
                if (cashBillReturnValue == 0) {
                    int tempIndexValue = 1, j = 0, k = 1;
                    for (int i = 9; i <= indivCashBillValue.size(); i++) {
                        if (tempIndexValue <= separated_CashBillValue.size()) {
                            Sp_CashBillLineItem = "{call sp_CashBillLineItem(?,?,?,?,?,?)}";
                            pstmt = con.prepareCall(Sp_CashBillLineItem);
                            pstmt.setString("p_cashBillId", String.valueOf(cashBillOrderNumber));
                            //System.out.println("------1     :   " + cashBillOrderNumber);
                            pstmt.setString("p_plantId", cado.getPlantId());//SHOWROOM ID
                            //System.out.println("------2     :   " + cado.getPlantId());
                            pstmt.setString("p_counterBillNo", indivCashBillValue.elementAt(i + j).toString().trim());
                            //System.out.println("------3==" + (i + j) + "    :   " + indivCashBillValue.elementAt(i + j).toString().trim());
                            pstmt.setString("p_cashBillAmt", indivCashBillValue.elementAt(i + k).toString().trim());
                            //System.out.println("------4==" + (i + k) + "  :   " + indivCashBillValue.elementAt(i + k).toString().trim());

                            /*FOR BACKDATED POSTING-LINEITEM
                             TEMP-BLOCKED-15.04.2015
                             if (!((pushingBackDatedValue.equals("0000-01-01")) && (pushingCashierPkID.equals(" ")))) {
                             */
                            if (!(pushingBackDatedValue.equals("0000-01-01"))) {
                                pstmt.setString("p_entryDateTime", pushingBackDatedValue.trim());
                            } else if (pushingBackDatedValue.equals("0000-01-01")) {
                                pstmt.setString("p_entryDateTime", "0000-00-00 00:00:00");
                            }
                            /*FOR BACKDATED POSTING-LINEITEM*/
                            pstmt.registerOutParameter("p_return_cashLineItemStatus", java.sql.Types.INTEGER);
                            j = j + 1;
                            k = k + 1;
                            spCashBill_LineItem_Result = pstmt.executeUpdate();
                        }
                        tempIndexValue++;
                    }
                    if (pstmt.getInt("p_return_cashLineItemStatus") == 0) {
                        printFlagValue = 1;
                        couponRedemtionIns(pushingCouponRedemptionDeatail, cashBillNo);
                        con.commit();
                    } else if (pstmt.getInt("p_return_cashLineItemStatus") == -1 || pstmt.getInt("p_return_cashLineItemStatus") == -2) {
                        con.rollback();
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception In Pushing CashBill Values Into Db :  " + ex);
            try {
                con.rollback();
            } catch (Exception e) {

            }
        }
        return SUCCESS;
    }

    public String getPushingCashierPkID() {
        return pushingCashierPkID;
    }

    public void setPushingCashierPkID(String pushingCashierPkID) {
        this.pushingCashierPkID = pushingCashierPkID;
    }

    public String getPushingBackDatedValue() {
        return pushingBackDatedValue;
    }

    public void setPushingBackDatedValue(String pushingBackDatedValue) {
        this.pushingBackDatedValue = pushingBackDatedValue;
    }

    public String getPushingCouponRedemptionDeatail() {
        return pushingCouponRedemptionDeatail;
    }

    public void setPushingCouponRedemptionDeatail(String pushingCouponRedemptionDeatail) {
        this.pushingCouponRedemptionDeatail = pushingCouponRedemptionDeatail;
    }

    public String getCounterBillNo() {
        return counterBillNo;
    }

    public void setCounterBillNo(String counterBillNo) {
        this.counterBillNo = counterBillNo;
    }

    public boolean isIsCounterBillProcessedStatus() {
        return isCounterBillProcessedStatus;
    }

    public void setIsCounterBillProcessedStatus(boolean isCounterBillProcessedStatus) {
        this.isCounterBillProcessedStatus = isCounterBillProcessedStatus;
    }

    public boolean isIsCashBillStatus() {
        return isCashBillStatus;
    }

    public void setIsCashBillStatus(boolean isCashBillStatus) {
        this.isCashBillStatus = isCashBillStatus;
    }

    public String getReprintCashBillNo() {
        return reprintCashBillNo;
    }

    public void setReprintCashBillNo(String reprintCashBillNo) {
        this.reprintCashBillNo = reprintCashBillNo;
    }

    public Map getSession() {
        return session;
    }

    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public int getCounterBillCancelStatus() {
        return counterBillCancelStatus;
    }

    public void setCounterBillCancelStatus(int counterBillCancelStatus) {
        this.counterBillCancelStatus = counterBillCancelStatus;
    }

    public String getCashBillSrlNo() {
        return cashBillSrlNo;
    }

    public void setCashBillSrlNo(String cashBillSrlNo) {
        this.cashBillSrlNo = cashBillSrlNo;
    }

    public String getCashBillNo() {
        return cashBillNo;
    }

    public void setCashBillNo(String cashBillNo) {
        this.cashBillNo = cashBillNo;
    }

    public int getPrintFlagValue() {
        return printFlagValue;
    }

    public void setPrintFlagValue(int printFlagValue) {
        this.printFlagValue = printFlagValue;
    }

    public String getReceivePackagedValue() {
        return receivePackagedValue;
    }

    public void setReceivePackagedValue(String receivePackagedValue) {
        this.receivePackagedValue = receivePackagedValue;
    }

    public int getCounterBillnoCountValue() {
        return counterBillnoCountValue;
    }

    public void setCounterBillnoCountValue(int counterBillnoCountValue) {
        this.counterBillnoCountValue = counterBillnoCountValue;
    }

    public String getDbNetAmt() {
        return dbNetAmt;
    }

    public void setDbNetAmt(String dbNetAmt) {
        this.dbNetAmt = dbNetAmt;
    }

    public String getDbDateTime() {
        return dbDateTime;
    }

    public void setDbDateTime(String dbDateTime) {
        this.dbDateTime = dbDateTime;
    }

    public String getTxtcounterBillNo() {
        return txtcounterBillNo;
    }

    public void setTxtcounterBillNo(String txtcounterBillNo) {
        this.txtcounterBillNo = txtcounterBillNo;
    }
}
