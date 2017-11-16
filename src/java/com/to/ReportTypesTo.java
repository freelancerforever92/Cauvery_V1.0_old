package com.to;

/*@author pranesh */
public class ReportTypesTo {

    private int reportId;
    private String reportName;
    private String aliasReportName;

    public ReportTypesTo() {
    }

    @Override
    public String toString() {
        return "ReportTypesTo{" + "reportId=" + reportId + ", reportName=" + reportName + ", aliasReportName=" + aliasReportName + '}';
    }

    public ReportTypesTo(int reportId, String reportName, String aliasReportName) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.aliasReportName = aliasReportName;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getAliasReportName() {
        return aliasReportName;
    }

    public void setAliasReportName(String aliasReportName) {
        this.aliasReportName = aliasReportName;
    }

}
