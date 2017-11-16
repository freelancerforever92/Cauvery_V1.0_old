/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.to;

/**
 *
 * @author Administrator
 */
public class CounterNumbers {

    private String counterLegacyNumber;
    private String counterName;

    /*private String craftName;
     private String craftGroup;
     */
    public CounterNumbers() {
    }

    public CounterNumbers(String counterName, String counterLegacyNumber) {
        this.counterName = counterName;
        this.counterLegacyNumber = counterLegacyNumber;
    }

    @Override
    public String toString() {
        return "CounterNumbers{" + "counterLegacyNumber=" + counterLegacyNumber + ", counterName=" + counterName + '}';
    }

    public String getCounterLegacyNumber() {
        return counterLegacyNumber;
    }

    public void setCounterLegacyNumber(String counterLegacyNumber) {
        this.counterLegacyNumber = counterLegacyNumber;
    }

    public String getCounterName() {
        return counterName;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

}
