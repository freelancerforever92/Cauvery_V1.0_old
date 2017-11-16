/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.updated.reports;

import com.Action.CashBill.CraftGroupAndVendorMethods;
import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import com.schudeler.LineItem;
import com.schudeler.LineItems;
import com.schudeler.XmlToBean;
import com.to.CashLineItemDCSR;
import com.to.SalesIntimationList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UpdatedSalesIntimationDetail extends ActionSupport {

    public static List<SalesIntimationList> UpdatedSalesIntimationDetailReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String vendortype, String vendorIds, String craftGroups) {
        List<SalesIntimationList> salesIntimationList = new ArrayList<>();
        List<SalesIntimationList> salesIntimationListGT = new ArrayList<>();
        ArrayList<String> arrayCraftGroup = null;
        DaoClass daoClass = new DaoClass();
        Connection con = null;
        PreparedStatement ps = null;
        PreparedStatement psV = null;
        ResultSet rs = null;
        ResultSet rset = null;
        ResultSet vset = null;
        XmlToBean xTb = new XmlToBean();
        String vendorNumber = "";
        float grossAmt = 0.0f;
        float discAmt = 0.0f;
        float netAmt = 0.0f;
        float qty = 0.0f;

        float grossAmtG = 0.0f;
        float discAmtG = 0.0f;
        float netAmtG = 0.0f;
        float qtyG = 0.0f;

        float grossAmtSub = 0.0f;
        float discAmtSub = 0.0f;
        float netAmtSub = 0.0f;
        float qtySub = 0.0f;

        float grossAmtGF = 0.0f;
        float discAmtGF = 0.0f;

        try {
            con = daoClass.Fun_DbCon();
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

            String query = "SELECT * FROM pos.report_summary where date_time between ? and ?";
            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                query = query + " and paymentType=?";
            }

            ps = con.prepareStatement(query);
            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                ps.setString(1, paraFromDate + "%");
                ps.setString(2, paraToDate + "%");
                ps.setString(3, paraPaymentType);
            } else {
                ps.setString(1, paraFromDate + "%");
                ps.setString(2, paraToDate + "%");
            }
            rs = ps.executeQuery();

            while (rs.next()) {

                CashLineItemDCSR cSR = new CashLineItemDCSR();
                String xml = rs.getString("xml_document");
                LineItems items = xTb.xmlToBean(xml);

                for (LineItem it : items.getItems()) {

                    if (vendorIds.contains(it.getVendor())) {

                        String vquery = "SELECT  vendor_name FROM pos.vendor_master where vendor_id=?";

                        psV = con.prepareStatement(vquery);
                        psV.setString(1, it.getVendor());

                        vset = psV.executeQuery();
                        if (arrayCraftGroup.contains(it.getMaterialCraftGroup())) {

                            grossAmtG = grossAmtG + Float.parseFloat(it.getPrcValue());
                            qtyG = qtyG + Float.parseFloat(it.getQuantity());
                            discAmtG = discAmtG + Float.parseFloat(it.getDiscount());

                            SalesIntimationList val = new SalesIntimationList();
                            val.setCraftGroupName(rs.getString("counter_name"));
                            val.setVendorNumber(it.getVendor());
                            while (vset.next()) {

                                val.setVendorName(vset.getString("vendor_name"));
                            }

                            val.setDateTime(rs.getString("date_time") + " / " + rs.getString("paymentType"));
                            val.setManualBilNo(rs.getString("manual_bill_no"));
                            val.setMaterialId(rs.getString("counter_name"));
                            val.setDescription(it.getMaterial() + " / " + it.getDescription());
                            val.setQty(Float.parseFloat(it.getQuantity()));
                            val.setRate(Float.parseFloat(it.getPrice()));
                            val.setGrossAmt(Float.parseFloat(it.getPrcValue()));
                            val.setDiskPer(it.getDiscountPer());
                            val.setDiscAmt(Float.parseFloat(it.getDiscount()));
                            val.setBillNo(rs.getString("sales_orderno"));

                            grossAmt = val.getGrossAmt();
                            discAmt = val.getDiscAmt();
                            netAmt = grossAmt - discAmt;
                            val.setNetAmt(netAmt);
                            vendorNumber = it.getVendor();

                            netAmtG = netAmtG + netAmt;

                            salesIntimationList.add(val);

                        }
                    }

                }

            }
            Collections.sort(salesIntimationList, SalesIntimationList.NameComparator);

            String Vendor = "Empty";
            String vendorDes = "Empty";
            String counterName = "Empty";
            for (SalesIntimationList d : salesIntimationList) {

                if (!Vendor.equals("Empty")) {
                    if (!Vendor.equals(d.getVendorNumber())) {
                        SalesIntimationList val = new SalesIntimationList();

                        val.setDateTime("");
                        val.setpaymentType("");
                        val.setManualBilNo("");
                        val.setMaterialId("");
                        val.setDescription(vendorDes + "-" + Vendor + " " + "SUB-TOTAL(" + vendorDes + ")");
                        val.setQty(qtySub);
                        val.setRate(0.0F);
                        val.setGrossAmt(grossAmtSub);
                        val.setDiskPer("");
                        val.setDiscAmt(discAmtSub);
                        val.setNetAmt(netAmtSub);
                        salesIntimationListGT.add(val);

                        grossAmtSub = 0.0f;
                        discAmtSub = 0.0f;
                        netAmtSub = 0.0f;
                        qtySub = 0.0f;
                    }
                }
                vendorDes = d.getVendorName();
                counterName = d.getCraftGroupName();
                grossAmtSub = grossAmtSub + d.getGrossAmt();
                discAmtSub = discAmtSub + d.getDiscAmt();
                grossAmtGF = d.getGrossAmt();
                discAmtGF = d.getDiscAmt();
                netAmt = grossAmtGF - discAmtGF;
                netAmtSub = netAmtSub + netAmt;
                qtySub = qtySub + d.getQty();

               salesIntimationListGT.add(d);

                Vendor = d.getVendorNumber();
            }

            SalesIntimationList val = new SalesIntimationList();

            val.setDateTime("");
            val.setpaymentType("");
            val.setManualBilNo("");
            val.setMaterialId("");
            val.setDescription(vendorDes + "-" + Vendor + " " + "SUB-TOTAL(" + vendorDes + ")");
            val.setQty(qtySub);
            val.setRate(0.0F);
            val.setGrossAmt(grossAmtSub);
            val.setDiskPer("");
            val.setDiscAmt(discAmtSub);
            val.setNetAmt(netAmtSub);
            salesIntimationListGT.add(val);

            salesIntimationListGT.add(new SalesIntimationList(qtyG, 0.0F, grossAmtG, discAmtG, netAmtG, " ", " ", " ", " ", " ", "GRAND-TOTAL ", " ", " "));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error :: " + e.getMessage());
        }

        return salesIntimationListGT;
    }

}
