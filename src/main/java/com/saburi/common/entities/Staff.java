/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import javax.persistence.Lob;
import com.saburi.common.utils.CommonEnums.Gender;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;

@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class Staff extends DBEntity {

    @Column(updatable = false)
    private int idHelper;
    @Id
    @NotNull(message = "The field: Staff ID cannot be null\n")
    @Size(max = 20, message = "The field: Staff ID size cannot be greater than 20\n")
    @Column(length = 20, updatable = false)
    private String staffID;
    @Lob
    private byte[] photo;
    @Size(max = 20, message = "The field: First Name size cannot be greater than 20\n")
    @NotNull(message = "The field: First Name cannot be null")
    @Column(length = 20)
    private String firstName;
    @Size(max = 20, message = "The field: Last Name size cannot be greater than 20\n")
    @NotNull(message = "The field: Last Name cannot be null\n")
    @Column(length = 20)
    private String lastName;
    @Size(max = 20, message = "The field: Other Names size cannot be greater than 20\n")
    @Column(length = 20)
    private String otherNames;
    @Size(max = 100, message = "The field: Initials size cannot be greater than 100\n")
    @Column(length = 100)
    private String initials;
    @Enumerated
    private Gender gender;
    @Past(message = "Birth Date ${validateValue} must be in the past\n")
    private LocalDate birthDate;
    @PastOrPresent(message = "Join Date ${validateValue}: must be in the past or present\n")
    private LocalDate joinDate;
    @Size(max = 100, message = "The field: National ID size cannot be greater than 100\n")
    @Column(length = 100, unique = true)
    private String nationalID;
    @OneToOne
    @JoinColumn(name = "countryID", foreignKey = @ForeignKey(name = "fkCountryIDStaff"))
    private LookupData country;
    @OneToOne
    @JoinColumn(name = "villageID", foreignKey = @ForeignKey(name = "fkVillageIDStaff"))
    private Village village;
    @OneToOne
    @JoinColumn(name = "staffTitleID", foreignKey = @ForeignKey(name = "fkStaffTitleIDStaff"))
    private LookupData staffTitle;
    @OneToOne
    @JoinColumn(name = "qualificationID", foreignKey = @ForeignKey(name = "fkQualificationIDStaff"))
    private LookupData qualification;
    @OneToOne
    @JoinColumn(name = "departmentID", foreignKey = @ForeignKey(name = "fkDepartmentIDStaff"))
    private LookupData department;
    @Size(max = 100, message = "The field: Phone No size cannot be greater than 100")
    @Column(length = 100, unique = true)
    private String phoneNo;
    @Email(message = "Invaild Email Address: ${validateValue}")
    @Size(max = 100, message = "The field: Email Address  size cannot be greater than 100")
    @Column(length = 100, unique = true)
    private String emailAddress;
    @Size(max = 200, message = "The field: Physical Address size cannot be greater than 200")
    @Column(length = 200)
    private String physicalAddress;
    @Size(max = 200, message = "The field: Special Skills size cannot be greater than 200")
    @Column(length = 200)
    private String specialSkills;
    private boolean active;

    public Staff() {
    }

    public Staff(int idHelper, String staffID, byte[] photo, String firstName, String lastName, String otherNames, String initials, Gender gender, LocalDate birthDate, LocalDate joinDate, String nationalID, LookupData country, Village village, LookupData staffTitle, LookupData qualification, LookupData department, String phoneNo, String emailAddress, String physicalAddress, String specialSkills, boolean active) {
        this.idHelper = idHelper;
        this.staffID = staffID;
        this.photo = photo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.otherNames = otherNames;
        this.initials = initials;
        this.gender = gender;
        this.birthDate = birthDate;
        this.joinDate = joinDate;
        this.nationalID = nationalID;
        this.country = country;
        this.village = village;
        this.staffTitle = staffTitle;
        this.qualification = qualification;
        this.department = department;
        this.phoneNo = phoneNo;
        this.emailAddress = emailAddress;
        this.physicalAddress = physicalAddress;
        this.specialSkills = specialSkills;
        this.active = active;

    }

    public int getIdHelper() {
        return idHelper;
    }

    public void setIdHelper(int idHelper) {
        this.idHelper = idHelper;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getNationalID() {
        return nationalID;
    }

    public void setNationalID(String nationalID) {
        this.nationalID = nationalID;
    }

    public LookupData getCountry() {
        return country;
    }

    public void setCountry(LookupData country) {
        this.country = country;
    }

    public Village getVillage() {
        return village;
    }

    public void setVillage(Village village) {
        this.village = village;
    }

    public LookupData getStaffTitle() {
        return staffTitle;
    }

    public void setStaffTitle(LookupData staffTitle) {
        this.staffTitle = staffTitle;
    }

    public LookupData getQualification() {
        return qualification;
    }

    public void setQualification(LookupData qualification) {
        this.qualification = qualification;
    }

    public LookupData getDepartment() {
        return department;
    }

    public void setDepartment(LookupData department) {
        this.department = department;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public String getSpecialSkills() {
        return specialSkills;
    }

    public void setSpecialSkills(String specialSkills) {
        this.specialSkills = specialSkills;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Staff)) {
            return false;
        }

        Staff staff = (Staff) o;

        return this.getId().equals(staff.getId());
    }

    @Override
    public int hashCode() {
        return this.staffID.hashCode();

    }

    @Override
    public Object getId() {
        return this.staffID;
    }

    @Override
    public String getDisplayKey() {
        return this.firstName + " " + this.lastName + " " + this.otherNames;
    }

}
