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

public class UpdatedDcsrAbstract extends ActionSupport {

    private static String counterNamesub;

    public static List<CashLineItemDCSR> UpdatedDcsrAbstractReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId, String craftGroups) throws SQLException {
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

        Float gstPercentage = 0f;
        Float sgstAmount = 0f;
        Float cgstAmount = 0f;

        Float packAmount = 0f;
        Float cashBillAmount = 0f;

        Float grossAmountT = 0f;
        Float discAmountT = 0f;
        Float netAmountT = 0f;
        Float vatAmountT = 0f;

        Float gstPercentageT = 0f;
        Float sgstAmountT = 0f;
        Float cgstAmountT = 0f;

        Float packAmountT = 0f;
        Float cashBillAmountT = 0f;

        Float grossAmountGT = 0f;
        Float discAmountGT = 0f;
        Float netAmountGT = 0f;
        Float vatAmountGT = 0f;

        Float gstPercentageGT = 0f;
        Float sgstAmountGT = 0f;
        Float cgstAmountGT = 0f;

        Float packAmountGT = 0f;
        Float cashBillAmountGT = 0f;

        Float grossAmountTS = 0f;
        Float discAmountTS = 0f;
        Float netAmountTS = 0f;
        Float vatAmountTS = 0f;

        Float gstPercentageTS = 0f;
        Float sgstAmountTS = 0f;
        Float cgstAmountTS = 0f;

        Float packAmountTS = 0f;
        Float cashBillAmountTS = 0f;
        String counter_type = "";
        String counter_type_s = "Empty";

        try {
            ArrayList<String> arrayCraftGroup = new ArrayList<>();
            con = daoClass.Fun_DbCon();
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
                    ResultSet ResultSet = daoClass.Fun_Resultset(getCounterNoandNameQuery);
                    while (ResultSet.next()) {
                        arrayCraftGroup.add(ResultSet.getString("counter_name"));
                    }
                }

            }

            String sptdFromDate[] = paraFromDate.split("-");
            int frmDD = Integer.parseInt(sptdFromDate[2]);
            int frmMM = Integer.parseInt(sptdFromDate[1]);
            int frmYYYY = Integer.parseInt(sptdFromDate[0]);

            String[] SplitFromDate = paraFromDate.split("-");
            String[] SplitToDate = paraToDate.split("-");
            con = daoClass.Fun_DbCon();
            for (String lstCraftGroup : arrayCraftGroup) {
                for (int i = Integer.parseInt(SplitFromDate[2]); i <= Integer.parseInt(SplitToDate[2]); i++) {
                    //for (String lstCraftGroup : arrayCraftGroup){
                    String CDate = SplitToDate[0] + "-" + SplitToDate[1] + "-" + i;
                    paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + CDate.trim() + "',1))");
                    String query = "SELECT * FROM pos.report_summary where date_time between ? and ? and counter_name=?";

                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        query = query + " and paymentType=?";
                    }
                    ps = con.prepareStatement(query);
                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        ps.setString(1, CDate + "%");
                        ps.setString(2, paraToDate + "%");
                        ps.setString(3, lstCraftGroup.trim());
                        ps.setString(4, paraPaymentType);

                    } else {
                        ps.setString(1, CDate + "%");
                        ps.setString(2, paraToDate + "%");
                        ps.setString(3, lstCraftGroup.trim());
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

                        //Total value for Cdate
                        grossAmountT = grossAmountT + grossAmount;
                        discAmountT = discAmountT + discAmount;
                        netAmountT = netAmountT + netAmount;
                        vatAmountT = vatAmountT + vatAmount;

                        gstPercentageT = gstPercentageT + gstPercentage;
                        sgstAmountT = sgstAmountT + sgstAmount;
                        cgstAmountT = cgstAmountT + cgstAmount;

                        packAmountT = packAmountT + packAmount;
                       // cashBillAmountT = cashBillAmountT + cashBillAmount;
                        cashBillAmountT = cashBillAmountT + Math.round(cashBillAmount);

                        grossAmountGT = grossAmountGT + grossAmount;
                        discAmountGT = discAmountGT + discAmount;
                        netAmountGT = netAmountGT + netAmount;
                        vatAmountGT = vatAmountGT + vatAmount;

                        gstPercentageGT = gstPercentageGT + gstPercentage;
                        sgstAmountGT = sgstAmountGT + sgstAmount;
                        cgstAmountGT = cgstAmountGT + cgstAmount;

                        packAmountGT = packAmountGT + packAmount;
                        cashBillAmountGT = cashBillAmountGT + cashBillAmount;

                        grossAmount = 0f;
                        discAmount = 0f;
                        netAmount = 0f;
                        vatAmount = 0f;

                        gstPercentage = 0f;
                        sgstAmount = 0f;
                        cgstAmount = 0f;

                        packAmount = 0f;
                        cashBillAmount = 0f;
                    }

                    if (cashBillAmountT != 0f) {
                        CashLineItemDCSR cSRG = new CashLineItemDCSR();
                        cSRG.setDcsrDate(CDate);
                        cSRG.setCraftGroup(counter_type);
                        cSRG.setGrossAmountFloat(grossAmountT);
                        cSRG.setDiscAmountFloat(discAmountT);
                        cSRG.setNetAmountFloat(netAmountT);
                        cSRG.setVatAmountFloat(vatAmountT);

                        cSRG.setGstPercentage(gstPercentageT);
                        cSRG.setSgstAmountFloat(sgstAmountT);
                        cSRG.setCgstAmountFloat(cgstAmountT);

                        cSRG.setPackAmountFloat(packAmountT);
                        cSRG.setCashBillAmountFloat(Math.round(cashBillAmountT));

                        cSRG.setFromDD(frmDD);
                        cSRG.setFromMM(frmMM);
                        cSRG.setFromYY(frmYYYY);

                        summaryViewLinesDCSR.add(cSRG);
                        //Sub Total
                        grossAmountTS = grossAmountTS + grossAmountT;
                        discAmountTS = discAmountTS + discAmountT;
                        netAmountTS = netAmountTS + netAmountT;
                        vatAmountTS = vatAmountTS + vatAmountT;

                        gstPercentageTS = gstPercentageTS + gstPercentageT;
                        sgstAmountTS = sgstAmountTS + sgstAmountT;
                        cgstAmountTS = cgstAmountTS + cgstAmountT;

                        packAmountTS = packAmountTS + packAmountT;
                        cashBillAmountTS = cashBillAmountTS + cashBillAmountT;
                        grossAmountT = 0f;
                        discAmountT = 0f;
                        netAmountT = 0f;
                        vatAmountT = 0f;

                        gstPercentageT = 0f;
                        sgstAmountT = 0f;
                        cgstAmountT = 0f;

                        packAmountT = 0f;
                        cashBillAmountT = 0f;
                    }

                }
                if (!counter_type.equals("Empty")) {
                    if (!counter_type.equals(counter_type_s)) {
                        // System.out.println(counter_type+"-----"+counter_type_s);    
                        CashLineItemDCSR cSRGs = new CashLineItemDCSR();
                        cSRGs.setDcsrDate("Sub-Total");
                        //cSRGs.setCraftGroup("");
                        cSRGs.setGrossAmountFloat(grossAmountTS);
                        cSRGs.setDiscAmountFloat(discAmountTS);
                        cSRGs.setNetAmountFloat(netAmountTS);
                        cSRGs.setVatAmountFloat(vatAmountTS);

                        cSRGs.setGstPercentage(gstPercentageTS);
                        cSRGs.setSgstAmountFloat(sgstAmountTS);
                        cSRGs.setCgstAmountFloat(cgstAmountTS);

                        cSRGs.setPackAmountFloat(packAmountTS);
                        cSRGs.setCashBillAmountFloat(Math.round(cashBillAmountTS));
                        summaryViewLinesDCSR.add(cSRGs);
                        grossAmountTS = 0.0f;
                        discAmountTS = 0.0f;
                        netAmountTS = 0.0f;
                        vatAmountTS = 0.0f;

                        gstPercentageTS = 0.0f;
                        sgstAmountTS = 0.0f;
                        cgstAmountTS = 0.0f;

                        packAmountTS = 0.0f;
                        cashBillAmountTS = 0.0f;
                    }
                }

                counter_type_s = counter_type;

            }

            // Collections.sort(summaryViewLinesDCSR, CashLineItemDCSR.NameComparator3);
            CashLineItemDCSR cSRG = new CashLineItemDCSR();
            cSRG.setDcsrDate("Grand- Total");
            cSRG.setGrossAmountFloat(grossAmountGT);
            cSRG.setDiscAmountFloat(discAmountGT);
            cSRG.setNetAmountFloat(netAmountGT);
            cSRG.setVatAmountFloat(vatAmountGT);

            cSRG.setGstPercentage(gstPercentageGT);
            cSRG.setSgstAmountFloat(sgstAmountGT);
            cSRG.setCgstAmountFloat(cgstAmountGT);

            cSRG.setPackAmountFloat(packAmountGT);
            cSRG.setCashBillAmountFloat(Math.round(cashBillAmountGT));
            summaryViewLinesDCSR.add(cSRG);
        } catch (Exception e) {
            e.printStackTrace();
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
