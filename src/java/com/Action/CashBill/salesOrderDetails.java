package com.Action.CashBill;

import java.sql.*;
import com.DAO.*;
import java.util.*;

import com.opensymphony.xwork2.ActionSupport;
import com.pojo.SalesHistoryTo;

public class salesOrderDetails extends ActionSupport {

    String salesOrderNo = "", txtCounterBillNoReturn;
    ResultSet rs = null;
    DaoClass cado = new DaoClass();
    List<SalesHistoryTo> salesHistoryTos = new ArrayList();
    List salesHisList = new ArrayList();

    public salesOrderDetails() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String fetchSalesOrderDetails() {
        try {
            String salesOrderDetailsQuery = "SELECT lineitm.item,lineitm.material,lineitm.qty,lineitm.price,hdr.net_amt,lineitm.vendor,lineitm.sales_orderno FROM lineitem lineitm INNER JOIN header hdr ON (lineitm.sales_orderno = hdr.sales_orderno) WHERE (lineitm.sales_orderno = '" + salesOrderNo.trim() + "')";
            rs = cado.Fun_Resultset(salesOrderDetailsQuery);
            while (rs.next()) {
                salesHistoryTos.add(new SalesHistoryTo(rs.getString("item"), rs.getString("material"), rs.getString("qty"), rs.getString("price"), rs.getString("net_amt"), rs.getString("vendor"), rs.getString("vendor")));
            }
        } catch (Exception ex) {
            System.out.println("Exception In fetchSalesOrderDetails :  " + ex);
        }
        return "success";
    }

    public String getTxtCounterBillNoReturn() {
        return txtCounterBillNoReturn;
    }

    public void setTxtCounterBillNoReturn(String txtCounterBillNoReturn) {
        this.txtCounterBillNoReturn = txtCounterBillNoReturn;
    }

    public List getSalesHisList() {
        return salesHisList;
    }

    public void setSalesHisList(List salesHisList) {
        this.salesHisList = salesHisList;
    }

    public List<SalesHistoryTo> getSalesHistoryTos() {
        return salesHistoryTos;
    }

    public void setSalesHistoryTos(List<SalesHistoryTo> salesHistoryTos) {
        this.salesHistoryTos = salesHistoryTos;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }
}
