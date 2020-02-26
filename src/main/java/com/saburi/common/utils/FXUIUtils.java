/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

//import com.sun.javafx.scene.control.skin.TableViewSkin;

import com.saburi.common.controllers.ViewController;
import com.saburi.common.dbaccess.DBAccess;
import com.saburi.common.dbaccess.LookupDataDA;
import com.saburi.common.entities.DBEntity;
import com.saburi.common.entities.LookupData;
import static com.saburi.common.utils.Utilities.isNullOrEmpty;
import com.saburi.common.controllers.CaptureController;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javafx.scene.control.skin.TableViewSkin;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Pair;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kordamp.desktoppanefx.scene.layout.DesktopPane;
import org.kordamp.desktoppanefx.scene.layout.InternalWindow;

/**
 *
 * @author ClinicMaster13
 */
public class FXUIUtils {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void browse(TextField textField) {
        try {

            FileChooser fileChooser = new FileChooser();
//                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//                fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setTitle("Choose File");
            File file = fileChooser.showOpenDialog(null);
            String path = file.getPath();
            textField.setText(path);
        } catch (Exception e) {
        }
    }

    public static void browseSave(TextField textField) {
        try {

            FileChooser fileChooser = new FileChooser();
//                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//                fileChooser.getExtensionFilters().add(extFilter);
            fileChooser.setTitle("Choose File");
            File file = fileChooser.showSaveDialog(null);
            String path = file.getPath();
            textField.setText(path);
        } catch (Exception e) {
        }
    }

    public static void browseImage(ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(null);
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

    }

    public static void browseDirectory(TextField textField) {
        try {

            DirectoryChooser directoryChooser = new DirectoryChooser();
//                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//                directoryChooser.getExtensionFilters().add(extFilter);
            directoryChooser.setTitle("Choose Location");
            File file = directoryChooser.showDialog(null);
            String path = file.getAbsolutePath();
            textField.setText(path);
        } catch (Exception e) {
        }
    }

    public static void browseDirectory(TextField textField, Stage stage) {
        try {

            DirectoryChooser directoryChooser = new DirectoryChooser();
//                FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//                directoryChooser.getExtensionFilters().add(extFilter);
            directoryChooser.setTitle("Choose Location");
            File file = directoryChooser.showDialog(stage);
            String path = file.getAbsolutePath();
            textField.setText(path);
        } catch (Exception e) {
        }
    }

