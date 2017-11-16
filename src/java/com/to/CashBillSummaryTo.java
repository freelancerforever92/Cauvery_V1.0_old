package com.to;

/*@author Administrator*/
public class CashBillSummaryTo {

    private String cashBillNumber;
    private String counterBillNumber;
    private String empId;
    private String paymentType;
    private float totalBillAmount;
    private String createdDateTime;
    private String craftGroupName;
    private String plantId;
//    public CashBillSummaryTo(String cashBillNumber, String counterBillNumber, float totalBillAmount, String createdDateTime, String craftGroupName) {
//        this.cashBillNumber = cashBillNumber;
//        this.counterBillNumber = counterBillNumber;
//        this.totalBillAmount = totalBillAmount;
//        this.createdDateTime = createdDateTime;
//        this.craftGroupName = craftGroupName;
//    }

    public CashBillSummaryTo(String cashBillNumber, String counterBillNumber, String empId, String paymentType, float totalBillAmount, String createdDateTime, String plantId) {
        this.cashBillNumber = cashBillNumber;
        this.counterBillNumber = counterBillNumber;
        this.empId = empId;
        this.paymentType = paymentType;
        this.totalBillAmount = totalBillAmount;
        this.createdDateTime = createdDateTime;
        this.plantId = plantId;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    
    
    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCraftGroupName() {
        return craftGroupName;
    }

    public void setCraftGroupName(String craftGroupName) {
        this.craftGroupName = craftGroupName;
    }

    public String getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(String createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getCashBillNumber() {
        return cashBillNumber;
    }

    public void setCashBillNumber(String cashBillNumber) {
        this.cashBillNumber = cashBillNumber;
    }

    public String getCounterBillNumber() {
        return counterBillNumber;
    }

    public void setCounterBillNumber(String counterBillNumber) {
        this.counterBillNumber = counterBillNumber;
    }

    public float getTotalBillAmount() {
        return totalBillAmount;
    }

    public void setTotalBillAmount(float totalBillAmount) {
        this.totalBillAmount = totalBillAmount;
    }

}
