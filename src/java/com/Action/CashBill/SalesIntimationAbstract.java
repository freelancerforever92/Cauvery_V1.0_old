package com.Action.CashBill;

import static com.Action.CashBill.CashDailySummaryView_0.counterTypeRs;
import static com.Action.CashBill.DcsrReport.daoClass;
import com.opensymphony.xwork2.ActionSupport;
import com.to.SalesIntimationList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SalesIntimationAbstract extends ActionSupport {

    static List<SalesIntimationList> salesIntimationList = new ArrayList<>();
    static ResultSet SalesIntimationRs = null;

    public static List<SalesIntimationList> SalesIntimationAbstractReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String vendortype, String vendorIds, String craftGroups) {
        ArrayList<String> arrayCraftGroup = null;
        salesIntimationList.clear();
        Float grossAmt = 0.0f;
        Float discAmt = 0.0f;
        Float netAmt = 0.0f;
        //float packAmt = 0f;
        String subTotalCraftGroup = "";
        String grandTotalCraftGroup = "";
        Float subGrossAmt = 0.0f;
        Float subDiscAmt = 0.0f;
        Float subNetAmt = 0.0f;
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
                        + "lineitem.vendor as VENDOR_NO,     \n"
                        + "lineitem.materialCraftGroup as CRAFT_GROUP,\n"
                        + "counterList.description as CRAFT_NAME, \n"
                        + "sum(lineitem.qty) as QUANTITY,\n"
                        + "lineitem.price as RATE,\n"
                        + "sum(lineitem.prc_value) as GROSS,\n"
                        + "sum(lineitem.discount_value) as DISCOUNT,\n"
                        + "sum(lineitem.prc_value)-sum(lineitem.discount_value) as NET_AMT\n"
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
                        + "AND (lineitem.materialCraftGroup='" + AL + "')\n";

                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    getSalesIntimationQuery = getSalesIntimationQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                }
                getSalesIntimationQuery = getSalesIntimationQuery.concat("  AND (lineitem.vendor in (" + vendorIds.trim() + "))\n");

                getSalesIntimationQuery = getSalesIntimationQuery + " AND (header.cancelFlag ='N') group by lineitem.vendor";
                SalesIntimationRs = daoClass.Fun_Resultset(getSalesIntimationQuery);
                while (SalesIntimationRs.next()) {
                    grossAmt += SalesIntimationRs.getFloat("GROSS");
                    discAmt += SalesIntimationRs.getFloat("DISCOUNT");
                    netAmt += SalesIntimationRs.getFloat("NET_AMT");
                    if (!(AL.equalsIgnoreCase(crftGrp))) {
                        subGrossAmt += SalesIntimationRs.getFloat("GROSS");
                        subDiscAmt += SalesIntimationRs.getFloat("DISCOUNT");
                        subNetAmt += SalesIntimationRs.getFloat("NET_AMT");
                        subTotalCraftGroup = SalesIntimationRs.getString("CRAFT_NAME");
                    } else {
                        subGrossAmt += SalesIntimationRs.getFloat("GROSS");
                        subDiscAmt += SalesIntimationRs.getFloat("DISCOUNT");
                        subNetAmt += SalesIntimationRs.getFloat("NET_AMT");
                        subTotalCraftGroup = SalesIntimationRs.getString("CRAFT_NAME");
                    }
                    salesIntimationList.add(new SalesIntimationList(SalesIntimationRs.getString("VENDOR_NO"), SalesIntimationRs.getString("VENDOR_NAME"), SalesIntimationRs.getFloat("GROSS"), SalesIntimationRs.getFloat("DISCOUNT"), SalesIntimationRs.getFloat("NET_AMT"), SalesIntimationRs.getString("CRAFT_GROUP") + "-" + SalesIntimationRs.getString("CRAFT_NAME")));
                }

                if (subGrossAmt != 0 && subNetAmt != 0) {
                    crftGrp = AL;
                    salesIntimationList.add(new SalesIntimationList(" ", "SUB-TOTAL ( " + subTotalCraftGroup + " )", subGrossAmt, subDiscAmt, subNetAmt, " "));
                    subGrossAmt = 0.0f;
                    subDiscAmt = 0.0f;
                    subNetAmt = 0.0f;
                }
                grandTotalCraftGroup = grandTotalCraftGroup + " " + crftGrp;
                crftGrp = "";
            }
            salesIntimationList.add(new SalesIntimationList(" ", "GRAND-TOTAL ( " + grandTotalCraftGroup + " )", grossAmt, discAmt, netAmt, " "));
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        } finally {
            daoClass.closeResultSet(counterTypeRs);
            daoClass.closeResultSet(SalesIntimationRs);
        }
        return salesIntimationList;
    }

}
