package com.model;

import com.DAO.AmountToString;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.DAO.DaoClass;
import com.coupon.CouponDetailsTo;
import com.to.CashBillHeaderTo;
import com.to.CashBillLineDetailTo;
import com.to.CashBillLineTo;
import com.to.CouponSalesHeaderTo;
import com.to.CouponSalesPrintTo;
import com.to.PurchaseOrderHeaderTo;
import com.to.PurchaseOrderLineTo;
import java.text.DecimalFormat;

public class GenericModule {
    
    static DecimalFormat decimalFormat = new DecimalFormat("#.00");
    
    public static PurchaseOrderHeaderTo getPurchaseOrderPrint(String purchaseOrderNo, String counterType) {
        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet lineRS = null;
        ResultSet headerRS = null;
        DaoClass daoClass = new DaoClass();
        Connection con = daoClass.Fun_DbCon();
        PurchaseOrderHeaderTo poht = new PurchaseOrderHeaderTo();
        try {
            stmt = con.createStatement();
            stmt1 = con.createStatement();
            StringBuilder headerQuery = new StringBuilder();
            //            headerQuery.append("SELECT cust.custname,cust.street1,cust.street2,cust.country,cust.state,cust.city,");
            //            headerQuery.append("cust.zipcode,DATE_FORMAT(he.date_time,'%d-%m-%Y %h:%m:%s'),he.emp_id,he.show_room,he.discunt_approval,");
            //            headerQuery.append("he.pck_charge,he.bil_amt, he.txt_bilAmt, he.net_amt,he.sales_orderno, branch.branch_code,emp.emp_name, branch.login_userid ");
            //            headerQuery.append("FROM coustomer_info cust RIGHT JOIN header he  on cust.sales_ordno=he.sales_orderno JOIN branch_counter branch on he.show_room=branch.branch_code ");
            //            headerQuery.append("JOIN emp_master emp  on branch.login_userid=emp.emp_id WHERE he.sales_orderno = ");
            //            headerQuery.append(purchaseOrderNo.trim());

            /*PART : 1 LAST MODIFIED @ 14-11-2014
             headerQuery.append("SELECT cust.custname,");
             headerQuery.append("DATE_FORMAT(he.date_time,'%d-%m-%Y'),he.emp_id,he.show_room,he.discunt_approval,");
             headerQuery.append("he.pck_charge,he.bil_amt, he.txt_bilAmt, he.net_amt,he.sales_orderno, branch.branch_code,emp.emp_name, branch.login_userid ");
             headerQuery.append("FROM coustomer_info cust RIGHT JOIN header he on cust.sales_ordno=he.sales_orderno JOIN branch_counter branch on he.show_room=branch.branch_code ");
             headerQuery.append("JOIN emp_master emp  on branch.login_userid=emp.emp_id WHERE he.sales_orderno = ");
             headerQuery.append(purchaseOrderNo.trim());
             headerQuery.append("");*/
            /*PART :2 LAST MODIFIED @ 15-11-2014
             headerQuery.append("SELECT cust.custname,");
             headerQuery.append("DATE_FORMAT(he.date_time,'%d-%m-%Y'),he.emp_id,he.show_room,he.discunt_approval,");
             headerQuery.append("he.pck_charge,he.bil_amt, he.txt_bilAmt, he.net_amt,he.sales_orderno, branch.branch_code,emp.emp_name,he.counterpk,branch.login_userid ");
             headerQuery.append("FROM coustomer_info cust RIGHT JOIN header he on cust.sales_ordno=he.sales_orderno JOIN branch_counter branch on he.show_room=branch.branch_code ");
             headerQuery.append("JOIN emp_master emp  on branch.login_userid=emp.emp_id WHERE he.sales_orderno = ");
             headerQuery.append(purchaseOrderNo.trim());
             headerQuery.append("");
             %Y-%c-%e %H:%i:%s
             */
            headerQuery.append("SELECT cust.custname,");
            headerQuery.append("DATE_FORMAT(he.date_time,'%d-%m-%Y %H:%i:%s'),he.emp_id,he.show_room,he.discunt_approval,");
            headerQuery.append("he.pck_charge,he.bil_amt,he.txt_bilAmt,he.net_amt,he.sales_orderno,emp.emp_name,he.counterpk,he.manual_bill_no,");
            headerQuery.append(" he.gst_percentage,he.sgst_value,he.cgst_value ");
            headerQuery.append("FROM coustomer_info cust RIGHT JOIN header he on cust.sales_ordno=he.sales_orderno JOIN ");
            headerQuery.append("emp_master emp on he.emp_id=emp.emp_id WHERE he.sales_orderno = ");
            headerQuery.append(purchaseOrderNo.trim());
            headerQuery.append("");
            //String headerQuery="SELECT cust.custname,cust.street1,cust.street2,cust.country,cust.state,cust.city,cust.zipcode,DATE_FORMAT(he.date_time,'%d-%m-%Y %h:%m:%s'),he.emp_id,he.show_room,he.discunt_approval,he.pck_charge,he.bil_amt, he.txt_bilAmt, he.net_amt,he.sales_orderno, emp.emp_name FROM coustomer_info cust RIGHT JOIN header he  on cust.sales_ordno=he.sales_orderno JOIN emp_master emp  on he.emp_id=emp.emp_id WHERE he.sales_orderno ='"+purchaseOrderNo.trim()+"' ";
            headerRS = stmt.executeQuery(headerQuery.toString());
            while (headerRS.next()) {
                String sql = "";
                List<PurchaseOrderLineTo> lineTos = new ArrayList();
                //if (!(counterType.equals("Billets/RM"))) {
                int counterNumber = daoClass.Fun_Int("select counter_pk from branch_counter where counter='" + counterType.trim() + "'");
                if (counterNumber != 5) {
                    //sql = "SELECT lineitem.item,lineitem.material,lineitem.descrip,SUM(lineitem.qty),lineitem.price,SUM(lineitem.prc_value),lineitem.discount_percentage,SUM(lineitem.discount_value),lineitem.vat_percentage,SUM(lineitem.vat_value),SUM(lineitem.calcu_value),lineitem.vendor FROM lineitem lineitem where lineitem.sales_orderno='" + purchaseOrderNo.trim() + "' group by lineitem.material order by lineitem.item";
                    sql = "SELECT lineitem.item,lineitem.material,lineitem.descrip,lineitem.qty,lineitem.price,lineitem.prc_value,lineitem.discount_percentage,lineitem.discount_value,lineitem.vat_percentage,lineitem.vat_value,lineitem.calcu_value,lineitem.vendor FROM lineitem lineitem where lineitem.sales_orderno='" + purchaseOrderNo.trim() + "' ";
                } //else if (counterType.equals("Billets/RM")) {
                else if (counterNumber == 5) {
                    sql = "SELECT lineitem.item,lineitem.material,lineitem.descrip,lineitem.qty,lineitem.price,lineitem.prc_value,lineitem.discount_percentage,lineitem.discount_value,lineitem.vat_percentage,lineitem.vat_value,lineitem.calcu_value,lineitem.vendor FROM lineitem lineitem where lineitem.sales_orderno='" + purchaseOrderNo.trim() + "'";
                }
                lineRS = stmt1.executeQuery(sql);
                while (lineRS.next()) {
                    lineTos.add(new PurchaseOrderLineTo(lineRS.getInt(1), lineRS.getString(2), lineRS.getString(3), lineRS.getDouble(4), lineRS.getFloat(5), lineRS.getFloat(6), lineRS.getDouble(7), lineRS.getFloat(8), lineRS.getFloat(9), lineRS.getFloat(10), lineRS.getFloat(11), lineRS.getString(12)));
                }
                poht.setCounterName(counterType);
                poht.setCustomeName(headerRS.getString(1));
                //poht.setStreetOne(headerRS.getString(2));
                //poht.setStreetTwo(headerRS.getString(3));
                //poht.setCountry(headerRS.getString(4));
                //poht.setState(headerRS.getString(5));
                //poht.setCity(headerRS.getString(6));
                //poht.setZipCode(headerRS.getString(7));
                poht.setDateTime(headerRS.getString(2));
                poht.setEmpId(headerRS.getString(3));
                poht.setShowRoom(headerRS.getString(4));
                poht.setDiscountApproval(headerRS.getString(5));
                poht.setPackageCharge(headerRS.getFloat(6));
                poht.setBillAmount(headerRS.getFloat(7));
                //System.out.println("BillAmount : " + headerRS.getString(7));
                poht.setBillAmountWord(headerRS.getString(8));
                poht.setNetBillAmount(headerRS.getFloat(9));
                poht.setSalesOrderNo(headerRS.getString(10));
                //poht.setBranchName(headerRS.getString(11));
                poht.setEmpName(headerRS.getString(11));
                if (headerRS.getInt(12) > 0) {
                    poht.setCounterTitle(daoClass.Fun_Str("select counter_no_legacy from branch_counter where counter_pk='" + headerRS.getInt(12) + "'") + "-" + daoClass.Fun_Str("select counter from branch_counter where counter_pk='" + headerRS.getInt(12) + "'"));
                } else {
                    poht.setCounterTitle("");
                }
                poht.setManualBillNumber(headerRS.getString(13));
                poht.setGstPercentage(headerRS.getFloat(14));
                poht.setSgst(headerRS.getFloat(15));
                poht.setCgst(headerRS.getFloat(16));
                poht.setHsnCode(daoClass.Fun_Str("SELECT craft_tax_price.hsn_code FROM (pos.lineitem lineitem INNER JOIN pos.craft_tax_price craft_tax_price ON (lineitem.materialCraftGroup = craft_tax_price.craft_group))INNER JOIN pos.header header ON (header.sales_orderno = lineitem.sales_orderno) WHERE (header.sales_orderno = '" + purchaseOrderNo.trim() + "')"));
                poht.setPurchaseOrderLineTos(lineTos);
            }
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            daoClass.closeResultSet(headerRS);
        }
        return poht;
    }
    
