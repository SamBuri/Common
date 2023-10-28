/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.util.List;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import javax.persistence.OneToMany;

@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class UserRole extends DBEntity {

    @Id
    @NotNull(message = "The field: Role Name cannot be null")
    @Size(max = 20, message = "The field: Role Name size cannot be greater than 20")
    @Column(length = 20, updatable = false)
    private String roleName;
    @Size(max = 200, message = "The field: Description size cannot be greater than 200")
    @Column(length = 200)
    private String description;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userRole")
    private List<UserRoleDetail> userRoleDetails = new ArrayList<>();

    public UserRole() {
    }

    public UserRole(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;

    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<UserRoleDetail> getUserRoleDetails() {
        return userRoleDetails;
    }

    public void setUserRoleDetails(List<UserRoleDetail> userRoleDetails) {
        this.userRoleDetails = userRoleDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserRole)) {
            return false;
        }

        UserRole userRole = (UserRole) o;

        return this.getId().equals(userRole.getId());
    }

    @Override
    public int hashCode() {
        return this.roleName.hashCode();

    }

    @Override
    public Object getId() {
        return this.roleName;
    }

    @Override
    public String getDisplayKey() {
        return this.roleName;
    }

}
