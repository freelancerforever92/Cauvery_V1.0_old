
package com.to;

public class CashBillTimeBasedList {
    String invoiceNumber;
    String item;
    String materialNumber;        
    String craftGroup;                
    String materialDesc;                
    String vendorNumber;                
    String unitPrice;
    String dispPer;              
    String vatPer;
    float disValue;
    float grossValue;                
    float qty;                
    float vatValue;              
    float calcValue;                   
    float packCharge;                
    String dateTime;                    

    
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public String getCraftGroup() {
        return craftGroup;
    }

    public void setCraftGroup(String craftGroup) {
        this.craftGroup = craftGroup;
    }

    public String getMaterialDesc() {
        return materialDesc;
    }

    public void setMaterialDesc(String materialDesc) {
        this.materialDesc = materialDesc;
    }

    public String getVendorNumber() {
        return vendorNumber;
    }

    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public float getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(float grossValue) {
        this.grossValue = grossValue;
    }

    public String getDispPer() {
        return dispPer;
    }

    public void setDispPer(String dispPer) {
        this.dispPer = dispPer;
    }

    public float getDisValue() {
        return disValue;
    }

    public void setDisValue(float disValue) {
        this.disValue = disValue;
    }

    public String getVatPer() {
        return vatPer;
    }

    public void setVatPer(String vatPer) {
        this.vatPer = vatPer;
    }

    public float getVatValue() {
        return vatValue;
    }

    public void setVatValue(float vatValue) {
        this.vatValue = vatValue;
    }

    public float getCalcValue() {
        return calcValue;
    }

    public void setCalcValue(float calcValue) {
        this.calcValue = calcValue;
    }

    public float getPackCharge() {
        return packCharge;
    }

    public void setPackCharge(float packCharge) {
        this.packCharge = packCharge;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

               
}
