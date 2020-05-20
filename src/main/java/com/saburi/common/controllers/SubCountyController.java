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
import com.saburi.common.dbaccess.SubCountyDA;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import java.io.IOException;
import com.saburi.common.utils.CommonObjectNames;
import com.saburi.common.dbaccess.CountyDA;
import com.saburi.common.entities.County;
import com.saburi.common.entities.LookupData;
import com.saburi.common.utils.CommonNavigate;
import java.util.ArrayList;
import java.util.List;

public class SubCountyController extends EditController {

    private final SubCountyDA oSubCountyDA = new SubCountyDA();
    @FXML
    private ComboBox cboDistrict;
    @FXML
    private MenuItem cmiSelectDistrict;
    @FXML
    private ComboBox cboCounty;
    @FXML
    private MenuItem cmiSelectCounty;
    @FXML
    private TextField txtSubCountyID;
    @FXML
    private TextField txtSubCountyName;

    private final CountyDA oCountyDA = new CountyDA();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.primaryKeyControl = txtSubCountyID;
            this.dbAccess = oSubCountyDA;
            this.restrainColumnConstraint = false;
            this.primaryKeyControl = txtSubCountyID;
            this.prefSize = 360;

            loadLookupData(cboDistrict, CommonObjectNames.DISTRICT);

//            cmiLoad.visibleProperty().set(this.formMode.equals(FormMode.Update));
            cboDistrict.setOnAction(e -> {
                LookupData district = (LookupData) getEntity(cboDistrict);
                if (district == null) {
                    return;
                }
                loadDBEntities(oCountyDA.getCounties(district), cboCounty);
            });

            loadDBEntities(new ArrayList<County>(), cboCounty);
            cboCounty.setOnAction(e -> this.setNextSubCountyID());

            selectLookupData(cmiSelectDistrict, CommonObjectNames.DISTRICT, "District", 700, 450, btnSave, restrainColumnConstraint);
            selectItem(CommonNavigate.MAIN_CLASS, cmiSelectCounty, new CountyDA(), "County", "County", 700, 450, cboCounty, true);
        } catch (IOException e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            this.editSuccessful = false;
            County county = (County) getEntity(cboCounty, "County");
            String subCountyID = getText(txtSubCountyID, "Sub County ID");
            String subCountyName = getText(txtSubCountyName, "Sub County Name");

            SubCountyDA subCountyDA = new SubCountyDA(county, subCountyID, subCountyName);
            String buttonText = btnSave.getText();
            if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {

                subCountyDA.save();
                message("Saved Successfully");
                clear();
            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {
                subCountyDA.update();
                message("Updated Successfully");
            }
            this.dbAccess = subCountyDA;
            this.editSuccessful = true;
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void delete() {
        try {
            String subCountyID = getText(txtSubCountyID, "Sub County ID");
            SubCountyDA subCountyDA = oSubCountyDA.get(subCountyID);
            if (!warningOk("Confirm Delete", "You are about to delete a record with ID: " + subCountyID + " Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            if (subCountyDA.delete()) {
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
            String subCountyID = getText(txtSubCountyID, "Sub County ID");

            SubCountyDA subCountyDA = oSubCountyDA.get(subCountyID);
            cboDistrict.setValue(subCountyDA.getDistrict());
            County county = subCountyDA.getCounty();
            List<County> counties = cboCounty.getItems();
            if (!counties.contains(county)) {
                cboCounty.getItems().add(county);
            }
            cboCounty.setValue(county);
            txtSubCountyID.setText(subCountyDA.getSubCountyID());
            txtSubCountyName.setText(subCountyDA.getSubCountyName());

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void setNextSubCountyID() {
        if (btnSave.getText().equalsIgnoreCase(FormMode.Save.name())) {
            try {

                County county = (County) getEntity(cboCounty);
                String countyID = county == null ? "" : county.getCountyID();
                txtSubCountyID.setText(oSubCountyDA.getNextSubCountyID(oSubCountyDA.getNextIdHelper(county), countyID));
            } catch (Exception ex) {
                errorMessage(ex);
            }
        }
    }

    @Override
    public void clear() {
        cboDistrict.setValue(null);
        cboCounty.setValue(null);
        txtSubCountyID.clear();
        txtSubCountyName.clear();
        cboCounty.setOnAction(e -> this.setNextSubCountyID());
    }

}
