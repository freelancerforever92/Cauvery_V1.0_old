package com.to;

import java.util.List;
import java.math.BigDecimal;

/*@author pranesh*/
public class CashHeaderDTO {

    private String headerId;
    private String paymentType;
    private String headerDetail;
    private BigDecimal cashBillTotalAmount;
    private List<CashLineItemDTO> cashLineItemDTO;

    public CashHeaderDTO() {
    }

    @Override
    public String toString() {
        return "CashHeaderDTO{" + "headerId=" + headerId + ", paymentType=" + paymentType + ", headerDetail=" + headerDetail + ", cashBillTotalAmount=" + cashBillTotalAmount + ", cashLineItemDTO=" + cashLineItemDTO + '}';
    }

    public CashHeaderDTO(String headerId, String headerDetail, BigDecimal cashBillTotalAmount, String paymentType, List<CashLineItemDTO> cashLineItemDTO) {
        this.headerId = headerId;
        this.headerDetail = headerDetail;
        this.cashBillTotalAmount = cashBillTotalAmount;
        this.paymentType = paymentType;
        this.cashLineItemDTO = cashLineItemDTO;
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

    public List<CashLineItemDTO> getCashLineItemDTO() {
        return cashLineItemDTO;
    }

    public void setCashLineItemDTO(List<CashLineItemDTO> cashLineItemDTO) {
        this.cashLineItemDTO = cashLineItemDTO;
    }

}
