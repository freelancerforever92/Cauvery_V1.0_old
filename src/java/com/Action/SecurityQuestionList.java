package com.Action;

import com.DAO.DaoClass;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SecurityQuestionList extends ActionSupport {

    private String securityQuestion;
    private int securityPk;
    private String securityCreateDate;
    private String securityUpdatedDate;

    private List questionsList = new ArrayList<>();
    ResultSet questionsListRS = null;

    DaoClass daoClass = new DaoClass();
    int updateQuestionValue;
    int questionsCount;
    int questionsRowsCount;
    int isQuestionCreated;
    int questionslimitValue;

    static ResourceBundle rb = ResourceBundle.getBundle("dbConnection");

    public String questionsList() {
        try {
            String getQuestionsQry = "select questionPk,securityQuestion,DATE_FORMAT(password_security_questions.createdDate,'%d-%m-%Y %h:%m:%s') as createdDate,DATE_FORMAT(password_security_questions.updatedDate,'%d-%m-%Y %h:%m:%s') as updatedDate from pos.password_security_questions";
            questionsListRS = daoClass.Fun_Resultset(getQuestionsQry);
            while (questionsListRS.next()) {
                if (questionsListRS.getInt("questionPk") != 1) {
                    SecurityQuestionList securityObject = new SecurityQuestionList(questionsListRS.getString("securityQuestion"), questionsListRS.getInt("questionPk"), questionsListRS.getString("createdDate"), questionsListRS.getString("updatedDate"));
                    questionsList.add(securityObject);
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception  : " + ex);
        } finally {
            daoClass.closeResultSet(questionsListRS);
            return SUCCESS;
        }

    }

    public String updateQuestion() {
        try {
            String questionsQry = "select count(*) from pos.password_security_questions where securityQuestion='" + securityQuestion.trim() + "'";
            questionsCount = daoClass.Fun_Int(questionsQry);
            if (questionsCount <= 0) {
                String updateQuestionQry = "update pos.password_security_questions set securityQuestion='" + securityQuestion + "', updatedDate=now() where questionPk='" + securityPk + "'";
                updateQuestionValue = daoClass.Fun_Updat(updateQuestionQry);
            }

        } catch (Exception e) {
            updateQuestionValue = 0;
            System.err.println("Error Message is :" + e.getMessage());
        } finally {
            return SUCCESS;
        }

    }

    public String createQuestion() {
        try {
            questionslimitValue = Integer.parseInt(rb.getString("security.question.limit")) + 1;
            String questionsQry = "select count(*) from pos.password_security_questions where securityQuestion='" + securityQuestion.trim() + "'";
            String questionsRowQry = "select count(*) from pos.password_security_questions";
            questionsCount = daoClass.Fun_Int(questionsQry);
            questionsRowsCount = daoClass.Fun_Int(questionsRowQry);
            if (questionsCount <= 0) {
                if (questionsRowsCount < questionslimitValue) {
                    String createQuestionQry = "INSERT INTO `pos`.`password_security_questions` (`securityQuestion`, `createdDate`, `updatedDate`) VALUES ('" + securityQuestion.trim() + "', now(), '0001-01-01 01:01:01')";
                    isQuestionCreated = daoClass.Fun_Updat(createQuestionQry);
                }
            }
        } catch (Exception e) {
            System.err.println("Exception Is :" + e.getMessage());
        } finally {
            return SUCCESS;
        }
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public List getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(List questionsList) {
        this.questionsList = questionsList;
    }

    public int getSecurityPk() {
        return securityPk;
    }

    public void setSecurityPk(int securityPk) {
        this.securityPk = securityPk;
    }

    public String getSecurityCreateDate() {
        return securityCreateDate;
    }

    public void setSecurityCreateDate(String securityCreateDate) {
        this.securityCreateDate = securityCreateDate;
    }

    public String getSecurityUpdatedDate() {
        return securityUpdatedDate;
    }

    public void setSecurityUpdatedDate(String securityUpdatedDate) {
        this.securityUpdatedDate = securityUpdatedDate;
    }

    public SecurityQuestionList(String securityQuestion, int securityPk, String securityCreateDate, String securityUpdatedDate) {
        this.securityQuestion = securityQuestion;
        this.securityPk = securityPk;
        this.securityCreateDate = securityCreateDate;
        this.securityUpdatedDate = securityUpdatedDate;
    }

    @Override
    public String toString() {
        return "SecurityQuestion{" + "securityQuestion=" + securityQuestion + ", securityPk=" + securityPk + ", securityCreateDate=" + securityCreateDate + ", securityUpdatedDate=" + securityUpdatedDate + '}';
    }

    public SecurityQuestionList() {
    }

    public int getUpdateQuestionValue() {
        return updateQuestionValue;
    }

    public void setUpdateQuestionValue(int updateQuestionValue) {
        this.updateQuestionValue = updateQuestionValue;
    }

    public int getQuestionsCount() {
        return questionsCount;
    }

    public void setQuestionsCount(int questionsCount) {
        this.questionsCount = questionsCount;
    }

    public int getQuestionsRowsCount() {
        return questionsRowsCount;
    }

    public void setQuestionsRowsCount(int questionsRowsCount) {
        this.questionsRowsCount = questionsRowsCount;
    }

    public int getIsQuestionCreated() {
        return isQuestionCreated;
    }

    public void setIsQuestionCreated(int isQuestionCreated) {
        this.isQuestionCreated = isQuestionCreated;
    }

    public int getQuestionslimitValue() {
        return questionslimitValue;
    }

    public void setQuestionslimitValue(int questionslimitValue) {
        this.questionslimitValue = questionslimitValue;
    }

}
