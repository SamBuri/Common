/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.dbaccess;

import com.saburi.common.entities.AccessObject;
import com.saburi.common.entities.UserRole;
import javafx.beans.property.*;
import java.util.ArrayList;
import com.saburi.common.entities.AppRevisionEntity;
import java.util.List;
import com.saburi.common.utils.SearchColumn;
import com.saburi.common.utils.SearchColumn.SearchDataTypes;
import org.hibernate.envers.RevisionType;
import com.saburi.common.entities.UserRoleDetail;
import com.saburi.common.utils.CommonEnums;

public class UserRoleDA extends DBAccess {

    private UserRole userRole = new UserRole();
    private final SimpleStringProperty roleName = new SimpleStringProperty(this, "roleName");
    private final SimpleStringProperty description = new SimpleStringProperty(this, "description");
    private List<UserRoleDetail> userRoleDetails = new ArrayList<>();

    public UserRoleDA() {
        createSearchColumns();
    }

    public UserRoleDA(String persistenceUnit) {
        super(persistenceUnit);
    }

    public UserRoleDA(UserRole userRole) {
        this.userRole = userRole;
        initialseProprties();
        createSearchColumns();
    }

    public UserRoleDA(String persistenceUnit, UserRole userRole) {
        super(persistenceUnit);
        this.userRole = userRole;
        initialseProprties();
        createSearchColumns();
    }

    public UserRoleDA(String roleName, String description) {
        this.userRole = new UserRole(roleName, description);
        initialseProprties();
        createSearchColumns();
    }

    public UserRoleDA(String persistenceUnit, String roleName, String description) {
        super(persistenceUnit);
        this.userRole = new UserRole(roleName, description);
        initialseProprties();
        createSearchColumns();
    }

    public String getRoleName() {
        return roleName.get();
    }

    public void setRoleName(String roleName) {
        userRole.setRoleName(roleName);
        this.roleName.set(roleName);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        userRole.setDescription(description);
        this.description.set(description);
    }

    public List<UserRoleDetail> getUserRoleDetails() {
        return userRoleDetails;
    }

    public List<UserRoleDetailDA> getUserRoleDetailsDAs() {
        return UserRoleDetailDA.getUserRoleDetailDAs(userRole.getUserRoleDetails());
    }

    public void setUserRoleDetails(List<UserRoleDetail> userRoleDetails) {
        userRole.setUserRoleDetails(userRoleDetails);
        this.userRoleDetails = userRoleDetails;
    }

