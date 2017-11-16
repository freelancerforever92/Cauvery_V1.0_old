package com.Action.CounterReport;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import com.to.CounterBillSummaryViewTo;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.struts2.interceptor.SessionAware;

public class CounterBillSummary extends ActionSupport implements SessionAware {
    
    Map session;
    private String counterToDate;
    String counterFromDate;
    
    String filterFromDate;
    private String filterToDate;
    
    String CounterExport;
    String counterReportButton = "";
    String filterButtonType = "";
    
    String counterGridParameter;
    String counterButtonType;
    
    String selectedFlag;
    String filterFlagValue;
    String selectedSearchByFlag;
    
    String counterSummaryQuery;
    String filterSummaryQuery;
    String countCounterBillQuery;
    
    String getAmtCountQry;
    ResultSet getAmtCountRS = null;
    
    String getPackingCharge;
    private String loginUserType;
    float counterTotalSum;
    int totalCounterBill;
    String TOTAL = "TOTAL";
    private double columnCount;
    private double grossAmount;
    private double netAmount;
    private double discAmount;
    private double vataAmount;
    private double pckingAmount;
    private double totSalesAmount;
    ResultSet resultSet = null;
    DaoClass daoClass = new DaoClass();
    List counterBillSummaryData = new ArrayList();
    private List<String> counterNumber = new ArrayList();
    private List<String> craftGroupCode = new ArrayList();
    private List counterBillSummaryViewData = new ArrayList();
    
    public CounterBillSummary() {
    }
    
    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public String setFilterParameters() {
        try {
            setCounterFromDate(counterFromDate);
            setCounterToDate(counterToDate);
            setCounterReportButton(counterButtonType);
            setSelectedFlag(selectedFlag);
        } catch (Exception ex) {
            System.out.println("Exception in setting Counter Parameter : " + ex);
        }
        return SUCCESS;
    }
    
    public String CraftWiseCounterReports() {
        setSelectedFlag(selectedFlag);
        setCounterToDate(counterToDate);
        setCounterFromDate(counterFromDate);
        setCounterReportButton(counterButtonType);
        return SUCCESS;
    }
    