    public static void validateIteger(TextField field) {
        field.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d*]", ""));
            }
        });
    }

    public static void validateNumber(TextField field) {
        field.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*\\.")) {
                field.setText(newValue.replaceAll("[^\\d*\\.\\,]", ""));
            }
        });

    }

    public static void validateNumber(TextField field, TextArea tArea) {
        field.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!newValue.matches("\\d*\\.")) {
                field.setText(newValue.replaceAll("[^\\d*\\.\\,]", ""));
                tArea.setText(NumberToWord.toWords(getDouble(field)));
            }
        });

    }

    public static void formatValue(TextField field) {
        field.focusedProperty().addListener((ov, t, t1) -> {
            if (t) {
                try {
                    field.setText(Utilities.formatNumber(getDouble(field)));
                } catch (Exception ex) {
                    errorMessage(ex);
                }
            }
        });

    }

    public static void formatInteger(TextField field) {
        field.focusedProperty().addListener((ov, t, t1) -> {
            if (t) {
                try {
                    field.setText(Utilities.formatInteger(getInt(field)));
                } catch (Exception ex) {
                    errorMessage(ex);
                }
            }
        });

    }

    public static void setTableAUtoFill(TableView tableView) {
        Method columnToFitMethod;
        try {
            columnToFitMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
            columnToFitMethod.setAccessible(true);
            tableView.getItems().addListener(new ListChangeListener<Object>() {
                @Override
                public void onChanged(ListChangeListener.Change<? extends Object> c) {
                    for (Object column : tableView.getColumns()) {
                        try {
                            columnToFitMethod.invoke(tableView.getSkin(), column, -1);

                        } catch (IllegalAccessException ex) {
                            errorMessage(ex);
                        } catch (IllegalArgumentException ex) {
                            errorMessage(ex);
                        } catch (InvocationTargetException ex) {
                            errorMessage(ex);
                        }

                    }
                }
            });

        } catch (NoSuchMethodException | SecurityException e) {
            errorMessage(e);
        }

    }

    public static InternalWindow getInternalWindow(Node node) {
        return DesktopPane.resolveInternalWindow(node);
    }

    public static DesktopPane getDesktopPane(Node node) {
        return DesktopPane.resolveInternalWindow(node).getDesktopPane();
    }

    public static void setInternalFrame(Node base, Node content, String title) {
        InternalWindow window = new InternalWindow(title, null, title, content);
        window.setMinWidth(360);
        window.setResizable(false);
        getDesktopPane(base).addInternalWindow(window);
    }

    public static void setInternalFrame(Node base, Node content, String title, int size) {
        InternalWindow window = new InternalWindow(title, null, title, content);
        window.setMinWidth(size);
        window.setResizable(false);
        getDesktopPane(base).addInternalWindow(window);
    }

    public static void close(Node node) {
        try {

            DesktopPane.resolveInternalWindow(node).closeWindow();
        } catch (Exception e) {
            node.getScene().getWindow().hide();
        } finally {
        }
    }

    public static void errorMessage(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
        alert.setTitle("Error!");
        alert.setHeaderText(null);

        Throwable cause = e.getCause();
        String message = cause == null ? e.getMessage() : cause.getMessage();
        alert.setContentText(message);
        alert.show();
        e.printStackTrace();
        LOGGER.error(e, e);
        LOGGER.error(e);
        
    }

    public static void errorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setTitle("Error!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
        LOGGER.error(message);

    }

    public static void errorMessage(String title, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
e.printStackTrace();
        alert.show();
        LOGGER.error(e, e);
        LOGGER.error(e);

    }

    public static void errorMessage(String title, Throwable e) {
        Alert alert = new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage());
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(e.getLocalizedMessage());
e.printStackTrace();
        alert.show();
        LOGGER.error(e, e);
        LOGGER.error(e);

    }

    public static Optional<DBAccess> getChoiceDialog(List<DBAccess> dBAccesses, String title, String headerText, String contentText) {
        ChoiceDialog<DBAccess> dialog = new ChoiceDialog<>(new DBAccess(), dBAccesses);

        dialog.getResultConverter().call(ButtonType.APPLY);
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);
        return dialog.showAndWait();
    }

    public static Optional<DBEntity> getDADialog(String title, String buttonText, String label,
            List<? extends DBEntity> dBEntities, Window window) {

        Dialog<DBEntity> dialog = new Dialog<>();
        dialog.initOwner(window);
        dialog.setTitle(title);

// Set the icon (must be included in the project).
//        dialog.setGraphic(new ImageView(FXUIUtils.class.getResource("login.png").toString()));
// Set the button types.
        ButtonType confirmButton = new ButtonType(buttonText, ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        ComboBox<DBEntity> combobox = new ComboBox();
        combobox.setMinWidth(185);
        loadDBEntities(dBEntities, combobox);
        Label l = new Label(label);
        l.setMinWidth(100);
        grid.add(l, 0, 0);
        grid.add(combobox, 1, 0);

        Node dialogButton = dialog.getDialogPane().lookupButton(confirmButton);
        dialogButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        combobox.valueProperty().addListener((observable, oldValue, newValue) -> {
            dialogButton.setDisable(newValue == null);
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> combobox.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(button -> {
            if (button == confirmButton) {
                return combobox.getValue();
            }
            return null;
        });

        return dialog.showAndWait();

    }

    public static Optional<DBEntity> getDADialog(String title, String headerText, String buttonText, String label,
            List<? extends DBEntity> dbEntities, Window window) {

        Dialog<DBEntity> dialog = new Dialog<>();
        dialog.initOwner(window);
        dialog.setTitle(title);
        dialog.setHeaderText(headerText);

// Set the icon (must be included in the project).
//        dialog.setGraphic(new ImageView(FXUIUtils.class.getResource("login.png").toString()));
// Set the button types.
        ButtonType confirmButton = new ButtonType(buttonText, ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        ComboBox<DBEntity> combobox = new ComboBox();
        combobox.setMinWidth(185);
        loadDBEntities(dbEntities, combobox);
        grid.add(new Label(label), 0, 0);
        grid.add(combobox, 1, 0);

        Node dialogButton = dialog.getDialogPane().lookupButton(confirmButton);
        dialogButton.setDisable(true);

// Do some validation (using the Java 8 lambda syntax).
        combobox.valueProperty().addListener((observable, oldValue, newValue) -> {
            dialogButton.setDisable(newValue == null);
        });

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> combobox.requestFocus());

// Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(button -> {
            if (button == confirmButton) {
                return combobox.getValue();
            }
            return null;
        });

        return dialog.showAndWait();

    }

    public static ImageView getImage(ImageView imageView, byte[] bytes) {
        if (bytes != null) {
            InputStream in = new ByteArrayInputStream(bytes);
            Image image = new Image(in);
            imageView.setImage(image);
        }
        return imageView;
    }

    public static ImageView setImage(ImageView imageView, byte[] bytes, float width, float height) {
        if (bytes != null) {
            InputStream in = new ByteArrayInputStream(bytes);
            Image image = new Image(in, width, height, true, true);
            imageView.setImage(image);
        }
        return imageView;
    }

    public static ImageView getImage(ImageView imageView, byte[] bytes, float width, float height) {
        if (bytes != null) {
            InputStream in = new ByteArrayInputStream(bytes);
            Image image = new Image(in, width, height, true, true);
            imageView.setImage(image);
        }
        return imageView;
    }

    public static ImageView setImage(ImageView imageView, byte[] bytes) {
        if (bytes != null) {
            InputStream in = new ByteArrayInputStream(bytes);
            Image image = new Image(in);
            imageView.setImage(image);
        }
        return imageView;
    }

    public static ImageView setTableSizeImage(ImageView imageView, byte[] bytes) {
        if (bytes != null) {
            InputStream in = new ByteArrayInputStream(bytes);
            Image image = new Image(in, 50, 50, true, true);
            imageView.setImage(image);
        }
        return imageView;
    }

    public static void printError(Exception e) {
        e.printStackTrace();
    }

    public static void message(Object message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message.toString());
        alert.setHeaderText(null);
        alert.show();
    }

    public static void message(Window window, Object message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message.toString());
        alert.setHeaderText(null);
        alert.initOwner(window);
        alert.show();
    }

    public static void message(String title, Object message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message.toString());
        alert.show();
    }

    public static Optional<ButtonType> warningMessage(String title, String headerText, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    public static Optional<ButtonType> warningMessage(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }

    public static boolean warningOk(String title, String headerText, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        return alert.showAndWait().get() == ButtonType.OK;
    }

    public static boolean warningOK(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().get() == ButtonType.OK;
    }

    public static void loadCombo(List<Pair<String, Object>> pairs, ComboBox<Pair<String, Object>> combo) {

        Pair<String, Object> EMPTY_PAIR = new Pair<>("", "");
        combo.getItems().add(EMPTY_PAIR);
        pairs.add(0, EMPTY_PAIR);
        combo.setItems(FXCollections.observableArrayList(pairs));
        combo.setValue(EMPTY_PAIR);
        Callback<ListView<Pair<String, Object>>, ListCell<Pair<String, Object>>> factory
                = (lv)
                -> new ListCell<Pair<String, Object>>() {
            @Override
            protected void updateItem(Pair<String, Object> item,
                    boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(item.getKey());
                }
            }
        };
        combo.setCellFactory(factory);
        combo.setButtonCell(factory.call(null));
    }

    public static void loadLookupData(ComboBox box, int objectID) {
        loadDBEntities(new LookupDataDA().getLookupDataByObjectID(objectID), box);
    }

    public static void loadLookupData(ComboBox box, int objectID, boolean loadDefault) {
        box.getItems().clear();
        LookupDataDA lookupDataDA = new LookupDataDA();
        List<LookupData> list = lookupDataDA.getLookupDataByObjectID(objectID);
        loadDBEntities(list, box);
        if (loadDefault) {
            box.setValue(lookupDataDA.getDefaultLookupData(objectID));
        }
    }

    public static void loadLookupDataCombo(String persistenceUnit, ComboBox box, int objectID, boolean loadDefault) {
        LookupDataDA lookupDataDA = new LookupDataDA(persistenceUnit);
        List<LookupData> list = lookupDataDA.getLookupDataByObjectID(objectID);
        loadDBEntities(list, box);
        if (loadDefault) {
            box.setValue(lookupDataDA.getDefaultLookupDataDA(objectID));
        }
    }

    public static void loadFieldsCombo(List<Field> fields, ComboBox<Field> combo) {

        combo.getItems().addAll(fields);
        Callback<ListView<Field>, ListCell<Field>> factory
                = (lv)
                -> new ListCell<Field>() {
            @Override
            protected void updateItem(Field field,
                    boolean empty) {
                super.updateItem(field, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(field.getName());
                }
            }
        };
        combo.setCellFactory(factory);
        combo.setButtonCell(factory.call(null));
    }

    public static void loadSearchColumnCombo(List<SearchColumn> searchColumns, ComboBox<SearchColumn> combo) {
        SearchColumn searchColumn = new SearchColumn("", "", "", SearchColumn.SearchDataTypes.STRING);
        combo.getItems().clear();
        combo.getItems().add(searchColumn);
        combo.getItems().addAll(searchColumns);
        combo.setValue(searchColumn);
        Callback<ListView<SearchColumn>, ListCell<SearchColumn>> factory
                = (lv)
                -> new ListCell<SearchColumn>() {
            @Override
            protected void updateItem(SearchColumn item,
                    boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(item.getDisplayValue());
                }
            }
        };
        combo.setCellFactory(factory);
        combo.setButtonCell(factory.call(null));
    }

    public static void loadDBEntities(List<? extends DBEntity> dBEntities, ComboBox<DBEntity> combo) {
        combo.getItems().clear();
        combo.getItems().add(null);
        combo.getItems().addAll(dBEntities);
        combo.setValue(null);
        Callback<ListView<DBEntity>, ListCell<DBEntity>> factory
                = (lv)
                -> new ListCell<DBEntity>() {
            @Override
            protected void updateItem(DBEntity item,
                    boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(item == null ? "" : item.getDisplayKey());
                }
            }
        };
        combo.setCellFactory(factory);
        combo.setButtonCell(factory.call(null));
    }

    public static void loadNotNullEntities(List<? extends DBEntity> dBEntities, ComboBox<DBEntity> combo) {
        combo.getItems().clear();
        combo.getItems().addAll(dBEntities);
        Callback<ListView<DBEntity>, ListCell<DBEntity>> factory
                = (lv)
                -> new ListCell<DBEntity>() {
            @Override
            protected void updateItem(DBEntity item,
                    boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(item == null ? "" : item.getDisplayKey());
                }
            }
        };
        combo.setCellFactory(factory);
        combo.setButtonCell(factory.call(null));
    }

    public static void loadDaListView(List<DBAccess> dBAccesses, ListView<DBAccess> listView) {

        listView.setItems(FXCollections.observableList(dBAccesses));
        Callback<ListView<DBAccess>, ListCell<DBAccess>> factory
                = (lv)
                -> new ListCell<DBAccess>() {
            @Override
            protected void updateItem(DBAccess item,
                    boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(item.getDisplayKey());
                }
            }
        };
        listView.setCellFactory(factory);

    }

    public static void loadListView(List<DBEntity> dBEntities, ListView<DBEntity> listView) {

        listView.setItems(FXCollections.observableList(dBEntities));
        Callback<ListView<DBEntity>, ListCell<DBEntity>> factory
                = (lv)
                -> new ListCell<DBEntity>() {
            @Override
            protected void updateItem(DBEntity item,
                    boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText("");
                } else {
                    setText(item.getDisplayKey());
                }
            }
        };
        listView.setCellFactory(factory);

    }

    public static void setTableEditable(TableView table) {
        table.setEditable(true);
        // allows the individual cells to be selected
        table.getSelectionModel().cellSelectionEnabledProperty().set(true);
        // when character or numbers pressed it will start edit in editable
        // fields
        table.setOnKeyPressed(event -> {
            if (event.getCode().isLetterKey() || event.getCode().isDigitKey()) {
                editFocusedCell(table);
            } else if (event.getCode() == KeyCode.RIGHT
                    || event.getCode() == KeyCode.TAB) {
                table.getSelectionModel().selectNext();
                event.consume();
            } else if (event.getCode() == KeyCode.LEFT) {
                // work around due to
                // TableView.getSelectionModel().selectPrevious() due to a bug
                // stopping it from working on
                // the first column in the last row of the table
                selectPrevious(table);
                event.consume();
            } else if (event.getCode() == KeyCode.DELETE) {
                table.getItems().removeAll(table.getSelectionModel().getSelectedItems());
            }
        });
    }

    public static void editFocusedCell(TableView<Class> table) {
        final TablePosition<Class, ?> focusedCell = table
                .focusModelProperty().get().focusedCellProperty().get();
        table.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    @SuppressWarnings("unchecked")
    public static void selectPrevious(TableView table) {
        if (table.getSelectionModel().isCellSelectionEnabled()) {
            // in cell selection mode, we have to wrap around, going from
            // right-to-left, and then wrapping to the end of the previous line
            TablePosition<Class, ?> pos = table.getFocusModel()
                    .getFocusedCell();
            if (pos.getColumn() - 1 >= 0) {
                // go to previous row
                table.getSelectionModel().select(pos.getRow(),
                        getTableColumn(table, pos.getTableColumn(), -1));
            } else if (pos.getRow() < table.getItems().size()) {
                // wrap to end of previous row
                table.getSelectionModel().select(pos.getRow() - 1,
                        table.getVisibleLeafColumn(
                                table.getVisibleLeafColumns().size() - 1));
            }
        } else {
            int focusIndex = table.getFocusModel().getFocusedIndex();
            if (focusIndex == -1) {
                table.getSelectionModel().select(table.getItems().size() - 1);
            } else if (focusIndex > 0) {
                table.getSelectionModel().select(focusIndex - 1);
            }
        }
    }

    public static TableColumn<Class, ?> getTableColumn(TableView table,
            final TableColumn<Class, ?> column, int offset) {
        int columnIndex = table.getVisibleLeafIndex(column);
        int newColumnIndex = columnIndex + offset;
        return table.getVisibleLeafColumn(newColumnIndex);
    }

    public static void addRow(TableView tableView, Object object) {

        Object lastID = null;
        TablePosition pos = tableView.getFocusModel().getFocusedCell();
        List items = tableView.getItems();
        int size = items.size();
        Object lastObject = items.isEmpty() ? null : tableView.getItems().get(size - 1);
        if (lastObject instanceof DBAccess) {
            lastID = ((DBAccess) lastObject).getId();
        }
        if (pos.getRow() == size - 1 || lastID != null) {

            tableView.getItems().add(object);
        }
    }

    public static String getText(TextField field, String string) throws Exception {
        String enteredString = field.getText();
        if (isNullOrEmpty(enteredString)) {
            field.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        }
        return enteredString.trim();
    }

    public static String getText(TextField field, String string, boolean condition) throws Exception {
        return condition ? getText(field, string) : getText(field);
    }

    public static String getText(ComboBox box, String string) throws Exception {
        String enteredString = box.getValue().toString().trim();
        if (isNullOrEmpty(enteredString)) {
            box.requestLayout();
            throw new Exception("You must enter: " + string + "!");
        }
        return enteredString;
    }

    public static String getTextValue(ComboBox box, String string) throws Exception {
        Pair<String, Object> pair = (Pair<String, Object>) box.getValue();
        if (pair == null) {
            throw new Exception("You must enter: " + string + "!");
        };
        String enteredString = pair.getValue().toString().trim();
        if (isNullOrEmpty(enteredString)) {
            box.requestLayout();
            throw new Exception("You must enter: " + string + "!");
        }
        return enteredString;
    }

    public static Object getSelectedValue(ComboBox box, String string) throws Exception {
        Object object = box.getValue();
        if (object == null) {
            box.requestLayout();
            throw new Exception("You must select: " + string + "!");

        }
        return object;
    }

    public static DBEntity getEntity(ComboBox<DBEntity> box, String string) throws Exception {
        DBEntity object = box.getValue();
        if (object == null) {
            box.requestLayout();
            throw new Exception("You must select : " + string + "!");

        }

        return object;
    }

    public static DBEntity getEntity(ComboBox<DBEntity> box) {
        DBEntity object = box.getValue();
        return object == null ? null : object.getId() == null ? null : object;

    }

    public static DBEntity getEntity(ComboBox<DBEntity> box, String string, boolean condition) throws Exception {
        return condition ? getEntity(box, string) : getEntity(box);
    }

    public static Object getSelectedValue(ComboBox box) {

        return box.getValue();
    }

    public static String getText(TextArea textArea, String string) throws Exception {
        String enteredString = textArea.getText().trim();
        if (isNullOrEmpty(enteredString)) {
            textArea.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        }
        return enteredString;
    }

    public static String getText(TextArea textArea) {
        String enteredString = textArea.getText().trim();
        return enteredString;
    }

    public static String getText(TextArea textArea, String string, boolean condition) throws Exception {
        return condition ? getText(textArea, string) : getText(textArea);
    }

    public static LocalDate getDate(DatePicker picker, String string) throws Exception {
        LocalDate enteredDate = picker.getValue();
        if (enteredDate == null) {
            picker.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        }
        return enteredDate;
    }

    public static String getText(TextField field) {
        String enteredString = field.getText().trim();
        return enteredString;
    }

    public static String getText(ComboBox box) {
        try {
            return box.getValue().toString().trim();
        } catch (Exception e) {
            return "";
        }
    }

    public static String getComboDisplay(ComboBox box) {
        Pair<String, Object> pair = (Pair<String, Object>) box.getValue();
        String enteredString = pair.getKey().trim();
        return enteredString;
    }

    public static LocalDate getDate(DatePicker picker) {
        LocalDate enteredDate = picker.getValue();

        return enteredDate;
    }

    public static int getInt(TextField field, String string) throws Exception {
        String enteredString = field.getText().trim();
        if (isNullOrEmpty(enteredString)) {
            field.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        } else if (!Utilities.isInteger(enteredString)) {
            field.requestFocus();
            throw new Exception("Invalid Integer value: " + enteredString + " for " + string + "!");
        } else {
            return Utilities.defortInteger(enteredString);
        }

    }

    public static int getInt(ComboBox box, String string) throws Exception {
        String enteredString = box.getValue().toString().trim();
        if (isNullOrEmpty(enteredString)) {
            box.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        } else if (!Utilities.isInteger(enteredString)) {
            box.requestFocus();
            throw new Exception("Invalid Integer value: " + enteredString + " for " + string + "!");
        } else {
            return Utilities.defortInteger(enteredString);
        }
    }

    public static int getIntValue(ComboBox box, String string) throws Exception {
        Pair<String, Object> pair = (Pair<String, Object>) box.getValue();
        String enteredString = pair.getValue().toString().trim();
        if (isNullOrEmpty(enteredString)) {
            box.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        } else if (!Utilities.isInteger(enteredString)) {
            box.requestFocus();
            throw new Exception("Invalid Integer value: " + enteredString + " for " + string + "!");
        } else {
            return Utilities.defortInteger(enteredString);
        }
    }

    public static int getInt(TextArea field, String string) throws Exception {
        String enteredString = field.getText().trim();
        if (isNullOrEmpty(enteredString)) {
            field.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        } else if (!Utilities.isInteger(enteredString)) {
            field.requestFocus();
            throw new Exception("Invalid Integer vslue: " + enteredString + " for " + string + "!");
        } else {
            return Utilities.defortInteger(enteredString);
        }
    }

    public static int getInt(TextField field) throws Exception {
        String enteredString = field.getText().trim();
        if (isNullOrEmpty(enteredString)) {
            return 0;
        } else if (!Utilities.isInteger(enteredString)) {
            field.requestFocus();
            throw new Exception("Invalid Integer value: " + enteredString + "!");
        } else {
            return Utilities.defortInteger(enteredString);
        }

    }

    public static int getInt(ComboBox box) throws Exception {
        String enteredString = box.getValue().toString().trim();
        if (isNullOrEmpty(enteredString)) {
            return 0;
        } else if (!Utilities.isInteger(enteredString)) {
            box.requestFocus();
            throw new Exception("Invalid Integer value: " + enteredString + "!");
        } else {
            return Integer.parseInt(enteredString);
        }
    }

    public static int getIntValue(ComboBox box) throws Exception {
        Pair<String, Object> pair = (Pair<String, Object>) box.getValue();
        String enteredString = pair.getValue().toString().trim();
        if (isNullOrEmpty(enteredString)) {
            return 0;
        } else if (!Utilities.isInteger(enteredString)) {
            box.requestFocus();
            throw new Exception("Invalid Integer value: " + enteredString + "!");
        } else {
            return Integer.parseInt(enteredString);
        }
    }

    public static int getInt(TextArea field) throws Exception {
        String enteredString = field.getText().trim();
        if (isNullOrEmpty(enteredString)) {
            return 0;
        } else if (!Utilities.isInteger(enteredString)) {
            field.requestFocus();
            throw new Exception("Invalid Integer value: " + enteredString + "!");
        } else {
            return Integer.parseInt(enteredString);
        }
    }

    public static float getFloat(TextField field, String string) throws Exception {
        String enteredString = field.getText().trim();
        if (isNullOrEmpty(enteredString)) {
            field.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        } else if (!Utilities.isFloat(enteredString)) {
            field.requestFocus();
            throw new Exception("Invalid Float value: " + enteredString + " for " + string + "!");
        } else {
            return Float.parseFloat(enteredString);
        }

    }

    public static float getFloat(ComboBox box, String string) throws Exception {
        String enteredString = box.getValue().toString().trim();
        if (isNullOrEmpty(enteredString)) {
            box.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        } else if (!Utilities.isFloat(enteredString)) {
            box.requestFocus();
            throw new Exception("Invalid Float value: " + enteredString + " for " + string + "!");
        } else {
            return Utilities.defortFloat(string);
        }
    }

    public static float getFloat(TextArea field, String string) throws Exception {
        String enteredString = field.getText().trim();
        if (isNullOrEmpty(enteredString)) {
            field.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        } else if (!Utilities.isFloat(enteredString)) {
            field.requestFocus();
            throw new Exception("Invalid Float value: " + enteredString + " for " + string + "!");
        } else {
            return Utilities.defortFloat(enteredString);
        }
    }

    public static float getFloat(TextField field) throws Exception {
        String enteredString = field.getText().trim();
        if (isNullOrEmpty(enteredString)) {
            return 0;
        } else if (!Utilities.isFloat(enteredString)) {
            field.requestFocus();
            throw new Exception("Invalid Float value: " + enteredString + "!");
        } else {
            return Utilities.defortFloat(enteredString);
        }

    }

    public static float getFloat(ComboBox box) throws Exception {
        String enteredString = box.getValue().toString().trim();
        if (isNullOrEmpty(enteredString)) {
            return 0;
        } else if (!Utilities.isFloat(enteredString)) {
            box.requestFocus();
            throw new Exception("Invalid Float value: " + enteredString + "!");
        } else {
            return Utilities.defortFloat(enteredString);
        }
    }

    public static float getFloatValue(ComboBox box) throws Exception {
        Pair<String, Object> pair = (Pair<String, Object>) box.getValue();
        String enteredString = pair.getValue().toString().trim();
        if (isNullOrEmpty(enteredString)) {
            return 0;
        } else if (!Utilities.isFloat(enteredString)) {
            box.requestFocus();
            throw new Exception("Invalid Float value: " + enteredString + "!");
        } else {
            return (float) Utilities.defortNumber(enteredString);
        }
    }

    public static float getFloat(TextArea field) throws Exception {
        String enteredString = field.getText().trim();
        if (isNullOrEmpty(enteredString)) {
            return 0;
        } else if (!Utilities.isFloat(enteredString)) {
            field.requestFocus();
            throw new Exception("Invalid Float value: " + enteredString + "!");
        } else {
            return (float) Utilities.defortNumber(enteredString);
        }
    }

    public static double getDouble(TextField field, String string) throws Exception {
        String enteredString = (field.getText().trim());
        if (isNullOrEmpty(enteredString)) {
            field.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        } else if (!Utilities.isDouble(enteredString)) {
            field.requestFocus();
            throw new Exception("Invalid Double value: " + enteredString + " for " + string + "!");
        } else {
            return Utilities.defortNumber(enteredString);
        }

    }

    public static double getDouble(ComboBox box, String string) throws Exception {
        String enteredString = box.getValue().toString().trim();
        if (isNullOrEmpty(enteredString)) {
            box.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        } else if (!Utilities.isDouble(enteredString)) {
            box.requestFocus();
            throw new Exception("Invalid Double value: " + enteredString + " for " + string + "!");
        } else {
            return Utilities.defortNumber(enteredString);
        }
    }

    public static double getDouble(TextArea field, String string) throws Exception {
        String enteredString = field.getText().trim();
        if (isNullOrEmpty(enteredString)) {
            field.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        } else if (!Utilities.isDouble(enteredString)) {
            field.requestFocus();
            throw new Exception("Invalid Double vslue: " + enteredString + " for " + string + "!");
        } else {
            return Utilities.defortNumber(enteredString);
        }
    }

    public static double getDouble(Object value, String string) throws Exception {

        if (Utilities.isNullOrEmpty(value.toString())) {

            throw new Exception("You must enter: " + string + "!");
        } else if (!Utilities.isDouble(value)) {

            throw new Exception("Invalid Double value: " + value + " for " + string + "!");
        } else {
            return Utilities.defortNumber(value.toString());
        }
    }

    public static double getDouble(TextField field) {
        return Utilities.defortNumberOptional(field.getText().trim());

    }

    public static double getDouble(ComboBox box) {
        return Utilities.defortNumberOptional(box.getValue().toString().trim());
    }

    public static double getDouble(TextArea field) {

        return Utilities.defortNumberOptional(field.getText().trim());
    }

    public static byte[] getBytes(ImageView imageView, String string) throws Exception {

        if (imageView.getImage() == null) {
            imageView.requestFocus();
            throw new Exception("You must enter: " + string + "!");
        } else {
            BufferedImage bImage = SwingFXUtils.fromFXImage(imageView.getImage(), null);
            byte[] image;
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                ImageIO.write(bImage, "png", byteArrayOutputStream);
                image = byteArrayOutputStream.toByteArray();
            }

            return image;
        }
    }

    public static byte[] getBytes(ImageView imageView) throws Exception {

        Image image = imageView.getImage();
        byte[] bytes;
        if (image == null) {
            return null;
        }
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            ImageIO.write(bImage, "png", byteArrayOutputStream);
            bytes = byteArrayOutputStream.toByteArray();

            return bytes;
        }
    }

