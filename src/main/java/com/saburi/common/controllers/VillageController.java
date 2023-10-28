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
import com.saburi.common.dbaccess.VillageDA;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import com.saburi.common.entities.LookupData;
import com.saburi.common.utils.CommonNavigate;
import com.saburi.common.utils.CommonObjectNames;
import com.saburi.common.dbaccess.CountyDA;
import com.saburi.common.entities.County;
import com.saburi.common.dbaccess.SubCountyDA;
import com.saburi.common.entities.SubCounty;
import com.saburi.common.dbaccess.ParishDA;
import com.saburi.common.entities.Parish;
import java.util.List;

public class VillageController extends EditController {

    private final VillageDA oVillageDA = new VillageDA();
    @FXML
    private ComboBox cboDistrict;
    @FXML
    private MenuItem cmiSelectDistrict;
    @FXML
    private ComboBox cboCounty;
    @FXML
    private MenuItem cmiSelectCounty;
    @FXML
    private ComboBox cboSubCounty;
    @FXML
    private MenuItem cmiSelectSubCounty;
    @FXML
    private ComboBox cboParish;
    @FXML
    private MenuItem cmiSelectParish;
    @FXML
    private TextField txtVillageID;
    @FXML
    private TextField txtVillageName;
    private final CountyDA oCountyDA = new CountyDA();
    private final SubCountyDA oSubCountyDA = new SubCountyDA();
    private final ParishDA oParishDA = new ParishDA();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadLookupData(cboDistrict, CommonObjectNames.DISTRICT);
            loadDBEntities(cboCounty);
            loadDBEntities(cboSubCounty);
            loadDBEntities(cboParish);

            this.primaryKeyControl = txtVillageID;
            this.dbAccess = oVillageDA;
            this.restrainColumnConstraint = false;
            //this.prefSize = 360;
            cboParish.setOnAction(e -> this.setNextVillageID());
            selectLookupData( cmiSelectDistrict, CommonObjectNames.DISTRICT,  "District", cboDistrict, false);
            selectItem(CommonNavigate.MAIN_CLASS, cmiSelectCounty, oCountyDA, "County", "County", cboCounty, true);
            selectItem(CommonNavigate.MAIN_CLASS, cmiSelectSubCounty, oSubCountyDA, "SubCounty", "Sub County", cboSubCounty, true);
            selectItem(CommonNavigate.MAIN_CLASS, cmiSelectParish, oParishDA, "Parish", "Parish", cboParish, true);

            cboDistrict.setOnAction(e -> {
                LookupData district = (LookupData) getEntity(cboDistrict);
                if (district == null) {
                    return;
                }
                loadDBEntities(oCountyDA.getCounties(district), cboCounty);
            });

            cboCounty.setOnAction(e -> {
                County county = (County) getEntity(cboCounty);
                if (county == null) {
                    return;
                }
                loadDBEntities(oSubCountyDA.getSubCounties(county), cboSubCounty);
            });

            cboSubCounty.setOnAction(e -> {
                SubCounty subCounty = (SubCounty) getEntity(cboSubCounty);
                if (subCounty == null) {
                    return;
                }
                loadDBEntities(oParishDA.getParishes(subCounty), cboParish);
            });
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            this.editSuccessful = false;
            LookupData district = (LookupData) getEntity(cboDistrict, "District");
            County county = (County) getEntity(cboCounty, "County");
            SubCounty subCounty = (SubCounty) getEntity(cboSubCounty, "Sub County");
            Parish parish = (Parish) getEntity(cboParish, "Parish");
            String villageID = getText(txtVillageID, "Village ID");
            String villageName = getText(txtVillageName, "Village Name");

            VillageDA villageDA = new VillageDA(parish, villageID, villageName);
            String buttonText = btnSave.getText();
            if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {
                villageDA.save();
                message("Saved Successfully");
                clear();
            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {
                villageDA.update();
                message("Updated Successfully");
            }
            this.dbAccess = villageDA;
            this.editSuccessful = true;
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void delete() {
        try {
            String villageID = getText(txtVillageID, "Village ID");
            VillageDA villageDA = oVillageDA.get(villageID);
            if (!warningOk("Confirm Delete", "You are about to delete a record with ID: " + villageID + " Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            if (villageDA.delete()) {
                message("Deleted Successfully");
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    @Override
    public void loadData() {
        try {
            String villageID = getText(txtVillageID, "Village ID");

            VillageDA villageDA = oVillageDA.get(villageID);
            cboDistrict.setValue(villageDA.getDistrict());
            cboCounty.setValue(villageDA.getCounty());
            cboSubCounty.setValue(villageDA.getSubCounty());
            cboParish.setValue(villageDA.getParish());
            txtVillageID.setText(villageDA.getVillageID());
            txtVillageName.setText(villageDA.getVillageName());

            County county = villageDA.getCounty();
            List<County> counties = cboCounty.getItems();
            if (!counties.contains(county)) {
                cboCounty.getItems().add(county);
            }
            cboCounty.setValue(county);
            SubCounty subCounty = villageDA.getSubCounty();
            List<SubCounty> subCounties = cboSubCounty.getItems();
            if (!subCounties.contains(subCounty)) {
                cboSubCounty.getItems().add(subCounty);
            }
            cboSubCounty.setValue(subCounty);

            Parish parish = villageDA.getParish();
            List<Parish> parishes = cboParish.getItems();
            if (!parishes.contains(parish)) {
                cboParish.getItems().add(parish);
            }
            cboParish.setValue(parish);

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void setNextVillageID() {
        try {
            if (btnSave.getText().equalsIgnoreCase(FormMode.Save.name())) {
                Parish parish = (Parish) getEntity(cboParish);
                if (parish == null) {
                    return;
                }
                txtVillageID.setText(oVillageDA.getNextVillageID(oVillageDA.getNextIdHelper(parish), parish.getId().toString()));
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

}
