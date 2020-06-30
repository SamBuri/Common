/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.controllers;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.saburi.common.dbaccess.DBAccess;
import com.saburi.common.entities.DBEntity;
import com.saburi.common.utils.CommonEnums.AggregateFunctions;
import com.saburi.common.utils.CommonEnums.LogicalOperators;
import com.saburi.common.utils.CommonEnums.ViewMenuTypes;
import com.saburi.common.utils.CommonNavigate;
import static com.saburi.common.utils.CurrentUser.applyRights;
import com.saburi.common.utils.FXUIUtils;
import static com.saburi.common.utils.FXUIUtils.copySelectionToClipboard;
import static com.saburi.common.utils.FXUIUtils.errorMessage;
import static com.saburi.common.utils.FXUIUtils.formatDatePicker;
import static com.saburi.common.utils.FXUIUtils.getDate;
import static com.saburi.common.utils.FXUIUtils.getDouble;
import static com.saburi.common.utils.FXUIUtils.getText;
import static com.saburi.common.utils.FXUIUtils.installCopyPasteHandler;
import static com.saburi.common.utils.FXUIUtils.loadSearchColumnCombo;
import static com.saburi.common.utils.FXUIUtils.makeCSV;
import static com.saburi.common.utils.FXUIUtils.makeExcel;
import static com.saburi.common.utils.FXUIUtils.makeText;
import static com.saburi.common.utils.FXUIUtils.warningOk;
import com.saburi.common.utils.JavaFXPDF;
import com.saburi.common.utils.Navigation;
import com.saburi.common.utils.SearchColumn;
import com.saburi.common.utils.SearchColumn.SearchDataTypes;
import com.saburi.common.utils.SearchColumn.SearchType;
import com.saburi.common.utils.SearchCriteria;
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
import java.util.Comparator;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 *
 * @author Sam Buriima
 */
public abstract class AbstractViewController implements Initializable {

    protected boolean popUp, constrainColumns;
    protected DBAccess selectedItem;
    protected DBAccess oDBAccess;

    protected FilteredList filteredList;
    protected List<? extends DBAccess> list;
    protected String objectName, uiEdit, title;
    protected Class mainClass;
    protected float editUIWidth, EditUIHeight;
    protected ObservableList data;
//    protected FilteredList fList;
    protected TableView<DBAccess> tableView;
    @FXML
    protected HBox hbxTextFields, hbxDatePickers, hbxCheckBox, hbxAggregates;
    @FXML
    protected DatePicker dtpFirst, dtpSecond;
    @FXML
    protected TextField txtSearch, txtSecondValue, txtggregate;
    @FXML
    protected Label lblReturnedRecords;
    @FXML
    private MenuItem cmiNew, cmiUpdate, cmiPrint, cmiPreview, cmiDelete, cmiSelectAll, mnuCSV, mnuPDF, mnuExcel, mnuText, cmiCopy;
    @FXML
    private ComboBox<SearchColumn> cboSearchColumn, cboAggregateColumn;
    @FXML
    private ComboBox<String> cboSearchType;
    @FXML
    private ComboBox<AggregateFunctions> cboAggregate;
    @FXML
    private CheckBox chkValue;
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
    @FXML
    protected Button btnClearFilters, btnMultipleFilters;
    private List<SearchColumn> searchColumns;
    List<SearchColumn> defaultSearchColumns = new ArrayList<>();
    protected ObservableList selectedItems;
    protected Node editNode;
//    protected boolean editable = true;
//    protected boolean printable = false;
    protected ViewMenuTypes viewMenuType;
    protected List<SearchCriteria> searchCriterias;

    public void setPopUp(boolean popUp) {
        this.popUp = popUp;

    }

