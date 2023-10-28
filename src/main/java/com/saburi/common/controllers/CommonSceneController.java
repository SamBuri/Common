/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.controllers;

import com.saburi.common.dbaccess.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import org.kordamp.desktoppanefx.scene.layout.DesktopPane;
import com.saburi.common.utils.CurrentUser;
import com.saburi.common.utils.LoginHelper;
import static com.saburi.common.utils.CommonNavigate.*;
import com.saburi.common.utils.FXUIUtils;
import com.saburi.common.utils.Navigation;
import com.saburi.common.utils.Utilities.FormMode;
import java.io.IOException;

/**
 *
 * @author Hp
 */
public abstract class CommonSceneController implements Initializable {

    protected final DesktopPane desktopPane = new DesktopPane();
    @FXML
    private MenuItem mnuFileClose, mnuFileLogout;
    @FXML
    private StackPane stpMain;
    @FXML
    private Label lblStatusBar;
    @FXML
    protected MenuItem mnuSearchEngine;
    @FXML
    private MenuItem mnuSettingsUsersAdd, mnuSettingsUsersUpdate, mnuSettingsUsersView;
    @FXML
    private MenuItem mnuStaffAdd, mnuStaffUpdate, mnuStaffView;
    @FXML
    private MenuItem mnuSettingsLookupDataAdd, mnuSettingsLookupDataUpdate, mnuSettingsLookupDataView;
    @FXML
    private MenuItem mnuCompanyAdd, mnuCompanyUpdate, mnuCompanyView;
    @FXML
    private MenuItem mnuIDGeneratorUpdate, mnuGeneratorView;
    @FXML
    private MenuItem mnuLicenceUpdate;
    @FXML
    private MenuItem mnuSecurityUserRoleAdd, mnuSecurityUserRoleUpdate, mnuSecurityUserRoleView;
    @FXML
    private MenuItem mnuCountyAdd, mnuCountyUpdate, mnuCountyView;
    @FXML
    private MenuItem mnuSubCountyAdd, mnuSubCountyUpdate, mnuSubCountyView;
    @FXML
    private MenuItem mnuParishAdd, mnuParishUpdate, mnuParishView;
    @FXML
    private MenuItem mnuVillageAdd, mnuVillageUpdate, mnuVillageView;
    @FXML
    private MenuItem mnuOptionsUpdate, mnuOptionsView;

    protected void init() throws IOException {
      
            try {
                desktopPane.setMinHeight(stpMain.getHeight());
                stpMain.getChildren().add(desktopPane);
                Navigation.setParents(desktopPane, stpMain);
                mnuFileClose.setOnAction(e -> System.exit(0));
                mnuFileLogout.setOnAction(e -> {
                    CurrentUser.setAppUser(null);
                    lblStatusBar.getScene().getWindow().hide();
                    LoginHelper.showStage("Login");
                });
                
                lblStatusBar.setText(CurrentUser.getSimpleUserData());
                
                editMenuItemClick(mnuSettingsLookupDataAdd, "LookupData", "Lookup Data", FormMode.Save);
                editMenuItemClick(mnuSettingsLookupDataUpdate, "LookupData", "Lookup Data", FormMode.Update);
                viewMenuItemClick(mnuSettingsLookupDataView, new LookupDataDA(), "LookupData", "Lookup Data", true, true);
                
                editMenuItemClick(mnuIDGeneratorUpdate, "IDGenerator", "ID Generator", FormMode.Update);
                viewMenuItemClick(mnuGeneratorView, new IDGeneratorDA(), "IDGenerator", "ID Generator", true, true);
//                loadUI(mnuLicenceUpdate, "Licence", "Licence", 360, false);
                editMenuItemClick(mnuLicenceUpdate, "Licence", "Licence", FormMode.Save);
                
                editMenuItemClick(mnuStaffAdd, "Staff", "Staff", FormMode.Save);
                editMenuItemClick(mnuStaffUpdate, "Staff", "Staff", FormMode.Update);
                viewMenuItemClick(mnuStaffView, new StaffDA(), "Staff", "Staff", false, true);
                
                editMenuItemClick(mnuSettingsUsersAdd, "AppUser", "App User", FormMode.Save);
                editMenuItemClick(mnuSettingsUsersUpdate, "AppUser", "App User", FormMode.Update);
                viewMenuItemClick(mnuSettingsUsersView, new AppUserDA(), "AppUser", "App Users", true, true);
                
                editMenuItemClick(mnuCompanyAdd, "Company", "Company", FormMode.Save);
                editMenuItemClick(mnuCompanyUpdate, "Company", "Company", FormMode.Update);
                viewMenuItemClick(mnuCompanyView, new CompanyDA(), "Company", "Company", true, true);
                
                editMenuItemClick(mnuSecurityUserRoleAdd, "UserRole", "User Role", FormMode.Save);
                editMenuItemClick(mnuSecurityUserRoleUpdate, "UserRole", "User Role", FormMode.Update);
                viewMenuItemClick(mnuSecurityUserRoleView, new UserRoleDA(), "UserRole", "User Role", true, true);
                
                editMenuItemClick(mnuCountyAdd, "County", "County", FormMode.Save);
                editMenuItemClick(mnuCountyUpdate, "County", "County", FormMode.Update);
                viewMenuItemClick(mnuCountyView, new CountyDA(), "County", "County", true, true);
                
                editMenuItemClick(mnuSubCountyAdd, "SubCounty", "Sub County", FormMode.Save);
                editMenuItemClick(mnuSubCountyUpdate, "SubCounty", "Sub County", FormMode.Update);
                viewMenuItemClick(mnuSubCountyView, new SubCountyDA(), "SubCounty", "Sub County", true, true);
                
                editMenuItemClick(mnuVillageAdd, "Village", "Village", FormMode.Save);
                editMenuItemClick(mnuVillageUpdate, "Village", "Village", FormMode.Update);
                viewMenuItemClick(mnuVillageView, new VillageDA(), "Village", "Village", true, true);
                
                editMenuItemClick(mnuParishAdd, "Parish", "Parish", FormMode.Save);
                editMenuItemClick(mnuParishUpdate, "Parish", "Parish", FormMode.Update);
                viewMenuItemClick(mnuParishView, new ParishDA(), "Parish", "Parish", true, true);
                
                editMenuItemClick(mnuOptionsUpdate, "Options", "Options", FormMode.Update);
                viewMenuItemClick(mnuOptionsView, new OptionsDA(), "Options", "Options", true, true);
            } catch (Exception ex) {
                FXUIUtils.errorMessage(ex);
            }
        
    }

}
