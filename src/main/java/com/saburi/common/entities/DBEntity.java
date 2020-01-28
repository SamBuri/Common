/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.entities;

import com.saburi.common.utils.CurrentUser;
import static com.saburi.common.utils.Utilities.getHostName;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author Forever
 */
@MappedSuperclass
//@Audited
public abstract class DBEntity implements Serializable {

//    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", updatable = false)
    @Column(updatable = false)
    private final LocalDateTime recordDateTime = LocalDateTime.now();
    ;
    protected String userID;
    protected String userFullName;
    private final String clientMachine = getHostName();
//    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdateDateTime = LocalDateTime.now();
    protected String branch = "Default";
    protected transient Object id;
    protected transient String Dislaykey;

    public DBEntity() {
        if (CurrentUser.getAppUser() != null) {
            this.userID = CurrentUser.getAppUser().getLoginID();
            this.userFullName = CurrentUser.getFullName();

        }

    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public LocalDateTime getLastUpdateDateTime() {
        return lastUpdateDateTime;
    }

    public void setLastUpdateDateTime(LocalDateTime lastUpdateDateTime) {
        this.lastUpdateDateTime = lastUpdateDateTime;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDislaykey() {
        return Dislaykey;
    }

    public void setDislaykey(String Dislaykey) {
        this.Dislaykey = Dislaykey;
    }

    public LocalDateTime getRecordDateTime() {
        return recordDateTime;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public String getClientMachine() {
        return clientMachine;
    }

    public abstract Object getId();

    public abstract String getDisplayKey();

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

}
