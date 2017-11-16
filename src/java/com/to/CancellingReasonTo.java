package com.to;

import java.util.Date;

public class CancellingReasonTo {

    private int reasonId;
    private String reasonDesc;
    private String reasonNo;
    private String createdDate;
    private String updatedDate;

    public CancellingReasonTo() {
    }

    public CancellingReasonTo(int reasonId, String reasonDesc, String reasonNo, String createdDate, String updatedDate) {
        this.reasonId = reasonId;
        this.reasonDesc = reasonDesc;
        this.reasonNo = reasonNo;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public int getReasonId() {
        return reasonId;
    }

    public void setReasonId(int reasonId) {
        this.reasonId = reasonId;
    }

    public String getReasonDesc() {
        return reasonDesc;
    }

    public void setReasonDesc(String reasonDesc) {
        this.reasonDesc = reasonDesc;
    }

    public String getReasonNo() {
        return reasonNo;
    }

    public void setReasonNo(String reasonNo) {
        this.reasonNo = reasonNo;
    }

}
