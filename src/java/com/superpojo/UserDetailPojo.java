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
public class UserDetailPojo {

    int employeePk;
    String employeeId;
    String plantId;
    String employeeName;
    String employeeType;
    String employeeCreatedDate;
    String updatedDate;
    int employeeStatus;
    int securityQuestion_fk;

    public UserDetailPojo() {
    }

    @Override
    public String toString() {
        return "UserDetailPojo{" + "employeePk=" + employeePk + ", employeeId=" + employeeId + ", plantId=" + plantId + ", employeeName=" + employeeName + ", employeeType=" + employeeType + ", employeeCreatedDate=" + employeeCreatedDate + ", updatedDate=" + updatedDate + ", employeeStatus=" + employeeStatus + ", securityQuestion_fk=" + securityQuestion_fk + '}';
    }



    public UserDetailPojo(int employeePk, String employeeId, String plantId, String employeeName, String employeeType, String employeeCreatedDate, String updatedDate, int employeeStatus, int securityQuestion_fk) {
        this.employeePk = employeePk;
        this.employeeId = employeeId;
        this.plantId = plantId;
        this.employeeName = employeeName;
        this.employeeType = employeeType;
        this.employeeCreatedDate = employeeCreatedDate;
        this.updatedDate = updatedDate;
        this.employeeStatus = employeeStatus;
        this.securityQuestion_fk = securityQuestion_fk;
    }

    public int getEmployeePk() {
        return employeePk;
    }

    public void setEmployeePk(int employeePk) {
        this.employeePk = employeePk;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getEmployeeCreatedDate() {
        return employeeCreatedDate;
    }

    public void setEmployeeCreatedDate(String employeeCreatedDate) {
        this.employeeCreatedDate = employeeCreatedDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(int employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    public int getSecurityQuestion_fk() {
        return securityQuestion_fk;
    }

    public void setSecurityQuestion_fk(int securityQuestion_fk) {
        this.securityQuestion_fk = securityQuestion_fk;
    }

}
