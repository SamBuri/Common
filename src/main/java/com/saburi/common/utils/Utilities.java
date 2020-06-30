/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import org.hibernate.Session;

/**
 *
 * @author Forever
 */
public class Utilities {

    public static enum FormMode {
        Save, Update, Print, Preview
    }

    public static String getHostName() {
        try {
            return Inet4Address.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    //-------------------------------PRIMITIVE DATA TYPES
    public static boolean isInteger(Object o) {
        if (o == null) {

            return false;
        } else {
            try {
                defortInteger(o.toString());
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
    }

    public static String listToString(List list, String seperator) {
        String string = "";

        for (int i = 0; i < list.size(); i++) {
            string += list.get(i);
            if (i < list.size() - 1) {
                string += seperator;
            }
        }
        return string;
    }

    public static String removeCharactor(String string, char toExclude) {

        String toReturn = "";
        if (string == null) {
            return "";
        } else {

            for (int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);

                if (c != toExclude) {
                    toReturn += c;
                }
            }
            if (toReturn.equalsIgnoreCase("null")) {
                return null;
            } else {
                return toReturn;
            }
        }
    }

    public static int getInteger(Object o) {
        if (isInteger(o)) {
            return Integer.parseInt(o.toString());
        } else {
            return 0;
        }
    }

    public static float getFloat(Object o) {
        if (isFloat(o)) {
            return Float.parseFloat(o.toString());
        } else {
            return 0;
        }
    }

    public static double getDouble(Object o) {
        if (isDouble(o)) {
            return Double.parseDouble(o.toString());
        } else {
            return 0;
        }
    }

    public static boolean isFloat(Object o) {
        if (o == null) {
            return false;
        } else {
            try {
                Float.parseFloat(String.valueOf(defortNumber(o.toString())));
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public static boolean isDouble(Object o) {
        if (o == null) {
            return false;
        } else {
            try {
                Double.parseDouble(String.valueOf(defortNumber(o.toString())));
                return true;

            } catch (Exception e) {
                return false;
            }
        }
    }

    public static Date getDate(Object o) throws ParseException {
        DateFormat format = new SimpleDateFormat("YYY-MM-dd");
        Date date = (Date) format.parse(o.toString());
        return date;
    }

    public static boolean isDate(Object o) {
        if (o == null) {
            return false;
        } else {
            try {
                Date date = (Date) (o);
                return true;

            } catch (Exception e) {

                return false;
            }
        }
    }

    public static boolean isBoolean(Object o) {
        if (o == null) {
            return false;
        } else {

            String s = o.toString();
            return s.equalsIgnoreCase("true") || s.equalsIgnoreCase("false");
        }
    }

//______________________________________________________________________________________________
    public static float roundHalfUp(double f, int no) {
        return round(f, no, BigDecimal.ROUND_HALF_UP);

    }

    public static float round(double f, int no, int roundMode) {
        try {
            BigDecimal bd = new BigDecimal("" + f);
            bd = bd.setScale(no, roundMode);
            return bd.floatValue();
        } catch (Exception ex) {
            return 0;
        }
    }

    public static float getSum(List list) {
        float total = 0;

        for (Object o : list) {
            total += getFloat(o);
        }
        return total;
    }

    public static float getSum(Object[] list) {
        float total = 0;

        for (Object o : list) {
            total += getFloat(o);
        }
        return total;
    }

    public static float getAverage(List list) {
        float total = 0;

        for (Object o : list) {
            total += getFloat(o);
        }

        return roundHalfUp(getFloat(total) / list.size(), 2);
    }

    public static String formatNumber(double number) {
        DecimalFormat formatter = new DecimalFormat("#,###.00");
        return formatter.format(number);
    }

    public static String formatNumber(float number) {
        DecimalFormat formatter = new DecimalFormat("#,###.0");
        return formatter.format(number);
    }

    public static String formatNumber(int number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

    public static String formatInteger(double number) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(number);
    }

    public static double defortNumber(String number) {

        return Double.parseDouble(number.replaceAll("(?<=\\d),(?=\\d)|\\$", ""));

    }

    public static float defortFloat(String number) {

        return Float.parseFloat(number.replaceAll("(?<=\\d),(?=\\d)|\\$", ""));

    }

    public static int defortInteger(String number) {

        return Integer.parseInt(number.replaceAll("(?<=\\d),(?=\\d)|\\$", ""));

    }

    public static double defortNumberOptional(String number) {
        try {
            return Double.parseDouble(number.replaceAll("(?<=\\d),(?=\\d)|\\$", ""));
        } catch (Exception e) {
            return 0;

        }
    }

    public static String cleanText(String text) {
        return text.replaceAll("(?<=\\d), (?=\\d)|\\$' '-_", "");
    }

    public static void AllowOnlyDigits(java.awt.event.KeyEvent evt, TextField field) {
        char c = evt.getKeyChar();
        if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
            evt.consume();
            Toolkit.getDefaultToolkit().beep();
        }
    }

    //FILES
    public static void makeDirectory(String dir) {

        File f = new File(dir);
        f.setExecutable(true, true);
        f.setWritable(true, true);
        f.setReadable(true, true);

        if (!f.exists()) {
            f.mkdirs();

        }

    }

    public static String readFileString(String fileName, String seperator) throws FileNotFoundException, IOException {
        String fileContent = "";
        BufferedReader reader = null;
        try {
            String line;

            reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {

                fileContent += line + "\n";
            }

            String[] contents = fileContent.trim().split(seperator);
            if (contents.length > 0) {
                fileContent = contents[0];
            }

        } catch (FileNotFoundException ex) {
            throw ex;
        } catch (IOException ex) {
            throw ex;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }

        return fileContent;
    }

    public static String readFileString(String fileName) throws FileNotFoundException, IOException {
        String fileContent = "";
        BufferedReader reader = null;
        try {
            String line;

            reader = new BufferedReader(new FileReader(fileName));
            while ((line = reader.readLine()) != null) {

                fileContent += line + "\n";
            }

        } catch (FileNotFoundException ex) {
            throw ex;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }

        return fileContent;
    }

    public static String[] readFile(String fileName, String seperator) throws FileNotFoundException, IOException {
        String fileContent = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) {

                fileContent += line + "\n";
            }

        } catch (FileNotFoundException ex) {
            throw ex;

        } catch (IOException ex) {
            throw ex;
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                throw ex;
            }
        }

        return fileContent.trim().split(seperator);
    }

    public static void writeFile(String fileName, Object[] value) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (Object s : value) {
            writer.append(s.toString());
            writer.newLine();

        }
    }

    public static void writeFile(String fileName, String content) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        writer.write(content);
        writer.newLine();

        writer.close();

    }

