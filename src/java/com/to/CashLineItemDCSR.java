package com.to;

/*
 @author pranesh
 */
import java.math.BigDecimal;
import java.util.Comparator;

public class CashLineItemDCSR {

    private String cashInvoiceNumber;
    private BigDecimal grossAmount;
    private BigDecimal discAmount;
    private BigDecimal netAmount;
    private BigDecimal vatAmount;
    private BigDecimal gstPer;
    private BigDecimal sgstAmount;
    private BigDecimal cgstAmount;
    private BigDecimal packAmount;
    private String manualBillNo;
    //private BigDecimal cashBillAmount;
    private int cashBillAmount;
    private String empName;
    private String abstractDcsrDate;
    private float cashBillAbsNetValue;
    private int count;
    private long countD;

    private String craftGroup;
    private float cashInvoiceNumberFloat;
    private float grossAmountFloat;
    private float discAmountFloat;
    private float netAmountFloat;
    private float vatAmountFloat;
    private float gstPercentage;
    private float sgstAmountFloat;
    private float cgstAmountFloat;
    private float packAmountFloat;
    private float cashBillAmountFloat;
    private String dcsrDate;
    private int fromDD;
    private int fromMM;
    private int fromYY;

    private float CashBillAmountWROFloat;

    public CashLineItemDCSR() {
    }

    public float getCashBillAmountWROFloat() {
        return CashBillAmountWROFloat;
    }

    public void setCashBillAmountWROFloat(float CashBillAmountWROFloat) {
        this.CashBillAmountWROFloat = CashBillAmountWROFloat;
    }

    @Override
    public String toString() {
        return "CashLineItemDCSR{" + "cashInvoiceNumber=" + cashInvoiceNumber + ", grossAmount=" + grossAmount + ", discAmount=" + discAmount + ", netAmount=" + netAmount + ", vatAmount=" + vatAmount + ", packAmount=" + packAmount + ", cashBillAmount=" + cashBillAmount + ", empName=" + empName + ", abstractDcsrDate=" + abstractDcsrDate + ", cashBillAbsNetValue=" + cashBillAbsNetValue + ", count=" + count + ", countD=" + countD + ", cashInvoiceNumberFloat=" + cashInvoiceNumberFloat + ", grossAmountFloat=" + grossAmountFloat + ", discAmountFloat=" + discAmountFloat + ", netAmountFloat=" + netAmountFloat + ", vatAmountFloat=" + vatAmountFloat + ", packAmountFloat=" + packAmountFloat + ", cashBillAmountFloat=" + cashBillAmountFloat + ",fromDD=" + fromDD + ",fromMM=" + fromMM + ",fromYY=" + fromYY + ", dcsrDate=" + dcsrDate + '}';
    }

    public CashLineItemDCSR(String cashInvoiceNumber, BigDecimal grossAmount, BigDecimal discAmount, BigDecimal netAmount, BigDecimal vatAmount, BigDecimal gstPercentage, BigDecimal sgstValue, BigDecimal cgstValue, BigDecimal packAmount, int cashBillAmount, String empName) {
        this.cashInvoiceNumber = cashInvoiceNumber;
        this.grossAmount = grossAmount;
        this.discAmount = discAmount;
        this.netAmount = netAmount;
        this.vatAmount = vatAmount;
        this.gstPer = gstPercentage;
        this.sgstAmount = sgstValue;
        this.cgstAmount = cgstValue;
        this.packAmount = packAmount;
        this.cashBillAmount = cashBillAmount;
        this.empName = empName;
    }

    public float getCashBillAmountFloat() {
        return cashBillAmountFloat;
    }

    public void setCashBillAmountFloat(float cashBillAmountFloat) {
        this.cashBillAmountFloat = cashBillAmountFloat;
    }

    public String getCashInvoiceNumber() {
        return cashInvoiceNumber;
    }

    public void setCashInvoiceNumber(String cashInvoiceNumber) {
        this.cashInvoiceNumber = cashInvoiceNumber;
    }

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    public BigDecimal getDiscAmount() {
        return discAmount;
    }

    public void setDiscAmount(BigDecimal discAmount) {
        this.discAmount = discAmount;
    }

    public BigDecimal getNetAmount() {
        return netAmount;
    }

    public String getCraftGroup() {
        return craftGroup;
    }

    public void setCraftGroup(String craftGroup) {
        this.craftGroup = craftGroup;
    }

    public void setNetAmount(BigDecimal netAmount) {
        this.netAmount = netAmount;
    }

    public BigDecimal getVatAmount() {
        return vatAmount;
    }

    public void setVatAmount(BigDecimal vatAmount) {
        this.vatAmount = vatAmount;
    }

    public BigDecimal getGstPer() {
        return gstPer;
    }

    public void setGstPer(BigDecimal gstPer) {
        this.gstPer = gstPer;
    }

    public BigDecimal getSgstAmount() {
        return sgstAmount;
    }

    public void setSgstAmount(BigDecimal sgstAmount) {
        this.sgstAmount = sgstAmount;
    }

    public BigDecimal getCgstAmount() {
        return cgstAmount;
    }

    public void setCgstAmount(BigDecimal cgstAmount) {
        this.cgstAmount = cgstAmount;
    }

