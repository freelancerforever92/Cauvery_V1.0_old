package com.coupon;

public class CouponTypesTo {

    int couponId;
    String couponType;

    public CouponTypesTo() {
    }

    public CouponTypesTo(int couponId, String couponType) {
        this.couponId = couponId;
        this.couponType = couponType;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    

    
}
