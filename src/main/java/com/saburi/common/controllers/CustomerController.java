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
import javafx.scene.control.MenuItem;
import com.saburi.common.entities.LookupData;
import com.saburi.common.utils.CommonObjectNames;
import javafx.scene.control.TextArea;
import com.saburi.common.dbaccess.CustomerDA;
import com.saburi.common.entities.Customer;
import com.saburi.common.dbaccess.CustomerPostingGroupDA;
import com.saburi.common.entities.CustomerPostingGroup;
import com.saburi.common.utils.CommonNavigate;
import static com.saburi.common.utils.Utilities.formatNumber;

public class CustomerController extends EditController {

    private final CustomerDA oCustomerDA = new CustomerDA();
    @FXML
    private TextField txtCustomerID;
    @FXML
    private TextField txtCustomerName;
    @FXML
    private ComboBox cboCustomerCategory;
    @FXML
    private MenuItem cmiSelectCustomerCategory;
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
    private ComboBox cboBillToCustomer;
    @FXML
    private MenuItem cmiSelectBillToCustomer;
    @FXML
    private ComboBox cboCustomerPostingGroup;
    @FXML
    private MenuItem cmiSelectCustomerPostingGroup;
    @FXML
    private ComboBox cboBusinessGroup;
    @FXML
    private MenuItem cmiSelectBusinessGroup;
    @FXML
    private ComboBox cboVATBusinessGroup;
    @FXML
    private MenuItem cmiSelectVATBusinessGroup;
    @FXML
    private TextField txtReferenceNo;
    @FXML
    private TextField txtBalance;
    private final CustomerPostingGroupDA oCustomerPostingGroupDA = new CustomerPostingGroupDA();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadLookupData(cboCustomerCategory, CommonObjectNames.CUSTOMERCATEGORY);
            loadLookupData(cboPriceGroup, CommonObjectNames.PRICEGROUP);
            loadLookupData(cboBusinessGroup, CommonObjectNames.BUSINESSGROUP);
            loadLookupData(cboVATBusinessGroup, CommonObjectNames.VATBUSINESSGROUP);
            loadDBEntities(oCustomerDA.getCustomers(), cboBillToCustomer);
            loadDBEntities(oCustomerPostingGroupDA.getCustomerPostingGroups(), cboCustomerPostingGroup);

            validateNumber(txtBalance);
            formatValue(txtBalance);
            this.primaryKeyControl = txtCustomerID;
            this.dbAccess = oCustomerDA;
            this.restrainColumnConstraint = false;
            //this.minSize = 360;
            this.setNextCustomerID();
            selectLookupData(CommonNavigate.MAIN_CLASS,cmiSelectCustomerCategory, CommonObjectNames.CUSTOMERCATEGORY, "view", "CustomerCategory", 700, 400, cboCustomerCategory, false);
            selectLookupData(CommonNavigate.MAIN_CLASS, cmiSelectPriceGroup, CommonObjectNames.PRICEGROUP, "view", "PriceGroup", 700, 400, cboPriceGroup, false);
            selectItem(CommonNavigate.MAIN_CLASS, cmiSelectBillToCustomer, oCustomerDA, "View", "BillToCustomer", 700, 400, cboBillToCustomer, true);
            selectItem(CommonNavigate.MAIN_CLASS, cmiSelectCustomerPostingGroup, oCustomerPostingGroupDA, "View", "CustomerPostingGroup", 700, 400, cboCustomerPostingGroup, true);
            selectLookupData(CommonNavigate.MAIN_CLASS, cmiSelectBusinessGroup, CommonObjectNames.BUSINESSGROUP, "view", "BusinessGroup", 700, 400, cboBusinessGroup, false);
            selectLookupData(CommonNavigate.MAIN_CLASS, cmiSelectVATBusinessGroup, CommonObjectNames.VATBUSINESSGROUP, "view", "VATBusinessGroup", 700, 400, cboVATBusinessGroup, false);
           
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            this.editSuccessful = false;
            String customerID = getText(txtCustomerID, "Customer ID");
            String customerName = getText(txtCustomerName, "Customer Name");
            LookupData customerCategory = (LookupData) getEntity(cboCustomerCategory, "Customer Category");
            String phoneNo = getText(txtPhoneNo, "PhoneNo");
            String email = getText(txtEmail);
            String address = getText(txaAddress, "Address");
            LookupData priceGroup = (LookupData) getEntity(cboPriceGroup);
            Customer billToCustomer = (Customer) getEntity(cboBillToCustomer);
            CustomerPostingGroup customerPostingGroup = (CustomerPostingGroup) getEntity(cboCustomerPostingGroup, "Customer Posting Group");
            LookupData businessGroup = (LookupData) getEntity(cboBusinessGroup, "Business Group");
            LookupData vATBusinessGroup = (LookupData) getEntity(cboVATBusinessGroup, "VAT Business Group");
            String referenceNo = getText(txtReferenceNo);

            CustomerDA customerDA = new CustomerDA(customerID, customerName, customerCategory, phoneNo, email, address, priceGroup, billToCustomer, customerPostingGroup, businessGroup, vATBusinessGroup, referenceNo);
            String buttonText = btnSave.getText();
            if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {
                customerDA.save();
                message("Saved Successfully");
                clear();
                this.setNextCustomerID();
            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {
                customerDA.update();
                message("Updated Successfully");
            }
            this.dbAccess = customerDA;
            this.editSuccessful = true;
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void delete() {
        try {
            String customerID = getText(txtCustomerID, "Customer ID");
            CustomerDA customerDA = oCustomerDA.get(customerID);
            if (!warningOk("Confirm Delete", "You are about to delete a record with ID: " + customerID + " Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            if (customerDA.delete()) {
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
            String customerID = getText(txtCustomerID, "Customer ID");

            CustomerDA customerDA = oCustomerDA.get(customerID);
            txtCustomerID.setText(customerDA.getCustomerID());
            txtCustomerName.setText(customerDA.getCustomerName());
            cboCustomerCategory.setValue(customerDA.getCustomerCategory());
            txtPhoneNo.setText(customerDA.getPhoneNo());
            txtEmail.setText(customerDA.getEmail());
            txaAddress.setText(customerDA.getAddress());
            cboPriceGroup.setValue(customerDA.getPriceGroup());
            cboBillToCustomer.setValue(customerDA.getBillToCustomer());
            cboCustomerPostingGroup.setValue(customerDA.getCustomerPostingGroup());
            cboBusinessGroup.setValue(customerDA.getBusinessGroup());
            cboVATBusinessGroup.setValue(customerDA.getVATBusinessGroup());
            txtReferenceNo.setText(customerDA.getReferenceNo());
            txtBalance.setText(formatNumber(customerDA.getBalance()));

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void setNextCustomerID() {
        try {
            if (btnSave.getText().equalsIgnoreCase(FormMode.Save.name())) {
                txtCustomerID.setText(oCustomerDA.getNextCustomerID(oCustomerDA.getNextIdHelper()));
            }
        } catch (Exception e) {
            errorMessage(e);
        }
    }

    
}
