/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.coupon;

import com.opensymphony.xwork2.ActionSupport;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import org.jsoup.Jsoup;

/**
 *
 * @author khdcluser
 */
public class CouponRedemptionAction extends ActionSupport {

    private String couponType;
    private String couponNumber;
    private String responseStatus;
    private String responseCouponType;
    private String responseCouponNumber;
    private BigDecimal responseCouponAmount;

    private boolean isCouponValid = false;
    private boolean couponStatus = false;

    public CouponRedemptionAction() {
    }

    

    public boolean getCouponStatus() {
        try {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String strURL = "http://164.100.133.74:3200/CauveryWebServer/couponredemption.jsp?couponNumber=" + couponNumber.trim() + "&couponType=" + couponType.trim() + "";
            URL url = new URL(strURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.getContent();
            //connection.setReadTimeout(15 * 1000);
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String couponResponse = Jsoup.parse(sb.toString()).text();
            String sapResult[] = couponResponse.split(",");
            responseStatus = sapResult[3];
            if (!(responseStatus.equalsIgnoreCase("e") || responseStatus.equals("") || responseStatus.equalsIgnoreCase(null))) {
                responseCouponNumber = sapResult[0];
                responseCouponType = sapResult[1];
                responseCouponAmount = new BigDecimal(sapResult[2]);
                couponStatus = true;
            } else if (responseStatus.equalsIgnoreCase("e")) {
                couponStatus = false;
            }

        } catch (Exception ex) {
            System.out.println("Coupon URL Exception : " + ex);
        }
        return couponStatus;
    }

    public String siCOUPONREDEMPTIONVAILD() {
        getCouponStatus();
        return SUCCESS;
    }

    public BigDecimal getResponseCouponAmount() {
        return responseCouponAmount;
    }

    public void setResponseCouponAmount(BigDecimal responseCouponAmount) {
        this.responseCouponAmount = responseCouponAmount;
    }

    public String getResponseCouponNumber() {
        return responseCouponNumber;
    }

    public void setResponseCouponNumber(String responseCouponNumber) {
        this.responseCouponNumber = responseCouponNumber;
    }

    public boolean isCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(boolean couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(String couponNumber) {
        this.couponNumber = couponNumber;
    }
}
