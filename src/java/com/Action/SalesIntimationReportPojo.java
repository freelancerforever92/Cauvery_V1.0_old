package com.Action;

import com.opensymphony.xwork2.ActionSupport;

public class SalesIntimationReportPojo  extends  ActionSupport{

    private int siReportrNmePk;
    private String siReportNme;
    private String siReportAliasNme;
    private String createDate;
    private String updatedDate;
    public SalesIntimationReportPojo(int siReportrNmePk, String siReportNme, String siReportAliasNme, String CreateDate, String UpdatedDate) {
        this.siReportrNmePk = siReportrNmePk;
        this.siReportNme = siReportNme;
        this.siReportAliasNme = siReportAliasNme;
        this.createDate = CreateDate;
        this.updatedDate = UpdatedDate;
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String CreateDate) {
        this.createDate = CreateDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String UpdatedDate) {
        this.updatedDate = UpdatedDate;
    }
    
    
    
    
    

}
