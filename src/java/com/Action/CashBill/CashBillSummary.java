package com.Action.CashBill;

import com.DAO.*;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import com.to.CashBillSummaryTo;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.struts2.interceptor.SessionAware;

/*@author Administrator*/
public class CashBillSummary extends ActionSupport implements SessionAware {

    Map session;
    String gridParameter;
    String cbToDate = "";
    String cbFromDate = "";
    String filterCbFromDate = "";
    String filterCbToDate = "";
    String sumOfTotalAmount;
    ResultSet resultSet = null;
    boolean searchStatus = false;

    String getCashBillSummary = "";
    String filterCashBillSummary = "";

    int totalCounterBillCountValue;
    String getCounterBillCountQuery = "";

    float cashBillTotalSum = 0.0f;

    String cashBillReportButton = "";
    String filterCashBillReportButton = "";

    String reportPaymentType;

    String cashBillReportPrintButton;

    String loginSessionId;
    private String loginSessionUserType;

    DaoClass daoClass = new DaoClass();
    List cashBillSummaryList = new ArrayList();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

    public String assignInputValues() {
        try {
            setCbToDate(cbToDate);
            setCbFromDate(cbFromDate);
            setReportPaymentType(reportPaymentType);
            setCashBillReportButton(cashBillReportButton);
        } catch (Exception ex) {
            System.out.println("Exception in assigning values : " + ex);
        }
        return SUCCESS;
    }

