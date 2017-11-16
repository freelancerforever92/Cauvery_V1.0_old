/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pos.updated.reports;

import java.util.ArrayList;
import java.util.List;

public class NewClass {
    public static void main(String ...args){
        List<String> al=new ArrayList<String>();
        al.add("v1");
        al.add("2v");
        al.add("v3");
        al.add("v6");
        al.add("v4");
        al.add("v5");
        System.out.println("===>"+al.toString());
        
        System.out.println("==>"+al.contains("v6"));
        
    }
}
