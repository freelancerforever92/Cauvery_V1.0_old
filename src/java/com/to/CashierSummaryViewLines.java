package com.to;

public class CashierSummaryViewLines {

    private String invoiceNumber;
    private Float billAmount;

    public CashierSummaryViewLines() {
    }

    public CashierSummaryViewLines(String invoiceNumber, Float billAmount) {
        this.invoiceNumber = invoiceNumber;
        this.billAmount = billAmount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Float getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Float billAmount) {
        this.billAmount = billAmount;
    }
}