    public BigDecimal getPackAmount() {
        return packAmount;
    }

    public void setPackAmount(BigDecimal packAmount) {
        this.packAmount = packAmount;
    }

    public int getCashBillAmount() {
        return cashBillAmount;
    }

    public void setCashBillAmount(int cashBillAmount) {
        this.cashBillAmount = cashBillAmount;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getAbstractDcsrDate() {
        return abstractDcsrDate;
    }

    public void setAbstractDcsrDate(String abstractDcsrDate) {
        this.abstractDcsrDate = abstractDcsrDate;
    }

    public float getCashBillAbsNetValue() {
        return cashBillAbsNetValue;
    }

    public void setCashBillAbsNetValue(float cashBillAbsNetValue) {
        this.cashBillAbsNetValue = cashBillAbsNetValue;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getCountD() {
        return countD;
    }

    public void setCountD(long countD) {
        this.countD = countD;
    }

    public float getCashInvoiceNumberFloat() {
        return cashInvoiceNumberFloat;
    }

    public void setCashInvoiceNumberFloat(float cashInvoiceNumberFloat) {
        this.cashInvoiceNumberFloat = cashInvoiceNumberFloat;
    }

    public float getGrossAmountFloat() {
        return grossAmountFloat;
    }

    public void setGrossAmountFloat(float grossAmountFloat) {
        this.grossAmountFloat = grossAmountFloat;
    }

    public float getDiscAmountFloat() {
        return discAmountFloat;
    }

    public void setDiscAmountFloat(float discAmountFloat) {
        this.discAmountFloat = discAmountFloat;
    }

    public float getNetAmountFloat() {
        return netAmountFloat;
    }

    public void setNetAmountFloat(float netAmountFloat) {
        this.netAmountFloat = netAmountFloat;
    }

    public float getVatAmountFloat() {
        return vatAmountFloat;
    }

    public void setVatAmountFloat(float vatAmountFloat) {
        this.vatAmountFloat = vatAmountFloat;
    }

    public float getGstPercentage() {
        return gstPercentage;
    }

    public void setGstPercentage(float gstPercentage) {
        this.gstPercentage = gstPercentage;
    }

    public float getSgstAmountFloat() {
        return sgstAmountFloat;
    }

    public void setSgstAmountFloat(float sgstAmountFloat) {
        this.sgstAmountFloat = sgstAmountFloat;
    }

    public float getCgstAmountFloat() {
        return cgstAmountFloat;
    }

    public void setCgstAmountFloat(float cgstAmountFloat) {
        this.cgstAmountFloat = cgstAmountFloat;
    }

    public float getPackAmountFloat() {
        return packAmountFloat;
    }

    public void setPackAmountFloat(float packAmountFloat) {
        this.packAmountFloat = packAmountFloat;
    }

    public String getDcsrDate() {
        return dcsrDate;
    }

    public void setDcsrDate(String dcsrDate) {
        this.dcsrDate = dcsrDate;
    }

    public String getManualBillNo() {
        return manualBillNo;
    }

    public void setManualBillNo(String manualBillNo) {
        this.manualBillNo = manualBillNo;
    }

    public int getFromDD() {
        return fromDD;
    }

    public void setFromDD(int fromDD) {
        this.fromDD = fromDD;
    }

    public int getFromMM() {
        return fromMM;
    }

    public void setFromMM(int fromMM) {
        this.fromMM = fromMM;
    }

    public int getFromYY() {
        return fromYY;
    }

    public void setFromYY(int fromYY) {
        this.fromYY = fromYY;
    }

    public static Comparator<CashLineItemDCSR> NameComparator = new Comparator<CashLineItemDCSR>() {

        public int compare(CashLineItemDCSR s1, CashLineItemDCSR s2) {
            String StudentName1 = s1.getDcsrDate();
            String StudentName2 = s2.getDcsrDate();
            //ascending order
            return StudentName1.compareTo(StudentName2);
            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };

    public static Comparator<CashLineItemDCSR> NameComparator2 = new Comparator<CashLineItemDCSR>() {

        public int compare(CashLineItemDCSR s1, CashLineItemDCSR s2) {
            String StudentName1 = s1.getCashInvoiceNumber();
            String StudentName2 = s2.getCashInvoiceNumber();
            //ascending order
            return StudentName1.compareTo(StudentName2);
            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };

    public static Comparator<CashLineItemDCSR> NameComparator3 = new Comparator<CashLineItemDCSR>() {

        public int compare(CashLineItemDCSR s1, CashLineItemDCSR s2) {
            String StudentName1 = s1.getCraftGroup();
            String StudentName2 = s2.getCraftGroup();
            //ascending order
            return StudentName1.compareTo(StudentName2);
            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };
    public static Comparator<CashLineItemDCSR> NameComparator4 = new Comparator<CashLineItemDCSR>() {

        public int compare(CashLineItemDCSR s1, CashLineItemDCSR s2) {
            String StudentName1 = s1.getEmpName();
            String StudentName2 = s2.getEmpName();
            //ascending order
            return StudentName1.compareTo(StudentName2);
            //descending order
            //return StudentName2.compareTo(StudentName1);
        }
    };
}
