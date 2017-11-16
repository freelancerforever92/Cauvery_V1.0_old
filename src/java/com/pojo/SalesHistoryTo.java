package com.pojo;

/*@author pranesh*/
public class SalesHistoryTo {
//item,material,qty,price,net_amt,vendor,sales_orderno 
    //Item

    private String item;
    private String material;
    private String quantity;
    private String price;
    private String netAmount;
    private String vendor;
    private String salesOrderNo;

    public SalesHistoryTo() {
    }

    @Override
    public String toString() {
        return "SalesHistoryTo{" + "item=" + item + ", material=" + material + ", quantity=" + quantity + ", price=" + price + ", netAmount=" + netAmount + ", vendor=" + vendor + ", salesOrderNo=" + salesOrderNo + '}';
    }

    
    
    public SalesHistoryTo(String item, String material, String quantity, String price, String netAmount, String vendor, String salesOrderNo) {
        this.item = item;
        this.material = material;
        this.quantity = quantity;
        this.price = price;
        this.netAmount = netAmount;
        this.vendor = vendor;
        this.salesOrderNo = salesOrderNo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

}