    public static CashBillHeaderTo getCashBillOrderPrint(String cashBillOrderId) {
        int itemSno = 0;
        ResultSet rs = null;
        Statement stmt = null;
        Statement stmt1 = null;
        Statement stmt2 = null;
        ResultSet lineRS = null;
        ResultSet headerRS = null;
        DaoClass daoClass = new DaoClass();
        Connection con = daoClass.Fun_DbCon();
        CashBillHeaderTo cbht = new CashBillHeaderTo();
        float totCashAmt = 0.0f;
        try {
            stmt = con.createStatement();
            stmt1 = con.createStatement();
            stmt2 = con.createStatement();
            StringBuilder headerQuery = new StringBuilder();
            String billCounterNo = null;
            String createdDateTime = null;
            //1===String cashBillLineQuery = "SELECT clm.counterbill_no,clm.cashbill_amt,DATE_FORMAT(clm.cashbill_dateTime,'%d-%m-%Y %h:%m:%s'),clm.cashBill_id FROM cashbill_lineitem_master clm WHERE clm.cashBill_id = '" + cashBillOrderId + "'";
            //2===String cashBillLineQuery = "SELECT clm.counterbill_no,clm.cashbill_amt,DATE_FORMAT(clm.cashbill_dateTime,'%d-%m-%Y %h:%m:%s'),clm.cashBill_id,chm.total_amt FROM pos.cashbill_header_master chm INNER JOIN pos.cashbill_lineitem_master clm ON(chm.cashBill_id ='" + cashBillOrderId + "')";
            String cashBillLineQuery = "SELECT clm.counterbill_no,clm.cashbill_amt,DATE_FORMAT(clm.cashbill_dateTime,'%d-%m-%Y %H:%i:%s'),clm.cashBill_id FROM pos.cashbill_lineitem_master clm where clm.cashBill_id='" + cashBillOrderId + "'";
            String couponRedemtValue = "select coupon_redu from cashbill_header_master where cashBill_id='" + cashBillOrderId.trim() + "'";
            headerRS = stmt.executeQuery(cashBillLineQuery);
            List<CashBillLineTo> billLineTos = new ArrayList();
            while (headerRS.next()) {
                CashBillLineTo cblt = new CashBillLineTo();
                List<CashBillLineDetailTo> billLineDetailTos = new ArrayList();
                billCounterNo = headerRS.getString("clm.counterbill_no");
                createdDateTime = headerRS.getString("DATE_FORMAT(clm.cashbill_dateTime,'%d-%m-%Y %H:%i:%s')");
                cblt.setBillCounterNo(billCounterNo);
                String sql = "SELECT lineitem.item,lineitem.material,ROUND(SUM(lineitem.qty),3),lineitem.descrip,lineitem.price,SUM(lineitem.prc_value),lineitem.discount_percentage,SUM(lineitem.discount_value),lineitem.vat_percentage,SUM(lineitem.vat_value),lineitem.pck_charge FROM lineitem lineitem where lineitem.sales_orderno='" + billCounterNo + "' group by lineitem.material order by lineitem.item";
                //String sql = "SELECT lineitem.item,lineitem.material,lineitem.qty,lineitem.descrip,lineitem.price,lineitem.prc_value,lineitem.discount_percentage,lineitem.discount_value,lineitem.vat_percentage,lineitem.vat_value,lineitem.pck_charge FROM lineitem lineitem where lineitem.sales_orderno='" + billCounterNo + "' order by lineitem.item";
                lineRS = stmt1.executeQuery(sql);
                Float packCharge = 0.0f;
                while (lineRS.next()) {
                    Float priceValue = lineRS.getFloat(6);
                    Float discout = lineRS.getFloat(8);
                    Float vatValue = lineRS.getFloat(10);
                    Float value = (priceValue - discout + vatValue);
                    packCharge = lineRS.getFloat(11);
                    totCashAmt += value;
                    itemSno = itemSno + 1;
                    billLineDetailTos.add(new CashBillLineDetailTo(itemSno, lineRS.getString(2), lineRS.getFloat(3), lineRS.getString(4), lineRS.getFloat(5), priceValue, lineRS.getFloat(7), discout, lineRS.getFloat(9), vatValue, packCharge));
                }
                totCashAmt += packCharge;
                totCashAmt = Math.round(totCashAmt);
                cblt.setBillLineDetailTos(billLineDetailTos);
                billLineTos.add(cblt);
            }
            //headerQuery.append("SELECT cust.custname,cust.street1,cust.street2,cust.country,cust.state,cust.city,");
            //headerQuery.append("cust.zipcode,DATE_FORMAT(he.date_time,'%d-%m-%Y %h:%m:%s'),he.emp_id,he.show_room,he.discunt_approval,");
            //headerQuery.append("he.pck_charge,he.bil_amt, he.txt_bilAmt, he.net_amt,he.sales_orderno, branch.branch_code,emp.emp_name, branch.login_userid ");
            //headerQuery.append("FROM coustomer_info cust RIGHT JOIN header he  on cust.sales_ordno=he.sales_orderno JOIN branch_counter branch on he.show_room=branch.branch_code ");
            //headerQuery.append("JOIN emp_master emp  on branch.login_userid=emp.emp_id WHERE he.sales_orderno = ");
            //headerQuery.append(billCounterNo);
            headerQuery.append("SELECT cust.custname,");
            headerQuery.append("DATE_FORMAT(he.date_time,'%d-%m-%Y %H:%i:%s'),he.emp_id,he.show_room,he.discunt_approval,");
            headerQuery.append("he.pck_charge,he.bil_amt, he.txt_bilAmt, he.net_amt,he.sales_orderno, branch.branch_code,emp.emp_name, branch.login_userid ");
            headerQuery.append("FROM coustomer_info cust RIGHT JOIN header he  on cust.sales_ordno=he.sales_orderno JOIN branch_counter branch on he.show_room=branch.branch_code ");
            headerQuery.append("JOIN emp_master emp  on branch.login_userid=emp.emp_id WHERE he.sales_orderno = ");
            headerQuery.append(billCounterNo);
            headerQuery.append("");
            rs = stmt2.executeQuery(headerQuery.toString());
            AmountToString ntw = new AmountToString();
            while (rs.next()) {
                cbht.setCustomeName(rs.getString(1));
                //cbht.setStreetOne(rs.getString(2));
                //cbht.setStreetTwo(rs.getString(3));
                //cbht.setCountry(rs.getString(4));
                //cbht.setState(rs.getString(5));
                //cbht.setCity(rs.getString(6));
                //cbht.setZipCode(rs.getString(7));
                //cbht.setDateTime(rs.getString(2));
                cbht.setDateTime(createdDateTime);
                cbht.setEmpId(rs.getString(3));
                cbht.setShowRoom(rs.getString(4));
                cbht.setDiscountApproval(rs.getString(5));
                cbht.setPackageCharge(rs.getFloat(6));
                cbht.setCouponRedemValue(Float.valueOf(daoClass.Fun_Str(couponRedemtValue)));
                cbht.setBillAmount(rs.getFloat(7));
                if (cbht.getCouponRedemValue() > 0) {
                    Float couponRemdemtCashAmt = totCashAmt - Float.valueOf(daoClass.Fun_Str(couponRedemtValue));
                    cbht.setBillAmountWord(ntw.EnglishNumber(couponRemdemtCashAmt.longValue()));
                    cbht.setNetBillAmount(couponRemdemtCashAmt);
                } else if (cbht.getCouponRedemValue() == 0) {
                    cbht.setBillAmountWord(ntw.EnglishNumber((long) totCashAmt));
                    cbht.setNetBillAmount(totCashAmt);
                }
                cbht.setSalesOrderNo(cashBillOrderId);
                cbht.setBranchName(rs.getString(11));
                //cbht.setEmpName(rs.getString(12));
                String getCreatedEmpId_Qry = "select user_id from pos.cashbill_header_master where cashBill_id='" + cashBillOrderId.trim() + "'";
                String createdEmpId = daoClass.Fun_Str(getCreatedEmpId_Qry);
                cbht.setEmpName(daoClass.Fun_Str("select emp_name from emp_master where emp_id='" + createdEmpId.trim() + "'"));
                cbht.setBillLineTos(billLineTos);
            }
            
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            daoClass.closeResultSet(rs);
        }
        return cbht;
    }
    
