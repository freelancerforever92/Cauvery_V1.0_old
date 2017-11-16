package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Reports extends ActionSupport {

    private int reportTypePk;
    private String rptName;
    ResultSet getreportTypeRs = null;
    DaoClass daoClass = new DaoClass();
    private int updateRTypeVal;
    private int existTypeVal;

    public String showType() {
        try {
            String getTypeQry = "select rptName from pos.reportype_master where rptPk='" + reportTypePk + "'";
            getreportTypeRs = daoClass.Fun_Resultset(getTypeQry);
            while (getreportTypeRs.next()) {
                rptName = getreportTypeRs.getString("rptName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportType.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoClass.closeResultSet(getreportTypeRs);
        }
        return SUCCESS;
    }

    public String updateType() {
        try {
            String rTypeQry = "select count(*) from pos.reportype_master where rptName='" + rptName.trim() + "'";
            existTypeVal = daoClass.Fun_Int(rTypeQry);
            if (existTypeVal <= 0) {
                String updateRTypeQry = "update pos.reportype_master set rptName='" + rptName.trim() + "',updatedDate=now() where rptPk='" + reportTypePk + "'";
                updateRTypeVal = daoClass.Fun_Updat(updateRTypeQry);
            }
        } catch (Exception ex) {
            updateRTypeVal = 0;
            System.err.println("Error Message Is : " + ex.getMessage());
        }

        return SUCCESS;
    }

    public int getReportTypePk() {
        return reportTypePk;
    }

    public void setReportTypePk(int reportTypePk) {
        this.reportTypePk = reportTypePk;
    }

    public String getRptName() {
        return rptName;
    }

    public void setRptName(String rptName) {
        this.rptName = rptName;
    }

    public int getUpdateRTypeVal() {
        return updateRTypeVal;
    }

    public void setUpdateRTypeVal(int updateRTypeVal) {
        this.updateRTypeVal = updateRTypeVal;
    }

    public int getExistTypeVal() {
        return existTypeVal;
    }

    public void setExistTypeVal(int existTypeVal) {
        this.existTypeVal = existTypeVal;
    }

}
