/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import java.util.Random;

/**
 *
 * @author Forever
 */
public class SaburiEncryptor {

    private static final String[] TOKENS = {"`", "~", "1", "!", "2", "@", "3", "#", "4", "$", "5", "%", "6", "^", "7", "&", "8", "*", "9", "(",
        "0", ")", "-", "_", "=", "+", " ", "q", "Q", "w", "W", "e", "E", "r", "R", "t", "T", "y", "Y", "u", "U",
        "i", "I", "`", "o", "O", "p", "P", "[", "{", "]", "}", "\\", "|", "~", "a", "A", "s", "S", "d", "D", "f", "F",
        "g", "G", "h", "!", "H", "j", "J", "k", "K", "l", "L", ";", ":", "'", "\"", "z", "Z", "x", "X", "c",
        "C", "v", "V", "b", "B", "n", "N", "m", "M", ",", "<", ".", ">", "/"};

    private static final String[] TOKEN_ENCRYPTOR = {"AB", "aa", "aA", "bB", "BC", "bb", "Ac",
        "ab", "ac",
        "ad", "Ba", "Bd", "AC", "23", "@M", "DC", "Aa", "aB", "30", "dc",
        "yt", "cc", "XF", "ax", "cX", "cF", "fX", "uH", "io", "NB", "hV", "dx", "HG",
        "81", "7c", "AA", "YY",
        "JG", "Uz", "8U", "bG", "p1",
        "RR", "6=", "fd", "jT", "sA", "J2", "@z", "5C", "KC", "L7", "oz", "33", "78", "ZZ", "K1", "77",
        "An", "na", "ma", "sa", "as", "br",
        "SM", "AM", "AN", "K2", "LD", "1X", "Hn", "H6", "H%", "mo", "Ho", "re", "ER", "GO", "DG", "&K", "0y",
        "6G", "cJ",
        "vH", "u$", "8H", "6R", "%#", "Yb", "dn", "Dn", "ND", "==", "BM", "ST", "st", "##"};

    private static final String[] LETTER_SEQUENCE = {"DF", "UC", "ae", "tI", "0L", "TB", "Om", "kc", "sa", "SX",
        "Hk", "pc",
        "Fe", "MO", "&3", "1V", "PX", "MC", "NC", "nx", "jl", "@&", "md", "AX", "Jz", "2w", "iN", "8Q", "VS",
        "MP", "@3", "Lv", "QH", "HG", "rA", "tD", "tu", "kh", "KM", "YN", "uX", "U6",
        "iS", "Yt", "14", "ZC", "HO", "YU", "P7", "08", "AZ", "TE", "OP", "I8", "!?", "bb", "KW", "KF",
        "AS", "AM", "xs", "55", "LD", "DA", "00", "GG", "JM", "!7", "zx", "jQ", "JN", "AS", "42", "IL", "LO", "VO",
        "bB", "IG", "GL", "AL", "LU", "xK", "MN", "cO", "IS", "TR", "ND", "VM", "JH", "IO", "NG", "Ja", "Mk", "op",
        "iF", "JE", "SL", "PO"
    };

    private static final Random RAND = new Random();

  
    private static String setLetSequence() {
        int sno = 2;
        String letterSequence = "";

        for (int i = 0; i < sno; i++) {
            int x = RAND.nextInt(LETTER_SEQUENCE.length - 1);
            String name = LETTER_SEQUENCE[x];
            letterSequence += name;
        }

        return letterSequence;

    }

    private static String appendLetterSequence(String string) {
        String appendedTo = "";

        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            for (int ti = 0; ti < TOKENS.length; ti++) {
                char tc = TOKENS[ti].charAt(0);
                if (c == tc) {
                    appendedTo += TOKEN_ENCRYPTOR[ti] + "" + setLetSequence();
                }
            }

        }
        return appendedTo;
    }

    public static String encrypt(String string) {
        return setLetSequence().concat(appendLetterSequence(string));
    }

    public static String decrypt(String string) {
    
        String word = "";
        int startPosition=4;
        for (int i = 4; i < string.length() - 4; i++) {
           
            if(i==startPosition){
                int endPosition=(i+2);
                 String sam=string.substring(i, endPosition);
                 for(int j=0; j<TOKEN_ENCRYPTOR.length; j++){
                  String s= TOKEN_ENCRYPTOR[j];
//                  if(TOKENS[j].equals("i")) JOptionPane.showMessageDialog(null, TOKEN_ENCRYPTOR[j]);
                  if(s.equals(sam)){
                      word+=TOKENS[j];
                  }
                 }
                
                 startPosition= endPosition+4;
               
                 
            }
            
        }
        
        return word;
    }
    
   
}
