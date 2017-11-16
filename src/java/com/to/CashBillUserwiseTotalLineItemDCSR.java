/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.to;

/**
 *
 * @author pranesh
 */
public class CashBillUserwiseTotalLineItemDCSR {

    String finalName;
    private int finalAmount;

    @Override
    public String toString() {
        return "CashBillUserwiseTotalLineItemDCSR{" + "finalName=" + finalName + ", finalAmount=" + finalAmount + '}';
    }

    public CashBillUserwiseTotalLineItemDCSR() {
    }

    public CashBillUserwiseTotalLineItemDCSR(String finalName, int finalAmount) {
        this.finalName = finalName;
        this.finalAmount = finalAmount;
    }

    public String getFinalName() {
        return finalName;
    }

    public void setFinalName(String finalName) {
        this.finalName = finalName;
    }

    public int getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(int finalAmount) {
        this.finalAmount = finalAmount;
    }

}
