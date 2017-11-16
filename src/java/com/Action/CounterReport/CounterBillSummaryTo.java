package com.Action.CounterReport;

/*@author pranesh*/
public class CounterBillSummaryTo {

    private String salesOrderNumber;
    private String manualBillNumber;
    private int itemValue;
    private String materialNumber;
    private String materialDescription;
    private float quantity;
    private String materialCraftGroup;
    private float calculatedValue;
    private String employeeId;
    private String showRoomId;
    private float discountPercentage;
    private float discountValue;
    private float vatPercentage;
    private float vatValue;
    private float packingCharge;
    private String dateTime;

    public CounterBillSummaryTo(String salesOrderNumber, String manualBillNumber, int itemValue, String materialNumber, String materialDescription, float quantity, String materialCraftGroup, float calculatedValue, String employeeId, String showRoomId, float discountPercentage, float discountValue, float vatPercentage, float vatValue, float packingCharge, String dateTime) {
        this.salesOrderNumber = salesOrderNumber;
        this.manualBillNumber = manualBillNumber;
        this.itemValue = itemValue;
        this.materialNumber = materialNumber;
        this.materialDescription = materialDescription;
        this.quantity = quantity;
        this.materialCraftGroup = materialCraftGroup;
        this.calculatedValue = calculatedValue;
        this.employeeId = employeeId;
        this.showRoomId = showRoomId;
        this.discountPercentage = discountPercentage;
        this.discountValue = discountValue;
        this.vatPercentage = vatPercentage;
        this.vatValue = vatValue;
        this.packingCharge = packingCharge;
        this.dateTime = dateTime;
    }

    public String getSalesOrderNumber() {
        return salesOrderNumber;
    }

    public void setSalesOrderNumber(String salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }

    public String getManualBillNumber() {
        return manualBillNumber;
    }

    public void setManualBillNumber(String manualBillNumber) {
        this.manualBillNumber = manualBillNumber;
    }

    public int getItemValue() {
        return itemValue;
    }

    public void setItemValue(int itemValue) {
        this.itemValue = itemValue;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getMaterialDescription() {
        return materialDescription;
    }

    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public String getMaterialCraftGroup() {
        return materialCraftGroup;
    }

    public void setMaterialCraftGroup(String materialCraftGroup) {
        this.materialCraftGroup = materialCraftGroup;
    }

    public float getCalculatedValue() {
        return calculatedValue;
    }

    public void setCalculatedValue(float calculatedValue) {
        this.calculatedValue = calculatedValue;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getShowRoomId() {
        return showRoomId;
    }

    public void setShowRoomId(String showRoomId) {
        this.showRoomId = showRoomId;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public float getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(float discountValue) {
        this.discountValue = discountValue;
    }

    public float getVatPercentage() {
        return vatPercentage;
    }

    public void setVatPercentage(float vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    public float getVatValue() {
        return vatValue;
    }

    public void setVatValue(float vatValue) {
        this.vatValue = vatValue;
    }

    public float getPackingCharge() {
        return packingCharge;
    }

    public void setPackingCharge(float packingCharge) {
        this.packingCharge = packingCharge;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
