package com.Action;

import java.sql.*;
import com.DAO.*;
import java.util.*;
import com.to.PaymentTypeTo;
import com.to.CounterNumbers;
import com.opensymphony.xwork2.ActionSupport;
import static com.opensymphony.xwork2.Action.SUCCESS;
import com.to.CashierDetails;
import com.to.CraftGroupsTo;
import com.to.PasswordQuestions;
import com.to.ReportTypesTo;
//import org.apache.log4j.Logger;
import com.to.SalesIntimationTypes;
import com.to.VendorNos;
import org.apache.struts2.interceptor.SessionAware;

/*@author pranesh*/
public class Common_Action extends ActionSupport implements SessionAware {
    /*
     NOTE : 
     1) 14.08.15 : 
     MATERIAL-VENDOR VALIDATION IS TEMP BLOCKED FOR
     14.08.15 DEPLOYMENT-PRANESH
     */

    Map session;
    int bolAuthe = 0;
    String selectedVendorType;
    ResultSet rs = null;
    List<PaymentTypeTo> ptts;
    List<PasswordQuestions> pqs;
    List<CashierDetails> cashierNames;
    List<ReportTypesTo> reportTypesTo;
    List<CraftGroupsTo> craftGroupsTo;
    List<VendorNos> vendorNumbers;
    List<SalesIntimationTypes> intimationTypesTo;
    private static List<String> vendorCraftGroupLst = new ArrayList();
    DaoClass cado = new DaoClass();
    List cityList = new ArrayList();
    List stateList = new ArrayList();
    List branchCodes = new ArrayList();
    ResultSet vendorsRs = null;
    private String dcsrFromDate;
    private String dcsrToDate;
    private int isBranchExits;
    private String branchCode;
    private String receivedBranchId;
    boolean isVendorValid = false;
    boolean isMaterialEmpty = false;
    boolean isSameCraftGroup = false;
    boolean isMaterialIdVaild = false;
    private boolean isVendorCgBoolean = false;
    private List<CraftGroupsTo> craftGrpName;
    private List<CounterNumbers> counterNumberses;
    private static List<String> crftGrpList = new ArrayList();
    //static final Logger log = Logger.getLogger(Common_Action.class);
    String Txtmaterial, Txtvendor, StateVal, disUname, disPwd, autheUtype = "", disCuntAccess = "";

    public Common_Action() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String Fun_DiscuntAuthe() {
        try {
            disCuntAccess = "";
            String autheQry = "", bolAutheQry = "";
            bolAutheQry = "select count(*) from user_master where username='" + disUname.trim() + "' and pwd='" + disPwd.trim() + "'";
            bolAuthe = cado.Fun_Int(bolAutheQry);
            if (bolAuthe >= 1) {
                autheQry = "SELECT emp_master.emp_type, user_master.username, user_master.pwd FROM pos.emp_master emp_master INNER JOIN pos.user_master user_master ON (emp_master.emp_pk = user_master.emp_fk)WHERE(user_master.username = '" + disUname.trim() + "')AND(user_master.pwd ='" + disPwd.trim() + "' )";
                autheUtype = cado.Fun_Str(autheQry);
                //System.out.println("USER TYPE :   ======== >   " + autheUtype);
                if (autheUtype.equalsIgnoreCase("admin")) {
                    disCuntAccess = "proceed";
                } else {
                    disCuntAccess = "denied";
                }
            } else if (bolAuthe <= 0) {
            }
        } catch (Exception ex) {
            System.out.println("Exception In DiscountAuthecation :   " + ex);

        }
        return SUCCESS;
    }

