package com.Action.CashBill;
/*@author pranesh*/

import com.DAO.DaoClass;
import com.to.CashHeaderDTO;
import com.to.CashLineItemDTO;
import com.opensymphony.xwork2.ActionSupport;
import com.to.CashBillUserwiseTotalLineItemDCSR;
import com.to.CashHeaderDCSR;
import com.to.CashLineItemDCSR;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import com.to.CashierSummaryViewHeader;
import com.to.CounterTypeList;
import com.to.SalesIntimationList;
import com.to.VendorTypeList;
import java.math.BigDecimal;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashSet;

public class CashDailySummaryView_0 extends ActionSupport {

    int colCount;
    int pageIndex = 4;
    String cwsrExcelFileName;
    private String cbToDate;
    private String cbFromDate;
    private String loggedSessionId;
    private String reportPaymentType;
    private float cwdsTotAmount;
    private int cwdsTotBillCount = 0;

    ResultSet resultSet = null;
    String cwsrXcelFileName = null;
    DaoClass daoClass = new DaoClass();
    ResultSetMetaData resultSetmd = null;
    java.util.Date date = new java.util.Date();

    static ResultSet cashierSummaryLinesRs = null;
    static ResultSet cashierSummaryHeaderRs = null;

    static float grossTotal;
    static float netTotal;
    static float disToatl;
    static float vattotal;
    static float packTaltal;
    static float finalTatal;
    static List<String> counterInfo;
    static ResultSet counterTypeRs = null;
    static ResultSet vendorsListRs = null;
    static ResultSet SalesIntimationRs = null;
    static ResultSet SalesIntimationSummaryRs = null;
    static ResultSet craftGrpsRs = null;
    static ResultSet dcsrHeaderRs = null;
    static ResultSet dcsrLinesRs = null;
    static ResultSet userWiseLinesRs = null;
    static ResultSet userWisepackLinesRs = null;
    static ResultSet cwsrLinesRs = null;
    static ResultSet cwsrHeaderRs = null;
    //SimpleDateFormat datformat = new SimpleDateFormat("ddMMyyHHmmss");
    static CashierSummaryViewHeader header = new CashierSummaryViewHeader();

    public CashDailySummaryView_0() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /*
     public static List<CashHeaderDTO> displayCashDailySummaryReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId) {
     DaoClass daoClass = new DaoClass();
     List<CashHeaderDTO> chdtos = new ArrayList();
     try {
     String counterName = null;
     paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
     //String getCounterNoandNameQuery = "SELECT distinct branchCounter.counter_no_legacy,branchCounter.counter FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + paraPaymentType.trim() + "')AND(cashbillHeader.user_id = '" + paraLoggedSessionId.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";
     String getCounterNoandNameQuery = "SELECT distinct branch_counter.counter_no_legacy,branch_counter.counter FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)CROSS JOIN pos.header header INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)WHERE(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND(cashbill_header_master.user_id = '" + paraLoggedSessionId.trim() + "')AND(header.cancelFlag = 'N')AND(cashbill_lineitem_master.cashbill_dateTime  between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";
     cashierSummaryHeaderRs = daoClass.Fun_Resultset(getCounterNoandNameQuery);
     while (cashierSummaryHeaderRs.next()) {
     CashHeaderDTO chdto = new CashHeaderDTO();
     List<CashLineItemDTO> summaryViewLines = new ArrayList();
     counterName = cashierSummaryHeaderRs.getString("counter");
     if (paraPaymentType.equalsIgnoreCase("CRC")) {
     chdto.setPaymentType("CARD");
     } else {
     chdto.setPaymentType(paraPaymentType);
     }
     chdto.setHeaderDetail(cashierSummaryHeaderRs.getString("counter"));
     chdto.setHeaderId(cashierSummaryHeaderRs.getString("counter_no_legacy"));
     //String subTotalQry="SELECT sum(cashbillLineitem.cashbill_amt) FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + paraPaymentType.trim() + "')AND(cashbillHeader.user_id = '" + paraLoggedSessionId.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "'  AND branchCounter.counter='" + counterName.trim() + "')order by cashbillLineitem.counterbill_no asc";
     //ABOVE BLOCK QUERY IS WRONG/PRANESH/12-03-2015
     String subTotalQry = "SELECT sum(cashbill_lineitem_master.cashbill_amt) FROM((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.cashbill_header_master cashbill_header_master ON(cashbill_lineitem_master.cashBill_id =cashbill_header_master.cashBill_id))INNER JOIN pos.header header ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no))INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)WHERE(header.cancelFlag = 'N')AND(cashbill_header_master.user_id = '" + paraLoggedSessionId.trim() + "')AND(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND(branch_counter.counter = '" + counterName.trim() + "')AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')order by cashbill_lineitem_master.counterbill_no asc";
     chdto.setCashBillTotalAmount(BigDecimal.valueOf(daoClass.Fun_Int(subTotalQry)));
     //String getBillNoandAmtQuery = "SELECT cashbillLineitem.counterbill_no,cashbillLineitem.cashbill_amt FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + paraPaymentType.trim() + "')AND(cashbillHeader.user_id = '" + paraLoggedSessionId.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "'  AND branchCounter.counter='" + counterName.trim() + "')order by cashbillLineitem.counterbill_no asc";
     //ABOVE BLOCK QUERY IS WRONG/PRANESH/12-03-2015
     String getBillNoandAmtQuery = "SELECT cashbill_lineitem_master.counterbill_no,cashbill_lineitem_master.cashbill_amt FROM((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.cashbill_header_master cashbill_header_master ON(cashbill_lineitem_master.cashBill_id =cashbill_header_master.cashBill_id))INNER JOIN pos.header header ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no))INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)WHERE(header.cancelFlag = 'N')AND(cashbill_header_master.user_id = '" + paraLoggedSessionId.trim() + "')AND(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND(branch_counter.counter = '" + counterName.trim() + "')AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')order by cashbill_lineitem_master.counterbill_no asc";
     cashierSummaryLinesRs = daoClass.Fun_Resultset(getBillNoandAmtQuery);
     while (cashierSummaryLinesRs.next()) {
     CashLineItemDTO itemDTO = new CashLineItemDTO();
     itemDTO.setCashInvoiceNumber(cashierSummaryLinesRs.getLong("counterbill_no"));
     itemDTO.setCashBillAmount(cashierSummaryLinesRs.getBigDecimal("cashbill_amt"));
     summaryViewLines.add(itemDTO);
     }
     chdto.setCashLineItemDTO(summaryViewLines);
     chdtos.add(chdto);
     }
     } catch (Exception ex) {
     System.out.println("Exception in displaying CSRview : " + ex.getMessage());
     } finally {
     daoClass.closeResultSet(cashierSummaryHeaderRs);
     daoClass.closeResultSet(cashierSummaryLinesRs);
     }
     return chdtos;
     }
     */

