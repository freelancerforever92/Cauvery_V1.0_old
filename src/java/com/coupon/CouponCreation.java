package com.coupon;

import com.DAO.DaoClass;
import com.model.GenericModule;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.CallableStatement;
import java.sql.*;
import java.util.ArrayList;
import java.util.*;
import java.util.StringTokenizer;
import org.apache.struts2.dispatcher.SessionMap;

public class CouponCreation extends ActionSupport {

    private Integer couponId;
    private String couponType;
    private String couponDescription;
    private Float couponRate;
    private Integer couponFrom;
    private Integer couponTo;
    private Integer isError = 0;
    private List<CouponTypesTo> couponTypeList;
    private List<CouponDetailsTo> couponDetailsTos;
    private String sellingCouponData;
    private Integer rowCount;
    private Vector packedCouponData;
    private Vector individualCouponData;

    DaoClass cado = new DaoClass();
    Connection con;

    private ResultSet rs = null;
    private CallableStatement insCouponsPstmt = null;
    String couponSrlNo;
    String couponCreatedStatus = "";
    private SessionMap<String, Object> sessionMap;

    public CouponCreation() {
    }

    @Override
    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String insCouponCreationDetails() {
        isError = GenericModule.insertNewCouponData(couponType.toUpperCase(), couponDescription, couponRate, couponFrom, couponTo);
        return SUCCESS;
    }

    public String createCouponBillNo() {
        couponSrlNo = "";
        try {
            int couponSalesOrderNoLength = 4;
            String salesordno_Querry = "select coupon_id from coupon_transcation";
            couponSrlNo = cado.Fun_SalesOrderNo(cado.getPlantId(), sessionMap.get("LoginCounter").toString(), salesordno_Querry, couponSalesOrderNoLength);
            System.out.println("=====++salesOrderNo+++===== :  " + couponSrlNo);
        } catch (Exception ex) {
            System.err.println("Exception In Creating SalesOrder :  " + ex);
        }
        return SUCCESS;
    }

    public String insCouponSalesDetails() {
        try {
            int spinsStatus = 0;
            System.out.println("Purchase Product            >>>>>>>>  : " + sellingCouponData);

            packedCouponData = new Vector(5);
            individualCouponData = new Vector(5);

            StringTokenizer TokenPurcProd = new StringTokenizer(sellingCouponData, ",");
            while (TokenPurcProd.hasMoreTokens()) {
                packedCouponData.add(TokenPurcProd.nextElement());
            }
            for (int i = 0; i < packedCouponData.size(); i++) {
                StringTokenizer TokenSepProd_Details = new StringTokenizer(packedCouponData.elementAt(i).toString().trim(), "*");
                while (TokenSepProd_Details.hasMoreElements()) {
                    individualCouponData.add(TokenSepProd_Details.nextElement());
                }
            }
            System.out.println("PACKED DATA SIZE :   " + individualCouponData.size());
            if (packedCouponData.size() > 1) {

                int customerID = 0;
                con = cado.Fun_DbCon();

                String insCustomerInfo_Qry = "insert into coustomer_info(custname,sales_ordno)values('" + individualCouponData.elementAt(0).toString().trim() + "','" + individualCouponData.elementAt(1).toString().trim() + "')";
                //System.out.println("insCustomerInfo_Qry :  " + insCustomerInfo_Qry);
                cado.Fun_Updat(insCustomerInfo_Qry);

                String getCustId_Qry = "select info_pk from coustomer_info where sales_ordno='" + individualCouponData.elementAt(1).toString().trim() + "'";
                //System.out.println("getCustId_Qry :  " + getCustId_Qry);
                customerID = cado.Fun_Int(getCustId_Qry);
                int n2 = 1, n3 = 2;
                for (int j = 1; j <= individualCouponData.size(); j++) {
                    String Sp_couponSales = "{call sp_CouponSales(?,?,?,?)}";
                    insCouponsPstmt = con.prepareCall(Sp_couponSales);
                    insCouponsPstmt.setString(1, individualCouponData.elementAt(1).toString().trim());//orderid
                    insCouponsPstmt.setInt(2, customerID);//customerid
                    String couponIdQry = "select coupon_id from coupon_master where coupon_type='" + individualCouponData.elementAt(j + n2).toString().trim() + "'";
                    int couponId = cado.Fun_Int(couponIdQry);
                    insCouponsPstmt.setInt(3, couponId);//
                    insCouponsPstmt.setInt(4, Integer.parseInt(individualCouponData.elementAt(j + n3).toString().trim()));
                    spinsStatus = insCouponsPstmt.executeUpdate();
                    n2 = n2 + 1;
                    n3 = n3 + 1;
                    if (spinsStatus == 1) {
                        couponCreatedStatus = "";
                        couponCreatedStatus = "insertedCoupon";
                    } else {
                        couponCreatedStatus = "";
                        couponCreatedStatus = "couponNotInserted";
                    }
                }
                System.out.println("spinsStatus  " + couponCreatedStatus);
            }
        } catch (Exception ex) {
            System.out.println("dfgfdgdfg :  " + ex);
        }
        return SUCCESS;
    }

