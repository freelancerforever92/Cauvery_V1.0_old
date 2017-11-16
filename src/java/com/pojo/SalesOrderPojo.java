package com.pojo;

/*@author pranesh*/
public class SalesOrderPojo {

    private String salesOrderNumber;
    private String showRoomId;
    private String employeeId;
    private Float billAmount;

    @Override
    public String toString() {
        return "SalesOrderPojo{" + "salesOrderNumber=" + salesOrderNumber + ", showRoomId=" + showRoomId + ", employeeId=" + employeeId + ", billAmount=" + billAmount + '}';
    }

    public SalesOrderPojo(String salesOrderNumber, String showRoomId, String employeeId, Float billAmount) {
        this.salesOrderNumber = salesOrderNumber;
        this.showRoomId = showRoomId;
        this.employeeId = employeeId;
        this.billAmount = billAmount;
    }

    public String getSalesOrderNumber() {
        return salesOrderNumber;
    }

    public void setSalesOrderNumber(String salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }

    public String getShowRoomId() {
        return showRoomId;
    }

    public void setShowRoomId(String showRoomId) {
        this.showRoomId = showRoomId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Float getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Float billAmount) {
        this.billAmount = billAmount;
    }

}
