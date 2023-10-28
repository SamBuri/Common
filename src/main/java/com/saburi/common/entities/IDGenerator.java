/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
public class IDGenerator extends DBEntity {

    @Id
    @Column(name = "objectName", updatable = false, length = 100, nullable = false)
    private String objectName;
    @Column(name = "padChar", length = 1)
    private String padChar;
    private int padLength;
    @Column(name = "seperator", length = 1)
    private String seperator;
    private int seporatorPosition;
    @Column(name = "startWith", length = 10)
    private String startWith;

    public IDGenerator() {
    }

    public IDGenerator(String objectName, String padChar, int padLength, String seperator, int seporatorPosition, String startWith) {
        this.objectName = objectName;
        this.padChar = padChar;
        this.padLength = padLength;
        this.seperator = seperator;
        this.seporatorPosition = seporatorPosition;
        this.startWith = startWith;

    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getPadChar() {
        return padChar;
    }

    public void setPadChar(String padChar) {
        this.padChar = padChar;
    }

    public int getPadLength() {
        return padLength;
    }

    public void setPadLength(int padLength) {
        this.padLength = padLength;
    }

    public String getSeperator() {
        return seperator;
    }

    public void setSeperator(String seperator) {
        this.seperator = seperator;
    }

    public int getSeporatorPosition() {
        return seporatorPosition;
    }

    public void setSeporatorPosition(int seporatorPosition) {
        this.seporatorPosition = seporatorPosition;
    }

    public String getStartWith() {
        return startWith;
    }

    public void setStartWith(String startWith) {
        this.startWith = startWith;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IDGenerator)) {
            return false;
        }

        IDGenerator iDGenerator = (IDGenerator) o;

        return this.getId().equals(iDGenerator.getId());
    }

    @Override
    public int hashCode() {
        return this.objectName.hashCode();

    }

    @Override
    public Object getId() {
        return this.objectName;
    }

    @Override
    public String getDisplayKey() {
        return this.objectName;
    }

}