    public boolean isPopUp() {
        return this.popUp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<? extends DBAccess> getList() {
        return list;
    }

    public void setList(List<? extends DBAccess> list) {
        this.list = list;
    }

    public List<SearchColumn> getSearchColumns() {
        return searchColumns;
    }

    public void setSearchColumns(List<SearchColumn> searchColumns) {
        this.searchColumns = searchColumns;
    }

    public DBAccess getSelectedItem() {
        return selectedItem;
    }

    public Node getEditNode() {
        return editNode;
    }

    public void setEditNode(Node editNode) {
        this.editNode = editNode;
    }

    public void setEditUIWidth(float editUIWidth) {
        this.editUIWidth = editUIWidth;
    }

    public void setConstrainColumns(boolean constrainColumns) {
        this.constrainColumns = constrainColumns;
    }

    public ViewMenuTypes getViewMenuType() {
        return viewMenuType;
    }

    public void setViewMenuType(ViewMenuTypes viewMenuType) {
        this.viewMenuType = viewMenuType;
    }

    private void searchColumnSelected() {
        SearchColumn searchColumn = cboSearchColumn.getValue();
        cboSearchType.setValue(null);
        if (searchColumn == null) {
            hbxTextFields.setVisible(true);
            hbxDatePickers.setVisible(false);
            hbxCheckBox.setVisible(false);
            return;
        }
        ObservableList searchTypes = FXCollections.observableList(searchColumn.getSearchTypes());
        cboSearchType.setItems(searchTypes);
        String searchDataType = searchColumn.getDataType().name();
        cboSearchType.setValue(searchColumn.getDefaultSearchType().name());

        if (searchDataType.equalsIgnoreCase(SearchDataTypes.DATE.name())) {
            hbxTextFields.setVisible(false);
            hbxDatePickers.setVisible(true);
            hbxCheckBox.setVisible(false);
        } else if (searchDataType.equalsIgnoreCase(SearchColumn.SearchDataTypes.BOOLEAN.name())) {
            hbxTextFields.setVisible(false);
            hbxDatePickers.setVisible(false);
            hbxCheckBox.setVisible(true);
            cboSearchType.setValue(SearchType.Equal.name());
        } else {
            hbxTextFields.setVisible(true);
            hbxDatePickers.setVisible(false);
            hbxCheckBox.setVisible(false);
        }

    }

    public void initSearchEvents() {

        formatDatePicker(dtpFirst);
        formatDatePicker(dtpSecond);

        this.tableView.setItems(FXCollections.observableArrayList());
        this.tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView.getSelectionModel().setCellSelectionEnabled(true);
        installCopyPasteHandler(tableView);
        cmiNew.setOnAction(e -> setUpdate(this.mainClass, this.uiEdit, FormMode.Save));
        cmiUpdate.setOnAction(e -> setUpdate(this.mainClass, this.uiEdit, FormMode.Update));
        cmiPrint.setOnAction(e -> setUpdate(this.mainClass, this.uiEdit, FormMode.Print));
        cmiPreview.setOnAction(e -> setUpdate(this.mainClass, this.uiEdit, FormMode.Preview));
        cmiCopy.setOnAction(e -> copySelectionToClipboard(tableView));
        cmiDelete.setOnAction(e -> this.delete());
        cmiSelectAll.setOnAction(e -> tableView.getSelectionModel().selectAll());
        cboAggregateColumn.setOnAction(e -> calculateAggregates());
        cboAggregate.setOnAction(e -> calculateAggregates());
        this.btnClearFilters.setOnAction(e -> {
            tableView.setItems(data);
            lblReturnedRecords.setText(data.size() + " record");
            calculateAggregates();
            btnClearFilters.setDisable(true);
        });
        this.cboSearchColumn.setOnAction(e -> searchColumnSelected());

        cboSearchType.setOnAction(e -> {
            String searchType = getText(cboSearchType);
            boolean showSecond = searchType.equals(SearchType.Between.name()) || searchType.equals(SearchType.Between_Inclusive.name());
            this.txtSecondValue.setVisible(showSecond);
            this.dtpSecond.setVisible(showSecond);
        });

        this.txtSearch.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                setSearchCriteria();
            }
        });
        this.txtSecondValue.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                setSearchCriteria();
            }
        });

        this.dtpFirst.setOnKeyPressed(e -> {

            if (e.getCode() == KeyCode.ENTER) {
                dtpFirst.setValue(dtpFirst.getConverter().fromString(dtpFirst.getEditor().getText()));
                setSearchCriteria();
            }
        });
        this.dtpSecond.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                dtpSecond.setValue(dtpSecond.getConverter().fromString(dtpSecond.getEditor().getText()));
                setSearchCriteria();
            }
        });

        chkValue.setOnAction(e -> setSearchCriteria());

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

        mnuPDF.setOnAction(e -> {
            try {
                new JavaFXPDF(objectName, PageSize.A2).makeTable(tableView, title);
            } catch (IOException | DocumentException ex) {
                errorMessage(ex);
            }
        });

        mnuCSV.setOnAction(e -> makeCSV(tableView, objectName));
        mnuExcel.setOnAction(e -> makeExcel(tableView, title));
        mnuText.setOnAction(e -> makeText(tableView, objectName));
        btnMultipleFilters.setOnAction(e -> {
            try {
                this.showDialog("SearchCriteria", "Search Criteria", btnMultipleFilters);
            } catch (IOException ex) {
                errorMessage(ex);
            }
        });

    }

    public void loadTable() {

        try {
            if (this.searchColumns == null) {
                this.searchColumns = this.oDBAccess.getSearchColumns();
            }
            this.defaultSearchColumns = this.oDBAccess.getDefaultSearchColumns();
            if (isPopUp()) {
                this.searchColumns.removeAll(this.oDBAccess.getDefaultSearchColumns());
            }

            if (this.list == null) {
                this.list = oDBAccess.get();
            }

            this.data = FXCollections.observableArrayList(this.list);
            filteredList = new FilteredList<>(data, e -> true);

            FilteredList<SearchColumn> filteredSearchColumns = new FilteredList<>(FXCollections.observableArrayList(searchColumns), e -> true);
            filteredSearchColumns.setPredicate((P) -> P.isVisible());
            for (int i = 0; i < filteredSearchColumns.size(); i++) {
                final int x = i;
                SearchColumn sc = filteredSearchColumns.get(x);

                if (sc.getDataType().equals(SearchColumn.SearchDataTypes.BOOLEAN)) {
                    TableColumn<DBAccess, Boolean> tableColumn = new TableColumn(sc.getCaption());

                    tableColumn.setCellValueFactory((TableColumn.CellDataFeatures<DBAccess, Boolean> param) -> {

                        Object value = param.getValue().getSearchColumns().get(param.getValue().getSearchColumns().lastIndexOf(sc)).getValue();
                        SimpleBooleanProperty valueBooleanProperty = new SimpleBooleanProperty(value == null ? false : Boolean.valueOf(value.toString()));
                        valueBooleanProperty.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
                            valueBooleanProperty.set(newValue);
                        });
                        return valueBooleanProperty;
                    });
                    tableView.getColumns().add(tableColumn);
                    final Callback<TableColumn<DBAccess, Boolean>, TableCell<DBAccess, Boolean>> cellFCallback = CheckBoxTableCell.forTableColumn(tableColumn);
                    tableColumn.setCellFactory(cellFCallback);

                } else {
                    TableColumn<DBAccess, Object> tableColumn = new TableColumn(sc.getCaption());
                    tableColumn.setCellValueFactory(param
                            -> new ReadOnlyObjectWrapper<>(param.getValue().getSearchColumns()
                                    .get(param.getValue().getSearchColumns().lastIndexOf(sc)).getDispValue() != null
                                    ? param.getValue().getSearchColumns().get(param.getValue().getSearchColumns().lastIndexOf(sc)).getDispValue()
                                    : param.getValue().getSearchColumns().get(param.getValue().getSearchColumns().lastIndexOf(sc)).getValue())
                    );
                    tableView.getColumns().add(tableColumn);
                }

            }

            tableView.setItems(FXCollections.observableArrayList(data));

            lblReturnedRecords.setText(data.size() + ": records");
            if (constrainColumns) {
                tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            }

            if (!data.isEmpty()) {

                loadSearchColumnCombo(FXCollections.observableArrayList(filteredSearchColumns), cboSearchColumn);
                cboAggregate.setItems(FXCollections.observableArrayList(AggregateFunctions.values()));
                ObservableList aggreateColumn = filteredSearchColumns
                        .filtered((p) -> p.getDataType().name().equalsIgnoreCase(SearchDataTypes.NUMBER.name())
                        || p.getDataType().name().equalsIgnoreCase(SearchDataTypes.MONEY.name())
                        || p.getDataType().name().equalsIgnoreCase(SearchDataTypes.INTEGER.name()));
                hbxAggregates.setVisible(aggreateColumn.size() > 0);
                loadSearchColumnCombo(aggreateColumn, cboAggregateColumn);
            }

            this.selectedItems = tableView.getSelectionModel().getSelectedItems();
