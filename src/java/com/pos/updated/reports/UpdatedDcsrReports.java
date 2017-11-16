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

public class UpdatedDcsrReports extends ActionSupport {

    public static List<CashLineItemDCSR> newUpdatedDcsrReportPos(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId) throws SQLException {
        List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList<>();
        List<CashLineItemDCSR> counterDetails = new ArrayList<>();
        List<CashLineItemDCSR> dcsrEmp = new ArrayList();
        List<CashLineItemDCSR> dcsrEmpGT = new ArrayList();
        List<CashLineItemDCSR> FinalEmp = new ArrayList();
        List<CashLineItemDCSR> dcsrEmpAGrand = new ArrayList();
        CashLineItemDCSR c;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        DaoClass daoClass = new DaoClass();
        XmlToBean xTb = new XmlToBean();
        String empName = "";
        String lastCounter = "";

        Float grossAmount = 0f;
        Float discAmount = 0f;
        Float netAmount = 0f;
        Float vatAmount = 0f;

        Float gstPercentageAmount = 0f;
        Float sgstAmount = 0f;
        Float cgstAmount = 0f;

        Float packAmount = 0f;
        Float cashBillAmount = 0f;

        Float grossAmountL = 0f;
        Float discAmountL = 0f;
        Float netAmountL = 0f;
        Float vatAmountL = 0f;

        Float gstPercentageAmountL = 0f;
        Float sgstAmountL = 0f;
        Float cgstAmountL = 0f;

        Float packAmountL = 0f;
        Float cashBillAmountL = 0f;

        Float grossAmountS = 0f;
        Float discAmountS = 0f;
        Float netAmountS = 0f;
        Float vatAmountS = 0f;

        Float gstPercentageS = 0f;
        Float sgstAmountS = 0f;
        Float cgstAmountS = 0f;

        Float packAmountS = 0f;
        Float totalS = 0f;
        Float cashBillAmountFloat = 0f;

        Float empGrossAmtS = 0f;
        Float empdiscAmountS = 0f;
        Float empnetAmountS = 0f;
        Float empvatAmountS = 0f;

        Float empGstPercentageS = 0f;
        Float empSgstAmountS = 0f;
        Float empcgstAmountS = 0f;

        Float emppackAmountS = 0f;
        Float empcashBillAmountFloat = 0f;

        Float grossAmountGT = 0f;
        Float discAmountGT = 0f;
        Float netAmountGT = 0f;
        Float vatAmountGT = 0f;

        Float gstAmountGT = 0f;
        Float sgstAmountGT = 0f;
        Float cgstAmountGT = 0f;

        Float packAmountGT = 0f;
        Float cashBillAmountGT = 0f;

        Float empGrossAmtG = 0f;
        Float empdiscAmountG = 0f;
        Float empnetAmountG = 0f;
        Float empvatAmountG = 0f;

        Float empGstPercentageG = 0f;
        Float empSgstAmountG = 0f;
        Float empCgstAmountG = 0f;

        Float emppackAmountG = 0f;
        Float empcashBillAmountFloatG = 0f;

        Float cashBillAmountLWROF = 0f;
        Float totalSWROF = 0f;
        Float cashBillAmountWROFloat = 0f;
        Float empcashBillAmountWROFloat = 0f;
        Float cashBillAmountWROFGT = 0f;
        Float empcashBillAmountWROFloatG = 0f;

        try {
            String counterName = "";
            con = daoClass.Fun_DbCon();

            String sptdFromDate[] = paraFromDate.split("-");
            int frmDD = Integer.parseInt(sptdFromDate[2]);
            int frmMM = Integer.parseInt(sptdFromDate[1]);
            int frmYYYY = Integer.parseInt(sptdFromDate[0]);

            paraToDate = daoClass.Fun_Str("SELECT SQL_CACHE Date(ADDDATE('" + paraToDate.trim() + "',1))");

            String query = "SELECT * FROM pos.report_summary where date_time between ? and ? ";
            System.out.println(paraPaymentType + "=-=HELLO DCSR-=>" + query);
            if (!paraPaymentType.equalsIgnoreCase("CASH/CARD")) {
                query = query + " and paymentType=?";
            }
            query = query + "ORDER BY user_id";
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
                    grossAmount = grossAmount + Float.parseFloat(it.getPrcValue());
                    discAmount = discAmount + Float.parseFloat(it.getDiscount());
                    vatAmount = vatAmount + Float.parseFloat(it.getVatPer());
                }
                netAmount = grossAmount - discAmount;
                packAmount = rs.getFloat("pck_charge");

                cSR.setCashInvoiceNumber(rs.getString("sales_orderno") + "-" + rs.getString("paymentType"));
                cSR.setManualBillNo(rs.getString("manual_bill_no"));
                cSR.setGrossAmountFloat(grossAmount);
                cSR.setDiscAmountFloat(discAmount);
                cSR.setNetAmountFloat(netAmount);
                if (frmYYYY <= 2017 && (frmMM <= 6 || frmMM <= 06) && frmDD <= 30) {
                    // for VAT
                    cashBillAmount = netAmount + packAmount + vatAmount;
                    cSR.setVatAmountFloat(vatAmount);
                } else {
                    // for GST
                    gstPercentageAmount = gstPercentageAmount + rs.getFloat("gst_percentage");
                    sgstAmount = sgstAmount + rs.getFloat("sgst_amt");
                    cgstAmount = cgstAmount + rs.getFloat("cgst_amt");

                    cashBillAmount = netAmount + packAmount + sgstAmount + cgstAmount;
                    cSR.setGstPercentage(rs.getFloat("gst_percentage"));
                    cSR.setSgstAmountFloat(rs.getFloat("sgst_amt"));
                    cSR.setCgstAmountFloat(rs.getFloat("cgst_amt"));

                }

                cSR.setPackAmountFloat(packAmount);
                cSR.setDcsrDate(rs.getString("counter_type") + "-" + rs.getString("counter_name"));
                cSR.setFromDD(frmDD);
                cSR.setFromMM(frmMM);
                cSR.setFromYY(frmYYYY);
                cSR.setCashBillAmountFloat(Math.round(cashBillAmount));
                cSR.setCashBillAmountWROFloat((cashBillAmount));
                cSR.setEmpName(rs.getString("emp_name"));
                summaryViewLinesDCSR.add(cSR);

                grossAmountGT = grossAmountGT + grossAmount;
                discAmountGT = discAmountGT + discAmount;
                netAmountGT = netAmountGT + netAmount;
                vatAmountGT = vatAmountGT + vatAmount;

                gstAmountGT = gstAmountGT + gstPercentageAmount;    // for gst
                sgstAmountGT = sgstAmountGT + sgstAmount;           // for gst
                cgstAmountGT = cgstAmountGT + cgstAmount;           // for gst
                
                packAmountGT = packAmountGT + packAmount;
                cashBillAmountGT = cashBillAmountGT + Math.round(cashBillAmount);
                if (frmYYYY <= 2017 && (frmMM <= 6 || frmMM <= 06) && frmDD <= 30) {
                    // for VAT
                    cashBillAmountWROFGT = cashBillAmountWROFGT + netAmount + packAmount + vatAmount;
                } else {
                    // for gst
                    cashBillAmountWROFGT = cashBillAmountWROFGT + netAmount + packAmount + sgstAmountGT + cgstAmountGT;
                }
                grossAmount = 0f;
                discAmount = 0f;
                netAmount = 0f;
                vatAmount = 0f;

                gstPercentageAmount = 0f;   // for gst
                sgstAmount = 0f;          //for gst
                cgstAmount = 0f;          //for gst

                packAmount = 0f;
                cashBillAmount = 0f;

            }
            Collections.sort(summaryViewLinesDCSR, CashLineItemDCSR.NameComparator);
            String Counter = "empty";
            String EmpName = "empty";
            for (CashLineItemDCSR sd : summaryViewLinesDCSR) {

                cashBillAmountFloat = cashBillAmountFloat + sd.getCashBillAmountFloat();
                cashBillAmountWROFloat = cashBillAmountWROFloat + sd.getCashBillAmountWROFloat();
                //Adding Sub total
                if (!EmpName.equals("empty")) {
                    if (!EmpName.equals(sd.getEmpName())) {
                        CashLineItemDCSR subemp = new CashLineItemDCSR();

                        subemp.setDcsrDate("Empty");
                        subemp.setCashInvoiceNumber(EmpName);
                        subemp.setGrossAmountFloat(empGrossAmtS);
                        subemp.setDiscAmountFloat(empdiscAmountS);
                        subemp.setNetAmountFloat(empnetAmountS);
                        subemp.setVatAmountFloat(empvatAmountS);

                        subemp.setGstPercentage(empGstPercentageS);    // for gst
                        subemp.setSgstAmountFloat(empSgstAmountS);     // for gst
                        subemp.setCgstAmountFloat(empcgstAmountS);     // for gst

                        subemp.setPackAmountFloat(emppackAmountS);
                        subemp.setCashBillAmountFloat(empcashBillAmountFloat);
                        subemp.setCashBillAmountWROFloat(empcashBillAmountWROFloat);
                        dcsrEmp.add(subemp);
                        empGrossAmtS = 0f;
                        empdiscAmountS = 0f;
                        empnetAmountS = 0f;
                        empvatAmountS = 0f;

                        empGstPercentageS = 0f;  // for gst
                        empSgstAmountS = 0f;     // for gst
                        empcgstAmountS = 0f;     // for gst

                        emppackAmountS = 0f;
                        empcashBillAmountFloat = 0f;
                        empcashBillAmountWROFloat = 0f;
                    }
                }
                if (EmpName.equals(sd.getEmpName()) & !Counter.equals(sd.getDcsrDate())) {
                    CashLineItemDCSR subemp = new CashLineItemDCSR();

                    subemp.setDcsrDate("Empty");
                    subemp.setCashInvoiceNumber(EmpName);
                    subemp.setGrossAmountFloat(empGrossAmtS);
                    subemp.setDiscAmountFloat(empdiscAmountS);
                    subemp.setNetAmountFloat(empnetAmountS);
                    subemp.setVatAmountFloat(empvatAmountS);

                    subemp.setGstPercentage(empGstPercentageS);    //for gst
                    subemp.setSgstAmountFloat(empSgstAmountS);     //for gst
                    subemp.setCgstAmountFloat(empcgstAmountS);     //for gst

                    subemp.setPackAmountFloat(emppackAmountS);
                    subemp.setCashBillAmountFloat(empcashBillAmountFloat);
                    subemp.setCashBillAmountWROFloat(empcashBillAmountWROFloat);
                    dcsrEmp.add(subemp);
                    empGrossAmtS = 0f;
                    empdiscAmountS = 0f;
                    empnetAmountS = 0f;
                    empvatAmountS = 0f;

                    empGstPercentageS = 0f;    // for gst
                    empSgstAmountS = 0f;       // for gst
                    empcgstAmountS = 0f;       // for gst

                    emppackAmountS = 0f;
                    empcashBillAmountFloat = 0f;
                    empcashBillAmountWROFloat = 0f;
                }
                if (!sd.getDcsrDate().equals(Counter) & !Counter.isEmpty()) {

                    if (grossAmountS != 0) {
                        CashLineItemDCSR sub = new CashLineItemDCSR();
                        sub.setDcsrDate("Sub-Total");
                        sub.setGrossAmountFloat(grossAmountS);
                        sub.setDiscAmountFloat(discAmountS);
                        sub.setNetAmountFloat(netAmountS);
                        sub.setVatAmountFloat(vatAmountS);

                        sub.setGstPercentage(gstPercentageS);   //for gst
                        sub.setSgstAmountFloat(sgstAmountS);    //for gst
                        sub.setCgstAmountFloat(cgstAmountS);    //for gst

                        sub.setPackAmountFloat(packAmountS);
                        sub.setCashBillAmountFloat(totalS);
                        sub.setCashBillAmountWROFloat(totalSWROF);
                        dcsrEmpAGrand.add(sub);
                    }
                    grossAmountS = 0f;
                    discAmountS = 0f;
                    netAmountS = 0f;
                    vatAmountS = 0f;

                    gstPercentageS = 0f;    // for gst
                    sgstAmountS = 0f;       // for gst
                    cgstAmountS = 0f;       // for gst

                    packAmountS = 0f;
                    cashBillAmountFloat = 0f;
                    totalS = 0f;
                    totalSWROF = 0F;

                    for (CashLineItemDCSR cd : dcsrEmp) {

                        if (cd.getDcsrDate().equalsIgnoreCase("Empty")) {
                            cd.setDcsrDate("Cashier Sub-Total");
                            dcsrEmpAGrand.add(cd);
                        }
                    }

                    dcsrEmp.clear();

                }
                empGrossAmtS = empGrossAmtS + sd.getGrossAmountFloat();
                empdiscAmountS = empdiscAmountS + sd.getDiscAmountFloat();
                empnetAmountS = empnetAmountS + sd.getNetAmountFloat();
                empvatAmountS = empvatAmountS + sd.getVatAmountFloat();

                empGstPercentageS = empGstPercentageS + sd.getGstPercentage();       // for gst
                empSgstAmountS = empSgstAmountS + sd.getSgstAmountFloat();           // for gst
                empcgstAmountS = empcgstAmountS + sd.getCgstAmountFloat();           // for gst

                emppackAmountS = emppackAmountS + sd.getPackAmountFloat();
                empcashBillAmountFloat = empcashBillAmountFloat + sd.getCashBillAmountFloat();
                empcashBillAmountWROFloat = empcashBillAmountWROFloat + sd.getCashBillAmountWROFloat();
                CashLineItemDCSR emp = new CashLineItemDCSR();
                emp.setGrossAmountFloat(empGrossAmtS);
                emp.setDiscAmountFloat(empdiscAmountS);
                emp.setNetAmountFloat(empnetAmountS);
                emp.setVatAmountFloat(empvatAmountS);

                emp.setGstPercentage(empGstPercentageS);       // for gst
                emp.setSgstAmountFloat(empSgstAmountS);        // for gst
                emp.setCgstAmountFloat(empcgstAmountS);        // for gst

                emp.setPackAmountFloat(emppackAmountS);
                emp.setCashBillAmountFloat(empcashBillAmountFloat);
                emp.setDcsrDate(sd.getEmpName());
                emp.setCashInvoiceNumber(sd.getDcsrDate());
                emp.setCashBillAmountWROFloat(empcashBillAmountWROFloat);
                dcsrEmp.add(emp);
                //Employee Total

                //Subtotal craft wise
                grossAmountS = grossAmountS + sd.getGrossAmountFloat();
                discAmountS = discAmountS + sd.getDiscAmountFloat();
                netAmountS = netAmountS + sd.getNetAmountFloat();
                vatAmountS = vatAmountS + sd.getVatAmountFloat();

                gstPercentageS = gstPercentageS + sd.getGstPercentage();    // for gst
                sgstAmountS = sgstAmountS + sd.getSgstAmountFloat();        // for gst
                cgstAmountS = cgstAmountS + sd.getCgstAmountFloat();        // for gst

                packAmountS = packAmountS + sd.getPackAmountFloat();
                totalS = totalS + sd.getCashBillAmountFloat();
                totalSWROF = totalSWROF + sd.getCashBillAmountWROFloat();

                dcsrEmpAGrand.add(sd);
                Counter = sd.getDcsrDate();
                EmpName = sd.getEmpName();
            }

