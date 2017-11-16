package com.Action;

import com.DAO.*;
import java.sql.*;
import java.util.Map;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpSession;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.SessionAware;
import static com.opensymphony.xwork2.Action.SUCCESS;
import java.util.ArrayList;
import java.util.List;
//import org.apache.log4j.Logger;
/*@author pranesh*/

public class UserLogin_Action extends ActionSupport implements SessionAware {

    int logpk;
    int counterId;
    String plantId;
    int counter_status;
    String counterName;
    int userCountValue = 0;
    HttpSession httpSession;
    String selectedCounterText;
    private String Txtmaterial;
    ResultSet rs = null;
    ResultSet resultSet = null;
    private String loginCounterName;
    private String changedCounterType;
    private String hiddenNavigatedCounterName;
    DaoClass cado = new DaoClass();
    private SessionMap<String, Object> session;
    private static List<String> craftGroupList = new ArrayList();
//    static final Logger log = Logger.getLogger(UserLogin_Action.class);

    String curntLoginId = "", txtlog_empid = "", txtorderno = "", disLogName = "", errMsg = "", loginPlantid, loginempId, salesOrderNo, forwardPage, displayname, cursession_value, Return_userType = "", isUserCountQuery = "", txtUname, txtPassword, loguname, loguserId;
    private String logusrtype;

    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");

    private String user_userName;
    private String user_security_answer;
    private String user_confirm_password;
    private String passwordResetUserName;
    private String user_security_questions;

    private String buttonValue;
    int user_password_status = 0;
    private int isUserStatusVaild = 0;
    private boolean isSameCraftGroup = false;
    private boolean isMaterialIdVaild = false;
    private boolean passwordRefreshedStatus = false;

    private String empPk;
    private int updatePwdvalue;

