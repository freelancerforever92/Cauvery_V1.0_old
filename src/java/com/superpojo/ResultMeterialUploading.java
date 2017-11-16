/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superpojo;

/**
 *
 * @author JAVA
 */
public class ResultMeterialUploading {

    String meterialNo;
    String meterialDesc;
    String resField;
    String VendorId;
    String craftGroup;
    String resVendorField;

    @Override
    public String toString() {
        return "ResultMeterialUploading{" + "meterialNo=" + meterialNo + ", meterialDesc=" + meterialDesc + ", resField=" + resField + ", VendorId=" + VendorId + ", craftGroup=" + craftGroup + ", resVendorField=" + resVendorField + '}';
    }

    

    public ResultMeterialUploading(String meterialNo, String meterialDesc, String resField) {
        this.meterialNo = meterialNo;
        this.meterialDesc = meterialDesc;
        this.resField = resField;
    }

    public ResultMeterialUploading(String meterialNo, String VendorId, String craftGroup, String resVendorField) {
        this.meterialNo = meterialNo;
        this.VendorId = VendorId;
        this.craftGroup = craftGroup;
        this.resVendorField = resVendorField;
    }

    public String getMeterialNo() {
        return meterialNo;
    }

    public void setMeterialNo(String meterialNo) {
        this.meterialNo = meterialNo;
    }

    public String getMeterialDesc() {
        return meterialDesc;
    }

    public void setMeterialDesc(String meterialDesc) {
        this.meterialDesc = meterialDesc;
    }

    public String getResField() {
        return resField;
    }

    public void setResField(String resField) {
        this.resField = resField;
    }

    public String getVendorId() {
        return VendorId;
    }

    public void setVendorId(String VendorId) {
        this.VendorId = VendorId;
    }

    public String getCraftGroup() {
        return craftGroup;
    }

    public void setCraftGroup(String craftGroup) {
        this.craftGroup = craftGroup;
    }

    public String getResVendorField() {
        return resVendorField;
    }

    public void setResVendorField(String resVendorField) {
        this.resVendorField = resVendorField;
    }
    
   
    
    

    
    
    
   

}