//    Load Forms
  
    public static void loadUI(DesktopPane desktop, String title, Parent content, int size) {
        InternalWindow window = new InternalWindow(title, null, title, content);
        window.setMinWidth(size);
        desktop.addInternalWindow(window);

    }

    public static void searchColumnSelected(ComboBox<SearchColumn> cboSearchColumn, ComboBox cboSearchType) {
        SearchColumn searchColumn = cboSearchColumn.getValue();
        if (searchColumn == null) {
            return;
        }
        ObservableList searchTypes = FXCollections.observableList(searchColumn.getSearchTypes());
        cboSearchType.setItems(searchTypes);
        cboSearchType.setValue(searchColumn.getDefaultSearchType());

    }

    public static void setContainsSearch(FilteredList filteredList, ComboBox cboSearchType,
            ComboBox<SearchColumn> cboSearchColumn, TextField txtSearch, TableView table) {
        SearchColumn selectedSearchColumn = cboSearchColumn.getValue();

        String searchType = getText(cboSearchType);

        txtSearch.textProperty().addListener((obervableValue, oldValue, newValue) -> {
            if (!(searchType.equalsIgnoreCase(SearchColumn.SearchType.Contains.name()) || searchType.isBlank())) {
                return;
            }
            filteredList.setPredicate((Predicate<DBAccess>) dbAccess -> {

                String lowerNewValue = txtSearch.getText().trim();
                List<SearchColumn> searchColumns = dbAccess.getSearchColumns();
                if (newValue.isBlank()) {
                    return true;
                } else {
                    if (isNullOrEmpty(selectedSearchColumn.getName())) {
                        if (searchColumns.stream().anyMatch((searchColumn) -> (searchColumn.getValue().toString().equalsIgnoreCase(newValue)))) {
                            return true;
                        }
                    } else {

                        if (searchColumns.stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.getValue().toString().equalsIgnoreCase(lowerNewValue)))) {
                            return true;
                        }

                    }
                }
                return false;
            });

        });

        SortedList<DBAccess> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);

    }

    public static void setSearch(FilteredList filteredList, ComboBox cboSearchType,
            ComboBox<SearchColumn> cboSearchColumn, TextField txtSearch, TextField txtSecondValue, TableView table) {
        String searchType = getText(cboSearchType);
//        if (searchType.equalsIgnoreCase(SearchColumn.SearchType.Contains.name()) || isNullOrEmpty(searchType)) {
//            return;
//        }

        String enteredText = txtSearch.getText();
        String enteredText1 = txtSecondValue.getText();

        SearchColumn selectedColumn = cboSearchColumn.getValue();

        if (searchType.equalsIgnoreCase(SearchColumn.SearchType.Equal.name())) {

            filteredList.setPredicate(selectedColumn.equalsPrediacte(selectedColumn, enteredText));

        }
        if (searchType.equalsIgnoreCase(SearchColumn.SearchType.Contains.name())) {

            filteredList.setPredicate(selectedColumn.containsPrediacte(selectedColumn, enteredText));

        } else if (searchType.equalsIgnoreCase(SearchColumn.SearchType.Between.name())) {

            filteredList.setPredicate(selectedColumn.betweenPredicate(selectedColumn, enteredText, enteredText1));
        } else if (searchType.equalsIgnoreCase(SearchColumn.SearchType.Begins.name())) {
            filteredList.setPredicate(selectedColumn.beginsPrediacte(selectedColumn, enteredText));
        } else if (searchType.equalsIgnoreCase(SearchColumn.SearchType.Ends.name())) {
            filteredList.setPredicate(selectedColumn.endsPrediacte(selectedColumn, enteredText));
        } else if (searchType.equalsIgnoreCase(SearchColumn.SearchType.Length_Equal.name())) {
            try {
                filteredList.setPredicate(selectedColumn.lengthLessEqualPredicate(selectedColumn, getInt(txtSearch, "Search Value")));
            } catch (Exception ex) {
                errorMessage(ex);
            }

        } else if (searchType.equalsIgnoreCase(SearchColumn.SearchType.Length_Greater.name())) {
            try {
                filteredList.setPredicate(selectedColumn.lengthLessGreaterPredicate(selectedColumn, getInt(txtSearch, "Search Value")));
            } catch (Exception ex) {
                errorMessage(ex);
            }

        } else if (searchType.equalsIgnoreCase(SearchColumn.SearchType.Length_Less.name())) {
            try {
                filteredList.setPredicate(selectedColumn.lengthLessPrediacte(selectedColumn, getInt(txtSearch, "Search Value")));
            } catch (Exception ex) {
                errorMessage(ex);
            }

        }

        SortedList<DBAccess> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    public static void setCapturedImage(ImageView imageView) {
        try {
            FXMLLoader loader = CommonNavigate.getUILoader("Capture");
            Parent root = loader.load();
            CaptureController controller = loader.<CaptureController>getController();
            controller.openCamera();
            Scene scene = new Scene(root, 500, 350);
            Stage stage = new Stage();
            stage.setTitle("Capture Image");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(imageView.getParent().getScene().getWindow());
            stage.showAndWait();

            if (controller.isPhotoChosen()) {
                imageView.setImage(controller.getChoosenImage());
            }
        } catch (IOException ex) {
            errorMessage(ex);
        }
    }

    public static void showDialog(ImageView imageView) {
        try {
            FXMLLoader loader = CommonNavigate.getUILoader("Capture");
            Parent root = loader.load();
            CaptureController controller = loader.<CaptureController>getController();
            controller.openCamera();
            Scene scene = new Scene(root, 500, 350);
            Stage stage = new Stage();
            stage.setTitle("Capture Image");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(imageView.getParent().getScene().getWindow());
            stage.showAndWait();

            if (controller.isPhotoChosen()) {
                imageView.setImage(controller.getChoosenImage());
            }
        } catch (IOException ex) {
            errorMessage(ex);
        }
    }

    public static DBAccess getSelectedItem(Class mainClass, DBAccess dbAccess, String uiName, String title, float widith, float height,
            Node node, boolean constrainColumns) throws IOException {
        try {
            String viewUI = "View";

            FXMLLoader loader = CommonNavigate.getUILoader(viewUI);
            Parent root = loader.load();
            ViewController controller = loader.<ViewController>getController();
            controller.setInitData(mainClass, dbAccess, uiName, constrainColumns, true);

            Scene scene = new Scene(root);
            scene.getStylesheets().add(Navigation.styleControls);
            Stage stage = new Stage();
            if (widith > 0) {
                stage.setMinWidth(widith);
            }
            if (height > 0) {
                stage.setMinHeight(height);
            }
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(node.getScene().getWindow());

            stage.showAndWait();

            DBAccess selectedItem = controller.getSelectedItem();

            return selectedItem;

        } catch (IOException e) {
            throw e;
        }

    }

    public static void selectedItem(Class mainClass, DBAccess dbAccess, String uiName, String title,
            Node node, boolean constrainColumns) throws IOException, Exception {
        try {

            DBAccess selectedItem = getSelectedDA(mainClass, dbAccess, uiName, title, 0, 0, node, constrainColumns);
            if (selectedItem == null) {
                return;
            }
            if (node instanceof ComboBox) {
                ComboBox box = (ComboBox) node;
                DBEntity entity = selectedItem.getDBEntity();
                if (!box.getItems().contains(entity)) {
                    box.getItems().add(0, entity);
                }
                box.setValue(entity);
            } else if (node instanceof TextField) {
                TextField field = (TextField) node;
                field.setText(selectedItem.getId().toString());

            }

        } catch (IOException e) {
            throw e;
        }

    }

    public static void selectedItem(Class mainClass, DBAccess dbAccess, String uiName, String title, float width, float height,
            Node node, boolean constrainColumns) throws IOException, Exception {
        try {

            DBAccess selectedItem = getSelectedDA(mainClass, dbAccess, uiName, title, width, height, node, constrainColumns);
            if (selectedItem == null) {
                return;
            }
            if (node instanceof ComboBox) {
                ComboBox box = (ComboBox) node;
                DBEntity entity = selectedItem.getDBEntity();
                if (!box.getItems().contains(entity)) {
                    box.getItems().add(0, entity);
                }
                box.setValue(entity);
            } else if (node instanceof TextField) {
                TextField field = (TextField) node;
                field.setText(selectedItem.getId().toString());

            }

        } catch (IOException e) {
            throw e;
        }

    }

    public static void selectItem(Class mainClass, MenuItem menuItem, DBAccess dbAccess, String uiName, String title,
            Node node, boolean constrainColumns) {
        menuItem.setOnAction(e -> {
            try {
                selectedItem(mainClass, dbAccess, uiName, title, node, constrainColumns);
            } catch (Exception ex) {
                errorMessage(ex);
            }
        });
    }

    public static void selectItem(Class mainClass, MenuItem menuItem, DBAccess dbAccess, String uiName, String title, float width, float height,
            Node node, boolean constrainColumns) {
        menuItem.setOnAction(e -> {
            try {
                selectedItem(mainClass, dbAccess, uiName, title, width, height, node, constrainColumns);
            } catch (Exception ex) {
                errorMessage(ex);
            }
        });
    }

//No Lisit Injected end
    public static DBAccess getSelectedItem(Class mainClass, DBAccess dbAccess, List<? extends DBAccess> dBAccesses,
            String objectName, String title, float widith, float height,
            Node node, boolean constrainColumns) throws IOException {
        try {
            String viewUI = "View";
            FXMLLoader loader = CommonNavigate.getUILoader(viewUI);
            Parent root = loader.load();
            ViewController controller = loader.<ViewController>getController();
            controller.setInitData(mainClass, dbAccess, dBAccesses, objectName, constrainColumns, true);
            Scene scene = new Scene(root, widith, height);
            scene.getStylesheets().add(Navigation.styleControls);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            if (widith > 0) {
                stage.setWidth(widith);
            }
            if (height > 0) {
                stage.setHeight(height);
            }
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(node.getScene().getWindow());
            stage.showAndWait();
            DBAccess selectedItem = controller.getSelectedItem();
            if (selectedItem != null) {
                if (node instanceof ComboBox) {
                    ComboBox box = (ComboBox) node;
                    DBEntity entity = selectedItem.getDBEntity();
                    if (!box.getItems().contains(entity)) {
                        box.getItems().add(0, entity);
                    }
                    box.setValue(entity);
                } else if (node instanceof TextField) {
                    TextField field = (TextField) node;
                    field.setText(selectedItem.getId().toString());

                }
            }
            return selectedItem;

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

    }

    public static void selectedItem(Class mainClass, DBAccess dbAccess, List<? extends DBAccess> dBAccesses, String objectName, String title,
            Node node, boolean constrainColumns) throws IOException {
        try {
            getSelectedItem(mainClass, dbAccess, dBAccesses, objectName, title, 0, 0, node, constrainColumns);

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

    }

    public static void selectItem(Class mainClass, MenuItem menuItem, DBAccess dbAccess, List<? extends DBAccess> dBAccesses,
            String uiName, String title, float widith, float height,
            Node node, boolean constrainColumns) {
        menuItem.setOnAction(e -> {
            try {
                getSelectedItem(mainClass, dbAccess, dBAccesses, uiName, title, widith, height, node, constrainColumns);
            } catch (IOException ex) {
                errorMessage(ex);
            }
        });
    }

    public static DBAccess getSelectedItem(Class mainClass, DBAccess dbAccess, List<? extends DBAccess> dBAccesses, List<SearchColumn> searchColumns,
            String uiName, String title, float widith, float height,
            Node node, boolean constrainColumns) throws IOException {
        try {

            FXMLLoader loader = CommonNavigate.getUILoader("View");
            Parent root = loader.load();
            ViewController controller = loader.<ViewController>getController();
            controller.setInitData(mainClass, dbAccess, dBAccesses, searchColumns, uiName, constrainColumns);

            controller.setPopUp(true);
            Scene scene = new Scene(root, widith, height);
            scene.getStylesheets().add(Navigation.styleControls);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(node.getScene().getWindow());
            stage.showAndWait();
            DBAccess selectedItem = controller.getSelectedItem();
            if (selectedItem != null) {
                if (node instanceof ComboBox) {
                    ComboBox box = (ComboBox) node;
                    DBEntity entity = selectedItem.getDBEntity();
                    box.setValue(selectedItem);
                    if (!box.getItems().contains(entity)) {
                        box.getItems().add(0, entity);
                    }
                } else if (node instanceof TextField) {
                    TextField field = (TextField) node;
                    field.setText(selectedItem.getId().toString());

                }
            }
            return selectedItem;

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

    }

    public static void selectItem(Class mainClass, MenuItem menuItem, DBAccess dbAccess, List<DBAccess> dBAccesses, List<SearchColumn> searchColumns,
            String uiName, String title, float widith, float height,
            Node node, boolean constrainColumns) {
        menuItem.setOnAction(e -> {
            try {
                getSelectedItem(mainClass, dbAccess, dBAccesses, searchColumns, uiName, title, widith, height, node, constrainColumns);
            } catch (IOException ex) {
                errorMessage(ex);
            }
        });
    }

    public static DBAccess getSelectedLookupData(Class mainClass, int objectID, String uiName, String title, float widith, float height,
            Node node, boolean constrainColumns) throws IOException {
        try {

            LookupDataDA lookupDataDA = new LookupDataDA();
            List<LookupDataDA> list = LookupDataDA.getLookupDataDAs(lookupDataDA.getLookupDataByObjectID(objectID));

            return getSelectedItem(mainClass, lookupDataDA, list, uiName, title, widith, height, node, constrainColumns);

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

    }

    public static void selectLookupData(Class mainClass, MenuItem menuItem, int objectID, String uiName, String title, Node node,
            boolean constrainColumns) throws IOException {
        try {

            LookupDataDA lookupDataDA = new LookupDataDA();
            menuItem.setOnAction(e -> {
                try {
                    List<? extends DBAccess> list = LookupDataDA.getLookupDataDAs(lookupDataDA.getLookupDataByObjectID(objectID));
                    selectedItem(mainClass, lookupDataDA, list, uiName, title, node, constrainColumns);

                } catch (IOException ex) {
                    errorMessage(ex);
                }
            });

        } catch (Exception e) {
            throw e;
        }

    }
    
    

    public static void selectLookupData(Class mainClass, MenuItem menuItem, int objectID, String uiName, String title, float widith, float height,
            Node node, boolean constrainColumns) throws IOException {
        try {

            LookupDataDA lookupDataDA = new LookupDataDA();
            List<? extends DBAccess> list = LookupDataDA.getLookupDataDAs(lookupDataDA.getLookupDataByObjectID(objectID));
            menuItem.setOnAction(e -> {
                try {
                    getSelectedItem(mainClass, lookupDataDA, list, uiName, title, widith, height, node, constrainColumns);

                } catch (IOException ex) {
                    errorMessage(ex);
                }
            });

        } catch (Exception e) {
            throw e;
        }

    }

    public static void showDialog(String uiName, String title, float widith, float height,
            Node node) throws IOException {
        try {

            FXMLLoader loader = CommonNavigate.getUILoader(uiName);
            Parent root = loader.load();
            Scene scene = new Scene(root, widith, height);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(node.getScene().getWindow());
            stage.showAndWait();

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

    }

    public static void showDialog(Parent root, String title, Node node) throws IOException {
        try {

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/css/StyleControls.css");
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(node.getScene().getWindow());
            stage.setMaximized(false);
            stage.showAndWait();

        } catch (Exception e) {
            throw e;
        }

    }

    public static DBAccess getSelectedDA(Class mainClass, DBAccess dbAccess, String objectName, String title, float widith, float height,
            Node node, boolean constrainColumns) throws IOException {
        try {
            String viewUI = "View";
            FXMLLoader loader = CommonNavigate.getUILoader(viewUI);
            Parent root = loader.load();
            ViewController controller = loader.<ViewController>getController();
            controller.setInitData(mainClass, dbAccess, objectName, constrainColumns, true);
            Scene scene = new Scene(root, widith, height);
            scene.getStylesheets().add(Navigation.styleControls);
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UTILITY);
            stage.initOwner(node.getScene().getWindow());
            stage.showAndWait();
            DBAccess selectedItem = controller.getSelectedItem();

            return selectedItem;

        } catch (IOException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }

    }

    public static void clear(Parent parent) {
        List<Node> nodes = parent.getChildrenUnmodifiable();

        for (Node node : nodes) {
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                textField.clear();
            } else if (node instanceof TextArea) {
                TextArea textArea = (TextArea) node;
                textArea.clear();
            } else if (node instanceof ComboBox) {
                ComboBox comboBox = (ComboBox) node;
                comboBox.setValue(null);
            } else if (node instanceof DatePicker) {
                DatePicker datePicker = (DatePicker) node;
                datePicker.setValue(null);
            } else if (node instanceof CheckBox) {
                CheckBox checkBox = (CheckBox) node;
                checkBox.setSelected(false);
            } else if (node instanceof TableView) {
                TableView tableView = (TableView) node;
                tableView.getItems().clear();
            }
        }
    }

}
