package com.Action.CashBill;

import com.DAO.*;
import java.sql.*;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

public class cancelSalesOrder extends ActionSupport implements SessionAware {

    Map session;
    ResultSet rs = null;
    Date date = new Date();
    int dbOrderNoCancelStatus;
    private String orderCreatedBy;
    boolean isCancelledBy = false;
    boolean isCashCollected = false;
    DaoClass cado = new DaoClass();
    private String cancelSalesOrderNo;
    private String cancellingReasonSno;
    boolean cancelledInfoStatus = false;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public cancelSalesOrder() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String salesOrderCancelStatus() {
        try {
            String cancelStatusQuery = "select count(*) from header where cancelFlag='X' and sales_orderno='" + cancelSalesOrderNo.trim() + "'";
            int checkOrderNoCancelStatus = cado.Fun_Int(cancelStatusQuery);
            if (checkOrderNoCancelStatus <= 0) {
                //NO-RECORDS
                dbOrderNoCancelStatus = 0;
            } else if (checkOrderNoCancelStatus >= 1) {
                dbOrderNoCancelStatus = 1;
            }
        } catch (Exception ex) {
            System.out.println("Excepiton In Checking salesOrderCancelStatus :   " + ex);
        }
        return SUCCESS;
    }

    public String cancelSalesOrderDetail() {
        try {
//            String getCancellingReasonIndexQuerry = "select reason_id from cancelling_reason_master where reason_desc='" + cancellingReasonSno.trim() + "'";
//            int cancelledIndex = cado.Fun_Int(getCancellingReasonIndexQuerry);
            String isCashCollectedQry = "SELECT count(*) FROM pos.cashbill_lineitem_master where counterbill_no='" + cancelSalesOrderNo.trim() + "'";
            int isCashCollectedCount = cado.Fun_Int(isCashCollectedQry);
            if (isCashCollectedCount >= 1) {
                isCashCollected = true;
            } else if (isCashCollectedCount <= 0) {
                isCashCollected = false;
                String getOrderCreatedByQuery = cado.Fun_Str("select emp_id from header where sales_orderno='" + cancelSalesOrderNo.trim() + "'");
                String checkCancellingflag = cado.Fun_Str("select cancelling_flag from pos.emp_master where emp_id='" + session.get("Login_Id").toString() + "'");
                if ((getOrderCreatedByQuery.equalsIgnoreCase(session.get("Login_Id").toString())) || (checkCancellingflag.equalsIgnoreCase("Y"))) {
                    isCancelledBy = true;//SAME USER
                    String cancelQuerry = "update header set cancelFlag='X',syncflag='0',Queue_flag='0',cancelReason='" + cancellingReasonSno.trim() + "',cancelDatetime='" + dateFormat.format(date).trim() + "',cancelledBy='" + session.get("Login_Id") + "' where sales_orderno='" + cancelSalesOrderNo.trim() + "'";
                    int counterLineItemCount = cado.Fun_Int("select count(line_pk) from pos.lineitem where sales_orderno='" + cancelSalesOrderNo.trim() + "'");
                    if (counterLineItemCount > 1) {
                        for (int i = 0; i <= counterLineItemCount; i++) {
                            String cancelUpdateLineItems = "update lineitem set Queue_flag='0',syncflag='0' where sales_orderno='" + cancelSalesOrderNo.trim() + "'";
                            int cancelledStatus = cado.Fun_Updat(cancelQuerry);
                            int calcelledUpdateLineItem = cado.Fun_Updat(cancelUpdateLineItems);
                            if (cancelledStatus >= 1 && calcelledUpdateLineItem >= 1) {
                                cancelledInfoStatus = true;
                            } else {
                                cancelledInfoStatus = false;
                            }
                        }
                    } else if (counterLineItemCount == 1) {
                        String cancelUpdateLineItems = "update lineitem set Queue_flag='0',syncflag='0' where sales_orderno='" + cancelSalesOrderNo.trim() + "'";
                        int cancelledStatus = cado.Fun_Updat(cancelQuerry);
                        int calcelledUpdateLineItem = cado.Fun_Updat(cancelUpdateLineItems);
                        if (cancelledStatus >= 1 && calcelledUpdateLineItem >= 1) {
                            cancelledInfoStatus = true;
                        } else {
                            cancelledInfoStatus = false;
                        }
                    }
                } else {
                    orderCreatedBy = cado.Fun_Str("select emp_name from emp_master where emp_id='" + getOrderCreatedByQuery.trim() + "' ");
                    isCancelledBy = false;//DIFFERENT USER
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception In Cancelling SalesOrder :  " + ex);
        }
        return SUCCESS;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public int getDbOrderNoCancelStatus() {
        return dbOrderNoCancelStatus;
    }

    public void setDbOrderNoCancelStatus(int dbOrderNoCancelStatus) {
        this.dbOrderNoCancelStatus = dbOrderNoCancelStatus;
    }

    public boolean isCancelledInfoStatus() {
        return cancelledInfoStatus;
    }

    public void setCancelledInfoStatus(boolean cancelledInfoStatus) {
        this.cancelledInfoStatus = cancelledInfoStatus;
    }

    public boolean isIsCancelledBy() {
        return isCancelledBy;
    }

    public void setIsCancelledBy(boolean isCancelledBy) {
        this.isCancelledBy = isCancelledBy;
    }

    public boolean isIsCashCollected() {
        return isCashCollected;
    }

    public void setIsCashCollected(boolean isCashCollected) {
        this.isCashCollected = isCashCollected;
    }

    public String getOrderCreatedBy() {
        return orderCreatedBy;
    }

    public void setOrderCreatedBy(String orderCreatedBy) {
        this.orderCreatedBy = orderCreatedBy;
    }

    public String getCancellingReasonSno() {
        return cancellingReasonSno;
    }

    public void setCancellingReasonSno(String cancellingReasonSno) {
        this.cancellingReasonSno = cancellingReasonSno;
    }

    public String getCancelSalesOrderNo() {
        return cancelSalesOrderNo;
    }

    public void setCancelSalesOrderNo(String cancelSalesOrderNo) {
        this.cancelSalesOrderNo = cancelSalesOrderNo;
    }
}
