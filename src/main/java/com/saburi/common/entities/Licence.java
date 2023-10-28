package com.saburi.common.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Lob;

@Entity
public class Licence extends DBEntity {

    @Id
    @Column(name = "lC1", nullable = false, length = 200)
    private String lC1;
    @Lob
    @Column(name = "lC2")
    private String lC2;
    @Lob
    @Column(name = "lC3")
    private String lC3;
    @Lob
    @Column(name = "lC4")
    private String lC4;
    @Lob
    @Column(name = "lC5")
    private String lC5;
    @Column(name = "lC6")
    @Lob
    private String lC6;
    @Column(name = "lC7")
    @Lob
    private String lC7;
    @Lob
    @Column(name = "lC8")
    private String lC8;

    public Licence() {
    }

    public Licence(String lC1) {
        this.lC1 = lC1;
    }

    public Licence(String lC1, String lC2, String lC3, String lC4, String lC5, String lC6, String lC7, String lC8) {
        this.lC1 = lC1;
        this.id = lC1;
        this.lC2 = lC2;
        this.lC3 = lC3;
        this.lC4 = lC4;
        this.lC5 = lC5;
        this.lC6 = lC6;
        this.lC7 = lC7;
        this.lC8 = lC8;

    }

    public void setLC1(String lC1) {
        this.lC1 = lC1;
    }

    public void setLC2(String lC2) {
        this.lC2 = lC2;
    }

    public void setLC3(String lC3) {
        this.lC3 = lC3;
    }

    public void setLC4(String lC4) {
        this.lC4 = lC4;
    }

    public void setLC5(String lC5) {
        this.lC5 = lC5;
    }

    public void setLC6(String lC6) {
        this.lC6 = lC6;
    }

    public void setLC7(String lC7) {
        this.lC7 = lC7;
    }

    public void setLC8(String lC8) {
        this.lC8 = lC8;
    }

    public String getLC1() {
        return this.lC1;
    }

    public String getLC2() {
        return this.lC2;
    }

    public String getLC3() {
        return this.lC3;
    }

    public String getLC4() {
        return this.lC4;
    }

    public String getLC5() {
        return this.lC5;
    }

    public String getLC6() {
        return this.lC6;
    }

    public String getLC7() {
        return this.lC7;
    }

    public String getLC8() {
        return this.lC8;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Licence)) {
            return false;
        }

        Licence licence = (Licence) o;

        return this.getId().equals(licence.getId());
    }

    @Override
    public int hashCode() {
        return this.lC2.hashCode();

    }

    @Override
    public Object getId() {
        return this.lC1;
    }

    @Override
    public String getDisplayKey() {
        return this.lC2;
    }

}