    public void setUserRoleDetailsDAs(List<UserRoleDetailDA> userRoleDetailDAs) {
        this.userRole.setUserRoleDetails(UserRoleDetailDA.getUserRoleDetailList(userRoleDetailDAs));

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRoleDA)) {
            return false;
        }

        UserRoleDA userRoleDA = (UserRoleDA) o;

        if (userRoleDA.getDBEntity() == null || this.getDBEntity() == null) {
            return false;
        }
        return this.getId().equals(userRoleDA.getId());
    }

    @Override
    public int hashCode() {
        return userRole.getId().hashCode();
    }

    private void initialseProprties() {
        this.dBEntity = userRole;
        this.roleName.set(userRole.getRoleName());
        this.description.set(userRole.getDescription());
        initCommonProprties();
    }

    private void createSearchColumns() {
        this.searchColumns.add(new SearchColumn("roleName", "Role Name", this.roleName.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("description", "Description", this.description.get(), SearchDataTypes.STRING));
        this.searchColumns.addAll(this.getDefaultSearchColumns());
    }

    @Override
    public List<SearchColumn> getSearchColumns() {
        return this.searchColumns;
    }

    @Override
    public Object getId() {
        return this.userRole.getId();
    }

    @Override
    public String getDisplayKey() {
        return this.userRole.getDisplayKey();
    }

    public static List<UserRoleDA> getUserRoleDAs(List<UserRole> userRoles) {
        List<UserRoleDA> list = new ArrayList<>();
        userRoles.forEach((userRole) -> {
            list.add(new UserRoleDA(userRole));
        });
        return list;
    }

    public static List<UserRole> getUserRoleList(List<UserRoleDA> userRoleDAs) {
        List<UserRole> userRoles = new ArrayList<>();
        userRoleDAs.forEach(a -> userRoles.add(a.userRole));
        return userRoles;
    }

    public boolean save() throws Exception {
        return super.persist(this.userRole);

    }

    public boolean update() throws Exception {
        userRole.getUserRoleDetails().forEach(e->System.out.println(e.getUserRoleDetailID()));
        return super.merge(this.userRole);

    }

    public boolean delete() {
        return super.remove(this.userRole);

    }

    public UserRole getUserRole(String roleName) {
        return (UserRole) super.findJoin(UserRole.class, roleName, "userRoleDetails");
    }

    public UserRole getUserRole() {
        return this.userRole;
    }

    public List<UserRole> getUserRoles() {
        return super.find(UserRole.class);
    }

    @Override
    public List<DBAccess> get() {
        List<DBAccess> list = new ArrayList<>();
        selectQuery(UserRole.class).forEach(o -> list.add(new UserRoleDA((UserRole) o)));
        if (entityManager != null) {
            entityManager.close();
        }
        return list;
    }

    public UserRoleDA get(String roleName) throws Exception {
        UserRole oUserRole = getUserRole(roleName);
        if (oUserRole == null) {
            throw new Exception("No Record with id: " + roleName);
        }
        return new UserRoleDA(oUserRole);
    }

    public List<UserRoleDA> get(String columName, Object value) {
        List<UserRoleDA> list = new ArrayList<>();
        super.selectQuery(UserRole.class, columName, value).forEach(da -> list.add(new UserRoleDA((UserRole) da)));
        if (entityManager != null) {
            entityManager.close();
        }
        return list;
    }

    public List<UserRoleDA> toDaList(List<UserRole> userRoles) {
        List<UserRoleDA> userRoleDAs = new ArrayList<>();
        userRoles.forEach(s -> userRoleDAs.add(new UserRoleDA(s)));
        return userRoleDAs;
    }

    public List<DBAccess> toDBAccessList(List<UserRole> userRoles) {
        List<DBAccess> dbAccesses = new ArrayList<>();
        userRoles.forEach(s -> dbAccesses.add(new UserRoleDA(s)));
        return dbAccesses;
    }

    public int getMax(String columnName) {
        return super.getMax(UserRole.class, columnName);
    }

    public int getMax(String columnName, String comparatorColumn, Object comparatorVaue) {
        return super.getMax(UserRole.class, columnName, comparatorColumn, comparatorVaue);
    }

    public List<UserRole> getUserRoles(String columName, Object value) {
        return super.find(UserRole.class, columName, value);
    }

    @Override
    public List<DBAccess> getRevisions() {
        try {

            List<Object[]> list = getEntityRevisions(UserRole.class);
            List<DBAccess> dBAccesses = new ArrayList<>();
            list.forEach(e -> {

                UserRoleDA userRoleDA = new UserRoleDA((UserRole) e[0]);
                userRoleDA.revisionEntity = (AppRevisionEntity) e[1];
                userRoleDA.oRevisionType = (RevisionType) e[2];
                userRoleDA.initRevProprties();
                userRoleDA.searchColumns.addAll(userRoleDA.getRevSearchColumns());
                dBAccesses.add(userRoleDA);

            });

            return dBAccesses;
        } catch (Exception e) {
            throw e;
        } finally {
            if (entityManager == null) {
                entityManager.close();
            }
        }

    }
    
    public void createDefaultUserRole() throws Exception{
        AccessObjectDA accessObjectDA =  new AccessObjectDA();
        
        if(this.getUserRoles().size()<1){
            UserRole lUserRole = new UserRole("Administrator", "Inital Role");
            List<UserRoleDetail> lUserRoleDetails = List.of(new UserRoleDetail(lUserRole, 
                    accessObjectDA.getAccessObject("UserRole"), true, true, true, true, true),
                    new UserRoleDetail(lUserRole, 
                    accessObjectDA.getAccessObject("UserRoleDetail"), true, true, true, true, true),
                    new UserRoleDetail(lUserRole, 
                    accessObjectDA.getAccessObject("Licence"), true, true, true, true, true)
                    );
            lUserRole.setUserRoleDetails(lUserRoleDetails);
            
            this.persist(lUserRole);
        }
    }

}
