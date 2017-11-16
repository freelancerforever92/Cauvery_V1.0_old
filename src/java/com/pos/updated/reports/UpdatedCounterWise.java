/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pos.updated.reports;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import com.schudeler.LineItem;
import com.schudeler.LineItems;
import com.schudeler.XmlToBean;
import com.to.CashLineItemDCSR;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UpdatedCounterWise extends ActionSupport {

    private static ResultSet rs;
    private static String counterNamesub;
    private static String counterNameGrand;

    public static List<CashLineItemDCSR> newUpdatedCounterWiseReportPos(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String craftGroups) {

        PreparedStatement ps = null;
        Connection con = null;
        List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList();
        DaoClass daoClass = new DaoClass();
        XmlToBean xTb = new XmlToBean();
        Float grossAmount = 0f;
        Float discAmount = 0f;
        Float netAmount = 0f;
        Float vatAmount = 0f;

        Float gstPercentage = 0f;       //for gst
        Float sgstAmount = 0f;          //for gst
        Float cgstAmount = 0f;          //for gst

        Float packAmount = 0f;
        Float cashBillAmount = 0f;
        Float discSubTotAmt = 0.0f;
        Float netSubTotAmt = 0.0f;
        Float vatSubTotAmt = 0.0f;

        Float gstPercentageSubTot = 0.0f;
        Float sgstSubTotAmt = 0.0f;
        Float cgstSubTotAmt = 0.0f;

        Float packSubTotAmt = 0.0f;
        Float grossSubTotAmt = 0.0f;
        Float cashBillSubTotAmt = 0.0f;
        Float netTotAmt = 0.0f;
        Float vatTotAmt = 0.0f;

        Float gstPercentageTotAmt = 0.0f;
        Float sgstTotAmt = 0.0f;
        Float cgstTotAmt = 0.0f;

        Float packTotAmt = 0.0f;
        Float discTotAmt = 0.0f;
        Float grossTotAmt = 0.0f;
        Float cashBillTotAmt = 0.0f;

        String counter_type = "";
        try {
            ArrayList<String> arrayCraftGroup = new ArrayList<>();
            con = daoClass.Fun_DbCon();
            String sptdFromDate[] = paraFromDate.split("-");

            int frmDD = Integer.parseInt(sptdFromDate[2]);      // for gst
            int frmMM = Integer.parseInt(sptdFromDate[1]);      // for gst
            int frmYYYY = Integer.parseInt(sptdFromDate[0]);    // for gst

            paraToDate = daoClass.Fun_Str("SELECT SQL_CACHE Date(ADDDATE('" + paraToDate.trim() + "',1))");

            if (craftGroups.contains("All")) {

                String getCounterNoandNameQuery = "";
                getCounterNoandNameQuery = "SELECT distinct SQL_CACHE counter_name FROM pos.report_summary";
                ps = con.prepareStatement(getCounterNoandNameQuery);
                ResultSet ResultSet = ps.executeQuery();
                while (ResultSet.next()) {
                    arrayCraftGroup.add(ResultSet.getString("counter_name"));
                }
            } else {
                craftGroups = craftGroups.replace("'", "");
                String[] array = craftGroups.split(",");
                for (int i = 0; i < array.length; i++) {
                    String getCounterNoandNameQuery = "SELECT distinct counter_name FROM pos.report_summary where counter_type like '%" + array[i] + "'";
                    //System.out.println("getCounterNoandNameQuery" + getCounterNoandNameQuery);
                    ResultSet ResultSet = daoClass.Fun_Resultset(getCounterNoandNameQuery);
                    while (ResultSet.next()) {
                        arrayCraftGroup.add(ResultSet.getString("counter_name"));
                    }
                }

            }

            for (String lstCraftGroup : arrayCraftGroup) {

                String query = "SELECT * FROM pos.report_summary where date_time between ? and ? and counter_name=?";
                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    query = query + " and paymentType=?";

                }
                ps = con.prepareStatement(query);
                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    ps.setString(1, paraFromDate + "%");
                    ps.setString(2, paraToDate + "%");
                    ps.setString(3, lstCraftGroup.trim());
                    ps.setString(4, paraPaymentType);
                } else {
                    ps.setString(1, paraFromDate + "%");
                    ps.setString(2, paraToDate + "%");
                    ps.setString(3, lstCraftGroup.trim());

                }
                rs = ps.executeQuery();

                while (rs.next()) {
                    counterNamesub = lstCraftGroup;
                    String xml = rs.getString("xml_document");
                    LineItems items = xTb.xmlToBean(xml);
                    for (LineItem it : items.getItems()) {
                        grossAmount = grossAmount + Float.parseFloat(it.getPrcValue());
                        discAmount = discAmount + Float.parseFloat(it.getDiscount());
                        vatAmount = vatAmount + Float.parseFloat(it.getVatPer());
                    }
                    netAmount = grossAmount - discAmount;
                    packAmount = packAmount + rs.getFloat("pck_charge");
                    if (frmYYYY <= 2017 && (frmMM <= 6 || frmMM <= 06) && frmDD <= 30) {
                        // for vat
                        cashBillAmount = netAmount + packAmount + vatAmount;
                    } else {
                        //for gst
                        vatAmount = 0f;
                        gstPercentage = gstPercentage + rs.getFloat("gst_percentage");
                        sgstAmount = sgstAmount + rs.getFloat("sgst_amt");
                        cgstAmount = cgstAmount + rs.getFloat("cgst_amt");
                        cashBillAmount = netAmount + packAmount + sgstAmount + cgstAmount;
                    }
                    counter_type = rs.getString("counter_type") + "_" + lstCraftGroup;
                }

                grossSubTotAmt = grossSubTotAmt + grossAmount;
                discSubTotAmt = discSubTotAmt + discAmount;
                netSubTotAmt = netSubTotAmt + netAmount;
                vatSubTotAmt = vatSubTotAmt + vatAmount;

                gstPercentageSubTot = gstPercentageSubTot + gstPercentage;          // for GST
                sgstSubTotAmt = sgstSubTotAmt + sgstAmount;                         // for GST
                cgstSubTotAmt = cgstSubTotAmt + cgstAmount;                         // for GST

                packSubTotAmt = packSubTotAmt + packAmount;
                cashBillSubTotAmt = cashBillSubTotAmt + cashBillAmount;

                grossTotAmt = grossTotAmt + grossSubTotAmt;
                discTotAmt = discTotAmt + discSubTotAmt;
                netTotAmt = netTotAmt + netSubTotAmt;
                vatTotAmt = vatTotAmt + vatSubTotAmt;

                gstPercentageTotAmt = gstPercentageTotAmt + gstPercentageSubTot;      // for GST
                sgstTotAmt = sgstTotAmt + sgstSubTotAmt;                              // for GST
                cgstTotAmt = cgstTotAmt + cgstSubTotAmt;                              // for GST

                packTotAmt = packTotAmt + packSubTotAmt;
                cashBillTotAmt = cashBillTotAmt + cashBillSubTotAmt;

                grossAmount = 0f;
                discAmount = 0f;
                netAmount = 0f;
                vatAmount = 0f;

                gstPercentage = 0f;                                                     // for GST
                sgstAmount = 0f;                                                        // for GST
                cgstAmount = 0f;                                                        // for GST

                packAmount = 0f;
                cashBillAmount = 0f;

                if (cashBillSubTotAmt != 0.0) {
                    // counterNameGrand = counterNameGrand + "," + counterNamesub;
                    CashLineItemDCSR itemDCSRS = new CashLineItemDCSR();
                    itemDCSRS.setGrossAmountFloat(grossSubTotAmt);
                    itemDCSRS.setDiscAmountFloat(discSubTotAmt);
                    itemDCSRS.setNetAmountFloat(netSubTotAmt);
                    itemDCSRS.setVatAmountFloat(vatSubTotAmt);

                    itemDCSRS.setGstPercentage(gstPercentageSubTot);
                    itemDCSRS.setSgstAmountFloat(sgstSubTotAmt);
                    itemDCSRS.setCgstAmountFloat(cgstSubTotAmt);

                    itemDCSRS.setPackAmountFloat(packSubTotAmt);
                    itemDCSRS.setCashBillAmountFloat(cashBillSubTotAmt);
                    itemDCSRS.setDcsrDate(counter_type);

                    itemDCSRS.setFromDD(frmDD);
                    itemDCSRS.setFromMM(frmMM);
                    itemDCSRS.setFromYY(frmYYYY);
                    // itemDCSRS.setDcsrDate(counterNamesub);
                    summaryViewLinesDCSR.add(itemDCSRS);

                    discSubTotAmt = 0.0f;
                    netSubTotAmt = 0.0f;
                    vatSubTotAmt = 0.0f;

                    gstPercentageSubTot = 0.0f;     // for GST
                    sgstSubTotAmt = 0.0f;           // for GST
                    cgstSubTotAmt = 0.0f;           // for GST

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

                itemDCSRS2.setGstPercentage(gstPercentageTotAmt);       // for GST
                System.out.println("GST  : %" + gstPercentageTotAmt);
                itemDCSRS2.setSgstAmountFloat(sgstTotAmt);              // for GST
                System.out.println("SGST : %" + sgstTotAmt);
                itemDCSRS2.setCgstAmountFloat(cgstTotAmt);              // for GST
                System.out.println("CGST : %" + cgstTotAmt);

                itemDCSRS2.setPackAmountFloat(packTotAmt);
                itemDCSRS2.setCashBillAmountFloat(cashBillTotAmt);
                itemDCSRS2.setDcsrDate("Total");
                summaryViewLinesDCSR.add(itemDCSRS2);
            }

        } catch (Exception ex) {
            System.out.println("Exception :: " + ex.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception e) {
                System.out.println("Error Closeing : " + e.getMessage());
            }
        }

        return summaryViewLinesDCSR;

    }

}
