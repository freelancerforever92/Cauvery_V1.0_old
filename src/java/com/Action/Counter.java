package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Counter extends ActionSupport {

    private String counterName;
    private String counterId;
    private String counterLegacyNo;
    private String branchCode;

    DaoClass daoClass = new DaoClass();
    ResultSet rs = null;
    int valAcess = 0;
    int count = 0;
    int legacyCount = 0;
    int checkCountId = 0;
    int isCounterCreated = 0;

    public String createCounter() {
        String nameQry = "select count(*) from pos.branch_counter where counter='" + counterName.trim() + "'";
        count = daoClass.Fun_Int(nameQry);
        String legacyQry = "select count(*) from pos.branch_counter where counter_no_legacy='" + counterLegacyNo.trim() + "'";
        legacyCount = daoClass.Fun_Int(legacyQry);
//        String countIdQry = "select count(*) from pos.branch_counter where counter_no='" + counterId.trim() + "'";
//        checkCountId = daoClass.Fun_Int(countIdQry);

        if (count <= 0) {
            if (legacyCount <= 0) {
//                if (checkCountId <= 0) {
                    String qry = "INSERT INTO `pos`.`branch_counter` (`counter`, `counter_no`, `counter_no_legacy`, `login_userid`, `branch_code`, `counter_status`,`createdDateTime`,`lastUpdatedDateTime`)  VALUES ('" + counterName + "', '" + counterId.trim() + "', '" + counterLegacyNo + "', '', '" + branchCode + "','0',now(),'0001-01-01 01:01:01')";
                    isCounterCreated = daoClass.Fun_Updat(qry);
//                }
            }
        }
        return SUCCESS;
    }

    public String getCounterInfo() {
        try {
            String qry = "SELECT * FROM pos.branch_counter where counter_no='" + counterId.trim() + "'";
            rs = daoClass.Fun_Resultset(qry);
            if (rs.next()) {
                valAcess = 2;
                counterName = rs.getString("counter");
                counterLegacyNo = rs.getString("counter_no_legacy");

            } else {
                valAcess = 1;
            }
        } catch (Exception ex) {
            System.out.println(" Exception is: " + ex.getMessage());
        } finally {
            daoClass.closeResultSet(rs);
            return SUCCESS;
        }

    }

    public Counter() {
    }

    public int getValAcess() {
        return valAcess;
    }

    public void setValAcess(int valAcess) {
        this.valAcess = valAcess;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getCounterId() {
        return counterId;
    }

    public void setCounterId(String counterId) {
        this.counterId = counterId;
    }

    public String getCounterLegacyNo() {
        return counterLegacyNo;
    }

    public void setCounterLegacyNo(String counterLegacyNo) {
        this.counterLegacyNo = counterLegacyNo;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
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

}
