package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReportType extends ActionSupport {

    

    ResultSet reportTypeRs;
    private List reportTypeList = new ArrayList<>();
    DaoClass daoClass = new DaoClass();

   

    public String displayTypes() {
        try {
            //System.out.println("reports");
            String getreportTypeQry = "select rptPk,rptName,DATE_FORMAT(reportype_master.updatedDate,'%d-%m-%Y %h:%m:%s') as updatedDate from pos.reportype_master";
            reportTypeRs = daoClass.Fun_Resultset(getreportTypeQry);
            while (reportTypeRs.next()) {
                if (reportTypeRs.getInt("rptPk") != 1) {
                    ReportTypesList typesListObj = new ReportTypesList(reportTypeRs.getInt("rptPk"), reportTypeRs.getString("rptName"), reportTypeRs.getString("updatedDate"));
                    reportTypeList.add(typesListObj);
                    //System.out.println("List :" +reportTypeList);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ReportType.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
          daoClass.closeResultSet(reportTypeRs);
        }

        return SUCCESS;
    }

    public List getReportTypeList() {
        return reportTypeList;
    }

    public void setReportTypeList(List reportTypeList) {
        this.reportTypeList = reportTypeList;
    }
}
