/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;
import org.apache.struts2.interceptor.SessionAware;

public class LoginInterceptor extends AbstractInterceptor implements Interceptor, SessionAware, StrutsStatics {

    private static final Log log = LogFactory.getLog(LoginInterceptor.class);
    private static final String USER_HANDLE = "LoginName";
    Map session;

    public Map getSession() {
        return session;
    }

    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public LoginInterceptor() {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
        log.info("intializing login");
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        String rtn = "";
        final ActionContext ac = actionInvocation.getInvocationContext();
        session = ac.getSession();
        String str = (String) session.get("Login_name");
        //if (str.equalsIgnoreCase(null) || str.equals("")) {
        if (((session == null) || (session.isEmpty()) || (session.get("Login_name") == null))) {
            //return "r_error";
            rtn = "r_error";
        } else {
            rtn = "success";
        }
        String actionResult = actionInvocation.invoke();
        /*if(rtn.equals("r_error"))
         {
         if(session.get("Login_name")!="" || session.get("Login_name")!=null)
         {
         session.remove("Login_pk");
         session.remove("Login_name");
         session.remove("Company_name");
         System.out.println("=======SESSION DESTROYED IN Interceptor======");
         System.out.println("------Login_pk----------" + session.get("Login_pk"));
         System.out.println("------Login_name--------" + session.get("Login_name"));
         System.out.println("------Company_name------" + session.get("Company_name"));                             
         }   
         }
         else
         {
         }*/
        return rtn;
    }
}
