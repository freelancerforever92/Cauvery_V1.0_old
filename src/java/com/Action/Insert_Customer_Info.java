package com.Action;

import java.sql.*;
import com.DAO.*;
import java.util.*;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

/*@author PRANESH*/
public class Insert_Customer_Info extends ActionSupport implements SessionAware {

    Map session;
    ResultSet rs;
    Connection con;
    private String custCNo;
    private String custName;
    private String orderNumber;
    CallableStatement pstmt;
    DaoClass cado = new DaoClass();
    private int customerInfoInsStatus = 0;
    int spresult = 0, instCustInfo_Status = 0;

    String custFname, custLname, txtConNo, txtStr1, txtStr2, custState, custCity, txtZipcode;

    public Insert_Customer_Info() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String Fun_InsertCustomerInfo() {
        System.out.println("----------CAME---------------");
        String SpCustInfo = "";
        spresult = 0;
        try {
            String cust_name = custFname + "." + custLname;
            SpCustInfo = "{call sp_CustomerInfo(?,?,?,?,?,?,?,?,?,?)}";
            con = cado.Fun_DbCon();
            pstmt = con.prepareCall(SpCustInfo);
            pstmt.setString(1, cust_name.trim());
            pstmt.setString(2, txtConNo.trim());//
            pstmt.setString(3, txtStr1.trim());///
            pstmt.setString(4, txtStr2.trim());//
            pstmt.setString(5, "");//
            pstmt.setString(6, custState.trim());//
            pstmt.setString(7, custCity.trim());//
            pstmt.setString(8, txtZipcode.trim());//
            pstmt.setInt(9, 0);
            pstmt.setString(10, session.get("SalesorderNo").toString());
            spresult = pstmt.executeUpdate();
            System.err.println("Result Value  :  " + spresult);
            if (spresult == 1) {
                instCustInfo_Status = 1;
                System.err.println("instCustInfo_Status :  " + instCustInfo_Status);
                ////JOptionPane.showMessageDialog(this, "Information Save Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                //this.hide();
            } else {
                instCustInfo_Status = -1;
                System.err.println("instCustInfo_Status :  " + instCustInfo_Status);
                //JOptionPane.showMessageDialog(this, "Process Failed,Try again..", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            System.out.println("Exception In Inserring the Customer Info :  " + ex);
        }
        return SUCCESS;
    }

    public String storeCustomerInfo() {
        try {
            String insCustomerInfoQuery;
            String cust_name = custFname + "." + custLname;
            if (!((custCNo.equals("")) || (custCNo.equalsIgnoreCase(null)))) {
                insCustomerInfoQuery = "insert into coustomer_info(sales_ordno,custname,contact,info_status,datetime)values('" + orderNumber + "','" + cust_name + "','" + custCNo + "','0',now())";
            } else {
                custCNo = "";
                insCustomerInfoQuery = "insert into coustomer_info(sales_ordno,custname,contact,info_status,datetime)values('" + orderNumber + "','" + cust_name + "','" + custCNo + "','0',now())";
            }
            customerInfoInsStatus = cado.Fun_Updat(insCustomerInfoQuery);
        } catch (Exception ex) {
            System.out.println("Exception In Inserting Customer Data :  " + ex);
        }
        return SUCCESS;
    }

    public String getCustCNo() {
        return custCNo;
    }

    public void setCustCNo(String custCNo) {
        this.custCNo = custCNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getCustomerInfoInsStatus() {
        return customerInfoInsStatus;
    }

    public void setCustomerInfoInsStatus(int customerInfoInsStatus) {
        this.customerInfoInsStatus = customerInfoInsStatus;
    }

    public int getinstCustInfo_Status() {
        return instCustInfo_Status;
    }

    public void setinstCustInfo_Status(int instCustInfo_Status) {
        this.instCustInfo_Status = instCustInfo_Status;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String getCustFname() {
        return custFname;
    }

    public void setCustFname(String custFname) {
        this.custFname = custFname;
    }

    public String getCustLname() {
        return custLname;
    }

    public void setCustLname(String custLname) {
        this.custLname = custLname;
    }

    public String getTxtConNo() {
        return txtConNo;
    }

    public void setTxtConNo(String txtConNo) {
        this.txtConNo = txtConNo;
    }

    public String getTxtStr1() {
        return txtStr1;
    }

    public void setTxtStr1(String txtStr1) {
        this.txtStr1 = txtStr1;
    }

    public String getTxtStr2() {
        return txtStr2;
    }

    public void setTxtStr2(String txtStr2) {
        this.txtStr2 = txtStr2;
    }

    public String getCustState() {
        return custState;
    }

    public void setCustState(String custState) {
        this.custState = custState;
    }

    public String getCustCity() {
        return custCity;
    }

    public void setCustCity(String custCity) {
        this.custCity = custCity;
    }

    public String getTxtZipcode() {
        return txtZipcode;
    }

    public void setTxtZipcode(String txtZipcode) {
        this.txtZipcode = txtZipcode;
    }
}
