/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coupon;

/**
 *
 * @author Administrator
 */
public class CouponDetailsTo {

    private Integer couponId;
    private String couponType;
    private String couponDescp;
    private int couponNo;
    private Float couponRate;

    public CouponDetailsTo() {
    }

    public CouponDetailsTo(Integer couponId, String couponType, String couponDescp, int couponNo, Float couponRate) {
        this.couponId = couponId;
        this.couponType = couponType;
        this.couponDescp = couponDescp;
        this.couponNo = couponNo;
        this.couponRate = couponRate;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponDescp() {
        return couponDescp;
    }

    public void setCouponDescp(String couponDescp) {
        this.couponDescp = couponDescp;
    }

    public int getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(int couponNo) {
        this.couponNo = couponNo;
    }

    public Float getCouponRate() {
        return couponRate;
    }

    public void setCouponRate(Float couponRate) {
        this.couponRate = couponRate;
    }

    
    

}
