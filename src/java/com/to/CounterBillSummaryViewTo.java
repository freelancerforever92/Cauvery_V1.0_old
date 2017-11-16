/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.to;

public class CounterBillSummaryViewTo {

    String counterno;
    String counter;
    String grossamount;
    String netamount;
    String discamount;
    String vatamount;
    String packamount;
    String totalsales;

    @Override
    public String toString() {
        return "CounterBillSummaryViewTo{" + "counterno=" + counterno + ", counter=" + counter + ", grossamount=" + grossamount + ", netamount=" + netamount + ", discamount=" + discamount + ", vatamount=" + vatamount + ", packamount=" + packamount + ", totalsales=" + totalsales + '}';
    }

    public CounterBillSummaryViewTo(String counterno, String counter, String grossamount, String discamount, String netamount, String vatamount, String packamount, String totalsales) {
        this.counterno = counterno;
        this.counter = counter;
        this.grossamount = grossamount;
        this.netamount = netamount;
        this.discamount = discamount;
        this.vatamount = vatamount;
        this.packamount = packamount;
        this.totalsales = totalsales;
    }

    public String getCounterno() {
        return counterno;
    }

    public void setCounterno(String counterno) {
        this.counterno = counterno;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getGrossamount() {
        return grossamount;
    }

    public void setGrossamount(String grossamount) {
        this.grossamount = grossamount;
    }

    public String getNetamount() {
        return netamount;
    }

    public void setNetamount(String netamount) {
        this.netamount = netamount;
    }

    public String getDiscamount() {
        return discamount;
    }

    public void setDiscamount(String discamount) {
        this.discamount = discamount;
    }

    public String getVatamount() {
        return vatamount;
    }

    public void setVatamount(String vatamount) {
        this.vatamount = vatamount;
    }

    public String getPackamount() {
        return packamount;
    }

    public void setPackamount(String packamount) {
        this.packamount = packamount;
    }

    public String getTotalsales() {
        return totalsales;
    }

    public void setTotalsales(String totalsales) {
        this.totalsales = totalsales;
    }

}
