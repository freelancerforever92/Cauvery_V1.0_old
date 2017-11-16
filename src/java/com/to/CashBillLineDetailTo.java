/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.to;

/**
 *
 * @author Administrator
 */
public class CashBillLineDetailTo {

    private Integer itemNo;
    private String matrialNo;
    private Float qty;
    private Float rate;
    private Float value;
    private String description;//25.05.14
    private Float discountPercentage;//25.05.14
    private Float discountValue;//25.05.14
    private Float vatPercentage;//25.05.14
    private Float vatValue;//25.05.14
    private Float packingValue;

    public CashBillLineDetailTo() {
    }

    public CashBillLineDetailTo(Integer itemNo, String matrialNo, Float qty, String description, Float rate, Float value, Float discountPercentage, Float discountValue, Float vatPercentage, Float vatValue, Float packingValue) {
        this.itemNo = itemNo;
        this.matrialNo = matrialNo;
        this.qty = qty;
        this.rate = rate;
        this.value = value;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.discountValue = discountValue;
        this.vatPercentage = vatPercentage;
        this.vatValue = vatValue;
        this.packingValue = packingValue;
    }

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getMatrialNo() {
        return matrialNo;
    }

    public void setMatrialNo(String matrialNo) {
        this.matrialNo = matrialNo;
    }

    public Float getQty() {
        return qty;
    }

    public void setQty(Float qty) {
        this.qty = qty;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Float getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Float discountValue) {
        this.discountValue = discountValue;
    }

    public Float getVatPercentage() {
        return vatPercentage;
    }

    public void setVatPercentage(Float vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    public Float getVatValue() {
        return vatValue;
    }

    public void setVatValue(Float vatValue) {
        this.vatValue = vatValue;
    }

    public Float getPackingValue() {
        return packingValue;
    }

    public void setPackingValue(Float packingValue) {
        this.packingValue = packingValue;
    }

}
