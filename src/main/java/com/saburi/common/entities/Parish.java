/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.entities;

import java.util.Objects;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)

@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"subCountyID", "parishName"}, name = "uqSubCountyIDparishNameParish"))
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Parish extends DBEntity {

    @Column(updatable = false)
    private int idHelper;
    @NotNull(message = "The field: Sub County cannot be null")
    @OneToOne
    @JoinColumn(name = "subCountyID", foreignKey = @ForeignKey(name = "fkSubCountyIDParish"))
    private SubCounty subCounty;
    @Id
    @NotNull(message = "The field: Parish ID cannot be null")
    @Size(max = 20, message = "The field: Parish ID size cannot be greater than 20")
    @Column(length = 20, updatable = false)
    private String parishID;
    @Size(max = 100, message = "The field: Parish Name size cannot be greater than 100")
    @NotNull(message = "The field: Parish Name cannot be null")
    @Column(length = 100)
    private String parishName;

    public Parish() {
    }

    public Parish(int idHelper, SubCounty subCounty, String parishID, String parishName) {
        this.idHelper = idHelper;
        this.subCounty = subCounty;
        this.parishID = parishID;
        this.parishName = parishName;

    }

    public int getIdHelper() {
        return idHelper;
    }

    public void setIdHelper(int idHelper) {
        this.idHelper = idHelper;
    }

    public SubCounty getSubCounty() {
        return subCounty;
    }

    public void setSubCounty(SubCounty subCounty) {
        this.subCounty = subCounty;
    }

    public String getParishID() {
        return parishID;
    }

    public void setParishID(String parishID) {
        this.parishID = parishID;
    }

    public String getParishName() {
        return parishName;
    }

    public void setParishName(String parishName) {
        this.parishName = parishName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Parish)) {
            return false;
        }

        Parish parish = (Parish) o;

        return this.getId().equals(parish.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.parishID);

    }

    @Override
    public Object getId() {
        return this.parishID;
    }

    @Override
    public String getDisplayKey() {
        return this.parishName;
    }

}
