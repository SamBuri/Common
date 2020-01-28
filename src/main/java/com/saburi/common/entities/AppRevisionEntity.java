/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.entities;


import javax.persistence.Entity;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

/**
 *
 * @author CLINICMASTER13
 */
@Entity
@RevisionEntity(EntityRevisionListener.class)
public class AppRevisionEntity extends DefaultRevisionEntity {

    private String userID;
    private String clienMachine;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getClienMachine() {
        return clienMachine;
    }

    public void setClienMachine(String clienMachine) {
        this.clienMachine = clienMachine;
    }
    
    

}
