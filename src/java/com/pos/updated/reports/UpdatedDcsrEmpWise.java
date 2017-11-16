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
import java.util.Collections;
import java.util.List;

public class UpdatedDcsrEmpWise extends ActionSupport {

    public static List<CashLineItemDCSR> updatedDcsrEmpWiseNewReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId) {
        List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList();
        List<String> user_id = new ArrayList();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        XmlToBean xTb = new XmlToBean();
        DaoClass daoClass = new DaoClass();
        Float grossAmount = 0f;
        Float discAmount = 0f;
        Float netAmount = 0f;
        Float vatAmount = 0f;
        Float packAmount = 0f;
        Float cashBillAmount = 0f;

        Float grossAmountT = 0f;
        Float discAmountT = 0f;
        Float netAmountT = 0f;
        Float vatAmountT = 0f;
        Float packAmountT = 0f;
        Float cashBillAmountT = 0f;

        Float grossAmountGT = 0f;
        Float discAmountGT = 0f;
        Float netAmountGT = 0f;
        Float vatAmountGT = 0f;
        Float packAmountGT = 0f;
        Float cashBillAmountGT = 0f;
        
        Float grossAmountTS = 0f;
        Float discAmountTS = 0f;
        Float netAmountTS = 0f;
        Float vatAmountTS = 0f;
        Float packAmountTS = 0f;
        Float cashBillAmountTS = 0f;
        try {

            con = daoClass.Fun_DbCon();
            String CDate = paraToDate;
           // paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");

            
            
            //only fir testing
            String query = "SELECT distinct user_id from pos.report_summary where date_time between ? and ?";
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
                user_id.add(rs.getString("user_id"));
            }
            String[] SplitFromDate = paraFromDate.split("-");
            String[] SplitToDate = paraToDate.split("-");
//            if(Integer.parseInt((SplitToDate[1])) > Integer.parseInt(SplitFromDate[1])){
//                SplitToDate[2]=CDate.split("-")[2];
//                        
//            }
            String emp_name = "";
            String emp_name_s = "Empty";
            for (String u : user_id) {

                for (int i = Integer.parseInt(SplitFromDate[2]); i <= Integer.parseInt(SplitToDate[2]); i++) {
                    CDate = SplitToDate[0] + "-" + SplitToDate[1] + "-" + i;
                    String toCDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + CDate.trim() + "',1))");;

                    String userid_query = "select *from pos.report_summary where user_id=? and date_time between ? and ? ";
                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        userid_query = userid_query + " and paymentType=?";
                    }

                    ps = con.prepareStatement(userid_query);

                    if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                        ps.setString(1, u);
                        ps.setString(2, CDate + "%");
                        ps.setString(3, toCDate + "%");
                        ps.setString(4, paraPaymentType);
                    } else {
                        ps.setString(1, u);
                        ps.setString(2, CDate + "%");
                        ps.setString(3, toCDate + "%");

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
                        emp_name = rs.getString("emp_name");
                        netAmount = grossAmount - discAmount;
                        packAmount = rs.getFloat("pck_charge");
                        cashBillAmount = netAmount + packAmount + vatAmount;
                        grossAmountT = grossAmountT + grossAmount;
                        discAmountT = discAmountT + discAmount;
                        netAmountT = netAmountT + netAmount;
                        vatAmountT = vatAmountT + vatAmount;
                        packAmountT = packAmountT + packAmount;
                        cashBillAmountT = cashBillAmountT + Math.round(cashBillAmount);

                        //Grand Total
                        grossAmountGT = grossAmountGT + grossAmount;
                        discAmountGT = discAmountGT + discAmount;
                        netAmountGT = netAmountGT + netAmount;
                        vatAmountGT = vatAmountGT + vatAmount;
                        packAmountGT = packAmountGT + packAmount;
                        cashBillAmountGT = cashBillAmountGT + cashBillAmount;

                        grossAmount = 0f;
                        discAmount = 0f;
                        netAmount = 0f;
                        vatAmount = 0f;
                        packAmount = 0f;
                        cashBillAmount = 0f;
                    }
                    if (cashBillAmountT != 0f) {
                        CashLineItemDCSR cSRG = new CashLineItemDCSR();
                        cSRG.setDcsrDate(CDate);
                        cSRG.setEmpName(emp_name);
                        cSRG.setGrossAmountFloat(grossAmountT);
                        cSRG.setDiscAmountFloat(discAmountT);
                        cSRG.setNetAmountFloat(netAmountT);
                        cSRG.setVatAmountFloat(vatAmountT);
                        cSRG.setPackAmountFloat(packAmountT);
                        cSRG.setCashBillAmountFloat(Math.round(cashBillAmountT));
                        summaryViewLinesDCSR.add(cSRG);
                        //Sub Total
                          grossAmountTS=grossAmountTS+grossAmountT;
                          discAmountTS=discAmountTS+discAmountT;
                          netAmountTS=netAmountTS+netAmountT;
                          vatAmountTS=vatAmountTS+vatAmountT;
                          packAmountTS=packAmountTS+packAmountT;
                          cashBillAmountTS=cashBillAmountTS+cashBillAmountT;
                        grossAmountT = 0f;
                        discAmountT = 0f;
                        netAmountT = 0f;
                        vatAmountT = 0f;
                        packAmountT = 0f;
                        cashBillAmountT = 0f;
                    }
                }
                
                if(!emp_name.equals("Empty")){
                             if(!emp_name.equals(emp_name_s)){
                        CashLineItemDCSR cSRGs = new CashLineItemDCSR();
                        cSRGs.setDcsrDate("");
                        cSRGs.setEmpName("Sub-total");
                        cSRGs.setGrossAmountFloat(grossAmountTS);
                        cSRGs.setDiscAmountFloat(discAmountTS);
                        cSRGs.setNetAmountFloat(netAmountTS);
                        cSRGs.setVatAmountFloat(vatAmountTS);
                        cSRGs.setPackAmountFloat(packAmountTS);
                        cSRGs.setCashBillAmountFloat(Math.round(cashBillAmountTS));
                        summaryViewLinesDCSR.add(cSRGs);
                        grossAmountTS=0.0f;
                        discAmountTS =0.0f;
                        netAmountTS =0.0f;
                        vatAmountTS =0.0f;
                        packAmountTS =0.0f;
                        cashBillAmountTS =0.0f;
                             }

            }
                  emp_name_s=emp_name;
            }
            CashLineItemDCSR cSRG = new CashLineItemDCSR();
            cSRG.setDcsrDate(" ");
            cSRG.setEmpName("Grand-Total");
            cSRG.setGrossAmountFloat(grossAmountGT);
            cSRG.setDiscAmountFloat(discAmountGT);
            cSRG.setNetAmountFloat(netAmountGT);
            cSRG.setVatAmountFloat(vatAmountGT);
            cSRG.setPackAmountFloat(packAmountGT);
            cSRG.setCashBillAmountFloat(Math.round(cashBillAmountGT));
            summaryViewLinesDCSR.add(cSRG);

        } catch (Exception ex) {
            System.out.println("Exception in displaying DCSR: " + ex.getMessage());
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
