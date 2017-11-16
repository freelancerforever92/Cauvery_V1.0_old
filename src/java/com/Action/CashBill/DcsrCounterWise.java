package com.Action.CashBill;

import static com.Action.CashBill.CashDailySummaryView_0.dcsrLinesRs;
import static com.Action.CashBill.DcsrReport.daoClass;
import com.opensymphony.xwork2.ActionSupport;
import com.to.CashLineItemDCSR;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DcsrCounterWise extends ActionSupport {

    public static List<CashLineItemDCSR> DcsrCounterWiseReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String vendortype, String vendorIds, String craftGroups) {
        vendorIds = "'" + vendorIds + "'";
        String counterName = null;

        String counterNamesub = "";
        String counterNameGrand = "";

        List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList();
        try {
            String arrayVendorIds = " ";
            ArrayList<String> arrayCraftGroup = new ArrayList<>();
            paraToDate = daoClass.Fun_Str("SELECT SQL_CACHE Date(ADDDATE('" + paraToDate.trim() + "',1))");
            CraftGroupAndVendorMethods dcsrReport = new CraftGroupAndVendorMethods();
            arrayCraftGroup = dcsrReport.getCraftList(paraFromDate, paraToDate, "dcsr");
            System.out.println("=> :: "+arrayCraftGroup);
            if (vendorIds.contains("All")) {
                arrayVendorIds = dcsrReport.getVendorList(paraFromDate, paraToDate, vendortype);
            } else if ((!(vendorIds.contains("All")))) {
                //arrayCraftGroup = dcsrReport.getCraftList(paraFromDate, paraToDate, "dcsr");
                arrayVendorIds = vendorIds;
            }
            String[] newArrayList = arrayVendorIds.split(",");
            /*Toget next DATE from given date.*/
            String newList = null;
            String mutiCounterName = "";
            String updatedPaymentType = "";

            Float netTotAmt = 0.0f;
            Float vatTotAmt = 0.0f;
            Float packTotAmt = 0.0f;
            Float discTotAmt = 0.0f;
            Float grossTotAmt = 0.0f;
            Float cashBillTotAmt = 0.0f;

            Float discSubTotAmt = 0.0f;
            Float netSubTotAmt = 0.0f;
            Float vatSubTotAmt = 0.0f;
            Float packSubTotAmt = 0.0f;
            Float grossSubTotAmt = 0.0f;
            Float cashBillSubTotAmt = 0.0f;
            for (String lstCraftGroup : arrayCraftGroup) {
                for (String lstVendors : newArrayList) {
                    String getBillNoandAmtQuery = "SELECT SQL_CACHE cashbill_header_master.paymentType as PaymentType,header.manual_bill_no,cashbill_lineitem_master.counterbill_no,branch_counter.counter_no_legacy,branch_counter.counter,SUM(lineitem.prc_value) as Gross_Amount,SUM(lineitem.discount_value) as Disc_Amount,SUM(lineitem.prc_value-lineitem.discount_value) as Net_Amount,SUM(lineitem.vat_value) as Vat_Amount,lineitem.pck_charge as Pack_Amount,header.net_amt+lineitem.pck_charge as cashbill_amt,emp_master.emp_name FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) INNER JOIN pos.craft_counter_list counterCraft ON (branch_counter.counter_no = counterCraft.storage_location) WHERE (header.cancelFlag='N') \n";
                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        getBillNoandAmtQuery = getBillNoandAmtQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                    }
                    getBillNoandAmtQuery = getBillNoandAmtQuery.concat(" AND (cashbill_lineitem_master.cashbill_dateTime between '" + paraFromDate.trim() + "'  AND '" + paraToDate.trim() + "' AND lineitem.vendor=" + lstVendors + " AND lineitem.materialCraftGroup ='" + lstCraftGroup + "')group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc");
                    
                    dcsrLinesRs = daoClass.Fun_Resultset(getBillNoandAmtQuery);
                    while (dcsrLinesRs.next()) {
                        if (dcsrLinesRs.getString("PaymentType").equalsIgnoreCase("crc")) {
                            updatedPaymentType = "CARD";
                        } else if (!dcsrLinesRs.getString("PaymentType").equalsIgnoreCase("crc")) {
                            updatedPaymentType = dcsrLinesRs.getString("PaymentType");
                        }
                        counterNamesub = dcsrLinesRs.getString("counter");
                        /*SUB-TOTAL CALCULATION*/
                        grossSubTotAmt = grossSubTotAmt + dcsrLinesRs.getFloat("Gross_Amount");
                        discSubTotAmt = discSubTotAmt + dcsrLinesRs.getFloat("Disc_Amount");
                        netSubTotAmt = netSubTotAmt + dcsrLinesRs.getFloat("Net_Amount");
                        vatSubTotAmt = vatSubTotAmt + dcsrLinesRs.getFloat("Vat_Amount");
                        packSubTotAmt = packSubTotAmt + dcsrLinesRs.getFloat("Pack_Amount");
                        System.out.println("packSubTotAmt :: " + packSubTotAmt);
                        cashBillSubTotAmt = cashBillSubTotAmt + Math.round((dcsrLinesRs.getFloat("Net_Amount") + dcsrLinesRs.getFloat("Vat_Amount") + dcsrLinesRs.getFloat("Pack_Amount")));
                        /*TOTAL CALCULATION*/
                        grossTotAmt = grossTotAmt + dcsrLinesRs.getFloat("Gross_Amount");
                        discTotAmt = discTotAmt + dcsrLinesRs.getFloat("Disc_Amount");
                        netTotAmt = netTotAmt + dcsrLinesRs.getFloat("Net_Amount");
                        vatTotAmt = vatTotAmt + dcsrLinesRs.getFloat("Vat_Amount");
                        packTotAmt = packTotAmt + dcsrLinesRs.getFloat("Pack_Amount");
                        cashBillTotAmt = cashBillTotAmt + Math.round((dcsrLinesRs.getFloat("Net_Amount") + dcsrLinesRs.getFloat("Vat_Amount") + dcsrLinesRs.getFloat("Pack_Amount")));
                    }
                    mutiCounterName = mutiCounterName + "," + counterName;
                }
                if (cashBillSubTotAmt != 0.0) {
                    counterNameGrand = counterNameGrand + "," + counterNamesub;
                    CashLineItemDCSR itemDCSRS = new CashLineItemDCSR();
                    itemDCSRS.setGrossAmountFloat(grossSubTotAmt);
                    itemDCSRS.setDiscAmountFloat(discSubTotAmt);
                    itemDCSRS.setNetAmountFloat(netSubTotAmt);
                    itemDCSRS.setVatAmountFloat(vatSubTotAmt);
                    itemDCSRS.setPackAmountFloat(packSubTotAmt);
                    itemDCSRS.setCashBillAmountFloat(cashBillSubTotAmt);
                    itemDCSRS.setDcsrDate(counterNamesub);
                    summaryViewLinesDCSR.add(itemDCSRS);
                    discSubTotAmt = 0.0f;
                    netSubTotAmt = 0.0f;
                    vatSubTotAmt = 0.0f;
                    packSubTotAmt = 0.0f;
                    grossSubTotAmt = 0.0f;
                    cashBillSubTotAmt = 0.0f;
                }
            }
            if (grossTotAmt != 0.0) {
                CashLineItemDCSR itemDCSRS2 = new CashLineItemDCSR();
                itemDCSRS2.setGrossAmountFloat(grossTotAmt);
                itemDCSRS2.setDiscAmountFloat(discTotAmt);
                itemDCSRS2.setNetAmountFloat(netTotAmt);
                itemDCSRS2.setVatAmountFloat(vatTotAmt);
                itemDCSRS2.setPackAmountFloat(packTotAmt);
                itemDCSRS2.setCashBillAmountFloat(cashBillTotAmt);
                itemDCSRS2.setDcsrDate("Total" + "( " + counterNameGrand + ")");
                summaryViewLinesDCSR.add(itemDCSRS2);
            }
        } catch (SQLException ex) {
            System.out.println("Exception :: " + ex.getMessage());
        }
        return summaryViewLinesDCSR;
    }
}
