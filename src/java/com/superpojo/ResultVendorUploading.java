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
public class ResultVendorUploading {

    String vendorId;
    String vendorName;
    String resField;

    @Override
    public String toString() {
        return "ResultVendorUploading{" + "vendorId=" + vendorId + ", vendorName=" + vendorName + ", resField=" + resField + '}';
    }

    public ResultVendorUploading(String vendorId, String vendorName, String resField) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
        this.resField = resField;
    }

    public ResultVendorUploading() {
    }

   

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

   

    public String getResField() {
        return resField;
    }

    public void setResField(String resField) {
        this.resField = resField;
    }
   

}
