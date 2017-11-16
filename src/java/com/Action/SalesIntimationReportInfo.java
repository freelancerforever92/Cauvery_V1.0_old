package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalesIntimationReportInfo extends ActionSupport {

    private int siReportrNmePk;
    private String siReportNme;
    private String siReportAliasNme;
    ResultSet getSIRTInfoRs;
    ResultSet getSIRAliasCountRs;
    private int updateSIRAliasVal;
    private int checkAliasNameVal = 0;

    DaoClass daoClass = new DaoClass();

    public String getSIRTInfo() {
        try {
            String getSIRTInfoQry = "select reportName,reportAliasName from pos.salesintimation_reportname_master where reportPk='" + siReportrNmePk + "'";
            getSIRTInfoRs = daoClass.Fun_Resultset(getSIRTInfoQry);
            while (getSIRTInfoRs.next()) {
                siReportNme = getSIRTInfoRs.getString("reportName");
                siReportAliasNme = getSIRTInfoRs.getString("reportAliasName");
            }

        } catch (SQLException ex) {
            Logger.getLogger(SalesIntimationReport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoClass.closeResultSet(getSIRTInfoRs);
        }
        return SUCCESS;
    }

    public String updateSIRTInfo() {
        String array[] = siReportNme.split("-");
        String newSiReportNme = array[0] + "-" + siReportAliasNme.trim();
        try {
            String getAliasCountQuery = "SELECT count(*) as aliasCount FROM pos.salesintimation_reportname_master where reportAliasName='" + siReportAliasNme.trim() + "'";
            getSIRAliasCountRs = daoClass.Fun_Resultset(getAliasCountQuery);
            while (getSIRAliasCountRs.next()) {
                if (getSIRAliasCountRs.getInt("aliasCount") <= 0) {
                    checkAliasNameVal = 1;
                    String updateSIRAliasQry = "UPDATE `pos`.`salesintimation_reportname_master` SET `reportName`='" + newSiReportNme.trim() + "',`reportAliasName`='" + siReportAliasNme.trim() + "',`updatedDate`=now() WHERE `reportPk`='" + siReportrNmePk + "'";
                    updateSIRAliasVal = daoClass.Fun_Updat(updateSIRAliasQry);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(SalesIntimationReportInfo.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoClass.closeResultSet(getSIRAliasCountRs);
        }
        return SUCCESS;
    }

    public int getSiReportrNmePk() {
        return siReportrNmePk;
    }

    public void setSiReportrNmePk(int siReportrNmePk) {
        this.siReportrNmePk = siReportrNmePk;
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

    public int getUpdateSIRAliasVal() {
        return updateSIRAliasVal;
    }

    public void setUpdateSIRAliasVal(int updateSIRAliasVal) {
        this.updateSIRAliasVal = updateSIRAliasVal;
    }

    public int getCheckAliasNameVal() {
        return checkAliasNameVal;
    }

    public void setCheckAliasNameVal(int checkAliasNameVal) {
        this.checkAliasNameVal = checkAliasNameVal;
    }

}
