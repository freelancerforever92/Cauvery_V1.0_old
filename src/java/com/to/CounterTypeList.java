/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.to;

/**
 *
 * @author Dheeraj
 */
public class CounterTypeList {
    private int counterNoLegacy; 
    private String counterName;

    public int getCounterNoLegacy() {
        return counterNoLegacy;
    }

    public void setCounterNoLegacy(int counterNoLegacy) {
        this.counterNoLegacy = counterNoLegacy;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public CounterTypeList(String counterName) {
        this.counterName = counterName;
    }

    public CounterTypeList(int counterNoLegacy, String counterName) {
        this.counterNoLegacy = counterNoLegacy;
        this.counterName = counterName;
    }
    
}
