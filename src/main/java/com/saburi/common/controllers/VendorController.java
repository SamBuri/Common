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
import com.saburi.common.dbaccess.VendorDA;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import com.saburi.common.entities.LookupData;
import com.saburi.common.utils.CommonObjectNames;
import javafx.scene.control.TextArea;
import com.saburi.common.dbaccess.VendorPostingGroupDA;
import com.saburi.common.entities.VendorPostingGroup;
import com.saburi.common.utils.CommonNavigate;
import static com.saburi.common.utils.Utilities.formatNumber;

public class VendorController extends EditController {

    private final VendorDA oVendorDA = new VendorDA();
    @FXML
    private TextField txtVendorID;
    @FXML
    private TextField txtVendorName;
    @FXML
    private ComboBox cboVendorCategory;
    @FXML
    private MenuItem cmiSelectVendorCategory;
    @FXML
    private TextField txtPhoneNo;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextArea txaAddress;
    @FXML
    private ComboBox cboPriceGroup;
    @FXML
    private MenuItem cmiSelectPriceGroup;
    @FXML
    private ComboBox cboVendorPostingGroup;
    @FXML
    private MenuItem cmiSelectVendorPostingGroup;
    @FXML
    private ComboBox cboBusinessGroup;
    @FXML
    private MenuItem cmiSelectBusinessGroup;
    @FXML
    private ComboBox cboVATBusinessGroup;
    @FXML
    private MenuItem cmiSelectVATBusinessGroup;
    @FXML
    private TextField txtBalance;
    private final VendorPostingGroupDA oVendorPostingGroupDA = new VendorPostingGroupDA();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadLookupData(cboVendorCategory, CommonObjectNames.VENDORCATEGORY);
            loadLookupData(cboPriceGroup, CommonObjectNames.PRICEGROUP);
            loadLookupData(cboBusinessGroup, CommonObjectNames.BUSINESSGROUP);
            loadLookupData(cboVATBusinessGroup, CommonObjectNames.VATBUSINESSGROUP);
            loadDBEntities(oVendorPostingGroupDA.getVendorPostingGroups(), cboVendorPostingGroup);

            validateNumber(txtBalance);
            formatValue(txtBalance);
            this.primaryKeyControl = txtVendorID;
            this.dbAccess = oVendorDA;
            this.restrainColumnConstraint = false;
            //this.minSize = 360;
            this.setNextVendorID();
            selectLookupData(CommonNavigate.mainClass, cmiSelectVendorCategory, CommonObjectNames.VENDORCATEGORY, "view", "VendorCategory", 700, 400, cboVendorCategory, false);
            selectLookupData(CommonNavigate.mainClass, cmiSelectPriceGroup, CommonObjectNames.PRICEGROUP, "view", "PriceGroup", 700, 400, cboPriceGroup, false);
            selectItem(CommonNavigate.mainClass, cmiSelectVendorPostingGroup, oVendorPostingGroupDA, "View", "VendorPostingGroup", 700, 400, cboVendorPostingGroup, true);
            selectLookupData(CommonNavigate.mainClass, cmiSelectBusinessGroup, CommonObjectNames.BUSINESSGROUP, "view", "BusinessGroup", 700, 400, cboBusinessGroup, false);
            selectLookupData(CommonNavigate.mainClass, cmiSelectVATBusinessGroup, CommonObjectNames.VATBUSINESSGROUP, "view", "VATBusinessGroup", 700, 400, cboVATBusinessGroup, false);
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            String vendorID = getText(txtVendorID, "Vendor ID");
            String vendorName = getText(txtVendorName, "Vendor Name");
            LookupData vendorCategory = (LookupData) getEntity(cboVendorCategory, "Vendor Category");
            String phoneNo = getText(txtPhoneNo, "PhoneNo");
            String email = getText(txtEmail, "Email");
            String address = getText(txaAddress, "Address");
            LookupData priceGroup = (LookupData) getEntity(cboPriceGroup, "Price Group");
            VendorPostingGroup vendorPostingGroup = (VendorPostingGroup) getEntity(cboVendorPostingGroup, "Vendor Posting Group");
            LookupData businessGroup = (LookupData) getEntity(cboBusinessGroup, "Business Group");
            LookupData vATBusinessGroup = (LookupData) getEntity(cboVATBusinessGroup, "VAT Business Group");

            VendorDA vendorDA = new VendorDA(vendorID, vendorName, vendorCategory, phoneNo, email, address, priceGroup, vendorPostingGroup, businessGroup, vATBusinessGroup);
            String buttonText = btnSave.getText();
            if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {
                vendorDA.save();
                message("Saved Successfully");
                clear();
            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {
                vendorDA.update();
                message("Updated Successfully");
            }
            this.dbAccess = vendorDA;
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void delete() {
        try {
            String vendorID = getText(txtVendorID, "Vendor ID");
            VendorDA vendorDA = oVendorDA.get(vendorID);
            if (!warningOk("Confirm Delete", "You are about to delete a record with ID: " + vendorID + " Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            if (vendorDA.delete()) {
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
            String vendorID = getText(txtVendorID, "Vendor ID");

            VendorDA vendorDA = oVendorDA.get(vendorID);
            txtVendorID.setText(vendorDA.getVendorID());
            txtVendorName.setText(vendorDA.getVendorName());
            cboVendorCategory.setValue(vendorDA.getVendorCategory());
            txtPhoneNo.setText(vendorDA.getPhoneNo());
            txtEmail.setText(vendorDA.getEmail());
            txaAddress.setText(vendorDA.getAddress());
            cboPriceGroup.setValue(vendorDA.getPriceGroup());
            cboVendorPostingGroup.setValue(vendorDA.getVendorPostingGroup());
            cboBusinessGroup.setValue(vendorDA.getBusinessGroup());
            cboVATBusinessGroup.setValue(vendorDA.getVATBusinessGroup());
            txtBalance.setText(formatNumber(vendorDA.getBalance()));

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void setNextVendorID() {
        try {
            if (btnSave.getText().equalsIgnoreCase(FormMode.Save.name())) {
                txtVendorID.setText(oVendorDA.getNextVendorID(oVendorDA.getNextIdHelper()));
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    private void clear() {
        txtVendorID.clear();
        txtVendorName.clear();
        cboVendorCategory.setValue(null);
        txtPhoneNo.clear();
        txtEmail.clear();
        txaAddress.clear();
        cboPriceGroup.setValue(null);
        cboVendorPostingGroup.setValue(null);
        cboBusinessGroup.setValue(null);
        cboVATBusinessGroup.setValue(null);
        txtBalance.clear();
        this.setNextVendorID();

    }

}
