/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.to;
import java.util.List;
/**
 *
 * @author Administrator
 */
public class CashBillHeaderTo {
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
    private String discountApproval;
    private Float billAmount;
    private String billAmountWord;
    private Float netBillAmount;
    private String branchName;
    private Float packageCharge;
    private String empId;
    private String empName;
    private Float totalAmt;
    private Float couponRedemValue;
    private List<CashBillLineTo> billLineTos;

    public CashBillHeaderTo() {
    }

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

    public String getDiscountApproval() {
        return discountApproval;
    }

    public void setDiscountApproval(String discountApproval) {
        this.discountApproval = discountApproval;
    }

    public Float getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(Float billAmount) {
        this.billAmount = billAmount;
    }

    public String getBillAmountWord() {
        return billAmountWord;
    }

    public void setBillAmountWord(String billAmountWord) {
        this.billAmountWord = billAmountWord;
    }

    public Float getNetBillAmount() {
        return netBillAmount;
    }

    public void setNetBillAmount(Float netBillAmount) {
        this.netBillAmount = netBillAmount;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Float getPackageCharge() {
        return packageCharge;
    }

    public void setPackageCharge(Float packageCharge) {
        this.packageCharge = packageCharge;
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

    public Float getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Float totalAmt) {
        this.totalAmt = totalAmt;
    }
    
    public Float getCouponRedemValue() {
        return couponRedemValue;
    }

    public void setCouponRedemValue(Float couponRedemValue) {
        this.couponRedemValue = couponRedemValue;
    }

    public List<CashBillLineTo> getBillLineTos() {
        return billLineTos;
    }

    public void setBillLineTos(List<CashBillLineTo> billLineTos) {
        this.billLineTos = billLineTos;
    }
    
    
}