    public String Fun_FillPaymentType() {
        String payment_qry = "";
        try {
            ptts = new ArrayList();
            payment_qry = "select * from payment_type";
            rs = cado.Fun_Resultset(payment_qry);
            while (rs.next()) {
                ptts.add(new PaymentTypeTo(rs.getInt("paytype_pk"), rs.getString("paytype_type"), rs.getString("payment_value")));
            }
        } catch (Exception ex) {
            System.out.println("Exception In Filling Payment Type :  " + ex);
        } finally {
            cado.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public String loadCraftGroup() {
        ResultSet craftGroupTypesRs = null;
        try {
            craftGroupsTo = new ArrayList<>();
            String getCraftGroups = "SELECT craft_group,description FROM pos.craft_counter_list";
            craftGroupTypesRs = cado.Fun_Resultset(getCraftGroups);
            while (craftGroupTypesRs.next()) {
                if (session.get("User_type").toString().equalsIgnoreCase("all")) {
                    craftGroupsTo.add(new CraftGroupsTo(craftGroupTypesRs.getString("craft_group").trim(), craftGroupTypesRs.getString("description").trim()));
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception in loading CraftGroup : " + ex.getMessage());
        } finally {
            cado.closeResultSet(craftGroupTypesRs);
        }
        return SUCCESS;
    }

    public String loadVendorNumbers() {
        String getVendorNumbers = null;
        try {
            vendorNumbers = new ArrayList();
            if (!(selectedVendorType.equalsIgnoreCase("all"))) {
                dcsrToDate = cado.Fun_Str("SELECT SQL_CACHE Date(ADDDATE('" + dcsrToDate.trim() + "',1))");
                getVendorNumbers = "SELECT SQL_CACHE distinct(lineItemData.vendor),vendorMaster.vendor_name FROM (pos.lineitem lineItemData INNER JOIN pos.vendor_master vendorMaster ON (lineItemData.vendor = vendorMaster.vendor_id))INNER JOIN pos.header headerItemData ON (headerItemData.sales_orderno = lineItemData.sales_orderno)WHERE(lineItemData.vendor LIKE'" + selectedVendorType.trim() + "%')AND(headerItemData.cancelFlag = 'N')AND(lineItemData.date_time BETWEEN '" + dcsrFromDate + "' AND '" + dcsrToDate + "')order by lineItemData.vendor asc";
            } else if (selectedVendorType.equalsIgnoreCase("all")) {
                getVendorNumbers = "SELECT SQL_CACHE distinct(lineItemData.vendor),vendorMaster.vendor_name FROM (pos.lineitem lineItemData INNER JOIN pos.vendor_master vendorMaster ON (lineItemData.vendor = vendorMaster.vendor_id))INNER JOIN pos.header headerItemData ON (headerItemData.sales_orderno = lineItemData.sales_orderno)WHERE(headerItemData.cancelFlag = 'N')order by lineItemData.vendor asc";
            }
            vendorsRs = cado.Fun_Resultset(getVendorNumbers);
            while (vendorsRs.next()) {
                vendorNumbers.add(new VendorNos(vendorsRs.getString("vendor").trim(), vendorsRs.getString("vendor").trim() + " - " + vendorsRs.getString("vendor_name").trim()));
            }
        } catch (SQLException ex) {
            System.out.println("Exception in loading VendorId : " + ex.getMessage());
        } finally {
            cado.closeResultSet(vendorsRs);
        }
        return SUCCESS;
    }

    public String loadReportType() {
        ResultSet reportTypeRs = null;
        try {
            reportTypesTo = new ArrayList();
            String getReportTypes = "SELECT rptPk,rptName,rptAliasName FROM pos.reportype_master where visibility = 1";
            reportTypeRs = cado.Fun_Resultset(getReportTypes);
            while (reportTypeRs.next()) {
                if (session.get("User_type").toString().equalsIgnoreCase("all")) {
                    reportTypesTo.add(new ReportTypesTo(reportTypeRs.getInt("rptPk"), reportTypeRs.getString("rptName").trim(), reportTypeRs.getString("rptAliasName").trim()));
                } else {
                    if (reportTypeRs.getInt("rptPk") == 3 || reportTypeRs.getInt("rptPk") == 4) {
                        reportTypesTo.add(new ReportTypesTo(reportTypeRs.getInt("rptPk"), reportTypeRs.getString("rptName").trim(), reportTypeRs.getString("rptAliasName").trim()));
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception in loading paymentTypes : " + ex.getMessage());
        } finally {
            cado.closeResultSet(reportTypeRs);
        }
        return SUCCESS;
    }

    public String getSalesIntimationTypes() {
        ResultSet intimationTypesRs = null;
        try {
            intimationTypesTo = new ArrayList();
            String intimationTypesQry = "select reportPk,reportName,reportAliasName FROM pos.salesintimation_reportname_master";
            intimationTypesRs = cado.Fun_Resultset(intimationTypesQry);
            intimationTypesTo.clear();
            while (intimationTypesRs.next()) {
                intimationTypesTo.add(new SalesIntimationTypes(intimationTypesRs.getInt("reportPk"), intimationTypesRs.getString("reportName"), intimationTypesRs.getString("reportAliasName")));
            }
        } catch (Exception ex) {
            System.out.println("Exception in loading SalesIntimationTypes : " + ex.getMessage());
        }
        return SUCCESS;
    }

    public String loadingCashierName() {
        try {
            cashierNames = new ArrayList();
            String getCashierNames = "select emp_pk,emp_id,emp_name from pos.emp_master where emp_type IN ('cashCounter', 'all') and emp_status='1'";
            rs = cado.Fun_Resultset(getCashierNames);
            while (rs.next()) {
                if (!rs.getString("emp_id").equals(cado.getTestUserId())) {
                    cashierNames.add(new CashierDetails(rs.getInt("emp_pk"), rs.getString("emp_id").trim(), rs.getString("emp_name").trim()));
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception in loading CashierNames : " + ex.getMessage());
        } finally {
            cado.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public String loadPasswordSecurityQuestions() {
        try {
            pqs = new ArrayList();
            String fetchPasswordQuestions_query = "select questionPk,securityQuestion from password_security_questions";
            rs = cado.Fun_Resultset(fetchPasswordQuestions_query);
            while (rs.next()) {
                pqs.add(new PasswordQuestions(rs.getInt("questionPk"), rs.getString("securityQuestion").trim()));
            }
        } catch (Exception ex) {
            System.out.println("Exception in PasswordQuestion :  " + ex);
        } finally {
            cado.closeResultSet(rs);
        }
        return SUCCESS;
    }

//    public String checkMaterial() {
//        String materiaCheckQry = "";
//        int materialCountValue = 0;
//        try {
//            if (!((Txtmaterial.equalsIgnoreCase(null)) || (Txtmaterial.equals("")))) {
//                materiaCheckQry = "select count(*) from material_master_taxgroup where material_no='" + Txtmaterial.trim() + "'";
//                materialCountValue = cado.Fun_Int(materiaCheckQry);
//                if (materialCountValue > 0) {
//                    //String loginCraftGroupQuery = "select craft_group from material_master_taxgroup where material_no='" + Txtmaterial.trim() + "'";
//                    String loginStorageLocationQuery = "SELECT Ccounter.storage_location,MmTaxGroup.material_no FROM pos.material_master_taxgroup MmTaxGroup INNER JOIN pos.craft_counter_list Ccounter ON(MmTaxGroup.craft_group =Ccounter.craft_group)WHERE (MmTaxGroup.material_no ='" + Txtmaterial.trim() + "')";
//                    System.out.println("loginStorageLocationQuery :  " + loginStorageLocationQuery);
//                    isMaterialIdVaild = true;
//                    System.out.println("C1 : " + cado.Fun_Str(loginStorageLocationQuery));
//                    System.out.println("C2 : " + session.get("loginStorageLocation").toString());
//
//                    if (cado.Fun_Str(loginStorageLocationQuery).equalsIgnoreCase(session.get("loginStorageLocation").toString())) {
//                        isSameCraftGroup = true;
//                    } else {
//                        isSameCraftGroup = false;
//                    }
//                } else if (materialCountValue <= 0) {
//                    isMaterialIdVaild = false;
//                }
//                materialCountValue = 0;
//            }
//        } catch (Exception ex) {
//            System.out.println("Exception In Checking Material :  " + ex);
//        }
//        return SUCCESS;
//    }
    public String checkVendor() {
        ResultSet resultSet;
        int vndrCountValue = 0;
        String vndrIsAvaiable = null;
        try {
            if (!((Txtvendor.equalsIgnoreCase(null)) || (Txtvendor.equals("")))) {
                vndrIsAvaiable = "select count(*)from vendor_master where vendor_id='" + Txtvendor.trim() + "'";
                vndrCountValue = cado.Fun_Int(vndrIsAvaiable);
                if (vndrCountValue > 0) {
                    isVendorValid = true;
                    if (!((Txtmaterial.equalsIgnoreCase(null)) || (Txtmaterial.equals("")))) {
                        isMaterialEmpty = false;
                        String getCraftGroupQuery = "select craft_group from pos.craft_counter_list where storage_location='" + session.get("loginStorageLocation") + "'";
                        resultSet = cado.Fun_Resultset(getCraftGroupQuery);
                        crftGrpList.clear();
                        while (resultSet.next()) {
                            crftGrpList.add(resultSet.getString("craft_group"));
                        }
                        for (String grpNames : crftGrpList) {
                            String isMaterialVendorValid = "SELECT count(*) FROM pos.material_vendor_craft where material_no='" + Txtmaterial.trim() + "' and vendor_id='" + Txtvendor.trim() + "'  and craftgroup='" + grpNames.trim() + "'  limit 1";
                            if (cado.Fun_Int(isMaterialVendorValid) > 0) {
                                isVendorCgBoolean = true;
                                break;
                            } else if (cado.Fun_Int(isMaterialVendorValid) <= 0) {
                                /*BY DEFAULT MADE TRUE TO AVOID CHECKING MATERIAL-VENDOR VALIDATION */
                                isVendorCgBoolean = false;
                                //isVendorCgBoolean = true;
                            }
                        }
                    } else {
                        isMaterialEmpty = true;
                    }
                } else if (vndrCountValue <= 0) {
                    isVendorValid = false;
                }
            }
        } catch (Exception ex) {
            System.out.println("" + ex.getMessage());
        }
        return SUCCESS;
    }

    public String checkVendor_old() {
        String vendorCheckQry = "";
        int vendorCountValue = 0;
        ResultSet resultSet = null;
        String getMtrlCgQry = null;
        String getVendorCgQry = null;

        try {
            if (!((Txtvendor.equalsIgnoreCase(null)) || (Txtvendor.equals("")))) {
                vendorCheckQry = "select count(*)from vendor_master where vendor_id='" + Txtvendor.trim() + "'";
                vendorCountValue = cado.Fun_Int(vendorCheckQry);
                /*
                 If Count value is greater that one,atleast one element is in Db.
                 */

                if (vendorCountValue > 0) {
                    isVendorValid = true;
                    /*
                     TEMP-BLOCKED-14.08.2015-PRANESH
                     */
                    getMtrlCgQry = "select craft_group from pos.material_master_taxgroup where material_no='" + Txtmaterial.trim() + "'";
                    String materialCrftGrp = cado.Fun_Str(getMtrlCgQry);
                    getVendorCgQry = "select craftGroup from pos.vendor_master where vendor_id='" + Txtvendor.trim() + "'";
                    String vendorCrftGrp = cado.Fun_Str(getVendorCgQry);
                    /* Vendor validation for CRAFT GROUP
                     TEMP-BLOCKED-14.08.2015-PRANESH
                     */
                    if ((!(materialCrftGrp.equals("")) && (!(vendorCrftGrp).equals("")))) {
                        if (materialCrftGrp.equals(vendorCrftGrp)) {
                            isVendorCgBoolean = true;
                        } else {
                            isVendorCgBoolean = false;
                        }
                    } else {
                        isVendorCgBoolean = true;
                    }
                } else if (vendorCountValue <= 0) {
                    isVendorValid = false;
                }
                vendorCountValue = 0;
            }
        } catch (Exception ex) {
            System.out.println("Exception In Checking Vendor :  " + ex);
        }
        return SUCCESS;
    }

    public String Fun_loadBranchCounter() {
        ResultSet rs = null;
        try {
            counterNumberses = new ArrayList();
            String counterNoQuerry = "select counter_no_legacy,counter from branch_counter";
            rs = cado.Fun_Resultset(counterNoQuerry);
            counterNumberses.clear();
            while (rs.next()) {
                //counterNumberses.add(new CounterNumbers(rs.getString("CraftGroup"),rs.getString("CraftName")));
                counterNumberses.add(new CounterNumbers(rs.getString("counter"), rs.getString("counter_no_legacy") + " - " + rs.getString("counter")));
            }
        } catch (SQLException ex) {
            System.out.println("Exception In Filling Branch Code :  " + ex.getMessage());
        } finally {
            cado.closeResultSet(rs);
        }
        return SUCCESS;
    }

    public String loadCraftGrpNames() {
        ResultSet crftGrpNameRs = null;
        try {
            craftGrpName = new ArrayList();
            String craftGrupQuery = "SELECT distinct craft_counter_list.craft_group as CraftGroup,craft_counter_list.description as CraftName FROM pos.branch_counter branch_counter INNER JOIN pos.craft_counter_list craft_counter_list ON (branch_counter.counter_no = craft_counter_list.storage_location)";
            crftGrpNameRs = cado.Fun_Resultset(craftGrupQuery);
            craftGrpName.clear();
            while (crftGrpNameRs.next()) {
                craftGrpName.add(new CraftGroupsTo(crftGrpNameRs.getString("CraftGroup").trim(), crftGrpNameRs.getString("CraftName").trim()));
            }
        } catch (Exception ex) {
            System.out.println("Exception in loading CraftGrpNames :: " + ex.getMessage());
        } finally {
            cado.closeResultSet(crftGrpNameRs);
        }
        return SUCCESS;
    }

    public String validateBranchNumber() {
        isBranchExits = 0;
        try {
            if (branchCode.equals(cado.getPlantId())) {
                isBranchExits = 1;
            } else {
                isBranchExits = 0;
            }
        } catch (Exception ex) {
            System.out.println("Exception In Filling BranchId :  " + ex.getMessage());
        }
        return SUCCESS;
    }

    public String getDcsrFromDate() {
        return dcsrFromDate;
    }

    public void setDcsrFromDate(String dcsrFromDate) {
        this.dcsrFromDate = dcsrFromDate;
    }

    public String getDcsrToDate() {
        return dcsrToDate;
    }

    public void setDcsrToDate(String dcsrToDate) {
        this.dcsrToDate = dcsrToDate;
    }

    public List<CraftGroupsTo> getCraftGrpName() {
        return craftGrpName;
    }

    public void setCraftGrpName(List<CraftGroupsTo> craftGrpName) {
        this.craftGrpName = craftGrpName;
    }

    public List<VendorNos> getVendorNumbers() {
        return vendorNumbers;
    }

    public void setVendorNumbers(List<VendorNos> vendorNumbers) {
        this.vendorNumbers = vendorNumbers;
    }

    public String getSelectedVendorType() {
        return selectedVendorType;
    }

    public void setSelectedVendorType(String selectedVendorType) {
        this.selectedVendorType = selectedVendorType;
    }

    public List<CraftGroupsTo> getCraftGroupsTo() {
        return craftGroupsTo;
    }

    public void setCraftGroupsTo(List<CraftGroupsTo> craftGroupsTo) {
        this.craftGroupsTo = craftGroupsTo;
    }

    public boolean isIsVendorCgBoolean() {
        return isVendorCgBoolean;
    }

    public void setIsVendorCgBoolean(boolean isVendorCgBoolean) {
        this.isVendorCgBoolean = isVendorCgBoolean;
    }

    public List<SalesIntimationTypes> getIntimationTypesTo() {
        return intimationTypesTo;
    }

    public void setIntimationTypesTo(List<SalesIntimationTypes> intimationTypesTo) {
        this.intimationTypesTo = intimationTypesTo;
    }

    public List<CashierDetails> getCashierNames() {
        return cashierNames;
    }

    public void setCashierNames(List<CashierDetails> cashierNames) {
        this.cashierNames = cashierNames;
    }

    public List<ReportTypesTo> getReportTypesTo() {
        return reportTypesTo;
    }

    public void setReportTypesTo(List<ReportTypesTo> reportTypesTo) {
        this.reportTypesTo = reportTypesTo;
    }

    public List<PasswordQuestions> getPqs() {
        return pqs;
    }

    public void setPqs(List<PasswordQuestions> pqs) {
        this.pqs = pqs;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String getReceivedBranchId() {
        return receivedBranchId;
    }

    public void setReceivedBranchId(String receivedBranchId) {
        this.receivedBranchId = receivedBranchId;
    }

    public int getIsBranchExits() {
        return isBranchExits;
    }

    public void setIsBranchExits(int isBranchExits) {
        this.isBranchExits = isBranchExits;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public List getBranchCodes() {
        return branchCodes;
    }

    public void setBranchCodes(List branchCodes) {
        this.branchCodes = branchCodes;
    }

    public List<PaymentTypeTo> getPtts() {
        return ptts;
    }

    public void setPtts(List<PaymentTypeTo> ptts) {
        this.ptts = ptts;
    }

    public String getDisCuntAccess() {
        return disCuntAccess;
    }

    public void setDisCuntAccess(String disCuntAccess) {
        this.disCuntAccess = disCuntAccess;
    }

    public String getDisUname() {
        return disUname;
    }

    public void setDisUname(String disUname) {
        this.disUname = disUname;
    }

    public String getDisPwd() {
        return disPwd;
    }

    public void setDisPwd(String disPwd) {
        this.disPwd = disPwd;
    }

    public String getAutheUtype() {
        return autheUtype;
    }

    public void setAutheUtype(String autheUtype) {
        this.autheUtype = autheUtype;
    }

    public int getBolAuthe() {
        return bolAuthe;
    }

    public void setBolAuthe(int bolAuthe) {
        this.bolAuthe = bolAuthe;
    }

    public List<CounterNumbers> getCounterNumberses() {
        return counterNumberses;
    }

    public void setCounterNumberses(List<CounterNumbers> counterNumberses) {
        this.counterNumberses = counterNumberses;
    }

    public List getStateList() {
        return stateList;
    }

    public void setStateList(List stateList) {
        this.stateList = stateList;
    }

    public List getCityList() {
        return cityList;
    }

    public void setCityList(List cityList) {
        this.cityList = cityList;
    }

    public String getStateVal() {
        return StateVal;
    }

    public void setStateVal(String StateVal) {
        this.StateVal = StateVal;
    }

    public String getTxtvendor() {
        return Txtvendor;
    }

    public void setTxtvendor(String Txtvendor) {
        this.Txtvendor = Txtvendor;
    }

    public boolean isIsSameCraftGroup() {
        return isSameCraftGroup;
    }

    public void setIsSameCraftGroup(boolean isSameCraftGroup) {
        this.isSameCraftGroup = isSameCraftGroup;
    }

    public boolean isIsMaterialIdVaild() {
        return isMaterialIdVaild;
    }

    public void setIsMaterialIdVaild(boolean isMaterialIdVaild) {
        this.isMaterialIdVaild = isMaterialIdVaild;
    }

    public boolean isIsVendorValid() {
        return isVendorValid;
    }

    public void setIsVendorValid(boolean isVendorValid) {
        this.isVendorValid = isVendorValid;
    }

    public String getTxtmaterial() {
        return Txtmaterial;
    }

    public void setTxtmaterial(String Txtmaterial) {
        this.Txtmaterial = Txtmaterial;
    }
}
