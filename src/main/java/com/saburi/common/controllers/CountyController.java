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
import com.saburi.common.dbaccess.CountyDA;
import com.saburi.common.entities.LookupData;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import com.saburi.common.utils.CommonObjectNames;
import java.io.IOException;

public class CountyController extends EditController {

    private final CountyDA oCountyDA = new CountyDA();
    @FXML
    private ComboBox cboDistrict;
    @FXML
    private MenuItem cmiSelectDistrict;
    @FXML
    private TextField txtCountyID;
    @FXML
    private TextField txtCountyName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.prefSize = 360;
            this.primaryKeyControl = txtCountyID;
            loadLookupData(cboDistrict, CommonObjectNames.DISTRICT);

            cboDistrict.setOnAction(e -> this.setNextCountyID());

            selectLookupData(cmiSelectDistrict, CommonObjectNames.DISTRICT, "District", cboDistrict, true);

        } catch (IOException e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            this.editSuccessful = false;
            LookupData district = (LookupData) getEntity(cboDistrict, "District");
            String countyID = getText(txtCountyID, "County ID");
            String countyName = getText(txtCountyName, "County Name");

            CountyDA countyDA = new CountyDA(district, countyID, countyName);
            String buttonText = btnSave.getText();
            if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {
                countyDA.save();
                message("Saved Successfully");
                clear();
            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {
                countyDA.update();
                message("Updated Successfully");
            }
            this.dbAccess = countyDA;
            this.editSuccessful = true;
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void delete() {
        try {
            String countyID = getText(txtCountyID, "County ID");
            CountyDA countyDA = oCountyDA.get(countyID);
            if (!warningOk("Confirm Delete", "You are about to delete a record with ID: " + countyID + " Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            if (countyDA.delete()) {
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
            String countyID = getText(txtCountyID, "County ID");

            CountyDA countyDA = oCountyDA.get(countyID);
            cboDistrict.setValue(countyDA.getDistrict());
            txtCountyID.setText(countyDA.getCountyID());
            txtCountyName.setText(countyDA.getCountyName());

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void setNextCountyID() {
        if (btnSave.getText().equalsIgnoreCase(FormMode.Save.name())) {
            LookupData district = (LookupData) getEntity(cboDistrict);
            String districtID = district == null ? "" : district.getLookupDataID();

            txtCountyID.setText(oCountyDA.getNextCountyID(oCountyDA.getNextIdHelper(district), districtID));
        }
    }

}
