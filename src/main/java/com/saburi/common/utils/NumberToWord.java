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
        "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"
    };

    private static final String[] TEN_TO_NINTEEN = {
        "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen"
    };

    private static final String[] DOZENS = {
        "ten", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"
    };

    public static String toWords(double number) {
        if (number == 0) {
            int length = ZERO.length();
            return ZERO.substring(0, 1).toUpperCase().concat(ZERO.substring(1, length));
        }

        String towords = toWordsNonZeroLower(number).trim();
        int length = towords.length();
        if (length > 0) {
            return towords.substring(0, 1).toUpperCase().concat(towords.substring(1, length));
        }
        return towords;
    }

    public static String toWordsLower(double number) {
        if (number == 0) {
            return ZERO;
        }

        return toWordsNonZeroLower(number).trim();
    }

    public static String toWordsNonZeroLower(double number) {
        if (number >= 1000000000) {
            return toWordsNonZeroLower(number / 1000000000) + " billion " + toWordsNonZeroLower(number % 1000000000);
        } else if (number >= 1000000) {
            return toWordsNonZeroLower(number / 1000000) + " million " + toWordsNonZeroLower(number % 1000000);
        } else if (number >= 1000) {
            return toWordsNonZeroLower(number / 1000) + " thousand " + toWordsNonZeroLower(number % 1000);
        } else if (number >= 100) {
            return toWordsNonZeroLower(number / 100) + " hundred " + toWordsNonZeroLower(number % 100);
        }

        int n = (int) number;
        return generate1To99(n);
    }

    private static String generate1To99(int number) {
        if (number == 0) {
            return "";
        }

        if (number <= 9) {
            return ONE_TO_NINE[number - 1];
        } else if (number <= 19) {
            return TEN_TO_NINTEEN[number % 10];
        } else {
            return DOZENS[number / 10 - 1] + " " + generate1To99(number % 10);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {

            System.err.println("Please enter the number: ");
            double number = scanner.nextDouble();
            System.out.println(toWords(number));
        } while (true);
    }
}
