package com.Action;

import com.DAO.*;
import java.util.*;
import java.sql.ResultSet;
import com.opensymphony.xwork2.ActionSupport;
import com.to.CancellingReasonTo;

public class Cancelling_Reason extends ActionSupport {

    private int reasonId;
    private String reasonDesc;
    private String reasonNo;
    private String createdDate;
    private String updatedDate;

    private int updateValueCancelResion = 0;
    int valAcess = 0;
    int countExixt = 0;
    int legacyCount = 0;
    int checkCountId = 0;
    int isCounterCreated = 0;
    int countConcat = 0;

    DaoClass cado = new DaoClass();
    ResultSet rs = null;
    private List<CancellingReasonTo> cancellingReasonTos;

    int reasonslimitValue;
    static ResourceBundle rb = ResourceBundle.getBundle("dbConnection");

    public Cancelling_Reason() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String fillCancelReasonTypes() {
        try {
            String fetchReasonTypes = "select reason_id,reason_desc,reasonNo,DATE_FORMAT(cancelling_reason_master.createdDate,'%d-%m-%Y %h:%m:%s') as createdDate,DATE_FORMAT(cancelling_reason_master.updatedDate,'%d-%m-%Y %h:%m:%s') as updatedDate from cancelling_reason_master";
            cancellingReasonTos = new ArrayList();
            rs = cado.Fun_Resultset(fetchReasonTypes);
            while (rs.next()) {
                if (rs.getInt("reason_id") != 0) {
                    cancellingReasonTos.add(new CancellingReasonTo(rs.getInt("reason_id"), rs.getString("reason_desc"), rs.getString("reasonNo"), rs.getString("createdDate"), rs.getString("updatedDate")));
                }
            }
        } catch (Exception ex) {
            System.out.println("Excetion In Filling Cancel Resaons :  " + ex);
        } finally {
            cado.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public String fillCancelReasonTypesIDBased() {

        try {
            String fetchReasonTypes = "select * from cancelling_reason_master where reason_id='" + reasonId + "'";
            rs = cado.Fun_Resultset(fetchReasonTypes);
            while (rs.next()) {
                reasonDesc = rs.getString("reason_desc");
            }
        } catch (Exception ex) {
            System.out.println("Excetion In Filling Cancel Resaons :  " + ex);
        } finally {
            cado.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public String CreateCancelReasonTypesIDBased() {
        try {
            reasonslimitValue = Integer.parseInt(rb.getString("cancelling.reasons.limit"));
            System.out.println("Limoity : " + reasonslimitValue);

            String nameQry = "select count(*) FROM pos.cancelling_reason_master where reason_desc='" + reasonDesc + "'";
            countExixt = cado.Fun_Int(nameQry);
            if (countExixt == 0) {
                String getPkId = "SELECT count(*) FROM pos.cancelling_reason_master";
                countConcat = cado.Fun_Int(getPkId);
                if (countConcat < reasonslimitValue) {
                    countConcat++;
                    String conCatNumber = "0" + countConcat;
                    String qry = "INSERT INTO `pos`.`cancelling_reason_master` (`reason_id`, `reason_desc`, `reasonNo`, `createdDate`, `updatedDate`) VALUES ('" + countConcat + "', '" + reasonDesc + "', '" + conCatNumber + "', now(), '0001-01-01 01:01:01');";

                    isCounterCreated = cado.Fun_Updat(qry);
                } else {
                    isCounterCreated = 0;
                }
            } else {
                System.out.println("Error..!");
            }
        } catch (Exception ex) {
            System.out.println("Excetion In Filling Cancel Resaons :  " + ex);
        } finally {
            cado.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public String UpdateCancelReasonTypesIDBased() {
        try {
            String nameQry = "select count(*) FROM pos.cancelling_reason_master where reason_desc='" + reasonDesc + "'";
            countExixt = cado.Fun_Int(nameQry);
            if (countExixt == 0) {
                String fetchReasonTypes = "UPDATE `pos`.`cancelling_reason_master` SET `reason_desc`='" + reasonDesc + "', `updatedDate`=now() WHERE `reason_id`='" + reasonId + "';";
                updateValueCancelResion = cado.Fun_Updat(fetchReasonTypes);
            } else {
                countExixt = 1;
            }

        } catch (Exception ex) {
            System.out.println("Excetion In Filling Cancel Resaons :  " + ex);
        } finally {
            cado.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public List<CancellingReasonTo> getCancellingReasonTos() {
        return cancellingReasonTos;
    }

    public void setCancellingReasonTos(List<CancellingReasonTo> cancellingReasonTos) {
        this.cancellingReasonTos = cancellingReasonTos;
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

    public int getUpdateValueCancelResion() {
        return updateValueCancelResion;
    }

    public void setUpdateValueCancelResion(int updateValueCancelResion) {
        this.updateValueCancelResion = updateValueCancelResion;
    }

    public int getValAcess() {
        return valAcess;
    }

    public void setValAcess(int valAcess) {
        this.valAcess = valAcess;
    }

    public int getCountExixt() {
        return countExixt;
    }

    public void setCountExixt(int countExixt) {
        this.countExixt = countExixt;
    }

    public int getLegacyCount() {
        return legacyCount;
    }

    public void setLegacyCount(int legacyCount) {
        this.legacyCount = legacyCount;
    }

    public int getCheckCountId() {
        return checkCountId;
    }

    public void setCheckCountId(int checkCountId) {
        this.checkCountId = checkCountId;
    }

    public int getIsCounterCreated() {
        return isCounterCreated;
    }

    public void setIsCounterCreated(int isCounterCreated) {
        this.isCounterCreated = isCounterCreated;
    }

    public int getCountConcat() {
        return countConcat;
    }

    public void setCountConcat(int countConcat) {
        this.countConcat = countConcat;
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

    public int getReasonslimitValue() {
        return reasonslimitValue;
    }

    public void setReasonslimitValue(int reasonslimitValue) {
        this.reasonslimitValue = reasonslimitValue;
    }

}
