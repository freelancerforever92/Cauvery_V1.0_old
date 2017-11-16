/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pojo;

/**
 *
 * @author pranesh
 */
public class DailyCashReportPojo {

    private String date_time;
    private String invoiceNumber;
    private String cashBillNumber;
    private String paymentType;
    private String userId;
    private float amount;
    private String plantId;

    public DailyCashReportPojo() {
      
    }

    

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCashBillNumber() {
        return cashBillNumber;
    }

    public void setCashBillNumber(String cashBillNumber) {
        this.cashBillNumber = cashBillNumber;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public DailyCashReportPojo(String date_time, String invoiceNumber, String cashBillNumber, String paymentType, String userId, float amount, String plantId) {
        this.date_time = date_time;
        this.invoiceNumber = invoiceNumber;
        this.cashBillNumber = cashBillNumber;
        this.paymentType = paymentType;
        this.userId = userId;
        this.amount = amount;
        this.plantId = plantId;
    }

    @Override
    public String toString() {
        return "DailyCashReportPojo{" + "date_time=" + date_time + ", invoiceNumber=" + invoiceNumber + ", cashBillNumber=" + cashBillNumber + ", paymentType=" + paymentType + ", userId=" + userId + ", amount=" + amount + ", plantId=" + plantId + '}';
    }

     

}
