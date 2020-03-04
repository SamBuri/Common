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
import com.saburi.common.dbaccess.CompanyDA;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import com.saburi.common.utils.CommonEnums.BusinessTypes;
import javafx.collections.FXCollections;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

public class CompanyController extends EditController {

    private final CompanyDA oCompanyDA = new CompanyDA();
    @FXML
    private TextField txtCompanyID;
    @FXML
    private TextField txtCompanyName;
    @FXML
    private ComboBox cboBusinessType;
    @FXML
    private TextField txtPhysicalAddress;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtWebSite;
    @FXML
    private TextField txtTagLine;
    @FXML
    private ImageView imvLogo;
    @FXML
    private Button btnBrowseLogo, btnCaptureLogo, btnClearLogo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cboBusinessType.setItems(FXCollections.observableArrayList(BusinessTypes.values()));
            this.primaryKeyControl = txtCompanyID;
            this.dbAccess = oCompanyDA;
            this.restrainColumnConstraint = false;
            this.minSize = 450;
            btnBrowseLogo.setOnAction(e -> browseImage(imvLogo));
            btnCaptureLogo.setOnAction(e -> setCapturedImage(imvLogo));
            btnClearLogo.setOnAction(e -> imvLogo.setImage(null));
            this.setNextCompanyID();
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            String companyID = getText(txtCompanyID, "Company ID");
            String companyName = getText(txtCompanyName, "Company Name");
            BusinessTypes businessType = (BusinessTypes) getSelectedValue(cboBusinessType, "Business Type");
            String physicalAddress = getText(txtPhysicalAddress, "Physical Address");
            String phone = getText(txtPhone, "Phone");
            String email = getText(txtEmail, "Email");
            String webSite = getText(txtWebSite, "Web Site");
            String tagLine = getText(txtTagLine, "Tag Line");
            byte[] logo = getBytes(imvLogo, "Logo");

            CompanyDA companyDA = new CompanyDA(companyID, companyName, businessType, physicalAddress, phone, email, webSite, tagLine, logo);
            String buttonText = btnSave.getText();
            if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {
                companyDA.save();
                message("Saved Successfully");
                clear();
            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {
                companyDA.update();
                message("Updated Successfully");
            }
            this.dbAccess = companyDA;
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void delete() {
        try {
            String companyID = getText(txtCompanyID, "Company ID");
            CompanyDA companyDA = oCompanyDA.get(companyID);
            if (!warningOk("Confirm Delete", "You are about to delete a record with ID: " + companyID + " Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            if (companyDA.delete()) {
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
            String companyID = getText(txtCompanyID, "Company ID");

            CompanyDA companyDA = oCompanyDA.get(companyID);
            txtCompanyID.setText(companyDA.getCompanyID());
            txtCompanyName.setText(companyDA.getCompanyName());
            cboBusinessType.setValue(companyDA.getBusinessType());
            txtPhysicalAddress.setText(companyDA.getPhysicalAddress());
            txtPhone.setText(companyDA.getPhone());
            txtEmail.setText(companyDA.getEmail());
            txtWebSite.setText(companyDA.getWebSite());
            txtTagLine.setText(companyDA.getTagLine());
            setImage(imvLogo, companyDA.getLogo());

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void setNextCompanyID() {
        try {
            if (btnSave.getText().equalsIgnoreCase(FormMode.Save.name())) {
                txtCompanyID.setText(oCompanyDA.getNextCompanyID(oCompanyDA.getNextIDHelper()));
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    protected void clear() {
        txtCompanyID.clear();
        txtCompanyName.clear();
        cboBusinessType.setValue(null);
        txtPhysicalAddress.clear();
        txtPhone.clear();
        txtEmail.clear();
        txtWebSite.clear();
        txtTagLine.clear();
        imvLogo.setImage(null);
        this.setNextCompanyID();

    }

}
