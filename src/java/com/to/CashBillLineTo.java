package com.to;

import java.util.List;

public class CashBillLineTo {

    private String billCounterNo;
    private List<CashBillLineDetailTo> billLineDetailTos;

    public CashBillLineTo() {
    }

    public CashBillLineTo(String billCounterNo, List<CashBillLineDetailTo> billLineDetailTos) {
        this.billCounterNo = billCounterNo;
        this.billLineDetailTos = billLineDetailTos;
    }

    public String getBillCounterNo() {
        return billCounterNo;
    }

    public void setBillCounterNo(String billCounterNo) {
        this.billCounterNo = billCounterNo;
    }

    public List<CashBillLineDetailTo> getBillLineDetailTos() {
        return billLineDetailTos;
    }

    public void setBillLineDetailTos(List<CashBillLineDetailTo> billLineDetailTos) {
        this.billLineDetailTos = billLineDetailTos;
    }

}
