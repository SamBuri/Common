/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import com.saburi.common.entities.ChartAccount;

/**
 *
 * @author Hp
 */
public class AccountAmount {

    private ChartAccount chartAccount;
    private double amount;

    public AccountAmount(ChartAccount chartAccount, double amount) {
        this.chartAccount = chartAccount;
        this.amount = amount;
    }

    public ChartAccount getChartAccount() {
        return chartAccount;
    }

    public void setChartAccount(ChartAccount chartAccount) {
        this.chartAccount = chartAccount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
