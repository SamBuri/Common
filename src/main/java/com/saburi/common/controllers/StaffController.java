/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import static com.saburi.common.utils.FXUIUtils.*;
import com.saburi.common.utils.Utilities.FormMode;
import static com.saburi.common.utils.FXUIUtils.warningOk;
import com.saburi.common.dbaccess.StaffDA;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import com.saburi.common.utils.CommonEnums.Gender;
import javafx.collections.FXCollections;
import javafx.scene.control.DatePicker;
import java.time.LocalDate;
;
import javafx.scene.control.MenuItem;
import com.saburi.common.entities.LookupData;
import com.saburi.common.utils.CommonObjectNames;
import com.saburi.common.dbaccess.VillageDA;
import com.saburi.common.entities.Village;
import com.saburi.common.utils.CommonNavigate;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;



public class StaffController extends EditController {

    private final StaffDA oStaffDA = new StaffDA();
    @FXML
    private TextField txtStaffID;
    @FXML
    private ImageView imvPhoto;
    @FXML
    private Button btnBrowsePhoto, btnCapturePhoto, btnClearPhoto;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtOtherNames;
    @FXML
    private TextField txtInitials;
    @FXML
    private ComboBox cboGender;
    @FXML
    private DatePicker dtpBirthDate;
    @FXML
    private DatePicker dtpJoinDate;
    @FXML
    private TextField txtNationalID;
    @FXML
    private ComboBox cboCountry;
    @FXML
    private MenuItem cmiSelectCountry;
    @FXML
    private ComboBox cboVillage;
    @FXML
    private MenuItem cmiSelectVillage;
    @FXML
    private ComboBox cboStaffTitle;
    @FXML
    private MenuItem cmiSelectStaffTitle;
    @FXML
    private ComboBox cboQualification;
    @FXML
    private MenuItem cmiSelectQualification;
    @FXML
    private ComboBox cboDepartment;
    @FXML
    private MenuItem cmiSelectDepartment;
    @FXML
    private TextField txtPhoneNo;
    @FXML
    private TextField txtEmailAddress;
    @FXML
    private TextArea txaPhysicalAddress;
    @FXML
    private TextArea txaSpecialSkills;
    @FXML
    private CheckBox chkActive;
    private final VillageDA oVillageDA = new VillageDA();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadLookupData(cboCountry, CommonObjectNames.COUNTRY);
            loadLookupData(cboStaffTitle, CommonObjectNames.STAFFTITLE);
            loadLookupData(cboQualification, CommonObjectNames.QUALIFICATION);
            loadLookupData(cboDepartment, CommonObjectNames.DEPARTMENT);
            cboGender.setItems(FXCollections.observableArrayList(Gender.values()));
            loadDBEntities(oVillageDA.getVillages(), cboVillage);

