package com.report.pdf;

import com.DAO.DaoClass;

import com.itextpdf.text.*;
import com.itextpdf.text.Font.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opensymphony.xwork2.ActionSupport;
import com.to.CashBillUserwiseLineItemDCSR;
import com.to.CashHeaderDCSR;
import com.to.CashLineItemDCSR;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.struts2.ServletActionContext;
/*@author pranesh*/

public class Dcsr extends ActionSupport {

    private String dcsrFrmDate;
    private String dcsrToDate;
    private String dcsrReportType;
    private String dcsrSessionId;
    private String userIdDate;
    String reportName;
    String pdfFileName;

    Date processedDate = new Date();
    SimpleDateFormat dateformat = new SimpleDateFormat("ddMMyyHHmmss");

    public Dcsr() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String GenerateDcsrPdf() {
        DaoClass daoClass = new DaoClass();
        int columnCount;
        ResultSet dcsrHeaderRs = null;
        ResultSet dcsrLinesRs = null;
        ResultSetMetaData dcsrLinesRSMD = null;
        try {
            System.out.println("From    : " + dcsrFrmDate);
            System.out.println("To      : " + dcsrToDate);
            System.out.println("Report  :" + dcsrReportType);
            System.out.println("Session :" + dcsrSessionId);
            String counterName = null;

            Font font6 = FontFactory.getFont("Verdana", 5);
            Font font6b = FontFactory.getFont("Verdana", 4, 3, BaseColor.BLACK);
            Font font8 = FontFactory.getFont("Verdana", 4, 1);
            Font font10 = FontFactory.getFont("Verdana", 10, 3, BaseColor.DARK_GRAY);
            Font font14 = FontFactory.getFont("Verdana", 14, 3, BaseColor.DARK_GRAY);
//special font sizes
            Font bfBold12 = new Font(FontFamily.TIMES_ROMAN, 5, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(FontFamily.TIMES_ROMAN, 5);
            pdfFileName = SetFilename();
            pdfFileName = "DSCR" + pdfFileName + "." + "pdf";
            String servletActionContextPath = ServletActionContext.getServletContext().getRealPath("/Downloads");

            File theFile = new File(servletActionContextPath, pdfFileName);
            theFile.createNewFile();

            Document pdfDoc = new Document(PageSize.A4_LANDSCAPE, 10, 10, 20, 20);
            PdfWriter.getInstance(pdfDoc, new FileOutputStream(theFile));
            pdfDoc.open();

            String getCounterNoandNameQuery = "SELECT distinct branch_counter.counter_no_legacy, branch_counter.counter FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)CROSS JOIN pos.header header INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)WHERE(cashbill_header_master.paymentType ='" + dcsrReportType.trim() + "')AND(header.cancelFlag = 'N')AND(cashbill_lineitem_master.cashbill_dateTime  between '" + dcsrFrmDate.trim() + "' AND '" + dcsrToDate.trim() + "')";
            dcsrHeaderRs = daoClass.Fun_Resultset(getCounterNoandNameQuery);
            while (dcsrHeaderRs.next()) {
                CashHeaderDCSR dCSRto = new CashHeaderDCSR();
                List<CashLineItemDCSR> summaryViewLinesDCSR = new ArrayList();
                List<CashBillUserwiseLineItemDCSR> cSRs = new ArrayList();
                counterName = dcsrHeaderRs.getString("counter");
                if (dcsrReportType.equalsIgnoreCase("CRC")) {
                    dCSRto.setPaymentType("CARD");
                } else {
                    dCSRto.setPaymentType(dcsrReportType);
                }

                dCSRto.setHeaderDetail(dcsrHeaderRs.getString("counter"));
                dCSRto.setHeaderId(dcsrHeaderRs.getString("counter_no_legacy"));
                String getBillNoandAmtQuery = "SELECT cashbill_lineitem_master.counterbill_no,SUM(lineitem.prc_value) as Gross_Amount,SUM(lineitem.discount_value) as Disc_Amount,SUM(lineitem.prc_value-lineitem.discount_value) as Net_Amount,SUM(lineitem.vat_value) as Vat_Amount,lineitem.pck_charge as Pack_Amount,header.net_amt+header.pck_charge as cashbill_amt,emp_master.emp_name FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE(cashbill_header_master.paymentType ='" + dcsrReportType.trim() + "')AND(header.cancelFlag='N')AND(cashbill_lineitem_master.cashbill_dateTime between '" + dcsrFrmDate.trim() + "' AND '" + dcsrToDate.trim() + "' AND branch_Counter.counter='" + counterName.trim() + "')group by cashbill_lineitem_master.counterbill_no order by cashbill_lineitem_master.counterbill_no asc";
                System.out.println("" + getBillNoandAmtQuery);
                dcsrLinesRs = daoClass.Fun_Resultset(getBillNoandAmtQuery);
                dcsrLinesRSMD = dcsrLinesRs.getMetaData();
                columnCount = dcsrLinesRSMD.getColumnCount();
                PdfPTable tbl = new PdfPTable(columnCount);
                tbl.setWidthPercentage(100f);

                //insert column headings
                insertCell(tbl, "INVOICE-NO", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(tbl, "GROSS-AMT", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(tbl, "DISC-AMT", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(tbl, "NET-AMT", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(tbl, "VAT-AMT", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(tbl, "PACK-AMT", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(tbl, "TOTAL-AMT", Element.ALIGN_CENTER, 1, bfBold12);
                insertCell(tbl, "CASHIER", Element.ALIGN_CENTER, 1, bfBold12);
                tbl.setHeaderRows(1);

                //insert an empty row
                //insertCell(tbl, "", Element.ALIGN_LEFT, 4, bfBold12);
//create section heading by cell merging
                insertCell(tbl, "New York Orders ...", Element.ALIGN_LEFT, 4, bfBold12);
                double orderTotal, total = 0;

                pdfDoc.add(tbl);
                pdfDoc.close();
            }

        } catch (Exception ex) {
            System.out.println("Exception In Generating DCSR PDF : " + ex.getMessage());
        } finally {
            daoClass.closeResultSet(dcsrHeaderRs);
            daoClass.closeResultSet(dcsrLinesRs);
        }
        return SUCCESS;
    }

    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }

    public String SetFilename() {
        try {
            userIdDate = dcsrSessionId + "" + dateformat.format(processedDate);
            String[] fmt = userIdDate.split(" +");
            reportName = fmt[0].trim();
        } catch (Exception ex) {
            System.out.println("Excception In Setting FileName :  " + ex);
        }
        return reportName;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public void setPdfFileName(String pdfFileName) {
        this.pdfFileName = pdfFileName;
    }

    public String getDcsrFrmDate() {
        return dcsrFrmDate;
    }

    public void setDcsrFrmDate(String dcsrFrmDate) {
        this.dcsrFrmDate = dcsrFrmDate;
    }

    public String getDcsrToDate() {
        return dcsrToDate;
    }

    public void setDcsrToDate(String dcsrToDate) {
        this.dcsrToDate = dcsrToDate;
    }

    public String getDcsrReportType() {
        return dcsrReportType;
    }

    public void setDcsrReportType(String dcsrReportType) {
        this.dcsrReportType = dcsrReportType;
    }

    public String getDcsrSessionId() {
        return dcsrSessionId;
    }

    public void setDcsrSessionId(String dcsrSessionId) {
        this.dcsrSessionId = dcsrSessionId;
    }

}
