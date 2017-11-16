package com.superadmin;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import java.sql.SQLException;

/*@author pranesh */
public class CreateNewCounterUser extends ActionSupport {

    private String empId;
    String emppk;
    String empname;
    String emprole;

    int isAccess = 0;
    private String employeeFname;
    private String employeeLname;
    private String userRole;
    private boolean isUserExits = false;
    private String employeePersonalNumber;
    private boolean employeeCreatedStatus = false;
    DaoClass daoClass = new DaoClass();

    public CreateNewCounterUser() {
    }

    public String createCounterUser() {
        System.out.println("0000");
        try {

            String empCountQry = "select count(emp_id) from emp_master where emp_id='" + employeePersonalNumber.trim() + "'";
            //System.out.println("" + empCountQry);
            if (!(daoClass.Fun_Int(empCountQry) > 0)) {
                isUserExits = false;
                String empName = null;
                if (!(employeeLname.equals(""))) {
                    empName = employeeFname.trim() + "." + employeeLname.trim();
                } else {
                    empName = employeeFname.trim();
                }
                String createUser_Query = "insert into emp_master(emp_id,emp_name,emp_type,emp_createdDate,emp_updatedDate,emp_status,cancelling_flag)values('" + employeePersonalNumber.trim() + "','" + empName.trim() + "','" + userRole.trim() + "',now(),now(),'1','N')";
             //   System.out.println(createUser_Query);
                int createUser_Query_status = daoClass.Fun_Updat(createUser_Query);
                String createUserCredinals = "insert into user_master(plant,username,pwd,emp_fk,securityQuestion_fk,security_answer,lastUpdated)values('" + daoClass.getPlantId().trim() + "','" + employeePersonalNumber.trim() + "','123','" + daoClass.Fun_Int("select emp_pk from emp_master where emp_id='" + employeePersonalNumber.trim() + "'") + "','1','Nill','0000-01-01 00:00:00')";
                int createUserCredinals_status = daoClass.Fun_Updat(createUserCredinals);
                employeeCreatedStatus = (createUser_Query_status > 0) && (createUserCredinals_status > 0);
            } else {
                isUserExits = true;
            }
        } catch (Exception ex) {
            System.out.println("Exception in creating new user : " + ex);
        }
        return SUCCESS;
    }

    public String getUserInfo() throws SQLException {
        String qry = "select emp_pk,emp_name,emp_type from emp_master where emp_id='" + empId + "'";
        ResultSet rs = daoClass.Fun_Resultset(qry);
        if (rs.next()) {
            emppk = rs.getString("emp_pk");
            if ((emppk.equals("7") || emppk.equals("19"))) {
                isAccess = -1;
            } else {
                isAccess = 1;
                empname = rs.getString("emp_name");
                emprole = rs.getString("emp_type");
            }
        } else {
            System.out.println("Employer Id  is Not there");
        }
        return SUCCESS;
    }

    public boolean isEmployeeCreatedStatus() {
        return employeeCreatedStatus;
    }

    public void setEmployeeCreatedStatus(boolean employeeCreatedStatus) {
        this.employeeCreatedStatus = employeeCreatedStatus;
    }

    public boolean isIsUserExits() {
        return isUserExits;
    }

    public void setIsUserExits(boolean isUserExits) {
        this.isUserExits = isUserExits;
    }

    public String getEmployeeFname() {
        return employeeFname;
    }

    public void setEmployeeFname(String employeeFname) {
        this.employeeFname = employeeFname;
    }

    public String getEmployeeLname() {
        return employeeLname;
    }

    public void setEmployeeLname(String employeeLname) {
        this.employeeLname = employeeLname;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getEmployeePersonalNumber() {
        return employeePersonalNumber;
    }

    public void setEmployeePersonalNumber(String employeePersonalNumber) {
        this.employeePersonalNumber = employeePersonalNumber;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public int getIsAccess() {
        return isAccess;
    }

    public void setIsAccess(int isAccess) {
        this.isAccess = isAccess;
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
