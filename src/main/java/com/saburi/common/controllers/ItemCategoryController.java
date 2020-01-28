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
import com.saburi.common.dbaccess.ItemCategoryDA;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import com.saburi.common.utils.CommonEnums.ItemCategoryGroups;
import javafx.collections.FXCollections;
import javafx.scene.control.CheckBox;

public class ItemCategoryController extends EditController {

    private final ItemCategoryDA oItemCategoryDA = new ItemCategoryDA();
    @FXML
    private ComboBox cboItemGroup;
    @FXML
    private TextField txtItemCategoryID;
    @FXML
    private TextField txtItemCategoryName;
    @FXML
    private CheckBox chkHidden;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cboItemGroup.setItems(FXCollections.observableArrayList(ItemCategoryGroups.values()));
            this.primaryKeyControl = txtItemCategoryID;
            this.dbAccess = oItemCategoryDA;
            this.restrainColumnConstraint = false;
            this.minSize = 360;
            cboItemGroup.setOnAction(e -> this.setNextItemCategoryID());
            this.chkHidden.disableProperty().set(btnSave.getText().equalsIgnoreCase(FormMode.Save.name()));
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            this.editSuccessful = false;
            ItemCategoryGroups itemGroup = (ItemCategoryGroups) getSelectedValue(cboItemGroup, "Item Group");
            String itemCategoryID = getText(txtItemCategoryID, "Item Category ID");
            String itemCategoryName = getText(txtItemCategoryName, "Item Category Name");
            boolean hidden = chkHidden.isSelected();

            ItemCategoryDA itemCategoryDA = new ItemCategoryDA(itemGroup, itemCategoryID, itemCategoryName, hidden);
            String buttonText = btnSave.getText();
            if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {
                itemCategoryDA.save();
                message("Saved Successfully");
                clear();
            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {
                itemCategoryDA.update();
                message("Updated Successfully");
            }
            this.dbAccess = itemCategoryDA;
            this.editSuccessful = true;
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void delete() {
        try {
            String itemCategoryID = getText(txtItemCategoryID, "Item Category ID");
            ItemCategoryDA itemCategoryDA = oItemCategoryDA.get(itemCategoryID);
            if (!warningOk("Confirm Delete", "You are about to delete a record with ID: " + itemCategoryID + " Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            if (itemCategoryDA.delete()) {
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
            String itemCategoryID = getText(txtItemCategoryID, "Item Category ID");

            ItemCategoryDA itemCategoryDA = oItemCategoryDA.get(itemCategoryID);
            cboItemGroup.setValue(itemCategoryDA.getItemGroup());
            txtItemCategoryID.setText(itemCategoryDA.getItemCategoryID());
            txtItemCategoryName.setText(itemCategoryDA.getItemCategoryName());
            chkHidden.setSelected(itemCategoryDA.isHidden());

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void setNextItemCategoryID() {
        try {
            if (btnSave.getText().equalsIgnoreCase(FormMode.Save.name())) {
                ItemCategoryGroups itemGroup = (ItemCategoryGroups) cboItemGroup.getValue();
                if (itemGroup == null) {
                    return;
                }
                txtItemCategoryID.setText(oItemCategoryDA.getNextItemCategoryID(oItemCategoryDA.getNextIdHelper(itemGroup), itemGroup));
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    private void clear() {
        cboItemGroup.setValue(null);
        txtItemCategoryID.clear();
        txtItemCategoryName.clear();
        chkHidden.setSelected(false);
        cboItemGroup.setOnAction(e -> this.setNextItemCategoryID());
    }

}
