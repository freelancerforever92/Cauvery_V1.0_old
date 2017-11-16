package com.to;

import java.util.*;

public class CouponSalesHeaderTo {

    private String salesOrderNo;
    private String dateTime;
    private String customeName;
    private String streetOne;
    private String streetTwo;
    private String country;
    private String state;
    private String city;
    private String zipCode;
    private String showRoom;
    private String branchName;
    private String empId;
    private String empName;
    private Float totalValue;
    private String totalValueInText;
    private List<CouponSalesPrintTo> couponSalesPrintTos;

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCustomeName() {
        return customeName;
    }

    public void setCustomeName(String customeName) {
        this.customeName = customeName;
    }

    public String getStreetOne() {
        return streetOne;
    }

    public void setStreetOne(String streetOne) {
        this.streetOne = streetOne;
    }

    public String getStreetTwo() {
        return streetTwo;
    }

    public void setStreetTwo(String streetTwo) {
        this.streetTwo = streetTwo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getShowRoom() {
        return showRoom;
    }

    public void setShowRoom(String showRoom) {
        this.showRoom = showRoom;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public List<CouponSalesPrintTo> getCouponSalesPrintTos() {
        return couponSalesPrintTos;
    }

    public Float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(Float totalValue) {
        this.totalValue = totalValue;
    }
    
    public void setCouponSalesPrintTos(List<CouponSalesPrintTo> couponSalesPrintTos) {
        this.couponSalesPrintTos = couponSalesPrintTos;
    }

    public String getTotalValueInText() {
        return totalValueInText;
    }

    public void setTotalValueInText(String totalValueInText) {
        this.totalValueInText = totalValueInText;
    }

    
    
}
