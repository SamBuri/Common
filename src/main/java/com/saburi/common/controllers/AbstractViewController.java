/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.controllers;

import com.saburi.common.dbaccess.DBAccess;
import com.saburi.common.entities.DBEntity;
import static com.saburi.common.utils.CurrentUser.applyRights;
import static com.saburi.common.utils.FXUIUtils.errorMessage;
import static com.saburi.common.utils.FXUIUtils.getText;
import static com.saburi.common.utils.FXUIUtils.loadSearchColumnCombo;
import static com.saburi.common.utils.FXUIUtils.searchColumnSelected;
import static com.saburi.common.utils.FXUIUtils.setSearch;
import static com.saburi.common.utils.FXUIUtils.warningOk;
import com.saburi.common.utils.Navigation;
import com.saburi.common.utils.SearchColumn;
import com.saburi.common.utils.Utilities;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import org.kordamp.desktoppanefx.scene.layout.DesktopPane;
import com.saburi.common.utils.Utilities.FormMode;

/**
 *
 * @author CLINICMASTER13
 */
public abstract class AbstractViewController implements Initializable {

    protected boolean popUp, constrainColumns;
    protected DBAccess selectedItem;
    protected DBAccess oDBAccess;

    protected FilteredList filteredList;
    protected List<? extends DBAccess> list = new ArrayList<>();
    protected String objectName, uiEdit, title;
    protected Class mainClass;
    protected float editUIWidth, EditUIHeight;
    protected ObservableList data;
    protected FilteredList fList;
    protected TableView tableView;

    @FXML
    protected TextField txtSearch, txtSecondValue;
    @FXML
    private MenuItem cmiNew, cmiUpdate, cmiPrint, cmiDelete, cmiSelectAll;
    @FXML
    private ComboBox<SearchColumn> cboSearchColumn;
    @FXML
    private ComboBox<SearchColumn.SearchType> cboSearchType;
    @FXML
    protected TableColumn tbcUserID;
    @FXML
    protected TableColumn tbcUserFullName;
    @FXML
    protected TableColumn tbcClientMachine;
    @FXML
    protected TableColumn tbcRecordDateTime;
    @FXML
    protected TableColumn tbcLastUpdateDateTime;
    @FXML
    protected ContextMenu cmuView;

    private List<SearchColumn> searchColumns;
    List<SearchColumn> defaultSearchColumns = new ArrayList<>();
    protected ObservableList selectedItems;
    protected Node editNode;
    protected boolean editable;
    protected boolean printable;

    public void setPopUp(boolean popUp) {
        this.popUp = popUp;

    }

    public boolean isPopUp() {
        return this.popUp;
    }

    public DBAccess getSelectedItem() {
        return selectedItem;
    }

