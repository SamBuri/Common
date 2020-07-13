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
import java.util.logging.Level;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private TextField txtServerName, txtDatabase, txtUsername, txtServerPassword;
    @FXML
    private CheckBox chkSaveConnection;
    
    @FXML
    private ContextMenu cmuLogin;
    @FXML
    private MenuItem cmiUser, cmiLicence;
    private static final Logger LOG = LogManager.getLogger();
    
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
                    
                    showDialog("AppUser", "User", 400, 450, btnClose, true);
                } catch (IOException ex) {
                    errorMessage(ex);
                }
            });
            
            cmiLicence.setOnAction(e -> {
                try {
                    
                    showDialog("Licence", "Licence", 400, 450, btnClose, true);
                } catch (IOException ex) {
                    errorMessage(ex);
                }
            });
            
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
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
        LOG.info("Login Started");
        try {
            String loginID = getText(txtLoginID, "Login ID");
            String password = getText(txtPassword, "Password");
            setCurrentDBConnection();
            LOG.info("Iniating the login task");
            Task<Integer> task = new Task<Integer>() {
                
                private int success = 0;
                
                @Override
                protected Integer call() {
                    try {
                        LOG.info("Preparing session");
                        updateMessage("Initiating Connection...");
                        Thread.sleep(500);
                        AppUserDA appUserDA = new AppUserDA();
                        updateProgress(50, 100);
                        updateMessage("Validating Licence...");
                        Thread.sleep(500);
                        Thread.sleep(500);
                        if (LicenceDA.noUploadedLicences()) {
                            Platform.runLater(() -> message("No licence Found. Please upload a licence to continue"));
                            return 0;
                            
                        } else {
                            if (!LicenceDA.isValid()) {
                                Platform.runLater(() -> message("Invalid licence"));
                                return 0;
                            }
                        }
                        updateProgress(70, 100);
                        updateMessage("Validating user...");
                        Thread.sleep(500);
                        updateProgress(success, success);
                        
                        if (appUserDA.login(loginID, password)) {
                            
                            success = 1;
                        }
                        updateMessage("Preparing WIndow...");
                        updateProgress(90, 100);
                        Thread.sleep(500);
                        return success;
                    } catch (Exception e) {
                        Platform.runLater(() -> errorMessage("Error Connecting to the database.\n"
                                + "Please check if your computer is connected to the network\n " + (e.getMessage() == null ? " "
                                + "Null Pointer Exception" : " " + e.getMessage())));
                        LOG.error("Login Failed: " + e.getMessage());
                        LOG.error(e, e);
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
                        LOG.info("Login succedded");
                        
                        Thread.sleep(500);
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
                    } catch (IOException | InterruptedException ex) {
                        Platform.runLater(() -> errorMessage(ex));
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
            lblStatus.textProperty().bind(task.messageProperty());
            
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
            LOG.info("Loading DB Connection Properties");
            DBConnection dBConnection = new DBConnection();
            dBConnection = dBConnection.xmlDecode();
            txtServerName.setText(dBConnection.getUrl());
            txtDatabase.setText(dBConnection.getDbName());
            txtUsername.setText(dBConnection.getUsername());
            txtServerPassword.setText(SaburiEncryptor.decrypt(dBConnection.getPassword()));
            LOG.info("Done Loading DB Connection Properties");
        } catch (IOException ex) {
            errorMessage(ex);
        }
        
    }
    
}
