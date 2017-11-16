package com.Action;

import com.opensymphony.xwork2.ActionSupport;
import com.DAO.DaoClass;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Vendors extends ActionSupport {

    private String vendorId;  // vendor_id
    private int vendorAccountGroup;  // account_group
    private String vendorTitle;  // title
    private String vendorName;   // vendor_name
    private String vendorName2;  // vendor_name2
    private String vendorName3;  // vendor_name3
    private String vendorName4;  // vendor_name4
    private String vendorSearchTerm;    // search_term
    private String vendorAddress1;  // address1
    private String vendorAddress2;  // address2
    private String vendorAddress3;  // address3
    private String vendorAddress4;  // address4
    private String vendorCity;   // city
    private String vendorPinCode;  // pincode
    private String vendorDistrict;    // district
    private String vendorState;     // state
    private String vendorTelNo;  // tel_no
    private String vendorMobileNo;  // mobile_no
    private String vendorFaxNo;     // fax_no
    private String vendorEmailId;    // email_id created_date_time  , updated_date_time
    private String vendorCreatedDate;
    private String vendorUpdatedDate;
    
    int valAcess;
    public String returnString;
    List vendorList = new ArrayList();
    DaoClass daoClass = new DaoClass();
    ResultSet rs = null;

    public String vendorList() {
        try {
            String vendorListQuery = "SELECT vendor_id,account_group,title,vendor_name,vendor_name2,vendor_name3,vendor_name4,search_term,address1,address2,address3,address4,city,pincode,district,state,tel_no,mobile_no,fax_no,email_id,DATE_FORMAT(vendor_master.created_date_time,'%d-%m-%Y %h:%m:%s')as created_date_time,DATE_FORMAT(vendor_master.updated_date_time,'%d-%m-%Y %h:%m:%s')as updated_date_time FROM pos.vendor_master";
            rs = daoClass.Fun_Resultset(vendorListQuery);
            while (rs.next()) {
                Vendors vendorobj = new Vendors(rs.getString("vendor_id"), rs.getInt("account_group"), rs.getString("title"), rs.getString("vendor_name"), rs.getString("vendor_name2"), rs.getString("vendor_name3"), rs.getString("vendor_name4"), rs.getString("search_term"), rs.getString("address1"), rs.getString("address2"), rs.getString("address3"), rs.getString("address4"), rs.getString("city"), rs.getString("pincode"), rs.getString("district"), rs.getString("state"), rs.getString("tel_no"), rs.getString("mobile_no"), rs.getString("fax_no"), rs.getString("email_id"), rs.getString("created_date_time"), rs.getString("updated_date_time"));
                vendorList.add(vendorobj);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
        }

        return SUCCESS;
    }

    public String vendorInfo() {        
        try {            
            String qry = "SELECT * FROM pos.vendor_master where vendor_id='"+vendorId+"'";
            rs = daoClass.Fun_Resultset(qry);           
            if(rs.next()) {              
                valAcess=1;
                vendorAccountGroup=rs.getInt("account_group");
                vendorTitle=rs.getString("title");
                vendorName=rs.getString("vendor_name");
                vendorName2=rs.getString("vendor_name2");
                vendorName3=rs.getString("vendor_name3");
                vendorName4=rs.getString("vendor_name4");
                vendorSearchTerm=rs.getString("search_term");
                vendorAddress1=rs.getString("address1");
                vendorAddress2=rs.getString("address2");
                vendorAddress3=rs.getString("address3");
                vendorAddress4=rs.getString("address4");
                vendorCity=rs.getString("city");
                vendorPinCode=rs.getString("pincode");
                vendorDistrict=rs.getString("district");
                vendorState=rs.getString("state");
                vendorTelNo=rs.getString("tel_no");
                vendorMobileNo=rs.getString("mobile_no");
                vendorFaxNo=rs.getString("fax_no");
                vendorEmailId=rs.getString("email_id");
            } 
            else {
                valAcess=0;
//                System.out.println("Vendor Id  is Not there");

            }
        } catch (SQLException ex) {
             System.out.println("Exception");
            Logger.getLogger(Vendors.class.getName()).log(Level.SEVERE, null, ex);
        }

        return SUCCESS;
    }
    
    public String updateVendor(){        
        String qry="update pos.vendor_master set account_group='"
                + vendorAccountGroup + "', title='" + vendorTitle + "', vendor_name='" 
                + vendorName + "', vendor_name2='" + vendorName2 + "', vendor_name3='"
                + vendorName3 + "', vendor_name4='" + vendorName4 + "', search_term='" 
                + vendorSearchTerm + "', address1='" + vendorAddress1 + "', address2='"                
                + vendorAddress2 + "', address3='" + vendorAddress3 + "', address4='"
                + vendorAddress4 + "', city='" + vendorCity + "', pincode='"
                + vendorPinCode + "', district='" + vendorDistrict + "', state='"
                + vendorState + "', tel_no='" + vendorTelNo + "', mobile_no='"
                + vendorMobileNo + "', fax_no='" + vendorFaxNo + "', email_id='"
                + vendorEmailId + "', updated_date_time=now() "
                + "where vendor_id='" + vendorId.trim() + "'";
//        System.out.println("Query: "+qry);
      daoClass.updatingRecord(qry);
//      System.out.println("Update");
        return SUCCESS;
        
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public int getVendorAccountGroup() {
        return vendorAccountGroup;
    }

    public void setVendorAccountGroup(int vendorAccountGroup) {
        this.vendorAccountGroup = vendorAccountGroup;
    }

    public String getVendorTitle() {
        return vendorTitle;
    }

    public void setVendorTitle(String vendorTitle) {
        this.vendorTitle = vendorTitle;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorName2() {
        return vendorName2;
    }

    public void setVendorName2(String vendorName2) {
        this.vendorName2 = vendorName2;
    }

    public String getVendorName3() {
        return vendorName3;
    }

    public void setVendorName3(String vendorName3) {
        this.vendorName3 = vendorName3;
    }

    public String getVendorName4() {
        return vendorName4;
    }

    public void setVendorName4(String vendorName4) {
        this.vendorName4 = vendorName4;
    }

    public String getVendorSearchTerm() {
        return vendorSearchTerm;
    }

    public void setVendorSearchTerm(String vendorSearchTerm) {
        this.vendorSearchTerm = vendorSearchTerm;
    }

    public String getVendorAddress1() {
        return vendorAddress1;
    }

    public void setVendorAddress1(String vendorAddress1) {
        this.vendorAddress1 = vendorAddress1;
    }

    public String getVendorAddress2() {
        return vendorAddress2;
    }

    public void setVendorAddress2(String vendorAddress2) {
        this.vendorAddress2 = vendorAddress2;
    }

    public String getVendorAddress3() {
        return vendorAddress3;
    }

    public void setVendorAddress3(String vendorAddress3) {
        this.vendorAddress3 = vendorAddress3;
    }

    public String getVendorAddress4() {
        return vendorAddress4;
    }

    public void setVendorAddress4(String vendorAddress4) {
        this.vendorAddress4 = vendorAddress4;
    }

    public String getVendorCity() {
        return vendorCity;
    }

    public void setVendorCity(String vendorCity) {
        this.vendorCity = vendorCity;
    }

    public String getVendorPinCode() {
        return vendorPinCode;
    }

    public void setVendorPinCode(String vendorPinCode) {
        this.vendorPinCode = vendorPinCode;
    }

    public String getVendorDistrict() {
        return vendorDistrict;
    }

    public void setVendorDistrict(String vendorDistrict) {
        this.vendorDistrict = vendorDistrict;
    }

    public String getVendorState() {
        return vendorState;
    }

    public void setVendorState(String vendorState) {
        this.vendorState = vendorState;
    }

    public String getVendorTelNo() {
        return vendorTelNo;
    }

    public void setVendorTelNo(String vendorTelNo) {
        this.vendorTelNo = vendorTelNo;
    }

    public String getVendorMobileNo() {
        return vendorMobileNo;
    }

    public void setVendorMobileNo(String vendorMobileNo) {
        this.vendorMobileNo = vendorMobileNo;
    }

    public String getVendorFaxNo() {
        return vendorFaxNo;
    }

    public void setVendorFaxNo(String vendorFaxNo) {
        this.vendorFaxNo = vendorFaxNo;
    }

    public String getVendorEmailId() {
        return vendorEmailId;
    }

    public void setVendorEmailId(String vendorEmailId) {
        this.vendorEmailId = vendorEmailId;
    }

    public List getVendorList() {
        return vendorList;
    }

    public void setVendorList(List vendorList) {
        this.vendorList = vendorList;
    }

    public String getVendorCreatedDate() {
        return vendorCreatedDate;
    }

    public void setVendorCreatedDate(String vendorCreatedDate) {
        this.vendorCreatedDate = vendorCreatedDate;
    }

    public String getVendorUpdatedDate() {
        return vendorUpdatedDate;
    }

    public void setVendorUpdatedDate(String vendorUpdatedDate) {
        this.vendorUpdatedDate = vendorUpdatedDate;
    }

    public int getValAcess() {
        return valAcess;
    }

    public void setValAcess(int valAcess) {
        this.valAcess = valAcess;
    }
    
    

    @Override
    public String toString() {
        return "Vendors{" + "vendorId=" + vendorId + ", vendorAccountGroup=" + vendorAccountGroup + ", vendorTitle=" + vendorTitle + ", vendorName=" + vendorName + ", vendorName2=" + vendorName2 + ", vendorName3=" + vendorName3 + ", vendorName4=" + vendorName4 + ", vendorSearchTerm=" + vendorSearchTerm + ", vendorAddress1=" + vendorAddress1 + ", vendorAddress2=" + vendorAddress2 + ", vendorAddress3=" + vendorAddress3 + ", vendorAddress4=" + vendorAddress4 + ", vendorCity=" + vendorCity + ", vendorPinCode=" + vendorPinCode + ", vendorDistrict=" + vendorDistrict + ", vendorState=" + vendorState + ", vendorTelNo=" + vendorTelNo + ", vendorMobileNo=" + vendorMobileNo + ", vendorFaxNo=" + vendorFaxNo + ", vendorEmailId=" + vendorEmailId + ", vendorCreatedDate=" + vendorCreatedDate + ", vendorUpdatedDate=" + vendorUpdatedDate + '}';
    }

    public Vendors() {
    }

    public Vendors(String vendorId, int vendorAccountGroup, String vendorTitle, String vendorName, String vendorName2, String vendorName3, String vendorName4, String vendorSearchTerm, String vendorAddress1, String vendorAddress2, String vendorAddress3, String vendorAddress4, String vendorCity, String vendorPinCode, String vendorDistrict, String vendorState, String vendorTelNo, String vendorMobileNo, String vendorFaxNo, String vendorEmailId, String vendorCreatedDate, String vendorUpdatedDate) {
        this.vendorId = vendorId;
        this.vendorAccountGroup = vendorAccountGroup;
        this.vendorTitle = vendorTitle;
        this.vendorName = vendorName;
        this.vendorName2 = vendorName2;
        this.vendorName3 = vendorName3;
        this.vendorName4 = vendorName4;
        this.vendorSearchTerm = vendorSearchTerm;
        this.vendorAddress1 = vendorAddress1;
        this.vendorAddress2 = vendorAddress2;
        this.vendorAddress3 = vendorAddress3;
        this.vendorAddress4 = vendorAddress4;
        this.vendorCity = vendorCity;
        this.vendorPinCode = vendorPinCode;
        this.vendorDistrict = vendorDistrict;
        this.vendorState = vendorState;
        this.vendorTelNo = vendorTelNo;
        this.vendorMobileNo = vendorMobileNo;
        this.vendorFaxNo = vendorFaxNo;
        this.vendorEmailId = vendorEmailId;
        this.vendorCreatedDate = vendorCreatedDate;
        this.vendorUpdatedDate = vendorUpdatedDate;
    }

}
