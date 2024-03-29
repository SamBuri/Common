/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import com.saburi.common.utils.CommonEnums.DataTypes;
import javax.persistence.Cacheable;
import javax.persistence.Enumerated;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Options extends DBEntity {

    @Id
    @Column(name = "optionID", updatable = false, length = 20, nullable = false)
    private String optionID;
    @Column(name = "optionName", length = 100, nullable = false)
    private String optionName;
    @Enumerated
    @Column(name = "dataType")
    private DataTypes dataType;
    @Column(name = "optionValue", length = 100)
    private String optionValue;

    public Options() {
    }

    public Options(String optionID, String optionName, DataTypes dataType, String optionValue) {
        this.optionID = optionID;
        this.optionName = optionName;
        this.dataType = dataType;
        this.optionValue = optionValue;

    }

    public String getOptionID() {
        return optionID;
    }

    public void setOptionID(String optionID) {
        this.optionID = optionID;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public DataTypes getDataType() {
        return dataType;
    }

    public void setDataType(DataTypes dataType) {
        this.dataType = dataType;
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Options)) {
            return false;
        }

        Options options = (Options) o;

        return this.getId().equals(options.getId());
    }

    @Override
    public int hashCode() {
        return this.optionID.hashCode();

    }

    @Override
    public Object getId() {
        return this.optionID;
    }

    @Override
    public String getDisplayKey() {
        return this.optionName;
    }

}
