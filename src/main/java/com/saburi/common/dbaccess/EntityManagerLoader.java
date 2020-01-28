/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.dbaccess;

import com.saburi.common.utils.DBConnection;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author CLINICMASTER13
 */
public class EntityManagerLoader {
    
     private static EntityManagerFactory entityManagerFactory;
    private static final Logger LOGGER = LogManager.getLogger();
   
    static {
        try {
            Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.url", "jdbc:mysql://"+DBConnection.getCurrentDBConnection().getUrl()+"/"+DBConnection.getCurrentDBConnection().getDbName());
        properties.put("javax.persistence.jdbc.user", DBConnection.getCurrentDBConnection().getUsername());
        properties.put("javax.persistence.jdbc.password", DBConnection.getCurrentDBConnection().getPassword());
      
             entityManagerFactory = Persistence.createEntityManagerFactory("com.saburi.mysql", properties);
        } catch (Exception e) {
            LOGGER.error(e,e);
            throw e;
           
        }
       
    }
    
      public static EntityManagerFactory getInstance() {
        return entityManagerFactory;
    }
    
}
