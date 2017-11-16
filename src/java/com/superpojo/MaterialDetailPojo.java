/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.superpojo;

/**
 *
 * @author Pranesh
 */
public class MaterialDetailPojo {

    String matno;
    String matdes;
    String craftgroup;
    String plantno;
    String storageloc;
    int distributionChanel;
    float price;
    float standardPrice;
    String createdDate;
    String updatedDate;

    public MaterialDetailPojo() {

    }

    @Override
    public String toString() {
        return "MaterialDetailPojo{" + "matno=" + matno + ", matdes=" + matdes + ", craftgroup=" + craftgroup + ", plantno=" + plantno + ", storageloc=" + storageloc + ", distributionChanel=" + distributionChanel + ", price=" + price + ", standardPrice=" + standardPrice + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + '}';
    }



    public MaterialDetailPojo(int distributionChanel, String matno, String matdes, String craftgroup, String plantno, String storageloc, float price, float standardPrice, String createdDate, String updatedDate) {
        this.matno = matno;
        this.matdes = matdes;
        this.craftgroup = craftgroup;
        this.plantno = plantno;
        this.storageloc = storageloc;
        this.distributionChanel = distributionChanel;
        this.price = price;
        this.standardPrice = standardPrice;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;

    }

    public String getMatno() {
        return matno;
    }

    public void setMatno(String matno) {
        this.matno = matno;
    }

    public String getMatdes() {
        return matdes;
    }

    public void setMatdes(String matdes) {
        this.matdes = matdes;
    }

    public String getCraftgroup() {
        return craftgroup;
    }

    public void setCraftgroup(String craftgroup) {
        this.craftgroup = craftgroup;
    }

    public String getPlantno() {
        return plantno;
    }

    public void setPlantno(String plantno) {
        this.plantno = plantno;
    }

    public String getStorageloc() {
        return storageloc;
    }

    public void setStorageloc(String storageloc) {
        this.storageloc = storageloc;
    }

    public int getDistributionChanel() {
        return distributionChanel;
    }

    public void setDistributionChanel(int distributionChanel) {
        this.distributionChanel = distributionChanel;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(float standardPrice) {
        this.standardPrice = standardPrice;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

}