//        cmiDelete.disableProperty().bind(Bindings.size(selectedItems).lessThan(1));
//        cmiUpdate.disableProperty().bind(Bindings.size(selectedItems).isNotEqualTo(1));
//           cmiCopy.disableProperty().bind(Bindings.size(selectedItems).isNotEqualTo(1));
            cmiSelectAll.disableProperty().bind(Bindings.size(tableView.getItems()).lessThan(1));

            cmuView.setOnShowing((event) -> {
                this.viewContextMenuShowing();
            });

        } catch (Exception e) {
            errorMessage(e);
        }
    }

    private void setSearchCriteria() {
        try {
            LogicalOperators operator = LogicalOperators.AND;
            SearchColumn searchColumn = cboSearchColumn.getValue();
            Predicate<DBAccess> predicate;
            if (searchColumn == null || searchColumn.getName().isBlank()) {
                String searchType = getText(cboSearchType);
                if (searchType.isBlank()) {
                    searchType = SearchType.Contains.name();
                }
                String value = txtSearch.getText();
                predicate = SearchCriteria.makePredicate(searchColumns, value, searchType, false);

            } else {
                String searchType = getText(cboSearchType, "Search Type");
                SearchDataTypes searchDateType = searchColumn.getDataType();
                boolean enforceSecondValue = txtSecondValue.isVisible();
                Object value;
                Object secondValue;
                switch (searchDateType) {
                    case DATE:
                        value = getDate(dtpFirst, "First Date");
                        secondValue = getDate(dtpSecond, "Second Date", enforceSecondValue);
                        break;
                    case BOOLEAN:
                        value = chkValue.isSelected();
                        secondValue = null;
                        break;
                    case NUMBER:
                        value = getDouble(txtSearch, "Value");
                        secondValue = getDouble(txtSecondValue, "Second Value", enforceSecondValue);
                        break;
                    case MONEY:
                        value = getDouble(txtSearch, "Value");
                        secondValue = getDouble(txtSecondValue, "Second Value", enforceSecondValue);
                        break;
                    case INTEGER:
                        value = getDouble(txtSearch, "Value");
                        secondValue = getDouble(txtSecondValue, "Second Value", enforceSecondValue);
                        break;
                    default:
                        value = getText(txtSearch, "Value");
                        secondValue = getText(txtSecondValue, "Second Value", enforceSecondValue);
                        break;
                }

                SearchCriteria searchCriteria = new SearchCriteria(operator, searchColumn, searchType, value, secondValue, false);
                if (!enforceSecondValue) {
                    searchCriteria.setSecondValue("");
                }
                predicate = searchCriteria.makePredicate();
            }
            this.filteredList.setPredicate(predicate);
            int size = FXUIUtils.setFilteredItemstoTable(tableView, this.filteredList).size();
            String records = size == 1 ? size + " record" : size + " records";
            lblReturnedRecords.setText(records);
            calculateAggregates();
            btnClearFilters.disableProperty().set(false);
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    private void calculateAggregates() {
        try {
            txtggregate.clear();

            SearchColumn searchColumn = cboAggregateColumn.getValue();
            AggregateFunctions aggregateFunction = cboAggregate.getValue();

            if (searchColumn == null) {
                return;
            }

            if (aggregateFunction == null) {
                return;
            }
            List<DBAccess> dBAccesses = tableView.getItems();
            SearchDataTypes searchDataType = searchColumn.getDataType();
            double sum = 0;
            List<Double> numbers = new ArrayList();
            for (DBAccess dba : dBAccesses) {
                double value = dba.getSearchColumns()
                        .parallelStream()
                        .filter((p) -> p.getName().equalsIgnoreCase(searchColumn.getName()))
                        .mapToDouble((p) -> Utilities.getDouble(p.getValue()))
                        .findAny().orElse(0);
                sum += value;
                numbers.add(value);
            }
            double number;
            switch (aggregateFunction) {
                case Sum:
                    number = sum;
                    if (searchDataType.equals(SearchDataTypes.INTEGER)) {
                        txtggregate.setText(Utilities.formatInteger(number));
                    } else {
                        txtggregate.setText(Utilities.formatNumber(number));
                    }
                    break;
                case Average:
                    txtggregate.setText(Utilities.formatNumber(numbers.stream().collect(Collectors.averagingDouble(Double::doubleValue))));
                    break;
                case Minimum:
                    number = numbers.parallelStream()
                            .sorted()
                            .findFirst().get();
                    if (searchDataType.equals(SearchDataTypes.INTEGER)) {
                        txtggregate.setText(Utilities.formatInteger(number));
                    } else {
                        txtggregate.setText(Utilities.formatNumber(number));
                    }
                    break;
                case Maximum:
                    number = numbers.parallelStream()
                            .sorted(Comparator.reverseOrder())
                            .findFirst().get();

                    if (searchDataType.equals(SearchDataTypes.INTEGER)) {
                        txtggregate.setText(Utilities.formatInteger(number));
                    } else {
                        txtggregate.setText(Utilities.formatNumber(number));
                    }
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            errorMessage(e);
        }
    }

    protected void setUpdate(Class type, String ui, FormMode formMode) {

        try {

            switch (formMode) {
                case Save:
                    Navigation.loadEditUI(type, ui, this.title, "", tableView, Utilities.FormMode.Save, isPopUp());
                    break;
                case Update: {
                    DBAccess dBAccess = (DBAccess) tableView.getSelectionModel().getSelectedItem();
                    Object recordID = dBAccess.getId();
                    Navigation.loadEditUI(type, ui, this.title, recordID, tableView, Utilities.FormMode.Update, isPopUp());
                    break;
                }
                case Print: {
                    DBAccess dBAccess = (DBAccess) tableView.getSelectionModel().getSelectedItem();
                    Object recordID = dBAccess.getId();
                    Navigation.loadEditUI(type, ui, title, recordID, tableView, Utilities.FormMode.Print, isPopUp());
                    break;
                }
                
                case Preview: {
                    DBAccess dBAccess = (DBAccess) tableView.getSelectionModel().getSelectedItem();
                    Object recordID = dBAccess.getId();
                    Navigation.loadEditUI(type, ui, title, recordID, tableView, Utilities.FormMode.Preview, isPopUp());
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

    public void setInitData(Class type, DBAccess oDBAccess, String objectName, boolean constrainColumns) {
        this.constrainColumns = constrainColumns;
        this.oDBAccess = oDBAccess;
        this.objectName = objectName;
        this.uiEdit = objectName;
        this.mainClass = type;
        this.title = objectName;
        loadTable();

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
            this.cmiPrint.setVisible(false);
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
        this.showMenuOptions();
    }

    private void showDialog(String uiName, String title,
            Node node) throws IOException {
        try {

            FXMLLoader loader = CommonNavigate.getUILoader(uiName);
            Parent root = loader.load();
            SearchCriteriaController controller = loader.<SearchCriteriaController>getController();
            controller.loadSearchColumns(cboSearchColumn.getItems());
            if (searchCriterias != null) {
                controller.loadTable(FXCollections.observableList(searchCriterias));
            }
            Scene scene = new Scene(root, 800, 500);
            scene.getStylesheets().add(Navigation.styleControls);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(node.getScene().getWindow());
            stage.showAndWait();
            if (controller.isResultOk()) {
                Predicate<DBAccess> predicate = controller.getPredicate();
                this.filteredList.setPredicate(predicate);
                int size = FXUIUtils.setFilteredItemstoTable(tableView, this.filteredList).size();
                String records = size == 1 ? size + " record" : size + " records";
                lblReturnedRecords.setText(records);
                this.searchCriterias = controller.getSearchCriterias();
                calculateAggregates();
                this.btnClearFilters.disableProperty().set(false);
            }
        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

    }

    private void showMenuOptions() {
        boolean visible = false;
        switch (this.viewMenuType) {
            case None:
                this.cmiDelete.setVisible(false);
                this.cmiNew.setVisible(visible);
                this.cmiUpdate.setVisible(visible);
                this.cmiPrint.setVisible(visible);
                break;
            case PrintNew:
                this.cmiDelete.setVisible(false);
                this.cmiNew.setVisible(true);
                this.cmiUpdate.setVisible(false);
                this.cmiPrint.setVisible(false);
                break;
                case Preview:
                this.cmiDelete.setVisible(false);
                this.cmiNew.setVisible(false);
                this.cmiUpdate.setVisible(false);
                this.cmiPrint.setVisible(false);
                this.cmiPreview.setVisible(true);
                break;
            case EditOnly:
                this.cmiDelete.setVisible(false);
                this.cmiNew.setVisible(false);
                this.cmiUpdate.setVisible(true);
                this.cmiPrint.setVisible(visible);
                break;
            default:
                break;
        }

    }

}
