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
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import com.saburi.common.utils.CommonEnums.AccountTypes;
import javafx.collections.FXCollections;
import javafx.scene.control.MenuItem;
import com.saburi.common.dbaccess.AccountCategoryDA;
import com.saburi.common.entities.AccountCategory;
import com.saburi.common.utils.CommonNavigate;
import javafx.scene.control.CheckBox;

public class AccountCategoryController extends EditController {

    private final AccountCategoryDA oAccountCategoryDA = new AccountCategoryDA();
    @FXML
    private ComboBox cboAccountType;
    @FXML
    private TextField txtCategoryID;
    @FXML
    private TextField txtCategoryName;
    @FXML
    private ComboBox cboParentCategory;
    @FXML
    private MenuItem cmiSelectParentCategory;
    @FXML
    private CheckBox chkReadOnly;
    @FXML
    private CheckBox chkHidden;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cboAccountType.setItems(FXCollections.observableArrayList(AccountTypes.values()));
            loadDBEntities(new AccountCategoryDA().getAccountCategorys(), cboParentCategory);

            this.primaryKeyControl = txtCategoryID;
            this.dbAccess = oAccountCategoryDA;
            this.restrainColumnConstraint = false;
            this.minSize = 360;
            cboAccountType.setOnAction(e -> this.setNextCategoryID());
            selectItem(CommonNavigate.MAIN_CLASS, cmiSelectParentCategory, new AccountCategoryDA(), "AccountCategory", "Parent Category", 700, 400, cboParentCategory, true);
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            this.editSuccessful = false;
            AccountTypes accountType = (AccountTypes) getSelectedValue(cboAccountType, "Account Type");
            String categoryID = getText(txtCategoryID, "Category ID");
            String categoryName = getText(txtCategoryName, "Category Name");
            AccountCategory parentCategory = (AccountCategory) getEntity(cboParentCategory);
            boolean readOnly = chkReadOnly.isSelected();
            boolean hidden = chkHidden.isSelected();

            AccountCategoryDA accountCategoryDA = new AccountCategoryDA(accountType, categoryID, categoryName, parentCategory, readOnly, hidden);
            String buttonText = btnSave.getText();
            if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {
                accountCategoryDA.save();
                message("Saved Successfully");
                loadDBEntities(new AccountCategoryDA().getAccountCategorys(), cboParentCategory);
                clear();
            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {
                accountCategoryDA.update();
                message("Updated Successfully");
            }
            this.dbAccess = accountCategoryDA;
            this.editSuccessful = true;
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void delete() {
        try {
            String categoryID = getText(txtCategoryID, "Category ID");
            AccountCategoryDA accountCategoryDA = oAccountCategoryDA.get(categoryID);
            if (!warningOk("Confirm Delete", "You are about to delete a record with ID: " + categoryID + " Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            if (accountCategoryDA.delete()) {
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
            String categoryID = getText(txtCategoryID, "Category ID");

            AccountCategoryDA accountCategoryDA = oAccountCategoryDA.get(categoryID);
            cboAccountType.setValue(accountCategoryDA.getAccountType());
            txtCategoryID.setText(accountCategoryDA.getCategoryID());
            txtCategoryName.setText(accountCategoryDA.getCategoryName());
            cboParentCategory.setValue(accountCategoryDA.getParentCategoryDA());
            chkReadOnly.setSelected(accountCategoryDA.isReadOnly());
            chkHidden.setSelected(accountCategoryDA.isHidden());

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void setNextCategoryID() {
        try {
            if (btnSave.getText().equalsIgnoreCase(FormMode.Save.name())) {
                AccountTypes accountType = (AccountTypes) cboAccountType.getValue();
                if (accountType == null) {
                    return;
                }
                txtCategoryID.setText(oAccountCategoryDA.getNextCategoryID(oAccountCategoryDA.getNextIdHelper(accountType), accountType));
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    private void clear() {
        cboAccountType.setValue(null);
        txtCategoryID.clear();
        txtCategoryName.clear();
        cboParentCategory.setValue(null);
        chkReadOnly.setSelected(false);
        chkHidden.setSelected(false);
        cboAccountType.setOnAction(e -> this.setNextCategoryID());
    }

}
