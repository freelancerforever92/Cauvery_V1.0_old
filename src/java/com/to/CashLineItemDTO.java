package com.to;

import java.math.BigDecimal;

/*@author pranesh*/
public class CashLineItemDTO {

    private Long cashInvoiceNumber;
    private String manualBillNumber;
    private BigDecimal cashBillAmount;
    private String counterNoName;
    private Float cashBillAmountFloat;
    private String processedDate;
   private float cashBillAmountWROFloat;
   private String craftGroupName;

    public float getCashBillAmountWROFloat() {
        return cashBillAmountWROFloat;
    }

    public void setCashBillAmountWROFloat(float cashBillAmountWROFloat) {
        this.cashBillAmountWROFloat = cashBillAmountWROFloat;
    }

    public CashLineItemDTO() {
    }

    @Override
    public String toString() {
        return "CashLineItemDTO{" + "cashInvoiceNumber=" + cashInvoiceNumber + ", manualBillNumber=" + manualBillNumber + ", cashBillAmount=" + cashBillAmount + ", counterNoName=" + counterNoName + ", cashBillAmountFloat=" + cashBillAmountFloat + ", processedDate=" + processedDate + '}';
    }

    public String getProcessedDate() {
        return processedDate;
    }

    public void setProcessedDate(String processedDate) {
        this.processedDate = processedDate;
    }

    
    
    
    
    public String getManualBillNumber() {
        return manualBillNumber;
    }

    public void setManualBillNumber(String manualBillNumber) {
        this.manualBillNumber = manualBillNumber;
    }

    public String getCounterNoName() {
        return counterNoName;
    }

    public void setCounterNoName(String counterNoName) {
        this.counterNoName = counterNoName;
    }

    public Float getCashBillAmountFloat() {
        return cashBillAmountFloat;
    }

    public void setCashBillAmountFloat(Float cashBillAmountFloat) {
        this.cashBillAmountFloat = cashBillAmountFloat;
    }

    public CashLineItemDTO(Long cashInvoiceNumber, BigDecimal cashBillAmount) {
        this.cashInvoiceNumber = cashInvoiceNumber;
        this.cashBillAmount = cashBillAmount;
    }

    public Long getCashInvoiceNumber() {
        return cashInvoiceNumber;
    }

    public void setCashInvoiceNumber(Long cashInvoiceNumber) {
        this.cashInvoiceNumber = cashInvoiceNumber;
    }

    public BigDecimal getCashBillAmount() {
        return cashBillAmount;
    }

    public void setCashBillAmount(BigDecimal cashBillAmount) {
        this.cashBillAmount = cashBillAmount;
    }
     public String getCraftGroupName() {
        return craftGroupName;
    }

    public void setCraftGroupName(String craftGroupName) {
        this.craftGroupName = craftGroupName;
    }

}
