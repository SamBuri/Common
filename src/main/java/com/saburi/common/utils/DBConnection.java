/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author CLINICMASTER13
 */
public class DBConnection {
    private String url;
    private String dbName;
    private String username;
    private String password;
    private  final String fileName ="./connection.xml";
    private static DBConnection currentDBConnection;
    public DBConnection() {
    }


    public DBConnection(String url, String dbName, String username, String password) {
        this.url = url;
        this.dbName = dbName;
        this.username = username;
        this.password = password;
    }
    
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static DBConnection getCurrentDBConnection() {
        return currentDBConnection;
    }

    public static void setCurrentDBConnection(DBConnection currentDBConnection) {
        DBConnection.currentDBConnection = currentDBConnection;
    }
    
    
    
    public void xmlEncode() throws FileNotFoundException, IOException{
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName); XMLEncoder encoder = new XMLEncoder(fileOutputStream)) {
            encoder.writeObject(this);
        }
    }
    
    public DBConnection xmlDecode() throws FileNotFoundException, IOException{
        try (FileInputStream fileInputStream = new FileInputStream(fileName); 
                XMLDecoder decoder = new XMLDecoder(fileInputStream)) {
            return (DBConnection) decoder.readObject();
        }
    }
    
    

    
    
    
}
