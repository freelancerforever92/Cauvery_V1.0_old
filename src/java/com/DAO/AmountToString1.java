/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.DAO;

/**
 *
 * @author Administrator
 */
public class AmountToString1 {

    private static final String[] ones
            = {
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
                " Nineteen"};

    private static final String[] tens
            = {
                " Twenty",
                " Thirty",
                " Fourty",
                " Fifty",
                " Sixty",
                " Seventy",
                " Eighty",
                " Ninety"};

//
// so quintillions is as big as it gets. The
// program would automatically handle larger
// numbers if this array were extended.
//
    private static final String[] groups
            = {
                "",
                " Thousand",
                " Million",
                " Billion",
                " Trillion",
                " Quadrillion",
                " Quintillion"};

    private String string = new String();

    public String getString() {
        return string;
    }

    public String EnglishNumber(float receiveAmount) {
        string = "";
        long enteredNo = (long) ((int) (Math.round(receiveAmount)));

// Go through the number one group at a time.
//System.out.println("groups.length= " + groups.length);
        for (int i = groups.length - 1; i >= 0; i--) {

// Is the number as big as this group?
            long cutoff = (long) Math.pow((double) 10, (double) (i * 3));

            if (enteredNo >= cutoff) {
                int thisPart = (int) (enteredNo / cutoff);

// Use the ones[] array for both the
// hundreds and the ones digit. Note
// that tens[] starts at "twenty".
                if (thisPart >= 100) {
                    string += ones[(thisPart / 100) - 1] + " Hundred" + " " + "And";

                    thisPart = thisPart % 100;
                }
                if (thisPart >= 20) {
                    string += tens[(thisPart / 10) - 2];
                    thisPart = thisPart % 10;
                }
                if (thisPart >= 1) {
                    string += ones[thisPart - 1];
                }

                string += groups[i];

                enteredNo = enteredNo % cutoff;//to check for big numbers which are greater than or equal to 1 million
//System.out.println("enteredNo is : " + enteredNo);
            }
        }

        if (string.length() == 0) {
            string = "Zero";
        } else {
// remove initial space
            string = string.substring(1);
        }
        return string;
    }

    public static void main(String args[]) {

        AmountToString1 ntw = new AmountToString1();
        String Englishword = null;
        Englishword = ntw.EnglishNumber(1251);
        System.out.println("The entered number in words is : " + Englishword);

    }

}
