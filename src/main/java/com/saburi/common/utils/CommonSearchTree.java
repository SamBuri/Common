/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import com.saburi.common.dbaccess.*;
import com.saburi.common.main.App;
import static com.saburi.common.utils.CommonEnums.SearchItemTypes.Revision;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.TreeItem;

/**
 *
 * @author CLINICMASTER13
 */
public class CommonSearchTree {

    protected final TreeItem<SearchItem> triEntities = new TreeItem<>(new SearchItem("Entities"));
    protected final TreeItem<SearchItem> triRevisions = new TreeItem<>(new SearchItem("Revision History"));
    protected final TreeItem<SearchItem> triSetup = new TreeItem<>(new SearchItem("Set up"));
    protected final TreeItem<SearchItem> triSetupRev = new TreeItem<>(new SearchItem("Set up"));
    protected final TreeItem<SearchItem> triSeccurity = new TreeItem<>(new SearchItem("Security"));
    protected final TreeItem<SearchItem> triSeccurityRev = new TreeItem<>(new SearchItem("Seccurity"));
    protected final TreeItem<SearchItem> triCustomisation = new TreeItem<>(new SearchItem("Customisation"));
    protected final TreeItem<SearchItem> triCustomisationRev = new TreeItem<>(new SearchItem("Customisation"));
    protected final TreeItem<SearchItem> triGeographicalLocation = new TreeItem<>(new SearchItem("Geographical Location"));
    protected final TreeItem<SearchItem> triGeographicalLocationRev = new TreeItem<>(new SearchItem("Geographical Location"));
    protected final TreeItem<SearchItem> triGeneral = new TreeItem<>(new SearchItem("General"));
    protected final TreeItem<SearchItem> triGeneralRev = new TreeItem<>(new SearchItem("General"));

    protected List<SearchItem> commonSearchObjects = new ArrayList<>();
    protected List<TreeItem> treeItems;
    private final Class mainClass = App.class;
    private final List<SearchItem> sItems = Arrays.asList(
            //           Setup

            new SearchItem(mainClass, new LookupDataDA(), "LookupData", "Lookup Data", true, triSetup),
            new SearchItem(mainClass, new LookupDataDA(), Revision, "LookupData", "LookupData", true, triSetupRev),
            new SearchItem(mainClass, new StaffDA(), "Staff", "Staff", false, triSetup),
            new SearchItem(mainClass, new StaffDA(), Revision, "Staff", "Staff", false, triSetupRev),
            new SearchItem(mainClass, new CountyDA(), "County", "County", true, triGeographicalLocation),
            new SearchItem(mainClass, new SubCountyDA(), "SubCounty", "Sub County", true, triGeographicalLocation),
            new SearchItem(mainClass, new ParishDA(), "Parish", "Parish", true, triGeographicalLocation),
            new SearchItem(mainClass, new VillageDA(), "Village", "Village", true, triGeographicalLocation),
            new SearchItem(mainClass, new CountyDA(), Revision, "County", "County", true, triGeographicalLocationRev),
            new SearchItem(mainClass, new SubCountyDA(), Revision, "SubCounty", "Sub County", true, triGeographicalLocationRev),
            new SearchItem(mainClass, new ParishDA(), Revision, "Parish", "Parish", true, triGeographicalLocationRev),
            new SearchItem(mainClass, new VillageDA(), Revision, "Village", "Village", true, triGeographicalLocationRev),
            //            Security
            new SearchItem(mainClass, new UserRoleDA(), "UserRole", "User Roles", true, triSeccurity),
            new SearchItem(mainClass, new UserRoleDA(), Revision, "UserRole", "User Roles", true, triSeccurityRev),
            new SearchItem(mainClass, new UserRoleDetailDA(), "UserRoleDetail", "User Role Details", true, triSeccurity),
            new SearchItem(mainClass, new UserRoleDetailDA(), Revision, "UserRoleDetail", "User Role Details", true, triSeccurityRev),
            new SearchItem(mainClass, new AppUserDA(), "AppUser", "Users", true, triSeccurity),
            new SearchItem(mainClass, new AppUserDA(), Revision, "AppUser", "Users", true, triSeccurityRev),
            //           Security

            //            Customisation
            new SearchItem(mainClass, new OptionsDA(), "Options", "Options", true, triCustomisation),
            new SearchItem(mainClass, new OptionsDA(), Revision, "Options", "Options", true, triCustomisationRev),
            new SearchItem(mainClass, new IDGeneratorDA(), "IDGenerator", "ID Generators", true, triCustomisation),
            new SearchItem(mainClass, new IDGeneratorDA(), Revision, "IDGenerator", "ID Generators", true, triCustomisationRev)
    //            Customisation
    //            Set up
    );

    public List<SearchItem> getSearchObjects() {
        if (!commonSearchObjects.containsAll(sItems)) {
            commonSearchObjects.addAll(sItems);
        }
        return commonSearchObjects;
    }

    protected void addTreeItem(SearchItem searchItem) {
        String objectName = searchItem.getUiName();
        if (objectName != null) {
            if (!CurrentUser.hasRights(objectName, CommonEnums.Rights.Read)) {
                return;
            }
        }
        TreeItem parent = searchItem.getParent();
        if (parent == null) {
            CommonEnums.SearchItemTypes searchItemType = searchItem.getSearchItemTypes();
            parent = triGeneral;
            if (searchItemType != null) {
                if (searchItemType.equals(CommonEnums.SearchItemTypes.Revision)) {
                    parent = triGeneralRev;
                }
            }
        }
        TreeItem<SearchItem> treeItem = new TreeItem(searchItem);
        treeItem.setExpanded(true);
        parent.getChildren().add(treeItem);

    }

    public void setCommonSearchObject() {

        sItems.forEach(e -> addTreeItem(e));

    }

    public List<TreeItem> getTreeItems() {
        triEntities.setExpanded(true);

        triGeneral.setExpanded(true);
        triGeneralRev.setExpanded(true);
        triEntities.setExpanded(true);
        triRevisions.setExpanded(false);
        triSetup.getChildren().addAll(triGeographicalLocation, triCustomisation, triSeccurity);
        triSetupRev.getChildren().addAll(triGeographicalLocationRev, triCustomisationRev, triSeccurityRev);
        sItems.forEach(e -> addTreeItem(e));
        triEntities.getChildren().addAll(triSetup, triGeneral);
        triRevisions.getChildren().addAll(triSetupRev, triGeneralRev);
        this.treeItems = Arrays.asList(triEntities, triRevisions);
        return this.treeItems;
    }

}
