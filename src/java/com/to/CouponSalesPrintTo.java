package com.to;

public class CouponSalesPrintTo {

    private Integer slNo;
    private String couponType;
    private String couponNo;
    private String couponDesc;
    private Float couponRate;

    public CouponSalesPrintTo() {
    }

    public CouponSalesPrintTo(Integer slNo, String couponType, String couponNo, String couponDesc, Float couponRate) {
        this.slNo = slNo;
        this.couponType = couponType;
        this.couponNo = couponNo;
        this.couponDesc = couponDesc;
        this.couponRate = couponRate;
    }

    public Integer getSlNo() {
        return slNo;
    }

    public void setSlNo(Integer slNo) {
        this.slNo = slNo;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo;
    }

    public String getCouponDesc() {
        return couponDesc;
    }

    public void setCouponDesc(String couponDesc) {
        this.couponDesc = couponDesc;
    }

    public Float getCouponRate() {
        return couponRate;
    }

    public void setCouponRate(Float couponRate) {
        this.couponRate = couponRate;
    }

    
}
