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
import com.saburi.common.dbaccess.ParishDA;
import com.saburi.common.entities.Parish;
import com.saburi.common.utils.CommonNavigate;

public class VillageController extends EditController {

    private final VillageDA oVillageDA = new VillageDA();
    @FXML
    private ComboBox cboParish;
    @FXML
    private MenuItem cmiSelectParish;
    @FXML
    private TextField txtVillageID;
    @FXML
    private TextField txtVillageName;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadDBEntities(new ParishDA().getParishs(), cboParish);

            this.primaryKeyControl = txtVillageID;
            this.dbAccess = oVillageDA;
            this.restrainColumnConstraint = false;
//            this.minSize=360;
            cboParish.setOnAction(e -> this.setNextVillageID());
            selectItem(CommonNavigate.mainClass, cmiSelectParish, new ParishDA(), "Parish", "Parish", 700, 450, cboParish, true);

        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            this.editSuccessful = false;
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
                this.clear();
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
            cboParish.setValue(villageDA.getParishDA());
            txtVillageID.setText(villageDA.getVillageID());
            txtVillageName.setText(villageDA.getVillageName());

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void setNextVillageID() {
        try {
            if (btnSave.getText().equalsIgnoreCase(FormMode.Save.name())) {

                Parish parish = (Parish) getEntity(cboParish);
                String parishID = parish == null ? "" : parish.getParishID();
                txtVillageID.setText(oVillageDA.getNextVillageID(oVillageDA.getNextIdHelper(parish), parishID));
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    private void clear() {
        cboParish.setValue(null);
        txtVillageID.clear();
        txtVillageName.clear();
        cboParish.setOnAction(e -> this.setNextVillageID());
    }

}
