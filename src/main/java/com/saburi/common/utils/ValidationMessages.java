/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

/**
 *
 * @author Sam Buriima
 */
public class ValidationMessages {

    public static String uniqueMessage(String validatingName, String caption, Object value) {
        return "There is already an exisiting " + validatingName + " for the"
                + caption + ": " + value;
    }

    public static String uniqueMessage(String validatingName, String caption, Object value, String caption1, Object value1) {
        return "There is already an exisiting " + validatingName + " for the"
                + caption + ": " + value + " and " + caption1 + ": " + value1;
    }

}
