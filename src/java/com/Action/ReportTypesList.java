

package com.Action;

public class ReportTypesList{
    
    private int  reportTypePk;
    private String rptName;
    private String updatedDate;    
   
    @Override
    public String toString() {
        return "ReportTypesList{" + "reportTypePk=" + reportTypePk + ", rptName=" + rptName + ", updatedDate=" + updatedDate + '}';
    }
    
    public ReportTypesList(int reportTypePk, String rptName, String updatedDate) {
        this.reportTypePk = reportTypePk;
        this.rptName = rptName;
        this.updatedDate = updatedDate;
    }

    public ReportTypesList() {
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

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }
    
    
    
     
     
     
    
    
    
}
