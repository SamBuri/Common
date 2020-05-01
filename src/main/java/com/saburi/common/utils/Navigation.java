/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import com.saburi.common.main.App;
import com.saburi.common.controllers.AbstractViewController;
import static com.saburi.common.utils.FXUIUtils.errorMessage;
import com.saburi.common.utils.Utilities.FormMode;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import org.kordamp.desktoppanefx.scene.layout.DesktopPane;
import static org.kordamp.desktoppanefx.scene.layout.DesktopPane.resolveInternalWindow;
import org.kordamp.desktoppanefx.scene.layout.InternalWindow;
import com.saburi.common.controllers.EditController;
import com.saburi.common.controllers.SearchController;
import com.saburi.common.dbaccess.DBAccess;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;

/**
 *
 * @author CLINICMASTER13
 */
public class Navigation {

    private static DesktopPane desktopPane;
    private static StackPane stpMain;
    public static Class parentScene;
    public static String parentFXMl;
    public static String mainStyleSheet = App.class.getResource("MainScene.css").toExternalForm();
    public static String styleControls = App.class.getResource("StyleControls.css").toExternalForm();
    public static String persistenceUnit;
    public static String appTitle;
    public static Image image;

    public static void setParents(DesktopPane desktopPane, StackPane stpMain) {
        Navigation.desktopPane = desktopPane;
        Navigation.stpMain = stpMain;
        Navigation.desktopPane.getStylesheets().add(styleControls);

    }

    public static DesktopPane getDesktopPane() {
        return Navigation.desktopPane;
    }

