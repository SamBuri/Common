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
import com.saburi.common.dbaccess.LookupDataDA;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import com.saburi.common.dbaccess.LookupObjectDA;
import com.saburi.common.entities.LookupObject;
import com.saburi.common.utils.CommonNavigate;
import javafx.scene.control.CheckBox;

public class LookupDataController extends EditController {

    private final LookupDataDA oLookupDataDA = new LookupDataDA();
    @FXML
    private ComboBox cboLookupObject;
    @FXML
    private MenuItem cmiSelectLookupObject;
    @FXML
    private TextField txtLookupDataID;
    @FXML
    private TextField txtLookupDataName;
    @FXML
    private CheckBox chkReadOnly;
    @FXML
    private CheckBox chkHidden;
    @FXML
    private CheckBox chkIsDefault;
    private final LookupObjectDA oLookupObjectDA = new LookupObjectDA();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadDBEntities(oLookupObjectDA.getLookupObjects(), cboLookupObject);

            this.primaryKeyControl = txtLookupDataID;
            this.dbAccess = oLookupDataDA;
            this.restrainColumnConstraint = false;
            //this.minSize = 360;
            cboLookupObject.setOnAction(e -> this.setNextLookupDataID());
            selectItem(CommonNavigate.MAIN_CLASS, cmiSelectLookupObject, oLookupObjectDA, "View", "LookupObject", 700, 400, cboLookupObject, true);
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            this.editSuccessful = false;
            LookupObject lookupObject = (LookupObject) getEntity(cboLookupObject, "Lookup Object");
            String lookupDataID = getText(txtLookupDataID, "Lookup Data ID");
            String lookupDataName = getText(txtLookupDataName, "Lookup Data Name");
            boolean readOnly = chkReadOnly.isSelected();
            boolean hidden = chkHidden.isSelected();
            boolean isDefault = chkIsDefault.isSelected();

            LookupDataDA lookupDataDA = new LookupDataDA(lookupObject, lookupDataID, lookupDataName, readOnly, hidden, isDefault);
            String buttonText = btnSave.getText();
            if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {
                lookupDataDA.save();
                message("Saved Successfully");
                clear();
            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {
                lookupDataDA.update();
                message("Updated Successfully");
            }
            this.dbAccess = lookupDataDA;
            this.editSuccessful = true;
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void delete() {
        try {
            String lookupDataID = getText(txtLookupDataID, "Lookup Data ID");
            LookupDataDA lookupDataDA = oLookupDataDA.get(lookupDataID);
            if (!warningOk("Confirm Delete", "You are about to delete a record with ID: " + lookupDataID + " Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            if (lookupDataDA.delete()) {
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
            String lookupDataID = getText(txtLookupDataID, "Lookup Data ID");

            LookupDataDA lookupDataDA = oLookupDataDA.get(lookupDataID);
            cboLookupObject.setValue(lookupDataDA.getLookupObject());
            txtLookupDataID.setText(lookupDataDA.getLookupDataID());
            txtLookupDataName.setText(lookupDataDA.getLookupDataName());
            chkReadOnly.setSelected(lookupDataDA.isReadOnly());
            chkHidden.setSelected(lookupDataDA.isHidden());
            chkIsDefault.setSelected(lookupDataDA.isIsDefault());

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void setNextLookupDataID() {
        try {
            if (btnSave.getText().equalsIgnoreCase(FormMode.Save.name())) {
                
                LookupObject lookupObject = (LookupObject) getEntity(cboLookupObject, "Lookup Object");
                if(lookupObject==null)return;
                txtLookupDataID.setText(oLookupDataDA.getNextLookupDataID(oLookupDataDA.getNextIdHelper(lookupObject), lookupObject.getLookupObjectID()));
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    private void clear() {
       txtLookupDataID.clear();
        txtLookupDataName.clear();
        chkReadOnly.setSelected(false);
        chkHidden.setSelected(false);
        chkIsDefault.setSelected(false);
        cboLookupObject.setOnAction(e -> this.setNextLookupDataID());
    }

}