    public String couponTypes() {
        DaoClass daoClass = new DaoClass();
        ResultSet rs = null;
        try {
            String fetchCouponTypeQuery = "select coupon_id, coupon_type from coupon_master";
            couponTypeList = new ArrayList();
            rs = daoClass.Fun_Resultset(fetchCouponTypeQuery);
            while (rs.next()) {
                couponTypeList.add(new CouponTypesTo(rs.getInt("coupon_id"), rs.getString("coupon_type")));
            }
        } catch (SQLException ex) {
            System.err.println("Exception In Fetch CouponTypes :  " + ex);
        } finally {
            daoClass.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public String listingCouponDetailsFromTo() {
        try {
            couponDetailsTos = GenericModule.couponDetails(couponId, couponFrom, couponTo);
        } catch (Exception ex) {
            System.out.println("Exception In  listingCouponDetails FromTo :  " + ex);
        }
        return "success";
    }

    public SessionMap<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(SessionMap<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

    public String getCouponCreatedStatus() {
        return couponCreatedStatus;
    }

    public void setCouponCreatedStatus(String couponCreatedStatus) {
        this.couponCreatedStatus = couponCreatedStatus;
    }

    public String getCouponSrlNo() {
        return couponSrlNo;
    }

    public void setCouponSrlNo(String couponSrlNo) {
        this.couponSrlNo = couponSrlNo;
    }

    public String getSellingCouponData() {
        return sellingCouponData;
    }

    public void setSellingCouponData(String sellingCouponData) {
        this.sellingCouponData = sellingCouponData;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponDescription() {
        return couponDescription;
    }

    public void setCouponDescription(String couponDescription) {
        this.couponDescription = couponDescription;
    }

    public Float getCouponRate() {
        return couponRate;
    }

    public void setCouponRate(Float couponRate) {
        this.couponRate = couponRate;
    }

    public Integer getCouponFrom() {
        return couponFrom;
    }

    public void setCouponFrom(Integer couponFrom) {
        this.couponFrom = couponFrom;
    }

    public Integer getCouponTo() {
        return couponTo;
    }

    public void setCouponTo(Integer couponTo) {
        this.couponTo = couponTo;
    }

    public Integer getIsError() {
        return isError;
    }

    public void setIsError(Integer isError) {
        this.isError = isError;
    }

    public List<CouponTypesTo> getCouponTypeList() {
        return couponTypeList;
    }

    public void setCouponTypeList(List<CouponTypesTo> couponTypeList) {
        this.couponTypeList = couponTypeList;
    }

    public List<CouponDetailsTo> getCouponDetailsTos() {
        return couponDetailsTos;
    }

    public void setCouponDetailsTos(List<CouponDetailsTo> couponDetailsTos) {
        this.couponDetailsTos = couponDetailsTos;
    }

}
