package com.pos.updated.reports;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import com.schudeler.LineItem;
import com.schudeler.LineItems;
import com.schudeler.XmlToBean;
import com.to.CashLineItemDCSR;
import com.to.CashLineItemDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdatedCasherWise extends ActionSupport {

    public static List<CashLineItemDTO> updatedEmpWiseNewReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId) {
        List<CashLineItemDTO> summaryViewLines = new ArrayList();
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
        Float cashBillAmountWROFT = 0f;
        Float cashBillAmountWROFGT = 0f;
        Float cashBillAmountWROF = 0f;
        try {
            String craftGroupName = "";
            String counter_type = "";
            con = daoClass.Fun_DbCon();
            String CDate = paraToDate;
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            String query = "SELECT distinct user_id from pos.report_summary where date_time between ? and ?";
            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                query = query + " and paymentType=?";
            }
            query = query + " and user_id=?";
            ps = con.prepareStatement(query);
            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                ps.setString(1, paraFromDate + "%");
                ps.setString(2, paraToDate + "%");
                ps.setString(3, paraPaymentType);
                ps.setString(4, paraLoggedSessionId);
            } else {
                ps.setString(1, paraFromDate + "%");
                ps.setString(2, paraToDate + "%");
                ps.setString(3, paraLoggedSessionId);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                user_id.add(rs.getString("user_id"));
            }
            String[] SplitFromDate = paraFromDate.split("-");
            String[] SplitToDate = paraToDate.split("-");
            String empName = "";

            for (String u : user_id) {
                String toCDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + CDate.trim() + "',1))");
                String userid_query = "select *from pos.report_summary where user_id=? and date_time between ? and ? ";
                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    userid_query = userid_query + " and paymentType=?";
                }
                ps = con.prepareStatement(userid_query);

                if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                    ps.setString(1, u);
                    ps.setString(2, paraFromDate + "%");
                    ps.setString(3, toCDate + "%");
                    ps.setString(4, paraPaymentType);
                } else {
                    ps.setString(1, u);
                    ps.setString(2, paraFromDate + "%");
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
                    packAmount = rs.getFloat("pck_charge");
                    netAmount = grossAmount - discAmount;
                    cashBillAmount = netAmount + packAmount + vatAmount;
                    cashBillAmountWROFT = cashBillAmountWROFT + cashBillAmount;
                    cashBillAmountWROFGT = cashBillAmountWROFGT + cashBillAmount;
                    cashBillAmountT = cashBillAmountT + cashBillAmount;
                    counter_type = rs.getString("counter_name");
                    System.out.println("counter_type" + counter_type);

                    //Grand Total
                    cashBillAmountGT = cashBillAmountGT + cashBillAmount;
                    empName = rs.getString("emp_name");
                    CashLineItemDTO itemDTO = new CashLineItemDTO();
                    itemDTO.setCounterNoName(empName);
                    itemDTO.setCraftGroupName(rs.getString("counter_name"));
                    itemDTO.setCashInvoiceNumber(rs.getLong("sales_orderno"));
                    itemDTO.setManualBillNumber(rs.getString("manual_bill_no"));
                    itemDTO.setCashBillAmountFloat((float) Math.round(cashBillAmount));
                    itemDTO.setCashBillAmountWROFloat(cashBillAmount);
                    String[] date_time = rs.getString("date_time").split(" ");

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    Date date = sdf.parse(date_time[0]);

                    sdf = new SimpleDateFormat("dd-MM-yyyy");
                    itemDTO.setProcessedDate(sdf.format(date));

                    summaryViewLines.add(itemDTO);
                    grossAmount = 0f;
                    discAmount = 0f;
                    netAmount = 0f;
                    vatAmount = 0f;
                    packAmount = 0f;
                    cashBillAmount = 0f;
                    cashBillAmountWROF = 0f;
                }

                //ST
                CashLineItemDTO cSRG = new CashLineItemDTO();
                cSRG.setCounterNoName("Sub-Total");
                cSRG.setCashInvoiceNumber(0L);
                cSRG.setManualBillNumber("");
                cSRG.setCashBillAmountFloat((float) (Math.round(cashBillAmountT)));
                cSRG.setCashBillAmountWROFloat(cashBillAmountWROFT);
                cSRG.setProcessedDate(" ");
                summaryViewLines.add(cSRG);
                cashBillAmountT = 0f;
                cashBillAmountWROFT = 0f;
            }
            //GT
            CashLineItemDTO cSRG = new CashLineItemDTO();

            cSRG.setCounterNoName("Grand-Total");
            cSRG.setCashInvoiceNumber(0L);
            cSRG.setManualBillNumber("");
            cSRG.setCashBillAmountFloat((float) (Math.round(cashBillAmountGT)));
            cSRG.setCashBillAmountWROFloat(cashBillAmountWROFGT);
            cSRG.setProcessedDate(" ");
            summaryViewLines.add(cSRG);

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

        return summaryViewLines;
    }

}
