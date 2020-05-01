package com.saburi.common.controllers;

import com.saburi.common.dbaccess.AppUserDA;
import com.saburi.common.dbaccess.LicenceDA;
import com.saburi.common.entities.AppUser;
import com.saburi.common.utils.DBConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import static com.saburi.common.utils.FXUIUtils.*;
import com.saburi.common.utils.Navigation;
import com.saburi.common.utils.SaburiEncryptor;
import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private Button btnLogin, btnClose;
    @FXML
    private TextField txtLoginID;
    @FXML
    private TextField txtPassword;
    @FXML
    private ProgressIndicator pgIndicator;

    @FXML
    private Label lblStatus;
    @FXML
    private GridPane gpnConnectionCreditential;

    @FXML
    private TitledPane tpnConnection;

    @FXML
    private TextField txtServerName, txtDatabase, txtUsername, txtServerPassword;
    @FXML
    private CheckBox chkSaveConnection;

    @FXML
    private ContextMenu cmuLogin;
    @FXML
    private MenuItem cmiUser, cmiLicence;
    private Parent nextParent;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            loadDBConnection();
            btnLogin.setOnAction(e -> login());
            btnClose.setOnAction(e -> System.exit(0));
            chkSaveConnection.setOnAction(e -> saveDBConnection());
            cmuLogin.setOnShowing(e -> showMenuItems());

            cmiUser.setOnAction(e -> {
                try {

                    showDialog("AppUser", "User", 400, 450, btnClose);
                } catch (IOException ex) {
                    errorMessage(ex);
                }
            });

            cmiLicence.setOnAction(e -> {
                try {

                    showDialog("Licence", "Licence", 400, 450, btnClose);
                } catch (IOException ex) {
                    errorMessage(ex);
                }
            });

        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    public Parent getNextParent() {
        return nextParent;
    }

    public void setNextParent(Parent nextParent) {
        this.nextParent = nextParent;
    }

    private void showMenuItems() {

        setCurrentDBConnection();
        Task<Integer> task = new Task<Integer>() {
//            private int success = 1;

            @Override
            protected Integer call() {
                setCurrentDBConnection();
                ObservableList<AppUser> appUser = FXCollections.observableList(new AppUserDA().getAppUsers());
                cmiUser.visibleProperty().bind(Bindings.size(appUser).lessThan(1));

                updateProgress(10, 10);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    errorMessage(e);
                }

                return 1;
            }

        };

//        task.setOnFailed(e -> errorMessage((Exception) task.getException()));
        new Thread(task).start();

        pgIndicator.progressProperty().bind(task.progressProperty());
        pgIndicator.visibleProperty().bind(task.runningProperty());
        lblStatus.visibleProperty().bind(task.runningProperty());

        new Thread(task).start();

    }

    private void login() {
        try {
            String loginID = getText(txtLoginID, "Login ID");
            String password = getText(txtPassword, "Password");
            setCurrentDBConnection();
            Task<Integer> task = new Task<Integer>() {

                private int success = 0;

                @Override
                protected Integer call() {
                    try {

                        AppUserDA appUserDA = new AppUserDA();

                        if (LicenceDA.noUploadedLicences()) {
                            Platform.runLater(() -> message("No licence Found. Please upload a licence to continue"));
                            return 0;

                        } else {
                            if (!LicenceDA.isValid()) {
                                Platform.runLater(() -> message("Invalid licence"));
                                return 0;
                            }
                        }
                        for (int i = 0; i < 10; i++) {

                            if (appUserDA.login(loginID, password)) {

                                success = 1;
                            }

                            updateProgress(i, 10);

                        }

                        return success;
                    } catch (Exception e) {
                        Platform.runLater(() ->message("Error Connecting to the database.\nPlease check if your computer is connected to the network\n "));
                        return 0;
                    }
                }

                @Override
                protected void succeeded() {
                    if (success == 0) {
                        message("Login Failed");
                        return;
                    }
                    try {
                        super.succeeded(); //To change body of generated methods, choose Tools | Templates.
//                        Parent nextParent = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));

                        Scene scene = new Scene(Navigation.loadFXML(Navigation.parentScene, Navigation.parentFXMl));
                        scene.getStylesheets().add(Navigation.mainStyleSheet);
                        Stage stage = new Stage();
                        stage.setMaximized(true);
                        stage.setScene(scene);
                        stage.getIcons().add(Navigation.image);
                        stage.setTitle(Navigation.appTitle);
                        stage.show();
                        stage.setOnCloseRequest(e -> System.exit(0));
                        btnClose.getScene().getWindow().hide();
                    } catch (IOException ex) {
                        errorMessage(ex);
                    }
                }

                @Override
                protected void failed() {
                    super.failed();

                    errorMessage("Login Failed", this.exceptionProperty().get());
                }

            };

//        task.setOnFailed(e -> errorMessage((Exception) task.getException()));
            new Thread(task).start();

            pgIndicator.progressProperty().bind(task.progressProperty());
            pgIndicator.visibleProperty().bind(task.runningProperty());
            lblStatus.visibleProperty().bind(task.runningProperty());

            new Thread(task).start();

        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void saveDBConnection() {
        try {
            if (chkSaveConnection.isSelected()) {
                String serverName = getText(txtServerName);
                String dbName = getText(txtDatabase);
                String username = getText(txtUsername);
                String password = getText(txtServerPassword);

                DBConnection dBConnection = new DBConnection(serverName, dbName, username, SaburiEncryptor.encrypt(password));
                dBConnection.xmlEncode();
            }
        } catch (IOException ex) {
            errorMessage(ex);
        }

    }

    private void setCurrentDBConnection() {

        try {
            String serverName = getText(txtServerName);
            String dbName = getText(txtDatabase);
            String username = getText(txtUsername);
            String password = getText(txtServerPassword);

            DBConnection dBConnection = new DBConnection(serverName, dbName, username, password);
            DBConnection.setCurrentDBConnection(dBConnection);
        } catch (Exception e) {
            errorMessage(e);
        }

    }

    private void loadDBConnection() {
        try {
            DBConnection dBConnection = new DBConnection();
            dBConnection = dBConnection.xmlDecode();
            txtServerName.setText(dBConnection.getUrl());
            txtDatabase.setText(dBConnection.getDbName());
            txtUsername.setText(dBConnection.getUsername());
            txtServerPassword.setText(SaburiEncryptor.decrypt(dBConnection.getPassword()));
        } catch (IOException ex) {
            errorMessage(ex);
        }

    }

}
