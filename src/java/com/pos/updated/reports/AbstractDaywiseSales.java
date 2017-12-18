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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AbstractDaywiseSales extends ActionSupport {

    public static List<CashLineItemDCSR> AbstractDaywiseSalesReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId) throws SQLException {
        List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DaoClass daoClass = new DaoClass();
        XmlToBean xTb = new XmlToBean();
        Float grossAmount = 0f;
        Float discAmount = 0f;
        Float netAmount = 0f;
        Float vatAmount = 0f;

        Float gstPercentageAmount = 0f;     //for GST
        Float sgstAmount = 0f;              //for GST
        Float cgstAmount = 0f;              //for GST

        Float packAmount = 0f;
        Float cashBillAmount = 0f;

        Float grossAmountT = 0f;
        Float discAmountT = 0f;
        Float netAmountT = 0f;
        Float vatAmountT = 0f;

        Float gstPercentageAmountT = 0f;              //for GST
        Float sgstAmountT = 0f;              //for GST
        Float cgstAmountT = 0f;              //for GST

        Float packAmountT = 0f;
        Float cashBillAmountT = 0f;

        Float grossAmountGT = 0f;
        Float discAmountGT = 0f;
        Float netAmountGT = 0f;
        Float vatAmountGT = 0f;

        Float gstPercentageGT = 0f;              //for GST
        Float sgstAmountGT = 0f;              //for GST
        Float cgstAmountGT = 0f;              //for GST

        Float packAmountGT = 0f;
        Float cashBillAmountGT = 0f;

        Float cashBillAmountWROFT = 0f;
        Float cashBillAmountWROFGT = 0.0f;
        try {
            String[] SplitFromDate = paraFromDate.split("-");
            String[] SplitToDate = paraToDate.split("-");

            String sptdFromDate[] = paraFromDate.split("-");
            int frmDD = Integer.parseInt(sptdFromDate[2]);
            int frmMM = Integer.parseInt(sptdFromDate[1]);
            int frmYYYY = Integer.parseInt(sptdFromDate[0]);

            con = daoClass.Fun_DbCon();
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            for (int i = Integer.parseInt(SplitFromDate[2]); i <= Integer.parseInt(SplitToDate[2]); i++) {
                String CDate = SplitToDate[0] + "-" + SplitToDate[1] + "-" + i;
                paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + CDate.trim() + "',1))");

                String query = "SELECT * FROM pos.report_summary where date_time between ? and ? ";

                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    query = query + " and paymentType=?";
                }
                ps = con.prepareStatement(query);
                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    ps.setString(1, CDate + "%");
                    ps.setString(2, paraToDate + "%");
                    ps.setString(3, paraPaymentType);
                } else {
                    ps.setString(1, CDate + "%");
                    ps.setString(2, paraToDate + "%");
                }
                rs = ps.executeQuery();

                while (rs.next()) {
                    String xml = rs.getString("xml_document");
                    LineItems items = xTb.xmlToBean(xml);
                    for (LineItem it : items.getItems()) {
                        grossAmount = grossAmount + Float.parseFloat(it.getPrcValue());
                        discAmount = discAmount + Float.parseFloat(it.getDiscount());
                        vatAmount = vatAmount + Float.parseFloat(it.getVatPer());
                    }
                    netAmount = grossAmount - discAmount;
                    packAmount = rs.getFloat("pck_charge");
                    if (frmYYYY <= 2017 && (frmMM <= 6 || frmMM <= 06) && frmDD <= 30) {
                        // for VAT
                        cashBillAmount = netAmount + packAmount + vatAmount;
                        //Total value for Cdate
                        cashBillAmountWROFT = cashBillAmountWROFT + netAmount + packAmount + vatAmount;
                    } else {
                        // for GST
                        gstPercentageAmount = gstPercentageAmount + rs.getFloat("gst_percentage");
                        sgstAmount = sgstAmount + rs.getFloat("sgst_amt");
                        cgstAmount = cgstAmount + rs.getFloat("cgst_amt");
                        cashBillAmount = netAmount + packAmount + sgstAmount + cgstAmount;
                        //Total value for Cdate
                        cashBillAmountWROFT = cashBillAmountWROFT + netAmount + packAmount + sgstAmount + cgstAmount;
                    }
                    grossAmountT = grossAmountT + grossAmount;
                    discAmountT = discAmountT + discAmount;
                    netAmountT = netAmountT + netAmount;
                    vatAmountT = vatAmountT + vatAmount;

                    gstPercentageAmountT = gstPercentageAmountT + gstPercentageAmount;  // for GST
                    sgstAmountT = sgstAmountT + sgstAmount;                             // for GST
                    cgstAmountT = cgstAmountT + cgstAmount;                             // for GST

                    packAmountT = packAmountT + packAmount;
                    cashBillAmountT = cashBillAmountT + Math.round(cashBillAmount);

                    grossAmountGT = grossAmountGT + grossAmount;
                    discAmountGT = discAmountGT + discAmount;
                    netAmountGT = netAmountGT + netAmount;
                    vatAmountGT = vatAmountGT + vatAmount;

                    gstPercentageGT = gstPercentageGT + gstPercentageAmount;    // for GST
                    sgstAmountGT = sgstAmountGT + sgstAmount;           // for GST
                    cgstAmountGT = cgstAmountGT + cgstAmount;           // for GST

                    packAmountGT = packAmountGT + packAmount;
                    cashBillAmountGT = cashBillAmountGT + Math.round(cashBillAmount);
                    if (frmYYYY <= 2017 && (frmMM <= 6 || frmMM <= 06) && frmDD <= 30) {
                        // VAT
                        cashBillAmountWROFGT = cashBillAmountWROFGT + netAmount + packAmount + vatAmount;
                    } else {
                        // GST
                        cashBillAmountWROFT = cashBillAmountWROFT + netAmount + packAmount + sgstAmount + cgstAmount;
                    }
                    grossAmount = 0f;
                    discAmount = 0f;
                    netAmount = 0f;
                    vatAmount = 0f;

                    gstPercentageAmount = 0f;       // for GST
                    sgstAmount = 0f;                // for GST
                    cgstAmount = 0f;                // for GST

                    packAmount = 0f;
                    cashBillAmount = 0f;
                }

                CashLineItemDCSR cSR = new CashLineItemDCSR();
                cSR.setDcsrDate(CDate);
                cSR.setGrossAmountFloat(grossAmountT);
                cSR.setDiscAmountFloat(discAmountT);
                cSR.setNetAmountFloat(netAmountT);
                cSR.setVatAmountFloat(vatAmountT);

                cSR.setGstPercentage(gstPercentageAmountT);     // for GST
                cSR.setSgstAmountFloat(sgstAmountT);            // for GST
                cSR.setCgstAmountFloat(cgstAmountT);            // for GST

                cSR.setFromDD(frmDD);
                cSR.setFromMM(frmMM);
                cSR.setFromYY(frmYYYY);
                
                cSR.setPackAmountFloat(packAmountT);
                cSR.setCashBillAmountFloat(Math.round(cashBillAmountT));
                cSR.setCashBillAmountWROFloat(cashBillAmountWROFT);
                summaryViewLinesDCSR.add(cSR);

                grossAmountT = 0f;
                discAmountT = 0f;
                netAmountT = 0f;
                vatAmountT = 0f;

                gstPercentageAmountT = 0f;          // for GST
                sgstAmountT = 0f;                    // for GST
                cgstAmountT = 0f;                   // for GST

                packAmountT = 0f;
                cashBillAmountT = 0f;
                cashBillAmountWROFT = 0f;

            }
            CashLineItemDCSR cSRG = new CashLineItemDCSR();
            cSRG.setDcsrDate("Grand- Total");
            cSRG.setGrossAmountFloat(grossAmountGT);
            cSRG.setDiscAmountFloat(discAmountGT);
            cSRG.setNetAmountFloat(netAmountGT);
            cSRG.setVatAmountFloat(vatAmountGT);
            cSRG.setPackAmountFloat(packAmountGT);

            cSRG.setGstPercentage(gstPercentageGT);    // for GST
            cSRG.setSgstAmountFloat(sgstAmountGT);     // for GST 
            cSRG.setCgstAmountFloat(cgstAmountGT);     // for GST

            cSRG.setCashBillAmountFloat(Math.round(cashBillAmountGT));
            cSRG.setCashBillAmountWROFloat(cashBillAmountWROFGT);
            summaryViewLinesDCSR.add(cSRG);
        } catch (Exception e) {
            System.out.println("Error..!");
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
