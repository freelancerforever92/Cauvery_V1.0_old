package com.Action;

import java.sql.*;
import com.DAO.*;
import java.util.*;
import com.pojo.*;
import com.opensymphony.xwork2.ActionSupport;

/*@author pranesh*/
public class SalsHistory_Action extends ActionSupport {

    ResultSet rs;
    DaoClass cado = new DaoClass();
    List salesHistoryList = new ArrayList();

    public SalsHistory_Action() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String Fun_FillsalesHistory() {
        System.out.println("-----------HISTORY aCTION CLASS");
        //String salesHistory_qry = "SELECT header.sales_orderno,lineitem.item,lineitem.material,lineitem.qty,lineitem.price,header.pay_type,header.emp_id,lineitem.vendor,lineitem.date_time FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)";
        String salesHistory_qry = "SELECT header.sales_orderno,lineitem.item,lineitem.material,lineitem.qty,lineitem.price,lineitem.prc_value,header.pay_type,header.emp_id,lineitem.vendor,lineitem.date_time FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)";
        System.out.println("History_qry-----------"+salesHistory_qry);
        try {
            rs = cado.Fun_Resultset(salesHistory_qry);
            while (rs.next()) {
                //SalesHistoryTo salesPojo = new SalesHistoryTo(rs.getString("sales_orderno"), rs.getString("item"), rs.getString("material"), rs.getString("qty"), rs.getString("price"), rs.getString("prc_value"),rs.getString("pay_type"), rs.getString("emp_id"), rs.getString("vendor"), rs.getString("date_time"));
                //salesHistoryList.add(salesPojo);
                System.out.println("LIST VALUES : ---------->  :   "+salesHistoryList);
            }
        } catch (Exception ex) {
            System.out.println("Exception In Filling SalesHistory :   " + ex);
        }
        return SUCCESS;
    }

    public List getSalesHistoryList() {
        return salesHistoryList;
    }

    public void setSalesHistoryList(List salesHistoryList) {
        this.salesHistoryList = salesHistoryList;
    }
}
