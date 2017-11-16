/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Action.CashBill;

import static com.Action.CashBill.CashDailySummaryView_0.userWiseLinesRs;
import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import com.to.CashHeaderDCSR;
import com.to.CashLineItemDCSR;
import com.to.CashierSummaryViewHeader;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JaVa
 */
public class DcsrEmpWise extends ActionSupport {

    int colCount;
    int pageIndex = 4;
    String cwsrExcelFileName;

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
    static CashierSummaryViewHeader header = new CashierSummaryViewHeader();

    public static List<CashLineItemDCSR> displayCashDailySummaryAbstractEmployeeReportDCSRAE(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String vendorIds, String vendortype) {
        DaoClass daoClass = new DaoClass();
        vendorIds = "'" + vendorIds + "'";
        List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList();
        try {
            Float grossAmountS = 0f;
            Float discAmountS = 0f;
            Float netAmountS = 0f;
            Float vatAmountS = 0f;
            Float packAmountS = 0f;
            Float cashBillAmountFloat = 0f;
            String arrayVendorIds = " ";
            Float grossAmountT = 0f;
            Float discAmountT = 0f;
            Float netAmountT = 0f;
            Float vatAmountT = 0f;
            Float packAmountT = 0f;
            Float cashBillAmountT = 0f;
            String paraToDateCdate = null;
             CraftGroupAndVendorMethods dcsrReport = new CraftGroupAndVendorMethods();
            String[] SplitFromDate = paraFromDate.split("-");
            String[] SplitToDate = paraToDate.split("-");
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            if (vendorIds.contains("All")) {

                arrayVendorIds = dcsrReport.getVendorList(paraFromDate, paraToDate, vendortype);
            } else if ((!(vendorIds.contains("All")))) {

                arrayVendorIds = vendorIds;
            }
            String CounterQuery = "SELECT SQL_CACHE distinct cashbill_header_master.user_id FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)CROSS JOIN pos.header header INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk) WHERE (header.cancelFlag = 'N')\n";
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
                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        getBillNoandAmtQuery = getBillNoandAmtQuery.concat(" AND(cashbill_header_master.paymentType ='" + paraPaymentType + "')\n ");
                    }
                    getBillNoandAmtQuery = getBillNoandAmtQuery + "AND(cashbill_lineitem_master.cashbill_dateTime between '" + CDate.trim() + "%' AND '" + paraToDateCdate.trim() + "' and emp_master.emp_id='" + userWiseLinesRs.getString("user_id") + "') AND lineitem.vendor In(" + arrayVendorIds + ")";
                    dcsrLinesRs = daoClass.Fun_Resultset(getBillNoandAmtQuery);
                    if (i == Integer.parseInt(SplitToDate[2])) {
                        while (dcsrLinesRs.next()) {
                            float Amt = ((dcsrLinesRs.getFloat("Net_Amount") + dcsrLinesRs.getFloat("Vat_Amount") + dcsrLinesRs.getFloat("Pack_Amount")));
                            CashLineItemDCSR itemDCSR = new CashLineItemDCSR();
                            if (dcsrLinesRs.getFloat("Net_Amount") != 0) {
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
}
