/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.to;

import java.util.Comparator;


public class SalesIntimationList {

    private String vendorNumber;
    private String vendorName;
    private String description;
    private String diskPer;
    private float packAmt;
    private float qty;
    private float rate;
    private float grossAmt;
    private float discAmt;
    private float netAmt;
    private String dateTime;
    private String billNo;
    private String manualBilNo;
    private String craftGroup;
    private String craftGroupName;
    private String materialId;
    private String paymentType;
    
    private String quantity;
    private String price;

    //Summary
    public SalesIntimationList(String vendorNumber, String vendorName, String craftGroup, String dateTime, String billNo, String materialId, String description, float qty, float rate, float grossAmt, float discAmt, float netAmt) {
        this.vendorNumber = vendorNumber;
        this.vendorName = vendorName;
        this.description = description;
        this.qty = qty;
        this.rate = rate;
        this.grossAmt = grossAmt;
        this.discAmt = discAmt;
        this.netAmt = netAmt;
        this.dateTime = dateTime;
        this.billNo = billNo;
        this.materialId = materialId;
        this.craftGroup = craftGroup;
    }

    public String getCraftGroupName() {
        return craftGroupName;
    }

    public void setCraftGroupName(String craftGroupName) {
        this.craftGroupName = craftGroupName;
    }

    public String getDiskPer() {
        return diskPer;
    }

    public void setDiskPer(String diskPer) {
        this.diskPer = diskPer;
    }
    public String getpaymentType()
    {
        return paymentType;
    }
    
    public void setpaymentType(String paymentType)
    {
        this.paymentType=paymentType;
    }

