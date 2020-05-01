/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.controllers;

import com.saburi.common.dbaccess.DBAccess;
import com.saburi.common.utils.CommonEnums.SearchItemTypes;
import com.saburi.common.utils.CommonNavigate;
import static com.saburi.common.utils.FXUIUtils.errorMessage;
import com.saburi.common.utils.SearchItem;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.kordamp.desktoppanefx.scene.layout.DesktopPane;
import org.kordamp.desktoppanefx.scene.layout.InternalWindow;

/**
 *
 * @author CLINICMASTER13
 */
public class SearchController implements Initializable {

    @FXML
    protected TreeView<SearchItem> tvSearchItems;
    @FXML
    protected StackPane stpSearch;
    private final TreeItem root = new TreeItem();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            root.setExpanded(true);
            tvSearchItems.setShowRoot(false);

            tvSearchItems.setCellFactory(tv -> {

                TreeCell<SearchItem> cell = new TreeCell<SearchItem>() {

                    @Override
                    protected void updateItem(SearchItem item, boolean empty) {
                        super.updateItem(item, empty);
                        textProperty().unbind();
                        if (empty) {
                            setText("");
                        } else {
                            textProperty().bind(new SimpleStringProperty(item.getTitle()));

                        }
                    }

                };

                return cell;
            });

            tvSearchItems.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
                try {
                    if (newValue != null) {
                        FXMLLoader loader = CommonNavigate.getUILoader("View");
                        Parent p = loader.load();
                        AbstractViewController controller = loader.<AbstractViewController>getController();
                        SearchItem searchItem = newValue.getValue();
                        String objectName = searchItem.getUiName();
                        String title = searchItem.getTitle();
                        Class mainClass = searchItem.getMainClass();
                        SearchItemTypes searchItemType = searchItem.getSearchItemTypes();
                        boolean columnConstraint = searchItem.isConstrainColumns();
                        boolean editbale = searchItem.isEditable();
                        boolean printable = searchItem.isPrintable();
                        DBAccess dBAccess = searchItem.getDbAccess();
                        InternalWindow window = DesktopPane.resolveInternalWindow(tvSearchItems);

                        if (dBAccess != null) {
                            if (searchItemType == null) {
                                controller.setEditable(editbale);
                                controller.setPrintable(printable);
                                controller.setInitData(mainClass, dBAccess, objectName, columnConstraint);
                                window.getTitleBar().titleProperty().set("Search Engine->" + title);
                                controller.setTitle(title);
                            } else {
                                switch (searchItemType) {
                                    case Entinty:
                                        controller.setEditable(editbale);
                                        controller.setPrintable(printable);
                                        controller.setInitData(mainClass, dBAccess, objectName, columnConstraint);
                                        window.getTitleBar().titleProperty().set("Search Engine->" + title);
                                        controller.setTitle(title);
                                        break;
                                    case Revision:
                                        window.getTitleBar().titleProperty().set("Search Engine ->Revision History->" + title);
                                        controller.setInitRevData(dBAccess, objectName, columnConstraint);
                                        controller.setTitle(title);
                                        break;
                                    default:
                                        break;
                                }
                            }
                            stpSearch.getChildren().clear();
                            stpSearch.getChildren().add(p);

                        }

                    }

                } catch (IOException e) {
                    errorMessage(e);

                } catch (Exception e) {
                    errorMessage(e);

                }

            });
        } catch (Exception e) {
            errorMessage(e);
        }

    }

    public void setTreeItems(List<TreeItem> treeItems) {
        treeItems.forEach(tri -> root.getChildren().add(tri));
        tvSearchItems.setRoot(root);
    }

}
