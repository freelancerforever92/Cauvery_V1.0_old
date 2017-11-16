package com.Action.CounterReport;

import static com.Action.CashBill.DailyCashReport.getLocalPath;
import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

/*@author pranesh*/
public class DailyCounterTransExcel extends ActionSupport implements SessionAware {

    int rtn = 0;
    Map session;
    int columnCount;
    String counterToDate;
    String counterFromDate;
    int page_index = 4;
    String CouterReportXcelFileName;
    String CouterReportXcelFilePath;
    String cancelledOptionParameter;
    String counterTranscationToExcel = null;

    ResultSet resultSet = null;
    ResultSet counterNosRs = null;
    ResultSet craftGroupsRs = null;
    ResultSet counterTransXcelRs = null;
    String counterXcelFileName = null;
    DaoClass daoClass = new DaoClass();
    ResultSetMetaData resultSetmd = null;
    Calendar now = Calendar.getInstance();

    private double totVatAmt;
    private double totPckAmt;
    private double totNetAmt;
    private double totDiscAmt;
    private double totSalesAmt;
    private double totGrossAmt;

    java.util.Date date = new java.util.Date();
    DecimalFormat df = new DecimalFormat("#0.00");
    ArrayList<String> craftGroupsLst = new ArrayList<String>();
    ArrayList<String> counterCraftNosLst = new ArrayList<String>();
    SimpleDateFormat datformat = new SimpleDateFormat("ddMMyyHHmmss");
    SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private static final ResourceBundle rb = ResourceBundle.getBundle("dbConnection");
    String dat = now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR);

    public DailyCounterTransExcel() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public static String getLocalPath() {
        return rb.getString("local.temp.path");
    }

    public String exportCounterTransToExcel() {
        String csrTotalQuery = "";
        try {
            int[] columnWidths = new int[]{3800, 3800, 3800, 3800, 3800, 3800, 3800, 3800, 3800, 3800, 3800, 3800, 3800};
            HSSFWorkbook wb2 = new HSSFWorkbook();
            HSSFSheet sheet = wb2.createSheet(" ");
            //HSSFRow rowhead = sheet.createRow((short) 4);
            HSSFRow rowhead = sheet.createRow((short) 3);

            Row row1 = sheet.createRow(1);
            HSSFCellStyle cellStyle = setHeaderStyle(wb2, row1, (short) 1, CellStyle.ALIGN_CENTER_SELECTION, CellStyle.VERTICAL_BOTTOM);
            HSSFCellStyle cellStyle2 = setHeaderStyle(wb2, row1, (short) 1, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_BOTTOM);

            HSSFRow title, title2;
            title = sheet.createRow((short) 0);
            title2 = sheet.createRow((short) 1);
            HSSFCell vCel1, vCel2, vCel3, vCel4;
            vCel1 = title.createCell(3);
            vCel1.setCellStyle(cellStyle);

            vCel2 = title2.createCell(2);
            vCel2.setCellStyle(cellStyle2);
            vCel2.setCellValue(new HSSFRichTextString("From date :    " + counterFromDate.trim()));

            vCel3 = title2.createCell(4);
            vCel3.setCellStyle(cellStyle);
            vCel3.setCellValue(new HSSFRichTextString("To Date   :    " + counterToDate.trim()));

            vCel4 = title2.createCell(6);
            vCel4.setCellStyle(cellStyle);
            if (cancelledOptionParameter.equalsIgnoreCase("Select")) {
                vCel4.setCellValue(new HSSFRichTextString("[ ALL ]"));
            } else if (cancelledOptionParameter.equalsIgnoreCase("X")) {
                vCel4.setCellValue(new HSSFRichTextString("[ CANCELLED BILLS ]"));
            } else if (cancelledOptionParameter.equalsIgnoreCase("N")) {
                vCel4.setCellValue(new HSSFRichTextString("[ PROCESSED BILLS ]"));
            }

            /*
             TEMP-BLOCKED 31-01-2015-PRANESH
             if (cancelledOptionParameter.equalsIgnoreCase("Select")) {
             //,lineitem.discount_percentage,lineitem.discount_value,lineitem.vat_percentage,lineitem.vat_value,lineitem.pck_charge
             vCel1.setCellValue(new HSSFRichTextString("   Counter Report Including Cancellation"));
             counterTranscationToExcel = "SELECT header.sales_orderno,lineitem.item,lineitem.material,lineitem.materialCraftGroup,lineitem.discount_percentage,lineitem.discount_value,lineitem.vat_percentage,lineitem.vat_value,lineitem.pck_charge,lineitem.calcu_value,header.emp_id,header.show_room,DATE_FORMAT(header.date_time,'%d-%m-%Y %h:%m:%s')as date_time FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + counterFromDate.trim() + "%')";
             } else if (cancelledOptionParameter.equalsIgnoreCase("N")) {
             vCel1.setCellValue(new HSSFRichTextString("   Counter Report "));
             counterTranscationToExcel = "SELECT header.sales_orderno,lineitem.item,lineitem.material,lineitem.materialCraftGroup,lineitem.discount_percentage,lineitem.discount_value,lineitem.vat_percentage,lineitem.vat_value,lineitem.pck_charge,lineitem.calcu_value,header.emp_id,header.show_room,DATE_FORMAT(header.date_time,'%d-%m-%Y %h:%m:%s')as date_time FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + counterFromDate.trim() + "%')AND(header.cancelFlag='N')";
             } else if (cancelledOptionParameter.equalsIgnoreCase("X")) {
             vCel1.setCellValue(new HSSFRichTextString("   Counter Report For Cancelled Bills"));
             counterTranscationToExcel = "SELECT header.sales_orderno,lineitem.item,lineitem.material,lineitem.materialCraftGroup,lineitem.discount_percentage,lineitem.discount_value,lineitem.vat_percentage,lineitem.vat_value,lineitem.pck_charge,lineitem.calcu_value,header.emp_id,header.show_room,DATE_FORMAT(header.date_time,'%d-%m-%Y %h:%m:%s')as date_time FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + counterFromDate.trim() + "%')AND(header.cancelFlag='X')";
             }
             TEMP-BLOCKED 31-01-2015-PRANESH
             */
            /*
             resultSet = daoClass.Fun_Resultset(counterTranscationToExcel);
             resultSetmd = resultSet.getMetaData();
             columnCount = resultSetmd.getColumnCount();
             System.out.println("Count : " + columnCount);
             counterXcelFileName = SetFilename();
             counterXcelFileName = counterXcelFileName + "." + "xls";

             //String dataDir = ServletActionContext.getServletContext().getRealPath("/Downloads");
             //System.out.println(":=> : " + getLocalPath());
             File file = new File(getLocalPath());
             if (!file.exists()) {
             file.mkdir();
             }
             CouterReportXcelFilePath = file.getPath();
             //System.out.println("File Name : " + CouterReportXcelFilePath);
             File theFile2 = new File(CouterReportXcelFilePath, counterXcelFileName);
             theFile2.createNewFile();
             HSSFCell fstCel = rowhead.createCell(page_index);

             for (int i = 2; i <= columnCount; i++) {
             fstCel = rowhead.createCell(i - 1);
             fstCel.setCellStyle(cellStyle);
             sheet.setColumnWidth(i - 1, columnWidths[i - 2]);
             fstCel.setCellValue(new HSSFRichTextString(resultSetmd.getColumnName(i - 1).toUpperCase()));
             }
             while (resultSet.next()) {
             HSSFRow row = sheet.createRow(page_index);
             fstCel = row.createCell((short) 250);
             row.setHeight((short) 430);
             for (int j = 2; j <= columnCount + 1; j++) {
             row.createCell((short) j - 1).setCellValue(resultSet.getString(j - 1).toString());
             }
             page_index++;
             rtn = 1;
             }
             wb2.write(new FileOutputStream(theFile2));
             */
            counterToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + counterToDate.trim() + "',1))");
            String getCraftNos = "SELECT counter_no FROM pos.branch_counter";
            counterNosRs = daoClass.Fun_Resultset(getCraftNos);
            while (counterNosRs.next()) {
                counterCraftNosLst.add(counterNosRs.getString("counter_no"));
            }
            for (String counterNo : counterCraftNosLst) {
                String getCraftGroupsQuery = " SELECT craft_group FROM pos.craft_counter_list where storage_location='" + counterNo + "'";
                craftGroupsRs = daoClass.Fun_Resultset(getCraftGroupsQuery);
                while (craftGroupsRs.next()) {
                    craftGroupsLst.add(craftGroupsRs.getString("craft_group"));
                }
            }

            //if (cancelledOptionParameter.equalsIgnoreCase("Select")) {
            vCel1.setCellValue(new HSSFRichTextString("                       Run Date  : " + currentDateFormat.format(date).trim() + "    CAUVERY  Karnataka State Arts & Crafts Emporium"));
            for (String craftGroup : craftGroupsLst) {
                String valid = "SELECT count(materialCraftGroup) FROM pos.lineitem where materialCraftGroup='" + craftGroup + "'";
                int x = daoClass.Fun_Int(valid);
                if (x > 0) {
                    // 30-12-2014 //  counterTranscationToExcel ="select lineitem.materialCraftGroup,sum(lineitem.prc_value) as Gross_Amount, sum(lineitem.prc_value)-sum(lineitem.discount_value) as Net_Amount, sum(lineitem.discount_value) as Discount_Amount, sum(lineitem.vat_value) as Vat_Amount,sum(lineitem.pck_charge) as Pck_Charge ,sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as Total_Sales FROM lineitem lineitem JOIN header header ON header.sales_orderno = lineitem.sales_orderno where (lineitem.plant = '" + session.get("Plant_Id").toString() + "' )AND(lineitem.date_time between  '" + counterFromDate.trim() + "%' and '" + counterToDate.trim() + "%')AND (lineitem.materialCraftGroup='"+craftGroup +"')";
                    // 30-12-2014 //counterTranscationToExcel_Total ="select lineitem.materialCraftGroup,sum(lineitem.prc_value) as Gross_Amount, sum(lineitem.prc_value)-sum(lineitem.discount_value) as Net_Amount, sum(lineitem.discount_value) as Discount_Amount, sum(lineitem.vat_value) as Vat_Amount,sum(lineitem.pck_charge) as Pck_Charge ,sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as Total_Sales FROM lineitem lineitem JOIN header header ON header.sales_orderno = lineitem.sales_orderno where (lineitem.plant = '" + session.get("Plant_Id").toString() + "' )AND(lineitem.date_time between  '" + counterFromDate.trim() + "%' and '" + counterToDate.trim() + "%')AND (lineitem.materialCraftGroup='"+craftGroup +"')";
                    if (!(cancelledOptionParameter.equalsIgnoreCase("Select"))) {
                        counterTranscationToExcel = "SELECT branch_counter.counter_no_legacy,branch_counter.counter,sum(lineitem.prc_value) as Gross_Amount,sum(lineitem.discount_value) as Disc_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value) as Net_Amount,sum(lineitem.vat_value)as Vat_Amount,sum(lineitem.pck_charge) as Pack_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as Total_Sales FROM    (   (   pos.craft_counter_list craft_counter_list INNER JOIN pos.branch_counter branch_counter ON (craft_counter_list.storage_location =branch_counter.counter_no)) INNER JOIN pos.lineitem lineitem ON (lineitem.materialCraftGroup = craft_counter_list.craft_group)) INNER JOIN pos.header header ON (header.sales_orderno = lineitem.sales_orderno) WHERE     (lineitem.date_time between   '" + counterFromDate.trim() + "%' and '" + counterToDate.trim() + "%') AND (lineitem.plant = '" + session.get("Plant_Id").toString() + "' )AND (lineitem.materialCraftGroup='" + craftGroup + "')AND(header.cancelFlag='" + cancelledOptionParameter.trim() + "')";
                        csrTotalQuery = "SELECT branch_counter.counter_no_legacy,branch_counter.counter,sum(lineitem.prc_value) as Gross_Amount,sum(lineitem.discount_value) as Disc_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value) as Net_Amount,sum(lineitem.vat_value)as Vat_Amount,sum(lineitem.pck_charge) as Pack_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as Total_Sales FROM    (   (   pos.craft_counter_list craft_counter_list INNER JOIN pos.branch_counter branch_counter ON (craft_counter_list.storage_location =branch_counter.counter_no)) INNER JOIN pos.lineitem lineitem ON (lineitem.materialCraftGroup = craft_counter_list.craft_group)) INNER JOIN pos.header header ON (header.sales_orderno = lineitem.sales_orderno) WHERE     (lineitem.date_time between    '" + counterFromDate.trim() + "%' and '" + counterToDate.trim() + "%') AND (lineitem.plant = '" + session.get("Plant_Id").toString() + "' )AND (lineitem.materialCraftGroup='" + craftGroup + "')AND(header.cancelFlag='" + cancelledOptionParameter.trim() + "')";
                    } else if (cancelledOptionParameter.equalsIgnoreCase("Select")) {
                        counterTranscationToExcel = "SELECT branch_counter.counter_no_legacy,branch_counter.counter,sum(lineitem.prc_value) as Gross_Amount,sum(lineitem.discount_value) as Disc_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value) as Net_Amount,sum(lineitem.vat_value)as Vat_Amount,sum(lineitem.pck_charge) as Pack_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as Total_Sales FROM    (   (   pos.craft_counter_list craft_counter_list INNER JOIN pos.branch_counter branch_counter ON (craft_counter_list.storage_location =branch_counter.counter_no)) INNER JOIN pos.lineitem lineitem ON (lineitem.materialCraftGroup = craft_counter_list.craft_group)) INNER JOIN pos.header header ON (header.sales_orderno = lineitem.sales_orderno) WHERE     (lineitem.date_time between   '" + counterFromDate.trim() + "%' and '" + counterToDate.trim() + "%') AND (lineitem.plant = '" + session.get("Plant_Id").toString() + "' )AND (lineitem.materialCraftGroup='" + craftGroup + "')";
                        csrTotalQuery = "SELECT branch_counter.counter_no_legacy,branch_counter.counter,sum(lineitem.prc_value) as Gross_Amount,sum(lineitem.discount_value) as Disc_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value) as Net_Amount,sum(lineitem.vat_value)as Vat_Amount,sum(lineitem.pck_charge) as Pack_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as Total_Sales FROM    (   (   pos.craft_counter_list craft_counter_list INNER JOIN pos.branch_counter branch_counter ON (craft_counter_list.storage_location =branch_counter.counter_no)) INNER JOIN pos.lineitem lineitem ON (lineitem.materialCraftGroup = craft_counter_list.craft_group)) INNER JOIN pos.header header ON (header.sales_orderno = lineitem.sales_orderno) WHERE     (lineitem.date_time between    '" + counterFromDate.trim() + "%' and '" + counterToDate.trim() + "%') AND (lineitem.plant = '" + session.get("Plant_Id").toString() + "' )AND (lineitem.materialCraftGroup='" + craftGroup + "')";
                    }
                    resultSet = daoClass.Fun_Resultset(csrTotalQuery);
                    while (resultSet.next()) {
                        totGrossAmt += resultSet.getFloat("Gross_Amount");
                        totDiscAmt += (resultSet.getFloat("Disc_Amount"));
                        totNetAmt += (resultSet.getFloat("Net_Amount"));
                        totVatAmt += (resultSet.getFloat("Vat_Amount"));
                        totPckAmt += (resultSet.getFloat("Pack_Amount"));
                        totSalesAmt += (resultSet.getFloat("Total_Sales"));
                    }
                    counterTransXcelRs = daoClass.Fun_Resultset(counterTranscationToExcel);
                    resultSetmd = counterTransXcelRs.getMetaData();
                    columnCount = resultSetmd.getColumnCount();
                    counterXcelFileName = SetFilename();
                    //System.out.println("F1 :  " + counterXcelFileName);
                    counterXcelFileName = counterXcelFileName + "." + "xls";
                    //System.out.println("FILE NAME : " + counterXcelFileName);
                    File file = new File(getLocalPath());
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    CouterReportXcelFilePath = ServletActionContext.getServletContext().getRealPath("/Downloads");
                    //System.out.println("CouterReportXcelFilePath :  " + CouterReportXcelFilePath);
                    File theFile2 = new File(CouterReportXcelFilePath, counterXcelFileName);
                    theFile2.createNewFile();
                    HSSFCell fstCel = rowhead.createCell(page_index);
                    for (int i = 2; i <= columnCount + 1; i++) {
                        fstCel = rowhead.createCell(i - 1);
                        fstCel.setCellStyle(cellStyle);
                        sheet.setColumnWidth(i - 1, columnWidths[i - 2]);
                        fstCel.setCellValue(new HSSFRichTextString(resultSetmd.getColumnName(i - 1).toUpperCase()));
                    }
                    while (counterTransXcelRs.next()) {
                        HSSFRow row = sheet.createRow(page_index);
                        fstCel = row.createCell((short) 250);
                        row.setHeight((short) 430);
                        for (int j = 2; j <= columnCount + 1; j++) {
                            row.createCell((short) j - 1).setCellValue(counterTransXcelRs.getString(j - 1).toString());
                        }
                        if (counterTransXcelRs.isLast()) {
                            int rowval = 10;
                            HSSFRow row0 = sheet.createRow(rowval);
                            df.setMaximumFractionDigits(2);
                            row0.createCell((short) 0).setCellValue("TOTAL");
                            row0.createCell((short) 3).setCellValue(df.format(totGrossAmt));
                            row0.createCell((short) 4).setCellValue(df.format(totDiscAmt));
                            row0.createCell((short) 5).setCellValue(df.format(totNetAmt));
                            row0.createCell((short) 6).setCellValue(df.format(totVatAmt));
                            row0.createCell((short) 7).setCellValue(df.format(totPckAmt));
                            row0.createCell((short) 8).setCellValue(df.format(totSalesAmt));
                        }
                        page_index++;
                        rtn = 1;
                    }
                    wb2.write(new FileOutputStream(theFile2));
                }
            }
            //}
        } catch (Exception ex) {
            System.out.println("Exception in ExportCounter :  " + ex);
        } finally {
            daoClass.closeResultSet(resultSet);
            daoClass.closeResultSet(counterNosRs);
            daoClass.closeResultSet(craftGroupsRs);
            daoClass.closeResultSet(counterTransXcelRs);
        }
        return SUCCESS;
    }

    private HSSFCellStyle setHeaderStyle(HSSFWorkbook sampleWorkBook, Row row, short column, short halign, short valign) {
        HSSFFont font = sampleWorkBook.createFont();
        Cell cell = row.createCell(column);
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFCellStyle cellStyle = sampleWorkBook.createCellStyle();
        cell.setCellValue(" ");
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setFont(font);
        return cellStyle;
    }

    public String SetFilename() {
        try {
            CouterReportXcelFileName = "DailyCounterBillReport" + "" + datformat.format(date);
        } catch (Exception ex) {
            System.out.println("Excception In Setting FileName :  " + ex);
        }
        return CouterReportXcelFileName;
    }

    public String getCounterXcelFileName() {
        return counterXcelFileName;
    }

    public void setCounterXcelFileName(String counterXcelFileName) {
        this.counterXcelFileName = counterXcelFileName;
    }

    public String getCounterToDate() {
        return counterToDate;
    }

    public void setCounterToDate(String counterToDate) {
        this.counterToDate = counterToDate;
    }

    public String getCancelledOptionParameter() {
        return cancelledOptionParameter;
    }

    public void setCancelledOptionParameter(String cancelledOptionParameter) {
        this.cancelledOptionParameter = cancelledOptionParameter;
    }

    public int getRtn() {
        return rtn;
    }

    public void setRtn(int rtn) {
        this.rtn = rtn;
    }

    public String getCouterReportXcelFileName() {
        return CouterReportXcelFileName;
    }

    public void setCouterReportXcelFileName(String CouterReportXcelFileName) {
        this.CouterReportXcelFileName = CouterReportXcelFileName;
    }

    public String getCouterReportXcelFilePath() {
        return CouterReportXcelFilePath;
    }

    public void setCouterReportXcelFilePath(String CouterReportXcelFilePath) {
        this.CouterReportXcelFilePath = CouterReportXcelFilePath;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String getCounterFromDate() {
        return counterFromDate;
    }

    public void setCounterFromDate(String counterFromDate) {
        this.counterFromDate = counterFromDate;
    }

}
