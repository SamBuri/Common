/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import com.saburi.common.dbaccess.FinancialPeriodDA;

/**
 *
 * @author CLINICMASTER13
 */
public class CurrentFinacialPeriod {

    private static FinancialPeriodDA financialPeriodDA;

    public static void setFinancialPeriodDA(FinancialPeriodDA financialPeriodDA) {
        CurrentFinacialPeriod.financialPeriodDA = financialPeriodDA;
    }

    public static String getCurrentPeriodNo() {
        return financialPeriodDA.getPeriodID();
    }

    public static FinancialPeriodDA getFinancialPeriodDA() {
        return CurrentFinacialPeriod.financialPeriodDA;
    }

}
