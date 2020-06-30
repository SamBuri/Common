/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.dbaccess;

import com.saburi.common.utils.DBConnection;
import com.saburi.common.utils.Navigation;
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
    private static final Logger LOG = LogManager.getLogger();

    static {
        LOG.info("Initialising Persistence Unit");
        try {
            Map<String, String> properties = new HashMap<>();
            properties.put("javax.persistence.jdbc.url", "jdbc:mysql://" + DBConnection.getCurrentDBConnection().getUrl() + "/" + DBConnection.getCurrentDBConnection().getDbName());
            properties.put("javax.persistence.jdbc.user", DBConnection.getCurrentDBConnection().getUsername());
            properties.put("javax.persistence.jdbc.password", DBConnection.getCurrentDBConnection().getPassword());
            LOG.info("Finished Initialising Persistence Unit");
            entityManagerFactory = Persistence.createEntityManagerFactory(Navigation.persistenceUnit, properties);
            LOG.info("Finishing creating persitence unit");
        } catch (Exception e) {
            try {
                LOG.error(e, e);
                throw new Exception("Failed to connect to the database. Please check if your computer is connected to the network" + e.getLocalizedMessage());
            } catch (Exception ex) {
                LOG.error(ex, ex);
            }

        }

    }

    public static EntityManagerFactory getInstance() {
        return entityManagerFactory;
    }

}
