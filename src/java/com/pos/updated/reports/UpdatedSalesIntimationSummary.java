/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.updated.reports;

import com.Action.CashBill.CraftGroupAndVendorMethods;

import com.Action.CashBill.SalesIntimationSummary;
import com.DAO.DaoClass;
import com.schudeler.LineItem;
import com.schudeler.LineItems;
import com.schudeler.XmlToBean;
import com.to.CashLineItemDCSR;
import com.to.SalesIntimationList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdatedSalesIntimationSummary {

    public static List<SalesIntimationList> UpdatedSalesIntimationSummaryReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String vendortype, String vendorIds, String craftGroups) {
        List<SalesIntimationList> salesIntimationSummaryList = new ArrayList<>();
        List<SalesIntimationList> salesIntimationSummaryTest = new ArrayList<>();
        List<SalesIntimationList> salesIntimationListGT = new ArrayList<>();
        List<SalesIntimationList> salesIntimationListCST = new ArrayList<>();
        ArrayList<String> arrayCraftGroup = null;
        DaoClass daoClass = new DaoClass();
        Connection con = null;
        Connection conn = null;
        PreparedStatement ps = null;
        PreparedStatement psV = null;
        PreparedStatement psd = null;
        ResultSet rs = null;
        ResultSet rset = null;
        ResultSet vset = null;
        ResultSet Fset = null;
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

        float grossAmtCS = 0.0f;
        float discAmtCS = 0.0f;
        float netAmtCS = 0.0f;
        float qtyCS = 0.0f;

        float grossAmtSum = 0.0f;
        float discAmtSum = 0.0f;
        float netAmtSum = 0.0f;
        float qtySum = 0.0f;
        float netAmtSumTemp = 0.0f;

        float netAmtTemp = 0.0f;
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
            query = query + "ORDER BY counter_name";
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
                String materialId = "EMPTY";
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
                            val.setCraftGroupName(rs.getString("counter_name") + " - " + it.getMaterialCraftGroup());
                            val.setVendorNumber(it.getVendor());
                            while (vset.next()) {

                                val.setVendorName(vset.getString("vendor_name"));
                            }

                            val.setDateTime(rs.getString("date_time"));
                            val.setManualBilNo(rs.getString("manual_bill_no"));
                            val.setDescription(it.getDescription());
                            val.setQty(Float.parseFloat(it.getQuantity()));
                            val.setRate(Float.parseFloat(it.getPrice()));

                            val.setGrossAmt(Float.parseFloat(it.getPrcValue()));
                            val.setDiskPer(it.getDiscountPer());
                            val.setDiscAmt(Float.parseFloat(it.getDiscount()));
                            val.setMaterialId(it.getMaterial() + " / " + rs.getString("paymentType"));
                            val.setCraftGroup(rs.getString("counter_name"));

                            netAmt = Float.parseFloat(it.getPrcValue()) - Float.parseFloat(it.getDiscount());
                            netAmtG = netAmtG + netAmt;
                            val.setNetAmt(netAmt);
                            vendorNumber = it.getVendor();
                            salesIntimationSummaryTest.add(val);

                        }

                    }
                }

            }
            Collections.sort(salesIntimationSummaryTest, SalesIntimationList.NameComparatorMaterial);
            Collections.sort(salesIntimationSummaryTest, SalesIntimationList.NameComparatorVen);

            String materialId = "Empty";
            String CraftTemp = "Empty";
            String vendorTemp = "Empty";
            String venodrName = "Empty";
            String venodrDesc = "Empty";
            Float rateTemp = 0.0F;
            String Craft = "Empty";
            for (SalesIntimationList e : salesIntimationSummaryTest) {

                if (!materialId.equals("Empty")) {
                    if (!materialId.equals(e.getMaterialId())) {
                        SalesIntimationList va = new SalesIntimationList();
                        va.setVendorNumber(vendorTemp);
                        va.setVendorName(venodrName);
                        va.setDateTime("");
                        va.setManualBilNo("");
                        va.setDescription(venodrDesc);
                        va.setQty(qtySum);
                        va.setRate(rateTemp);
                        va.setGrossAmt(grossAmtSum);
                        va.setDiskPer("");
                        va.setDiscAmt(discAmtSum);
                        va.setMaterialId(materialId);
                        va.setCraftGroup(CraftTemp);
                        va.setNetAmt(netAmtSum);
                        salesIntimationSummaryList.add(va);

                        grossAmtSum = 0.0f;
                        discAmtSum = 0.0f;
                        netAmtSum = 0.0f;
                        qtySum = 0.0f;
                    }

                }
                grossAmtSum = grossAmtSum + e.getGrossAmt();
                discAmtSum = discAmtSum + e.getDiscAmt();
                qtySum = qtySum + e.getQty();
                netAmtSumTemp = e.getGrossAmt() - e.getDiscAmt();

                CraftTemp = e.getCraftGroup();
                vendorTemp = e.getVendorNumber();
                venodrName = e.getVendorName();
                venodrDesc = e.getDescription();
                rateTemp = e.getRate();
                Craft = e.getCraftGroup();
                //  salesIntimationSummaryList.add(e);
                netAmtSum = netAmtSum + netAmtSumTemp;
                materialId = e.getMaterialId();
            }

            SalesIntimationList va = new SalesIntimationList();
            va.setCraftGroupName(CraftTemp);
            va.setVendorNumber(vendorTemp);
            va.setVendorName(venodrName);
            va.setDescription(venodrDesc);
            va.setRate(rateTemp);
            va.setDateTime("");
            va.setManualBilNo("");
            va.setDescription(venodrDesc);
            va.setQty(qtySum);
            va.setRate(rateTemp);
            va.setGrossAmt(grossAmtSum);
            va.setDiskPer("");
            va.setDiscAmt(discAmtSum);
            va.setMaterialId(materialId);
            va.setCraftGroup(Craft);
            va.setNetAmt(netAmtSum);
            salesIntimationSummaryList.add(va);

            String counter = "Empty";
            String Vendor = "Empty";
            String vendorDes = "Empty";
            String counterName = "Empty";
            for (SalesIntimationList d : salesIntimationSummaryList) {

                if (!counter.equals("Empty")) {
                    if (!counter.equals(d.getCraftGroup())) {
                        SalesIntimationList v = new SalesIntimationList();
                        v.setVendorNumber("");
                        v.setVendorName("Craft-Sub Total");
                        v.setCraftGroup(counter);
                        v.setMaterialId("");
                        v.setDescription("");
                        v.setQty(qtyCS);
                        v.setGrossAmt(grossAmtCS);
                        v.setDiscAmt(discAmtCS);
                        v.setNetAmt(netAmtCS);
                        salesIntimationListGT.add(v);
                        grossAmtCS = 0.0f;
                        discAmtCS = 0.0f;
                        netAmtCS = 0.0f;
                        qtyCS = 0.0f;
                    }

                }

                if (!Vendor.equals("Empty")) {
                    if (!Vendor.equals(d.getVendorNumber())) {
                        SalesIntimationList val = new SalesIntimationList();

                        val.setDateTime("");
                        val.setVendorName("Sub-Total");
                        val.setpaymentType("");
                        val.setManualBilNo("");
                        val.setCraftGroup(d.getCraftGroupName());
                        val.setMaterialId("");
                        val.setDescription("");
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

                grossAmtCS = grossAmtCS + d.getGrossAmt();
                discAmtCS = discAmtCS + d.getDiscAmt();
                qtyCS = qtyCS + d.getQty();
                netAmtTemp = d.getGrossAmt() - d.getDiscAmt();
                netAmtCS = netAmtCS + netAmtTemp;
                counter = d.getCraftGroup();

                salesIntimationListGT.add(d);

                Vendor = d.getVendorNumber();
            }

            SalesIntimationList v = new SalesIntimationList();
            v.setVendorNumber("");
            v.setVendorName("Craft-Sub Total");
            v.setCraftGroup(counter);
            v.setMaterialId("");
            v.setDescription("");
            v.setQty(qtyCS);
            v.setGrossAmt(grossAmtCS);
            v.setDiscAmt(discAmtCS);
            v.setNetAmt(netAmtCS);
            salesIntimationListGT.add(v);

            SalesIntimationList val = new SalesIntimationList();
            val.setDateTime("");
            val.setVendorName("Sub-Total");
            val.setpaymentType("");
            val.setManualBilNo("");
            val.setCraftGroup(counterName);
            val.setMaterialId("");
            val.setDescription("");
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