    public static void writeFileAppend(String fileName, String content) throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.append(content);
        writer.newLine();

        writer.close();

    }

    public static void openFile(String fileName) throws IOException {

        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + fileName);

    }

    //FILES
    public static String getNullString(String string) {

        if (null != String.valueOf(string)) {
            return string;
        } else {
            return "";
        }

    }

    static ArrayList<Method> findGettersSetters(Class<?> c) {
        ArrayList<Method> list = new ArrayList();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (isGetter(method) || isSetter(method)) {
                list.add(method);
            }
        }
        return list;
    }

    public static boolean isGetter(Method method) {
        if (Modifier.isPublic(method.getModifiers())
                && method.getParameterTypes().length == 0) {
            if (method.getName().matches("^get[A-Z].*")
                    && !method.getReturnType().equals(void.class)) {
                return true;
            }
            if (method.getName().matches("^is[A-Z].*")
                    && method.getReturnType().equals(boolean.class)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSetter(Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(void.class)
                && method.getParameterTypes().length == 1
                && method.getName().matches("^set[A-Z].*");
    }

    public static void restart(Class mainClass) throws URISyntaxException, IOException {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";

        final File currentJar = new File(mainClass.getProtectionDomain().getCodeSource().getLocation().toURI());
        if (currentJar.getName().endsWith(".jar")) {
            return;
        }
        final List<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        final ProcessBuilder builder = new ProcessBuilder(command);
        builder.start();
        System.exit(0);

    }

//    Read csv
    public static List<List> readCSV(String fileName, String seperator) throws FileNotFoundException, IOException {
        List<List> bigList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            List innerList = new ArrayList();
            String[] values = line.split(seperator);
//                display(values);
            innerList.addAll(Arrays.asList(values));
            bigList.add(innerList);
        }

        reader.close();

        return bigList;
    }

    public static boolean exist(Session session, String className, String field, Object value) {
        boolean exist = false;

        List list = session.createQuery("Select " + field + " from " + className + " where " + field + "= '" + value + "'").list();

        if (list.size() > 0) {
            exist = true;
        }

        return exist;

    }

    public static void restartApplication(Class mainClass) throws URISyntaxException, IOException {
        final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
        final File currentJar = new File(mainClass.getProtectionDomain().getCodeSource().getLocation().toURI());

        /* is it a jar file? */
        if (!currentJar.getName().endsWith(".jar")) {
            return;
        }

        /* Build command: java -jar application.jar */
        final ArrayList<String> command = new ArrayList();
        command.add(javaBin);
        command.add("-jar");
        command.add(currentJar.getPath());

        final ProcessBuilder builder = new ProcessBuilder(command);
        try {
            builder.start();
        } catch (IOException ex) {
            throw ex;
        }
        System.exit(0);
    }

    public static List addIfNotExist(List list, Object o) {
        if (!list.contains(o)) {
            list.add(o);
        }
        return list;
    }

    public static boolean isNullOrEmpty(String string) {
        if (string == null) {
            return true;
        }
        return string.isEmpty() || string.equals("");
    }

    public static String toWords(double number) {
        return NumberToWord.toWords(number);

    }

    public static String formatDate(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));

    }

    public static String formatDateTime(LocalDateTime localDateTime) {

        return localDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm:ss a"));

    }

    public static String formatNullDateTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        return localDateTime.format(DateTimeFormatter.ofPattern("dd MMM yyyy hh:mm:ss a"));

    }

    public static String formatTime(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("hh:mm:ss a"));

    }

    public static String formatDate(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));

    }

    public static String formatTime(LocalDateTime localDateTime, String pattern) {
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));

    }

    public static String formatDate(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        return localDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    public static String formatNullDate(LocalDate localDate) {
        if (localDate == null) {
            return "";
        }
        return localDate.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
    }

    public static String formatDate(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));

    }

    public static String formatNullDateTime(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat("dd MMM yyyy hh:mm:ss a").format(date);

    }

    public static Image getImage(byte[] bytes, float width, float height) {
        return new Image(new ByteArrayInputStream(bytes), width, height, true, true);

    }

}
