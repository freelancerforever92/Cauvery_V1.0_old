/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 *
 * @author pranesh
 */
public class AmountToString {

    private static final String[] tensNames = {
        "",
        " Ten",
        " Twenty",
        " Thirty",
        " Forty",
        " Fifty",
        " Sixty",
        " Seventy",
        " Eighty",
        " Ninety"
    };

    private static final String[] numNames = {
        "",
        " One",
        " Two",
        " Three",
        " Four",
        " Five",
        " Six",
        " Seven",
        " Eight",
        " Nine",
        " Ten",
        " Eleven",
        " Twelve",
        " Thirteen",
        " Fourteen",
        " Fifteen",
        " Sixteen",
        " Seventeen",
        " Eighteen",
        " Nineteen"
    };

    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20) {
            soFar = numNames[number % 100];
            number /= 100;
        } else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) {
            return soFar;
        }
        return numNames[number] + " Hundred" + soFar;
    }

    //public static String convert(long number) {
    public static String EnglishNumber(long number) {
// 0 to 999 999 999 999 
        if (number == 0) {
            return "Zero";
        }

        String snumber = Long.toString(number);

// pad with "0" 
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

// XXXnnnnnnnnn 
        int crores = Integer.parseInt(snumber.substring(2, 5));
// nnnXXXnnnnnn 
        int lakhs = Integer.parseInt(snumber.substring(5, 7));
// nnnnnnXXXnnn 
        int hundredThousands = Integer.parseInt(snumber.substring(7, 9));
// nnnnnnnnnXXX 
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradcrores;
        switch (crores) {
            case 0:
                tradcrores = "";
                break;
            case 1:
                tradcrores = convertLessThanOneThousand(crores)
                        + " Crore ";
                break;
            default:
                tradcrores = convertLessThanOneThousand(crores)
                        + " Crore ";
        }
        String result = tradcrores;

        String tradlakhs;
        switch (lakhs) {
            case 0:
                tradlakhs = "";
                break;
            case 1:
                tradlakhs = convertLessThanOneThousand(lakhs)
                        + " Lakh ";
                break;
            default:
                tradlakhs = convertLessThanOneThousand(lakhs)
                        + " Lakh ";
        }
        result = result + tradlakhs;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "One Thousand ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                        + " Thousand ";
        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = "" + result + tradThousand + "";

// remove extra spaces! 
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    /**
     * testing
     *
     * @param args
     */
    
    public static void main(String[] args) {
        BigDecimal bd = BigDecimal.TEN;

        long l = Math.round(bd.longValue());
        
    }
}
