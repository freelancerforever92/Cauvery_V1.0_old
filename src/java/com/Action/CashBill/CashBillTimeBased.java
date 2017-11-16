package com.Action.CashBill;

import static com.Action.CashBill.DcsrReport.daoClass;
import static com.Action.CashBill.DiscountPersentage.salesIntimationList;
import static com.Action.CashBill.SalesIntimationAbstract.SalesIntimationRs;
import com.to.CashBillTimeBasedList;
import com.to.SalesIntimationList;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CashBillTimeBased {

    public static List<CashBillTimeBasedList> CashBillTimeBasedReport(String paraFromDate, String paraToDate, String paraPaymentType, String paraLoggedSessionId) {
        List<CashBillTimeBasedList> CashBillTimeBasedList = new ArrayList<>();
        ResultSet CashBillTimeBasedRs = null;
        float disValueTotal = 0.0f;
        float grossValueTotal = 0.0f;
        float qtyTotal = 0.0f;
        float vatValueTotal = 0.0f;
        float calcValueTotal = 0.0f;
        float packChargeTotal = 0.0f;
        try {
            String paraFromDateSplit[] = paraFromDate.split("\\s");
            String paraFromDayDate = paraFromDateSplit[0];
            String paraFromHrMinDate = paraFromDateSplit[1];

            String paraToDateSplit[] = paraToDate.split("\\s");
            String paraToDayDate = paraToDateSplit[0];
            paraToDayDate = daoClass.Fun_Str("SELECT SQL_CACHE Date(ADDDATE('" + paraToDayDate.trim() + "',1))");
            String paraToHrMinDate = paraToDateSplit[1];
            String getCashBillTimeBasedQuery = "SELECT SQL_CACHE\n"
                    + "sales_orderno as INVOICE_NUMBER,\n"
                    + "item AS NO_ITEMS,\n"
                    + "material AS MATERIAL_NUMBER,\n"
                    + "materialCraftGroup AS CRAFT_GROUP,\n"
                    + "descrip AS MATERIAL_DESC,\n"
                    + "vendor AS VENDOR_NUMBER,\n"
                    + "qty AS QTY,\n"
                    + "price AS UNIT_PRC,\n"
                    + "prc_value AS GROSS_VALUE,\n"
                    + "discount_percentage AS DISC_PER,\n"
                    + "discount_value AS DISC_VALUE,\n"
                    + "vat_percentage AS VAT_PER,\n"
                    + "vat_value AS VAT_VALUE,\n"
                    + "calcu_value AS CALC_VALUE,\n"
                    + "pck_charge AS PCK_CHARGE,\n"
                    + " DATE_FORMAT(date_time,'%d-%m-%Y %h:%i:%s') AS DATE_TIME\n"
                    + "\n"
                    + "FROM pos.lineitem where sales_orderno in\n"
                    + "(SELECT sales_orderno FROM pos.header where date_time "
                    + "between '" + paraFromDayDate + "' and '" + paraToDayDate + "'and cancelFlag='N'  "
                    + "and TIME(date_time) BETWEEN '" + paraFromHrMinDate + "' AND '" + paraToHrMinDate + "') order by date_time;";
            CashBillTimeBasedRs = daoClass.Fun_Resultset(getCashBillTimeBasedQuery);
            while (CashBillTimeBasedRs.next()) {
                CashBillTimeBasedList cbtbl = new CashBillTimeBasedList();
                cbtbl.setCalcValue(CashBillTimeBasedRs.getFloat("CALC_VALUE"));
                cbtbl.setCraftGroup(CashBillTimeBasedRs.getString("CRAFT_GROUP"));
                cbtbl.setDateTime(CashBillTimeBasedRs.getString("DATE_TIME"));
                cbtbl.setDisValue(CashBillTimeBasedRs.getFloat("DISC_VALUE"));
                cbtbl.setDispPer(CashBillTimeBasedRs.getString("DISC_PER") + "%");
                cbtbl.setGrossValue(CashBillTimeBasedRs.getFloat("GROSS_VALUE"));
                cbtbl.setInvoiceNumber(CashBillTimeBasedRs.getString("INVOICE_NUMBER"));
                cbtbl.setItem(CashBillTimeBasedRs.getString("NO_ITEMS"));
                cbtbl.setMaterialDesc(CashBillTimeBasedRs.getString("MATERIAL_DESC"));
                cbtbl.setMaterialNumber(CashBillTimeBasedRs.getString("MATERIAL_NUMBER"));
                cbtbl.setPackCharge(CashBillTimeBasedRs.getFloat("PCK_CHARGE"));
                cbtbl.setQty(CashBillTimeBasedRs.getFloat("QTY"));
                cbtbl.setUnitPrice(CashBillTimeBasedRs.getString("UNIT_PRC"));
                cbtbl.setVatPer(CashBillTimeBasedRs.getString("VAT_PER") + "%");
                cbtbl.setVatValue(CashBillTimeBasedRs.getFloat("VAT_VALUE"));
                cbtbl.setVendorNumber(CashBillTimeBasedRs.getString("VENDOR_NUMBER"));

                //Total Calculation
                disValueTotal = disValueTotal + CashBillTimeBasedRs.getFloat("DISC_VALUE");
                grossValueTotal = grossValueTotal + CashBillTimeBasedRs.getFloat("GROSS_VALUE");
                qtyTotal = qtyTotal + CashBillTimeBasedRs.getFloat("QTY");
                vatValueTotal = vatValueTotal + CashBillTimeBasedRs.getFloat("VAT_VALUE");
                calcValueTotal = calcValueTotal + CashBillTimeBasedRs.getFloat("CALC_VALUE");
                packChargeTotal = packChargeTotal + CashBillTimeBasedRs.getFloat("PCK_CHARGE");
                CashBillTimeBasedList.add(cbtbl);

            }
            CashBillTimeBasedList cbtbl = new CashBillTimeBasedList();
            cbtbl.setCalcValue(calcValueTotal);
            cbtbl.setCraftGroup("");
            cbtbl.setDateTime("");
            cbtbl.setDisValue(disValueTotal);
            cbtbl.setDispPer("");
            cbtbl.setGrossValue(grossValueTotal);
            cbtbl.setInvoiceNumber("");
            cbtbl.setItem("");
            cbtbl.setMaterialDesc("");
            cbtbl.setMaterialNumber("");
            cbtbl.setPackCharge(packChargeTotal);
            cbtbl.setQty(qtyTotal);
            cbtbl.setUnitPrice("Total");
            cbtbl.setVatPer("");
            cbtbl.setVatValue(vatValueTotal);
            cbtbl.setVendorNumber("");
            CashBillTimeBasedList.add(cbtbl);
        } catch (Exception ex) {
            System.out.println("" + ex.getMessage());
        }

        return CashBillTimeBasedList;
    }
}
