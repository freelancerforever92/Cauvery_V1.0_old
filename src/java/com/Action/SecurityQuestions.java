package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SecurityQuestions extends ActionSupport {

    private String securityQuestion;
    private int securityPk;
    private String securityCreateDate;
    private String securityUpdatedDate;
    ResultSet questionsRS = null;
    DaoClass daoClass = new DaoClass();

    public String getQuestion() {
        try {
            String getQuestionQry = "select securityQuestion from pos.password_security_questions where questionPk='" + securityPk + "'";
            questionsRS = daoClass.Fun_Resultset(getQuestionQry);
            while (questionsRS.next()) {
                securityQuestion = questionsRS.getString("securityQuestion");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SecurityQuestions.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return SUCCESS;
        }
    }

    public int getSecurityPk() {
        return securityPk;
    }

    public void setSecurityPk(int securityPk) {
        this.securityPk = securityPk;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

}
