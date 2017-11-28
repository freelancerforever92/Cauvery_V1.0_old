package com.schudeler;

import com.DAO.DaoClass;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@DisallowConcurrentExecution
public class CornSchudelerTrigger implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        System.out.println("next triger time==>" + jec.getNextFireTime());
        runJob(jec);
    }

    void runJob(JobExecutionContext jec) {
        String jobName = jec.getJobDetail().getKey().getName();
        System.out.println("time" + jobName);

        try {
            
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            DaoClass daoClass = new DaoClass();
            
            XmlToBean td = new XmlToBean();
            ArrayList<String> salesOrderNo = new ArrayList<>();
            try {
                con = daoClass.Fun_DbCon();
                String query1 = "SELECT counterbill_no FROM pos.cashbill_lineitem_master where reportMoving=?";
                ps = con.prepareStatement(query1);
                ps.setInt(1, 0);
                rs = ps.executeQuery();
                while (rs.next()) {
                    salesOrderNo.add(rs.getString("counterbill_no"));
                    //String crftName = daoClass.Fun_Str("SELECT materialCraftGroup FROM pos.lineitem where sales_orderno='"+rs.getString("counterbill_no")+"'");
                    //System.out.println("CRAFT GROUP :: "+crftName+" <-> "+rs.getString("counterbill_no"));
//                    String qry1 = "SELECT materialCraftGroup FROM pos.lineitem where sales_orderno='"+rs.getString("counterbill_no")+"'";
//                    System.out.println("-> ::"+qry1);
//                    System.out.println("#############---&&&&&&&&&&&&&---********* :: "+daoClass.Fun_Str(qry1));
                }
                if (salesOrderNo.size() > 0) {
                    //Data to report table variables
                    LineItems items = new LineItems();
                    String counterbill_no = "Empty";
                    int plantId = 0;
                    String cashbill_amt = "Empty";
                    String date_time = null;
                    String gst_percentage = "Empty";
                    String sgst_amt = "Empty";
                    String cgst_amt = "Empty";
                    String bill_amt = "Empty";
                    String pck_charge = "Empty";
                    String net_amt = "Empty";
                    String txt_billAmt = "Empty";
                    String paymentType = "Empty";
                    String emp_name = "Empty";
                    String user_id = "Empty";
                    String manual_bill_no = " ";
                    String counter_name = " ";
                    String counter_type = " ";
                    List<LineItem> Listitems = new ArrayList<>();
                    for (String sNo : salesOrderNo) {
                        String q1 = "SELECT sales_orderno,materialCraftGroup FROM pos.lineitem where sales_orderno='"+sNo+"'";
                        System.out.println("AAA---BBB---CCC---DDD :   "+q1);
                        //Query
                        String query = "SELECT item,material,materialCraftGroup,descrip,vendor,qty,price,prc_value,discount_value,discount_percentage,vat_percentage,vat_value,header.sgst_value,header.gst_percentage,header.cgst_value,calcu_value,cashbill_lineitem_master.plantId,cashbill_amt,header.date_time, bil_amt,lineitem.pck_charge,net_amt,txt_bilAmt,paymentType,emp_name,user_id,manual_bill_no,counter,counter_no_legacy FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (lineitem.sales_orderno = cashbill_lineitem_master.counterbill_no )) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE (header.cancelFlag='N') and lineitem.sales_orderno=?";
                        //String query = "SELECT item,material,materialCraftGroup,descrip,vendor,qty,price,prc_value,discount_value,discount_percentage,vat_percentage,vat_value,calcu_value,cashbill_lineitem_master.plantId,cashbill_amt,header.date_time, bil_amt,lineitem.pck_charge,net_amt,txt_bilAmt,paymentType,emp_name,user_id,manual_bill_no,counter,counter_no_legacy FROM ((((pos.cashbill_lineitem_master cashbill_lineitem_master INNER JOIN pos.lineitem lineitem ON (cashbill_lineitem_master.counterbill_no = lineitem.sales_orderno)) INNER JOIN  pos.header header  ON (header.sales_orderno =   cashbill_lineitem_master.counterbill_no)) INNER JOIN pos.branch_counter branch_counter ON (header.counterpk = branch_counter.counter_pk)) INNER JOIN    pos.cashbill_header_master cashbill_header_master  ON (cashbill_lineitem_master.cashBill_id = cashbill_header_master.cashBill_id))INNER JOIN pos.emp_master emp_master ON (cashbill_header_master.user_id = emp_master.emp_id) WHERE (header.cancelFlag='N') and lineitem.sales_orderno=?";
                        ps = con.prepareStatement(query);
                        //Value
                        //sNo = "20170071164";
                        ps.setString(1,sNo);
                        rs = ps.executeQuery();

                        while (rs.next()) {
                            System.out.println("result==>" + rs.getString("date_time"));
                            //Value for list 
                            LineItem item1 = new LineItem();
                            item1.setId(rs.getInt("item"));
                            item1.setMaterial(rs.getString("material").trim());
                            item1.setMaterialCraftGroup(rs.getString("materialCraftGroup").trim());
                            item1.setDescription(rs.getString("descrip").trim());
                            item1.setVendor(rs.getString("vendor").trim());
                            item1.setQuantity(rs.getString("qty").trim());
                            item1.setPrice(rs.getString("price").trim());
                            item1.setPrcValue(rs.getString("prc_value").trim());
                            item1.setDiscount(rs.getString("discount_value").trim());
                            item1.setDiscountPer(rs.getString("discount_percentage").trim());
                            item1.setVat(rs.getString("vat_value").trim());
                            item1.setVatPer(rs.getString("vat_percentage").trim());
                            item1.setCalcuValu(rs.getString("calcu_value").trim());
                            Listitems.add(item1);
                            items.setItems(Listitems);
                            System.out.println("CRAFT GROUP : "+rs.getString("materialCraftGroup").trim());
                            if(rs.getString("materialCraftGroup").equalsIgnoreCase("P1") || rs.getString("materialCraftGroup").equalsIgnoreCase("P2") || rs.getString("materialCraftGroup").equalsIgnoreCase("P3")){
                                System.out.println("P1 - P2 -P3 : COUNTER'S :: "+items);
                            }

                            //uniqe valus
                            plantId = rs.getInt("plantId");
                            gst_percentage = rs.getString("gst_percentage").trim();  // for GST
                            sgst_amt = rs.getString("sgst_value").trim();            // for GST
                            cgst_amt = rs.getString("cgst_value").trim();            // for GST
                            cashbill_amt = rs.getString("cashbill_amt").trim();
                            date_time = rs.getString("date_time").trim();
                            bill_amt = rs.getString("bil_amt").trim();
                            pck_charge = rs.getString("pck_charge").trim();
                            net_amt = rs.getString("net_amt").trim();
                            txt_billAmt = rs.getString("txt_bilAmt").trim();
                            paymentType = rs.getString("paymentType").trim();
                            emp_name = rs.getString("emp_name").trim();
                            user_id = rs.getString("user_id").trim();
                            manual_bill_no = rs.getString("manual_bill_no").trim();
                            counter_name = rs.getString("counter").trim();
                            counter_type = rs.getString("counter_no_legacy").trim();
                            System.out.println("date===>" + date_time);
                        }
                        System.out.println(counterbill_no + " counterbill_no===sNo==>" + sNo);
                        if (!counterbill_no.equals(sNo)) {
                            String xml = td.convertToXml(items, LineItems.class);

                            System.out.println("==>" + xml);
                            if (xml.contains("items")) {
                                String sql = "INSERT INTO report_summary (sales_orderno,plant,xml_document,date_time,gst_percentage,sgst_amt,cgst_amt,cashbill_amt,bill_amt,pck_charge,net_amt,txt_billAmt,paymentType,user_id,emp_name,manual_bill_no,counter_name,counter_type) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//                                String sql = "INSERT INTO report_summary (sales_orderno,plant,xml_document,date_time,cashbill_amt,bill_amt,pck_charge,net_amt,txt_billAmt,paymentType,user_id,emp_name,manual_bill_no,counter_name,counter_type) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                                con = daoClass.Fun_DbCon();
                                ps = con.prepareStatement(sql);
                                ps.setString(1, sNo);
                                ps.setInt(2, plantId);
                                ps.setString(3, xml);
                                ps.setString(4, date_time);
                                ps.setString(5, gst_percentage);
                                ps.setString(6, sgst_amt);
                                ps.setString(7, cgst_amt);
                                ps.setString(8, cashbill_amt);
                                ps.setString(9, bill_amt);
                                ps.setString(10, pck_charge);
                                ps.setString(11, net_amt);
                                ps.setString(12, txt_billAmt);
                                ps.setString(13, paymentType);
                                ps.setString(14, user_id);
                                ps.setString(15, emp_name);
                                ps.setString(16, manual_bill_no);
                                ps.setString(17, counter_name);
                                ps.setString(18, counter_type);
                                int i = ps.executeUpdate();
                                System.out.println("inserting data to new Report table==>" + i);
                                if (i == 1) {
                                    String update = "UPDATE `pos`.`cashbill_lineitem_master` SET `reportMoving`=? WHERE `counterbill_no`=?";
                                    con = daoClass.Fun_DbCon();
                                    ps = con.prepareStatement(update);

                                    ps.setString(1, "1");
                                    ps.setString(2, sNo);
                                    int j = ps.executeUpdate();
                                    System.out.println("up dating header table==>" + j);
                                }
                                Listitems.clear();
                            }
                            counterbill_no = sNo;
                        }
                    }
                } else {
                    System.out.println("Nothing to update..!");
                }
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (ps != null) {
                        ps.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception e) {
                    System.out.println("Error Closeing : " + e.getMessage());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CornSchudelerTrigger.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
