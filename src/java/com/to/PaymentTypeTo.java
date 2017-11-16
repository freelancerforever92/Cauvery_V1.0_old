package com.to;

public class PaymentTypeTo {

    private int payTypePk;
    private String paytypeText;
    private String selectedPaymentTypeValue;

    public PaymentTypeTo() {
    }

    public PaymentTypeTo(int payTypePk, String paytypeText, String selectedPaymentTypeValue) {
        this.payTypePk = payTypePk;
        this.paytypeText = paytypeText;
        this.selectedPaymentTypeValue = selectedPaymentTypeValue;
    }

    public int getPayTypePk() {
        return payTypePk;
    }

    public void setPayTypePk(int payTypePk) {
        this.payTypePk = payTypePk;
    }

    public String getPaytypeText() {
        return paytypeText;
    }

    public void setPaytypeText(String paytypeText) {
        this.paytypeText = paytypeText;
    }

    public String getSelectedPaymentTypeValue() {
        return selectedPaymentTypeValue;
    }

    public void setSelectedPaymentTypeValue(String selectedPaymentTypeValue) {
        this.selectedPaymentTypeValue = selectedPaymentTypeValue;
    }

}
