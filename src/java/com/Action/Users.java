/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import java.sql.Statement;



/**
 *
 * @author JAVA
 */
public class Users extends ActionSupport {
    private String empId;
    private String empname;
    private String emprole;
    DaoClass daoClass=new DaoClass();
    Statement sta=null;
    
    String qry;
    
    public String updateUserInfo() throws SQLException{
   qry="update pos.emp_master set emp_name='"+empname+"', emp_type='"+emprole+"', emp_updatedDate=now() where emp_id='"+empId+"'";
   daoClass.updatingRecord(qry);
    return SUCCESS;
}

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getEmprole() {
        return emprole;
    }

    public void setEmprole(String emprole) {
        this.emprole = emprole;
    }
    
    
    
}
