/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.controllers;

import com.saburi.common.dbaccess.DBAccess;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import com.saburi.common.entities.DBEntity;
import com.saburi.common.utils.CommonEnums.Rights;
import com.saburi.common.utils.CommonNavigate;
import com.saburi.common.utils.CurrentUser;
import static com.saburi.common.utils.FXUIUtils.close;
import static com.saburi.common.utils.FXUIUtils.errorMessage;
import static com.saburi.common.utils.FXUIUtils.getSelectedItem;
import com.saburi.common.utils.Utilities.FormMode;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 *
 * @author CLINICMASTER13
 */
public abstract class EditController implements Initializable {

    @FXML
    protected Button btnSave, btnSearch, btnDelete, btnClose;
    @FXML
    protected MenuItem cmiLoad;
    protected FormMode formMode = FormMode.Save;
    protected Control primaryKeyControl;
    protected String viewUI = "View";
    protected String editUI;
    protected String title;
    protected String currentUI;
    protected DBAccess dbAccess;
    protected boolean restrainColumnConstraint = false;
    protected float viewWidth = 700;
    protected float viewHight = 400;
    protected float minSize;
    protected float preferredWitdh;
    protected float preferredHeight;
    protected boolean popuprestrainConstraint = false;
    protected TableView tableView;
    protected boolean editSuccessful = false;
    protected Class mainClass = CommonNavigate.mainClass; 

    public void setEdit(Object primaryKeyValue) {
        this.formMode = FormMode.Update;
        btnSave.setText(FormMode.Update.name());
        btnSearch.setVisible(true);
        btnDelete.setVisible(true);

        if (primaryKeyControl instanceof TextField) {
            TextField textField = (TextField) primaryKeyControl;
            textField.setText(primaryKeyValue.toString());
        } else if (primaryKeyControl instanceof ComboBox) {
            ComboBox textField = (ComboBox) primaryKeyControl;
            textField.setValue(primaryKeyValue);
        }
        btnSearch.fire();
    }

    public FormMode getFormMode() {
        return formMode;
    }

    public void setFormMode(FormMode formMode) {
        this.formMode = formMode;
    }

    public void init(String title, FormMode formMode) {
        this.title = title;
        this.formMode = formMode;
        btnSave.setText(formMode.name());
        btnSearch.setVisible(formMode.equals(FormMode.Update));
        btnDelete.setVisible(formMode.equals(FormMode.Update));
        cmiLoad.setVisible(formMode.equals(FormMode.Update));
        CurrentUser.applyRights(btnSave, Rights.Create);
        CurrentUser.applyRights(btnDelete, Rights.Delete);
        CurrentUser.applyRights(cmiLoad, Rights.Read);
        if (formMode.equals(FormMode.Update)) {
            CurrentUser.applyRights(btnSave, Rights.Update);
            if (primaryKeyControl instanceof TextField) {
                TextField textField = (TextField) primaryKeyControl;
                textField.clear();
            } else if (primaryKeyControl instanceof ComboBox) {
                ComboBox textField = (ComboBox) primaryKeyControl;
                textField.setValue(null);
            }
        }

        btnSave.setOnAction(e -> {
            this.save();
            btnSave.disableProperty().set(formMode.equals(FormMode.Update));
            if (tableView != null && editSuccessful) {
                if (formMode.equals(FormMode.Update)) {
                    tableView.getItems().set(tableView.getSelectionModel().selectedIndexProperty().get(), dbAccess);
                    tableView.refresh();
                } else if (formMode.equals(FormMode.Save)) {
                    tableView.getItems().add(0, dbAccess);
                }
                btnClose.fire();
            }
        });
        btnClose.setOnAction(e -> close(btnClose));
        btnSearch.setOnAction(e -> {
            this.loadData();
            CurrentUser.applyRights(btnSave, Rights.Update);
        });
        btnDelete.setOnAction(e -> this.delete());
        cmiLoad.setOnAction(e -> {
            try {
                DBAccess selectedItem = getSelectedItem(this.mainClass, dbAccess, this.viewUI, title, viewWidth, viewHight, primaryKeyControl, restrainColumnConstraint);
                if (selectedItem == null) {
                    e.consume();
                    return;
                }
                if (primaryKeyControl instanceof ComboBox) {
                    ComboBox box = (ComboBox) primaryKeyControl;
                    DBEntity entity = selectedItem.getDBEntity();
                    if (!box.getItems().contains(entity)) {
                        box.getItems().add(0, entity);
                    }
                    box.setValue(entity);
                } else if (primaryKeyControl instanceof TextField) {
                    TextField field = (TextField) primaryKeyControl;
                    field.setText(selectedItem.getId().toString());

                }

                btnSearch.fire();
            } catch (IOException ex) {
                errorMessage(ex);
            }
        });

        btnSave.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.SAVE));
        btnClose.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.CLOSE));
        btnSearch.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.SEARCH));
        btnDelete.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.REMOVE));
    }

    protected abstract void save();

    protected abstract void delete();

    protected abstract void loadData();

    public String getEditUI() {
        return editUI;
    }

    public float getPreferredWitdh() {
        return preferredWitdh;
    }

    public float getPreferredHeight() {
        return preferredHeight;
    }

    public float getMinSize() {
        return minSize;
    }

    public DBAccess getDbAccess() {
        return dbAccess;
    }

    public TableView getTableView() {
        return tableView;
    }

    public void setTableView(TableView tableView) {
        this.tableView = tableView;
    }

}
