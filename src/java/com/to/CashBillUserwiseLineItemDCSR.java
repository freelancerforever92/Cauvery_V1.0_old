package com.to;
/* @author pranesh*/

public class CashBillUserwiseLineItemDCSR {

    String userName;
    private int userAmount;

    public CashBillUserwiseLineItemDCSR() {
    }

    public CashBillUserwiseLineItemDCSR(String userName, int userAmount) {
        this.userName = userName;
        this.userAmount = userAmount;
    }

    @Override
    public String toString() {
        return "CashBillUserwiseLineItemDCSR{" + "userName=" + userName + ", userAmount=" + userAmount + '}';
    }

    public String getuserName() {
        return userName;
    }

    public void setuserName(String userName) {
        this.userName = userName;
    }

    public int getuserAmount() {
        return userAmount;
    }

    public void setuserAmount(int userAmount) {
        this.userAmount = userAmount;
    }

}
