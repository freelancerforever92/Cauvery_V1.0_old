package com.reports.source;

import com.DAO.*;
import com.opensymphony.xwork2.ActionSupport;
import com.pojo.SalesOrderPojo;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/*@author pranesh*/
public class GetHeaderDetails extends ActionSupport {
    
    ResultSet resultSet = null;
    List salesOrderRecordList = new ArrayList();
    DaoClass daoClass = new DaoClass();
    
    public GetHeaderDetails() {
    }
    
    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String getSalesRecords() {
        try {
            String getHeaderRecord_Query = "select sales_orderno,show_room,emp_id,bil_amt from header";
            System.out.println("" + getHeaderRecord_Query);
            resultSet = daoClass.Fun_Resultset(getHeaderRecord_Query);
            while (resultSet.next()) {
                SalesOrderPojo orderPojo = new SalesOrderPojo(resultSet.getString("sales_orderno"), resultSet.getString("sales_orderno"), resultSet.getString("emp_id"), resultSet.getFloat("bil_amt"));
                salesOrderRecordList.add(orderPojo);
            }
        } catch (Exception ex) {
            System.out.println("Exception in fetching the records  :  " + ex);
        } finally {
            daoClass.closeResultSet(resultSet);
        }
        return SUCCESS;
    }
    
    public List getSalesOrderRecordList() {
        return salesOrderRecordList;
    }
    
    public void setSalesOrderRecordList(List salesOrderRecordList) {
        this.salesOrderRecordList = salesOrderRecordList;
    }
}
