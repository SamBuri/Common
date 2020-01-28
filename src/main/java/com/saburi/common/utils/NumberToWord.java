/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import java.util.Scanner;

/**
 *
 * @author ClinicMaster13
 */
public class NumberToWord {

    private static final String ZERO = "zero";
    private static final String[] ONE_TO_NINE = {
            "one", "two", "three", "four", "five", "six", "seven", "height", "nine"
    };

    private static final String[] TEN_TO_NINTEEN = {
            "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] DOZENS = {
            "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    public static String solution(int number) {
        if(number == 0)
            return ZERO;
       
        return toWords(number).trim();
    }

    public static String toWords(double number) {
        if(number >= 1000000000) {
            return toWords(number / 1000000000) + " billion " + toWords(number % 1000000000);
        }
        else if(number >= 1000000) {
            return toWords(number / 1000000) + " million " + toWords(number % 1000000);
        }
        else if(number >= 1000) {
            return toWords(number / 1000) + " thousand " + toWords(number % 1000);
        }
        else if(number >= 100) {
            return toWords(number / 100) + " hundred " + toWords(number % 100);
        }
        int n = (int)number;
        return generate1To99(n);
    }

    private static String generate1To99(int number) {
        if (number == 0)
            return "";

        if (number <= 9)
            return ONE_TO_NINE[number - 1];
        else if (number <= 19)
            return TEN_TO_NINTEEN[number % 10];
        else {
            return DOZENS[number / 10 - 1] + " " + generate1To99(number % 10);
        }
    }
    
    public static void main(String[] args) {
         Scanner scanner =  new Scanner(System.in);
        do{
       
        System.err.println("Please enter the number: ");
        long number = scanner.nextLong();
        System.out.println(toWords(number));
        }while (true);
    }
}
