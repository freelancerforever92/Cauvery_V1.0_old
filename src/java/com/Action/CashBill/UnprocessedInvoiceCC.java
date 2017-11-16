package com.Action.CashBill;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import com.to.UnprocessedInvoicesDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/*@author pranesh*/

public class UnprocessedInvoiceCC extends ActionSupport {

    static ResultSet processedInvoiceRs = null;

    public UnprocessedInvoiceCC() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /*
     public static List<UnprocessedInvoicesDTO> displayUncollectedInvoices(String paraFromDate, String paraToDate) {
     DaoClass daoClass = new DaoClass();
     List<UnprocessedInvoicesDTO> unProcessedInvoicesDTOs = new ArrayList();
     try {
     paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
     String unProcessedInvoiceQuery = "SELECT t1.sales_orderno,t1.bil_amt,DATE_FORMAT(t1.date_Time,'%d-%m-%Y')as DATE_Time,emp_master.emp_name FROM (pos.header t1 INNER JOIN  pos.emp_master emp_master ON (t1.emp_id = emp_master.emp_id)) LEFT OUTER JOIN pos.cashbill_lineitem_master t2 ON t2.counterbill_no = t1.sales_orderno WHERE t2.counterbill_no IS NULL AND t1.date_Time between '" + paraFromDate.trim() + "' and '" + paraToDate.trim() + "' and t1.cancelFlag='N' order by t1.date_Time asc";
     processedInvoiceRs = daoClass.Fun_Resultset(unProcessedInvoiceQuery);
     while (processedInvoiceRs.next()) {
     UnprocessedInvoicesDTO invoicesDTO = new UnprocessedInvoicesDTO();
     invoicesDTO.setInvoiceNumber(processedInvoiceRs.getString("sales_orderno").trim());
     invoicesDTO.setBillAmount(processedInvoiceRs.getFloat("bil_amt"));
     invoicesDTO.setDateTimeValue(processedInvoiceRs.getString("DATE_Time").trim());
     invoicesDTO.setCounterUserName(processedInvoiceRs.getString("emp_name").trim());
     unProcessedInvoicesDTOs.add(invoicesDTO);
     }
     } catch (Exception ex) {
     System.out.println("Exception in processing uncollectedbills : " + ex.getMessage());
     } finally {
     daoClass.closeResultSet(processedInvoiceRs);
     }
     return unProcessedInvoicesDTOs;
     }
     */
    /*
     public static List<UnprocessedInvoicesDTO> displayUncollectedInvoices(String paraFromDate, String paraToDate) {
     DaoClass daoClass = new DaoClass();
     List<UnprocessedInvoicesDTO> unProcessedInvoicesDTOs = new ArrayList();
     float totalValue = 0f;
     try {
     paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
     String unProcessedInvoiceQuery = "SELECT SQL_CACHE t1.sales_orderno,t1.manual_bill_no,t1.bil_amt,DATE_FORMAT(t1.date_Time,'%d-%m-%Y')as DATE_Time,emp_master.emp_name FROM (pos.header t1 INNER JOIN  pos.emp_master emp_master ON (t1.emp_id = emp_master.emp_id)) LEFT OUTER JOIN pos.cashbill_lineitem_master t2 ON t2.counterbill_no = t1.sales_orderno WHERE t2.counterbill_no IS NULL AND t1.date_Time between str_to_date('" + paraFromDate.trim() + "','%Y-%m-%d') and str_to_date('" + paraToDate.trim() + "','%Y-%m-%d') and t1.cancelFlag='N' order by t1.date_Time asc";
     System.out.println("UnProcessQuery :: " + unProcessedInvoiceQuery);
     processedInvoiceRs = daoClass.Fun_Resultset(unProcessedInvoiceQuery);
     while (processedInvoiceRs.next()) {
     UnprocessedInvoicesDTO invoicesDTO = new UnprocessedInvoicesDTO();
     invoicesDTO.setInvoiceNumber(processedInvoiceRs.getString("sales_orderno"));
     invoicesDTO.setManualBilNumber(processedInvoiceRs.getString("manual_bill_no"));
     invoicesDTO.setDateTimeValue(processedInvoiceRs.getString("DATE_Time"));
     invoicesDTO.setCounterUserName(processedInvoiceRs.getString("emp_name"));
     invoicesDTO.setBillAmount(processedInvoiceRs.getFloat("bil_amt"));
     totalValue = totalValue + processedInvoiceRs.getFloat("bil_amt");
     unProcessedInvoicesDTOs.add(invoicesDTO);
     }
     UnprocessedInvoicesDTO invoicesDTO = new UnprocessedInvoicesDTO();
     invoicesDTO.setInvoiceNumber(" ");
     invoicesDTO.setDateTimeValue("TOTAL");
     invoicesDTO.setCounterUserName(" ");
     invoicesDTO.setBillAmount(totalValue);
     unProcessedInvoicesDTOs.add(invoicesDTO);
     } catch (SQLException ex) {
     System.out.println("Exception in processing uncollectedbills : " + ex.getMessage());
     } finally {
     daoClass.closeResultSet(processedInvoiceRs);
     }
     return unProcessedInvoicesDTOs;
     }
     }
     */