            CashLineItemDCSR sub = new CashLineItemDCSR();
            sub.setDcsrDate("Sub-Total");
            sub.setGrossAmountFloat(grossAmountS);
            sub.setDiscAmountFloat(discAmountS);
            sub.setNetAmountFloat(netAmountS);
            sub.setVatAmountFloat(vatAmountS);

            sub.setGstPercentage(gstPercentageS);   // for gst
            sub.setSgstAmountFloat(sgstAmountS);    // for gst
            sub.setCgstAmountFloat(cgstAmountS);    // for gst

            sub.setPackAmountFloat(packAmountS);
            sub.setCashBillAmountFloat(totalS);
            sub.setCashBillAmountWROFloat(totalSWROF);
            dcsrEmpAGrand.add(sub);

            String EmployeeName = "";
            for (CashLineItemDCSR d : summaryViewLinesDCSR) {

                if (d.getDcsrDate().equals(summaryViewLinesDCSR.get(summaryViewLinesDCSR.size() - 1).getDcsrDate())) {

                    if (!EmployeeName.equals("")) {
                        if (!EmployeeName.equals(d.getEmpName())) {

                            CashLineItemDCSR lastemp = new CashLineItemDCSR();
                            lastemp.setDcsrDate("Cashier Sub-Total");
                            lastemp.setCashInvoiceNumber(EmployeeName);
                            lastemp.setGrossAmountFloat(grossAmountL);
                            lastemp.setDiscAmountFloat(discAmountL);
                            lastemp.setNetAmountFloat(netAmountL);
                            lastemp.setVatAmountFloat(vatAmountL);

                            lastemp.setGstPercentage(gstPercentageAmountL);    //for gst
                            lastemp.setSgstAmountFloat(sgstAmountL);           //for gst
                            lastemp.setCgstAmountFloat(cgstAmountL);           //for gst

                            lastemp.setPackAmountFloat(packAmountL);
                            lastemp.setCashBillAmountFloat(cashBillAmountL);
                            lastemp.setCashBillAmountWROFloat(cashBillAmountLWROF);
                            dcsrEmpAGrand.add(lastemp);
                            grossAmountL = 0f;
                            discAmountL = 0f;
                            netAmountL = 0f;
                            vatAmountL = 0f;

                            gstPercentageAmountL = 0f; // for gst
                            sgstAmountL = 0f;          // for gst
                            cgstAmountL = 0f;          // for gst

                            packAmountL = 0f;
                            cashBillAmountL = 0f;
                            cashBillAmountLWROF = 0f;

                        }

                    }

                    grossAmountL = grossAmountL + d.getGrossAmountFloat();
                    discAmountL = discAmountL + d.getDiscAmountFloat();
                    netAmountL = netAmountL + d.getNetAmountFloat();
                    vatAmountL = vatAmountL + d.getVatAmountFloat();

                    gstPercentageAmountL = gstPercentageAmountL + d.getGstPercentage();   //for gst
                    sgstAmountL = sgstAmountL + d.getSgstAmountFloat();     //for gst
                    cgstAmountL = cgstAmountL + d.getCgstAmountFloat();     //for gst

                    packAmountL = packAmountL + d.getPackAmountFloat();
                    cashBillAmountL = cashBillAmountL + d.getCashBillAmountFloat();
                    cashBillAmountLWROF = cashBillAmountLWROF + d.getCashBillAmountWROFloat();
                    EmployeeName = d.getEmpName();

                }

            }

