/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Action.CashBill;

import static com.Action.CashBill.CashDailySummaryView_0.counterTypeRs;
import static com.Action.CashBill.DcsrReport.daoClass;
import static com.Action.CashBill.SalesIntimationAbstract.SalesIntimationRs;
import com.opensymphony.xwork2.ActionSupport;
import com.to.SalesIntimationList;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author JaVa
 */
public class SalesIntimationDetail extends ActionSupport {

    static List<SalesIntimationList> salesIntimationList = new ArrayList<>();

    public static List<SalesIntimationList> SalesIntimationDetailReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String vendortype, String vendorIds, String craftGroups) {
        ArrayList<String> arrayCraftGroup = null;
        salesIntimationList.clear();
        float qty = 0.0f;
        float rate = 0.0f;
        float grossAmt = 0.0f;
        float discAmt = 0.0f;
        float netAmt = 0.0f;
        //float packAmt = 0f;
        Float detailedQty = 0.0f;
        Float detailedRate = 0.0f;
        Float detailedSubGrossAmt = 0.0f;
        Float detailedSubDiscAmt = 0.0f;
        Float detailedSubNetAmt = 0.0f;
        String detailedVendName = "";
        String detailedVendId = "";
        String detailedCraftGroup = "";
        String detailedCrftGrp = "";
        String subTotalCraftGroup = "";
        String grandTotalCraftGroup = "";
        try {
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
            String crftGrp = "";
            for (String AL : arrayCraftGroup) {

                String getSalesIntimationQuery = "SELECT SQL_CACHE cashbill_header_master.paymentType as PaymentType,\n"
                        + "vendor_master.vendor_name as VENDOR_NAME, \n"
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
                        + "(header.date_time between '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "')\n"
                        + "AND (lineitem.materialCraftGroup='" + AL + "')\n"
                        + "AND (header.cancelFlag ='N')";

                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    getSalesIntimationQuery = getSalesIntimationQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                }
                getSalesIntimationQuery = getSalesIntimationQuery.concat("AND (lineitem.vendor in (" + vendorIds + "))\n");
                getSalesIntimationQuery = getSalesIntimationQuery.concat(" order by lineitem.vendor");
                SalesIntimationRs = daoClass.Fun_Resultset(getSalesIntimationQuery);
                while (SalesIntimationRs.next()) {
                    qty += SalesIntimationRs.getFloat("QUANTITY");
                    //rate = rate + SalesIntimationRs.getFloat("RATE");
                    grossAmt += SalesIntimationRs.getFloat("GROSS");
                    discAmt += SalesIntimationRs.getFloat("DISCOUNT");
                    netAmt += SalesIntimationRs.getFloat("NET_AMT");
                    if (!(SalesIntimationRs.getString("VENDOR_ID").equalsIgnoreCase(detailedCrftGrp))) {
                        if (detailedSubGrossAmt != 0 && detailedSubNetAmt != 0) {
                            salesIntimationList.add(new SalesIntimationList(detailedQty, detailedRate, detailedSubGrossAmt, detailedSubDiscAmt, detailedSubNetAmt, " ", " ", " ", "", "", detailedVendName + "-" + detailedVendId + " " + "SUB-TOTAL (" + subTotalCraftGroup + ")", " ", ""));
                        }
                        detailedSubGrossAmt = 0.0f;
                        detailedSubDiscAmt = 0.0f;
                        detailedSubNetAmt = 0.0f;
                        detailedQty = 0.0f;
                        detailedRate = 0.0f;
                        detailedVendId = "";
                        detailedVendName = "";
                        detailedCraftGroup = "";

                        detailedQty += SalesIntimationRs.getFloat("QUANTITY");
                        //detailedRate = detailedRate + SalesIntimationRs.getFloat("RATE");
                        detailedSubGrossAmt += SalesIntimationRs.getFloat("GROSS");
                        detailedSubDiscAmt += SalesIntimationRs.getFloat("DISCOUNT");
                        detailedSubNetAmt += SalesIntimationRs.getFloat("NET_AMT");
                        detailedVendId = SalesIntimationRs.getString("VENDOR_ID");
                        detailedVendName = SalesIntimationRs.getString("VENDOR_NAME");
                        subTotalCraftGroup = SalesIntimationRs.getString("CRAFT_NAME");
                        detailedCraftGroup = SalesIntimationRs.getString("CRAFT_GROUP") + " - " + SalesIntimationRs.getString("CRAFT_NAME");
                    } else {
                        detailedQty += SalesIntimationRs.getFloat("QUANTITY");
                        //detailedRate = detailedRate + SalesIntimationRs.getFloat("RATE");
                        detailedSubGrossAmt += SalesIntimationRs.getFloat("GROSS");
                        detailedSubDiscAmt += SalesIntimationRs.getFloat("DISCOUNT");
                        detailedSubNetAmt += SalesIntimationRs.getFloat("NET_AMT");
                        detailedVendId = SalesIntimationRs.getString("VENDOR_ID");
                        detailedVendName = SalesIntimationRs.getString("VENDOR_NAME");
                        subTotalCraftGroup = SalesIntimationRs.getString("CRAFT_NAME");
                        detailedCraftGroup = SalesIntimationRs.getString("CRAFT_GROUP") + " - " + SalesIntimationRs.getString("CRAFT_NAME");
                    }
                    salesIntimationList.add(new SalesIntimationList(SalesIntimationRs.getFloat("QUANTITY"), SalesIntimationRs.getFloat("RATE"), SalesIntimationRs.getFloat("GROSS"), SalesIntimationRs.getFloat("DISCOUNT"), SalesIntimationRs.getFloat("NET_AMT"), SalesIntimationRs.getString("DATE_TIME") + " / " + SalesIntimationRs.getString("PaymentType"), SalesIntimationRs.getString("Bill_No"), SalesIntimationRs.getString("manualBillNo"), SalesIntimationRs.getString("VENDOR_ID"), SalesIntimationRs.getString("VENDOR_NAME"), SalesIntimationRs.getString("MATERIAL") + "/" + SalesIntimationRs.getString("MATERIAL_DEC"), SalesIntimationRs.getString("DISCOUNT_PER") + " %", SalesIntimationRs.getString("CRAFT_GROUP") + " - " + SalesIntimationRs.getString("CRAFT_NAME")));
                    detailedCrftGrp = SalesIntimationRs.getString("VENDOR_ID").trim();
                    if (grossAmt != 0 || netAmt != 0) {
                        crftGrp = AL;
                    }
                }
                grandTotalCraftGroup = grandTotalCraftGroup + " " + crftGrp;
            }
            if (detailedQty != 0 && detailedSubNetAmt != 0) {
                salesIntimationList.add(new SalesIntimationList(detailedQty, detailedRate, detailedSubGrossAmt, detailedSubDiscAmt, detailedSubNetAmt, " ", " ", " ", " ", " ", detailedVendName + "-" + detailedVendId + " " + "SUB-TOTAL (" + subTotalCraftGroup + ")", " ", " "));
            }
            salesIntimationList.add(new SalesIntimationList(qty, rate, grossAmt, discAmt, netAmt, " ", " ", " ", " ", " ", "GRAND-TOTAL ( " + grandTotalCraftGroup + " )", " ", " "));
        } catch (SQLException e) {
            System.out.println("Exception :: " + e.getMessage());
        } finally {
            daoClass.closeResultSet(counterTypeRs);
            daoClass.closeResultSet(SalesIntimationRs);
        }
        return salesIntimationList;
    }
}
