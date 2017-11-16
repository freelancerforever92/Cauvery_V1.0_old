/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.to;

/**
 *
 * @author pranesh
 */
public class VendorNos {

    private String vendorIds;
    private String vendorIdName;

    public VendorNos() {
    }

    @Override
    public String toString() {
        return "VendorNos{" + "vendorIds=" + vendorIds + ", vendorIdName=" + vendorIdName + '}';
    }

    public VendorNos(String vendorIds) {
        this.vendorIds = vendorIds;
    }

    public VendorNos(String vendorIds, String vendorIdName) {
        this.vendorIds = vendorIds;
        this.vendorIdName = vendorIdName;
    }

    public String getVendorIdName() {
        return vendorIdName;
    }

    public void setVendorIdName(String vendorIdName) {
        this.vendorIdName = vendorIdName;
    }

    public String getVendorIds() {
        return vendorIds;
    }

    public void setVendorIds(String vendorIds) {
        this.vendorIds = vendorIds;
    }

}
