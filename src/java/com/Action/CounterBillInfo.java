package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import com.to.PurchaseOrderLineTo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CounterBillInfo extends ActionSupport {

    private String txtCancellingCounterBillNo;
    private String cashBillOrderId;
    DaoClass daoClass = new DaoClass();
    ResultSet getCounterBillRs = null;
    ResultSet cashBillOrderIdRs = null;
    ResultSet getCashCounterDataRs = null;
    private String empId;
    private String empName;
    private String date;
    private String packValue;
    private String totalAmt;
    private String textAmt;
    private String paymentType;
    private String cashProcessedBy;
    List counterBillList = new ArrayList<>();
    private String vendorId;
    private int salesNoCountVal;
    private int counterBillInfoVal;

    public String getInfoCounterBill() throws SQLException {
        try {
            String checkNoQry = "select count(*) as count from pos.header where sales_orderno='" + txtCancellingCounterBillNo + "'";
            salesNoCountVal = daoClass.Fun_Int(checkNoQry);
            if (salesNoCountVal > 0) {
                String Qry = "SELECT lineitem.material,lineitem.descrip,lineitem.vendor,lineitem.qty,lineitem.price,lineitem.prc_value,lineitem.discount_percentage,lineitem.discount_value,lineitem.vat_percentage,lineitem.vat_value,lineitem.calcu_value  FROM pos.header header INNER JOIN  pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno) WHERE (header.sales_orderno ='" + txtCancellingCounterBillNo + "') AND (header.cancelFlag = 'N')";
                getCounterBillRs = daoClass.Fun_Resultset(Qry);
                while (getCounterBillRs.next()) {
                    counterBillInfoVal = 1;
                    counterBillList.add(new PurchaseOrderLineTo(getCounterBillRs.getString("material"),
                            getCounterBillRs.getString("vendor"), getCounterBillRs.getDouble("qty"),
                            getCounterBillRs.getFloat("price"), getCounterBillRs.getFloat("lineitem.prc_value"),
                            getCounterBillRs.getString("descrip"), getCounterBillRs.getDouble("discount_percentage"),
                            getCounterBillRs.getFloat("lineitem.discount_value"),
                            getCounterBillRs.getFloat("vat_percentage"), getCounterBillRs.getFloat("lineitem.vat_value"), getCounterBillRs.getFloat("lineitem.calcu_value")));
                }
                String getCreatedEmpId_Qry = "SELECT emp_name FROM pos.emp_master where emp_id=(SELECT emp_id FROM pos.header where sales_orderno='" + txtCancellingCounterBillNo + "')";
                empName = daoClass.Fun_Str(getCreatedEmpId_Qry);
                String getDate_Qry = "SELECT DATE_FORMAT(lineitem.date_time,'%d-%m-%Y %r')as date_time FROM pos.lineitem where sales_orderno='" + txtCancellingCounterBillNo + "';";
                date = daoClass.Fun_Str(getDate_Qry);
                String getPackValue = "SELECT pck_charge FROM pos.header where sales_orderno='" + txtCancellingCounterBillNo + "'";
                packValue = daoClass.Fun_Str(getPackValue);
                String totalBillAmtQry = "  SELECT bil_amt FROM pos.header where sales_orderno='" + txtCancellingCounterBillNo + "'";
                totalAmt = daoClass.Fun_Str(totalBillAmtQry);
                String textAmtQry = " SELECT txt_bilAmt FROM pos.header where sales_orderno='" + txtCancellingCounterBillNo + "'";
                textAmt = daoClass.Fun_Str(textAmtQry);
                String getCashProcessDataQry = "SELECT empMaster.emp_name,cashHeaderMaster.paymentType FROM( pos.cashbill_header_master cashHeaderMaster INNER JOIN pos.emp_master empMaster ON (cashHeaderMaster.user_id = empMaster.emp_id))INNER JOIN pos.cashbill_lineitem_master cashLineitemMaster ON (cashLineitemMaster.cashBill_id =cashHeaderMaster.cashBill_id)WHERE (cashLineitemMaster.counterbill_no ='" + txtCancellingCounterBillNo + "')";
                getCashCounterDataRs = daoClass.Fun_Resultset(getCashProcessDataQry);
                while (getCashCounterDataRs.next()) {
                    cashProcessedBy = getCashCounterDataRs.getString("emp_name").trim();
                    paymentType = getCashCounterDataRs.getString("paymentType").trim();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CounterBillInfo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoClass.closeResultSet(getCounterBillRs);
            daoClass.closeResultSet(cashBillOrderIdRs);
            daoClass.closeResultSet(getCashCounterDataRs);
        }
        return SUCCESS;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCashProcessedBy() {
        return cashProcessedBy;
    }

    public void setCashProcessedBy(String cashProcessedBy) {
        this.cashProcessedBy = cashProcessedBy;
    }

    public String getTxtCancellingCounterBillNo() {
        return txtCancellingCounterBillNo;
    }

    public void setTxtCancellingCounterBillNo(String txtCancellingCounterBillNo) {
        this.txtCancellingCounterBillNo = txtCancellingCounterBillNo;
    }

    public List getCounterBillList() {
        return counterBillList;
    }

    public void setCounterBillList(List counterBillList) {
        this.counterBillList = counterBillList;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getCashBillOrderId() {
        return cashBillOrderId;
    }

    public void setCashBillOrderId(String cashBillOrderId) {
        this.cashBillOrderId = cashBillOrderId;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPackValue() {
        return packValue;
    }

    public void setPackValue(String packValue) {
        this.packValue = packValue;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getTextAmt() {
        return textAmt;
    }

    public void setTextAmt(String textAmt) {
        this.textAmt = textAmt;
    }

    public int getCounterBillInfoVal() {
        return counterBillInfoVal;
    }

    public void setCounterBillInfoVal(int counterBillInfoVal) {
        this.counterBillInfoVal = counterBillInfoVal;
    }

    public int getSalesNoCountVal() {
        return salesNoCountVal;
    }

    public void setSalesNoCountVal(int salesNoCountVal) {
        this.salesNoCountVal = salesNoCountVal;
    }
}
