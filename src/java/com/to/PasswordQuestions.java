package com.to;

/*@author pranesh */
public class PasswordQuestions {

    private int questionPk;
    private String securityQuestions;

    @Override
    public String toString() {
        return "PasswordQuestions{" + "questionPk=" + questionPk + ", securityQuestions=" + securityQuestions + '}';
    }

    public PasswordQuestions(int questionPk, String securityQuestions) {
        this.questionPk = questionPk;
        this.securityQuestions = securityQuestions;
    }

    public int getQuestionPk() {
        return questionPk;
    }

    public void setQuestionPk(int questionPk) {
        this.questionPk = questionPk;
    }

    public String getSecurityQuestions() {
        return securityQuestions;
    }

    public void setSecurityQuestions(String securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

}
