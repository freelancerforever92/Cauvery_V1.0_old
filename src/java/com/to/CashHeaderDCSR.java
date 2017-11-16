package com.to;
/*@author pranesh*/
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
public class CashHeaderDCSR {

    private String headerId;
    private String paymentType;
    private String headerDetail;
    private BigDecimal cashBillTotalAmount;
    private List<CashLineItemDCSR> cashLineItemDCSR;
    private List<CashBillUserwiseLineItemDCSR> cashBillUserwiseLineItemDCSR;
    private HashSet<CashBillUserwiseTotalLineItemDCSR> cashBillUserwiseTotalLineItemDCSR;
    private float grossTotal;
    private float netTotal;
    private float disToatl;
    private float vattotal;
    private float packTaltal;
    private float finalTatal;
    private String empName;
    private String empAmount;

    public List<CashLineItemDCSR> getCashLineItemDCSR() {
        return cashLineItemDCSR;
    }

    public void setCashLineItemDCSR(List<CashLineItemDCSR> cashLineItemDCSR) {
        this.cashLineItemDCSR = cashLineItemDCSR;
    }

    public float getGrossTotal() {
        return grossTotal;
    }

    public void setGrossTotal(float grossTotal) {
        this.grossTotal = grossTotal;
    }

    public float getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(float netTotal) {
        this.netTotal = netTotal;
    }

    public float getDisToatl() {
        return disToatl;
    }

    public void setDisToatl(float disToatl) {
        this.disToatl = disToatl;
    }

    public float getVattotal() {
        return vattotal;
    }

    public void setVattotal(float vattotal) {
        this.vattotal = vattotal;
    }

    public float getPackTaltal() {
        return packTaltal;
    }

    public void setPackTaltal(float packTaltal) {
        this.packTaltal = packTaltal;
    }

    public float getFinalTatal() {
        return finalTatal;
    }

    public void setFinalTatal(float finalTatal) {
        this.finalTatal = finalTatal;
    }

    public CashHeaderDCSR() {
    }

    @Override
    public String toString() {
        return "CashHeaderDTO{" + "headerId=" + headerId + ", paymentType=" + paymentType + ", headerDetail=" + headerDetail + ", cashBillTotalAmount=" + cashBillTotalAmount + ", cashLineItemDTO=" + cashLineItemDCSR + '}';
    }

    public CashHeaderDCSR(String headerId, String headerDetail, BigDecimal cashBillTotalAmount, String paymentType, List<CashLineItemDCSR> cashLineItemDCSR) {
        this.headerId = headerId;
        this.headerDetail = headerDetail;
        this.cashBillTotalAmount = cashBillTotalAmount;
        this.paymentType = paymentType;
        this.cashLineItemDCSR = cashLineItemDCSR;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public BigDecimal getCashBillTotalAmount() {
        return cashBillTotalAmount;
    }

    public void setCashBillTotalAmount(BigDecimal cashBillTotalAmount) {
        this.cashBillTotalAmount = cashBillTotalAmount;
    }

    public String getHeaderId() {
        return headerId;
    }

    public void setHeaderId(String headerId) {
        this.headerId = headerId;
    }

    public String getHeaderDetail() {
        return headerDetail;
    }

    public void setHeaderDetail(String headerDetail) {
        this.headerDetail = headerDetail;
    }

    public List<CashLineItemDCSR> getCashLineItemDTO() {
        return cashLineItemDCSR;
    }

    public void setCashLineItemDTO(List<CashLineItemDCSR> cashLineItemDCSR) {
        this.cashLineItemDCSR = cashLineItemDCSR;
    }

    public List<CashBillUserwiseLineItemDCSR> getCashBillUserwiseLineItemDCSR() {
        return cashBillUserwiseLineItemDCSR;
    }

    public void setCashBillUserwiseLineItemDCSR(List<CashBillUserwiseLineItemDCSR> cashBillUserwiseLineItemDCSR) {
        this.cashBillUserwiseLineItemDCSR = cashBillUserwiseLineItemDCSR;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAmount() {
        return empAmount;
    }

    public void setEmpAmount(String empAmount) {
        this.empAmount = empAmount;
    }

    public HashSet<CashBillUserwiseTotalLineItemDCSR> getCashBillUserwiseTotalLineItemDCSR() {
        return cashBillUserwiseTotalLineItemDCSR;
    }

    public void setCashBillUserwiseTotalLineItemDCSR(HashSet<CashBillUserwiseTotalLineItemDCSR> cashBillUserwiseTotalLineItemDCSR) {
        this.cashBillUserwiseTotalLineItemDCSR = cashBillUserwiseTotalLineItemDCSR;
    }

}
