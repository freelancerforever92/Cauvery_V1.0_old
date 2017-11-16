package com.schudeler;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class LineItem {

    public int id;
    public String material;
    public String materialCraftGroup;
    public String description;
    public String vendor;
    public String quantity;
    public String price;
    public String prcValue;
    public String discount;
    public String discountPer;
    public String vat;
    public String vatPer;
    public String calcuValu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterialCraftGroup() {
        return materialCraftGroup;
    }

    public void setMaterialCraftGroup(String materialCraftGroup) {
        this.materialCraftGroup = materialCraftGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPrcValue() {
        return prcValue;
    }

    public void setPrcValue(String prcValue) {
        this.prcValue = prcValue;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscountPer() {
        return discountPer;
    }

    public void setDiscountPer(String discountPer) {
        this.discountPer = discountPer;
    }

    public String getVat() {
        return vat;
    }

    public void setVat(String vat) {
        this.vat = vat;
    }

    public String getVatPer() {
        return vatPer;
    }

    public void setVatPer(String vatPer) {
        this.vatPer = vatPer;
    }

    public String getCalcuValu() {
        return calcuValu;
    }

    public void setCalcuValu(String calcuValu) {
        this.calcuValu = calcuValu;
    }

    @Override
    public String toString() {
        return "LineItem{" + "id=" + id + ", material=" + material + ", materialCraftGroup=" + materialCraftGroup + ", description=" + description + ", vendor=" + vendor + ", quantity=" + quantity + ", price=" + price + ", prcValue=" + prcValue + ", discount=" + discount + ", discountPer=" + discountPer + ", vat=" + vat + ", vatPer=" + vatPer + ", calcuValu=" + calcuValu + '}';
    }
    
    
}