    //<!--Changed Code Start-->
    public static List<CashLineItemDTO> displayCashDailySummaryReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId) {
        DaoClass daoClass = new DaoClass();
        List<CashHeaderDTO> chdtos = new ArrayList();
        List<CashLineItemDTO> summaryViewLines = new ArrayList();
        Float SubTotal = 0f, Total = 0f;
        try {
            String counterName = null;
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            //String getCounterNoandNameQuery = "SELECT distinct branchCounter.counter_no_legacy,branchCounter.counter FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + paraPaymentType.trim() + "')AND(cashbillHeader.user_id = '" + paraLoggedSessionId.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";
            String getCounterNoandNameQuery = "SELECT SQL_CACHE distinct branch_counter.counter_no_legacy,branch_counter.counter FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)CROSS JOIN pos.header header INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)WHERE (header.cancelFlag = 'N')\n ";
            //        + "(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')"
            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                getCounterNoandNameQuery = getCounterNoandNameQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
            }
            getCounterNoandNameQuery = getCounterNoandNameQuery + "AND(cashbill_header_master.user_id = '" + paraLoggedSessionId.trim() + "')AND(cashbill_lineitem_master.cashbill_dateTime  between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";
            cashierSummaryHeaderRs = daoClass.Fun_Resultset(getCounterNoandNameQuery);
            while (cashierSummaryHeaderRs.next()) {
                CashHeaderDTO chdto = new CashHeaderDTO();
                counterName = cashierSummaryHeaderRs.getString("counter");
                if (paraPaymentType.equalsIgnoreCase("CRC")) {
                    chdto.setPaymentType("CARD");
                } else {
                    chdto.setPaymentType(paraPaymentType);
                }
                String subTotalQry = "SELECT SQL_CACHE sum(cashbill_lineitem_master.cashbill_amt) FROM((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.cashbill_header_master cashbill_header_master ON(cashbill_lineitem_master.cashBill_id =cashbill_header_master.cashBill_id))INNER JOIN pos.header header ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no))INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)WHERE(header.cancelFlag = 'N')AND(cashbill_header_master.user_id = '" + paraLoggedSessionId.trim() + "') \n";
                //+ "AND(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')"
                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    subTotalQry = subTotalQry.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                }
                subTotalQry = subTotalQry + " AND(branch_counter.counter = '" + counterName.trim() + "')AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')order by cashbill_lineitem_master.counterbill_no asc";
                chdto.setCashBillTotalAmount(BigDecimal.valueOf(daoClass.Fun_Int(subTotalQry)));
                String getBillNoandAmtQuery = "SELECT SQL_CACHE cashbill_lineitem_master.counterbill_no as CounterBillNo,header.manual_bill_no as ManualBillNo,cashbill_lineitem_master.cashbill_amt as BillAmt,DATE_FORMAT(cashbill_lineitem_master.cashbill_dateTime,'%d-%m-%Y')AS DATE_TIME FROM((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.cashbill_header_master cashbill_header_master ON(cashbill_lineitem_master.cashBill_id =cashbill_header_master.cashBill_id))INNER JOIN pos.header header ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no))INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)WHERE(header.cancelFlag = 'N')AND(cashbill_header_master.user_id = '" + paraLoggedSessionId.trim() + "') \n";
                //+ "AND(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')"
                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    getBillNoandAmtQuery = getBillNoandAmtQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                }
                getBillNoandAmtQuery = getBillNoandAmtQuery + "AND(branch_counter.counter = '" + counterName.trim() + "')AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')order by cashbill_lineitem_master.counterbill_no asc";
                cashierSummaryLinesRs = daoClass.Fun_Resultset(getBillNoandAmtQuery);
                while (cashierSummaryLinesRs.next()) {
                    CashLineItemDTO itemDTO = new CashLineItemDTO();
                    itemDTO.setCounterNoName(cashierSummaryHeaderRs.getString("counter_no_legacy") + " " + cashierSummaryHeaderRs.getString("counter"));
                    itemDTO.setCashInvoiceNumber(cashierSummaryLinesRs.getLong("CounterBillNo"));
                    itemDTO.setManualBillNumber(cashierSummaryLinesRs.getString("ManualBillNo"));
                    itemDTO.setCashBillAmountFloat(cashierSummaryLinesRs.getFloat("BillAmt"));
                    itemDTO.setProcessedDate(cashierSummaryLinesRs.getString("DATE_TIME"));
                    SubTotal = SubTotal + cashierSummaryLinesRs.getFloat("BillAmt");
                    Total = Total + cashierSummaryLinesRs.getFloat("BillAmt");
                    summaryViewLines.add(itemDTO);
                }
                if (SubTotal != 0) {
                    CashLineItemDTO itemDTOSubTotal = new CashLineItemDTO();
                    itemDTOSubTotal.setCounterNoName("Sub-Total");
                    itemDTOSubTotal.setManualBillNumber("");
                    itemDTOSubTotal.setCashBillAmountFloat(SubTotal);
                    summaryViewLines.add(itemDTOSubTotal);
                }
                SubTotal = 0f;
            }
            CashLineItemDTO itemDTOTotal = new CashLineItemDTO();
            itemDTOTotal.setCounterNoName("Total");
            itemDTOTotal.setManualBillNumber("");
            itemDTOTotal.setCashBillAmountFloat(Total);
            summaryViewLines.add(itemDTOTotal);
            Total = 0f;
        } catch (Exception ex) {
            System.out.println("Exception in displaying CSRview : " + ex.getMessage());
        } finally {
            daoClass.closeResultSet(cashierSummaryHeaderRs);
            daoClass.closeResultSet(cashierSummaryLinesRs);
        }
        return summaryViewLines;
    }
    //<!--Changed Code End-->

    public String getTotBilNoandAmt() {
        try {
            String cName = null;
            //String sterDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + cbToDate.trim() + "',1))");
            String getCounterNoandNameQuery = "SELECT SQL_CACHE distinct branchCounter.counter_no_legacy,branchCounter.counter FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(cashbillHeader.user_id = '" + loggedSessionId.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime between '" + cbFromDate.trim() + "' AND '" + cbToDate.trim() + "')";
            cwsrHeaderRs = daoClass.Fun_Resultset(getCounterNoandNameQuery);
            while (cwsrHeaderRs.next()) {
                cName = cwsrHeaderRs.getString("counter");
                /*String getBillNoandAmtQuery = "SELECT cashbillLineitem.counterbill_no,cashbillLineitem.cashbill_amt FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(cashbillHeader.user_id = '" + loggedSessionId.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime between '" + cbFromDate.trim() + "' AND '" + sterDate.trim() + "'  AND branchCounter.counter='" + cName.trim() + "')order by cashbillLineitem.counterbill_no asc";*/
                /*ABOVE BLOCK QUERY IS WRONG/PRANESH/12-03-2015*/
                //String getBillNoandAmtQuery = "SELECT SQL_CACHE cashbill_lineitem_master.counterbill_no,cashbill_lineitem_master.cashbill_amt FROM((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.cashbill_header_master cashbill_header_master ON(cashbill_lineitem_master.cashBill_id =cashbill_header_master.cashBill_id))INNER JOIN pos.header header ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no))INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)WHERE(header.cancelFlag = 'N')AND(cashbill_header_master.user_id = '" + loggedSessionId.trim() + "')AND(cashbill_header_master.paymentType ='" + reportPaymentType.trim() + "')AND(branch_counter.counter = '" + cName.trim() + "')AND(cashbill_lineitem_master.cashbill_dateTime between '" + cbFromDate.trim() + "' AND '" + sterDate.trim() + "')order by cashbill_lineitem_master.counterbill_no asc";
                String getBillNoandAmtQuery = "SELECT SQL_CACHE cashbill_lineitem_master.counterbill_no,cashbill_lineitem_master.cashbill_amt FROM((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.cashbill_header_master cashbill_header_master ON(cashbill_lineitem_master.cashBill_id =cashbill_header_master.cashBill_id))INNER JOIN pos.header header ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no))INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)WHERE(header.cancelFlag = 'N')AND(cashbill_header_master.user_id = '" + loggedSessionId.trim() + "')AND(cashbill_header_master.paymentType ='" + reportPaymentType.trim() + "')AND(branch_counter.counter = '" + cName.trim() + "')AND(cashbill_lineitem_master.cashbill_dateTime between str_to_date('" + cbFromDate.trim() + "','%Y-%m-%d') AND str_to_date('" + cbToDate.trim() + "','%Y-%m-%d'))order by cashbill_lineitem_master.counterbill_no asc";
                cwsrLinesRs = daoClass.Fun_Resultset(getBillNoandAmtQuery);
                while (cwsrLinesRs.next()) {
                    cwdsTotBillCount++;
                    cwdsTotAmount += cwsrLinesRs.getFloat("cashbill_amt");
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception : " + ex.getMessage());
        } finally {
            daoClass.closeResultSet(cwsrHeaderRs);
            daoClass.closeResultSet(cwsrLinesRs);
        }
        return SUCCESS;
    }
    /*
     BLOCKED BY PRANESH-24.04.2015-DSCR WITHOUT DISPLAY TAG
     public static List<CashHeaderDCSR> displayCashDailySummaryReportDCSR(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId) {
     DaoClass daoClass = new DaoClass();
     List<CashHeaderDCSR> dCSRtos = new ArrayList();
     try {
     String counterName = null;
     paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
     //String getCounterNoandNameQuery = "SELECT distinct branchCounter.counter_no_legacy,branchCounter.counter FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id) WHERE(cashbillHeader.paymentType ='" + paraPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";
     String getCounterNoandNameQuery = "SELECT distinct branch_counter.counter_no_legacy, branch_counter.counter FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)CROSS JOIN pos.header header INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)WHERE(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND(header.cancelFlag = 'N')AND(cashbill_lineitem_master.cashbill_dateTime  between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";
     dcsrHeaderRs = daoClass.Fun_Resultset(getCounterNoandNameQuery);
     while (dcsrHeaderRs.next()) {
     CashHeaderDCSR dCSRto = new CashHeaderDCSR();
     List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList();
     List<CashBillUserwiseLineItemDCSR> cSRs = new ArrayList();
     counterName = dcsrHeaderRs.getString("counter");
     if (paraPaymentType.equalsIgnoreCase("CRC")) {
     dCSRto.setPaymentType("CARD");
     } else {
     dCSRto.setPaymentType(paraPaymentType);
     }

     dCSRto.setHeaderDetail(dcsrHeaderRs.getString("counter"));
     dCSRto.setHeaderId(dcsrHeaderRs.getString("counter_no_legacy"));
     //String getBillNoandAmtQuery = "SELECT cashbill_lineitem_master.counterbill_no,SUM(lineitem.prc_value) as Gross_Amount,SUM(lineitem.discount_value) as Disc_Amount,SUM(lineitem.prc_value-lineitem.discount_value) as Net_Amount,SUM(lineitem.vat_value) as Vat_Amount,lineitem.pck_charge as Pack_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+lineitem.pck_charge as cashbill_amt,emp_master.emp_name FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no =     lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id)) INNER JOIN    pos.emp_master emp_master     ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "' AND branch_Counter.counter='" + counterName.trim() + "')group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc";
     String getBillNoandAmtQuery = "SELECT cashbill_lineitem_master.counterbill_no,SUM(lineitem.prc_value) as Gross_Amount,SUM(lineitem.discount_value) as Disc_Amount,SUM(lineitem.prc_value-lineitem.discount_value) as Net_Amount,SUM(lineitem.vat_value) as Vat_Amount,lineitem.pck_charge as Pack_Amount,header.net_amt+header.pck_charge as cashbill_amt,emp_master.emp_name FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "' AND branch_Counter.counter='" + counterName.trim() + "')group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc";
     dcsrLinesRs = daoClass.Fun_Resultset(getBillNoandAmtQuery);
     while (dcsrLinesRs.next()) {
     CashLineItemDCSR itemDCSR = new CashLineItemDCSR();
     itemDCSR.setCashInvoiceNumber(dcsrLinesRs.getBigDecimal("counterbill_no"));
     itemDCSR.setGrossAmount(dcsrLinesRs.getBigDecimal("Gross_Amount"));
     itemDCSR.setDiscAmount(dcsrLinesRs.getBigDecimal("Disc_Amount"));
     itemDCSR.setNetAmount(dcsrLinesRs.getBigDecimal("Net_Amount"));
     itemDCSR.setVatAmount(dcsrLinesRs.getBigDecimal("Vat_Amount"));
     itemDCSR.setPackAmount(dcsrLinesRs.getBigDecimal("Pack_Amount"));
     itemDCSR.setCashBillAmount(Math.round(Float.valueOf(dcsrLinesRs.getFloat("cashbill_amt"))));
     itemDCSR.setEmpName(dcsrLinesRs.getString("emp_name"));
     grossTotal += dcsrLinesRs.getFloat("Gross_Amount");
     netTotal += dcsrLinesRs.getFloat("Disc_Amount");
     disToatl += dcsrLinesRs.getFloat("Net_Amount");
     vattotal += dcsrLinesRs.getFloat("Vat_Amount");
     packTaltal += dcsrLinesRs.getFloat("Pack_Amount");
     finalTatal += dcsrLinesRs.getFloat("cashbill_amt");
     //finalTatal += Math.round(Float.valueOf(dcsrLinesRs.getFloat("cashbill_amt")));
     summaryViewLinesDCSR.add(itemDCSR);
     }
     dCSRto.setCashLineItemDTO(summaryViewLinesDCSR);
     dCSRto.setGrossTotal(grossTotal);
     dCSRto.setNetTotal(netTotal);
     dCSRto.setDisToatl(disToatl);
     dCSRto.setVattotal(vattotal);
     dCSRto.setPackTaltal(packTaltal);
     dCSRto.setFinalTatal(Math.round(finalTatal));
     //String userSubtotalQuery = "SELECT sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as cashbill_amt,emp_master.emp_name FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id)) INNER JOIN    pos.emp_master emp_master     ON (cashbill_header_master.user_id = emp_master.emp_id)  WHERE (cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "' AND branch_Counter.counter='" + counterName.trim() + "')group by emp_master.emp_name  order by cashbill_header_master.user_id asc";
     String userSubtotalQuery = "SELECT sum(header.net_amt)+sum(header.pck_charge) as cashbill_amt,cashbill_header_master.user_id FROM  pos.header header INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk) INNER JOIN	pos.cashbill_lineitem_master cashbill_lineitem_master ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no) INNER JOIN pos.cashbill_header_master cashbill_header_master ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id) INNER JOIN pos.emp_master emp_master ON (header.emp_id = emp_master.emp_id) WHERE (cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "' AND branch_Counter.counter='" + counterName.trim() + "')group by cashbill_header_master.user_id   order by cashbill_header_master.user_id asc";
     userWiseLinesRs = daoClass.Fun_Resultset(userSubtotalQuery);
     String empName = null;
     while (userWiseLinesRs.next()) {
     String userpackSubtotalQuery = "SELECT emp_name FROM pos.emp_master where emp_id='" + userWiseLinesRs.getString("user_id") + "'";
     userWisepackLinesRs = daoClass.Fun_Resultset(userpackSubtotalQuery);
     while (userWisepackLinesRs.next()) {
     empName = userWisepackLinesRs.getString("emp_name");
     }
     CashBillUserwiseLineItemDCSR values = new CashBillUserwiseLineItemDCSR();
     //values.setuserName(userWiseLinesRs.getString("emp_name"));
     values.setuserName(empName);
     values.setuserAmount(Math.round(Float.valueOf(userWiseLinesRs.getFloat("cashbill_amt"))));
     cSRs.add(values);
     empName = null;
     }
     dCSRto.setCashBillUserwiseLineItemDCSR(cSRs);
     dCSRtos.add(dCSRto);
     grossTotal = 0;
     netTotal = 0;
     disToatl = 0;
     vattotal = 0;
     packTaltal = 0;
     finalTatal = 0;
     }
     } catch (Exception ex) {
     System.out.println("Exception in displaying DCSR: " + ex.getMessage());
     } finally {
     daoClass.closeResultSet(dcsrHeaderRs);
     daoClass.closeResultSet(dcsrLinesRs);
     daoClass.closeResultSet(userWiseLinesRs);
     daoClass.closeResultSet(userWisepackLinesRs);
     }
     return dCSRtos;
     }*/

    //<!--CHANGED CODE START-->
    public static List<CashLineItemDCSR> displayCashDailySummaryReportDCSR(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String craftGroups, String vendorIds) {
        vendorIds = "'" + vendorIds + "'";
        craftGroups = "'" + craftGroups + "'";
        String query = null;
        String counterName = null;
        DaoClass daoClass = new DaoClass();
        CashHeaderDCSR dCSRto = new CashHeaderDCSR();
        List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList();
        try {
            /*
             String query = "SELECT *\n"
             + "  FROM pos.lineitem lineitem\n"
             + "  INNER JOIN\n"
             + "  pos.craft_counter_list craft_counter_list\n"
             + "  ON (lineitem.materialCraftGroup = craft_counter_list.craft_group)\n"
             + " WHERE ";
             */
            String arrayVendorIds[] = vendorIds.split(",");
            String arrayCraftGroup[] = craftGroups.split(",");

            /*Toget next DATE from given date.*/
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            /*Toget next DATE from given date.*/
            String collectCraftGroups = "SELECT SQL_CACHE distinct lineitem.materialCraftGroup as materialCraftGroup\n"
                    + "  FROM ((pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                    + "INNER JOIN\n"
                    + "pos.cashbill_header_master cashbill_header_master\n"
                    + "ON (cashbill_lineitem_master.cashBill_id =\n"
                    + "cashbill_header_master.cashBill_id))\n"
                    + "INNER JOIN\n"
                    + "pos.lineitem lineitem\n"
                    + "ON (lineitem.sales_orderno =\n"
                    + "cashbill_lineitem_master.counterbill_no))\n"
                    + "INNER JOIN\n"
                    + "pos.header header\n"
                    + "ON (header.sales_orderno = lineitem.sales_orderno)\n"
                    + "WHERE (header.cancelFlag = 'N')\n"
                    + "AND (cashbill_lineitem_master.cashbill_dateTime  between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";
            craftGrpsRs = daoClass.Fun_Resultset(collectCraftGroups);
            while (craftGrpsRs.next()) {
                //System.out.println("=> 2 :: " + craftGrpsRs.getString("materialCraftGroup").trim());
            }
            if (vendorIds.contains("All") && craftGroups.contains("All")) {
                /*craftGroups = "'SW04','SB06','PF02'";
                 vendorIds = "'450030','410046','450039'";
                 query = query.concat(" (craft_counter_list.storage_location IN (" + craftGroups.trim() + "))AND(lineitem.vendor IN (" + vendorIds + "))");
                 System.out.println("INSIDE BOTH :: VENDOR ID :: " + vendorIds);
                 System.out.println("INSIDE BOTH :: CRAFT ID  :: " + craftGroups);
                 */
            } else if (!vendorIds.contains("All") && !craftGroups.contains("All")) {
                String mutiCounterName = "";
                String updatedPaymentType = "";

                Float netTotAmt = 0.0f;
                Float vatTotAmt = 0.0f;
                Float packTotAmt = 0.0f;
                Float discTotAmt = 0.0f;
                Float grossTotAmt = 0.0f;
                Float cashBillTotAmt = 0.0f;

                for (String lstCraftGroup : arrayCraftGroup) {
                    for (String lstVendors : arrayVendorIds) {
                        System.out.println("TEST");
                        Float discSubTotAmt = 0.0f;
                        Float netSubTotAmt = 0.0f;
                        Float vatSubTotAmt = 0.0f;
                        Float packSubTotAmt = 0.0f;
                        Float grossSubTotAmt = 0.0f;
                        Float cashBillSubTotAmt = 0.0f;
                        String getBillNoandAmtQuery = "SELECT SQL_CACHE cashbill_header_master.paymentType as PaymentType,header.manual_bill_no,cashbill_lineitem_master.counterbill_no,branch_counter.counter_no_legacy,branch_counter.counter,SUM(lineitem.prc_value) as Gross_Amount,SUM(lineitem.discount_value) as Disc_Amount,SUM(lineitem.prc_value-lineitem.discount_value) as Net_Amount,SUM(lineitem.vat_value) as Vat_Amount,lineitem.pck_charge as Pack_Amount,header.net_amt+header.pck_charge as cashbill_amt,emp_master.emp_name FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) INNER JOIN pos.craft_counter_list counterCraft ON (branch_counter.counter_no = counterCraft.storage_location) WHERE (header.cancelFlag='N') \n";
                        if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                            getBillNoandAmtQuery = getBillNoandAmtQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                        }
                        getBillNoandAmtQuery = getBillNoandAmtQuery + " AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "'  AND '" + paraToDate.trim() + "' AND lineitem.vendor=" + lstVendors + " AND lineitem.materialCraftGroup IN (" + lstCraftGroup + "))group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc";
                        dcsrLinesRs = daoClass.Fun_Resultset(getBillNoandAmtQuery);
                        System.out.println("getBillNoandAmtQuery :: " + getBillNoandAmtQuery);
                        while (dcsrLinesRs.next()) {
                            CashLineItemDCSR itemDCSR = new CashLineItemDCSR();
                            if (dcsrLinesRs.getString("PaymentType").equalsIgnoreCase("crc")) {
                                updatedPaymentType = "CARD";
                            } else if (!dcsrLinesRs.getString("PaymentType").equalsIgnoreCase("crc")) {
                                updatedPaymentType = dcsrLinesRs.getString("PaymentType");
                            }
                            counterName = dcsrLinesRs.getString("counter");
                            itemDCSR.setCashInvoiceNumber(dcsrLinesRs.getString("counterbill_no") + "-" + updatedPaymentType);
                            itemDCSR.setManualBillNo(dcsrLinesRs.getString("manual_bill_no"));
                            itemDCSR.setGrossAmountFloat(dcsrLinesRs.getFloat("Gross_Amount"));
                            itemDCSR.setDiscAmountFloat(dcsrLinesRs.getFloat("Disc_Amount"));
                            itemDCSR.setNetAmountFloat(dcsrLinesRs.getFloat("Net_Amount"));
                            itemDCSR.setVatAmountFloat(dcsrLinesRs.getFloat("Vat_Amount"));
                            itemDCSR.setPackAmountFloat(dcsrLinesRs.getFloat("Pack_Amount"));
                            itemDCSR.setCashBillAmountFloat(Math.round((dcsrLinesRs.getFloat("Net_Amount") + dcsrLinesRs.getFloat("Vat_Amount") + dcsrLinesRs.getFloat("Pack_Amount"))));
                            itemDCSR.setEmpName(dcsrLinesRs.getString("emp_name"));
                            itemDCSR.setDcsrDate(dcsrLinesRs.getString("counter_no_legacy") + "-" + dcsrLinesRs.getString("counter"));
                            itemDCSR.setCount(1);
                            /*SUB-TOTAL CALCULATION*/
                            grossSubTotAmt = grossSubTotAmt + itemDCSR.getGrossAmountFloat();
                            discSubTotAmt = discSubTotAmt + itemDCSR.getDiscAmountFloat();
                            netSubTotAmt = netSubTotAmt + itemDCSR.getNetAmountFloat();
                            vatSubTotAmt = vatSubTotAmt + itemDCSR.getVatAmountFloat();
                            packSubTotAmt = packSubTotAmt + itemDCSR.getPackAmountFloat();
                            cashBillSubTotAmt = cashBillSubTotAmt + itemDCSR.getCashBillAmountFloat();
                            /*TOTAL CALCULATION*/
                            grossTotAmt = grossTotAmt + itemDCSR.getGrossAmountFloat();
                            discTotAmt = discTotAmt + itemDCSR.getDiscAmountFloat();
                            netTotAmt = netTotAmt + itemDCSR.getNetAmountFloat();
                            vatTotAmt = vatTotAmt + itemDCSR.getVatAmountFloat();
                            packTotAmt = packTotAmt + itemDCSR.getPackAmountFloat();
                            cashBillTotAmt = cashBillTotAmt + itemDCSR.getCashBillAmountFloat();
                            summaryViewLinesDCSR.add(itemDCSR);
                        }
                        mutiCounterName = mutiCounterName + "," + counterName;
                        if (cashBillTotAmt != 0) {
                            CashLineItemDCSR itemDCSRS = new CashLineItemDCSR();
                            itemDCSRS.setGrossAmountFloat(grossSubTotAmt);
                            System.out.println("Sub-Gross :: " + grossSubTotAmt);
                            itemDCSRS.setDiscAmountFloat(discSubTotAmt);
                            itemDCSRS.setNetAmountFloat(netSubTotAmt);
                            itemDCSRS.setVatAmountFloat(vatSubTotAmt);
                            itemDCSRS.setPackAmountFloat(packSubTotAmt);
                            itemDCSRS.setCashBillAmountFloat(cashBillSubTotAmt);
                            itemDCSRS.setEmpName(" ");
                            itemDCSRS.setCount(0);
                            itemDCSRS.setManualBillNo(" ");
                            itemDCSRS.setDcsrDate("Sub-Total" + "( " + counterName + ")");
                            summaryViewLinesDCSR.add(itemDCSRS);
                        }
                    }
                }
            } else if ((!(vendorIds.contains("All"))) && ((craftGroups.contains("All")))) {
                /*
                 getCounterNoandNameQuery = getCounterNoandNameQuery.concat(query);
                 craftGroups = "'SW04','SB06','PF02'";
                 query = query.concat(" (craft_counter_list.storage_location IN (" + craftGroups + "))AND(lineitem.vendor IN (" + vendorIds + "))");
                 System.out.println("VENDOR ID    :: " + vendorIds);
                 System.out.println("CRAFT ID ALL :: " + craftGroups);
                 */
            } else if (((vendorIds.contains("All"))) && (!(craftGroups.contains("All")))) {
                vendorIds = "'450030','410046','450039'";
                query = query.concat(" (craft_counter_list.storage_location IN (" + craftGroups + "))AND(lineitem.vendor IN (" + vendorIds + "))");
                System.out.println("VENDOR ID ALL :: " + vendorIds);
                System.out.println("CRAFT ID      :: " + craftGroups);
            }
            System.out.println("OUTPUT QUERY :: " + query);
        } catch (Exception ex) {
            System.out.println("" + ex.getMessage());
        }
        return summaryViewLinesDCSR;
    }

    public static List<CashLineItemDCSR> displayCashDailySummaryReportDCSR_1(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String craftGroups, String vendorIds) {
        DaoClass daoClass = new DaoClass();
        Float discAmountS = 0.0f;
        Float netAmountS = 0.0f;
        Float vatAmountS = 0.0f;
        Float packAmountS = 0.0f;
        Float grossAmountS = 0.0f;
        Float cashBillAmountFloat = 0.0f;
        List<CashHeaderDCSR> dCSRtos = new ArrayList();
        List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList();

        Float netAmountT = 0.0f;
        Float vatAmountT = 0.0f;
        Float packAmountT = 0.0f;
        Float discAmountT = 0.0f;
        Float grossAmountT = 0.0f;
        Float cashBillAmountT = 0.0f;
        try {
            String counterName = "";
            String mutiCounterName = "";
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");/*Toget the next from the given date.*/

            //String getCounterNoandNameQuery = "SELECT distinct branchCounter.counter_no_legacy,branchCounter.counter FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id) WHERE(cashbillHeader.paymentType ='" + paraPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";
            String getCounterNoandNameQuery = "SELECT distinct SQL_CACHE  branch_counter.counter_no_legacy, branch_counter.counter FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)CROSS JOIN pos.header header INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)WHERE (header.cancelFlag = 'N') \n";

            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                getCounterNoandNameQuery = getCounterNoandNameQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
            }
            getCounterNoandNameQuery = getCounterNoandNameQuery + "AND(cashbill_lineitem_master.cashbill_dateTime  between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";
            dcsrHeaderRs = daoClass.Fun_Resultset(getCounterNoandNameQuery);
            CashHeaderDCSR dCSRto = new CashHeaderDCSR();
            while (dcsrHeaderRs.next()) {
                //List<CashBillUserwiseLineItemDCSR> cSRs = new ArrayList();
                counterName = dcsrHeaderRs.getString("counter");
                if (paraPaymentType.equalsIgnoreCase("CRC")) {
                    dCSRto.setPaymentType("CARD");
                } else {
                    dCSRto.setPaymentType(paraPaymentType);
                }

                dCSRto.setHeaderDetail(dcsrHeaderRs.getString("counter"));
                dCSRto.setHeaderId(dcsrHeaderRs.getString("counter_no_legacy"));
                //String getBillNoandAmtQuery = "SELECT cashbill_lineitem_master.counterbill_no,SUM(lineitem.prc_value) as Gross_Amount,SUM(lineitem.discount_value) as Disc_Amount,SUM(lineitem.prc_value-lineitem.discount_value) as Net_Amount,SUM(lineitem.vat_value) as Vat_Amount,lineitem.pck_charge as Pack_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+lineitem.pck_charge as cashbill_amt,emp_master.emp_name FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no =     lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id)) INNER JOIN    pos.emp_master emp_master     ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "' AND branch_Counter.counter='" + counterName.trim() + "')group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc";
                String getBillNoandAmtQuery = "SELECT SQL_CACHE cashbill_header_master.paymentType as PaymentType ,header.manual_bill_no,cashbill_lineitem_master.counterbill_no,SUM(lineitem.prc_value) as Gross_Amount,SUM(lineitem.discount_value) as Disc_Amount,SUM(lineitem.prc_value-lineitem.discount_value) as Net_Amount,SUM(lineitem.vat_value) as Vat_Amount,lineitem.pck_charge as Pack_Amount,header.net_amt+header.pck_charge as cashbill_amt,emp_master.emp_name FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE (header.cancelFlag='N') \n";
                //+ "(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')"
                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    getBillNoandAmtQuery = getBillNoandAmtQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                }
                getBillNoandAmtQuery = getBillNoandAmtQuery + " AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "'  AND '" + paraToDate.trim() + "' AND branch_Counter.counter='" + counterName.trim() + "')group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc";
                dcsrLinesRs = daoClass.Fun_Resultset(getBillNoandAmtQuery);
                while (dcsrLinesRs.next()) {
                    CashLineItemDCSR itemDCSR = new CashLineItemDCSR();
                    itemDCSR.setCashInvoiceNumber(dcsrLinesRs.getString("counterbill_no") + "-" + dcsrLinesRs.getString("PaymentType"));
                    itemDCSR.setManualBillNo(dcsrLinesRs.getString("manual_bill_no"));
                    itemDCSR.setGrossAmountFloat(dcsrLinesRs.getFloat("Gross_Amount"));
                    itemDCSR.setDiscAmountFloat(dcsrLinesRs.getFloat("Disc_Amount"));
                    itemDCSR.setNetAmountFloat(dcsrLinesRs.getFloat("Net_Amount"));
                    itemDCSR.setVatAmountFloat(dcsrLinesRs.getFloat("Vat_Amount"));
                    itemDCSR.setPackAmountFloat(dcsrLinesRs.getFloat("Pack_Amount"));
                    itemDCSR.setCashBillAmountFloat(Math.round((dcsrLinesRs.getFloat("Net_Amount") + dcsrLinesRs.getFloat("Vat_Amount") + dcsrLinesRs.getFloat("Pack_Amount"))));
                    itemDCSR.setEmpName(dcsrLinesRs.getString("emp_name"));
                    itemDCSR.setDcsrDate(dcsrHeaderRs.getString("counter_no_legacy") + "  " + dcsrHeaderRs.getString("counter"));
                    itemDCSR.setCount(1);
                    //itemDCSR.setCountD(Long.parseLong(SplitToDate[2]));
                    //Sub-Total
                    grossAmountS = grossAmountS + itemDCSR.getGrossAmountFloat();
                    discAmountS = discAmountS + itemDCSR.getDiscAmountFloat();
                    netAmountS = netAmountS + itemDCSR.getNetAmountFloat();
                    vatAmountS = vatAmountS + itemDCSR.getVatAmountFloat();
                    packAmountS = packAmountS + itemDCSR.getPackAmountFloat();
                    cashBillAmountFloat = cashBillAmountFloat + itemDCSR.getCashBillAmountFloat();

                    //Total
                    grossAmountT = grossAmountT + itemDCSR.getGrossAmountFloat();
                    discAmountT = discAmountT + itemDCSR.getDiscAmountFloat();
                    netAmountT = netAmountT + itemDCSR.getNetAmountFloat();
                    vatAmountT = vatAmountT + itemDCSR.getVatAmountFloat();
                    packAmountT = packAmountT + itemDCSR.getPackAmountFloat();
                    cashBillAmountT = cashBillAmountT + itemDCSR.getCashBillAmountFloat();

                    summaryViewLinesDCSR.add(itemDCSR);
                }
                mutiCounterName = mutiCounterName + "," + counterName;
                if (cashBillAmountFloat != 0) {
                    CashLineItemDCSR itemDCSRS = new CashLineItemDCSR();
                    itemDCSRS.setGrossAmountFloat(grossAmountS);
                    itemDCSRS.setDiscAmountFloat(discAmountS);
                    itemDCSRS.setNetAmountFloat(netAmountS);
                    itemDCSRS.setVatAmountFloat(vatAmountS);
                    itemDCSRS.setPackAmountFloat(packAmountS);
                    itemDCSRS.setCashBillAmountFloat(cashBillAmountFloat);
                    itemDCSRS.setEmpName(" ");
                    itemDCSRS.setCount(0);
                    itemDCSRS.setManualBillNo(" ");
                    itemDCSRS.setDcsrDate("Sub-Total" + "(" + counterName + ")");
                    summaryViewLinesDCSR.add(itemDCSRS);
                }
                //Employee and Total Amoutnt
                //String userSubtotalQuery = "SELECT sum(header.net_amt)+sum(header.pck_charge) as cashbill_amt,cashbill_header_master.user_id FROM  pos.header header INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk) INNER JOIN	pos.cashbill_lineitem_master cashbill_lineitem_master ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no) INNER JOIN pos.cashbill_header_master cashbill_header_master ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id) INNER JOIN pos.emp_master emp_master ON (header.emp_id = emp_master.emp_id) WHERE (cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "' AND branch_Counter.counter='" + counterName.trim() + "')group by cashbill_header_master.user_id   order by cashbill_header_master.user_id asc";
                String userSubtotalQuery = "SELECT SQL_CACHE sum(header.net_amt)+sum(header.pck_charge) as cashbill_amt,cashbill_header_master.user_id FROM  pos.header header INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk) INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no) INNER JOIN pos.cashbill_header_master cashbill_header_master ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id) INNER JOIN pos.emp_master emp_master ON (header.emp_id = emp_master.emp_id) WHERE (header.cancelFlag='N')\n";
                //+ " (cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')"
                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    userSubtotalQuery = userSubtotalQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                }
                userSubtotalQuery = userSubtotalQuery + " AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "' AND branch_Counter.counter='" + counterName.trim() + "')group by cashbill_header_master.user_id  order by cashbill_header_master.user_id asc";
                userWiseLinesRs = daoClass.Fun_Resultset(userSubtotalQuery);
                String empName = null;
                float Gross_Amount = 0f, Disc_Amount = 0f, Net_Amount = 0f, Vat_Amount = 0f, Pack_Amount = 0f, total = 0f;

                while (userWiseLinesRs.next()) {
                    //String userpackSubtotalQuery = "SELECT emp_name FROM pos.emp_master where emp_id='" + userWiseLinesRs.getString("user_id") + "'";
                    String userpackSubtotalQuery = "SELECT SQL_CACHE cashbill_lineitem_master.counterbill_no,SUM(lineitem.prc_value) as Gross_Amount,SUM(lineitem.discount_value) as Disc_Amount,SUM(lineitem.prc_value-lineitem.discount_value) as Net_Amount,SUM(lineitem.vat_value) as Vat_Amount,lineitem.pck_charge as Pack_Amount,header.net_amt+header.pck_charge as cashbill_amt,emp_master.emp_name FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE (header.cancelFlag='N') \n";
                    //+ "(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')"
                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        userpackSubtotalQuery = userpackSubtotalQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                    }
                    userpackSubtotalQuery = userpackSubtotalQuery + " AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "' AND branch_Counter.counter='" + counterName.trim() + "') AND emp_master.emp_id='" + userWiseLinesRs.getString("user_id") + "' group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc";
                    userWisepackLinesRs = daoClass.Fun_Resultset(userpackSubtotalQuery);
                    while (userWisepackLinesRs.next()) {
                        empName = userWisepackLinesRs.getString("emp_name");
                        Gross_Amount = Gross_Amount + userWisepackLinesRs.getFloat("Gross_Amount");
                        Disc_Amount = Disc_Amount + userWisepackLinesRs.getFloat("Disc_Amount");
                        Net_Amount = Net_Amount + userWisepackLinesRs.getFloat("Net_Amount");
                        Vat_Amount = Vat_Amount + userWisepackLinesRs.getFloat("Vat_Amount");
                        Pack_Amount = Pack_Amount + userWisepackLinesRs.getFloat("Pack_Amount");
                        total = total + (((userWisepackLinesRs.getFloat("Net_Amount") + userWisepackLinesRs.getFloat("Vat_Amount") + userWisepackLinesRs.getFloat("Pack_Amount"))));
                    }

                    CashLineItemDCSR itemDCSREmpTotal = new CashLineItemDCSR();
                    itemDCSREmpTotal.setGrossAmountFloat(Gross_Amount);
                    itemDCSREmpTotal.setDiscAmountFloat(Disc_Amount);
                    itemDCSREmpTotal.setNetAmountFloat(Net_Amount);
                    itemDCSREmpTotal.setVatAmountFloat(Vat_Amount);
                    itemDCSREmpTotal.setPackAmountFloat(Pack_Amount);
                    itemDCSREmpTotal.setCashBillAmountFloat(Math.round(total));
                    itemDCSREmpTotal.setDcsrDate(empName);
                    itemDCSREmpTotal.setCount(0);
                    itemDCSREmpTotal.setManualBillNo(" ");
                    summaryViewLinesDCSR.add(itemDCSREmpTotal);

                    //Total Based on Employee
                    empName = null;
                    Gross_Amount = 0f;
                    Disc_Amount = 0f;
                    Net_Amount = 0f;
                    Vat_Amount = 0f;
                    Pack_Amount = 0f;
                    total = 0f;

                }

                grossAmountS = 0f;
                discAmountS = 0f;
                netAmountS = 0f;
                vatAmountS = 0f;
                packAmountS = 0f;
                cashBillAmountFloat = 0f;
            }
            CashLineItemDCSR itemDCSRT = new CashLineItemDCSR();
            itemDCSRT.setGrossAmountFloat(grossAmountT);
            itemDCSRT.setDiscAmountFloat(discAmountT);
            itemDCSRT.setNetAmountFloat(netAmountT);
            itemDCSRT.setVatAmountFloat(vatAmountT);
            itemDCSRT.setPackAmountFloat(packAmountT);
            itemDCSRT.setCashBillAmountFloat(cashBillAmountT);
            itemDCSRT.setEmpName("");
            itemDCSRT.setManualBillNo(" ");
            itemDCSRT.setDcsrDate("Grand-Total" + "(" + mutiCounterName + ")");
            itemDCSRT.setCount(0);
            summaryViewLinesDCSR.add(itemDCSRT);
            String usertotalQuery = "SELECT SQL_CACHE cashbill_header_master.user_id FROM  pos.header header INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk) INNER JOIN	pos.cashbill_lineitem_master cashbill_lineitem_master ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no) INNER JOIN pos.cashbill_header_master cashbill_header_master ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id) INNER JOIN pos.emp_master emp_master ON (header.emp_id = emp_master.emp_id) WHERE (header.cancelFlag='N')\n";
            //+ " (cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')"
            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                usertotalQuery = usertotalQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
            }
            usertotalQuery = usertotalQuery + "AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')group by cashbill_header_master.user_id  order by cashbill_header_master.user_id asc";
            userWiseLinesRs = daoClass.Fun_Resultset(usertotalQuery);
            HashSet<CashBillUserwiseTotalLineItemDCSR> finalcSRs = new HashSet<>();
            String name = null;
            float Gross_Amount = 0f, Disc_Amount = 0f, Net_Amount = 0f, Vat_Amount = 0f, Pack_Amount = 0f, total = 0f;
            while (userWiseLinesRs.next()) {
                String userpackSubtotalQuery = "SELECT SQL_CACHE cashbill_lineitem_master.counterbill_no,SUM(lineitem.prc_value) as Gross_Amount,SUM(lineitem.discount_value) as Disc_Amount,SUM(lineitem.prc_value-lineitem.discount_value) as Net_Amount,SUM(lineitem.vat_value) as Vat_Amount,lineitem.pck_charge as Pack_Amount,header.net_amt+header.pck_charge as cashbill_amt,emp_master.emp_name FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE (header.cancelFlag='N') \n";
                //+ "(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')"
                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    userpackSubtotalQuery = userpackSubtotalQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                }
                userpackSubtotalQuery = userpackSubtotalQuery + "AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "' ) AND emp_master.emp_id='" + userWiseLinesRs.getString("user_id") + "' group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc";
                userWisepackLinesRs = daoClass.Fun_Resultset(userpackSubtotalQuery);
                while (userWisepackLinesRs.next()) {
                    name = userWisepackLinesRs.getString("emp_name");
                    Gross_Amount = Gross_Amount + userWisepackLinesRs.getFloat("Gross_Amount");
                    Disc_Amount = Disc_Amount + userWisepackLinesRs.getFloat("Disc_Amount");
                    Net_Amount = Net_Amount + userWisepackLinesRs.getFloat("Net_Amount");
                    Vat_Amount = Vat_Amount + userWisepackLinesRs.getFloat("Vat_Amount");
                    Pack_Amount = Pack_Amount + userWisepackLinesRs.getFloat("Pack_Amount");
                    total = total + (((userWisepackLinesRs.getFloat("Net_Amount") + userWisepackLinesRs.getFloat("Vat_Amount") + userWisepackLinesRs.getFloat("Pack_Amount"))));
                }
                CashLineItemDCSR itemDCSRTEmplTotal = new CashLineItemDCSR();
                itemDCSRTEmplTotal.setGrossAmountFloat(Gross_Amount);
                itemDCSRTEmplTotal.setDiscAmountFloat(Disc_Amount);
                itemDCSRTEmplTotal.setNetAmountFloat(Net_Amount);
                itemDCSRTEmplTotal.setVatAmountFloat(Vat_Amount);
                itemDCSRTEmplTotal.setPackAmountFloat(Pack_Amount);
                itemDCSRTEmplTotal.setCashBillAmountFloat(Math.round(total));
                itemDCSRTEmplTotal.setDcsrDate(name);
                itemDCSRTEmplTotal.setManualBillNo(" ");
                summaryViewLinesDCSR.add(itemDCSRTEmplTotal);
                name = null;
                Gross_Amount = 0f;
                Disc_Amount = 0f;
                Net_Amount = 0f;
                Vat_Amount = 0f;
                Pack_Amount = 0f;
                total = 0f;
            }
