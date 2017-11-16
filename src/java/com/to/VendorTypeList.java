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
public class VendorTypeList {

    private String vendorId;
    private String vendorName;

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

    public VendorTypeList(String vendorId, String vendorName) {
        this.vendorId = vendorId;
        this.vendorName = vendorName;
    }

}
