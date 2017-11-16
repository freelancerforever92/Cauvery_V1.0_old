package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CounterUpdation extends ActionSupport {

    private String counterName;
    private String counterId;
    private String counterLegacyNo;
    private String counterCreatedDate;
    private String counterUpdatedDate;
    DaoClass daoClass = new DaoClass();
    ResultSet rst = null;
    int count = 0;
    int legacyCount = 0;
    String testId;
    ResultSet counteRs = null;
    List countdisplayList = new ArrayList();

    private String plantId;

    public String getbranchCode() {
        plantId = daoClass.getPlantId();
        return SUCCESS;
    }

    public String counterList() {
        try {
            String counterListQuery = "SELECT counter,counter_no,counter_no_legacy,DATE_FORMAT(branch_counter.createdDateTime,'%d-%m-%Y %h:%m:%s')as createdDateTime,DATE_FORMAT(branch_counter.lastUpdatedDateTime,'%d-%m-%Y %h:%m:%s')as lastUpdatedDateTime FROM pos.branch_counter";
            counteRs = daoClass.Fun_Resultset(counterListQuery);
            while (counteRs.next()) {
                CounterUpdation counterObj = new CounterUpdation(counteRs.getString("counter"), counteRs.getString("counter_no"), counteRs.getString("counter_no_legacy"),counteRs.getString("createdDateTime"),counteRs.getString("lastUpdatedDateTime"));
                countdisplayList.add(counterObj);
            }          
        } catch (Exception ex) {
            System.out.println("" + ex.getMessage());
        } finally {
            daoClass.closeResultSet(counteRs);
        }

        return SUCCESS;
    }

    public String updateCounterInfo()
    {
        String nameQry = "select count(*) from pos.branch_counter where counter='" + counterName.trim() + "' and NOT counter_no='" + counterId.trim() + "'";
        count = daoClass.Fun_Int(nameQry);
        String legacyQry = "select count(*) from pos.branch_counter where counter_no_legacy='" + counterLegacyNo.trim() + "' and NOT counter_no='" + counterId.trim() + "'";
        legacyCount = daoClass.Fun_Int(legacyQry);
        if (count <= 0) {
            if (legacyCount <= 0) {
                String qry = "update pos.branch_counter set counter='" + counterName.trim()+ "', counter_no_legacy='" + counterLegacyNo.trim() + "', lastUpdatedDateTime=now() where counter_no='" + counterId + "'";
                daoClass.updatingRecord(qry);
            }
        }
        return SUCCESS;
    }

    @Override
    public String toString() {
        return "CounterUpdation{" + "counterName=" + counterName + ", counterId=" + counterId + ", counterLegacyNo=" + counterLegacyNo + ", counterCreatedDate=" + counterCreatedDate + ", counterUpdatedDate=" + counterUpdatedDate + '}';
    }

  
    public CounterUpdation() {
    }

    public CounterUpdation(String counterName, String counterId, String counterLegacyNo, String counterCreatedDate, String counterUpdatedDate) {
        this.counterName = counterName;
        this.counterId = counterId;
        this.counterLegacyNo = counterLegacyNo;
        this.counterCreatedDate = counterCreatedDate;
        this.counterUpdatedDate = counterUpdatedDate;
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

    public List getCountdisplayList() {
        return countdisplayList;
    }

    public void setCountdisplayList(List countdisplayList) {
        this.countdisplayList = countdisplayList;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public String getCounterCreatedDate() {
        return counterCreatedDate;
    }

    public void setCounterCreatedDate(String counterCreatedDate) {
        this.counterCreatedDate = counterCreatedDate;
    }

    public String getCounterUpdatedDate() {
        return counterUpdatedDate;
    }

    public void setCounterUpdatedDate(String counterUpdatedDate) {
        this.counterUpdatedDate = counterUpdatedDate;
    }
    
    

}