//            dCSRto.setCashLineItemDTO(summaryViewLinesDCSR);
        } catch (SQLException ex) {
            System.out.println("Exception in displaying DCSR: " + ex.getMessage());
        } finally {
            daoClass.closeResultSet(dcsrHeaderRs);
            daoClass.closeResultSet(dcsrLinesRs);
            daoClass.closeResultSet(userWiseLinesRs);
            daoClass.closeResultSet(userWisepackLinesRs);
        }
        return summaryViewLinesDCSR;
    }

    public static List<CashLineItemDCSR> displayCashDailySummaryAbstractReportDCSRA(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId) {
        DaoClass daoClass = new DaoClass();
        List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList();
        try {
            Float grossAmountT = 0f;
            Float discAmountT = 0f;
            Float netAmountT = 0f;
            Float vatAmountT = 0f;
            Float packAmountT = 0f;
            Float cashBillAmountT = 0f;
            String[] SplitFromDate = paraFromDate.split("-");
            String[] SplitToDate = paraToDate.split("-");
            //paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            CashHeaderDCSR dCSRto = new CashHeaderDCSR();
            for (int i = Integer.parseInt(SplitFromDate[2]); i <= Integer.parseInt(SplitToDate[2]); i++) {
                int packAmt = 0;
                String CDate = SplitToDate[0] + "-" + SplitToDate[1] + "-" + i;
                paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + CDate.trim() + "',1))");
                String getPackAmt = "SELECT SQL_CACHE DISTINCT(cashbill_lineitem_master.counterbill_no),\n"
                        + "header.pck_charge\n"
                        + "FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE (header.cancelFlag='N')\n";
                //+"(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')"
                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    getPackAmt = getPackAmt.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                }
                getPackAmt = getPackAmt + "AND(cashbill_lineitem_master.cashbill_dateTime between  '" + CDate.trim() + "' AND  '" + paraToDate.trim() + "')";
                dcsrHeaderRs = daoClass.Fun_Resultset(getPackAmt);
                while (dcsrHeaderRs.next()) {
                    packAmt = packAmt + dcsrHeaderRs.getInt("pck_charge");
                }
                daoClass.closeResultSet(dcsrHeaderRs);
                String getBillNoandAmtQuery = "SELECT SQL_CACHE \n"
                        + "SUM(lineitem.prc_value) as Gross_Amount,\n"
                        + "SUM(lineitem.discount_value) as Disc_Amount,\n"
                        + "SUM(lineitem.prc_value-lineitem.discount_value) as Net_Amount,\n"
                        + "SUM(lineitem.vat_value) as Vat_Amount,\n"
                        + "(lineitem.pck_charge) as Pack_Amount,\n"
                        + "ceil(SUM(lineitem.prc_value-lineitem.discount_value)+SUM(lineitem.vat_value) +(lineitem.pck_charge)) as Total_Amt FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE (header.cancelFlag='N')\n ";
                //+ "(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')"
                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    getBillNoandAmtQuery = getBillNoandAmtQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n ");
                }
                getBillNoandAmtQuery = getBillNoandAmtQuery + "AND(cashbill_lineitem_master.cashbill_dateTime between '" + CDate.trim() + "' AND '" + paraToDate.trim() + "')";
                dcsrLinesRs = daoClass.Fun_Resultset(getBillNoandAmtQuery);
                while (dcsrLinesRs.next()) {
                    float Amt = ((dcsrLinesRs.getFloat("Net_Amount") + dcsrLinesRs.getFloat("Vat_Amount") + dcsrLinesRs.getFloat("Pack_Amount")));
                    CashLineItemDCSR itemDCSR = new CashLineItemDCSR();
                    if (Amt != 0) {
                        itemDCSR.setGrossAmountFloat(dcsrLinesRs.getFloat("Gross_Amount"));
                        itemDCSR.setDiscAmountFloat(dcsrLinesRs.getFloat("Disc_Amount"));
                        itemDCSR.setNetAmountFloat(dcsrLinesRs.getFloat("Net_Amount"));
                        itemDCSR.setVatAmountFloat(dcsrLinesRs.getFloat("Vat_Amount"));
                        itemDCSR.setPackAmountFloat(packAmt);
                        itemDCSR.setCashBillAmountFloat(Math.round(dcsrLinesRs.getFloat("Net_Amount") + dcsrLinesRs.getFloat("Vat_Amount") + packAmt));
                        itemDCSR.setDcsrDate(i + "-" + SplitToDate[1] + "-" + SplitToDate[0]);
                        //Total
                        grossAmountT = grossAmountT + itemDCSR.getGrossAmountFloat();
                        discAmountT = discAmountT + itemDCSR.getDiscAmountFloat();
                        netAmountT = netAmountT + itemDCSR.getNetAmountFloat();
                        vatAmountT = vatAmountT + itemDCSR.getVatAmountFloat();
                        packAmountT = packAmountT + itemDCSR.getPackAmountFloat();
                        cashBillAmountT = cashBillAmountT + itemDCSR.getCashBillAmountFloat();
                        summaryViewLinesDCSR.add(itemDCSR);
                    }
                }
            }
            CashLineItemDCSR itemDCSRT = new CashLineItemDCSR();
            itemDCSRT.setGrossAmountFloat(grossAmountT);
            itemDCSRT.setDiscAmountFloat(discAmountT);
            itemDCSRT.setNetAmountFloat(netAmountT);
            itemDCSRT.setVatAmountFloat(vatAmountT);
            itemDCSRT.setPackAmountFloat(packAmountT);
            itemDCSRT.setCashBillAmountFloat(cashBillAmountT);
            itemDCSRT.setDcsrDate("Total");
            summaryViewLinesDCSR.add(itemDCSRT);
            dCSRto.setCashLineItemDTO(summaryViewLinesDCSR);
        } catch (NumberFormatException | SQLException ex) {
            System.out.println("Exception in displaying DCSRA: " + ex.getMessage());
        } finally {
            daoClass.closeResultSet(dcsrHeaderRs);
            daoClass.closeResultSet(dcsrLinesRs);
            daoClass.closeResultSet(userWiseLinesRs);
            daoClass.closeResultSet(userWisepackLinesRs);
        }
        return summaryViewLinesDCSR;
    }

    public static List<CashLineItemDCSR> displayCashDailySummaryAbstractEmployeeReportDCSRAE(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId) {
        DaoClass daoClass = new DaoClass();
        List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList();
        try {
            Float grossAmountS = 0f;
            Float discAmountS = 0f;
            Float netAmountS = 0f;
            Float vatAmountS = 0f;
            Float packAmountS = 0f;
            Float cashBillAmountFloat = 0f;

            Float grossAmountT = 0f;
            Float discAmountT = 0f;
            Float netAmountT = 0f;
            Float vatAmountT = 0f;
            Float packAmountT = 0f;
            Float cashBillAmountT = 0f;
            String paraToDateCdate = null;
            String[] SplitFromDate = paraFromDate.split("-");
            String[] SplitToDate = paraToDate.split("-");
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            String CounterQuery = "SELECT SQL_CACHE distinct cashbill_header_master.user_id FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)CROSS JOIN pos.header header INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk) WHERE (header.cancelFlag = 'N')\n";
            //+ "WHERE(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "') \n"
            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                CounterQuery = CounterQuery.concat(" AND(cashbill_header_master.paymentType ='" + paraPaymentType + "')\n ");
            }
            CounterQuery = CounterQuery + " AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";
            userWiseLinesRs = daoClass.Fun_Resultset(CounterQuery);
            CashHeaderDCSR dCSRto = new CashHeaderDCSR();
            while (userWiseLinesRs.next()) {
                int k = 1;
                for (int i = Integer.parseInt(SplitFromDate[2]); i <= Integer.parseInt(SplitToDate[2]); i++) {
                    int packAmt = 0;
                    String CDate = SplitToDate[0] + "-" + SplitToDate[1] + "-" + i;
                    paraToDateCdate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + CDate.trim() + "',1))");
                    String getPackAmt = "SELECT SQL_CACHE DISTINCT(cashbill_lineitem_master.counterbill_no),\n"
                            + "header.pck_charge \n"
                            + "FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE (header.cancelFlag='N') \n ";
                    //+ "(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')"
                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        getPackAmt = getPackAmt.concat(" AND(cashbill_header_master.paymentType ='" + paraPaymentType + "')\n ");
                    }
                    getPackAmt = getPackAmt + "AND(cashbill_lineitem_master.cashbill_dateTime between  '" + CDate.trim() + "' AND  '" + paraToDateCdate.trim() + "')and (emp_master.emp_id='" + userWiseLinesRs.getString("user_id").trim() + "')";
                    dcsrHeaderRs = daoClass.Fun_Resultset(getPackAmt);
                    while (dcsrHeaderRs.next()) {
                        packAmt = packAmt + dcsrHeaderRs.getInt("pck_charge");
                    }
                    daoClass.closeResultSet(dcsrHeaderRs);
                    String getBillNoandAmtQuery = "SELECT SQL_CACHE \n"
                            + "(SUM(lineitem.prc_value)) as Gross_Amount,\n"
                            + "(SUM(lineitem.discount_value)) as Disc_Amount,\n"
                            + "(SUM(lineitem.prc_value-lineitem.discount_value)) as Net_Amount,\n"
                            + "(SUM(lineitem.vat_value)) as Vat_Amount,\n"
                            + "((lineitem.pck_charge)) as Pack_Amount,\n"
                            + "emp_master.emp_name as Emp_id,\n"
                            + "ceil(SUM(lineitem.prc_value-lineitem.discount_value)+SUM(lineitem.vat_value) +SUM(lineitem.pck_charge)) as Total_Amt FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE (header.cancelFlag='N') \n";
                    //+ "(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')"
                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        getBillNoandAmtQuery = getBillNoandAmtQuery.concat(" AND(cashbill_header_master.paymentType ='" + paraPaymentType + "')\n ");
                    }
                    getBillNoandAmtQuery = getBillNoandAmtQuery + "AND(cashbill_lineitem_master.cashbill_dateTime between '" + CDate.trim() + "%' AND '" + paraToDateCdate.trim() + "' and emp_master.emp_id='" + userWiseLinesRs.getString("user_id") + "')";
                    dcsrLinesRs = daoClass.Fun_Resultset(getBillNoandAmtQuery);
                    if (i == Integer.parseInt(SplitToDate[2])) {
                        while (dcsrLinesRs.next()) {
                            float Amt = ((dcsrLinesRs.getFloat("Net_Amount") + dcsrLinesRs.getFloat("Vat_Amount") + dcsrLinesRs.getFloat("Pack_Amount")));
                            CashLineItemDCSR itemDCSR = new CashLineItemDCSR();
                            if (Amt != 0) {
                                itemDCSR.setGrossAmountFloat(dcsrLinesRs.getFloat("Gross_Amount"));
                                itemDCSR.setDiscAmountFloat(dcsrLinesRs.getFloat("Disc_Amount"));
                                itemDCSR.setNetAmountFloat(dcsrLinesRs.getFloat("Net_Amount"));
                                itemDCSR.setVatAmountFloat(dcsrLinesRs.getFloat("Vat_Amount"));
                                itemDCSR.setPackAmountFloat(packAmt);
                                itemDCSR.setCashBillAmountFloat(Math.round((dcsrLinesRs.getFloat("Net_Amount") + dcsrLinesRs.getFloat("Vat_Amount") + packAmt)));
                                itemDCSR.setEmpName(dcsrLinesRs.getString("Emp_id"));
                                itemDCSR.setDcsrDate(i + "-" + SplitToDate[1] + "-" + SplitToDate[0]);
                                itemDCSR.setCount(k++);
                                itemDCSR.setCountD(Long.parseLong(SplitToDate[2]));
                                //Sub-Total
                                grossAmountS = grossAmountS + itemDCSR.getGrossAmountFloat();
                                discAmountS = discAmountS + itemDCSR.getDiscAmountFloat();
                                netAmountS = netAmountS + itemDCSR.getNetAmountFloat();
                                vatAmountS = vatAmountS + itemDCSR.getVatAmountFloat();
                                packAmountS = packAmountS + itemDCSR.getPackAmountFloat();
                                cashBillAmountFloat = cashBillAmountFloat + itemDCSR.getCashBillAmountFloat();

                                //Total
                                grossAmountT = grossAmountT + itemDCSR.getGrossAmountFloat();
                                discAmountT = discAmountT + itemDCSR.getDiscAmountFloat();
                                netAmountT = netAmountT + itemDCSR.getNetAmountFloat();
                                vatAmountT = vatAmountT + itemDCSR.getVatAmountFloat();
                                packAmountT = packAmountT + itemDCSR.getPackAmountFloat();
                                cashBillAmountT = cashBillAmountT + itemDCSR.getCashBillAmountFloat();
                                summaryViewLinesDCSR.add(itemDCSR);
                            }

                            CashLineItemDCSR itemDCSRS = new CashLineItemDCSR();
                            itemDCSRS.setGrossAmountFloat(grossAmountS);
                            itemDCSRS.setDiscAmountFloat(discAmountS);
                            itemDCSRS.setNetAmountFloat(netAmountS);
                            itemDCSRS.setVatAmountFloat(vatAmountS);
                            itemDCSRS.setPackAmountFloat(packAmountS);
                            itemDCSRS.setCashBillAmountFloat(cashBillAmountFloat);
                            itemDCSRS.setEmpName("Sub Total");
                            itemDCSRS.setDcsrDate(" ");
                            summaryViewLinesDCSR.add(itemDCSRS);
                            grossAmountS = 0f;
                            discAmountS = 0f;
                            netAmountS = 0f;
                            vatAmountS = 0f;
                            packAmountS = 0f;
                            cashBillAmountFloat = 0f;
                        }
                    } else {
                        while (dcsrLinesRs.next()) {
                            float Amt = ((dcsrLinesRs.getFloat("Net_Amount") + dcsrLinesRs.getFloat("Vat_Amount") + packAmt));
                            CashLineItemDCSR itemDCSR = new CashLineItemDCSR();
                            if (Amt != 0) {
                                itemDCSR.setGrossAmountFloat(dcsrLinesRs.getFloat("Gross_Amount"));
                                itemDCSR.setDiscAmountFloat(dcsrLinesRs.getFloat("Disc_Amount"));
                                itemDCSR.setNetAmountFloat(dcsrLinesRs.getFloat("Net_Amount"));
                                itemDCSR.setVatAmountFloat(dcsrLinesRs.getFloat("Vat_Amount"));
                                itemDCSR.setPackAmountFloat(packAmt);
                                itemDCSR.setCashBillAmountFloat(Math.round((dcsrLinesRs.getFloat("Net_Amount") + dcsrLinesRs.getFloat("Vat_Amount") + packAmt)));
                                itemDCSR.setEmpName(dcsrLinesRs.getString("Emp_id"));
                                itemDCSR.setDcsrDate(i + "-" + SplitToDate[1] + "-" + SplitToDate[0]);
                                itemDCSR.setCount(k++);
                                itemDCSR.setCountD(Long.parseLong(SplitToDate[2]));
                                //Sub-Total
                                grossAmountS = grossAmountS + itemDCSR.getGrossAmountFloat();
                                discAmountS = discAmountS + itemDCSR.getDiscAmountFloat();
                                netAmountS = netAmountS + itemDCSR.getNetAmountFloat();
                                vatAmountS = vatAmountS + itemDCSR.getVatAmountFloat();
                                packAmountS = packAmountS + itemDCSR.getPackAmountFloat();
                                cashBillAmountFloat = cashBillAmountFloat + itemDCSR.getCashBillAmountFloat();

                                //Total
                                grossAmountT = grossAmountT + itemDCSR.getGrossAmountFloat();
                                discAmountT = discAmountT + itemDCSR.getDiscAmountFloat();
                                netAmountT = netAmountT + itemDCSR.getNetAmountFloat();
                                vatAmountT = vatAmountT + itemDCSR.getVatAmountFloat();
                                packAmountT = packAmountT + itemDCSR.getPackAmountFloat();
                                cashBillAmountT = cashBillAmountT + itemDCSR.getCashBillAmountFloat();
                                summaryViewLinesDCSR.add(itemDCSR);
                            }
                        }
                    }
                }
            }
            CashLineItemDCSR itemDCSRT = new CashLineItemDCSR();
            itemDCSRT.setGrossAmountFloat(grossAmountT);
            itemDCSRT.setDiscAmountFloat(discAmountT);
            itemDCSRT.setNetAmountFloat(netAmountT);
            itemDCSRT.setVatAmountFloat(vatAmountT);
            itemDCSRT.setPackAmountFloat(packAmountT);
            itemDCSRT.setCashBillAmountFloat(cashBillAmountT);
            itemDCSRT.setEmpName("Total");
            itemDCSRT.setDcsrDate(" ");
            summaryViewLinesDCSR.add(itemDCSRT);
            dCSRto.setCashLineItemDTO(summaryViewLinesDCSR);
        } catch (Exception ex) {
            System.out.println("Exception in displaying DCSR: " + ex.getMessage());
        } finally {
            daoClass.closeResultSet(dcsrHeaderRs);
            daoClass.closeResultSet(dcsrLinesRs);
            daoClass.closeResultSet(userWiseLinesRs);
            daoClass.closeResultSet(userWisepackLinesRs);
        }
        return summaryViewLinesDCSR;
    }

    public static List<CashHeaderDCSR> userTotalDCSR(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId) {
        DaoClass daoClass = new DaoClass();
        List<CashHeaderDCSR> CashHeaderDCSR = new ArrayList<CashHeaderDCSR>();
        try {
            CashHeaderDCSR sR = new CashHeaderDCSR();
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            //String usertotalQuery = "SELECT sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as cashbill_amt,emp_master.emp_name FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no =     lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id)) INNER JOIN    pos.emp_master emp_master     ON (cashbill_header_master.user_id = emp_master.emp_id)  WHERE (cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')group by emp_master.emp_name  order by cashbill_header_master.user_id asc";
            String usertotalQuery = "SELECT SQL_CACHE sum(header.net_amt)+sum(header.pck_charge) as cashbill_amt,cashbill_header_master.user_id FROM  pos.header header INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk) INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no) INNER JOIN pos.cashbill_header_master cashbill_header_master ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id) INNER JOIN pos.emp_master emp_master ON (header.emp_id = emp_master.emp_id) WHERE (cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')group by cashbill_header_master.user_id   order by cashbill_header_master.user_id asc";
            userWiseLinesRs = daoClass.Fun_Resultset(usertotalQuery);
            HashSet<CashBillUserwiseTotalLineItemDCSR> finalcSRs = new HashSet<>();
            String name = null;
            while (userWiseLinesRs.next()) {
                String userpackSubtotalQuery = "SELECT SQL_CACHE emp_name FROM pos.emp_master where emp_id='" + userWiseLinesRs.getString("user_id") + "'";
                userWisepackLinesRs = daoClass.Fun_Resultset(userpackSubtotalQuery);
                while (userWisepackLinesRs.next()) {
                    name = userWisepackLinesRs.getString("emp_name");
                }
                CashBillUserwiseTotalLineItemDCSR values = new CashBillUserwiseTotalLineItemDCSR();
                values.setFinalName(name);
                //values.setFinalName(userWiseLinesRs.getString("emp_name"));
                values.setFinalAmount(Math.round(Float.valueOf(userWiseLinesRs.getFloat("cashbill_amt"))));
                //values.setFinalAmount(userWiseLinesRs.getBigDecimal("cashbill_amt"));
                finalcSRs.add(values);
                sR.setCashBillUserwiseTotalLineItemDCSR(finalcSRs);
                name = null;
            }
            CashHeaderDCSR.add(sR);
        } catch (Exception ex) {
            //Logger.getLogger(CashDailySummaryView_0.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("TotalDCSR Exception : " + ex.getMessage());
        } finally {
            daoClass.closeResultSet(userWiseLinesRs);
            daoClass.closeResultSet(userWisepackLinesRs);
        }
        return CashHeaderDCSR;
    }

    public static List<SalesIntimationList> SalesIntimationDetailReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String typeData) {
        String subTotalCraftGroup = "";
        String grandTotalCraftGroup = "";
        DaoClass daoClass = new DaoClass();
        List<CounterTypeList> counterTypeList = new ArrayList<CounterTypeList>();
        List<SalesIntimationList> salesIntimationList = new ArrayList<SalesIntimationList>();
        float qty = 0f;
        float rate = 0f;
        float grossAmt = 0f;
        float discAmt = 0f;
        float netAmt = 0f;
        //float packAmt = 0f;
        Float detailedQty = 0.0f;
        Float detailedRate = 0.0f;
        Float detailedSubGrossAmt = 0.0f;
        Float detailedSubDiscAmt = 0.0f;
        Float detailedSubNetAmt = 0.0f;
        String detailedVendName = "";
        String detailedVendId = "";
        String detailedCraftGroup = "";

        try {
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            String getCounterNoandNameQuery = "SELECT SQL_CACHE distinct lineitem.materialCraftGroup as materialCraftGroup\n"
                    + "  FROM ((pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                    + "INNER JOIN\n"
                    + "pos.cashbill_header_master cashbill_header_master\n"
                    + "ON (cashbill_lineitem_master.cashBill_id =\n"
                    + "cashbill_header_master.cashBill_id))\n"
                    + "INNER JOIN\n"
                    + "pos.lineitem lineitem\n"
                    + "ON (lineitem.sales_orderno =\n"
                    + "cashbill_lineitem_master.counterbill_no))\n"
                    + "INNER JOIN\n"
                    + "pos.header header\n"
                    + "ON (header.sales_orderno = lineitem.sales_orderno)\n"
                    //+ "WHERE (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n"
                    + "WHERE (header.cancelFlag = 'N')\n"
                    + "AND (cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";

            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                getCounterNoandNameQuery = getCounterNoandNameQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
            }
            counterTypeRs = daoClass.Fun_Resultset(getCounterNoandNameQuery);
            while (counterTypeRs.next()) {
                if (!(counterTypeRs.getString("materialCraftGroup").equalsIgnoreCase(DaoClass.getRestricedCraftGroup()))) {
                    counterTypeList.add(new CounterTypeList(counterTypeRs.getString("materialCraftGroup")));
                    grandTotalCraftGroup = grandTotalCraftGroup.concat(counterTypeRs.getString("materialCraftGroup"));
                    grandTotalCraftGroup = grandTotalCraftGroup.concat(",");
                }
            }

            String detailedCrftGrp = "";
            for (int i = 0; i < counterTypeList.size(); i++) {
                String getSalesIntimationQuery = "SELECT SQL_CACHE cashbill_header_master.paymentType as PaymentType,\n"
                        //+ "vendor_master.vendor_id as VENDOR_ID, \n"
                        + "vendor_master.vendor_name as VENDOR_NAME, \n"
                        /// + "header.pck_charge as PCKAMT,\n"
                        + "header.manual_bill_no as manualBillNo,\n"
                        + "header.sales_orderno as Bill_No,\n"
                        + "lineitem.vendor as VENDOR_ID,     \n"
                        + "lineitem.material as MATERIAL,\n"
                        + "lineitem.descrip as MATERIAL_DEC,\n"
                        + "lineitem.materialCraftGroup as CRAFT_GROUP,\n"
                        + "counterList.description as CRAFT_NAME, \n"
                        + "lineitem.qty as QUANTITY,\n"
                        + "lineitem.price as RATE,\n"
                        + "lineitem.prc_value as GROSS,\n"
                        + "lineitem.discount_value as DISCOUNT,\n"
                        + "lineitem.discount_percentage as DISCOUNT_PER,\n"
                        + "(lineitem.prc_value-lineitem.discount_value) as NET_AMT,\n"
                        + "DATE_FORMAT(header.date_time,'%d-%m-%Y')as DATE_TIME \n"
                        + "FROM ((( pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                        + "INNER JOIN\n"
                        + "pos.cashbill_header_master cashbill_header_master\n"
                        + "ON (cashbill_lineitem_master.cashBill_id =\n"
                        + "cashbill_header_master.cashBill_id))\n"
                        + "INNER JOIN\n"
                        + "pos.lineitem lineitem\n"
                        + "ON (lineitem.sales_orderno =\n"
                        + "cashbill_lineitem_master.counterbill_no))\n"
                        + "INNER JOIN\n"
                        + " pos.craft_counter_list counterList\n"
                        + " ON (counterList.craft_group = lineitem.materialCraftGroup)\n"
                        + "INNER JOIN\n"
                        + "pos.header header\n"
                        + "ON (header.sales_orderno = lineitem.sales_orderno))\n"
                        + "INNER JOIN\n"
                        + "pos.vendor_master vendor_master\n"
                        + "ON (lineitem.vendor = vendor_master.vendor_id) WHERE \n"
                        //+ "WHERE (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n"
                        + "(header.date_time between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')\n"
                        + "AND (lineitem.materialCraftGroup='" + counterTypeList.get(i).getCounterName() + "')\n"
                        + "AND (header.cancelFlag ='N')";

                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    getSalesIntimationQuery = getSalesIntimationQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                }

                if (!typeData.equalsIgnoreCase("all")) {
                    getSalesIntimationQuery = getSalesIntimationQuery.concat("AND (lineitem.vendor like'" + typeData.trim() + "%')\n");
                }

                getSalesIntimationQuery = getSalesIntimationQuery.concat(" order by VENDOR_ID");
                SalesIntimationRs = daoClass.Fun_Resultset(getSalesIntimationQuery);
                //**Including Packing Charges**
                // String billNo = "";
                // float packVal = 0f;
                while (SalesIntimationRs.next()) {
                    if (!(SalesIntimationRs.getString("VENDOR_ID").equals(DaoClass.getRestricedVendor()))) {
                        qty = qty + SalesIntimationRs.getFloat("QUANTITY");
                        rate = rate + SalesIntimationRs.getFloat("RATE");
                        grossAmt = grossAmt + SalesIntimationRs.getFloat("GROSS");
                        discAmt = discAmt + SalesIntimationRs.getFloat("DISCOUNT");
                        netAmt = netAmt + SalesIntimationRs.getFloat("NET_AMT");
                        if (!(SalesIntimationRs.getString("VENDOR_ID").equalsIgnoreCase(detailedCrftGrp))) {
                            if (detailedSubGrossAmt != 0 && detailedSubNetAmt != 0) {
                                salesIntimationList.add(new SalesIntimationList(detailedQty, detailedRate, detailedSubGrossAmt, detailedSubDiscAmt, detailedSubNetAmt, " ", " ", " ", detailedVendId, detailedVendName, "SUB-TOTAL (" + subTotalCraftGroup + ")", " ", detailedCraftGroup));
                            }
                            detailedSubGrossAmt = 0.0f;
                            detailedSubDiscAmt = 0.0f;
                            detailedSubNetAmt = 0.0f;
                            detailedQty = 0.0f;
                            detailedRate = 0.0f;
                            detailedVendId = "";
                            detailedVendName = "";
                            detailedCraftGroup = "";

                            detailedQty = detailedQty + SalesIntimationRs.getFloat("QUANTITY");
                            detailedRate = detailedRate + SalesIntimationRs.getFloat("RATE");
                            detailedSubGrossAmt = detailedSubGrossAmt + SalesIntimationRs.getFloat("GROSS");
                            detailedSubDiscAmt = detailedSubDiscAmt + SalesIntimationRs.getFloat("DISCOUNT");
                            detailedSubNetAmt = detailedSubNetAmt + SalesIntimationRs.getFloat("NET_AMT");
                            detailedVendId = SalesIntimationRs.getString("VENDOR_ID");
                            detailedVendName = SalesIntimationRs.getString("VENDOR_NAME");
                            subTotalCraftGroup = SalesIntimationRs.getString("CRAFT_NAME");
                            detailedCraftGroup = SalesIntimationRs.getString("CRAFT_GROUP") + " - " + SalesIntimationRs.getString("CRAFT_NAME");
                        } else {
                            detailedQty = detailedQty + SalesIntimationRs.getFloat("QUANTITY");
                            detailedRate = detailedRate + SalesIntimationRs.getFloat("RATE");
                            detailedSubGrossAmt = detailedSubGrossAmt + SalesIntimationRs.getFloat("GROSS");
                            detailedSubDiscAmt = detailedSubDiscAmt + SalesIntimationRs.getFloat("DISCOUNT");
                            detailedSubNetAmt = detailedSubNetAmt + SalesIntimationRs.getFloat("NET_AMT");
                            detailedVendId = SalesIntimationRs.getString("VENDOR_ID");
                            detailedVendName = SalesIntimationRs.getString("VENDOR_NAME");
                            subTotalCraftGroup = SalesIntimationRs.getString("CRAFT_NAME");
                            detailedCraftGroup = SalesIntimationRs.getString("CRAFT_GROUP") + " - " + SalesIntimationRs.getString("CRAFT_NAME");
                        }

                        //**Including Packing Charges**
                        /*
                         if (!billNo.equals(SalesIntimationRs.getString("Bill_No"))) {
                         packAmt = packAmt + SalesIntimationRs.getFloat("PCKAMT");
                         System.out.println("Pck 1 : " + packAmt);
                         salesIntimationList.add(new SalesIntimationList(SalesIntimationRs.getString("VENDOR_ID"), SalesIntimationRs.getString("VENDOR_NAME"), SalesIntimationRs.getString("MATERIAL_DEC"), SalesIntimationRs.getFloat("PCKAMT"), SalesIntimationRs.getFloat("QUANTITY"), SalesIntimationRs.getFloat("RATE"), SalesIntimationRs.getFloat("GROSS"), SalesIntimationRs.getFloat("DISCOUNT"), SalesIntimationRs.getFloat("NET_AMT") + SalesIntimationRs.getFloat("PCKAMT"), SalesIntimationRs.getString("DATE_TIME"), SalesIntimationRs.getString("Bill_No"), SalesIntimationRs.getString("manualBillNo")));
                         } else {
                         packAmt = packAmt + packVal;
                         System.out.println("Pck 2 : " + packAmt);
                         salesIntimationList.add(new SalesIntimationList(SalesIntimationRs.getString("VENDOR_ID"), SalesIntimationRs.getString("VENDOR_NAME"), SalesIntimationRs.getString("MATERIAL_DEC"), packVal, SalesIntimationRs.getFloat("QUANTITY"), SalesIntimationRs.getFloat("RATE"), SalesIntimationRs.getFloat("GROSS"), SalesIntimationRs.getFloat("DISCOUNT"), SalesIntimationRs.getFloat("NET_AMT") + packVal, SalesIntimationRs.getString("DATE_TIME"), SalesIntimationRs.getString("Bill_No"), SalesIntimationRs.getString("manualBillNo")));
                         }
                         billNo = SalesIntimationRs.getString("Bill_No");
                         */
                        //**Without Packing Charges**
                        salesIntimationList.add(new SalesIntimationList(SalesIntimationRs.getFloat("QUANTITY"), SalesIntimationRs.getFloat("RATE"), SalesIntimationRs.getFloat("GROSS"), SalesIntimationRs.getFloat("DISCOUNT"), SalesIntimationRs.getFloat("NET_AMT"), SalesIntimationRs.getString("DATE_TIME") + " / " + SalesIntimationRs.getString("PaymentType"), SalesIntimationRs.getString("Bill_No"), SalesIntimationRs.getString("manualBillNo"), SalesIntimationRs.getString("VENDOR_ID"), SalesIntimationRs.getString("VENDOR_NAME"), SalesIntimationRs.getString("MATERIAL") + "/" + SalesIntimationRs.getString("MATERIAL_DEC"), SalesIntimationRs.getString("DISCOUNT_PER") + " %", SalesIntimationRs.getString("CRAFT_GROUP") + " - " + SalesIntimationRs.getString("CRAFT_NAME")));
                    }
                    detailedCrftGrp = SalesIntimationRs.getString("VENDOR_ID").trim();
                }
            }
            salesIntimationList.add(new SalesIntimationList(detailedQty, detailedRate, detailedSubGrossAmt, detailedSubDiscAmt, detailedSubNetAmt, " ", " ", " ", " ", " ", "SUB-TOTAL (" + subTotalCraftGroup + ")", " ", " "));
            salesIntimationList.add(new SalesIntimationList(qty, rate, grossAmt, discAmt, netAmt, " ", " ", " ", " ", " ", "GRAND-TOTAL ( " + grandTotalCraftGroup + " )", " ", " "));
        } catch (Exception e) {
            System.out.println("Excepttion in SID :  " + e.getMessage());
        } finally {
            daoClass.closeResultSet(counterTypeRs);
            daoClass.closeResultSet(SalesIntimationRs);
        }
        return salesIntimationList;
    }

    public static List<SalesIntimationList> SaleIntimationSummary(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String typeData) {
        String vendorNumber = null;
        String vendorName = null;
        String description = null;
        String craftGroup = null;
        String materialId = null;
        String MatId = null;
        Float qty = 0.0f;
        Float rate = 0.0f;
        Float netAmount = 0.0f;
        Float grossAmount = 0.0f;
        Float discountAmount = 0.0f;
        String tempMaterialId = "";
        DaoClass daoClass = new DaoClass();
        List<String> countersLst = new ArrayList<String>();
        List<String> intimationVendorsList = new ArrayList<String>();
        List<SalesIntimationList> saleIntimationLst = new ArrayList<SalesIntimationList>();
        try {
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            String getCounterNoandNameQuery = "SELECT SQL_CACHE distinct lineitem.materialCraftGroup as materialCraftGroup\n"
                    + "  FROM ((pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                    + "INNER JOIN\n"
                    + "pos.cashbill_header_master cashbill_header_master\n"
                    + "ON (cashbill_lineitem_master.cashBill_id =\n"
                    + "cashbill_header_master.cashBill_id))\n"
                    + "INNER JOIN\n"
                    + "pos.lineitem lineitem\n"
                    + "ON (lineitem.sales_orderno =\n"
                    + "cashbill_lineitem_master.counterbill_no))\n"
                    + "INNER JOIN\n"
                    + "pos.header header\n"
                    + "ON (header.sales_orderno = lineitem.sales_orderno)\n"
                    + "WHERE (cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')\n"
                    + "AND (header.cancelFlag = 'N')\n"
                    + "AND (cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";
            counterTypeRs = daoClass.Fun_Resultset(getCounterNoandNameQuery);
            /*ADDING MATERIAL CRAFT GROUP */
            while (counterTypeRs.next()) {
                if (!((counterTypeRs.getString("materialCraftGroup")).equalsIgnoreCase("SB"))) {
                    countersLst.add(counterTypeRs.getString("materialCraftGroup"));
                }
            }
            /*ADDING MATERIAL CRAFT GROUP */

            String getVendorsQry = "SELECT distinct lineitem.vendor FROM((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.cashbill_header_master cashbill_header_master ON (cashbill_lineitem_master.cashBill_id =cashbill_header_master.cashBill_id))INNER JOIN pos.lineitem lineitem ON (lineitem.sales_orderno = cashbill_lineitem_master.counterbill_no))INNER JOIN pos.header header ON (header.sales_orderno = lineitem.sales_orderno)WHERE(cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')AND (header.cancelFlag = 'N')AND (lineitem.date_time BETWEEN '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";
            //AND (lineitem.materialCraftGroup='SW')
            if (!typeData.equalsIgnoreCase("all")) {
                getVendorsQry = getVendorsQry.concat("AND (lineitem.vendor like'" + typeData.trim() + "%')\n");
            }

            /*ADDING VENDOR ID'S */
            vendorsListRs = daoClass.Fun_Resultset(getVendorsQry);
            while (vendorsListRs.next()) {
                intimationVendorsList.add(vendorsListRs.getString("vendor"));
            }
            /*ADDING VENDOR ID'S */

            //if (((countersLst.size() > 0) && (intimationVendorsList.size()) > 0)) {
            if ((intimationVendorsList.size()) > 0) {
                int countVar = 0;
                for (String vendors : intimationVendorsList) {
                    //for (String counters : countersLst) {
                    //System.out.println("COUNTERS : " + counters);
                    String getSalesIntimationQuery = "SELECT SQL_CACHE "
                            //+ "cashbill_header_master.paymentType as PaymentType,\n"
                            //+ "vendor_master.vendor_id as VENDOR_ID, \n"
                            + "vendor_master.vendor_name as VENDOR_NAME, \n"
                            //+ "header.pck_charge as PCKAMT,\n"
                            + "header.manual_bill_no as manualBillNo,\n"
                            //+ "header.sales_orderno as Bill_No,\n"
                            + "lineitem.vendor as VENDOR_NO,     \n"
                            + "lineitem.material as MATERIAL,\n"
                            + "lineitem.descrip as MATERIAL_DEC,\n"
                            + "lineitem.materialCraftGroup as CRAFT_GROUP,\n"
                            + "lineitem.qty as QUANTITY,\n"
                            + "lineitem.price as RATE,\n"
                            + "lineitem.prc_value as GROSS,\n"
                            + "lineitem.discount_value as DISCOUNT,\n"
                            + "(lineitem.prc_value-lineitem.discount_value) as NET_AMT \n"
                            //+ "DATE_FORMAT(header.date_time,'%d-%m-%Y %h:%m:%s')as DATE_TIME \n"
                            + "FROM ((( pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                            + "INNER JOIN\n"
                            + "pos.cashbill_header_master cashbill_header_master\n"
                            + "ON (cashbill_lineitem_master.cashBill_id =\n"
                            + "cashbill_header_master.cashBill_id))\n"
                            + "INNER JOIN\n"
                            + "pos.lineitem lineitem\n"
                            + "ON (lineitem.sales_orderno =\n"
                            + "cashbill_lineitem_master.counterbill_no))\n"
                            + "INNER JOIN\n"
                            + "pos.header header\n"
                            + "ON (header.sales_orderno = lineitem.sales_orderno))\n"
                            + "INNER JOIN\n"
                            + "pos.vendor_master vendor_master\n"
                            + "ON (lineitem.vendor = vendor_master.vendor_id)\n"
                            + "WHERE (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n"
                            + "AND (header.date_time between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')\n"
                            //+ "AND (lineitem.materialCraftGroup='" + counters.trim() + "')\n"
                            + "AND (header.cancelFlag ='N')";

                    if (!typeData.equalsIgnoreCase("all")) {
                        getSalesIntimationQuery = getSalesIntimationQuery.concat("AND (lineitem.vendor='" + vendors.trim() + "')\n");
                    }
                    //System.out.println("getSalesIntimationQuery :" + countVar + " :" + getSalesIntimationQuery);
                    SalesIntimationRs = daoClass.Fun_Resultset(getSalesIntimationQuery);
                    while (SalesIntimationRs.next()) {
                        //System.out.println("VENDOR_NO :  : " + SalesIntimationRs.getString("VENDOR_NO"));
                        if (!(SalesIntimationRs.getString("VENDOR_NO").equals(DaoClass.getRestricedVendor()))) {
                            //if (SalesIntimationRs.getString("MATERIAL").equalsIgnoreCase(tempMaterialId)) {
                            if (tempMaterialId.equalsIgnoreCase(SalesIntimationRs.getString("MATERIAL"))) {
                                vendorNumber = SalesIntimationRs.getString("VENDOR_NO");
                                vendorName = SalesIntimationRs.getString("VENDOR_NAME");
                                description = SalesIntimationRs.getString("MATERIAL_DEC");
                                craftGroup = SalesIntimationRs.getString("CRAFT_GROUP");
                                materialId = SalesIntimationRs.getString("MATERIAL");
                                qty = qty + SalesIntimationRs.getFloat("QUANTITY");
                                rate = rate + SalesIntimationRs.getFloat("RATE");
                                grossAmount = grossAmount + SalesIntimationRs.getFloat("GROSS");
                                discountAmount = discountAmount + SalesIntimationRs.getFloat("DISCOUNT");
                                netAmount = netAmount + SalesIntimationRs.getFloat("NET_AMT");
                            } else {
                                vendorNumber = SalesIntimationRs.getString("VENDOR_NO");
                                vendorName = SalesIntimationRs.getString("VENDOR_NAME");
                                description = SalesIntimationRs.getString("MATERIAL_DEC");
                                craftGroup = SalesIntimationRs.getString("CRAFT_GROUP");
                                materialId = SalesIntimationRs.getString("MATERIAL");
                                tempMaterialId = SalesIntimationRs.getString("MATERIAL");
                                qty = SalesIntimationRs.getFloat("QUANTITY");
                                rate = SalesIntimationRs.getFloat("RATE");
                                grossAmount = SalesIntimationRs.getFloat("GROSS");
                                discountAmount = SalesIntimationRs.getFloat("DISCOUNT");
                                netAmount = SalesIntimationRs.getFloat("NET_AMT");
                            }
                            System.out.println("QUANTITY  :AF: " + qty);
                        }
                        saleIntimationLst.add(new SalesIntimationList(vendorNumber, vendorName, description, qty, rate, grossAmount, discountAmount, netAmount, " ", craftGroup, materialId));
                    }
                    countVar++;
                    //}

                    for (int i = 0; i < saleIntimationLst.size(); i++) {
//                        if(MatId.equals(saleIntimationLst.get(i).getMaterialId())){
//                            MatId=saleIntimationLst.get(i).getMaterialId();
//                            saleIntimationLst.remove(i-1);
//                        }else{
//                            MatId=saleIntimationLst.get(i).getMaterialId();
//                        }

                    }
                }
            }

        } catch (Exception ex) {
            System.out.println("Exception In SIS : " + ex);
        } finally {
            daoClass.closeResultSet(counterTypeRs);
            daoClass.closeResultSet(vendorsListRs);
            daoClass.closeResultSet(SalesIntimationRs);
        }
        return saleIntimationLst;
    }

    /*Dheeraj*/
    public static List<SalesIntimationList> SalesIntimationSummaryReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String typeData) {
        DaoClass daoClass = new DaoClass();
        List<CounterTypeList> counterTypeList = new ArrayList<>();
        //List<VendorTypeList> counterVendorTypeList = new ArrayList<>();
        HashSet<VendorTypeList> counterVendorTypeList = new HashSet<>();
        List<SalesIntimationList> salesIntimationSummaryList = new ArrayList<SalesIntimationList>();
        //Sub-total
        float summarySubQty = 0f;
        float summarySubRate = 0f;
        float summarySubGross = 0f;
        float summarySubDis = 0f;
        float summarySubNet = 0f;
        //Grand total
        float summaryTotalQty = 0f;
        float summaryTotalRate = 0f;
        float summaryTotalGross = 0f;
        float summaryTotalDis = 0f;
        float summaryTotalNet = 0f;
        String summaryTotal = "";
        String summarySubTotal = "";
        //Craft Sub total
        float summaryCraftTotalQty = 0f;
        float summaryCraftTotalRate = 0f;
        float summaryCraftTotalGross = 0f;
        float summaryCraftTotalDis = 0f;
        float summaryCraftTotalNet = 0f;
        String summaryCraftSubTotal = "";

        try {
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            String getCounterNoandNameQuery = "SELECT SQL_CACHE distinct lineitem.materialCraftGroup as materialCraftGroup\n"
                    + "  FROM ((pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                    + "INNER JOIN\n"
                    + "pos.cashbill_header_master cashbill_header_master\n"
                    + "ON (cashbill_lineitem_master.cashBill_id =\n"
                    + "cashbill_header_master.cashBill_id))\n"
                    + "INNER JOIN\n"
                    + "pos.lineitem lineitem\n"
                    + "ON (lineitem.sales_orderno =\n"
                    + "cashbill_lineitem_master.counterbill_no))\n"
                    + "INNER JOIN\n"
                    + "pos.header header\n"
                    + "ON (header.sales_orderno = lineitem.sales_orderno)\n"
                    //+ "WHERE (cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')\n"
                    + "WHERE (header.cancelFlag = 'N')\n"
                    + "AND (cashbill_lineitem_master.cashbill_dateTime  between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";

            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                getCounterNoandNameQuery = getCounterNoandNameQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
            }
            counterTypeRs = daoClass.Fun_Resultset(getCounterNoandNameQuery);
            while (counterTypeRs.next()) {
                if (!(counterTypeRs.getString("materialCraftGroup").equalsIgnoreCase(DaoClass.getRestricedCraftGroup()))) {
                    counterTypeList.add(new CounterTypeList(counterTypeRs.getString("materialCraftGroup")));
                }
            }
            for (int i = 0; i < counterTypeList.size(); i++) {
                String getSalesIntimationQuery = "SELECT SQL_CACHE \n"
                        // + "vendor_master.vendor_name as VENDOR_NAME, \n"
                        + "lineitem.vendor as VENDOR_NO    \n"
                        + "FROM ((( pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                        + "INNER JOIN\n"
                        + "pos.cashbill_header_master cashbill_header_master\n"
                        + "ON (cashbill_lineitem_master.cashBill_id =\n"
                        + "cashbill_header_master.cashBill_id))\n"
                        + "INNER JOIN\n"
                        + "pos.lineitem lineitem\n"
                        + "ON (lineitem.sales_orderno =\n"
                        + "cashbill_lineitem_master.counterbill_no))\n"
                        + "INNER JOIN\n"
                        + " pos.craft_counter_list counterList\n"
                        + " ON (counterList.craft_group = lineitem.materialCraftGroup)\n"
                        + "INNER JOIN\n"
                        + "pos.header header\n"
                        + "ON (header.sales_orderno = lineitem.sales_orderno))\n"
                        + "INNER JOIN\n"
                        + "pos.vendor_master vendor_master\n"
                        + "ON (lineitem.vendor = vendor_master.vendor_id)\n"
                        + "WHERE (header.date_time between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')\n"
                        + "AND (lineitem.materialCraftGroup='" + counterTypeList.get(i).getCounterName() + "')\n";
                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    getSalesIntimationQuery = getSalesIntimationQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                }
                if (!typeData.equalsIgnoreCase("all")) {
                    getSalesIntimationQuery = getSalesIntimationQuery.concat("  AND (lineitem.vendor like'" + typeData.trim() + "%')\n");
                }
                getSalesIntimationQuery = getSalesIntimationQuery + " AND (header.cancelFlag ='N') group by lineitem.vendor";
                SalesIntimationRs = daoClass.Fun_Resultset(getSalesIntimationQuery);
                summaryCraftTotalQty = 0f;
                summaryCraftTotalRate = 0f;
                summaryCraftTotalGross = 0f;
                summaryCraftTotalDis = 0f;
                summaryCraftTotalNet = 0f;
                while (SalesIntimationRs.next()) {
                    String getSalesIntimationSummary = "SELECT SQL_CACHE cashbill_header_master.paymentType as PaymentType,\n"
                            + "vendor_master.vendor_name as VENDOR_NAME, \n"
                            + "header.manual_bill_no as manualBillNo,\n"
                            + "header.sales_orderno as Bill_No,\n"
                            + "lineitem.vendor as VENDOR_ID,     \n"
                            + "lineitem.material as MATERIAL,\n"
                            + "lineitem.descrip as MATERIAL_DEC,\n"
                            + "lineitem.materialCraftGroup as CRAFT_GROUP,\n"
                            + "counterList.description as CRAFT_NAME, \n"
                            + "sum(lineitem.qty) as QUANTITY,\n"
                            + "lineitem.price as RATE,\n"
                            + "sum(lineitem.prc_value) as GROSS,\n"
                            + "sum(lineitem.discount_value) as DISCOUNT,\n"
                            + "(sum(lineitem.prc_value)-sum(lineitem.discount_value))as NET_AMT,\n"
                            + "DATE_FORMAT(header.date_time,'%d-%m-%Y')as DATE_TIME \n"
                            + "FROM ((( pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                            + "INNER JOIN\n"
                            + "pos.cashbill_header_master cashbill_header_master\n"
                            + "ON (cashbill_lineitem_master.cashBill_id =\n"
                            + "cashbill_header_master.cashBill_id))\n"
                            + "INNER JOIN\n"
                            + "pos.lineitem lineitem\n"
                            + "ON (lineitem.sales_orderno =\n"
                            + "cashbill_lineitem_master.counterbill_no))\n"
                            + "INNER JOIN\n"
                            + " pos.craft_counter_list counterList\n"
                            + " ON (counterList.craft_group = lineitem.materialCraftGroup)\n"
                            + "INNER JOIN\n"
                            + "pos.header header\n"
                            + "ON (header.sales_orderno = lineitem.sales_orderno))\n"
                            + "INNER JOIN\n"
                            + "pos.vendor_master vendor_master\n"
                            + "ON (lineitem.vendor = vendor_master.vendor_id)  \n"
                            + "WHERE (header.date_time between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')\n"
                            + "and (lineitem.materialCraftGroup='" + counterTypeList.get(i).getCounterName() + "')\n"
                            + "AND (header.cancelFlag ='N')\n"
                            + "and lineitem.vendor='" + SalesIntimationRs.getString("VENDOR_NO") + "'\n";
                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        getSalesIntimationSummary = getSalesIntimationSummary.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                    }

                    getSalesIntimationSummary = getSalesIntimationSummary + "group by material ;";
                    SalesIntimationSummaryRs = daoClass.Fun_Resultset(getSalesIntimationSummary);
                    summarySubQty = 0f;
                    summarySubRate = 0f;
                    summarySubGross = 0f;
                    summarySubDis = 0f;
                    summarySubNet = 0f;
                    while (SalesIntimationSummaryRs.next()) {
                        salesIntimationSummaryList.add(new SalesIntimationList(SalesIntimationSummaryRs.getString("VENDOR_ID"), SalesIntimationSummaryRs.getString("VENDOR_NAME"), SalesIntimationSummaryRs.getString("CRAFT_GROUP") + " / " + SalesIntimationSummaryRs.getString("CRAFT_NAME"), SalesIntimationSummaryRs.getString("DATE_TIME"), SalesIntimationSummaryRs.getString("Bill_No"), SalesIntimationSummaryRs.getString("MATERIAL"), SalesIntimationSummaryRs.getString("MATERIAL_DEC"), SalesIntimationSummaryRs.getFloat("QUANTITY"), SalesIntimationSummaryRs.getFloat("RATE"), SalesIntimationSummaryRs.getFloat("GROSS"), SalesIntimationSummaryRs.getFloat("DISCOUNT"), SalesIntimationSummaryRs.getFloat("NET_AMT")));
                        //Sub total
                        summarySubQty = summarySubQty + SalesIntimationSummaryRs.getFloat("QUANTITY");
                        summarySubRate = summarySubRate + SalesIntimationSummaryRs.getFloat("RATE");
                        summarySubGross = summarySubGross + SalesIntimationSummaryRs.getFloat("GROSS");
                        summarySubDis = summarySubDis + SalesIntimationSummaryRs.getFloat("DISCOUNT");
                        summarySubNet = summarySubNet + SalesIntimationSummaryRs.getFloat("NET_AMT");
                        summarySubTotal = SalesIntimationSummaryRs.getString("CRAFT_GROUP") + " / " + SalesIntimationSummaryRs.getString("CRAFT_NAME");
                        //Craft Grand Total
                        summaryCraftTotalQty = summaryCraftTotalQty + SalesIntimationSummaryRs.getFloat("QUANTITY");
                        summaryCraftTotalRate = summaryCraftTotalRate + SalesIntimationSummaryRs.getFloat("RATE");
                        summaryCraftTotalGross = summaryCraftTotalGross + SalesIntimationSummaryRs.getFloat("GROSS");
                        summaryCraftTotalDis = summaryCraftTotalDis + SalesIntimationSummaryRs.getFloat("DISCOUNT");
                        summaryCraftTotalNet = summaryCraftTotalNet + SalesIntimationSummaryRs.getFloat("NET_AMT");
                        summaryCraftSubTotal = SalesIntimationSummaryRs.getString("CRAFT_GROUP") + " / " + SalesIntimationSummaryRs.getString("CRAFT_NAME");;
                        // Grand Total
                        summaryTotalQty = summaryTotalQty + SalesIntimationSummaryRs.getFloat("QUANTITY");
                        summaryTotalRate = summaryTotalRate + SalesIntimationSummaryRs.getFloat("RATE");
                        summaryTotalGross = summaryTotalGross + SalesIntimationSummaryRs.getFloat("GROSS");
                        summaryTotalDis = summaryTotalDis + SalesIntimationSummaryRs.getFloat("DISCOUNT");
                        summaryTotalNet = summaryTotalNet + SalesIntimationSummaryRs.getFloat("NET_AMT");

                    }
                    salesIntimationSummaryList.add(new SalesIntimationList(" ", "Sub Total ", summarySubTotal, " ", " ", " ", " ", summarySubQty, summarySubRate, summarySubGross, summarySubDis, summarySubNet));
                }
                salesIntimationSummaryList.add(new SalesIntimationList(" ", "Craft Sub Total ", summaryCraftSubTotal, " ", " ", " ", " ", summaryTotalQty, summaryTotalRate, summaryTotalGross, summaryTotalDis, summaryCraftTotalNet));
                summaryTotal = summaryTotal + "," + counterTypeList.get(i).getCounterName();
            }
            salesIntimationSummaryList.add(new SalesIntimationList(" ", "Grand Total ", summaryTotal, " ", " ", " ", " ", summaryTotalQty, summaryTotalRate, summaryTotalGross, summaryTotalDis, summaryTotalNet));

        } catch (Exception e) {
            System.out.println("=>" + e);
        } finally {

            daoClass.closeResultSet(SalesIntimationSummaryRs);
            daoClass.closeResultSet(SalesIntimationRs);
            daoClass.closeResultSet(counterTypeRs);
        }
        return salesIntimationSummaryList;
    }
    /*Dheeraj*/

    public static List<SalesIntimationList> SalesIntimationAbstractReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String typeData) {
        String subTotalCraftGroup = "";
        String grandTotalCraftGroup = "";
        DaoClass daoClass = new DaoClass();
        List<CounterTypeList> counterTypeList = new ArrayList<>();
        List<SalesIntimationList> salesIntimationList = new ArrayList<>();
        //float qty = 0f;
        //float rate = 0f;
        Float grossAmt = 0.0f;
        Float discAmt = 0.0f;
        Float netAmt = 0.0f;
        //float packAmt = 0f;

        Float subGrossAmt = 0.0f;
        Float subDiscAmt = 0.0f;
        Float subNetAmt = 0.0f;

        try {
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            String getCounterNoandNameQuery = "SELECT SQL_CACHE distinct lineitem.materialCraftGroup as materialCraftGroup\n"
                    + "  FROM ((pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                    + "INNER JOIN\n"
                    + "pos.cashbill_header_master cashbill_header_master\n"
                    + "ON (cashbill_lineitem_master.cashBill_id =\n"
                    + "cashbill_header_master.cashBill_id))\n"
                    + "INNER JOIN\n"
                    + "pos.lineitem lineitem\n"
                    + "ON (lineitem.sales_orderno =\n"
                    + "cashbill_lineitem_master.counterbill_no))\n"
                    + "INNER JOIN\n"
                    + "pos.header header\n"
                    + "ON (header.sales_orderno = lineitem.sales_orderno)\n"
                    //+ "WHERE (cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')\n"
                    + "WHERE (header.cancelFlag = 'N')\n"
                    + "AND (cashbill_lineitem_master.cashbill_dateTime  between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')";

            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                getCounterNoandNameQuery = getCounterNoandNameQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
            }
            counterTypeRs = daoClass.Fun_Resultset(getCounterNoandNameQuery);
            while (counterTypeRs.next()) {
                if (!(counterTypeRs.getString("materialCraftGroup").equalsIgnoreCase(DaoClass.getRestricedCraftGroup()))) {
                    counterTypeList.add(new CounterTypeList(counterTypeRs.getString("materialCraftGroup")));
                    grandTotalCraftGroup = grandTotalCraftGroup.concat(counterTypeRs.getString("materialCraftGroup"));
                    grandTotalCraftGroup = grandTotalCraftGroup.concat(",");
                }
            }
            String crftGrp = "";
            for (int i = 0; i < counterTypeList.size(); i++) {
                String getSalesIntimationQuery = "SELECT SQL_CACHE cashbill_header_master.paymentType as PaymentType,\n"
                        // + "vendor_master.vendor_id as VENDOR_ID, \n"
                        + "vendor_master.vendor_name as VENDOR_NAME, \n"
                        // + "header.pck_charge as PCKAMT,\n"
                        // + "header.sales_orderno as Bill_No,\n"
                        + "lineitem.vendor as VENDOR_NO,     \n"
                        // + "lineitem.material as MATERIAL,\n"
                        // + "lineitem.descrip as MATERIAL_DEC,\n"
                        + "lineitem.materialCraftGroup as CRAFT_GROUP,\n"
                        + "counterList.description as CRAFT_NAME, \n"
                        + "sum(lineitem.qty) as QUANTITY,\n"
                        + "lineitem.price as RATE,\n"
                        + "sum(lineitem.prc_value) as GROSS,\n"
                        + "sum(lineitem.discount_value) as DISCOUNT,\n"
                        // + "sum(lineitem.calcu_value )as NET_AMT,\n"
                        + "sum(lineitem.prc_value)-sum(lineitem.discount_value) as NET_AMT\n"
                        //+ "DATE_FORMAT(header.date_time,'%d-%m-%Y %h:%m:%s')as DATE_TIME      \n"
                        + "FROM ((( pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                        + "INNER JOIN\n"
                        + "pos.cashbill_header_master cashbill_header_master\n"
                        + "ON (cashbill_lineitem_master.cashBill_id =\n"
                        + "cashbill_header_master.cashBill_id))\n"
                        + "INNER JOIN\n"
                        + "pos.lineitem lineitem\n"
                        + "ON (lineitem.sales_orderno =\n"
                        + "cashbill_lineitem_master.counterbill_no))\n"
                        + "INNER JOIN\n"
                        + " pos.craft_counter_list counterList\n"
                        + " ON (counterList.craft_group = lineitem.materialCraftGroup)\n"
                        + "INNER JOIN\n"
                        + "pos.header header\n"
                        + "ON (header.sales_orderno = lineitem.sales_orderno))\n"
                        + "INNER JOIN\n"
                        + "pos.vendor_master vendor_master\n"
                        + "ON (lineitem.vendor = vendor_master.vendor_id)\n"
                        //  + "WHERE (cashbill_header_master.paymentType ='" + paraPaymentType.trim() + "')\n"
                        + "WHERE (header.date_time between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')\n"
                        + "AND (lineitem.materialCraftGroup='" + counterTypeList.get(i).getCounterName() + "')\n";
                //'" + paraFromDate.trim()  + "' AND '" + paraToDate.trim()  + "')

                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    getSalesIntimationQuery = getSalesIntimationQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                }

                if (!typeData.equalsIgnoreCase("all")) {
                    getSalesIntimationQuery = getSalesIntimationQuery.concat("  AND (lineitem.vendor like'" + typeData.trim() + "%')\n");
                }
                getSalesIntimationQuery = getSalesIntimationQuery + " AND (header.cancelFlag ='N') group by lineitem.vendor";
                SalesIntimationRs = daoClass.Fun_Resultset(getSalesIntimationQuery);
                /*
                 String billNo = "";
                 float packVal = 0f;
                 */
                while (SalesIntimationRs.next()) {
                    if (!(SalesIntimationRs.getString("VENDOR_NO").equals(DaoClass.getRestricedVendor()))) {
                        // qty = qty + SalesIntimationRs.getFloat("QUANTITY");
                        // rate = rate + SalesIntimationRs.getFloat("RATE");
                        grossAmt = grossAmt + SalesIntimationRs.getFloat("GROSS");
                        discAmt = discAmt + SalesIntimationRs.getFloat("DISCOUNT");
                        netAmt = netAmt + SalesIntimationRs.getFloat("NET_AMT");

                        if (!(counterTypeList.get(i).getCounterName().equalsIgnoreCase(crftGrp))) {
                            subGrossAmt = 0.0f;
                            subDiscAmt = 0.0f;
                            subNetAmt = 0.0f;
                            subGrossAmt = subGrossAmt + SalesIntimationRs.getFloat("GROSS");
                            subDiscAmt = subDiscAmt + SalesIntimationRs.getFloat("DISCOUNT");
                            subNetAmt = subNetAmt + SalesIntimationRs.getFloat("NET_AMT");
                            subTotalCraftGroup = SalesIntimationRs.getString("CRAFT_NAME");
                        } else {
                            subGrossAmt = subGrossAmt + SalesIntimationRs.getFloat("GROSS");
                            subDiscAmt = subDiscAmt + SalesIntimationRs.getFloat("DISCOUNT");
                            subNetAmt = subNetAmt + SalesIntimationRs.getFloat("NET_AMT");
                            subTotalCraftGroup = SalesIntimationRs.getString("CRAFT_NAME");
                        }

//                        if (!billNo.equals(SalesIntimationRs.getString("Bill_No"))) {
//                            salesIntimationList.add(new SalesIntimationList(SalesIntimationRs.getString("VENDOR_ID"), SalesIntimationRs.getString("VENDOR_NAME"), SalesIntimationRs.getString("MATERIAL_DEC"), SalesIntimationRs.getFloat("PCKAMT"), SalesIntimationRs.getFloat("QUANTITY"), SalesIntimationRs.getFloat("RATE"), SalesIntimationRs.getFloat("GROSS"), SalesIntimationRs.getFloat("DISCOUNT"), SalesIntimationRs.getFloat("NET_AMT") + SalesIntimationRs.getFloat("PCKAMT"), SalesIntimationRs.getString("DATE_TIME"), SalesIntimationRs.getString("Bill_No")));
//                            packAmt = packAmt + SalesIntimationRs.getFloat("PCKAMT");
//                        } else {
//                            salesIntimationList.add(new SalesIntimationList(SalesIntimationRs.getString("VENDOR_ID"), SalesIntimationRs.getString("VENDOR_NAME"), SalesIntimationRs.getString("MATERIAL_DEC"), packVal, SalesIntimationRs.getFloat("QUANTITY"), SalesIntimationRs.getFloat("RATE"), SalesIntimationRs.getFloat("GROSS"), SalesIntimationRs.getFloat("DISCOUNT"), SalesIntimationRs.getFloat("NET_AMT") + packVal, SalesIntimationRs.getString("DATE_TIME"), SalesIntimationRs.getString("Bill_No")));
//                            packAmt = packAmt + packVal;
//                        }
                        //Packing Charg and Vat Not Added
                        salesIntimationList.add(new SalesIntimationList(SalesIntimationRs.getString("VENDOR_NO"), SalesIntimationRs.getString("VENDOR_NAME"), SalesIntimationRs.getFloat("GROSS"), SalesIntimationRs.getFloat("DISCOUNT"), SalesIntimationRs.getFloat("NET_AMT"), SalesIntimationRs.getString("CRAFT_GROUP") + "-" + SalesIntimationRs.getString("CRAFT_NAME")));
                    }
                    crftGrp = counterTypeList.get(i).getCounterName();
                }
                if (grossAmt != 0 && netAmt != 0) {
                    salesIntimationList.add(new SalesIntimationList(" ", "SUB-TOTAL ( " + subTotalCraftGroup + " )", subGrossAmt, subDiscAmt, subNetAmt, " "));
                }
            }
            salesIntimationList.add(new SalesIntimationList(" ", "GRAND-TOTAL ( " + grandTotalCraftGroup + " )", grossAmt, discAmt, netAmt, " "));
        } catch (SQLException e) {
            System.out.println("SIA :: " + e.getMessage());
        } finally {
            daoClass.closeResultSet(counterTypeRs);
            daoClass.closeResultSet(SalesIntimationRs);
        }
        return salesIntimationList;
    }

    public Float getCwdsTotAmount() {
        return cwdsTotAmount;
    }

    public void setCwdsTotAmount(Float cwdsTotAmount) {
        this.cwdsTotAmount = cwdsTotAmount;
    }

    public int getCwdsTotBillCount() {
        return cwdsTotBillCount;
    }

    public void setCwdsTotBillCount(int cwdsTotBillCount) {
        this.cwdsTotBillCount = cwdsTotBillCount;
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

    public String getLoggedSessionId() {
        return loggedSessionId;
    }

    public void setLoggedSessionId(String loggedSessionId) {
        this.loggedSessionId = loggedSessionId;
    }

    public String getReportPaymentType() {
        return reportPaymentType;
    }

    public void setReportPaymentType(String reportPaymentType) {
        this.reportPaymentType = reportPaymentType;
    }

}
