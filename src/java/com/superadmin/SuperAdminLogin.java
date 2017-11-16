package com.superadmin;

import com.DAO.*;
import com.opensymphony.xwork2.ActionSupport;
import java.util.Map;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;

/*@author Administrator*/
public class SuperAdminLogin extends ActionSupport implements SessionAware {

    String superUname;
    String superPassword;
    String displayLoginName;
    String superAdminPlantId;
    String isSuperValid = "invalid";
    DaoClass daoClass = new DaoClass();
    private String redirectAdminPage = "";
    private SessionMap<String, Object> session;

    @Override
    public String toString() {
        return "SuperAdminLogin{" + "superUname=" + superUname + ", superPassword=" + superPassword + ", superAdminPlantId=" + superAdminPlantId + ", isSuperValid=" + isSuperValid + ", daoClass=" + daoClass + '}';
    }

    public String superLoginCheck() {
        try {
            String superCountQuerry = "select count(*)from user_master where username='" + superUname.trim() + "'and pwd='" + superPassword.trim() + "'";
            if (daoClass.Fun_Int(superCountQuerry) > 0) {
                //String superCheckQuery = "SELECT empmaster.emp_type FROM pos.user_master usermaster INNER JOIN pos.emp_master empmaster ON (usermaster.emp_fk = empmaster.emp_pk)WHERE(usermaster.username = '" + superUname.trim() + "')AND(usermaster.pwd = '" + superPassword.trim() + "')AND(usermaster.plant = '" + superAdminPlantId.trim() + "')";
                String superCheckQuery = "SELECT empmaster.emp_type FROM pos.user_master usermaster INNER JOIN pos.emp_master empmaster ON (usermaster.emp_fk = empmaster.emp_pk)WHERE(usermaster.username = '" + superUname.trim() + "')AND(usermaster.pwd = '" + superPassword.trim() + "')";
                if (daoClass.Fun_Str(superCheckQuery).equalsIgnoreCase("super")) {
                    isSuperValid = "valid";
                } else {
                    isSuperValid = "invalid";
                }
            } else {
                isSuperValid = "invalid";
            }
        } catch (Exception ex) {
            System.out.println("Exception In Super Login : " + ex);
        }
        return isSuperValid;
    }

    public String adminLogout() {
        try {
            if (session != null) {
                session.invalidate();
                redirectAdminPage = "superAdminHome";
            }
        } catch (Exception ex) {
            System.out.println("Exception in  SuperAdminLogout : " + ex);
        }
        return SUCCESS;
    }

    public String getRedirectAdminPage() {
        return redirectAdminPage;
    }

    public void setRedirectAdminPage(String redirectAdminPage) {
        this.redirectAdminPage = redirectAdminPage;
    }

    public String getDisplayLoginName() {
        return displayLoginName;
    }

    public void setDisplayLoginName(String displayLoginName) {
        this.displayLoginName = displayLoginName;
    }

    public String getSuperUname() {
        return superUname;
    }

    public void setSuperUname(String superUname) {
        this.superUname = superUname;
    }

    public String getSuperPassword() {
        return superPassword;
    }

    public void setSuperPassword(String superPassword) {
        this.superPassword = superPassword;
    }

    public String getSuperAdminPlantId() {
        return superAdminPlantId;
    }

    public void setSuperAdminPlantId(String superAdminPlantId) {
        this.superAdminPlantId = superAdminPlantId;
    }

    public String getIsSuperValid() {
        return isSuperValid;
    }

    public void setIsSuperValid(String isSuperValid) {
        this.isSuperValid = isSuperValid;
    }

    public SessionMap<String, Object> getSession() {
        return session;
    }

    public void setSession(SessionMap<String, Object> session) {
        this.session = session;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        session = (SessionMap) map;
    }

}