    public String CashBillSummaryData() {
        try {
            totalCounterBillCountValue = 0;
            StringTokenizer dat_tok = new StringTokenizer(gridParameter, "#");
            while (dat_tok.hasMoreElements()) {
                cbToDate = dat_tok.nextElement().toString();
                cbFromDate = dat_tok.nextElement().toString();
                reportPaymentType = dat_tok.nextElement().toString();
                cashBillReportButton = dat_tok.nextElement().toString();
            }
            /*
             sumOfTotalAmount = daoClass.Fun_Str("select sum(total_amt) from cashbill_header_master");
             String getCashBillSummary = "select cashBill_id,counterbill_no,total_amt,DATE_FORMAT(date_time,'%d-%m-%Y %h:%m:%s')as dateTime from cashbill_header_master where user_id='" + session.get("Login_Id").toString().trim() + "'";
             String getCraftGroupName = daoClass.Fun_Str("SELECT craft_counter_list.craft_group, branch_counter.counter FROM pos.branch_counter branch_counter INNER JOIN pos.craft_counter_list craft_counter_list ON (branch_counter.counter_no = craft_counter_list.storage_location)WHERE(branch_counter.counter = '" + session.get("LoginCounterName").toString().trim() + "')");
             if (cashBillReportButton.equalsIgnoreCase("Search")) {
             getCashBillSummary = "SELECT cashbill_header_master.cashBill_id,header.sales_orderno,sum(lineitem.calcu_value)as totalAmount,lineitem.materialCraftGroup,cashbill_header_master.user_id,DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime between '" + cbFromDate.trim() + "' and '" + cbToDate.trim() + "' FROM(pos.lineitem lineitem INNER JOIN pos.cashbill_header_master cashbill_header_master ON(lineitem.sales_orderno = cashbill_header_master.counterbill_no))INNER JOIN pos.header header ON (header.sales_orderno = lineitem.sales_orderno)where(cashbill_header_master.user_id='" + session.get("Login_Id").toString().trim() + "')and (lineitem.materialCraftGroup='" + getCraftGroupName.trim() + "')group by cashbill_header_master.cashBill_id";
             System.out.println("Ft : " + getCashBillSummary);
             } else {
             getCashBillSummary = "SELECT cashbill_header_master.cashBill_id,header.sales_orderno,sum(lineitem.calcu_value)as totalAmount,lineitem.materialCraftGroup,cashbill_header_master.user_id,DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime FROM(pos.lineitem lineitem INNER JOIN pos.cashbill_header_master cashbill_header_master ON(lineitem.sales_orderno = cashbill_header_master.counterbill_no))INNER JOIN pos.header header ON (header.sales_orderno = lineitem.sales_orderno)where(cashbill_header_master.user_id='" + session.get("Login_Id").toString().trim() + "')and (lineitem.materialCraftGroup='" + getCraftGroupName.trim() + "')group by cashbill_header_master.cashBill_id";
             }*/
            String yesterdayDate = daoClass.Fun_Str("SELECT Date(SUBDATE(NOW(),1))");
            if (!(cbToDate.equals("0000-01-01"))) {
                cbToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + cbToDate.trim() + "',1))");
            }
            if (cashBillReportButton.equals("") || cashBillReportButton.equalsIgnoreCase(null)) {
                ///*QUERRY BASED ON PLANT*/getCashBillSummary = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like '" + yesterdayDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')AND(cashbill_header_master.user_id='" + session.get("Login_Id").toString() + "')";
                getCashBillSummary = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like '" + yesterdayDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')";
                getCounterBillCountQuery = "select count(counterbill_no) from pos.cashbill_lineitem_master where cashbill_dateTime like '" + yesterdayDate.trim() + "%'";
            } else if (cashBillReportButton.equalsIgnoreCase("search")) {
                //getCashBillSummary = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time > '" + cbFromDate.trim() + "' AND cashbill_header_master.date_time <'" + cbToDate.trim() + "')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')AND(cashbill_header_master.user_id='" + session.get("Login_Id").toString() + "')";
                //getCashBillSummary = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time BETWEEN '" + cbFromDate.trim() + "%' AND '" + cbToDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')";
                if (cbFromDate.equals("0000-01-01") && (!(cbToDate.equals("0000-01-01")))) {
                    getCashBillSummary = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like'" + cbToDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')";
                    getCounterBillCountQuery = "select distinct count(counterbill_no)from pos.cashbill_lineitem_master where cashbill_dateTime like '" + cbToDate.trim() + "%'";
                } else if (cbToDate.equals("0000-01-01") && (!(cbFromDate.equals("0000-01-01")))) {
                    getCashBillSummary = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like'" + cbFromDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')";
                    getCounterBillCountQuery = "select distinct count(counterbill_no)from pos.cashbill_lineitem_master where cashbill_dateTime like '" + cbFromDate.trim() + "%'";
                } else if (cbFromDate.equals("0000-01-01") && cbToDate.equals("0000-01-01")) {
                    getCashBillSummary = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE (cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')";
                    getCounterBillCountQuery = "select distinct count(counterbill_no)from pos.cashbill_lineitem_master";
                } else if (!(((cbFromDate.equals("0000-01-01")) && ((cbToDate.equals("0000-01-01")))))) {
                    getCashBillSummary = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time between '" + cbFromDate.trim() + "' AND '" + cbToDate.trim() + "')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')";
                    //getCounterBillCountQuery = "select distinct count(counterbill_no)from pos.cashbill_lineitem_master where cashbill_dateTime between '" + cbFromDate.trim() + "' AND '" + cbToDate.trim() + "'";
                }
            }
            resultSet = daoClass.Fun_Resultset(getCashBillSummary);
            cashBillSummaryList.clear();
            while (resultSet.next()) {
                CashBillSummaryTo billSummaryTo = new CashBillSummaryTo(resultSet.getString("cashBill_id"), resultSet.getString("counterbill_no"), resultSet.getString("user_id"), resultSet.getString("paymentType"), resultSet.getFloat("cashbill_amt"), resultSet.getString("dateTime"), resultSet.getString("plantId"));
                cashBillSummaryList.add(billSummaryTo);
            }
            totalCounterBillCountValue = daoClass.Fun_Int(getCounterBillCountQuery);
        } catch (Exception ex) {
            System.out.println("Exception in getting cashbill summary data :  " + ex);
        } finally {
            daoClass.closeResultSet(resultSet);
        }
        return SUCCESS;
    }

    public String getCashSummaryTotal() {
        try {
            cashBillTotalSum = 0.0f;
            loginSessionId = session.get("Login_Id").toString();
            loginSessionUserType = session.get("User_type").toString().trim();
            if (filterCashBillReportButton.equals("") || filterCashBillReportButton.equalsIgnoreCase(null)) {
                String yesterdayDate = daoClass.Fun_Str("SELECT Date(SUBDATE(NOW(),1))");
                ///*QUERRY BASED ON PLANT*/getCashBillSummary = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like '" + yesterdayDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')AND(cashbill_header_master.user_id='" + session.get("Login_Id").toString() + "')";
                filterCashBillSummary = "SELECT sum(cashbill_lineitem_master.cashbill_amt) FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like '" + yesterdayDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')";
                getCounterBillCountQuery = "select count(counterbill_no) from pos.cashbill_lineitem_master where cashbill_dateTime like '" + yesterdayDate.trim() + "%'";
                cashBillTotalSum = daoClass.Fun_Float(filterCashBillSummary);
                totalCounterBillCountValue = daoClass.Fun_Int(getCounterBillCountQuery);
            }
            if (filterCashBillReportButton.equalsIgnoreCase("search")) {
                //getCashBillSummary = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time > '" + cbFromDate.trim() + "' AND cashbill_header_master.date_time <'" + cbToDate.trim() + "')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')AND(cashbill_header_master.user_id='" + session.get("Login_Id").toString() + "')";
                //getCashBillSummary = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as dateTime,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time BETWEEN '" + cbFromDate.trim() + "%' AND '" + cbToDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')";
                if (filterCbFromDate.equals("0000-01-01") && (!(filterCbToDate.equals("0000-01-01")))) {
                    //WHEN TO DATE IS GIVEN
                    /*
                     filterCashBillSummary = "SELECT sum(cashbill_lineitem_master.cashbill_amt) FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like'" + filterCbToDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')";
                     getCounterBillCountQuery = "select count(counterbill_no) from pos.cashbill_lineitem_master where cashbill_dateTime like '" + filterCbToDate.trim() + "%'";
                     */
                    filterCashBillSummary = "SELECT sum(cashbill_lineitem_master.cashbill_amt) FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like'" + filterCbToDate.trim() + "%')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')";
                    getCounterBillCountQuery = "SELECT count(cashbill_lineitem_master.counterbill_no)FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_lineitem_master.cashbill_dateTime like '" + filterCbToDate.trim() + "%')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')";
                } else if (filterCbToDate.equals("0000-01-01") && (!(filterCbFromDate.equals("0000-01-01")))) {
                    //WHEN FROM DATE IS GIVEN
                    /*
                     filterCashBillSummary = "SELECT sum(cashbill_lineitem_master.cashbill_amt) FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like'" + filterCbFromDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')";
                     getCounterBillCountQuery = "select count(counterbill_no) from pos.cashbill_lineitem_master where cashbill_dateTime like '" + filterCbFromDate.trim() + "%'";
                     */
                    //filterCashBillSummary = "SELECT sum(cashbill_lineitem_master.cashbill_amt)FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON(cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.paymentType ='" + reportPaymentType.trim() + "')AND(cashbill_lineitem_master.cashbill_dateTime  like'" + filterCbFromDate.trim() + "%'";
                    filterCashBillSummary = "SELECT sum(cashbill_lineitem_master.cashbill_amt) FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like'" + filterCbFromDate.trim() + "%')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')";
                    getCounterBillCountQuery = "SELECT count(cashbill_lineitem_master.counterbill_no)FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_lineitem_master.cashbill_dateTime like '" + filterCbFromDate.trim() + "%')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')";
                } else if (!((filterCbFromDate.equals("0000-01-01")) && ((filterCbToDate.equals("0000-01-01"))))) {
                    String nxtCbToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + filterCbToDate.trim() + "',1))");
                    //WHEN FROM DATE & TO DATE IS GIVEN
                    /*
                     filterCashBillSummary = "SELECT sum(cashbill_lineitem_master.cashbill_amt) FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time between'" + filterCbFromDate.trim() + "' AND '" + filterCbToDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')";
                     getCounterBillCountQuery = "select count(counterbill_no) from pos.cashbill_lineitem_master where cashbill_dateTime between '" + filterCbFromDate.trim() + "' AND '" + filterCbToDate.trim() + "%'";
                     //filterCashBillSummary = "SELECT sum(cashbill_lineitem_master.cashbill_amt) FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time between'" + filterCbFromDate.trim() + "' AND '" + filterCbToDate.trim() + "%')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "')";
                     */
                    filterCashBillSummary = "SELECT sum(cashbill_lineitem_master.cashbill_amt)FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON(cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.paymentType ='" + reportPaymentType.trim() + "')AND(cashbill_lineitem_master.cashbill_dateTime between'" + filterCbFromDate.trim() + "' AND '" + nxtCbToDate.trim() + "%')";
                    getCounterBillCountQuery = "SELECT count(cashbill_lineitem_master.counterbill_no)FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_lineitem_master.cashbill_dateTime between'" + filterCbFromDate.trim() + "' AND '" + nxtCbToDate.trim() + "%')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')";
                }
                cashBillTotalSum = daoClass.Fun_Float(filterCashBillSummary);
                totalCounterBillCountValue = daoClass.Fun_Int(getCounterBillCountQuery);
            }
        } catch (Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
        }
        return SUCCESS;
    }

    public String getLoginSessionUserType() {
        return loginSessionUserType;
    }

    public void setLoginSessionUserType(String loginSessionUserType) {
        this.loginSessionUserType = loginSessionUserType;
    }

    public String getFilterCbToDate() {
        return filterCbToDate;
    }

    public void setFilterCbToDate(String filterCbToDate) {
        this.filterCbToDate = filterCbToDate;
    }

    public String getLoginSessionId() {
        return loginSessionId;
    }

    public void setLoginSessionId(String loginSessionId) {
        this.loginSessionId = loginSessionId;
    }

    public String getReportPaymentType() {
        return reportPaymentType;
    }

    public void setReportPaymentType(String reportPaymentType) {
        this.reportPaymentType = reportPaymentType;
    }

    public String getFilterCbFromDate() {
        return filterCbFromDate;
    }

    public void setFilterCbFromDate(String filterCbFromDate) {
        this.filterCbFromDate = filterCbFromDate;
    }

    public String getFilterCashBillReportButton() {
        return filterCashBillReportButton;
    }

    public void setFilterCashBillReportButton(String filterCashBillReportButton) {
        this.filterCashBillReportButton = filterCashBillReportButton;
    }

    public float getCashBillTotalSum() {
        return cashBillTotalSum;
    }

    public void setCashBillTotalSum(float cashBillTotalSum) {
        this.cashBillTotalSum = cashBillTotalSum;
    }

    public int getTotalCounterBillCountValue() {
        return totalCounterBillCountValue;
    }

    public void setTotalCounterBillCountValue(int totalCounterBillCountValue) {
        this.totalCounterBillCountValue = totalCounterBillCountValue;
    }

    public String getGridParameter() {
        return gridParameter;
    }

    public void setGridParameter(String gridParameter) {
        this.gridParameter = gridParameter;
    }

    public boolean isSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(boolean searchStatus) {
        this.searchStatus = searchStatus;
    }

    public String getCashBillReportPrintButton() {
        return cashBillReportPrintButton;
    }

    public void setCashBillReportPrintButton(String cashBillReportPrintButton) {
        this.cashBillReportPrintButton = cashBillReportPrintButton;
    }

    public String getCashBillReportButton() {
        return cashBillReportButton;
    }

    public void setCashBillReportButton(String cashBillReportButton) {
        this.cashBillReportButton = cashBillReportButton;
    }

    public String getCbToDate() {
        return cbToDate;
    }

    public void setCbToDate(String cbToDate) {
        this.cbToDate = cbToDate;
    }

    public String getCbFromDate() {
        return cbFromDate;
    }

    public void setCbFromDate(String cbFromDate) {
        this.cbFromDate = cbFromDate;
    }

    public String getSumOfTotalAmount() {
        return sumOfTotalAmount;
    }

    public void setSumOfTotalAmount(String sumOfTotalAmount) {
        this.sumOfTotalAmount = sumOfTotalAmount;
    }

    public List getCashBillSummaryList() {
        return cashBillSummaryList;
    }

    public void setCashBillSummaryList(List cashBillSummaryList) {
        this.cashBillSummaryList = cashBillSummaryList;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }
}