    public UserLogin_Action() {
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String Fun_Login() {
        String userAccDetailQuery = "";
        Return_userType = "";

        try {
//            if (ActionContext.getContext().getSession().containsKey(txtUname.trim())) {
//                Return_userType = "userSessionExits";
//            } else {
            if (plantId.equalsIgnoreCase("")) {
                Return_userType = "Invaiduser";
            } else {
                String checkIsOnline = "select count(*)from onlineusers where userName='" + txtUname.trim() + "'";
                //int onlineUserCountValues = cado.Fun_Int(checkIsOnline);
                int onlineUserCountValues = 0;
                if (onlineUserCountValues <= 0) {
                    if (plantId.equals(cado.getPlantId())) {
                        isUserCountQuery = "select count(*) from user_master where username='" + txtUname.trim() + "' and pwd='" + txtPassword.trim() + "'";
                        userCountValue = cado.Fun_Int(isUserCountQuery);
                        if (userCountValue > 0) {
                            String userValidStatus = "SELECT emp_master.emp_status FROM pos.emp_master emp_master INNER JOIN pos.user_master user_master ON(emp_master.emp_pk = user_master.emp_fk)WHERE(user_master.username = '" + txtUname.trim() + "')AND(user_master.pwd ='" + txtPassword.trim() + "')";
                            isUserStatusVaild = cado.Fun_Int(userValidStatus);
                            if (isUserStatusVaild > 0) {
                                userAccDetailQuery = "SELECT emp_master.emp_pk,emp_master.emp_id,emp_master.emp_name,emp_master.emp_type FROM pos.user_master user_master INNER JOIN pos.emp_master emp_master ON (user_master.emp_fk = emp_master.emp_pk)WHERE(user_master.username = '" + txtUname.trim() + "')AND (user_master.pwd = '" + txtPassword.trim() + "')";
                                rs = cado.Fun_Resultset(userAccDetailQuery);
                                if (rs.next()) {
                                    ClassicSingleton.getOneInst();
                                    logpk = rs.getInt("emp_master.emp_pk");
                                    loguserId = rs.getString("emp_master.emp_id");
                                    loguname = rs.getString("emp_master.emp_name");
                                    logusrtype = rs.getString("emp_master.emp_type");
                                }
                                String getCounterNoQuery = "select counter_pk from branch_counter where counter='" + selectedCounterText.trim() + "'";
                                //String getLoginCraftGroup = "SELECT ccounter.craft_group FROM pos.branch_counter bcounter INNER JOIN pos.craft_counter_list ccounter ON(bcounter.counter_no = ccounter.storage_location)where bcounter.counter='" + selectedCounterText.trim() + "'";
                                String getLoginStorageLocation = "select counter_no from pos.branch_counter where counter='" + selectedCounterText.trim() + "'";
                                session.put("Login_pk", logpk);
                                session.put("activeUserName", txtUname.trim());
                                session.put("Login_name", loguname);
                                session.put("Login_Id", loguserId);
                                session.put("User_type", logusrtype);
                                session.put("Plant_Id", DaoClass.getPlantId());
                                session.put("LoginCounterId", cado.Fun_Int(getCounterNoQuery));
                                session.put("LoginCounterName", selectedCounterText.trim());
                                //session.put("loginCraftGroup", cado.Fun_Str(getLoginStorageLocation));
                                session.put("loginStorageLocation", cado.Fun_Str(getLoginStorageLocation));

                                String counterStatusLockQuery = "update branch_counter set counter_status='1',login_userid='" + loguserId + "' where counter='" + selectedCounterText.trim() + "'";
                                //System.out.println("counterStatusLockQuery  :   " + counterStatusLockQuery);
                                cado.Fun_Updat(counterStatusLockQuery);//LOCKING THE PARTICULAR COUNTER FOR LOGIN USER-WITH USERID.
                                //ActionContext.getContext().getSession().put(txtUname, txtUname.trim());
                                switch (logusrtype) {
                                    case "salesCounter":
                                        Return_userType = "salesCounterUser";
                                        break;
                                    case "cashCounter":
                                        Return_userType = "cashCounterUser";
                                        break;
                                    case "all":
                                        Return_userType = "genericCounter";
                                        break;
                                }
                                //Fun_FillSaleOrder();
                                disLogName = session.get("Login_name").toString();
                                loginCounterName = selectedCounterText.trim();
                                loginCounterName = loginCounterName + " " + "COUNTER";
                                String checkAvailOnlineUsersCount = "select count(*) from onlineusers where userName='" + txtUname.trim() + "'";
                                if (cado.Fun_Int(checkAvailOnlineUsersCount) == 0) {
                                    String onlineUsers = "insert into onlineusers(userName)values('" + txtUname.trim() + "')";
                                    cado.Fun_Updat(onlineUsers);
                                }
                                //System.out.println("Online User Querry :  " + onlineUsers);
                            } else if (isUserStatusVaild <= 0) {
                                Return_userType = "Invaiduser";
                            }
                            //}
                        } else if (userCountValue <= 0) {
                            Return_userType = "Invaiduser";
                        }
                    }
                } else if (onlineUserCountValues >= 1) {
                    Return_userType = "onlineuser";
                }
            }
        } catch (SQLException ex) {
//            log.info(ex.getMessage());
            //System.out.println("Exception In Login :  " + ex);
        } finally {
            cado.closeResultSet(rs);
            cado.closeResultSet(resultSet);
        }
        return Return_userType;
    }

    public String applyChangedCounterType() {
        try {
            String getGenericStorageLocation = "select counter_no from pos.branch_counter where counter='" + changedCounterType.trim() + "'";
            session.put("loginStorageLocation", cado.Fun_Str(getGenericStorageLocation));

            String getGenericCounterNoQuery = "select counter_pk from branch_counter where counter='" + changedCounterType.trim() + "'";
            session.put("LoginCounterId", cado.Fun_Int(getGenericCounterNoQuery));
        } catch (Exception ex) {
            //System.out.println("Exception in changing generic counter: " + ex);
//            log.info(ex.getMessage());
        }
        return SUCCESS;
    }

    public String checkMaterial() {
        String materialCheckQry = "";
        int materialCountValue = 0;
        String validateMaterialCraftGroupQuery = "";
        try {
            if (!((Txtmaterial.equalsIgnoreCase(null)) || (Txtmaterial.equals("")))) {
                materialCheckQry = "select count(*) from material_master_taxgroup where material_no='" + Txtmaterial.trim() + "'";
                materialCountValue = cado.Fun_Int(materialCheckQry);
                if (materialCountValue > 0) {
                    //String loginCraftGroupQuery = "select craft_group from material_master_taxgroup where material_no='" + Txtmaterial.trim() + "'";
                    //@22-11-2014:-String loginStorageLocationQuery = "SELECT Ccounter.storage_location,MmTaxGroup.material_no FROM pos.material_master_taxgroup MmTaxGroup INNER JOIN pos.craft_counter_list Ccounter ON(MmTaxGroup.craft_group =Ccounter.craft_group)WHERE (MmTaxGroup.material_no ='" + Txtmaterial.trim() + "')";
                    //System.out.println("Inside material check :  " + hiddenNavigatedCounterName);
                    validateMaterialCraftGroupQuery = "select craft_group from pos.material_master_taxgroup where material_no='" + Txtmaterial.trim() + "'";
                    isMaterialIdVaild = true;
                    String getCraftGroupQuery = "select craft_group from pos.craft_counter_list where storage_location='" + session.get("loginStorageLocation") + "'";
                    resultSet = cado.Fun_Resultset(getCraftGroupQuery);
                    craftGroupList.clear();
                    while (resultSet.next()) {
                        craftGroupList.add(resultSet.getString("craft_group"));
                    }
                    for (String groupNames : craftGroupList) {
                        if (cado.Fun_Str(validateMaterialCraftGroupQuery).equalsIgnoreCase(groupNames)) {
                            isSameCraftGroup = true;
                            break;
                        } else {
                            isSameCraftGroup = false;
                        }
                    }
                } else if (materialCountValue <= 0) {
                    isMaterialIdVaild = false;
                }
                materialCountValue = 0;
            }
        } catch (SQLException ex) {
            //System.out.println("Exception In Checking Material :  " + ex);
            //log.info(ex.getMessage());
        }
        return SUCCESS;
    }

    public String isPasswordRefreshed() {
        try {
            String checkingQuery = "select security_answer from user_master where username='" + passwordResetUserName.trim() + "'";
            passwordRefreshedStatus = cado.Fun_Str(checkingQuery).equalsIgnoreCase("null");
        } catch (Exception ex) {
            //System.out.println("Exception in checking password is refreshed :  " + ex);
//            log.info(ex.getMessage());
        }
        return SUCCESS;
    }

    public String userPasswordResetProcess() {
        try {
            String isUserNameQuestionMatch = null;
            String isUserNameAvai = "select count(username)from user_master where username='" + user_userName.trim() + "'";
            if (cado.Fun_Int(isUserNameAvai) > 0) {
                if (buttonValue.equalsIgnoreCase("set password")) {
                    isUserNameQuestionMatch = "select count(username) from user_master where username='" + user_userName.trim() + "'";
                } else if (buttonValue.equalsIgnoreCase("reset password")) {
                    isUserNameQuestionMatch = "select count(username) from user_master where username='" + user_userName.trim() + "' and securityQuestion_fk='" + user_security_questions.trim() + "' and security_answer='" + user_security_answer.trim() + "'";
                }
                if (cado.Fun_Int(isUserNameQuestionMatch) > 0) {
                    String updatingPasswordQuerry = "update user_master set pwd='" + user_confirm_password.trim() + "',securityQuestion_fk='" + user_security_questions.trim() + "',security_answer='" + user_security_answer.trim() + "',lastUpdated=now() where username='" + user_userName.trim() + "'";
                    if (cado.Fun_Updat(updatingPasswordQuerry) == 1) {
                        user_password_status = 1;//PASSWORD RESETED SUCCESSFULLY.
                    }
                } else {
                    user_password_status = -2;//INVVALID USERNAME AND SECURITY QUESTION.
                }
            } else {
                user_password_status = -1;//USERNAME NOT AVAIABLE.
            }
        } catch (Exception ex) {
            //System.out.println("Exception in reseting password :  " + ex);
//            log.info(ex.getMessage());
        }
        return SUCCESS;
    }

    public void Fun_FillSaleOrder() {
        salesOrderNo = "";
        try {
            int salesOrderNoLength = 4;
            String salesorderNoQuerry = "select max(header_pk) from header";
            salesOrderNo = cado.Fun_SalesOrderNo(cado.getPlantId(), session.get("LoginCounterId").toString(), salesorderNoQuerry, salesOrderNoLength);
        } catch (Exception ex) {
            System.err.println("Exception In Creating SalesOrder :  " + ex);
//            log.info(ex.getMessage());
        }
    }

    public String Fun_ChkSession() {
        try {
            loginempId = "";
            if (session != null) {
                //if (session.isEmpty()) {
                cursession_value = "valid";
                displayname = session.get("Login_name").toString();
                loginempId = session.get("Login_Id").toString();
                counterName = session.get("LoginCounterName").toString();
                logusrtype = session.get("User_type").toString();
                /*
                 HttpSession httpSession = null;
                 httpSession.setAttribute("userRole", session.get("User_type").toString());
                 */
                //Fun_FillSaleOrder();
            } else {
                cursession_value = "in-valid";
                displayname = "";
            }
        } catch (Exception ex) {
            System.out.println("Exception in Checking Session Value   " + ex);
//            log.info(ex.getMessage());
        }
        return SUCCESS;
    }

    public String Fun_ClearSession() {
        try {
            forwardPage = "";
            curntLoginId = "";
            if (session != null) {
                String removeOnlineUsers = "delete from onlineusers where userName='" + session.get("activeUserName").toString() + "'";
                cado.Fun_Updat(removeOnlineUsers);
                String counter_status_release = "update branch_counter set counter_status='0',login_userid='No-User' where counter='" + session.get("LoginCounterName").toString() + "'";
                cado.Fun_Updat(counter_status_release);
                session.invalidate();
                forwardPage = "homepage";
            }
        } catch (Exception ex) {
            System.out.println("Exception in Logout Session " + ex);
            //log.info(ex.getMessage());
        }
        return SUCCESS;
    }

    public String updatePwd() {
        try {
            String resetPwdQuery = "UPDATE `pos`.`user_master` SET `pwd`='123', `securityQuestion_fk`='1', `security_answer`='Nill', `lastUpdated`=now() WHERE `user_pk`='" + empPk + "' ";
            updatePwdvalue = cado.Fun_Updat(resetPwdQuery);
        } catch (Exception e) {
            updatePwdvalue = 0;
            System.err.println("Error Message is :" + e.getMessage());
        }

        return SUCCESS;
    }

    public String getLogusrtype() {
        return logusrtype;
    }

    public void setLogusrtype(String logusrtype) {
        this.logusrtype = logusrtype;
    }

    public String getHiddenNavigatedCounterName() {
        return hiddenNavigatedCounterName;
    }

    public void setHiddenNavigatedCounterName(String hiddenNavigatedCounterName) {
        this.hiddenNavigatedCounterName = hiddenNavigatedCounterName;
    }

    public String getChangedCounterType() {
        return changedCounterType;
    }

    public void setChangedCounterType(String changedCounterType) {
        this.changedCounterType = changedCounterType;
    }

    public static List<String> getCraftGroupList() {
        return craftGroupList;
    }

    public static void setCraftGroupList(List<String> craftGroupList) {
        UserLogin_Action.craftGroupList = craftGroupList;
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

    public String getTxtmaterial() {
        return Txtmaterial;
    }

    public void setTxtmaterial(String Txtmaterial) {
        this.Txtmaterial = Txtmaterial;
    }

    public String getUser_userName() {
        return user_userName;
    }

    public void setUser_userName(String user_userName) {
        this.user_userName = user_userName;
    }

    public String getUser_security_questions() {
        return user_security_questions;
    }

    public void setUser_security_questions(String user_security_questions) {
        this.user_security_questions = user_security_questions;
    }

    public String getUser_security_answer() {
        return user_security_answer;
    }

    public void setUser_security_answer(String user_security_answer) {
        this.user_security_answer = user_security_answer;
    }

    public String getUser_confirm_password() {
        return user_confirm_password;
    }

    public void setUser_confirm_password(String user_confirm_password) {
        this.user_confirm_password = user_confirm_password;
    }

    public String getPasswordResetUserName() {
        return passwordResetUserName;
    }

    public void setPasswordResetUserName(String passwordResetUserName) {
        this.passwordResetUserName = passwordResetUserName;
    }

    public boolean isPasswordRefreshedStatus() {
        return passwordRefreshedStatus;
    }

    public void setPasswordRefreshedStatus(boolean passwordRefreshedStatus) {
        this.passwordRefreshedStatus = passwordRefreshedStatus;
    }

    public String getButtonValue() {
        return buttonValue;
    }

    public void setButtonValue(String buttonValue) {
        this.buttonValue = buttonValue;
    }

    public int getUser_password_status() {
        return user_password_status;
    }

    public void setUser_password_status(int user_password_status) {
        this.user_password_status = user_password_status;
    }

    public String getLoginCounterName() {
        return loginCounterName;
    }

    public void setLoginCounterName(String loginCounterName) {
        this.loginCounterName = loginCounterName;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getPlantId() {
        return plantId;
    }

    public void setPlantId(String plantId) {
        this.plantId = plantId;
    }

    public String getSelectedCounterText() {
        return selectedCounterText;
    }

    public void setSelectedCounterText(String selectedCounterText) {
        this.selectedCounterText = selectedCounterText;
    }

    public String getTxtlog_empid() {
        return txtlog_empid;
    }

    public void setTxtlog_empid(String txtlog_empid) {
        this.txtlog_empid = txtlog_empid;
    }

    public String getTxtorderno() {
        return txtorderno;
    }

    public void setTxtorderno(String txtorderno) {
        this.txtorderno = txtorderno;
    }

    public String getCurntLoginId() {
        return curntLoginId;
    }

    public void setCurntLoginId(String curntLoginId) {
        this.curntLoginId = curntLoginId;
    }

    public String getDisLogName() {
        return disLogName;
    }

    public void setDisLogName(String disLogName) {
        this.disLogName = disLogName;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getLoginempId() {
        return loginempId;
    }

    public void setLoginempId(String loginempId) {
        this.loginempId = loginempId;
    }

    public String getSalesOrderNo() {
        return salesOrderNo;
    }

    public void setSalesOrderNo(String salesOrderNo) {
        this.salesOrderNo = salesOrderNo;
    }

    public String getForwardPage() {
        return forwardPage;
    }

    public void setForwardPage(String forwardPage) {
        this.forwardPage = forwardPage;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getCursession_value() {
        return cursession_value;
    }

    public void setCursession_value(String cursession_value) {
        this.cursession_value = cursession_value;
    }

    public String getTxtUname() {
        return txtUname;
    }

    public void setTxtUname(String txtUname) {
        this.txtUname = txtUname;
    }

    public String getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(String txtPassword) {
        this.txtPassword = txtPassword;
    }

    public SessionMap<String, Object> getSession() {
        return session;
    }

    @Override
    public void setSession(Map<String, Object> map) {
        session = (SessionMap) map;
    }

    public String getEmpPk() {
        return empPk;
    }

    public void setEmpPk(String empPk) {
        this.empPk = empPk;
    }

    public int getUpdatePwdvalue() {
        return updatePwdvalue;
    }

    public void setUpdatePwdvalue(int updatePwdvalue) {
        this.updatePwdvalue = updatePwdvalue;
    }

}