    public static Integer insertNewCouponData(String type, String descp, Float rate, Integer from, Integer to) {
        DaoClass daoClass = new DaoClass();
        Connection con = daoClass.Fun_DbCon();
        PreparedStatement ps = null;
        Integer isError = 0;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement("insert into coupon_master(coupon_type,coupon_descp,coupon_rate,coupon_from,coupon_to)values(?,?,?,?,?)");
            ps.setString(1, type);
            ps.setString(2, descp);
            ps.setFloat(3, rate);
            ps.setInt(4, from);
            ps.setInt(5, to);
            isError = ps.executeUpdate();
            System.out.println("");
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            if (isError > 0) {
                try {
                    con.commit();
                    daoClass.closeStatement(ps);
                } catch (SQLException ex) {
                    System.err.println(ex);
                }
            }
        }
        return isError;
    }
    
    public static CouponSalesHeaderTo getCouponOrderPrint(String couponBillNo) {
        DaoClass daoClass = new DaoClass();
        Connection con = daoClass.Fun_DbCon();
        Statement couponHeaderStmt = null;
        ResultSet couponHeaderRS = null;
        Statement couponStmt = null;
        ResultSet couponRS = null;
        CouponSalesHeaderTo csht = new CouponSalesHeaderTo();
        try {
            StringBuilder couponHeader = new StringBuilder();
            couponHeader.append("SELECT cust.custname,cust.street1,cust.street2,cust.country,cust.state,cust.city,");
            couponHeader.append("cust.zipcode,DATE_FORMAT(he.date_time,'%d-%m-%Y %h:%m:%s'),he.emp_id,he.show_room,");
            couponHeader.append("branch.branch_code,emp.emp_name,branch.login_userid ");
            couponHeader.append("FROM coustomer_info cust RIGHT JOIN header he  on cust.sales_ordno=he.sales_orderno JOIN branch_counter branch on he.show_room=branch.branch_code ");
            couponHeader.append("JOIN emp_master emp  on branch.login_userid=emp.emp_id WHERE he.sales_orderno = '");
            couponHeader.append(couponBillNo.trim());
            couponHeader.append("'");
            couponHeaderStmt = con.createStatement();
            System.out.println("Coupon Qurry :   " + couponHeader);
            AmountToString amountToString = new AmountToString();
            couponHeaderRS = couponHeaderStmt.executeQuery(couponHeader.toString());
            while (couponHeaderRS.next()) {
                Integer slNo = 1;
                Float tempTotal = 0.0f;
                List<CouponSalesPrintTo> csps = new ArrayList();
                couponStmt = con.createStatement();
                String qry = "SELECT cm.coupon_type,trans.coupon_no,cm.coupon_descp, cm.coupon_rate FROM coupon_transcation trans JOIN coupon_master cm ON trans.coupon_id = cm.coupon_id WHERE trans.couponsales_id = '" + couponBillNo.trim() + "'";
                couponRS = couponStmt.executeQuery(qry);
                while (couponRS.next()) {
                    Float unitValue = couponRS.getFloat(4);
                    tempTotal += unitValue;
                    csps.add(new CouponSalesPrintTo(slNo, couponRS.getString(1), couponRS.getString(2), couponRS.getString(3), unitValue));
                    slNo++;
                }
                csht.setCustomeName(couponHeaderRS.getString(1));
                csht.setStreetOne(couponHeaderRS.getString(2));
                csht.setStreetTwo(couponHeaderRS.getString(3));
                csht.setCountry(couponHeaderRS.getString(4));
                csht.setState(couponHeaderRS.getString(5));
                csht.setCity(couponHeaderRS.getString(6));
                csht.setZipCode(couponHeaderRS.getString(7));
                csht.setDateTime(couponHeaderRS.getString(8));
                csht.setEmpId(couponHeaderRS.getString(9));
                csht.setShowRoom(couponHeaderRS.getString(10));
                csht.setBranchName(couponHeaderRS.getString(11));
                csht.setEmpId(couponHeaderRS.getString(12));
                csht.setEmpName(couponHeaderRS.getString(13));
                csht.setTotalValue(tempTotal);
                csht.setTotalValueInText(amountToString.EnglishNumber(tempTotal.longValue()));
                csht.setCouponSalesPrintTos(csps);
                
            }
        } catch (SQLException ex) {
            System.err.println("Exc In CouponBill:  " + ex);
        } finally {
            daoClass.closeResultSet(couponRS);
        }
        return csht;
    }
    
    public Integer createCouponSales() {
        Integer insInserted = 0;
        
        return insInserted;
    }
    
    public static List<CouponDetailsTo> couponDetails(Integer couponId, Integer from, Integer to) {
        DaoClass daoClass = new DaoClass();
        Connection con = daoClass.Fun_DbCon();
        List<CouponDetailsTo> cdts = new ArrayList();
        Statement stmt = null;
        Statement stmt1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        try {
            stmt = con.createStatement();
            stmt1 = con.createStatement();
            String couponDetailQuerry = "select * from coupon_master where coupon_id=" + couponId + "";
            rs = stmt.executeQuery(couponDetailQuerry);
            if (rs.next()) {
                Integer couponIds = rs.getInt(1);
                String couponTypes = rs.getString(2);
                String couponDescp = rs.getString(3);
                Float rate = rs.getFloat(4);
                Integer froms = rs.getInt(5);
                Integer tos = rs.getInt(6);
                if (from >= froms && to <= tos) {
                    for (int i = from; i <= to; i++) {
                        String transactionCouponQuery = "select * from coupon_transcation where coupon_id='" + couponIds + "' and coupon_no=" + i + "";
                        rs1 = stmt1.executeQuery(transactionCouponQuery);
                        boolean status = rs1.next();
                        if (!status) {
                            cdts.add(new CouponDetailsTo(couponIds, couponTypes, couponDescp, i, rate));
                        }
                    }
                } else {
                    System.out.println("This Coupon Range is Not Available ");
                }
            }
            
        } catch (SQLException ex) {
            System.err.println(ex);
        } finally {
            daoClass.closeResultSet(rs);
        }
        return cdts;
    }
    
    public static void main(String[] args) {
        getCashBillOrderPrint("Cash00006");
        
    }
}
