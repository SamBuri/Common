/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.entities;

import com.saburi.common.utils.CurrentUser;
import com.saburi.common.utils.Utilities;
import org.hibernate.envers.RevisionListener;

/**
 *
 * @author CLINICMASTER13
 */
public class EntityRevisionListener implements RevisionListener{

    @Override
    public void newRevision(Object o) {
     AppRevisionEntity revisionEntity =  (AppRevisionEntity)o;
     revisionEntity.setClienMachine(Utilities.getHostName());
     revisionEntity.setUserID(CurrentUser.getLoginID());
    }

    
    
}
