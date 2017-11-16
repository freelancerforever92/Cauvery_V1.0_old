package com.Action.CashBill;

import static com.Action.CashBill.CashDailySummaryView_0.dcsrLinesRs;
import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import com.to.CashLineItemDCSR;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JaVa
 */
public class DcsrReport extends ActionSupport {

    static ResultSet dcsrHeaderRs = null;
    static ResultSet userWiseLinesRs = null;
    static ResultSet userWisepackLinesRs = null;
    static DaoClass daoClass = new DaoClass();

    public static List<CashLineItemDCSR> displayCashDailySummaryReportDCSR(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String vendorIds, String vendortype) {
        vendorIds = "'" + vendorIds + "'";
        String counterName = null;
        String counterNamesub = "";
        String counterNameGrand = "";
        List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList();
        try {
            String arrayVendorIds = " ";
            ArrayList<String> arrayCraftGroup = new ArrayList<String>();
            paraToDate = daoClass.Fun_Str("SELECT SQL_CACHE Date(ADDDATE('" + paraToDate.trim() + "',1))");
            CraftGroupAndVendorMethods dcsrReport = new CraftGroupAndVendorMethods();
            if (vendorIds.contains("All")) {
                arrayCraftGroup = dcsrReport.getCraftList(paraFromDate, paraToDate, "dcsr");
                arrayVendorIds = dcsrReport.getVendorList(paraFromDate, paraToDate, vendortype);
            } else if ((!(vendorIds.contains("All")))) {
                arrayCraftGroup = dcsrReport.getCraftList(paraFromDate, paraToDate, "dcsr");
                arrayVendorIds = vendorIds;
            }
            String[] newArrayList = arrayVendorIds.split(",");
            /*Toget next DATE from given date.*/
            String newList = null;
            Float netTotAmt = 0.0f;
            Float vatTotAmt = 0.0f;
            Float packTotAmt = 0.0f;
            Float discTotAmt = 0.0f;
            Float grossTotAmt = 0.0f;
            Float cashBillTotAmt = 0.0f;
            String mutiCounterName = "";
            String updatedPaymentType = "";

            Float discSubTotAmt = 0.0f;
            Float netSubTotAmt = 0.0f;
            Float vatSubTotAmt = 0.0f;
            Float packSubTotAmt = 0.0f;
            Float grossSubTotAmt = 0.0f;
            Float cashBillSubTotAmt = 0.0f;
            
            for (String lstCraftGroup : arrayCraftGroup) {
                //for (String lstVendors : newArrayList) {

                    String recCountQry = "SELECT SQL_CACHE count(*) FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) INNER JOIN pos.craft_counter_list counterCraft ON (branch_counter.counter_no = counterCraft.storage_location) WHERE (header.cancelFlag='N') \n"
                            + "AND (cashbill_header_master.paymentType ='"+paraPaymentType+"')\n"
                            //+ "AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "'  AND '" + paraToDate.trim() + "' AND lineitem.vendor=" + lstVendors + " AND lineitem.materialCraftGroup ='" + lstCraftGroup + "')group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc";
                            + "AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "'  AND '" + paraToDate.trim() + "' AND lineitem.materialCraftGroup ='" + lstCraftGroup + "')group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc";
                    int recCountValue = daoClass.Fun_Int(recCountQry);
                    if (recCountValue > 0) {
                        String getBillNoandAmtQuery = "SELECT SQL_CACHE cashbill_header_master.paymentType as PaymentType,header.manual_bill_no,cashbill_lineitem_master.counterbill_no,branch_counter.counter_no_legacy,branch_counter.counter,SUM(lineitem.prc_value) as Gross_Amount,SUM(lineitem.discount_value) as Disc_Amount,SUM(lineitem.prc_value-lineitem.discount_value) as Net_Amount,SUM(lineitem.vat_value) as Vat_Amount,lineitem.pck_charge as Pack_Amount,header.net_amt+header.pck_charge as cashbill_amt,emp_master.emp_name FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) INNER JOIN pos.craft_counter_list counterCraft ON (branch_counter.counter_no = counterCraft.storage_location) WHERE (header.cancelFlag='N') \n";
                        if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                            getBillNoandAmtQuery = getBillNoandAmtQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                        }
                        /*BLOCKED PASSING VENDOR-ID FOR DCSR-REPORT ( AS PER END-USER REQUESTED ) */
                        //getBillNoandAmtQuery = getBillNoandAmtQuery + " AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "'  AND '" + paraToDate.trim() + "' AND lineitem.vendor=" + lstVendors + " AND lineitem.materialCraftGroup ='" + lstCraftGroup + "')group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc";
                        getBillNoandAmtQuery = getBillNoandAmtQuery + " AND(cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "'  AND '" + paraToDate.trim() + "' AND lineitem.materialCraftGroup ='" + lstCraftGroup + "')group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc";
                        dcsrLinesRs = daoClass.Fun_Resultset(getBillNoandAmtQuery);
                        while (dcsrLinesRs.next()) {
                            CashLineItemDCSR itemDCSR = new CashLineItemDCSR();
                            if (dcsrLinesRs.getString("PaymentType").equalsIgnoreCase("crc")) {
                                updatedPaymentType = "CARD";
                            } else if (!dcsrLinesRs.getString("PaymentType").equalsIgnoreCase("crc")) {
                                updatedPaymentType = dcsrLinesRs.getString("PaymentType");
                            }
                            counterNamesub = dcsrLinesRs.getString("counter");
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
                            //grossSubTotAmt = grossSubTotAmt + itemDCSR.getGrossAmountFloat();
                            //discSubTotAmt = discSubTotAmt + itemDCSR.getDiscAmountFloat();
                            //netSubTotAmt = netSubTotAmt + itemDCSR.getNetAmountFloat();
                            //vatSubTotAmt = vatSubTotAmt + itemDCSR.getVatAmountFloat();
                            //packSubTotAmt = packSubTotAmt + itemDCSR.getPackAmountFloat();
                            //cashBillSubTotAmt = cashBillSubTotAmt + itemDCSR.getCashBillAmountFloat();
                            grossSubTotAmt += itemDCSR.getGrossAmountFloat();
                            discSubTotAmt += itemDCSR.getDiscAmountFloat();
                            netSubTotAmt += itemDCSR.getNetAmountFloat();
                            vatSubTotAmt += itemDCSR.getVatAmountFloat();
                            packSubTotAmt += itemDCSR.getPackAmountFloat();
                            cashBillSubTotAmt += itemDCSR.getCashBillAmountFloat();

                            /*TOTAL CALCULATION*/
                            //grossTotAmt = grossTotAmt + itemDCSR.getGrossAmountFloat();
                            //discTotAmt = discTotAmt + itemDCSR.getDiscAmountFloat();
                            //netTotAmt = netTotAmt + itemDCSR.getNetAmountFloat();
                            //vatTotAmt = vatTotAmt + itemDCSR.getVatAmountFloat();
                            //packTotAmt = packTotAmt + itemDCSR.getPackAmountFloat();
                            //cashBillTotAmt = cashBillTotAmt + itemDCSR.getCashBillAmountFloat();
                            grossTotAmt += itemDCSR.getGrossAmountFloat();
                            discTotAmt += itemDCSR.getDiscAmountFloat();
                            netTotAmt += itemDCSR.getNetAmountFloat();
                            vatTotAmt += itemDCSR.getVatAmountFloat();
                            packTotAmt += itemDCSR.getPackAmountFloat();
                            cashBillTotAmt += itemDCSR.getCashBillAmountFloat();
                            summaryViewLinesDCSR.add(itemDCSR);
                        }
                        mutiCounterName = mutiCounterName + "," + counterName;
                    }
                //}
                counterNameGrand = counterNameGrand + "," + counterNamesub;
                if (cashBillTotAmt != 0) {
                    CashLineItemDCSR itemDCSRS = new CashLineItemDCSR();
                    itemDCSRS.setGrossAmountFloat(grossSubTotAmt);
                    itemDCSRS.setDiscAmountFloat(discSubTotAmt);
                    itemDCSRS.setNetAmountFloat(netSubTotAmt);
                    itemDCSRS.setVatAmountFloat(vatSubTotAmt);
                    itemDCSRS.setPackAmountFloat(packSubTotAmt);
                    itemDCSRS.setCashBillAmountFloat(cashBillSubTotAmt);
                    itemDCSRS.setEmpName(" ");
                    itemDCSRS.setCount(0);
                    itemDCSRS.setManualBillNo(" ");
                    itemDCSRS.setDcsrDate("Sub-Total" + "( " + counterNamesub + ")");
                    summaryViewLinesDCSR.add(itemDCSRS);

                    discSubTotAmt = 0.0f;
                    netSubTotAmt = 0.0f;
                    vatSubTotAmt = 0.0f;
                    packSubTotAmt = 0.0f;
                    grossSubTotAmt = 0.0f;
                    cashBillSubTotAmt = 0.0f;

                    String empwiseSubtotlQry = "SELECT SQL_CACHE sum(lineitem.prc_value) as grossamt,\n"
                            + "              sum(lineitem.discount_value) as dicAmt,\n"
                            + "              sum(lineitem.prc_value)-sum(lineitem.discount_value) as netAmt,\n"
                            + "              sum(lineitem.vat_value) as vatAmt,\n"
                            + "              sum(header.pck_charge) as pkgAmt,\n"
                            + "              sum(lineitem.prc_value)-sum(lineitem.discount_value) +sum(header.pck_charge)+sum(lineitem.vat_value) as totalamt,\n"
                            + "       emp_master.emp_name\n"
                            + "  FROM    (   (   (   pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                            + "                   INNER JOIN\n"
                            + "                      pos.cashbill_header_master cashbill_header_master\n"
                            + "                   ON (cashbill_lineitem_master.cashBill_id =\n"
                            + "                          cashbill_header_master.cashBill_id))\n"
                            + "               INNER JOIN\n"
                            + "                  pos.emp_master emp_master\n"
                            + "               ON (cashbill_header_master.user_id = emp_master.emp_id))\n"
                            + "           INNER JOIN\n"
                            + "              pos.header header\n"
                            + "           ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no))\n"
                            + "       INNER JOIN\n"
                            + "          pos.lineitem lineitem\n"
                            + "       ON (lineitem.sales_orderno = header.sales_orderno)\n"
                            + " WHERE   (lineitem.date_time between '" + paraFromDate.trim() + "'  AND '" + paraToDate.trim() + "')\n"
                            + "       AND (lineitem.materialCraftGroup ='" + lstCraftGroup + "')\n"
                            + "       AND (lineitem.vendor In(" + arrayVendorIds + ")) ";
                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        empwiseSubtotlQry = empwiseSubtotlQry.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                    }
                    empwiseSubtotlQry = empwiseSubtotlQry.concat(" group by cashbill_header_master.user_id ");
                    ResultSet empwiseSubtotRS = daoClass.Fun_Resultset(empwiseSubtotlQry);
                    while (empwiseSubtotRS.next()) {
                        CashLineItemDCSR itemDCSRS1 = new CashLineItemDCSR();
                        itemDCSRS1.setGrossAmountFloat(empwiseSubtotRS.getFloat("grossamt"));
                        itemDCSRS1.setDiscAmountFloat(empwiseSubtotRS.getFloat("dicAmt"));
                        itemDCSRS1.setNetAmountFloat(empwiseSubtotRS.getFloat("netAmt"));
                        itemDCSRS1.setVatAmountFloat(empwiseSubtotRS.getFloat("vatAmt"));
                        itemDCSRS1.setPackAmountFloat(empwiseSubtotRS.getFloat("pkgAmt"));
                        itemDCSRS1.setCashBillAmountFloat(empwiseSubtotRS.getFloat("totalamt"));
                        itemDCSRS1.setEmpName(" ");
                        itemDCSRS1.setCount(0);
                        itemDCSRS1.setManualBillNo(" ");
                        itemDCSRS1.setDcsrDate(empwiseSubtotRS.getString("emp_name") + " (" + counterNamesub + ")");
                        summaryViewLinesDCSR.add(itemDCSRS1);
                    }
                }
            }
            CashLineItemDCSR itemDCSRS2 = new CashLineItemDCSR();
            itemDCSRS2.setGrossAmountFloat(grossTotAmt);
            itemDCSRS2.setDiscAmountFloat(discTotAmt);
            itemDCSRS2.setNetAmountFloat(netTotAmt);
            itemDCSRS2.setVatAmountFloat(vatTotAmt);
            itemDCSRS2.setPackAmountFloat(packTotAmt);
            itemDCSRS2.setCashBillAmountFloat(cashBillTotAmt);
            itemDCSRS2.setEmpName(" ");
            itemDCSRS2.setCount(0);
            itemDCSRS2.setManualBillNo(" ");
            itemDCSRS2.setDcsrDate("Grand Total" + "( " + counterNameGrand + ")");
            summaryViewLinesDCSR.add(itemDCSRS2);
            // Emp wise Grand total
            String empwiseGrandtotalQry = "SELECT SQL_CACHE sum(lineitem.prc_value) as grossamt,\n"
                    + "sum(lineitem.discount_value) as dicAmt,\n"
                    + "sum(lineitem.prc_value)-sum(lineitem.discount_value) as netAmt,\n"
                    + "sum(lineitem.vat_value) as vatAmt,\n"
                    + "sum(header.pck_charge) as pkgAmt,\n"
                    + "sum(lineitem.prc_value)-sum(lineitem.discount_value) +sum(header.pck_charge)+sum(lineitem.vat_value) as totalamt,\n"
                    + "emp_master.emp_name\n"
                    + "FROM(((pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                    + "INNER JOIN\n"
                    + "pos.cashbill_header_master cashbill_header_master\n"
                    + "ON (cashbill_lineitem_master.cashBill_id =\n"
                    + "cashbill_header_master.cashBill_id))\n"
                    + "INNER JOIN\n"
                    + "pos.emp_master emp_master\n"
                    + "ON (cashbill_header_master.user_id = emp_master.emp_id))\n"
                    + "INNER JOIN\n"
                    + "pos.header header\n"
                    + "ON (header.sales_orderno = cashbill_lineitem_master.counterbill_no))\n"
                    + "INNER JOIN\n"
                    + "pos.lineitem lineitem\n"
                    + "ON (lineitem.sales_orderno = header.sales_orderno)\n"
                    + "WHERE(lineitem.date_time between '" + paraFromDate.trim() + "'  AND '" + paraToDate.trim() + "')\n";
                    //+ "AND (lineitem.vendor In(" + newList + ")) ";
            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                empwiseGrandtotalQry = empwiseGrandtotalQry.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
            }
            empwiseGrandtotalQry = empwiseGrandtotalQry.concat("group by cashbill_header_master.user_id ");
            ResultSet empwiseGrandtotalRS = daoClass.Fun_Resultset(empwiseGrandtotalQry);
            while (empwiseGrandtotalRS.next()) {
                CashLineItemDCSR itemDCSRS3 = new CashLineItemDCSR();
                itemDCSRS3.setGrossAmountFloat(empwiseGrandtotalRS.getFloat("grossamt"));
                itemDCSRS3.setDiscAmountFloat(empwiseGrandtotalRS.getFloat("dicAmt"));
                itemDCSRS3.setNetAmountFloat(empwiseGrandtotalRS.getFloat("netAmt"));
                itemDCSRS3.setVatAmountFloat(empwiseGrandtotalRS.getFloat("vatAmt"));
                itemDCSRS3.setPackAmountFloat(empwiseGrandtotalRS.getFloat("pkgAmt"));
                itemDCSRS3.setCashBillAmountFloat(Math.round(empwiseGrandtotalRS.getFloat("totalamt")));
                itemDCSRS3.setEmpName(" ");
                itemDCSRS3.setCount(0);
                itemDCSRS3.setManualBillNo(" ");
                //itemDCSRS3.setDcsrDate(empwiseGrandtotalRS.getString("emp_name") + " (" + counterNameGrand + ")");
                itemDCSRS3.setDcsrDate(empwiseGrandtotalRS.getString("emp_name"));
                summaryViewLinesDCSR.add(itemDCSRS3);
            }
        } catch (SQLException ex) {
            System.out.println("Exception :: " + ex.getMessage());
        }
        return summaryViewLinesDCSR;
    }

}
