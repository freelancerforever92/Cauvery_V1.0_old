package com.to;

public class PurchaseOrderLineTo {

    private Integer itemNo;
    private String matrialNo;
    private String vendor;
    private Double qty;
    private Float rate;
    private Float value;
    private String description;
    private Double discountPercentage;
    private Float discountValue;
    private Float vattPercentage;
    private Float vattValue;
    private Float calculatedValue;

    public PurchaseOrderLineTo() {
        super();
    }

    public PurchaseOrderLineTo(Integer itemNo, String matrialNo, String description, Double qty, Float rate, Float value, Double discountPercentage, Float discountValue, Float vattPercentage, Float vattValue, Float calculatedValue, String vendor) {
        this.itemNo = itemNo;
        this.matrialNo = matrialNo;
        this.qty = qty;
        this.rate = rate;
        this.value = value;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.discountValue = discountValue;
        this.vattPercentage = vattPercentage;
        this.vattValue = vattValue;
        this.calculatedValue = calculatedValue;
        this.vendor = vendor;
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

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getQty() {
        return qty;
    }

    public void setQty(Double qty) {
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

    public Double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Float getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Float discountValue) {
        this.discountValue = discountValue;
    }

    public Float getVattPercentage() {
        return vattPercentage;
    }

    public void setVattPercentage(Float vattPercentage) {
        this.vattPercentage = vattPercentage;
    }

    public Float getVattValue() {
        return vattValue;
    }

    public void setVattValue(Float vattValue) {
        this.vattValue = vattValue;
    }

    public Float getCalculatedValue() {
        return calculatedValue;
    }

    public void setCalculatedValue(Float calculatedValue) {
        this.calculatedValue = calculatedValue;
    }

    public PurchaseOrderLineTo(String matrialNo, String vendor, Double qty, Float rate, Float value, String description, Double discountPercentage, Float discountValue, Float vattPercentage, Float vattValue, Float calculatedValue) {
        this.matrialNo = matrialNo;
        this.vendor = vendor;
        this.qty = qty;
        this.rate = rate;
        this.value = value;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.discountValue = discountValue;
        this.vattPercentage = vattPercentage;
        this.vattValue = vattValue;
        this.calculatedValue = calculatedValue;
    }

    @Override
    public String toString() {
        return "PurchaseOrderLineTo{" + "matrialNo=" + matrialNo + ", vendor=" + vendor + ", qty=" + qty + ", rate=" + rate + ", value=" + value + ", description=" + description + ", discountPercentage=" + discountPercentage + ", discountValue=" + discountValue + ", vattPercentage=" + vattPercentage + ", vattValue=" + vattValue + ", calculatedValue=" + calculatedValue + '}';
    }
}
