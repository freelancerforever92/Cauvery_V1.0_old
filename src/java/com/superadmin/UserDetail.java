package com.superadmin;

import com.DAO.*;
import java.sql.*;
import java.util.*;
import com.superpojo.*;
import com.opensymphony.xwork2.ActionSupport;

/* @author Administrator */
public class UserDetail extends ActionSupport {

    ResultSet rs = null;
    private String stus;
    private String actEmpid;
    List usersList = new ArrayList();
    private boolean empStatusUpdate = false;
    DaoClass daoClass = new DaoClass();

    public UserDetail() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String genUserDetails() {
        try {
            if (stus != null && actEmpid != null) {
                empStatusUpdating(stus, actEmpid);
            }
           // String empDetailsQuery = "SELECT empMaster.emp_pk,empMaster.emp_id,userMaster.plant,empMaster.emp_name,empMaster.emp_type,DATE_FORMAT(empMaster.emp_createdDate,'%d-%m-%Y %h:%m:%s')as createdDateTime,DATE_FORMAT(empMaster.emp_updatedDate,'%d-%m-%Y %h:%m:%s')as updatedDateTime,empMaster.emp_status FROM pos.emp_master empMaster INNER JOIN pos.user_master userMaster ON(empMaster.emp_pk = userMaster.emp_fk)";
           
            String empDetailsQuery="SELECT empMaster.emp_pk,empMaster.emp_id,userMaster.plant,empMaster.emp_name,empMaster.emp_type,DATE_FORMAT(empMaster.emp_createdDate,'%d-%m-%Y %h:%m:%s')as createdDateTime,DATE_FORMAT(empMaster.emp_updatedDate,'%d-%m-%Y %h:%m:%s')as updatedDateTime,empMaster.emp_status,userMaster.securityQuestion_fk FROM pos.emp_master empMaster INNER JOIN pos.user_master userMaster ON(empMaster.emp_pk = userMaster.emp_fk)";
            rs = daoClass.Fun_Resultset(empDetailsQuery);
            while (rs.next()) {
                if (rs.getInt("emp_pk") != Integer.parseInt(daoClass.getSuperAdminHideId().trim())) {
                    UserDetailPojo detailPojo = new UserDetailPojo(rs.getInt("emp_pk"), rs.getString("emp_id"), rs.getString("plant"), rs.getString("emp_name"), rs.getString("emp_type"), rs.getString("createdDateTime"), rs.getString("updatedDateTime"), rs.getInt("emp_status"),rs.getInt("securityQuestion_fk"));
                    usersList.add(detailPojo);
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception  : " + ex);
        } finally {
            daoClass.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public void empStatusUpdating(String statusValue, String empId) {
        empStatusUpdate = true;
        String empStatusUpdateQuery = "update emp_master set emp_status='" + statusValue + "',emp_updatedDate=now() where emp_pk='" + empId + "'";
        if (daoClass.Fun_Updat(empStatusUpdateQuery) > 0) {
            empStatusUpdate = true;
        } else {
            empStatusUpdate = false;
        }
    }

    public String getStus() {
        return stus;
    }

    public void setStus(String stus) {
        this.stus = stus;
    }

    public String getActEmpid() {
        return actEmpid;
    }

    public void setActEmpid(String actEmpid) {
        this.actEmpid = actEmpid;
    }

    public List getUsersList() {
        return usersList;
    }

    public void setUsersList(List usersList) {
        this.usersList = usersList;
    }
}
