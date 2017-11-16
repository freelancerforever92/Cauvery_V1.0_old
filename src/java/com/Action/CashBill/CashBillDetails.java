package com.Action.CashBill;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.struts2.interceptor.SessionAware;

public class CashBillDetails extends ActionSupport implements SessionAware {

    Map session;
    private String invoiceNo;
    private float totalAmt = 0.0f;
    private String payType;
    private String casherName;
    private String createdDate;
    private String updatedOn;
    static ResultSet cashToCardRS;
    static Integer userId;
    //  static ResultSet getSynFlagValRs; 
    private int synVal;
    static ResultSet countRs;
    String date_time;
    String date;
    private String netAmt;
    private int updateVal;
    DaoClass daoClass = new DaoClass();
    private int userValidVal = 0;

    public String getCashBillInfo() {
        synchronized (this) {
            try {
                String getSynFlagValQry = "SELECT syncflag FROM pos.cashbill_lineitem_master where  cashbill_lineitem_master.counterbill_no ='" + invoiceNo.trim() + "'";
                synVal = daoClass.Fun_Int(getSynFlagValQry);
                if (synVal == 0) {
                    String getCashBillInfoQry = "SELECT cashbill_header_master.paymentType,\n"
                            + "cashbill_header_master.user_id,\n"
                            + "cashbill_lineitem_master.counterbill_no,\n"
                            + "cashbill_lineitem_master.cashbill_amt,\n"
                            //+ "DATE_FORMAT(cashbill_lineitem_master.cashbill_dateTime,'%d-%m-%Y %r')AS cashbill_dateTime,\n"
                            + "cashbill_lineitem_master.cashbill_dateTime AS cashbill_dateTime,\n"
                            + "DATE_FORMAT(cashbill_lineitem_master.lastupdated_date_time,'%d-%m-%Y %r')AS lastupdated_date_time,\n"
                            + "emp_master.emp_name\n"
                            + "FROM(   pos.cashbill_header_master cashbill_header_master\n"
                            + "INNER JOIN\n"
                            + "pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                            + "ON (cashbill_header_master.cashBill_id =\n"
                            + "cashbill_lineitem_master.cashBill_id))\n"
                            + "INNER JOIN\n"
                            + "pos.emp_master emp_master\n"
                            + "ON (cashbill_header_master.user_id = emp_master.emp_id)\n"
                            + "WHERE(cashbill_header_master.user_id = '" + session.get("Login_Id") + "')\n"
                            + "AND(cashbill_lineitem_master.counterbill_no ='" + invoiceNo.trim() + "')";
                    cashToCardRS = daoClass.Fun_Resultset(getCashBillInfoQry);
                    if (cashToCardRS.next()) {
                        userValidVal = 1;
                        invoiceNo = cashToCardRS.getString("counterbill_no");
                        totalAmt = cashToCardRS.getFloat("cashbill_amt");
                        payType = cashToCardRS.getString("paymentType");
                        casherName = cashToCardRS.getString("emp_name");
                        createdDate = cashToCardRS.getString("cashbill_dateTime");
                        updatedOn = cashToCardRS.getString("lastupdated_date_time");
                    } else {
                        userValidVal = 0;
                    }

                } else {
                    //System.err.println("Access Denied");
                }

            } catch (SQLException ex) {
                Logger.getLogger(CashBillDetails.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                daoClass.closeResultSet(cashToCardRS);
            }
        }
        return SUCCESS;
    }

    public String updatePayType() {
        try {
            int countNo = 0;
            String cashBill_id = null;
            String onlydate[] = date_time.split(" ");
            String split_Date = onlydate[0];
            String getCountCashBillId = "SELECT count(*) as countNo,cashBill_id FROM pos.cashbill_header_master where date_time like '" + split_Date.trim() + "%' and user_id='" + session.get("Login_Id").toString().trim() + "' and paymentType='" + payType.trim() + "'";
            countRs = daoClass.Fun_Resultset(getCountCashBillId);
            while (countRs.next()) {
                countNo = countRs.getInt("countNo");
                cashBill_id = countRs.getString("cashBill_id");
            }
            String getNetAmt = "SELECT net_amt FROM pos.header where sales_orderno='" + invoiceNo.trim() + "'";
            netAmt = daoClass.Fun_Str(getNetAmt);
            if (countNo > 0) {
                String detectAmtQry = "UPDATE pos.cashbill_header_master SET total_amt=total_amt-" + netAmt + "  WHERE  cashBill_id=(select cashBill_id from pos.cashbill_lineitem_master where counterbill_no='" + invoiceNo.trim() + "')";
                int detectAmtValue = daoClass.Fun_Updat(detectAmtQry);

                String addAmtQry = "UPDATE pos.cashbill_header_master SET total_amt=total_amt+" + netAmt + "  WHERE  cashBill_id='" + cashBill_id + "'";
                int addAmtVal = daoClass.Fun_Updat(addAmtQry);

                String updateCashBillidQry = "UPDATE `pos`.`cashbill_lineitem_master` SET `cashBill_id`='" + cashBill_id + "',lastupdated_date_time=now() WHERE  counterbill_no='" + invoiceNo.trim() + "';";
                updateVal = daoClass.Fun_Updat(updateCashBillidQry);

            } else if (countNo == 0) {
                String detectAmtQry = "UPDATE pos.cashbill_header_master SET total_amt=total_amt-" + netAmt + "  WHERE  cashBill_id=(select cashBill_id from pos.cashbill_lineitem_master where counterbill_no='" + invoiceNo.trim() + "')";
                int detectAmtValue = daoClass.Fun_Updat(detectAmtQry);
                String qry = "INSERT INTO pos.cashbill_header_master (counterbill_no, paymentType, cardType, cardNumber, cardExpriyDate, cardHolderName, currencyType, coupon_redu, currencyExchangeRate, copuon_TotalAmount, total_amt, balance_amt, plantId, user_id, syncflag, queueflag, date_time, entryDateTime) VALUES ('" + invoiceNo.trim() + "', '" + payType.trim() + "', 'NoData', 'NoData', 'NoData', 'NoData', 'NoData', '0', '0', '0', '" + netAmt + "', '0', '" + daoClass.getPlantId() + "', '" + session.get("Login_Id").toString().trim() + "', '0', '0', '" + date_time + "', now());";
                daoClass.Fun_Updat(qry);
                String lastInserterdCashBillQuery = "SELECT cashBill_id FROM pos.cashbill_header_master where counterbill_no='" + invoiceNo.trim() + "'";
                String lastInserterdCashBillId = daoClass.Fun_Str(lastInserterdCashBillQuery);
                String updateCashBillidQry = "UPDATE `pos`.`cashbill_lineitem_master` SET `cashBill_id`='" + lastInserterdCashBillId + "',lastupdated_date_time=now() WHERE  counterbill_no='" + invoiceNo.trim() + "'";
                updateVal = daoClass.Fun_Updat(updateCashBillidQry);

            } else {
                updateVal = 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CashBillDetails.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("UpdatePayment Type Exception :: " + ex.getMessage());
        }
        return SUCCESS;
    }

    public static Integer getUserId() {
        return userId;
    }

    public static void setUserId(Integer userId) {
        CashBillDetails.userId = userId;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public float getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(float totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getCasherName() {
        return casherName;
    }

    public void setCasherName(String casherName) {
        this.casherName = casherName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public int getSynVal() {
        return synVal;
    }

    public void setSynVal(int synVal) {
        this.synVal = synVal;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getUpdateVal() {
        return updateVal;
    }

    public void setUpdateVal(int updateVal) {
        this.updateVal = updateVal;
    }

    public int getUserValidVal() {
        return userValidVal;
    }

    public void setUserValidVal(int userValidVal) {
        this.userValidVal = userValidVal;
    }

}
