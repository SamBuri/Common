/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.main;

import com.saburi.common.utils.FXUIUtils;

/**
 *
 * @author Hp
 */
public class Main {
    public static void main(String[] args) {
        try {
            App.main(args);
        } catch (Exception e) {
            FXUIUtils.errorMessage(e);
        }
    }
}
