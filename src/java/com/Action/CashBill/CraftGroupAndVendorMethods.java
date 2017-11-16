/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Action.CashBill;

import static com.Action.CashBill.DcsrReport.daoClass;
import com.DAO.DaoClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author JaVa
 */
public class CraftGroupAndVendorMethods {

  public   String getVendorList(String paraFromDate, String paraToDate, String vendortype) {
        StringBuilder vendorList = new StringBuilder("'0'");
        try {
            String queryForVendor = "SELECT SQL_CACHE DISTINCT lineitem.vendor \n"
                    + " FROM pos.lineitem lineitem \n"
                    + " INNER JOIN \n"
                    + " pos.header header \n"
                    + " ON (lineitem.sales_orderno = header.sales_orderno) \n"
                    + " WHERE (header.cancelFlag = 'N') AND (lineitem.date_time between '" + paraFromDate.trim() + "'  AND '" + paraToDate.trim() + "') ";
            if (!vendortype.equalsIgnoreCase("All")) {
                queryForVendor = queryForVendor.concat("and lineitem.vendor like '" + vendortype + "%'");
            }
           // System.out.println("VENDOR :: "+queryForVendor);
            ResultSet queryForVendorRs = daoClass.Fun_Resultset(queryForVendor);
            while (queryForVendorRs.next()) {
                vendorList.append(",'").append(queryForVendorRs.getString("vendor")).append("'");
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        return vendorList.toString();
    }

    public ArrayList<String> getCraftList(String paraFromDate, String paraToDate, String reportingType) throws SQLException {
        ArrayList<String> craftList = new ArrayList<>();
       PreparedStatement ps = null ;
        Connection con = null;
        try {
            String queryForCraft = "SELECT SQL_CACHE DISTINCT  lineitem.materialCraftGroup \n"
                    + " FROM    pos.lineitem lineitem \n"
                    + " INNER JOIN \n"
                    + " pos.header header \n"
                    + " ON (lineitem.sales_orderno = header.sales_orderno) \n"
                    + " WHERE (header.cancelFlag = 'N') AND (lineitem.date_time between ?  AND ?)";

            System.out.println("==>reportingType"+reportingType);  
          con = daoClass.Fun_DbCon();
          
            ps = con.prepareStatement(queryForCraft);
          ps.setString(1, paraFromDate.trim());
          ps.setString(2, paraToDate.trim());
            ResultSet craftGrpRs = ps.executeQuery();
            while (craftGrpRs.next()) {
                if (reportingType.equalsIgnoreCase("csi")) {
                    if (!(craftGrpRs.getString("materialCraftGroup").equalsIgnoreCase(DaoClass.getRestricedCraftGroup()))) {
                        craftList.add(craftGrpRs.getString("materialCraftGroup").trim());
                    }
                } else if (!(reportingType.equalsIgnoreCase("csi"))) {
                    craftList.add(craftGrpRs.getString("materialCraftGroup").trim());
                }
            }
        } catch (SQLException e) {
            System.out.println("" + e.getMessage());
        }
        return craftList;
    }
}
