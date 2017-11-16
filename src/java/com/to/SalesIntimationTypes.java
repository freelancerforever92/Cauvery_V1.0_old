package com.to;
/*@author pranesh */

public class SalesIntimationTypes {

    private int reportPk;
    private String reportName;
    private String reportAliasName;

    public SalesIntimationTypes() {
    }

    @Override
    public String toString() {
        return "SalesIntimationTypes{" + "reportPk=" + reportPk + ", reportName=" + reportName + ", reportAliasName=" + reportAliasName + '}';
    }

    public SalesIntimationTypes(int reportPk, String reportName, String reportAliasName) {
        this.reportPk = reportPk;
        this.reportName = reportName;
        this.reportAliasName = reportAliasName;
    }

    public int getReportPk() {
        return reportPk;
    }

    public void setReportPk(int reportPk) {
        this.reportPk = reportPk;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getReportAliasName() {
        return reportAliasName;
    }

    public void setReportAliasName(String reportAliasName) {
        this.reportAliasName = reportAliasName;
    }

}