    public String getCounterSummaryData() {
        try {
            StringTokenizer dat_tok = new StringTokenizer(counterGridParameter, "#");
            while (dat_tok.hasMoreElements()) {
                counterFromDate = dat_tok.nextElement().toString();
                counterToDate = dat_tok.nextElement().toString();
                counterReportButton = dat_tok.nextElement().toString();
                filterFlagValue = dat_tok.nextElement().toString();
            }
            counterToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + counterToDate.trim() + "',1))");
            String yesterdayDate = daoClass.Fun_Str("SELECT Date(SUBDATE(NOW(),1))");
            if (counterReportButton.equals("") || counterReportButton.equalsIgnoreCase(null)) {
                //AND(header.cancelFlag<>'X')
                counterSummaryQuery = "SELECT header.sales_orderno,header.manual_bill_no,lineitem.item,lineitem.material,lineitem.descrip,lineitem.qty,lineitem.materialCraftGroup,lineitem.calcu_value,header.emp_id,header.show_room,lineitem.discount_percentage,lineitem.discount_value,lineitem.vat_percentage,lineitem.vat_value,lineitem.pck_charge, DATE_FORMAT(header.date_time,'%e-%c-%Y %H:%i:%s')as CreatedDateTime FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + yesterdayDate.trim() + "%')";
                countCounterBillQuery = "select count(sales_orderno) from pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + yesterdayDate.trim() + "%')";
            } else if (counterReportButton.equalsIgnoreCase("Detailed-View")) {
                if (filterFlagValue.equalsIgnoreCase("Select")) {
                    counterSummaryQuery = "SELECT header.sales_orderno,header.manual_bill_no,lineitem.item,lineitem.material,lineitem.descrip,lineitem.qty,lineitem.materialCraftGroup,lineitem.calcu_value,header.emp_id,header.show_room,lineitem.discount_percentage,lineitem.discount_value,lineitem.vat_percentage,lineitem.vat_value,lineitem.pck_charge, DATE_FORMAT(header.date_time,'%e-%c-%Y %H:%i:%s')as CreatedDateTime FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + counterFromDate.trim() + "%'AND '" + counterToDate.trim() + "%')";
                    countCounterBillQuery = "select count(sales_orderno) from pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + counterFromDate.trim() + "%'AND '" + counterToDate.trim() + "%')";
                } else if (filterFlagValue.equalsIgnoreCase("N")) {
                    counterSummaryQuery = "SELECT header.sales_orderno,header.manual_bill_no,lineitem.item,lineitem.material,lineitem.descrip,lineitem.qty,lineitem.materialCraftGroup,lineitem.calcu_value,header.emp_id,header.show_room,lineitem.discount_percentage,lineitem.discount_value,lineitem.vat_percentage,lineitem.vat_value,lineitem.pck_charge, DATE_FORMAT(header.date_time,'%e-%c-%Y %H:%i:%s')as CreatedDateTime FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + counterFromDate.trim() + "%'AND '" + counterToDate.trim() + "%')AND(header.cancelFlag='N')";
                    countCounterBillQuery = "select count(sales_orderno) from pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + counterFromDate.trim() + "%'AND '" + counterToDate.trim() + "%')AND(header.cancelFlag='N')";
                } else if (filterFlagValue.equalsIgnoreCase("X")) {
                    counterSummaryQuery = "SELECT header.sales_orderno,header.manual_bill_no,lineitem.item,lineitem.material,lineitem.descrip,lineitem.qty,lineitem.materialCraftGroup,lineitem.calcu_value,header.emp_id,header.show_room,lineitem.discount_percentage,lineitem.discount_value,lineitem.vat_percentage,lineitem.vat_value,lineitem.pck_charge, DATE_FORMAT(header.date_time,'%e-%c-%Y %H:%i:%s')as CreatedDateTime FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + counterFromDate.trim() + "%'AND '" + counterToDate.trim() + "%')AND(header.cancelFlag='X')";
                    countCounterBillQuery = "select count(sales_orderno) from pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + counterFromDate.trim() + "%'AND '" + counterToDate.trim() + "%')AND(header.cancelFlag='X')";
                }
            }
            resultSet = daoClass.Fun_Resultset(counterSummaryQuery);
            counterBillSummaryData.clear();
            while (resultSet.next()) {
                CounterBillSummaryTo billSummaryTo = new CounterBillSummaryTo(resultSet.getString("sales_orderno").trim(), resultSet.getString("manual_bill_no").trim(), resultSet.getInt("item"), resultSet.getString("material").trim(), resultSet.getString("descrip").trim(), resultSet.getFloat("qty"), resultSet.getString("materialCraftGroup").trim(), resultSet.getFloat("calcu_value"), resultSet.getString("emp_id").trim(), resultSet.getString("show_room").trim(), resultSet.getFloat("discount_percentage"), resultSet.getFloat("discount_value"), resultSet.getFloat("vat_percentage"), resultSet.getFloat("vat_value"), resultSet.getFloat("pck_charge"), resultSet.getString("CreatedDateTime").trim());
                counterBillSummaryData.add(billSummaryTo);
            }
            totalCounterBill = daoClass.Fun_Int(countCounterBillQuery);
        } catch (Exception ex) {
            //System.out.println("Exception in fetching summary data : " + ex);
        } finally {
            daoClass.closeResultSet(resultSet);
        }
        return SUCCESS;
    }
    
    public String counterSummaryGrid() {
        DecimalFormat df = new DecimalFormat("#0.00");
        try {
            StringTokenizer dat_tok = new StringTokenizer(counterGridParameter, "#");
            while (dat_tok.hasMoreElements()) {
                counterFromDate = dat_tok.nextElement().toString();
                counterToDate = dat_tok.nextElement().toString();
                counterReportButton = dat_tok.nextElement().toString();
                filterFlagValue = dat_tok.nextElement().toString();
            }
            counterToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + counterToDate.trim() + "',1))");
            String counter_no_Statement = "SELECT counter_no FROM pos.branch_counter";
            resultSet = daoClass.Fun_Resultset(counter_no_Statement);
            while (resultSet.next()) {
                counterNumber.add(resultSet.getString("counter_no"));
            }
            for (String counterNo : counterNumber) {
                String craft_group_Statement = " SELECT craft_group FROM pos.craft_counter_list where  storage_location='" + counterNo.trim() + "'";
                resultSet = daoClass.Fun_Resultset(craft_group_Statement);
                while (resultSet.next()) {
                    craftGroupCode.add(resultSet.getString("craft_group"));
                }
            }
            
            if (counterReportButton.equals("") || counterReportButton.equalsIgnoreCase(null)) {
                //AND(header.cancelFlag<>'X')

            } else if (counterReportButton.equalsIgnoreCase("Abstract-View")) {
                if (filterFlagValue.equalsIgnoreCase("Select")) {
                    for (String craftGrp : craftGroupCode) {
                        String valid = "SELECT count(materialCraftGroup) FROM pos.lineitem where materialCraftGroup='" + craftGrp.trim() + "'";
                        int x = daoClass.Fun_Int(valid);
                        if (x > 0) {
                            String counterTranscationToExcel = "SELECT branch_counter.counter_no_legacy,branch_counter.counter,sum(lineitem.prc_value) as Gross_Amount,sum(lineitem.discount_value) as Disc_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value) as Net_Amount,sum(lineitem.vat_value)as Vat_Amount,sum(lineitem.pck_charge) as Pack_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as Total_Sales FROM    (   (   pos.craft_counter_list craft_counter_list INNER JOIN pos.branch_counter branch_counter ON (craft_counter_list.storage_location =branch_counter.counter_no)) INNER JOIN pos.lineitem lineitem ON (lineitem.materialCraftGroup = craft_counter_list.craft_group)) INNER JOIN pos.header header ON (header.sales_orderno = lineitem.sales_orderno) WHERE     (lineitem.date_time between   '" + counterFromDate.trim() + "%' and '" + counterToDate.trim() + "%') AND (lineitem.plant = '" + session.get("Plant_Id").toString() + "' )AND (lineitem.materialCraftGroup='" + craftGrp + "')";
                            resultSet = daoClass.Fun_Resultset(counterTranscationToExcel);
                            while (resultSet.next()) {
                                grossAmount += resultSet.getFloat("Gross_Amount");
                                discAmount += (resultSet.getFloat("Disc_Amount"));
                                netAmount += (resultSet.getFloat("Net_Amount"));
                                vataAmount += (resultSet.getFloat("Vat_Amount"));
                                pckingAmount += (resultSet.getFloat("Pack_Amount"));
                                totSalesAmount += (resultSet.getFloat("Total_Sales"));//
                                CounterBillSummaryViewTo viewTo = new CounterBillSummaryViewTo(resultSet.getString("counter_no_legacy"), resultSet.getString("counter"), df.format(resultSet.getDouble("Gross_Amount")), df.format(resultSet.getDouble("Disc_Amount")), df.format(resultSet.getDouble("Net_Amount")), df.format(resultSet.getDouble("Vat_Amount")), df.format(resultSet.getDouble("Pack_Amount")), df.format(resultSet.getDouble("Total_Sales")));
                                counterBillSummaryViewData.add(viewTo);
                            }
                        }
                    }
                } else /*if (filterFlagValue.equalsIgnoreCase("N"))*/ {
                    for (String craftGrp : craftGroupCode) {
                        String valid = "SELECT count(materialCraftGroup) FROM pos.lineitem where materialCraftGroup='" + craftGrp.trim() + "'";
                        int x = daoClass.Fun_Int(valid);
                        if (x > 0) {
                            String counterTranscationToExcel = "SELECT branch_counter.counter_no_legacy,branch_counter.counter,sum(lineitem.prc_value) as Gross_Amount,sum(lineitem.discount_value) as Disc_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value) as Net_Amount,sum(lineitem.vat_value)as Vat_Amount,sum(lineitem.pck_charge) as Pack_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as Total_Sales FROM    (   (   pos.craft_counter_list craft_counter_list INNER JOIN pos.branch_counter branch_counter ON (craft_counter_list.storage_location =branch_counter.counter_no)) INNER JOIN pos.lineitem lineitem ON (lineitem.materialCraftGroup = craft_counter_list.craft_group)) INNER JOIN pos.header header ON (header.sales_orderno = lineitem.sales_orderno) WHERE     (lineitem.date_time between   '" + counterFromDate.trim() + "%' and '" + counterToDate.trim() + "%') AND (lineitem.plant = '" + session.get("Plant_Id").toString() + "' )AND(header.cancelFlag='" + filterFlagValue.trim() + "')AND (lineitem.materialCraftGroup='" + craftGrp + "')";
                            //System.out.println("counterTranscationToExcel" + counterTranscationToExcel);
                            resultSet = daoClass.Fun_Resultset(counterTranscationToExcel);
                            while (resultSet.next()) {
                                grossAmount += resultSet.getFloat("Gross_Amount");
                                discAmount += (resultSet.getFloat("Disc_Amount"));
                                netAmount += (resultSet.getFloat("Net_Amount"));
                                vataAmount += (resultSet.getFloat("Vat_Amount"));
                                pckingAmount += (resultSet.getFloat("Pack_Amount"));
                                totSalesAmount += (resultSet.getFloat("Total_Sales"));
                                CounterBillSummaryViewTo viewTo = new CounterBillSummaryViewTo(resultSet.getString("counter_no_legacy"), resultSet.getString("counter"), df.format(resultSet.getDouble("Gross_Amount")), df.format(resultSet.getDouble("Disc_Amount")), df.format(resultSet.getDouble("Net_Amount")), df.format(resultSet.getDouble("Vat_Amount")), df.format(resultSet.getDouble("Pack_Amount")), df.format(resultSet.getDouble("Total_Sales")));
                                counterBillSummaryViewData.add(viewTo);
                                //System.out.println("counterBillSummaryViewData" + counterBillSummaryViewData);
                            }
                        }
                    }
                } /*else if (filterFlagValue.equalsIgnoreCase("X")) {
                 for (String craftGrp : craftGroupCode) {
                 String valid = "SELECT count(materialCraftGroup) FROM pos.lineitem where materialCraftGroup='" + craftGrp + "'";
                 int x = daoClass.Fun_Int(valid);
                 if (x > 0) {
                 String counterTranscationToExcel = "SELECT branch_counter.counter_no_legacy,branch_counter.counter,sum(lineitem.prc_value) as Gross_Amount,sum(lineitem.discount_value) as Disc_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value) as Net_Amount,sum(lineitem.vat_value)as Vat_Amount,sum(lineitem.pck_charge) as Pack_Amount,sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as Total_Sales FROM    (   (   pos.craft_counter_list craft_counter_list INNER JOIN pos.branch_counter branch_counter ON (craft_counter_list.storage_location =branch_counter.counter_no)) INNER JOIN pos.lineitem lineitem ON (lineitem.materialCraftGroup = craft_counter_list.craft_group)) INNER JOIN pos.header header ON (header.sales_orderno = lineitem.sales_orderno) WHERE     (lineitem.date_time between   '" + counterFromDate.trim() + "%' and '" + counterToDate.trim() + "%') AND (lineitem.plant = '" + session.get("Plant_Id").toString() + "' ) and (header.cancelFlag='X')AND (lineitem.materialCraftGroup='" + craftGrp + "')";
                 resultSet = daoClass.Fun_Resultset(counterTranscationToExcel);
                 while (resultSet.next()) {
                 grossAmount += resultSet.getFloat("Gross_Amount");
                 discAmount += (resultSet.getFloat("Disc_Amount"));
                 netAmount += (resultSet.getFloat("Net_Amount"));
                 vataAmount += (resultSet.getFloat("Vat_Amount"));
                 pckingAmount += (resultSet.getFloat("Pack_Amount"));
                 totSalesAmount += (resultSet.getFloat("Total_Sales"));
                 CounterBillSummaryViewTo viewTo = new CounterBillSummaryViewTo(resultSet.getString("counter_no_legacy"), resultSet.getString("counter"), df.format(resultSet.getDouble("Gross_Amount")), df.format(resultSet.getDouble("Disc_Amount")), df.format(resultSet.getDouble("Net_Amount")), df.format(resultSet.getDouble("Vat_Amount")), df.format(resultSet.getDouble("Pack_Amount")), df.format(resultSet.getDouble("Total_Sales")));
                 counterBillSummaryViewData.add(viewTo);
                 }
                 }
                 }
                 }*/
                
            }
        } catch (Exception ex) {
            System.out.println("Exception in fetching summary data : " + ex);
        } finally {
            daoClass.closeResultSet(resultSet);
        }
        CounterBillSummaryViewTo viewTo = new CounterBillSummaryViewTo(TOTAL, null, df.format(grossAmount), df.format(discAmount), df.format(netAmount), df.format(vataAmount), df.format(pckingAmount), df.format(totSalesAmount));
        counterBillSummaryViewData.add(viewTo);
        return SUCCESS;
    }
    
    public String showSummarySum() {
        try {
            totalCounterBill = 0;
            counterTotalSum = 0.0f;
            loginUserType = session.get("User_type").toString().trim();
            String yesterdayDate = daoClass.Fun_Str("SELECT Date(SUBDATE(NOW(),1))");
            if (!((filterToDate.equalsIgnoreCase("")) || (filterToDate.equalsIgnoreCase(null)))) {
                filterToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + filterToDate.trim() + "',1))");
            } else {
                filterToDate = yesterdayDate;
            }
            
            if (filterButtonType.equals("") || filterButtonType.equalsIgnoreCase(null)) {
                filterSummaryQuery = "SELECT sum(lineitem.calcu_value) FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + yesterdayDate.trim() + "%')AND(header.cancelFlag<>'X')";
                countCounterBillQuery = "select count(sales_orderno) from pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + yesterdayDate.trim() + "%')AND(header.cancelFlag<>'X')";
                //getPackingCharge = "select pck_charge from pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + yesterdayDate.trim() + "%')AND(header.cancelFlag<>'X')";
            } else if (filterButtonType.equalsIgnoreCase("Detailed-View")) {
                if (selectedSearchByFlag.equalsIgnoreCase("Select")) {
                    //filterSummaryQuery = "SELECT sum(lineitem.calcu_value) FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + filterFromDate.trim() + "%')";29-01-2015
                    /*
                     //Mastan 17-7-2015  to reduce two Query to single query.
                     filterSummaryQuery = "SELECT sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as Total_Sales FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + filterFromDate.trim() + "%' and '" + filterToDate.trim() + "%')";
                     countCounterBillQuery = "select count(sales_orderno) from pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + filterFromDate.trim() + "%')";
                     */
                    getAmtCountQry = "SELECT sum(bil_amt) as total,count(*) as count FROM pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + filterFromDate.trim() + "%' and '" + filterToDate.trim() + "%')";
                } else if (selectedSearchByFlag.equalsIgnoreCase("N")) {
                    //filterSummaryQuery = "SELECT sum(lineitem.calcu_value) FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + filterFromDate.trim() + "%')AND(header.cancelFlag='N')";29-01-2015
                    //getPackingCharge = "select pck_charge from pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + filterFromDate.trim() + "%')AND(header.cancelFlag='N')";
                    /*
                     filterSummaryQuery = "SELECT sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as Total_Sales FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + filterFromDate.trim() + "%' and '" + filterToDate.trim() + "%')AND(header.cancelFlag='N')";
                     countCounterBillQuery = "select count(sales_orderno) from pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + filterFromDate.trim() + "%' AND '" + filterToDate.trim() + "%')AND(header.cancelFlag='N')";
                     */
                    getAmtCountQry = "SELECT sum(bil_amt) as total,count(*) as count FROM pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + filterFromDate.trim() + "%' and '" + filterToDate.trim() + "%')AND(header.cancelFlag='N')";
                } else if (selectedSearchByFlag.equalsIgnoreCase("X")) {
                    //filterSummaryQuery = "SELECT sum(lineitem.calcu_value) FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + filterFromDate.trim() + "%')AND(header.cancelFlag='X')";29-01-2015
                    //getPackingCharge = "select pck_charge from pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time like '" + filterFromDate.trim() + "%')AND(header.cancelFlag='X')";
                    /*
                     filterSummaryQuery = "SELECT sum(lineitem.prc_value)-sum(lineitem.discount_value)+sum(lineitem.vat_value)+sum(lineitem.pck_charge) as Total_Sales FROM pos.header header INNER JOIN pos.lineitem lineitem ON (header.sales_orderno = lineitem.sales_orderno)WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + filterFromDate.trim() + "%' and '" + filterToDate.trim() + "%')AND(header.cancelFlag='X')";
                     countCounterBillQuery = "select count(sales_orderno) from pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + filterFromDate.trim() + "%'  AND '" + filterToDate.trim() + "%')AND(header.cancelFlag='X')";
                     */
                    getAmtCountQry = "SELECT sum(bil_amt) as total,count(*) as count FROM pos.header WHERE(header.show_room = '" + session.get("Plant_Id").toString() + "')AND(header.date_time between '" + filterFromDate.trim() + "%' and '" + filterToDate.trim() + "%')AND(header.cancelFlag='X')";
                }
            }
//            int packingCharge = daoClass.Fun_Int(getPackingCharge);
//            if (packingCharge > 0) {
//                counterTotalSum = daoClass.Fun_Float(filterSummaryQuery);
//                counterTotalSum += packingCharge;
//            } else if (packingCharge == 0) {
//                counterTotalSum = daoClass.Fun_Float(filterSummaryQuery);
//            }
            /*
             counterTotalSum = daoClass.Fun_Float(filterSummaryQuery);
             totalCounterBill = daoClass.Fun_Int(countCounterBillQuery);
             */
            getAmtCountRS = daoClass.Fun_Resultset(getAmtCountQry);
            if (getAmtCountRS.next()) {
                counterTotalSum = getAmtCountRS.getFloat("total");
                totalCounterBill = getAmtCountRS.getInt("count");
            }
        } catch (Exception ex) {
            //System.out.println("Exception in calculating the sum value :  " + ex);
        } finally {
            daoClass.closeResultSet(getAmtCountRS);
        }
        return SUCCESS;
    }
    
    public String getLoginUserType() {
        return loginUserType;
    }
    
    public void setLoginUserType(String loginUserType) {
        this.loginUserType = loginUserType;
    }
    
    public List getCounterBillSummaryViewData() {
        return counterBillSummaryViewData;
    }
    
    public void setCounterBillSummaryViewData(List counterBillSummaryViewData) {
        this.counterBillSummaryViewData = counterBillSummaryViewData;
    }
    
    public String getFilterToDate() {
        return filterToDate;
    }
    
    public void setFilterToDate(String filterToDate) {
        this.filterToDate = filterToDate;
    }
    
    public String getFilterFromDate() {
        return filterFromDate;
    }
    
    public void setFilterFromDate(String filterFromDate) {
        this.filterFromDate = filterFromDate;
    }
    
    public String getFilterButtonType() {
        return filterButtonType;
    }
    
    public void setFilterButtonType(String filterButtonType) {
        this.filterButtonType = filterButtonType;
    }
    
    public float getCounterTotalSum() {
        return counterTotalSum;
    }
    
    public void setCounterTotalSum(float counterTotalSum) {
        this.counterTotalSum = counterTotalSum;
    }
    
    public int getTotalCounterBill() {
        return totalCounterBill;
    }
    
    public void setTotalCounterBill(int totalCounterBill) {
        this.totalCounterBill = totalCounterBill;
    }
    
    public String getCounterGridParameter() {
        return counterGridParameter;
    }
    
    public void setCounterGridParameter(String counterGridParameter) {
        this.counterGridParameter = counterGridParameter;
    }
    
    public List getCounterBillSummaryData() {
        return counterBillSummaryData;
    }
    
    public void setCounterBillSummaryData(List counterBillSummaryData) {
        this.counterBillSummaryData = counterBillSummaryData;
    }
    
    public Map getSession() {
        return session;
    }
    
    public void setSession(Map session) {
        this.session = session;
    }
    
    public String getCounterButtonType() {
        return counterButtonType;
    }
    
    public void setCounterButtonType(String counterButtonType) {
        this.counterButtonType = counterButtonType;
    }
    
    public String getCounterToDate() {
        return counterToDate;
    }
    
    public void setCounterToDate(String counterToDate) {
        this.counterToDate = counterToDate;
    }
    
    public String getSelectedFlag() {
        return selectedFlag;
    }
    
    public void setSelectedFlag(String selectedFlag) {
        this.selectedFlag = selectedFlag;
    }
    
    public String getFilterFlagValue() {
        return filterFlagValue;
    }
    
    public void setFilterFlagValue(String filterFlagValue) {
        this.filterFlagValue = filterFlagValue;
    }
    
    public String getSelectedSearchByFlag() {
        return selectedSearchByFlag;
    }
    
    public void setSelectedSearchByFlag(String selectedSearchByFlag) {
        this.selectedSearchByFlag = selectedSearchByFlag;
    }
    
    public String getCounterFromDate() {
        return counterFromDate;
    }
    
    public void setCounterFromDate(String counterFromDate) {
        this.counterFromDate = counterFromDate;
    }
    
    public String getCounterExport() {
        return CounterExport;
    }
    
    public void setCounterExport(String CounterExport) {
        this.CounterExport = CounterExport;
    }
    
    public String getCounterReportButton() {
        return counterReportButton;
    }
    
    public void setCounterReportButton(String counterReportButton) {
        this.counterReportButton = counterReportButton;
    }
    
}