    public String getMaterialId() {
        return materialId;
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

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getCraftGroup() {
        return craftGroup;
    }

    public void setCraftGroup(String craftGroup) {
        this.craftGroup = craftGroup;
    }

    public float getPackAmt() {
        return packAmt;
    }

    public void setPackAmt(float packAmt) {
        this.packAmt = packAmt;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getQty() {
        return qty;
    }

    public void setQty(float qty) {
        this.qty = qty;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public float getGrossAmt() {
        return grossAmt;
    }

    public void setGrossAmt(float grossAmt) {
        this.grossAmt = grossAmt;
    }

    public float getDiscAmt() {
        return discAmt;
    }

    public void setDiscAmt(float discAmt) {
        this.discAmt = discAmt;
    }

    public float getNetAmt() {
        return netAmt;
    }

    public void setNetAmt(float netAmt) {
        this.netAmt = netAmt;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getVendorNumber() {
        return vendorNumber;
    }

    public void setVendorNumber(String vendorNumber) {
        this.vendorNumber = vendorNumber;
    }

    public String getManualBilNo() {
        return manualBilNo;
    }

    public void setManualBilNo(String manualBilNo) {
        this.manualBilNo = manualBilNo;
    }

    public SalesIntimationList() {

    }

    public SalesIntimationList(float qty, float rate, float grossAmt, float discAmt, float netAmt, String dateTime, String billNo, String manualBilNo, String vendorNumber, String vendorName, String description, String diskPer, String craftGroupName) {
        this.vendorNumber = vendorNumber;
        this.vendorName = vendorName;
        this.description = description;
        this.qty = qty;
        this.rate = rate;
        this.grossAmt = grossAmt;
        this.discAmt = discAmt;
        this.netAmt = netAmt;
        this.dateTime = dateTime;
        this.billNo = billNo;
        this.manualBilNo = manualBilNo;
        this.diskPer = diskPer;
        this.craftGroupName = craftGroupName;
    }

    public SalesIntimationList(String vendorNumber, String vendorName, String description, float packAmt, float qty, float rate, float grossAmt, float discAmt, float netAmt, String dateTime, String billNo) {
        this.vendorNumber = vendorNumber;
        this.vendorName = vendorName;
        this.description = description;
        this.packAmt = packAmt;
        this.qty = qty;
        this.rate = rate;
        this.grossAmt = grossAmt;
        this.discAmt = discAmt;
        this.netAmt = netAmt;
        this.dateTime = dateTime;
        this.billNo = billNo;
    }

    public SalesIntimationList(String vendorNumber, String vendorName, float grossAmt, float discAmt, float netAmt, String craftGroup) {
        this.vendorNumber = vendorNumber;
        this.vendorName = vendorName;
        this.grossAmt = grossAmt;
        this.discAmt = discAmt;
        this.netAmt = netAmt;
        this.craftGroup = craftGroup;
    }

    public SalesIntimationList(String vendorNumber, String vendorName, String description, float qty, float rate, float grossAmt, float discAmt, float netAmt, String manualBilNo, String craftGroup, String materialId) {
        this.vendorNumber = vendorNumber;
        this.vendorName = vendorName;
        this.description = description;
        this.qty = qty;
        this.rate = rate;
        this.grossAmt = grossAmt;
        this.discAmt = discAmt;
        this.netAmt = netAmt;
        this.manualBilNo = manualBilNo;
        this.craftGroup = craftGroup;
        this.materialId = materialId;
    }

    public SalesIntimationList(String craftGroupName, String vendorNumber, String dateTime, String paymentType, String manualBilNo, String materialId, String description, float qty, float rate ) {
        this.vendorNumber = vendorNumber;
        this.description = description;
        this.qty = qty;
        this.rate = rate;
        this.dateTime = dateTime;
        this.manualBilNo = manualBilNo;
      //  this.craftGroup = craftGroup;
        this.craftGroupName = craftGroupName;
        this.materialId = materialId;
        this.paymentType=paymentType;
    }
    
  public static Comparator<SalesIntimationList> NameComparator = new Comparator<SalesIntimationList>() {

        public int compare(SalesIntimationList s1, SalesIntimationList s2) {
           
             String StudentName3=s1.getMaterialId();
               String StudentName4=s2.getMaterialId();
           
            //ascending order
            int i= StudentName3.compareTo(StudentName4);
            if(i!=0)
            {
                return i;
            }
            else
            {
               String StudentName1 = s1.getVendorNumber();
            String StudentName2 = s2.getVendorNumber();
               return StudentName1.compareTo(StudentName2);
            }
            //descending order
            //return StudentName2.compareTo(StudentName1);
          
        }
    };   
  
  public static Comparator<SalesIntimationList> NameComparatorVen = new Comparator<SalesIntimationList>() {

        public int compare(SalesIntimationList s1, SalesIntimationList s2) {
           
             String StudentName3=s1.getCraftGroup();
               String StudentName4=s2.getCraftGroup();
           
            //ascending order
            int i= StudentName3.compareTo(StudentName4);
            if(i!=0)
            {
                return i;
            }
            else
            {
               String StudentName1 = s1.getVendorNumber();
            String StudentName2 = s2.getVendorNumber();
               return StudentName1.compareTo(StudentName2);
            }
            //descending order
            //return StudentName2.compareTo(StudentName1);
          
        }
    }; 
  
  public static Comparator<SalesIntimationList> NameComparator2 = new Comparator<SalesIntimationList>() {
      
      public int compare(SalesIntimationList s1, SalesIntimationList s2) {
          
          String StudentName1=s1.getVendorNumber();
               String StudentName2=s2.getVendorNumber();
         return StudentName1.compareTo(StudentName2);  
          
      }
      
      
      
      
      
      
  };
  
   public static Comparator<SalesIntimationList> NameComparator3 = new Comparator<SalesIntimationList>() {
      
      public int compare(SalesIntimationList s1, SalesIntimationList s2) {
          
          String StudentName1=s1.getMaterialId();
               String StudentName2=s2.getMaterialId();
         return StudentName1.compareTo(StudentName2);  
          
      }
      
      
      
      
      
      
  };
   public static Comparator<SalesIntimationList> NameComparatorMaterial = new Comparator<SalesIntimationList>() {
      
      public int compare(SalesIntimationList s1, SalesIntimationList s2) {
          
          String StudentName1=s1.getMaterialId();
               String StudentName2=s2.getMaterialId();
         return StudentName1.compareTo(StudentName2);  
          
      }
    
  };
  
    
  
  
  
  
    
   

}
