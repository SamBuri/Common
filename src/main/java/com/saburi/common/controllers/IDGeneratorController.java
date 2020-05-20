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
import com.saburi.common.dbaccess.IDGeneratorDA;
import javafx.scene.control.TextField;

public class IDGeneratorController extends EditController {

    private final IDGeneratorDA oIDGeneratorDA = new IDGeneratorDA();
    @FXML
    private TextField txtObjectName;
    @FXML
    private TextField txtPadChar;
    @FXML
    private TextField txtPadLength;
    @FXML
    private TextField txtSeperator;
    @FXML
    private TextField txtSeporatorPosition;
    @FXML
    private TextField txtStartWith;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            validateIteger(txtPadLength);
            validateIteger(txtSeporatorPosition);
            this.primaryKeyControl = txtObjectName;
            this.dbAccess = oIDGeneratorDA;
            this.restrainColumnConstraint = false;
            this.prefSize = 280;
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void save() {
        try {
            this.editSuccessful = false;
            String objectName = getText(txtObjectName, "Object Name");
            String padChar = getText(txtPadChar, "Pad Char");
            int padLength = getInt(txtPadLength, "PadLength");
            String seperator = getText(txtSeperator);
            int seporatorPosition = getInt(txtSeporatorPosition);
            String startWith = getText(txtStartWith);

            IDGeneratorDA iDGeneratorDA = new IDGeneratorDA(objectName, padChar, padLength, seperator, seporatorPosition, startWith);
            String buttonText = btnSave.getText();
            if (buttonText.equalsIgnoreCase(FormMode.Save.name())) {
                iDGeneratorDA.save();
                message("Saved Successfully");
                clear();
            } else if (buttonText.equalsIgnoreCase(FormMode.Update.name())) {
                iDGeneratorDA.update();
                message("Updated Successfully");
            }
            this.dbAccess =iDGeneratorDA;
            this.editSuccessful = true;
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void delete() {
        try {
            String objectName = getText(txtObjectName, "Object Name");
            IDGeneratorDA iDGeneratorDA = oIDGeneratorDA.get(objectName);
            if (!warningOk("Confirm Delete", "You are about to delete a record with ID: " + objectName + " Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            if (iDGeneratorDA.delete()) {
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
            String objectName = getText(txtObjectName, "Object Name");

            IDGeneratorDA iDGeneratorDA = oIDGeneratorDA.get(objectName);
            txtObjectName.setText(iDGeneratorDA.getObjectName());
            txtPadChar.setText(iDGeneratorDA.getPadChar());
            txtPadLength.setText(String.valueOf(iDGeneratorDA.getPadLength()));
            txtSeperator.setText(iDGeneratorDA.getSeperator());
            txtSeporatorPosition.setText(String.valueOf(iDGeneratorDA.getSeporatorPosition()));
            txtStartWith.setText(iDGeneratorDA.getStartWith());

        } catch (Exception e) {
            errorMessage(e);
        }

    }

  

}
