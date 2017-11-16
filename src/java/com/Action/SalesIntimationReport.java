package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalesIntimationReport extends ActionSupport {

    private String siReportNme;
    private String siReportAliasNme;

    DaoClass daoClass = new DaoClass();
    public String getListQry;
    ResultSet getListRs;
    List getListArrayList = new ArrayList<>();

    ResultSet getNameCountRs;
    ResultSet getAliasCountRs;
    String newSIRTName;

    int validationSIRVal;
    int checkAliasNameVal;
    int createSIRTVal;

    public String getSIRTList() {
        try {
            getListQry = "SELECT reportPk,reportName,reportAliasName,DATE_FORMAT(salesintimation_reportname_master.createdDate,'%d-%m-%Y %r') as createdDate,DATE_FORMAT(salesintimation_reportname_master.updatedDate,'%d-%m-%Y %r') as updatedDate FROM pos.salesintimation_reportname_master";
            getListRs = daoClass.Fun_Resultset(getListQry);

            getListArrayList.clear();
            while (getListRs.next()) {
                if (getListRs.getInt("reportPk") != 1 && getListRs.getInt("reportPk") != 2) {
                    SalesIntimationReportPojo intimationReport = new SalesIntimationReportPojo(getListRs.getInt("reportPk"), getListRs.getString("reportName"), getListRs.getString("reportAliasName"), getListRs.getString("createdDate"), getListRs.getString("updatedDate"));
                    getListArrayList.add(intimationReport);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalesIntimationReport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoClass.closeResultSet(getListRs);
        }
        return SUCCESS;
    }

    public String createSIRT() {
        try {
            if (siReportNme.equalsIgnoreCase("all") || siReportNme.equalsIgnoreCase("select")) {
                validationSIRVal = 0;
            } else {
                validationSIRVal = 1;
                newSIRTName = siReportNme + "-" + siReportAliasNme;
                String getAliasCountQuery = "SELECT count(*) as aliasCount FROM pos.salesintimation_reportname_master where reportAliasName='" + siReportAliasNme.trim() + "'";
                getAliasCountRs = daoClass.Fun_Resultset(getAliasCountQuery);
                while (getAliasCountRs.next()) {
                    if (getAliasCountRs.getInt("aliasCount") <= 0) {
                        checkAliasNameVal = 1;
                        String createSIRTQry = "INSERT INTO `pos`.`salesintimation_reportname_master` (`reportName`, `reportAliasName`, `createdDate`, `updatedDate`) VALUES ('" + newSIRTName + "', '" + siReportAliasNme.trim() + "', now(), now())";
                        createSIRTVal = daoClass.Fun_Updat(createSIRTQry);
                    } else {
                        checkAliasNameVal = 0;
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(SalesIntimationReport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoClass.closeResultSet(getAliasCountRs);
            daoClass.closeResultSet(getNameCountRs);
        }
        return SUCCESS;

    }

    public List getGetListArrayList() {
        return getListArrayList;
    }

    public void setGetListArrayList(List getListArrayList) {
        this.getListArrayList = getListArrayList;
    }

    public String getSiReportNme() {
        return siReportNme;
    }

    public void setSiReportNme(String siReportNme) {
        this.siReportNme = siReportNme;
    }

    public String getSiReportAliasNme() {
        return siReportAliasNme;
    }

    public void setSiReportAliasNme(String siReportAliasNme) {
        this.siReportAliasNme = siReportAliasNme;
    }

    public int getValidationSIRVal() {
        return validationSIRVal;
    }

    public void setValidationSIRVal(int validationSIRVal) {
        this.validationSIRVal = validationSIRVal;
    }

    public int getCheckAliasNameVal() {
        return checkAliasNameVal;
    }

    public void setCheckAliasNameVal(int checkAliasNameVal) {
        this.checkAliasNameVal = checkAliasNameVal;
    }

    public int getCreateSIRTVal() {
        return createSIRTVal;
    }

    public void setCreateSIRTVal(int createSIRTVal) {
        this.createSIRTVal = createSIRTVal;
    }

}
