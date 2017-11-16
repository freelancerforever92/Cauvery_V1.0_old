package com.Action.CashBill;

import static com.Action.CashBill.CashDailySummaryView_0.counterTypeRs;
import static com.Action.CashBill.DcsrReport.daoClass;
import static com.Action.CashBill.SalesIntimationAbstract.SalesIntimationRs;
import static com.Action.CashBill.SalesIntimationSummary.salesIntimationSummaryList;
import com.opensymphony.xwork2.ActionSupport;
import com.to.SalesIntimationList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DiscountPersentage extends ActionSupport {

    static List<SalesIntimationList> salesIntimationList = new ArrayList<>();

    public static List<SalesIntimationList> DiscountPersentageReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String vendortype, String vendorIds, String craftGroups, String discountPersentage) {
        ArrayList<String> arrayCraftGroup = null;
        salesIntimationList.clear();
        float qty = 0.0f;
        float rate = 0.0f;
        float grossAmt = 0.0f;
        float discAmt = 0.0f;
        float netAmt = 0.0f;
        int disPer = 0;

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
            String vendorArray[] = vendorIds.split(",");
            for (String AL : arrayCraftGroup) {
                for (int i = 0; i < vendorArray.length; i++) {
                    String getSalesIntimationQuery = "SELECT SQL_CACHE\n"
                            + "cashbill_header_master.paymentType as PaymentType,\n"
                            + "vendor_master.vendor_name as VENDOR_NAME, \n"
                            + "header.manual_bill_no as manualBillNo,\n"
                            + "header.sales_orderno as Bill_No,\n"
                            + "lineitem.vendor as VENDOR_ID,     \n"
                            + "lineitem.material as MATERIAL,\n"
                            + "lineitem.descrip as MATERIAL_DEC,\n"
                            + "lineitem.materialCraftGroup as CRAFT_GROUP,\n"
                            + "craft_counter_list.description  as CRAFT_NAME, \n"
                            + "lineitem.qty as QUANTITY,\n"
                            + "lineitem.price as RATE,\n"
                            + "lineitem.prc_value as GROSS,\n"
                            + "lineitem.discount_value as DISCOUNT,\n"
                            + "lineitem.discount_percentage as DISCOUNT_PER,\n"
                            + "(lineitem.prc_value-lineitem.discount_value) as NET_AMT,\n"
                            + "DATE_FORMAT(header.date_time,'%d-%m-%Y')as DATE_TIME \n"
                            + "  FROM    (   (   (   (   pos.lineitem lineitem\n"
                            + "                       INNER JOIN\n"
                            + "                          pos.vendor_master vendor_master\n"
                            + "                       ON (lineitem.vendor = vendor_master.vendor_id))\n"
                            + "                   INNER JOIN\n"
                            + "                      pos.header header\n"
                            + "                   ON (header.sales_orderno = lineitem.sales_orderno))\n"
                            + "               INNER JOIN\n"
                            + "                  pos.cashbill_lineitem_master cashbill_lineitem_master\n"
                            + "               ON (lineitem.sales_orderno =\n"
                            + "                      cashbill_lineitem_master.counterbill_no))\n"
                            + "           INNER JOIN\n"
                            + "              pos.cashbill_header_master cashbill_header_master\n"
                            + "           ON (cashbill_lineitem_master.cashBill_id =\n"
                            + "                  cashbill_header_master.cashBill_id))\n"
                            + "       INNER JOIN\n"
                            + "          pos.craft_counter_list craft_counter_list\n"
                            + "       ON (lineitem.materialCraftGroup = craft_counter_list.craft_group)\n"
                            + " WHERE     (lineitem.discount_percentage >'" + discountPersentage.trim() + "')\n"
                            + "       AND (cashbill_lineitem_master.cashbill_dateTime BETWEEN '" + paraFromDate.trim() + "' AND '" + paraToDate.trim() + "') \n"
                            + "AND (lineitem.materialCraftGroup='" + AL + "')\n"
                            + "      AND (header.cancelFlag = 'N' AND lineitem.vendor<>'100173')";

                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        getSalesIntimationQuery = getSalesIntimationQuery.concat(" AND (cashbill_header_master.paymentType ='" + paraPaymentType + "')\n");
                    }
                    getSalesIntimationQuery = getSalesIntimationQuery.concat("AND (lineitem.vendor in (" + vendorArray[i] + "))\n");
                    getSalesIntimationQuery = getSalesIntimationQuery.concat("order by Bill_No");
                    SalesIntimationRs = daoClass.Fun_Resultset(getSalesIntimationQuery);
                    while (SalesIntimationRs.next()) {
                        qty = qty + SalesIntimationRs.getFloat("QUANTITY");
                        rate = rate + SalesIntimationRs.getFloat("RATE");
                        grossAmt = grossAmt + SalesIntimationRs.getFloat("GROSS");
                        discAmt = discAmt + SalesIntimationRs.getFloat("DISCOUNT");
                        netAmt = netAmt + SalesIntimationRs.getFloat("NET_AMT");
                        disPer = disPer + SalesIntimationRs.getInt("DISCOUNT_PER");

                        salesIntimationList.add(new SalesIntimationList(SalesIntimationRs.getFloat("QUANTITY"), SalesIntimationRs.getFloat("RATE"), SalesIntimationRs.getFloat("GROSS"), SalesIntimationRs.getFloat("DISCOUNT"), SalesIntimationRs.getFloat("NET_AMT"), SalesIntimationRs.getString("DATE_TIME") + " / " + SalesIntimationRs.getString("PaymentType"), SalesIntimationRs.getString("Bill_No"), SalesIntimationRs.getString("manualBillNo"), SalesIntimationRs.getString("VENDOR_ID"), SalesIntimationRs.getString("VENDOR_NAME"), SalesIntimationRs.getString("MATERIAL") + "/" + SalesIntimationRs.getString("MATERIAL_DEC"), SalesIntimationRs.getString("DISCOUNT_PER") + " %", SalesIntimationRs.getString("CRAFT_GROUP") + " - " + SalesIntimationRs.getString("CRAFT_NAME")));
                        if (grossAmt != 0 || netAmt != 0) {
                            crftGrp = AL;
                        }
                    }

                }
                grandTotalCraftGroup = grandTotalCraftGroup + " " + crftGrp;
            }

            salesIntimationList.add(new SalesIntimationList(qty, rate, grossAmt, discAmt, netAmt, " ", " ", " ", " ", " ", "GRAND-TOTAL ( " + grandTotalCraftGroup + " )", disPer+" %", " "));
        } catch (SQLException e) {
            System.out.println("Exception :: " + e.getMessage());
        } finally {
            daoClass.closeResultSet(counterTypeRs);
            daoClass.closeResultSet(SalesIntimationRs);
        }
        return salesIntimationList;
    }
}