            CashLineItemDCSR lastemp = new CashLineItemDCSR();
            lastemp.setDcsrDate("Cashier Sub-Total");
            lastemp.setCashInvoiceNumber(EmployeeName);
            lastemp.setGrossAmountFloat(grossAmountL);
            lastemp.setDiscAmountFloat(discAmountL);
            lastemp.setNetAmountFloat(netAmountL);
            lastemp.setVatAmountFloat(vatAmountL);
            /*----------------------------------*/
            lastemp.setGstPercentage(gstPercentageAmountL);    // for Gst
            lastemp.setSgstAmountFloat(sgstAmountL);    // for Gst
            lastemp.setCgstAmountFloat(cgstAmountL);    // for Gst

            lastemp.setPackAmountFloat(packAmountL);
            lastemp.setCashBillAmountFloat(cashBillAmountL);
            lastemp.setCashBillAmountWROFloat(cashBillAmountLWROF);
            dcsrEmpAGrand.add(lastemp);

            for (CashLineItemDCSR empGT : dcsrEmpAGrand) {
                if (empGT.getDcsrDate().equals("Cashier Sub-Total")) {

                    dcsrEmpGT.add(empGT);
                }
            }

            CashLineItemDCSR cSRGT = new CashLineItemDCSR();
            cSRGT.setDcsrDate("Grand- Total");
            cSRGT.setGrossAmountFloat(grossAmountGT);
           
