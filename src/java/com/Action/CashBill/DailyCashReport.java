/*@author pranesh*/
package com.Action.CashBill;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

public class DailyCashReport extends ActionSupport implements SessionAware {

    int rtn = 0;
    int clocount;

    Map session;
    String filename;
    String filePath;
    private double totAmount = 0;
    String exportToExcel = null;
    private String reportPaymentType;
    DecimalFormat dcmlformat = new DecimalFormat("#0.00");

    int page_index = 4;
    String xlFileName = null;
    ResultSet resultSet = null;
    private ResultSet userIdRs = null;
    private ResultSet userNameRs = null;
    private ResultSet legacyHeaderRs = null;

    DaoClass daoClass = new DaoClass();
    ResultSetMetaData resultSetmd = null;
    Calendar now = Calendar.getInstance();
    java.util.Date date = new java.util.Date();

    SimpleDateFormat datformat = new SimpleDateFormat("ddMMyyHHmmss");
    SimpleDateFormat currDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    String dat = now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR);
    String cbFromDate;
    String cbToDate;

    public DailyCashReport() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    private static final ResourceBundle rb = ResourceBundle.getBundle("dbConnection");

    public static String getLocalPath() {
        return rb.getString("local.temp.path");
    }

    public String DailyReportProcess() {
        try {
            int rowval = 0;
            String counterNoandNameExcelQry = null;
            String getCreatedUserIds = null;
            if (cbFromDate.equalsIgnoreCase("") || cbFromDate.equalsIgnoreCase(null)) {
                cbFromDate = "0000-01-01";
            }
            if (cbToDate.equalsIgnoreCase("") || cbToDate.equalsIgnoreCase(null)) {
                cbFromDate = "0000-01-01";
            }

            int[] columnWidths = new int[]{3800, 3800, 3800, 3800, 3800, 3800, 3800};
            HSSFWorkbook wb2 = new HSSFWorkbook();
            HSSFSheet sheet = wb2.createSheet(" ");
            //HSSFRow rowhead = sheet.createRow((short) 4);
            HSSFRow rowhead = sheet.createRow((short) 3);
            HSSFCellStyle style = wb2.createCellStyle();

            HSSFRow dataRow1 = sheet.createRow(1);
            Row row1 = sheet.createRow(1);
            HSSFCellStyle cellStyle = setHeaderStyle(wb2, row1, (short) 1, CellStyle.ALIGN_CENTER_SELECTION, CellStyle.VERTICAL_BOTTOM);
            HSSFCellStyle cellStyle2 = setHeaderStyle(wb2, row1, (short) 1, CellStyle.ALIGN_LEFT, CellStyle.VERTICAL_BOTTOM);

            HSSFCellStyle cell1, cell2, cell3;

            HSSFRow title, title2;
            title = sheet.createRow((short) 0);
            title2 = sheet.createRow((short) 1);
            HSSFCell vCel1, vCel2, vCel3, vCel4;
            vCel1 = title.createCell(3);
            vCel1.setCellStyle(cellStyle);
            vCel1.setCellValue(new HSSFRichTextString("   Daily Cash Report"));

            vCel2 = title2.createCell(2);
            vCel2.setCellStyle(cellStyle2);
            //vCel2.setCellValue(new HSSFRichTextString("From date :    " + cbFromDate));

            vCel3 = title2.createCell(4);
            vCel3.setCellStyle(cellStyle);
            //vCel3.setCellValue(new HSSFRichTextString("To Date   :    " + cbToDate.trim()));

            vCel4 = title2.createCell(6);
            vCel4.setCellStyle(cellStyle);
            if (reportPaymentType.equalsIgnoreCase("Select")) {
                vCel4.setCellValue(new HSSFRichTextString("[ ALL ]"));
            } else if (reportPaymentType.equalsIgnoreCase("cash")) {
                vCel4.setCellValue(new HSSFRichTextString("[ CASH ]"));
            } else if (reportPaymentType.equalsIgnoreCase("card")) {
                vCel4.setCellValue(new HSSFRichTextString("[ CARD ]"));
            }

            if (reportPaymentType.equalsIgnoreCase("select")) {
                if (cbToDate.equals("0000-01-01") && (!(cbFromDate.equals("0000-01-01")))) {
                    vCel2.setCellValue(new HSSFRichTextString("From date :    " + cbFromDate));
                    exportToExcel = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y')as date_time,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like'" + cbFromDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "') order by cashbill_header_master.user_id";
                } else if (cbFromDate.equals("0000-01-01") && (!(cbToDate.equals("0000-01-01")))) {
                    vCel2.setCellValue(new HSSFRichTextString("To date :    " + cbToDate));
                    exportToExcel = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y')as date_time,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like'" + cbToDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "') order by cashbill_header_master.user_id";
                } else if (!(((cbFromDate.equals("0000-01-01")) && ((cbToDate.equals("0000-01-01")))))) {
                    vCel2.setCellValue(new HSSFRichTextString("From date :    " + cbFromDate));
                    vCel3.setCellValue(new HSSFRichTextString("To Date   :    " + cbToDate.trim()));
                    cbToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + cbToDate.trim() + "',1))");
                    exportToExcel = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y')as date_time,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time between'" + cbFromDate.trim() + "%' AND '" + cbToDate.trim() + "%')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "') order by cashbill_header_master.user_id";
                }
            } else if (!(reportPaymentType.equalsIgnoreCase("select"))) {
                if (cbToDate.equals("0000-01-01") && (!(cbFromDate.equals("0000-01-01")))) {
                    System.out.println("FOR FROM DATE");
                    /*exportToExcel = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y')as date_time,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like'" + cbFromDate.trim() + "%')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "') order by cashbill_header_master.user_id";*/
                    counterNoandNameExcelQry = "SELECT distinct branchCounter.counter FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime like'" + cbFromDate.trim() + "%')";
                    getCreatedUserIds = "SELECT distinct user_id from pos.cashbill_header_master where date_time like'" + cbFromDate.trim() + "%'order by cashbill_header_master.user_id ASC";
                    exportToExcel = "SELECT DATE_FORMAT(cashbillHeader.date_time,'%d-%m-%Y')as date_time,branchCounter.counter,cashbillHeader.cashBill_id,cashbillLineitem.counterbill_no,cashbillLineitem.cashbill_amt,cashbillHeader.paymentType,cashbillHeader.user_id FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(cashbillHeader.plantId = '" + session.get("Plant_Id").toString() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime like'" + cbFromDate.trim() + "%')order by cashbillHeader.user_id,cashbillLineitem.counterbill_no asc";
                } else if (cbFromDate.equals("0000-01-01") && (!(cbToDate.equals("0000-01-01")))) {
                    System.out.println("FOR TO DATE");
                    /*exportToExcel = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y')as date_time,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time like'" + cbToDate.trim() + "%')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "') order by cashbill_header_master.user_id";*/
                    counterNoandNameExcelQry = "SELECT distinct branchCounter.counter FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime like'" + cbToDate.trim() + "%')";
                    getCreatedUserIds = "SELECT distinct user_id from pos.cashbill_header_master where date_time like'" + cbToDate.trim() + "%'order by cashbill_header_master.user_id ASC";
                    exportToExcel = "SELECT DATE_FORMAT(cashbillHeader.date_time,'%d-%m-%Y')as date_time,branchCounter.counter,cashbillHeader.cashBill_id,cashbillLineitem.counterbill_no,cashbillLineitem.cashbill_amt,cashbillHeader.paymentType,cashbillHeader.user_id FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(cashbillHeader.plantId = '" + session.get("Plant_Id").toString() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime like'" + cbToDate.trim() + "%')order by cashbillHeader.user_id,cashbillLineitem.counterbill_no asc";
                } else if (!(((cbFromDate.equals("0000-01-01")) && ((cbToDate.equals("0000-01-01")))))) {
                    System.out.println("FOR FROM & TO DATE");
                    //exportToExcel = "SELECT DATE_FORMAT(cashbill_header_master.date_time,'%d-%m-%Y %h:%m:%s')as date_time,cashbill_header_master.cashBill_id,cashbill_lineitem_master.counterbill_no,cashbill_header_master.paymentType,cashbill_header_master.user_id,cashbill_lineitem_master.cashbill_amt,cashbill_header_master.plantId FROM pos.cashbill_header_master cashbill_header_master INNER JOIN pos.cashbill_lineitem_master cashbill_lineitem_master ON (cashbill_header_master.cashBill_id =cashbill_lineitem_master.cashBill_id)WHERE(cashbill_header_master.date_time between'" + cbFromDate.trim() + "%' AND '" + cbToDate.trim() + "%')AND(cashbill_header_master.paymentType='" + reportPaymentType.trim() + "')AND(cashbill_header_master.plantId = '" + session.get("Plant_Id").toString() + "') order by cashbill_header_master.user_id";
                    cbToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + cbToDate.trim() + "',1))");
                    counterNoandNameExcelQry = "SELECT distinct branchCounter.counter FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime  between '" + cbFromDate.trim() + "%' AND '" + cbToDate.trim() + "%')";
                    getCreatedUserIds = "SELECT distinct user_id from pos.cashbill_header_master where date_time  between '" + cbFromDate.trim() + "%' AND '" + cbToDate.trim() + "%'order by cashbill_header_master.user_id ASC";
                    exportToExcel = "SELECT DATE_FORMAT(cashbillHeader.date_time,'%d-%m-%Y')as date_time,branchCounter.counter,cashbillHeader.cashBill_id,cashbillLineitem.counterbill_no,cashbillLineitem.cashbill_amt,cashbillHeader.paymentType,cashbillHeader.user_id FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(cashbillHeader.plantId = '" + session.get("Plant_Id").toString() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime between '" + cbFromDate.trim() + "%' AND '" + cbToDate.trim() + "%')order by cashbillHeader.user_id,cashbillLineitem.counterbill_no asc";
                    //AND(cashbillHeader.user_id = '" + session.get("Login_Id") + "')
                    //group by branchCounter.counter
                }
            }
            System.out.println("=>=>:" + exportToExcel);
            System.out.println("getCreatedUserIds        : : =>=>:" + getCreatedUserIds);
            System.out.println("counterNoandNameExcelQry : : =>=>:" + counterNoandNameExcelQry);
            /*FETCHING DISTINCT COUNTER NAME BASED ON BETWEEN DATES*/
            /*String counterNoandNameExcelQry = "SELECT distinct branchCounter.counter FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime between '" + cbFromDate.trim() + "%' AND '" + cbToDate.trim() + "%')";*/
            //AND(cashbillHeader.user_id = '" + session.get("Login_Id").toString().trim() + "')
            legacyHeaderRs = daoClass.Fun_Resultset(counterNoandNameExcelQry);
            List<String> legacyCounterName = new ArrayList();
            while (legacyHeaderRs.next()) {
                legacyCounterName.add(legacyHeaderRs.getString("counter"));
            }

            /*FETCHING DISTINCT USERID'S BASED ON BETWEEN DATES*/
            /*String getCreatedUserIds = "SELECT distinct user_id from pos.cashbill_header_master where date_time between'" + cbFromDate.trim() + "%' AND '" + UserIdCbToDate.trim() + "%'order by cashbill_header_master.user_id ASC";*/
            userIdRs = daoClass.Fun_Resultset(getCreatedUserIds);
            List<String> listUserIds = new ArrayList();
            while (userIdRs.next()) {
                listUserIds.add(userIdRs.getString("user_id").trim());
            }

            resultSet = daoClass.Fun_Resultset(exportToExcel);
            resultSetmd = resultSet.getMetaData();
            clocount = resultSetmd.getColumnCount();
            xlFileName = SetFilename();
            xlFileName = xlFileName + "." + "xls";
            filePath = ServletActionContext.getServletContext().getRealPath("/Downloads");
            File theFile2 = new File(filePath, xlFileName);
            theFile2.createNewFile();
            HSSFCell fstCel = rowhead.createCell(page_index);
            vCel1.setCellValue(new HSSFRichTextString("                       Run Date  : " + currDateFormat.format(date).trim() + "    CAUVERY  Karnataka State Arts & Crafts Emporium"));
            for (int i = 2; i <= clocount + 1; i++) {
                fstCel = rowhead.createCell(i - 1);
                fstCel.setCellStyle(cellStyle);
                sheet.setColumnWidth(i - 1, columnWidths[i - 2]);
                fstCel.setCellValue(new HSSFRichTextString(resultSetmd.getColumnName(i - 1).toUpperCase()));
            }
            while (resultSet.next()) {
                rowval += 1;
                totAmount += resultSet.getFloat("cashbill_amt");
                HSSFRow row = sheet.createRow(page_index);
                fstCel = row.createCell((short) 250);
                row.setHeight((short) 430);
                for (int j = 2; j <= clocount + 1; j++) {
                    row.createCell((short) j - 1).setCellValue(resultSet.getString(j - 1).toString());
                }
                if (resultSet.isLast()) {
                    int newRowValue0 = 9;
                    int newRowValue1 = 10;
                    HSSFRow row0 = sheet.createRow(rowval + 6);
                    dcmlformat.setMaximumFractionDigits(2);
                    row0.createCell((short) 0).setCellValue("TOTAL");
                    row0.createCell((short) 5).setCellValue(dcmlformat.format(totAmount));
                    if (listUserIds.size() > 0) {
                        for (String userId : listUserIds) {
                            HSSFRow summayRow0 = sheet.createRow(rowval + newRowValue0);
                            String getUserNames = "SELECT empmaster.emp_name FROM pos.emp_master empmaster INNER JOIN pos.user_master usermaster ON (empmaster.emp_pk = usermaster.user_pk)WHERE (usermaster.username = '" + userId.trim() + "')";
                            userNameRs = daoClass.Fun_Resultset(getUserNames);
                            while (userNameRs.next()) {
                                summayRow0.createCell((short) 1).setCellValue(userNameRs.getString("emp_name").trim());
                                for (String counterName : legacyCounterName) {
                                    String countRecOfSumQry = null;
                                    int countRecOfSum = 0;
                                    if (cbToDate.equals("0000-01-01") && (!(cbFromDate.equals("0000-01-01")))) {
                                        countRecOfSumQry = "SELECT count(*) FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(cashbillHeader.user_id = '" + userId.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime like '" + cbFromDate.trim() + "%'  AND branchCounter.counter='" + counterName.trim() + "')order by cashbillLineitem.counterbill_no asc";
                                    } else if (cbFromDate.equals("0000-01-01") && (!(cbToDate.equals("0000-01-01")))) {
                                        countRecOfSumQry = "SELECT count(*) FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(cashbillHeader.user_id = '" + userId.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime like '" + cbToDate.trim() + "%'  AND branchCounter.counter='" + counterName.trim() + "')order by cashbillLineitem.counterbill_no asc";
                                    } else if (!(((cbFromDate.equals("0000-01-01")) && ((cbToDate.equals("0000-01-01")))))) {
                                        countRecOfSumQry = "SELECT count(*) FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(cashbillHeader.user_id = '" + userId.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime between '" + cbFromDate.trim() + "%' AND '" + cbToDate.trim() + "%'  AND branchCounter.counter='" + counterName.trim() + "')order by cashbillLineitem.counterbill_no asc";
                                    }
                                    System.out.println("countRecOfSumQry : " + countRecOfSumQry);
                                    countRecOfSum = daoClass.Fun_Int(countRecOfSumQry);
                                    System.out.println("countRecOfSumQry : " + countRecOfSumQry);
                                    if (countRecOfSum >= 1) {

                                        String sumQry = null;
                                        HSSFRow summayRow1 = sheet.createRow(rowval + newRowValue1);
                                        summayRow1.createCell((short) 2).setCellValue(counterName);
                                        if (cbToDate.equals("0000-01-01") && (!(cbFromDate.equals("0000-01-01")))) {
                                            sumQry = "SELECT sum(cashbillLineitem.cashbill_amt) FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(cashbillHeader.user_id = '" + userId.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime like '" + cbFromDate.trim() + "%'  AND branchCounter.counter='" + counterName.trim() + "')order by cashbillLineitem.counterbill_no asc";
                                        } else if (cbFromDate.equals("0000-01-01") && (!(cbToDate.equals("0000-01-01")))) {
                                            sumQry = "SELECT sum(cashbillLineitem.cashbill_amt) FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(cashbillHeader.user_id = '" + userId.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime like '" + cbToDate.trim() + "%'  AND branchCounter.counter='" + counterName.trim() + "')order by cashbillLineitem.counterbill_no asc";
                                        } else if (!(((cbFromDate.equals("0000-01-01")) && ((cbToDate.equals("0000-01-01")))))) {
                                            sumQry = "SELECT sum(cashbillLineitem.cashbill_amt) FROM((pos.header header INNER JOIN pos.branch_counter branchCounter ON(header.counterpk = branchCounter.counter_pk))INNER JOIN pos.cashbill_header_master cashbillHeader ON(cashbillHeader.counterbill_no = header.sales_orderno))INNER JOIN pos.cashbill_lineitem_master cashbillLineitem ON (cashbillHeader.cashBill_id =cashbillLineitem.cashBill_id)WHERE(cashbillHeader.paymentType ='" + reportPaymentType.trim() + "')AND(cashbillHeader.user_id = '" + userId.trim() + "')AND(header.cancelFlag='N')AND(cashbillLineitem.cashbill_dateTime between '" + cbFromDate.trim() + "%' AND '" + cbToDate.trim() + "%'  AND branchCounter.counter='" + counterName.trim() + "')order by cashbillLineitem.counterbill_no asc";
                                        }
                                        summayRow1.createCell((short) 4).setCellValue(daoClass.Fun_Float(sumQry));
                                    }
                                    newRowValue1 = newRowValue1 + 1;
                                }
                            }
                            rowval = rowval + 1;
                            newRowValue0 = newRowValue0 + 3;
                        }
                    }
                }
                page_index++;
                rtn = 1;
            }
            wb2.write(new FileOutputStream(theFile2));
        } catch (Exception ex) {
            System.out.println("Exception in reporting : " + ex);

        } finally {
            daoClass.closeResultSet(resultSet);
            daoClass.closeResultSet(userIdRs);
            daoClass.closeResultSet(userNameRs);
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
            filename = "DailyCashReport" + "" + datformat.format(date);
        } catch (Exception ex) {
            System.out.println("Excception In Setting FileName :  " + ex);
        }
        return filename;
    }

    public String getReportPaymentType() {
        return reportPaymentType;
    }

    public void setReportPaymentType(String reportPaymentType) {
        this.reportPaymentType = reportPaymentType;
    }

    public String getCbFromDate() {
        return cbFromDate;
    }

    public void setCbFromDate(String cbFromDate) {
        if (cbFromDate.equalsIgnoreCase("") || cbFromDate.equalsIgnoreCase(null)) {
            this.cbFromDate = "0000-01-01";
        } else {
            this.cbFromDate = cbFromDate;
        }
    }

    public String getCbToDate() {
        return cbToDate;
    }

    public void setCbToDate(String cbToDate) {
        if (cbToDate.equalsIgnoreCase("") || cbToDate.equalsIgnoreCase(null)) {
            this.cbToDate = "0000-01-01";
        } else {
            this.cbToDate = cbToDate;
        }
    }

    public int getRtn() {
        return rtn;
    }

    public void setRtn(int rtn) {
        this.rtn = rtn;
    }

    public String getXlFileName() {
        return xlFileName;
    }

    public void setXlFileName(String xlFileName) {
        this.xlFileName = xlFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

}
