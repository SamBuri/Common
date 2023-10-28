/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.saburi.common.utils;

/**
 *
 * @author samburiima
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvColumnSorter implements Comparator<Inner> {
    public static String LINE_SEPARATOR = "\n";
    public static String WORD_SEPARATOR =",";
    

    public static void main(String[] args) {

        String csvData = "Beth,Charles,Danielle,Adam,Eric\n"
                + "17945,10091,10088,3907,10132\n"
                + "2,12,13,48,11";

        String sortedV = sortCSV(csvData);

        System.out.println(sortedV);
    }

    public static String sortCSV(String s) {

        String[] ss = s.split(LINE_SEPARATOR);
        String headers[] = ss[0].split(",");
        String[] arrSorted = Arrays.copyOf(headers, headers.length);
        Arrays.sort(arrSorted);
//        List sortedHeaders = Arrays.asList(arrSorted);
        Map<Integer, Integer> map = new HashMap<>();
        int length = headers.length;
        for (int i = 0; i < length; i++) {
            String value = headers[i];
            int index = getIndex(arrSorted, value);
            map.put(i, index);

        }


          String result = "";
        for (String row : ss) {
            List<Inner> ls = new ArrayList<>();
           
            String[] arr = row.split(WORD_SEPARATOR);
            for (int i = 0; i < arr.length; i++) {
                String sortedValue = arr[i];
                Inner inner = new Inner(sortedValue, map.get(i));
                ls.add(inner);
            }

            ls.sort(new CsvColumnSorter());
            result+=string(ls);
            result+="\n";

        }

     

        return result;

    }
    
    private static String string(List<Inner> list){
        
        int length =list.size();
        String s = "";
        for(int i=0; i<length; i++){
           s+=list.get(i).value();
           if(i<length-1){
               s+=WORD_SEPARATOR;
           }
        }
        
        return s;
    }
    
   

    @Override
    public int compare(Inner o1, Inner o2) {
        return o1.pos() - o2.pos();
    }
    
    public static int getIndex(String [] a, String s){
        for(int i=0; i<a.length; i++){
             if(a[i].equals(s)) return i;
        }
        
        return -1;
    }

}

record Inner(String value, int pos) {
    
    @Override
    public String toString(){
        return value;
    }

}