            cSRGT.setDiscAmountFloat(discAmountGT);
            cSRGT.setNetAmountFloat(netAmountGT);
            cSRGT.setVatAmountFloat(vatAmountGT);

            cSRGT.setGstPercentage(gstAmountGT);     //for GST
            cSRGT.setSgstAmountFloat(sgstAmountGT);  //for GST
            
            cSRGT.setCgstAmountFloat(cgstAmountGT);  //for GST
            
            cSRGT.setPackAmountFloat(packAmountGT);
            cSRGT.setCashBillAmountFloat(Math.round(cashBillAmountGT));
            cSRGT.setCashBillAmountWROFloat(cashBillAmountWROFGT);
            dcsrEmpAGrand.add(cSRGT);

            Collections.sort(dcsrEmpGT, CashLineItemDCSR.NameComparator2);
            String tempEmp = "empty";
            for (CashLineItemDCSR li : dcsrEmpGT) {

                if (!tempEmp.equals("empty")) {
                    if (!tempEmp.equals(li.getCashInvoiceNumber())) {

                        CashLineItemDCSR gtE = new CashLineItemDCSR();

                        gtE.setDcsrDate("Cashier Grand-Total");
                        gtE.setCashInvoiceNumber(tempEmp);

                        gtE.setGrossAmountFloat(empGrossAmtG);
                        gtE.setDiscAmountFloat(empdiscAmountG);
                        gtE.setNetAmountFloat(empnetAmountG);
                        gtE.setVatAmountFloat(empvatAmountG);

                        gtE.setGstPercentage(empGstPercentageG);    // for gst
                        gtE.setSgstAmountFloat(empSgstAmountG);     // for gst
                        gtE.setCgstAmountFloat(empCgstAmountG);     // for gst

                        gtE.setPackAmountFloat(emppackAmountG);
                        gtE.setCashBillAmountFloat(empcashBillAmountFloatG);
                        gtE.setCashBillAmountWROFloat(empcashBillAmountWROFloatG);
                        dcsrEmpAGrand.add(gtE);
                        empGrossAmtG = 0f;
                        empdiscAmountG = 0f;
                        empnetAmountG = 0f;
                        empvatAmountG = 0f;

                        empGstPercentageG = 0f;  // for gst
                        empSgstAmountG = 0f;     // for gst
                        empCgstAmountG = 0f;     // for gst

                        emppackAmountG = 0f;
                        empcashBillAmountFloatG = 0f;
                        empcashBillAmountWROFloatG = 0f;
                    }
                }

                empGrossAmtG = empGrossAmtG + li.getGrossAmountFloat();
                empdiscAmountG = empdiscAmountG + li.getDiscAmountFloat();
                empnetAmountG = empnetAmountG + li.getNetAmountFloat();
                empvatAmountG = empvatAmountG + li.getVatAmountFloat();

                empGstPercentageG = empGstPercentageG + li.getGstPercentage();     // for gst
                empSgstAmountG = empSgstAmountG + li.getSgstAmountFloat();         // for gst
                empCgstAmountG = empCgstAmountG + li.getCgstAmountFloat();         // for gst

                emppackAmountG = emppackAmountG + li.getPackAmountFloat();
                empcashBillAmountFloatG = empcashBillAmountFloatG + li.getCashBillAmountFloat();
                empcashBillAmountWROFloatG = empcashBillAmountWROFloatG + li.getCashBillAmountWROFloat();

                tempEmp = li.getCashInvoiceNumber();
            }
            CashLineItemDCSR gtE = new CashLineItemDCSR();
            gtE.setDcsrDate("Cashier Grand-Total");
            gtE.setCashInvoiceNumber(tempEmp);
            gtE.setGrossAmountFloat(empGrossAmtG);
            gtE.setDiscAmountFloat(empdiscAmountG);
            gtE.setNetAmountFloat(empnetAmountG);
            gtE.setVatAmountFloat(empvatAmountG);

            gtE.setGstPercentage(empGstPercentageG);       // for gst
            gtE.setSgstAmountFloat(empSgstAmountG);        // for gst
            gtE.setCgstAmountFloat(empCgstAmountG);        // for gst

            gtE.setPackAmountFloat(emppackAmountG);
            gtE.setCashBillAmountFloat(empcashBillAmountFloatG);
            gtE.setCashBillAmountWROFloat(empcashBillAmountWROFloatG);
            dcsrEmpAGrand.add(gtE);
            empGrossAmtG = 0f;
            empdiscAmountG = 0f;
            empnetAmountG = 0f;
            empvatAmountG = 0f;

            empGstPercentageG = 0f;  // for gst
            empSgstAmountG = 0f;     // for gst
            empCgstAmountG = 0f;     // for gst

            emppackAmountG = 0f;
            empcashBillAmountFloatG = 0f;
            empcashBillAmountWROFloatG = 0f;

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error..!" + e.getMessage());
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

        return dcsrEmpAGrand;
    }

}