            this.primaryKeyControl = txtStaffID;
            this.dbAccess = oStaffDA;
            this.restrainColumnConstraint = false;
            //this.minSize = 360;
            btnBrowsePhoto.setOnAction(e -> browseImage(imvPhoto));
            btnCapturePhoto.setOnAction(e -> setCapturedImage(imvPhoto));
            btnClearPhoto.setOnAction(e -> imvPhoto.setImage(null));
            this.setNextStaffID();
            selectLookupData(CommonNavigate.MAIN_CLASS, cmiSelectCountry, CommonObjectNames.COUNTRY, "LookupData", "Country", cboCountry, false);
            selectItem(CommonNavigate.MAIN_CLASS, cmiSelectVillage, oVillageDA, "Village", "Village", cboVillage, true);
            selectLookupData(CommonNavigate.MAIN_CLASS, cmiSelectStaffTitle, CommonObjectNames.STAFFTITLE, "LookupData", "Staff Title", cboStaffTitle, false);
            selectLookupData(CommonNavigate.MAIN_CLASS, cmiSelectQualification, CommonObjectNames.QUALIFICATION, "LookupData", "Qualification", cboQualification, false);
            selectLookupData(CommonNavigate.MAIN_CLASS, cmiSelectDepartment, CommonObjectNames.DEPARTMENT, "LookupData", "Department", cboDepartment, false);
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            this.editSuccessful = false;
            String staffID = getText(txtStaffID, "Staff ID");
            byte[] photo = getBytes(imvPhoto, "Photo");
            String firstName = getText(txtFirstName, "First Name");
            String lastName = getText(txtLastName, "Last Name");
            String otherNames = getText(txtOtherNames, "Other Names");
            String initials = getText(txtInitials, "Initials");
            Gender gender = (Gender) getSelectedValue(cboGender, "Gender");
            LocalDate birthDate = getDate(dtpBirthDate, "Birth Date");
            LocalDate joinDate = getDate(dtpJoinDate, "Join Date");
            String nationalID = getText(txtNationalID, "National ID");
            LookupData country = (LookupData) getEntity(cboCountry, "Country");
            Village village = (Village) getEntity(cboVillage, "Village");
            LookupData staffTitle = (LookupData) getEntity(cboStaffTitle, "Staff Title");
            LookupData qualification = (LookupData) getEntity(cboQualification, "Qualification");
            LookupData department = (LookupData) getEntity(cboDepartment, "Department");
            String phoneNo = getText(txtPhoneNo, "Phone No");
            String emailAddress = getText(txtEmailAddress, "Email Address");
            String physicalAddress = getText(txaPhysicalAddress, "Physical Address");
            String specialSkills = getText(txaSpecialSkills, "Special Skills");
            boolean active = chkActive.isSelected();

            StaffDA staffDA = new StaffDA(staffID, photo, firstName, lastName, otherNames, initials, gender, birthDate, joinDate, nationalID, country, village, staffTitle, qualification, department, phoneNo, emailAddress, physicalAddress, specialSkills, active);
            String buttonText = btnSave.getText();
            if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {
                staffDA.save();
                message("Saved Successfully");
                clear();
                this.setNextStaffID();
            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {
                staffDA.update();
                message("Updated Successfully");
            }
            this.dbAccess = staffDA;
            this.editSuccessful = true;
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void delete() {
        try {
            String staffID = getText(txtStaffID, "Staff ID");
            StaffDA staffDA = oStaffDA.get(staffID);
            if (!warningOk("Confirm Delete", "You are about to delete a record with ID: " + staffID + " Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            if (staffDA.delete()) {
                message("Deleted Successfully");
                this.clear();
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    @Override
    public void loadData() {
        try {
            String staffID = getText(txtStaffID, "Staff ID");

            StaffDA staffDA = oStaffDA.get(staffID);
            txtStaffID.setText(staffDA.getStaffID());
            setImage(imvPhoto, staffDA.getPhoto());
            txtFirstName.setText(staffDA.getFirstName());
            txtLastName.setText(staffDA.getLastName());
            txtOtherNames.setText(staffDA.getOtherNames());
            txtInitials.setText(staffDA.getInitials());
            cboGender.setValue(staffDA.getGender());
            dtpBirthDate.setValue((LocalDate) staffDA.getBirthDate());
            dtpJoinDate.setValue((LocalDate) staffDA.getJoinDate());
            txtNationalID.setText(staffDA.getNationalID());
            cboCountry.setValue(staffDA.getCountry());
            cboVillage.setValue(staffDA.getVillage());
            cboStaffTitle.setValue(staffDA.getStaffTitle());
            cboQualification.setValue(staffDA.getQualification());
            cboDepartment.setValue(staffDA.getDepartment());
            txtPhoneNo.setText(staffDA.getPhoneNo());
            txtEmailAddress.setText(staffDA.getEmailAddress());
            txaPhysicalAddress.setText(staffDA.getPhysicalAddress());
            txaSpecialSkills.setText(staffDA.getSpecialSkills());
            chkActive.setSelected(staffDA.isActive());

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void setNextStaffID() {
        try {
            if (btnSave.getText().equalsIgnoreCase(FormMode.Save.name())) {
                txtStaffID.setText(oStaffDA.getNextStaffID(oStaffDA.getNextIdHelper()));
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    

}
