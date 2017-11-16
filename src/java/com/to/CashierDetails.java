package com.to;

/*@author pranesh*/
public class CashierDetails {

    private int cashierPk;
    private String cashierEmpId;
    private String cashierName;

    public CashierDetails() {
    }

    @Override
    public String toString() {
        return "CashierDetails{" + "cashierPk=" + cashierPk + ", cashierEmpId=" + cashierEmpId + ", cashierName=" + cashierName + '}';
    }

    public CashierDetails(int cashierPk, String cashierEmpId, String cashierName) {
        this.cashierPk = cashierPk;
        this.cashierEmpId = cashierEmpId;
        this.cashierName = cashierName;
    }

    public int getCashierPk() {
        return cashierPk;
    }

    public void setCashierPk(int cashierPk) {
        this.cashierPk = cashierPk;
    }

    public String getCashierEmpId() {
        return cashierEmpId;
    }

    public void setCashierEmpId(String cashierEmpId) {
        this.cashierEmpId = cashierEmpId;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

}
