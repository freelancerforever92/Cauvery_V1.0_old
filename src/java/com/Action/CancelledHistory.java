/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Action;

import com.DAO.DaoClass;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Java-1
 */
public class CancelledHistory extends ActionSupport {

    private String txtCancellingSalesOrderNo;
    DaoClass daoClass = new DaoClass();
    private String craftName;
    private String netAmount;
    private String cancelDate;
    private String processedDate;
    private String empNameCanceledBy;
    private String empNameProcessedBy;
    private String cancelReason;
    private String getSalesOrderNumbercanceled;
    ResultSet checkCancelledRs = null;
    ResultSet salesOrdercanceledHistoryRs = null;
    ResultSet getCanceledEmpnameRs = null;
    private int canceledHistoryVal;
    private int inoiceCountVal;
    private String cancelledFlag;
    private String cancelledBy;

    public String getCanceledHistoryInfo() {
        try {
            String inoiceCountValQery = "select count(*) as count from pos.header where sales_orderno='" + txtCancellingSalesOrderNo + "'";
            inoiceCountVal = daoClass.Fun_Int(inoiceCountValQery);
            if (inoiceCountVal > 0) {
                String checkCancelledQry = "select cancelFlag from pos.header where sales_orderno='" + txtCancellingSalesOrderNo + "';";
                checkCancelledRs = daoClass.Fun_Resultset(checkCancelledQry);
                while (checkCancelledRs.next()) {
                    cancelledFlag = checkCancelledRs.getString("cancelFlag");
                    if (cancelledFlag.equals("X")) {
                        canceledHistoryVal = 1;
                        /*String salesOrdercanceledHistoryQry = "SELECT DISTINCT\n"
                         + "craft_counter_list.description as Craft,\n"
                         + "header.sales_orderno as SalesOrderNumber,\n"
                         + "header.net_amt as TotalAmount,\n"
                         + "  emp_master_1.emp_name as CreatedBy,\n"
                         + "DATE_FORMAT(header.date_time,'%d-%m-%Y %r')as CreatedDate,\n"
                         + "emp_master.emp_name as CancelledBy,\n"
                         + "       DATE_FORMAT(header.cancelDatetime,'%d-%m-%Y %r')as CancelDate,\n"
                         + "       cancelling_reason_master.reason_desc AS CancelReason\n"
                         + "  FROM    (   (   (   (   pos.header header\n"
                         + "                       INNER JOIN\n"
                         + "                          pos.emp_master emp_master\n"
                         + "                       ON (header.cancelledBy = emp_master.emp_id))\n"
                         + "                   INNER JOIN\n"
                         + "                      pos.lineitem lineitem\n"
                         + "                   ON (header.sales_orderno = lineitem.sales_orderno))\n"
                         + "               INNER JOIN\n"
                         + "                  pos.craft_counter_list craft_counter_list\n"
                         + "               ON (lineitem.materialCraftGroup =\n"
                         + "                      craft_counter_list.craft_group))\n"
                         + "           INNER JOIN\n"
                         + "              pos.cancelling_reason_master cancelling_reason_master\n"
                         + "           ON (header.cancelReason = cancelling_reason_master.reason_id))\n"
                         + "       INNER JOIN\n"
                         + "          pos.emp_master emp_master_1\n"
                         + "       ON (header.emp_id = emp_master_1.emp_id)\n"
                         + " WHERE (header.sales_orderno ='" + txtCancellingSalesOrderNo.trim() + "' )";*/
                        String salesOrdercanceledHistoryQry = "SELECT craft_counter_list.description as Craft,\n"
                                + "       header.sales_orderno as SalesOrderNumber,\n"
                                + "       header.net_amt as TotalAmount,\n"
                                + "       DATE_FORMAT(header.date_time,'%d-%m-%Y %r') as CreatedDate,\n"
                                + "       emp_master.emp_name as CreatedBy,\n"
                                + "      DATE_FORMAT(header.cancelDatetime,'%d-%m-%Y %r') as CancelDate,\n"
                                + "       emp_master_1.emp_name as CancelledBy,\n"
                                + "       cancelling_reason_master.reason_desc as CancelReason\n"
                                + "      \n"
                                + "  FROM    (   (   (   (   pos.header header\n"
                                + "                       INNER JOIN\n"
                                + "                          pos.cancelling_reason_master cancelling_reason_master\n"
                                + "                       ON (header.cancelReason =\n"
                                + "                              cancelling_reason_master.reasonNo))\n"
                                + "                   INNER JOIN\n"
                                + "                      pos.lineitem lineitem\n"
                                + "                   ON (header.sales_orderno = lineitem.sales_orderno))\n"
                                + "               INNER JOIN\n"
                                + "                  pos.craft_counter_list craft_counter_list\n"
                                + "               ON (lineitem.materialCraftGroup =\n"
                                + "                      craft_counter_list.craft_group))\n"
                                + "           INNER JOIN\n"
                                + "              pos.emp_master emp_master\n"
                                + "           ON (header.emp_id = emp_master.emp_id))\n"
                                + "       INNER JOIN\n"
                                + "          pos.emp_master emp_master_1\n"
                                + "       ON (header.cancelledBy = emp_master_1.emp_id)\n"
                                + " WHERE (header.sales_orderno = '" + txtCancellingSalesOrderNo.trim() + "')";
                        salesOrdercanceledHistoryRs = daoClass.Fun_Resultset(salesOrdercanceledHistoryQry);
                        while (salesOrdercanceledHistoryRs.next()) {
                            craftName = salesOrdercanceledHistoryRs.getString("Craft");
                            getSalesOrderNumbercanceled = salesOrdercanceledHistoryRs.getString("SalesOrderNumber");
                            netAmount = salesOrdercanceledHistoryRs.getString("TotalAmount");
                            processedDate = salesOrdercanceledHistoryRs.getString("CreatedDate");
                            cancelDate = salesOrdercanceledHistoryRs.getString("CancelDate");
                            empNameProcessedBy = salesOrdercanceledHistoryRs.getString("CreatedBy");
                            empNameCanceledBy = salesOrdercanceledHistoryRs.getString("CancelledBy");
                            cancelReason = salesOrdercanceledHistoryRs.getString("CancelReason");
//                            String getCannceldEmpnameQry = "SELECT emp_name FROM pos.emp_master where emp_id='" + cancelledBy.trim() + "'";
//                            getCanceledEmpnameRs = daoClass.Fun_Resultset(getCannceldEmpnameQry);
//                            while (getCanceledEmpnameRs.next()) {
//                                empNameCanceledBy = getCanceledEmpnameRs.getString("emp_name");
//                            }
                        }
                    } else {
                        canceledHistoryVal = 0;
                    }
                }
            } else {
                inoiceCountVal = 0;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CancelledHistory.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoClass.closeResultSet(salesOrdercanceledHistoryRs);
            daoClass.closeResultSet(checkCancelledRs);
            daoClass.closeResultSet(getCanceledEmpnameRs);
        }
        return SUCCESS;
    }

    public String getCraftName() {
        return craftName;
    }

    public void setCraftName(String craftName) {
        this.craftName = craftName;
    }

    public String getTxtCancellingSalesOrderNo() {
        return txtCancellingSalesOrderNo;
    }

    public void setTxtCancellingSalesOrderNo(String txtCancellingSalesOrderNo) {
        this.txtCancellingSalesOrderNo = txtCancellingSalesOrderNo;
    }

    public String getGetSalesOrderNumbercanceled() {
        return getSalesOrderNumbercanceled;
    }

    public void setGetSalesOrderNumbercanceled(String getSalesOrderNumbercanceled) {
        this.getSalesOrderNumbercanceled = getSalesOrderNumbercanceled;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(String processedDate) {
        this.processedDate = processedDate;
    }

    public String getEmpNameProcessedBy() {
        return empNameProcessedBy;
    }

    public void setEmpNameProcessedBy(String empNameProcessedBy) {
        this.empNameProcessedBy = empNameProcessedBy;
    }

    public String getEmpNameCanceledBy() {
        return empNameCanceledBy;
    }

    public void setEmpNameCanceledBy(String empNameCanceledBy) {
        this.empNameCanceledBy = empNameCanceledBy;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public int getCanceledHistoryVal() {
        return canceledHistoryVal;
    }

    public void setCanceledHistoryVal(int canceledHistoryVal) {
        this.canceledHistoryVal = canceledHistoryVal;
    }

    public int getInoiceCountVal() {
        return inoiceCountVal;
    }

    public void setInoiceCountVal(int inoiceCountVal) {
        this.inoiceCountVal = inoiceCountVal;
    }

}
