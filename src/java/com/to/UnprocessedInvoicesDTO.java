package com.to;

/*@author pranesh*/
public class UnprocessedInvoicesDTO {

    private String craftGroup;
    private String invoiceNumber;
    private String manualBilNumber;
    private Float grossAmt;
    private Float disAmt;
    private Float vatAmt;
    private Float netAmt;
    private Float packAmt;
    private Float totalAmt;
    private String dateTimeValue;
    private String counterUserName;

    public UnprocessedInvoicesDTO() {
    }

    @Override
    public String toString() {
        return "UnprocessedInvoicesDTO{" + "craftGroup=" + craftGroup + ", invoiceNumber=" + invoiceNumber + ", manualBilNumber=" + manualBilNumber + ", grossAmt=" + grossAmt + ", disAmt=" + disAmt + ", vatAmt=" + vatAmt + ", netAmt=" + netAmt + ", packAmt=" + packAmt + ", totalAmt=" + totalAmt + ", dateTimeValue=" + dateTimeValue + ", counterUserName=" + counterUserName + '}';
    }

    public String getCraftGroup() {
        return craftGroup;
    }

    public void setCraftGroup(String craftGroup) {
        this.craftGroup = craftGroup;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getManualBilNumber() {
        return manualBilNumber;
    }

    public void setManualBilNumber(String manualBilNumber) {
        this.manualBilNumber = manualBilNumber;
    }

    public Float getGrossAmt() {
        return grossAmt;
    }

    public void setGrossAmt(Float grossAmt) {
        this.grossAmt = grossAmt;
    }

    public Float getDisAmt() {
        return disAmt;
    }

    public void setDisAmt(Float disAmt) {
        this.disAmt = disAmt;
    }

    public Float getVatAmt() {
        return vatAmt;
    }

    public void setVatAmt(Float vatAmt) {
        this.vatAmt = vatAmt;
    }

    public Float getNetAmt() {
        return netAmt;
    }

    public void setNetAmt(Float netAmt) {
        this.netAmt = netAmt;
    }

    public Float getPackAmt() {
        return packAmt;
    }

    public void setPackAmt(Float packAmt) {
        this.packAmt = packAmt;
    }

    public Float getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Float totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getDateTimeValue() {
        return dateTimeValue;
    }

    public void setDateTimeValue(String dateTimeValue) {
        this.dateTimeValue = dateTimeValue;
    }

    public String getCounterUserName() {
        return counterUserName;
    }

    public void setCounterUserName(String counterUserName) {
        this.counterUserName = counterUserName;
    }

}