    public static Parent loadFXML(Class type, String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(type.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static URL getIconURL(Class type) {
        return type.getClass().getResource("icon.ico");
    }

    public static Image getImage(Class type) {
        try {
            return new Image(type.getResourceAsStream("icon.png"));
        } catch (Exception e) {
            throw e;
        }

    }

    public static void loadEditUI(Class type, String uiName, String title, FormMode formMode) throws IOException {
        try {
            FXMLLoader loader = Navigation.getUILoader(type, uiName);
            Parent root = loader.load();
            EditController controller = loader.<EditController>getController();
            controller.init(title, formMode);
            float size = controller.getMinSize();
            desktopPane.removeInternalWindow(uiName);
            Node node = GlyphsDude.createIcon(FontAwesomeIcon.PLUS_CIRCLE);
            if (formMode.equals(FormMode.Update)) {
                node = GlyphsDude.createIcon(FontAwesomeIcon.EDIT);
            } else if (formMode.equals(FormMode.Print)) {
                node = GlyphsDude.createIcon(FontAwesomeIcon.PRINT);
            }

            InternalWindow window = new InternalWindow(uiName, node, title, root);

            if (size > 0) {
                window.setPrefWidth(size);
            }

            Navigation.desktopPane.addInternalWindow(window);

        } catch (IOException ex) {
            throw ex;
        }
    }

    public static void loadEditUI(Class type, FontAwesomeIcon icon, String uiName, String title, FormMode formMode) throws IOException {
        try {
            FXMLLoader loader = Navigation.getUILoader(type, uiName);
            Parent root = loader.load();
            EditController controller = loader.<EditController>getController();
            FontAwesomeIconView node = new FontAwesomeIconView(icon);
            controller.init(title, formMode);
            float size = controller.getMinSize();
            InternalWindow window = new InternalWindow(uiName, node, title, root);
            if (size > 0) {
                window.setMinWidth(size);
            }
            resolveInternalWindow(window).center();
            Navigation.desktopPane.addInternalWindow(window);
        } catch (IOException ex) {
            throw ex;
        }
    }

    public static void loadEditUI(Class type, String uiName, String title, Object objectValue, TableView tableView, FormMode formMode, boolean isPopup) throws IOException {
        try {

            FXMLLoader loader = Navigation.getUILoader(type, uiName);
            Parent root = loader.load();
            EditController controller = loader.<EditController>getController();
            controller.setFormMode(formMode);
            controller.init(title, formMode);
            controller.setTableView(tableView);
            if (formMode.equals(FormMode.Update) || formMode.equals(FormMode.Print)) {
                controller.setEdit(objectValue, formMode);
            }
            if (isPopup) {
                FXUIUtils.showDialog(root, title, tableView);
            } else {
                float size = controller.getMinSize();
                desktopPane.removeInternalWindow(uiName);
                InternalWindow window = new InternalWindow(uiName, GlyphsDude.createIcon(FontAwesomeIcon.BOOK), title, root);

                if (size > 0) {
                    window.setMinWidth(size);
                }
                resolveInternalWindow(window).center();
                Navigation.desktopPane.addInternalWindow(window);

            }
        } catch (IOException ex) {
            errorMessage(ex);

        }

    }

    public static void editMenuItemClick(Class type, MenuItem menuItem, FontAwesomeIcon icon, String uiName, String title, FormMode formMode) {

        CurrentUser.applyRights(uiName, menuItem);

        menuItem.setOnAction((event) -> {
            try {
                loadEditUI(type, uiName, title, formMode);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void editMenuItemClick(Class type, MenuItem menuItem, String uiName, String title, FormMode formMode) {

        CurrentUser.applyRights(uiName, menuItem);
        switch (formMode) {
            case Update:
                menuItem.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.EDIT));
                break;
            case Save:
                menuItem.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS));
                break;
            case Print:
                menuItem.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PRINT));
                break;
            default:
                break;
        }

        menuItem.setOnAction((event) -> {
            try {
                loadEditUI(type, uiName, title, formMode);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void editMenuItemClickIgnoreParent(Class type, MenuItem menuItem, String uiName, String title, FormMode formMode) {

        CurrentUser.applyRights(menuItem, false);

        menuItem.setOnAction((event) -> {
            try {
                loadEditUI(type, uiName, title, formMode);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void editMenuItemClickOptionalRight(Class type, MenuItem menuItem, String uiName, String title, FormMode formMode) {

        menuItem.setOnAction((event) -> {
            try {
                loadEditUI(type, uiName, title, formMode);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void loadUI(Class type, MenuItem menuItem, String uiName, String title, int width, boolean maximised) {

        CurrentUser.applyRights(uiName, menuItem);

        menuItem.setOnAction((event) -> {
            try {
                loadUI(type, uiName, title, width, maximised);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void editMenuItemClick(Class type, MenuItem menuItem, String uiName, String title, FormMode formMode, boolean maximised) {

        CurrentUser.applyRights(uiName, menuItem);
        if (formMode.equals(FormMode.Update)) {
            menuItem.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.EDIT));
        } else {
            menuItem.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.PLUS));
        }
        menuItem.setOnAction((event) -> {
            try {
                loadEditUI(type, uiName, title, formMode, maximised);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void editMenuItemClickIgnoreParent(Class type, MenuItem menuItem, String uiName, String title, FormMode formMode, boolean maximised) {

        CurrentUser.applyRights(menuItem, false);

        menuItem.setOnAction((event) -> {
            try {
                loadEditUI(type, uiName, title, formMode, maximised);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void editMenuItemClick(Class type, Button button, Node node, String uiName, String title, FormMode formMode, boolean maximised) {
        if (formMode.equals(FormMode.Update)) {
            button.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.EDIT));
        } else {
            button.setGraphic(node);
        }
        CurrentUser.applyRights(uiName, button);

        button.setOnAction((event) -> {
            try {
                loadEditUI(type, uiName, title, formMode, maximised);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void loadEditUI(Class type, String uiName, String title, FormMode formMode, int size) throws IOException {
        try {
            FXMLLoader loader = Navigation.getUILoader(type, uiName);
            Parent root = loader.load();
            EditController controller = loader.<EditController>getController();
            controller.init(title, formMode);
            InternalWindow window = new InternalWindow(uiName, GlyphsDude.createIcon(FontAwesomeIcon.BOOK), title, root);
            window.setMinWidth(size);
            resolveInternalWindow(window).center();
            Navigation.desktopPane.addInternalWindow(window);
        } catch (IOException ex) {
            throw ex;
        }
    }

    public static void loadEditUI(Class type, String uiName, String title, FormMode formMode, boolean maximised) throws IOException {
        try {
            FXMLLoader loader = Navigation.getUILoader(type, uiName);
            Parent root = loader.load();
            EditController controller = loader.<EditController>getController();
            controller.init(title, formMode);
            desktopPane.removeInternalWindow(uiName);
            InternalWindow window = new InternalWindow(uiName, GlyphsDude.createIcon(FontAwesomeIcon.BOOK), title, root);
            if (maximised) {
                window.maximizeOrRestoreWindow();
            }
            resolveInternalWindow(window).center();
            Navigation.desktopPane.addInternalWindow(window);
        } catch (IOException ex) {
            throw ex;
        }
    }

    public static void loadUI(Class type, String uiName, String title, int size, boolean maximised) throws IOException {
        try {
            FXMLLoader loader = Navigation.getUILoader(type, uiName);
            Parent root = loader.load();
            InternalWindow window = new InternalWindow(uiName, GlyphsDude.createIcon(FontAwesomeIcon.BOOK), title, root);
            window.setMinWidth(size);
            if (maximised) {
                window.maximizeOrRestoreWindow();
            }
            resolveInternalWindow(window).center();
            Navigation.desktopPane.addInternalWindow(window);
        } catch (IOException ex) {
            throw ex;
        }
    }

    public static void loadEditUI(Class type, String uiName, String title, FormMode formMode, Object objectValue, int size) throws IOException {
        try {

            FXMLLoader loader = Navigation.getUILoader(type, uiName);
            Parent root = loader.load();
            EditController controller = loader.<EditController>getController();
            controller.init(title, formMode);
            InternalWindow window = new InternalWindow(uiName, GlyphsDude.createIcon(FontAwesomeIcon.BOOK), title, root);
            window.setMinWidth(size);
            resolveInternalWindow(window).center();
            Navigation.desktopPane.addInternalWindow(window);
        } catch (IOException ex) {
            throw ex;
        }
    }

    public static void viewMenuItemClick(Class type, MenuItem menuItem, DBAccess oDBAccess, String objectName,
            String uiCaption, boolean restrainColumns, boolean maximised) {
        menuItem.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.TABLE));
        CurrentUser.applyRights(objectName, menuItem);
        menuItem.setOnAction((event) -> {
            try {

                view(type, oDBAccess, objectName, "View", uiCaption, restrainColumns, maximised);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void viewMenuItemClick(Class type, MenuItem menuItem, DBAccess oDBAccess, String objectName,
            String uiCaption, boolean restrainColumns, boolean maximised, boolean editable, boolean printable) {
        menuItem.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.TABLE));
        CurrentUser.applyRights(objectName, menuItem);
        menuItem.setOnAction((event) -> {
            try {

                view(type, oDBAccess, objectName, "View", uiCaption, restrainColumns, maximised, editable, printable);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void viewMenuItemClickOptionalRight(Class type, MenuItem menuItem, DBAccess oDBAccess, String objectName,
            String uiCaption, boolean restrainColumns, boolean maximised) {
        menuItem.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.TABLE));
        menuItem.setOnAction((event) -> {
            try {

                view(type, oDBAccess, objectName, "View", uiCaption, restrainColumns, maximised);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void viewMenuItemClickIgnoreParent(Class type, MenuItem menuItem, DBAccess oDBAccess, String objectName,
            String uiCaption, boolean restrainColumns, boolean maximised) {
        menuItem.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.TABLE));
        CurrentUser.applyRights(menuItem, false);
        menuItem.setOnAction((event) -> {
            try {

                view(type, oDBAccess, objectName, "View", uiCaption, restrainColumns, maximised);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void viewMenuItemClick(Class type, MenuItem menuItem, DBAccess oDBAccess, String objectName, String uiName,
            String uiCaption, boolean restrainColumns, boolean maximised) {
        menuItem.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.TABLE));
        CurrentUser.applyRights(objectName, menuItem);
        menuItem.setOnAction((event) -> {
            try {

                view(type, oDBAccess, objectName, uiName, uiCaption, restrainColumns, maximised);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static void viewMenuItemClickHasWithoutParent(Class type, MenuItem menuItem, DBAccess oDBAccess, String objectName,
            String uiName, String uiCaption, boolean restrainColumns, boolean maximised) {
        menuItem.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.TABLE));
        CurrentUser.applyRights(menuItem, false);
        menuItem.setOnAction((event) -> {
            try {

                view(type, oDBAccess, objectName, uiName, uiCaption, restrainColumns, maximised);
            } catch (IOException ex) {
                errorMessage(ex);
            }

        });

    }

    public static FXMLLoader getUILoader(Class className, String ui) {

        try {

            return new FXMLLoader(className.getResource(ui + ".fxml"));

        } catch (Exception e) {

            throw e;
        }
    }

    public static void view(Class mainClass, DBAccess oDBAccess, String ObjectName, String uiName, String title, boolean constrainColumns, boolean maximised) throws IOException {
        try {
            String windowID = ObjectName.concat(uiName);
            FXMLLoader loader = CommonNavigate.getUILoader(uiName);
            Parent root = loader.load();
            AbstractViewController controller = loader.<AbstractViewController>getController();
            controller.setInitData(mainClass, oDBAccess, ObjectName, constrainColumns);
            controller.setTitle(title);
            desktopPane.removeInternalWindow(windowID);
            InternalWindow window = new InternalWindow(windowID, GlyphsDude.createIcon(FontAwesomeIcon.BOOK), title, root);
            if (maximised) {
                window.maximizeOrRestoreWindow();
            }
            resolveInternalWindow(window).center();
            Navigation.desktopPane.addInternalWindow(window);

        } catch (IOException ex) {
            throw ex;
        }
    }

    public static void view(Class mainClass, DBAccess oDBAccess, String ObjectName, String uiName, String title,
            boolean constrainColumns, boolean maximised, boolean editable, boolean printable) throws IOException {
        try {
            String windowID = ObjectName.concat(uiName);
            FXMLLoader loader = CommonNavigate.getUILoader(uiName);
            Parent root = loader.load();
            AbstractViewController controller = loader.<AbstractViewController>getController();
            controller.setEditable(editable);
            controller.setPrintable(printable);
            controller.setInitData(mainClass, oDBAccess, ObjectName, constrainColumns);
            desktopPane.removeInternalWindow(windowID);
            InternalWindow window = new InternalWindow(windowID, GlyphsDude.createIcon(FontAwesomeIcon.BOOK), title, root);
            if (maximised) {
                window.maximizeOrRestoreWindow();
            }
            resolveInternalWindow(window).center();
            Navigation.desktopPane.addInternalWindow(window);

        } catch (IOException ex) {
            throw ex;
        }
    }

    public static void loadSearchEngine(MenuItem menuItem, List<TreeItem> treeItems, boolean maximised) throws IOException {
        menuItem.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.SEARCH));
        String searchUI = "Search";
        menuItem.setOnAction(e -> {
            try {
                FXMLLoader loader = CommonNavigate.getUILoader(searchUI);
                Parent root = loader.load();
                SearchController controller = loader.<SearchController>getController();
                controller.setTreeItems(treeItems);
                Navigation.desktopPane.removeInternalWindow(searchUI);
                InternalWindow window = new InternalWindow(searchUI, GlyphsDude.createIcon(FontAwesomeIcon.BOOK), "Search Engine", root);
                if (maximised) {
                    window.maximizeOrRestoreWindow();
                }
                resolveInternalWindow(window).center();
                Navigation.desktopPane.addInternalWindow(window);
            } catch (IOException ex) {
                errorMessage(ex);
            }
        });
    }

}