    public static List<UnprocessedInvoicesDTO> displayUncollectedInvoices(String paraFromDate, String paraToDate) {

        DaoClass daoClass = new DaoClass();
        List<UnprocessedInvoicesDTO> unProcessedInvoicesDTOs = new ArrayList();
        Float grossAmt = 0.0f;
        Float disAmt = 0.0f;
        Float vatAmt = 0.0f;
        Float netAmt = 0.0f;
        Float packAmt = 0.0f;
        Float totalAmt = 0.0f;

        Float SubgrossAmt = 0.0f;
        Float SubdisAmt = 0.0f;
        Float SubvatAmt = 0.0f;
        Float SubnetAmt = 0.0f;
        Float SubpackAmt = 0.0f;
        Float SubtotalAmt = 0.0f;
        String SubName = "Empty";
        try {
            paraToDate = daoClass.Fun_Str("SELECT Date(ADDDATE('" + paraToDate.trim() + "',1))");
            //String unProcessedInvoiceQuery = "SELECT SQL_CACHE t1.sales_orderno,t1.manual_bill_no,t1.bil_amt,DATE_FORMAT(t1.date_Time,'%d-%m-%Y')as DATE_Time,emp_master.emp_name FROM (pos.header t1 INNER JOIN  pos.emp_master emp_master ON (t1.emp_id = emp_master.emp_id)) LEFT OUTER JOIN pos.cashbill_lineitem_master t2 ON t2.counterbill_no = t1.sales_orderno WHERE t2.counterbill_no IS NULL AND t1.date_Time between str_to_date('" + paraFromDate.trim() + "','%Y-%m-%d') and str_to_date('" + paraToDate.trim() + "','%Y-%m-%d') and t1.cancelFlag='N' order by t1.date_Time asc";
            String unProcessedInvoiceQuery = "SELECT SQL_CACHE DISTINCT \n"
                    + "t1.sales_orderno,\n"
                    + "t1.manual_bill_no,\n"
                    + "emp_master.emp_name,\n"
                    + "lineitem.qty,\n"
                    + "SUM(prc_value) as GrossAmt,\n"
                    + "SUM(lineitem.discount_value) as discountAmt, \n"
                    + "SUM(prc_value)- SUM(lineitem.discount_value) as NetAmt,\n"
                    //+ "SUM(lineitem.calcu_value) as NetAmt,\n"
                    + "SUM(lineitem.vat_value) as VatAmt,\n"
                    + "lineitem.pck_charge as pacCharges,\n"
                    + "(t1.bil_amt) as totalAmt,\n"
                    + "DATE_FORMAT(t1.date_Time,'%d-%m-%Y')as DATE_Time,\n"
                    + "craft_counter_list.description \n"
                    + "FROM (pos.header t1 INNER JOIN  pos.emp_master emp_master ON (t1.emp_id = emp_master.emp_id))\n"
                    + "\n"
                    + "INNER JOIN\n"
                    + "pos.lineitem lineitem\n"
                    + "ON (t1.sales_orderno = lineitem.sales_orderno)\n"
                    + "INNER JOIN\n"
                    + "pos.craft_counter_list craft_counter_list\n"
                    + "ON (lineitem.materialCraftGroup =\n"
                    + "craft_counter_list.craft_group)\n"
                    + "LEFT OUTER JOIN pos.cashbill_lineitem_master t2 ON t2.counterbill_no = t1.sales_orderno \n"
                    + "WHERE t2.counterbill_no IS NULL AND t1.date_Time between '" + paraFromDate.trim() + "' and '" + paraToDate.trim() + "' \n"
                    + "and t1.cancelFlag='N' group by t1.sales_orderno order by description";
            processedInvoiceRs = daoClass.Fun_Resultset(unProcessedInvoiceQuery);
            while (processedInvoiceRs.next()) {
                if (!SubName.equals("Empty")) {
                    if (!SubName.equals(processedInvoiceRs.getString("description"))) {
                        UnprocessedInvoicesDTO invoicesSub = new UnprocessedInvoicesDTO();
                        invoicesSub.setCounterUserName(" ");
                        invoicesSub.setInvoiceNumber(" ");
                        invoicesSub.setManualBilNumber(" ");
                        invoicesSub.setGrossAmt(SubgrossAmt);
                        invoicesSub.setDisAmt(SubdisAmt);
                        invoicesSub.setVatAmt(SubvatAmt);
                        invoicesSub.setNetAmt(SubnetAmt);
                        invoicesSub.setPackAmt(SubpackAmt);
                        invoicesSub.setTotalAmt(SubtotalAmt);
                        invoicesSub.setDateTimeValue("");
                        invoicesSub.setCraftGroup("Sub-Total (" + SubName + ")");
                        unProcessedInvoicesDTOs.add(invoicesSub);
                        SubgrossAmt = 0.0f;
                        SubdisAmt = 0.0f;
                        SubnetAmt = 0.0f;
                        SubpackAmt = 0.0f;
                        SubtotalAmt = 0.0f;
                    }
                }
                UnprocessedInvoicesDTO invoicesDTO = new UnprocessedInvoicesDTO();
                invoicesDTO.setCounterUserName(processedInvoiceRs.getString("emp_name"));
                invoicesDTO.setInvoiceNumber(processedInvoiceRs.getString("sales_orderno"));
                invoicesDTO.setManualBilNumber(processedInvoiceRs.getString("manual_bill_no"));
                invoicesDTO.setGrossAmt(processedInvoiceRs.getFloat("GrossAmt"));
                invoicesDTO.setDisAmt(processedInvoiceRs.getFloat("discountAmt"));
                invoicesDTO.setVatAmt(processedInvoiceRs.getFloat("VatAmt"));
                invoicesDTO.setNetAmt(processedInvoiceRs.getFloat("NetAmt"));
                invoicesDTO.setPackAmt(processedInvoiceRs.getFloat("pacCharges"));
                invoicesDTO.setTotalAmt(processedInvoiceRs.getFloat("totalAmt"));
                invoicesDTO.setDateTimeValue(processedInvoiceRs.getString("DATE_Time"));
                invoicesDTO.setCraftGroup(processedInvoiceRs.getString("description"));
                unProcessedInvoicesDTOs.add(invoicesDTO);
                grossAmt += processedInvoiceRs.getFloat("GrossAmt");
                disAmt  += processedInvoiceRs.getFloat("discountAmt");
                vatAmt+=processedInvoiceRs.getFloat("VatAmt");
                netAmt  += processedInvoiceRs.getFloat("NetAmt");
                packAmt  += processedInvoiceRs.getFloat("pacCharges");
                totalAmt  += processedInvoiceRs.getFloat("totalAmt");

                SubgrossAmt  += processedInvoiceRs.getFloat("GrossAmt");
                SubdisAmt  += processedInvoiceRs.getFloat("discountAmt");
                SubvatAmt+=processedInvoiceRs.getFloat("VatAmt");
                SubnetAmt  += processedInvoiceRs.getFloat("NetAmt");
                SubpackAmt  += processedInvoiceRs.getFloat("pacCharges");
                SubtotalAmt  += processedInvoiceRs.getFloat("totalAmt");
                SubName = processedInvoiceRs.getString("description");
            }
            UnprocessedInvoicesDTO invoicesSub = new UnprocessedInvoicesDTO();
            invoicesSub.setCounterUserName(" ");
            invoicesSub.setInvoiceNumber(" ");
            invoicesSub.setManualBilNumber(" ");
            invoicesSub.setGrossAmt(SubgrossAmt);
            invoicesSub.setDisAmt(SubdisAmt);
            invoicesSub.setVatAmt(SubvatAmt);
            invoicesSub.setNetAmt(SubnetAmt);
            invoicesSub.setPackAmt(SubpackAmt);
            invoicesSub.setTotalAmt(SubtotalAmt);
            invoicesSub.setDateTimeValue("");
            invoicesSub.setCraftGroup("Sub-Total (" + SubName + ")");
            unProcessedInvoicesDTOs.add(invoicesSub);
            
            UnprocessedInvoicesDTO invoicesDTO = new UnprocessedInvoicesDTO();
            invoicesDTO.setCounterUserName(" ");
            invoicesDTO.setInvoiceNumber(" ");
            invoicesDTO.setManualBilNumber(" ");
            invoicesDTO.setGrossAmt(grossAmt);
            invoicesDTO.setDisAmt(disAmt);
            invoicesDTO.setVatAmt(vatAmt);
            invoicesDTO.setNetAmt(netAmt);
            invoicesDTO.setPackAmt(packAmt);
            invoicesDTO.setTotalAmt(totalAmt);
            invoicesDTO.setDateTimeValue("");
            invoicesDTO.setCraftGroup("GRAND - TOTAL");
            unProcessedInvoicesDTOs.add(invoicesDTO);
        } catch (SQLException ex) {
            System.out.println("Exception in processing uncollectedbills : " + ex.getMessage());
        } finally {
            daoClass.closeResultSet(processedInvoiceRs);
        }
        return unProcessedInvoicesDTOs;

    }
}