    public void initSearchEvents() {
        if (this.tableView == null) {

        }
        this.tableView.setItems(FXCollections.observableArrayList());
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        cmiNew.setOnAction(e -> setUpdate(this.mainClass, this.uiEdit, this.title, FormMode.Save));
        cmiUpdate.setOnAction(e -> setUpdate(this.mainClass, this.uiEdit, this.title, FormMode.Update));
        cmiPrint.setOnAction(e -> setUpdate(this.mainClass, this.uiEdit, this.title, FormMode.Print));
        cmiDelete.setOnAction(e -> this.delete());
        cmiSelectAll.setOnAction(e -> tableView.getSelectionModel().selectAll());

        this.cboSearchColumn.setOnAction(e -> searchColumnSelected(this.cboSearchColumn, this.cboSearchType));

        cboSearchType.setOnAction(e -> {
            String searchType = getText(cboSearchType);
            this.txtSecondValue.setVisible(searchType.equals(SearchColumn.SearchType.Between.name()));
        });

        this.txtSearch.setOnKeyPressed(e -> {

            if (e.getCode() == KeyCode.ENTER) {

                setSearch(filteredList, cboSearchType, cboSearchColumn, txtSearch, txtSecondValue, tableView);
            }
        });
        this.txtSecondValue.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                setSearch(filteredList, cboSearchType, cboSearchColumn, txtSearch, txtSecondValue, tableView);
            }
        });

        tableView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2 && popUp) {
                this.selectedItem = (DBAccess) tableView.getSelectionModel().getSelectedItem();
                if (this.editNode != null) {
                    if (editNode instanceof ComboBox) {
                        ComboBox box = (ComboBox) editNode;
                        DBEntity entity = selectedItem.getDBEntity();
                        if (!box.getItems().contains(entity)) {
                            box.getItems().add(0, entity);

                        }

                        box.setValue(entity);
                    } else if (editNode instanceof TextField) {
                        TextField field = (TextField) editNode;
                        field.setText(selectedItem.getId().toString());

                    }
                    DesktopPane.resolveInternalWindow(this.tableView).closeWindow();
                } else {
                    this.tableView.getScene().getWindow().hide();
                }

            }

        });

    }

    public void loadTable() {

        this.data = FXCollections.observableArrayList(this.list);
        filteredList = new FilteredList<>(data, e -> true);

        FilteredList<SearchColumn> filteredSearchColumns = new FilteredList<>(FXCollections.observableArrayList(searchColumns), e -> true);
        filteredSearchColumns.setPredicate((P) -> P.isVisible());
        for (int i = 0; i < filteredSearchColumns.size(); i++) {
            final int x = i;
            SearchColumn sc = filteredSearchColumns.get(x);
            TableColumn<DBAccess, Object> tableColumn = new TableColumn(sc.getDisplayValue());
            tableColumn.setCellValueFactory(param
                    -> new ReadOnlyObjectWrapper<>(param.getValue().getSearchColumns().get(param.getValue().getSearchColumns().lastIndexOf(sc)).getValue())
            );
            tableView.getColumns().add(tableColumn);
        }
        filteredSearchColumns.forEach(sc -> {

        });

        tableView.setItems(FXCollections.observableArrayList(data));
        if (constrainColumns) {
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        }

        if (!data.isEmpty()) {

            loadSearchColumnCombo(FXCollections.observableArrayList(filteredSearchColumns), cboSearchColumn);
        }

        this.selectedItems = tableView.getSelectionModel().getSelectedItems();
//        cmiDelete.disableProperty().bind(Bindings.size(selectedItems).lessThan(1));
//        cmiUpdate.disableProperty().bind(Bindings.size(selectedItems).isNotEqualTo(1));
        cmiSelectAll.disableProperty().bind(Bindings.size(tableView.getItems()).lessThan(1));

        cmuView.setOnShowing((event) -> {
            this.viewContextMenuShowing();
        });

    }

    protected void setUpdate(Class type, String ui, String title, FormMode formMode) {

        try {

            switch (formMode) {
                case Save:
                    Navigation.loadEditUI(type, ui, title, "", tableView, Utilities.FormMode.Save, isPopUp());
                    break;
                case Update: {
                    DBAccess dBAccess = (DBAccess) tableView.getSelectionModel().getSelectedItem();
                    Object recordID = dBAccess.getId();
                    Navigation.loadEditUI(type, ui, title, recordID, tableView, Utilities.FormMode.Update, isPopUp());
                    break;
                }
                case Print: {
                    DBAccess dBAccess = (DBAccess) tableView.getSelectionModel().getSelectedItem();
                    Object recordID = dBAccess.getId();
                    Navigation.loadEditUI(type, ui, title, recordID, tableView, Utilities.FormMode.Print, isPopUp());
                    break;
                }
                default:
                    break;
            }

        } catch (IOException ex) {
            errorMessage(ex);
        }
    }

    protected void delete() {
        try {
            List<DBAccess> selectedList = tableView.getSelectionModel().getSelectedItems();
            if (!warningOk("Confirm Delete", "You are about to delete " + selectedList.size() + "record(s) Are you sure you want to continue?", "Remember this action cannot be un done")) {
                return;
            }
            selectedList.forEach(e -> {
                DBAccess dBAccess = (DBAccess) e;
                dBAccess.remove(dBAccess.getDBEntity());
                tableView.getItems().remove(e);
            });

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    public void setInitData(Class type, DBAccess oDBAccess, String objectName, float editUIWidth, float EditUIHeight,
            boolean constrainColumns) {
        this.constrainColumns = constrainColumns;
        this.oDBAccess = oDBAccess;
        this.list = oDBAccess.get();
        this.objectName = objectName;
        this.uiEdit = objectName;
        this.mainClass = type;
        this.title = objectName;
        this.editUIWidth = editUIWidth;
        this.EditUIHeight = EditUIHeight;
        loadTable();

    }

    public void setInitData(Class type, DBAccess oDBAccess, String objectName, boolean constrainColumns) {
        this.constrainColumns = constrainColumns;
        this.oDBAccess = oDBAccess;
        this.list = oDBAccess.get();
        this.searchColumns = this.oDBAccess.getSearchColumns();
        this.defaultSearchColumns = this.oDBAccess.getDefaultSearchColumns();
        this.objectName = objectName;
        this.uiEdit = objectName;
        this.mainClass = type;
        this.title = objectName;

        loadTable();

    }

    public void setInitData(Class type, DBAccess oDBAccess, String objectName, boolean constrainColumns,
            boolean editable, boolean printable) {
        this.constrainColumns = constrainColumns;
        this.oDBAccess = oDBAccess;
        this.list = oDBAccess.get();
        this.searchColumns = this.oDBAccess.getSearchColumns();
        this.defaultSearchColumns = this.oDBAccess.getDefaultSearchColumns();
        this.objectName = objectName;
        this.uiEdit = objectName;
        this.mainClass = type;
        this.title = objectName;
        this.editable = editable;
        this.printable = printable;
        loadTable();

    }

    public void setInitData(Class type, DBAccess oDBAccess, String objectName, boolean constrainColumns, boolean popup) {
        this.constrainColumns = constrainColumns;
        this.oDBAccess = oDBAccess;
        this.list = oDBAccess.get();
        this.searchColumns = this.oDBAccess.getSearchColumns();
        this.defaultSearchColumns = this.oDBAccess.getDefaultSearchColumns();
        this.popUp = popup;
        if (popup) {
            this.searchColumns.removeAll(this.oDBAccess.getDefaultSearchColumns());
        }
        this.objectName = objectName;
        this.uiEdit = objectName;
        this.mainClass = type;
        this.title = objectName;
        loadTable();

    }

    public void setInitData(Class type, DBAccess oDBAccess, String objectName, Node editNode, boolean constrainColumns, boolean popup) {
        this.constrainColumns = constrainColumns;
        this.oDBAccess = oDBAccess;
        this.list = oDBAccess.get();
        this.searchColumns = this.oDBAccess.getSearchColumns();
        this.defaultSearchColumns = this.oDBAccess.getDefaultSearchColumns();
        this.popUp = popup;
        if (popup) {
            this.searchColumns.removeAll(this.oDBAccess.getDefaultSearchColumns());
        }
        this.objectName = objectName;
        this.uiEdit = objectName;
        this.mainClass = type;
        this.title = objectName;
        this.editNode = editNode;
        loadTable();

    }

    public void setInitData(Class type, DBAccess oDBAccess, List<? extends DBAccess> dBAccesses, String objectName, boolean constrainColumns, boolean popup) {
        this.constrainColumns = constrainColumns;
        this.oDBAccess = oDBAccess;
        this.list = dBAccesses;
        this.searchColumns = oDBAccess.getSearchColumns();

        this.popUp = popup;
        if (popup) {
            this.searchColumns.removeAll(this.oDBAccess.getDefaultSearchColumns());
        }
        this.objectName = objectName;
        this.uiEdit = objectName;
        this.mainClass = type;
        this.title = objectName;
        loadTable();

    }

    public void setInitData(Class type, DBAccess oDBAccess, List<? extends DBAccess> dBAccesses, String objectName, Node editNode, boolean constrainColumns, boolean popup) {
        this.constrainColumns = constrainColumns;
        this.oDBAccess = oDBAccess;
        this.list = dBAccesses;
        this.searchColumns = oDBAccess.getSearchColumns();

        this.popUp = popup;
        if (popup) {
            this.searchColumns.removeAll(this.oDBAccess.getDefaultSearchColumns());
        }
        this.objectName = objectName;
        this.uiEdit = objectName;
        this.mainClass = type;
        this.title = objectName;
        this.editNode = editNode;
        loadTable();

    }

    public void setInitData(Class type, DBAccess oDBAccess, List<? extends DBAccess> dBAccesses,
            List<SearchColumn> searchColumns, String objectName, boolean constrainColumns) {
        try {
            this.constrainColumns = constrainColumns;
            this.oDBAccess = oDBAccess;
            this.list = dBAccesses;
            this.searchColumns = searchColumns;
            this.objectName = objectName;
            this.uiEdit = objectName;
            this.mainClass = type;
            this.title = objectName;
            loadTable();
        } catch (Exception e) {
            throw e;
        }

    }

    public void setInitRevData(DBAccess oDBAccess, String objectName, boolean constrainColumns) {
        try {
            this.constrainColumns = constrainColumns;
            this.oDBAccess = oDBAccess;
            this.list = oDBAccess.getRevisions();
            this.searchColumns = oDBAccess.getSearchColumns();
            this.searchColumns.removeAll(oDBAccess.getDefaultSearchColumns());
            this.searchColumns.addAll(oDBAccess.getRevSearchColumns());
            this.objectName = objectName;
            this.uiEdit = objectName;
            this.title = objectName;
            this.cmuView.hide();
            loadTable();
            this.cmiNew.setVisible(false);
            this.cmiUpdate.setVisible(false);
            this.cmiDelete.setVisible(false);
        } catch (Exception e) {
            throw e;
        }

    }

    protected void viewContextMenuShowing() {
        if (selectedItems.size() >= 1) {
            applyRights(objectName, cmiDelete);
        } else {
            cmiDelete.disableProperty().set(true);
        }
        if (selectedItems.size() == 1) {
            applyRights(objectName, cmiUpdate);
        } else {
            cmiUpdate.disableProperty().set(true);
        }
        if (selectedItems.size() == 1) {
            applyRights(objectName, cmiPrint);
        } else {
            cmiPrint.disableProperty().set(true);
        }
        applyRights(objectName, cmiNew);
        this.cmiUpdate.setVisible(this.editable);
        this.cmiPrint.setVisible(this.printable);
    }

}
