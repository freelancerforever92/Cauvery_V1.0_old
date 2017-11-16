package com.Action.CashBill;

import static com.Action.CashBill.CashDailySummaryView_0.counterTypeRs;
import static com.Action.CashBill.DcsrReport.daoClass;
import static com.Action.CashBill.SalesIntimationAbstract.SalesIntimationRs;
import com.opensymphony.xwork2.ActionSupport;
import com.to.SalesIntimationList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalesIntimationSummary extends ActionSupport {

    static ResultSet SalesIntimationSummaryRs = null;

    static List<SalesIntimationList> salesIntimationSummaryList = new ArrayList<>();

    public static List<SalesIntimationList> SalesIntimationSummaryReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String vendortype, String vendorIds, String craftGroups) {
        ArrayList<String> arrayCraftGroup = null;
        salesIntimationSummaryList.clear();
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
            /*
             csi=CONSIGNMENT SALEINTIMATION 
             */
            paraToDate = daoClass.Fun_Str("SELECT SQL_CACHE Date(ADDDATE('" + paraToDate.trim() + "',1))");
            CraftGroupAndVendorMethods cgavm = new CraftGroupAndVendorMethods();
            if (vendorIds.contains("All") && craftGroups.contains("All")) {
                arrayCraftGroup = cgavm.getCraftList(paraFromDate, paraToDate, "csi");
                vendorIds = cgavm.getVendorList(paraFromDate, paraToDate, vendortype);
            } else if (!vendorIds.contains("All") && !craftGroups.contains("All")) {
                arrayCraftGroup = new ArrayList<>(Arrays.asList(craftGroups.replace("'", "").trim().split(",")));
                vendorIds = "'" + vendorIds + "'";
            } else if ((!(vendorIds.contains("All"))) && ((craftGroups.contains("All")))) {
                arrayCraftGroup = cgavm.getCraftList(paraFromDate, paraToDate, "csi");
                vendorIds = "'" + vendorIds + "'";
            } else if (((vendorIds.contains("All"))) && (!(craftGroups.contains("All")))) {
                arrayCraftGroup = new ArrayList<>(Arrays.asList(craftGroups.replace("'", "").trim().split(",")));
                vendorIds = cgavm.getVendorList(paraFromDate, paraToDate, vendortype);
            }
            String vendorArray[] = vendorIds.split(",");
            for (String AL : arrayCraftGroup) {
                for (int i = 0; i < vendorArray.length; i++) {
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
                            + "and (lineitem.materialCraftGroup='" + AL.trim() + "')\n"
                            + "AND (header.cancelFlag ='N')\n"
                            + "and lineitem.vendor in (" + vendorArray[i] + ")\n";
                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        getSalesIntimationSummary = getSalesIntimationSummary.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                    }
                    getSalesIntimationSummary = getSalesIntimationSummary + "group by material order by vendor";
                    SalesIntimationSummaryRs = daoClass.Fun_Resultset(getSalesIntimationSummary);
                    String subtotal = "empty";
                    while (SalesIntimationSummaryRs.next()) {
                        if (subtotal.equalsIgnoreCase(SalesIntimationSummaryRs.getString("VENDOR_ID")) | subtotal.equals("empty")) {

                        } else {
                            salesIntimationSummaryList.add(new SalesIntimationList(" ", "Sub-Total ", summarySubTotal, " ", " ", " ", " ", summarySubQty, summarySubRate, summarySubGross, summarySubDis, summarySubNet));
                            summarySubQty = 0.0f;
                            summarySubRate = 0.0f;
                            summarySubGross = 0.0f;
                            summarySubDis = 0.0f;
                            summarySubNet = 0.0f;
                        }
                        /*Sub total*/
                        summarySubQty += SalesIntimationSummaryRs.getFloat("QUANTITY");
                        //summarySubRate = summarySubRate + SalesIntimationSummaryRs.getFloat("RATE");
                        summarySubGross += SalesIntimationSummaryRs.getFloat("GROSS");
                        summarySubDis += SalesIntimationSummaryRs.getFloat("DISCOUNT");
                        summarySubNet += SalesIntimationSummaryRs.getFloat("NET_AMT");
                        summarySubTotal = SalesIntimationSummaryRs.getString("CRAFT_GROUP") + " - " + SalesIntimationSummaryRs.getString("CRAFT_NAME");
                        /* Grand Total*/
                        summaryCraftTotalQty += SalesIntimationSummaryRs.getFloat("QUANTITY");
                        //summaryCraftTotalRate = summaryCraftTotalRate + SalesIntimationSummaryRs.getFloat("RATE");
                        summaryCraftTotalGross += SalesIntimationSummaryRs.getFloat("GROSS");
                        summaryCraftTotalDis += SalesIntimationSummaryRs.getFloat("DISCOUNT");
                        summaryCraftTotalNet += SalesIntimationSummaryRs.getFloat("NET_AMT");
                        summaryCraftSubTotal = SalesIntimationSummaryRs.getString("CRAFT_GROUP") + " - " + SalesIntimationSummaryRs.getString("CRAFT_NAME");;
                        /*Craft Grand Total*/
                        summaryTotalQty += SalesIntimationSummaryRs.getFloat("QUANTITY");
                        //summaryTotalRate = summaryTotalRate + SalesIntimationSummaryRs.getFloat("RATE");
                        summaryTotalGross += SalesIntimationSummaryRs.getFloat("GROSS");
                        summaryTotalDis += SalesIntimationSummaryRs.getFloat("DISCOUNT");
                        summaryTotalNet += SalesIntimationSummaryRs.getFloat("NET_AMT");
                        subtotal = SalesIntimationSummaryRs.getString("VENDOR_ID");
                        salesIntimationSummaryList.add(new SalesIntimationList(SalesIntimationSummaryRs.getString("VENDOR_ID"), SalesIntimationSummaryRs.getString("VENDOR_NAME"), SalesIntimationSummaryRs.getString("CRAFT_GROUP") + " - " + SalesIntimationSummaryRs.getString("CRAFT_NAME") + " / " + SalesIntimationSummaryRs.getString("PaymentType"), SalesIntimationSummaryRs.getString("DATE_TIME"), SalesIntimationSummaryRs.getString("Bill_No"), SalesIntimationSummaryRs.getString("MATERIAL"), SalesIntimationSummaryRs.getString("MATERIAL_DEC"), SalesIntimationSummaryRs.getFloat("QUANTITY"), SalesIntimationSummaryRs.getFloat("RATE"), SalesIntimationSummaryRs.getFloat("GROSS"), SalesIntimationSummaryRs.getFloat("DISCOUNT"), SalesIntimationSummaryRs.getFloat("NET_AMT")));
                    }
                    if (summarySubQty != 0 && summarySubNet != 0) {
                        salesIntimationSummaryList.add(new SalesIntimationList(" ", "Sub-Total ", summarySubTotal, " ", " ", " ", " ", summarySubQty, summarySubRate, summarySubGross, summarySubDis, summarySubNet));
                        summarySubQty = 0f;
                        summarySubRate = 0f;
                        summarySubGross = 0f;
                        summarySubDis = 0f;
                        summarySubNet = 0f;
                    }
                }
                if (summaryCraftTotalQty != 0 && summaryCraftTotalNet != 0) {
                    salesIntimationSummaryList.add(new SalesIntimationList(" ", "Craft Sub-Total ", summaryCraftSubTotal, " ", " ", " ", " ", summaryCraftTotalQty, summaryCraftTotalRate, summaryCraftTotalGross, summaryCraftTotalDis, summaryCraftTotalNet));
                    summaryCraftTotalQty = 0f;
                    summaryCraftTotalRate = 0f;
                    summaryCraftTotalGross = 0f;
                    summaryCraftTotalDis = 0f;
                    summaryCraftTotalNet = 0f;
                    summaryTotal = summaryTotal + "," + AL.trim();
                }
            }
            salesIntimationSummaryList.add(new SalesIntimationList(" ", "Grand-Total ", summaryTotal, " ", " ", " ", " ", summaryTotalQty, summaryTotalRate, summaryTotalGross, summaryTotalDis, summaryTotalNet));
        } catch (SQLException e) {
            System.out.println("Exception : " + e.getMessage());
        } finally {
            daoClass.closeResultSet(counterTypeRs);
            daoClass.closeResultSet(SalesIntimationRs);
        }
        return salesIntimationSummaryList;
    }

}
