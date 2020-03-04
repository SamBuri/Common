package com.saburi.common.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import static com.saburi.common.utils.FXUIUtils.*;
import com.saburi.common.utils.Utilities.FormMode;
import static com.saburi.common.utils.FXUIUtils.warningOk;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import com.saburi.common.dbaccess.LicenceDA;
import com.saburi.common.utils.CommonEnums.LicenceTypes;
import com.saburi.common.utils.SaburiEncryptor;
import com.saburi.common.utils.Utilities;
import javafx.collections.FXCollections;
import java.time.LocalDate;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;

public class LicenceController extends EditController {

    @FXML
    private TextField txtLicenceNo;
    @FXML
    private TextField txtDetails;
    @FXML
    private ComboBox cboLicenceType;
    @FXML
    private DatePicker dtpStartDate;
    @FXML
    private DatePicker dtpEndDate;
    @FXML
    private TextField txtUserLimit;
    @FXML
    private TextField txtRecordLimit;
    @FXML
    private TextField txtBaseTable;
    @FXML
    private TextField txtFileName;
    @FXML
    private Button btnBrowse;
    @FXML
    private TextArea txaLicence;

    private final LicenceDA oLicence = new LicenceDA();

    public void setButtonText(String text) {
        btnSave.setText(text);
        btnSearch.setVisible(true);
        btnDelete.setVisible(true);
    }

    public void setEdit(String text) {
        btnSave.setText(FormMode.Update.name());
        btnSearch.setVisible(true);
        btnDelete.setVisible(true);
        this.txtLicenceNo.setText(text);
        btnSearch.fire();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            validateIteger(txtUserLimit);
            validateIteger(txtRecordLimit);
            cboLicenceType.setItems(FXCollections.observableArrayList(LicenceTypes.values()));
            btnSearch.setOnAction(e -> this.loadData());
            btnDelete.setOnAction(e -> this.delete());

            btnBrowse.setOnAction(e -> {
                browse(txtFileName);
                this.loadData();
            });
            txtFileName.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    loadData();
                }
            });

        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            this.editSuccessful =false;
            String licenceText = getText(txaLicence, "Licence");
            String[] licences = licenceText.split("\n");

            if (licences.length != 8) {
                message("Invalid licence");
                return;
            }

            LicenceDA licenceDA = new LicenceDA(licences[0], licences[1], licences[2], licences[3], licences[4], licences[5], licences[6], licences[7]);

            String buttonText = btnSave.getText();
            if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {
                licenceDA.save();
                message("Saved Successfully");
                clear();
            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {
                licenceDA.update();
                message("Updated Successfully");
            }
            this.dbAccess = licenceDA;
            this.editSuccessful = true;

        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

   

    @Override
    public void loadData() {
        try {

            txaLicence.setText(Utilities.readFileString(txtFileName.getText()));
            String licenceText = getText(txaLicence, "Licence");
            String[] licences = licenceText.split("\n");
            if (licences.length != 8) {
                message("Invalid licence");
                return;
            }

            txtLicenceNo.setText(SaburiEncryptor.decrypt(licences[0]));
            txtDetails.setText(SaburiEncryptor.decrypt(licences[1]));
            cboLicenceType.setValue(SaburiEncryptor.decrypt(licences[2]));
            dtpStartDate.setValue(LocalDate.parse(SaburiEncryptor.decrypt(licences[3])));
            dtpEndDate.setValue(LocalDate.parse(SaburiEncryptor.decrypt(licences[4])));
            txtUserLimit.setText(SaburiEncryptor.decrypt(licences[5]));
            txtRecordLimit.setText(SaburiEncryptor.decrypt(licences[6]));
            txtBaseTable.setText(SaburiEncryptor.decrypt(licences[7]));

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    @Override
    protected void delete() {
        try {
            String licenceNo = getText(txtLicenceNo, "Licence No");

            LicenceDA licenceDA = oLicence.get(licenceNo);
            if (!warningOk("Confirm Delete", "You are about to delete a record with ID: " + licenceNo + " Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            if (licenceDA.delete()) {
                message("Deleted Successfully");
                this.clear();
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

}
