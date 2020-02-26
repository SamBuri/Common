/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.controllers;

import com.saburi.common.dbaccess.*;
import de.jensd.fx.glyphs.GlyphsDude;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import com.saburi.common.entities.DBEntity;
import com.saburi.common.entities.FinancialPeriod;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.StackPane;
import org.kordamp.desktoppanefx.scene.layout.DesktopPane;
import com.saburi.common.utils.CurrentFinacialPeriod;
import com.saburi.common.utils.CurrentUser;
import com.saburi.common.utils.FXUIUtils;
import com.saburi.common.utils.LoginHelper;
import static com.saburi.common.utils.CommonNavigate.*;
import com.saburi.common.utils.Navigation;
import com.saburi.common.utils.Utilities.FormMode;
import java.io.IOException;

/**
 *
 * @author Hp
 */
public abstract class MainSceneController implements Initializable {

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
    private MenuItem mnuSettingsUsersAdd, mnuSettingsUsersUpdate;
    @FXML
    private MenuItem mnuStaffAdd, mnuStaffUpdate, mnuStaffView;
    @FXML
    private MenuItem mnuSettingsLookupDataAdd, mnuSettingsLookupDataUpdate, mnuSettingsLookupDataView;
    @FXML
    private MenuItem mnuCompanyAdd, mnuCompanyUpdate, mnuCompanyView;
    @FXML
    private MenuItem mnuVendorAdd, mnuVendorUpdate, mnuVendorView;
    @FXML
    private MenuItem mnuCurrencyAdd, mnuCurrencyUpdate, mnuCurrencyView;
    @FXML
    private MenuItem mnuAccountCategoryAdd, mnuAccountCategoryUpdate, mnuAccountCategoryView;
    @FXML
    private MenuItem mnuAdvancedIDGeneratorUpdate, mnuAdvancedIDGeneratorView;
    @FXML
    private MenuItem mnuChartAccountAdd, mnuChartAccountUpdate, mnuChartAccountView;
    @FXML
    private MenuItem mnuBankAccountAdd, mnuBankAccountUpdate, mnuBankAccountView;

    @FXML
    private MenuItem mnuGeneralPostingGroupAdd, mnuGeneralPostingGroupUpdate, mnuGeneralPostingGroupView;
    @FXML
    private MenuItem mnuCustomerPostingGroupAdd, mnuCustomerPostingGroupUpdate, mnuCustomerPostingGroupView;
    @FXML
    private MenuItem mnuVendorPostingGroupAdd, mnuVendorPostingGroupUpdate, mnuVendorPostingGroupView;

    @FXML
    private MenuItem mnuVATPostingGroupAdd, mnuVATPostingGroupUpdate, mnuVATPostingGroupView;
    @FXML
    private MenuItem mnuInventoryPostingGroupAdd, mnuInventoryPostingGroupUpdate, mnuInventoryPostingGroupView;
    @FXML
    private MenuItem spmCustomerAdd, spmCustomerUpdate, spmCustomerView;
    @FXML
    private MenuItem mnuFinancialPeriodAdd, mnuFinancialPeriodUpdate, mnuFinancialPeriodView;
    @FXML
    private MenuItem spmJournalEntryAdd, spmJournalEntryUpdate, spmJournalEntryView;
    @FXML
    private MenuItem mnuGeneralLedger;
    @FXML
    private MenuItem mnuLicenceUpdate;
    @FXML
    private MenuItem mnuToolsCurrentFinancialPeriod;
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
    private MenuItem mnuMeasureGroupAdd, mnuMeasureGroupUpdate, mnuMeasureGroupView;

    @FXML
    private MenuItem mnuItemCategoryAdd, mnuItemCategoryUpdate, mnuItemCategoryView;
    @FXML
    private MenuItem mnuItemTemplateAdd, mnuItemTemplateUpdate, mnuItemTemplateView;
    @FXML
    private MenuItem mnuItemAdd, mnuItemUpdate, mnuItemView;

    @FXML
    private MenuItem mnuOptionsUpdate, mnuOptionsView;

    @FXML
    private MenuItem spmSaleOrderAdd, spmSaleOrderUpdate, spmSaleOrderView;

    @FXML
    private MenuItem spmInvoiceAdd, spmInvoiceUpdate, spmInvoiceView;
    @FXML
    private MenuItem spmReceiptAdd, spmReceiptUpdate, spmReceiptView;

    @FXML
    SplitMenuButton spmCustomer, spmInvoicing, spmJournalEntry;

    protected void init() throws IOException {
        desktopPane.setMinHeight(stpMain.getHeight());
        stpMain.getChildren().add(desktopPane);
        Navigation.setParents(desktopPane, stpMain);
        setIcons();
        mnuFileClose.setOnAction(e -> System.exit(0));
        mnuFileLogout.setOnAction(e -> {
            CurrentUser.setAppUser(null);
            lblStatusBar.getScene().getWindow().hide();
            LoginHelper.showStage("Login");
        });
        lblStatusBar.setText(CurrentUser.getSimpleUserData());
        mnuToolsCurrentFinancialPeriod.setOnAction(e -> {
            Optional<DBEntity> op = FXUIUtils.getDADialog("Financial Periods", "Set", "Financial Period", new FinancialPeriodDA().getFinancialPeriods(),
                    stpMain.getScene().getWindow());
            if (op.isPresent()) {
                CurrentFinacialPeriod.setFinancialPeriodDA(new FinancialPeriodDA((FinancialPeriod) op.get()));
            }
        });

        editMenuItemClick(mnuSettingsLookupDataAdd, "LookupData", "Lookup Data", FormMode.Save);
        editMenuItemClick(mnuSettingsLookupDataUpdate, "LookupData", "Lookup Data", FormMode.Update);
        viewMenuItemClick(mnuSettingsLookupDataView, new LookupDataDA(), "LookupData", "Lookup Data", true, true);

        editMenuItemClick(mnuAdvancedIDGeneratorUpdate, "IDGenerator", "ID Generator", FormMode.Update);
        viewMenuItemClick(mnuAdvancedIDGeneratorView, new IDGeneratorDA(), "IDGenerator", "ID Generator", true, true);
        loadUI(mnuLicenceUpdate, "Licence", "Licence", 360, false);

        editMenuItemClick(mnuStaffAdd, "Staff", "Staff", FormMode.Save);
        editMenuItemClick(mnuStaffUpdate, "Staff", "Staff", FormMode.Update);
        viewMenuItemClick(mnuStaffView, new StaffDA(), "Staff", "Staff", false, true);

        editMenuItemClick(mnuSettingsUsersAdd, "AppUser", "App User", FormMode.Save);
        editMenuItemClick(mnuSettingsUsersUpdate, "AppUser", "App User", FormMode.Update);
        viewMenuItemClick(mnuSettingsUsersUpdate, new AppUserDA(), "AppUser", "App Users", true, true);

        editMenuItemClick(mnuCompanyAdd, "Company", "Company", FormMode.Save);
        editMenuItemClick(mnuCompanyUpdate, "Company", "Company", FormMode.Update);
        viewMenuItemClick(mnuCompanyView, new CompanyDA(), "Company", "Company", true, true);

//Begin SettingFinance ************************************************************************************
        editMenuItemClick(mnuCurrencyAdd, "Currency", "Currency", FormMode.Save);
        editMenuItemClick(mnuCurrencyUpdate, "Currency", "Currency", FormMode.Update);
        viewMenuItemClick(mnuCurrencyView, new CurrencyDA(), "Currency", "Currency", true, true);

        editMenuItemClick(mnuAccountCategoryAdd, "AccountCategory", "Account Category", FormMode.Save);
        editMenuItemClick(mnuAccountCategoryUpdate, "AccountCategory", "Account Category", FormMode.Update);
        viewMenuItemClick(mnuAccountCategoryView, new AccountCategoryDA(), "AccountCategory", "Account Category", true, true);

        editMenuItemClick(mnuChartAccountAdd, "ChartAccount", "Chart of Account", FormMode.Save);
        editMenuItemClick(mnuChartAccountUpdate, "ChartAccount", "Chart of Account", FormMode.Update);
        viewMenuItemClick(mnuChartAccountView, new ChartAccountDA(), "ChartAccount", "Chart of Account", false, true);

        editMenuItemClick(mnuBankAccountAdd, "BankAccount", "Bank Account", FormMode.Save);
        editMenuItemClick(mnuBankAccountUpdate, "BankAccount", "Bank Account", FormMode.Update);
        viewMenuItemClick(mnuBankAccountView, new BankAccountDA(), "BankAccount", "Bank Account", false, true);

        editMenuItemClick(mnuVendorAdd, "Vendor", "Vendor", FormMode.Save);
        editMenuItemClick(mnuVendorUpdate, "Vendor", "Vendor", FormMode.Update);
        viewMenuItemClick(mnuVendorView, new VendorDA(), "Vendor", "Vendor", false, true);

        editMenuItemClick(mnuGeneralPostingGroupAdd, "GeneralPostingGroup", "General Posting Group", FormMode.Save);
        editMenuItemClick(mnuGeneralPostingGroupUpdate, "GeneralPostingGroup", "General Posting Group", FormMode.Update);
        viewMenuItemClick(mnuGeneralPostingGroupView, new GeneralPostingGroupDA(), "GeneralPostingGroup", "General Posting Group", false, true);

        editMenuItemClick(mnuCustomerPostingGroupAdd, "CustomerPostingGroup", "Customer Posting Group", FormMode.Save);
        editMenuItemClick(mnuCustomerPostingGroupUpdate, "CustomerPostingGroup", "Customer Posting Group", FormMode.Update);
        viewMenuItemClick(mnuCustomerPostingGroupView, new CustomerPostingGroupDA(), "CustomerPostingGroup", "Customer Posting Group", false, true);

        editMenuItemClick(mnuVendorPostingGroupAdd, "VendorPostingGroup", "Vendor Posting Group", FormMode.Save);
        editMenuItemClick(mnuVendorPostingGroupUpdate, "VendorPostingGroup", "Vendor Posting Group", FormMode.Update);
        viewMenuItemClick(mnuVendorPostingGroupView, new VendorPostingGroupDA(), "VendorPostingGroup", "Vendor Posting Group", false, true);

        editMenuItemClick(mnuVATPostingGroupAdd, "VATPostingGroup", "VAT Posting Group", FormMode.Save);
        editMenuItemClick(mnuVATPostingGroupUpdate, "VATPostingGroup", "VAT Posting Group", FormMode.Update);
        viewMenuItemClick(mnuVATPostingGroupView, new VATPostingGroupDA(), "VATPostingGroup", "VAT Posting Group", false, true);

        editMenuItemClick(mnuInventoryPostingGroupAdd, "InventoryPostingGroup", "Inventory Posting Group", FormMode.Save);
        editMenuItemClick(mnuInventoryPostingGroupUpdate, "InventoryPostingGroup", "Inventory Posting Group", FormMode.Update);
        viewMenuItemClick(mnuInventoryPostingGroupView, new InventoryPostingGroupDA(), "InventoryPostingGroup", "Inventory Posting Group", false, true);

        editMenuItemClick(spmCustomerAdd, "Customer", "Customer", FormMode.Save);
        editMenuItemClick(spmCustomerUpdate, "Customer", "Customer", FormMode.Update);
        viewMenuItemClick(spmCustomerView, new CustomerDA(), "Customer", "Customer", false, true);
        editMenuItemClick(mnuFinancialPeriodAdd, "FinancialPeriod", "Financial Period", FormMode.Save);
        editMenuItemClick(mnuFinancialPeriodUpdate, "FinancialPeriod", "Financial Period", FormMode.Update);
        viewMenuItemClick(mnuFinancialPeriodView, new FinancialPeriodDA(), "FinancialPeriod", "Financial Period", true, true);

// End SettingFinance ***********************************************************************
//        Begin Accountant
        editMenuItemClick(spmJournalEntryAdd, "JournalEntry", "Journal Entry", FormMode.Save);
        editMenuItemClick(spmJournalEntryUpdate, "JournalEntry", "Journal Entry", FormMode.Update);
        viewMenuItemClick(spmJournalEntryView, new JournalEntryDA(), "JournalEntry", "JournalEntryView", "Journal Entry", false, true);
        viewMenuItemClickIgnoreParent(mnuGeneralLedger, new GeneralLedgerDA(), "GeneralLedger", "General Ledger", true, true);

//End Account
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
        editMenuItemClick(mnuMeasureGroupAdd, "MeasureGroup", "Measure Group", FormMode.Save);
        editMenuItemClick(mnuMeasureGroupUpdate, "MeasureGroup", "Measure Group", FormMode.Update);
        viewMenuItemClick(mnuMeasureGroupView, new MeasureGroupDA(), "MeasureGroup", "Measure Group", false, true);

        editMenuItemClick(mnuItemCategoryAdd, "ItemCategory", "Item Category", FormMode.Save);
        editMenuItemClick(mnuItemCategoryUpdate, "ItemCategory", "Item Category", FormMode.Update);
        viewMenuItemClick(mnuItemCategoryView, new ItemCategoryDA(), "ItemCategory", "Item Category", false, true);

        editMenuItemClick(mnuItemTemplateAdd, "ItemTemplate", "Item Template", FormMode.Save);
        editMenuItemClick(mnuItemTemplateUpdate, "ItemTemplate", "Item Template", FormMode.Update);
        viewMenuItemClick(mnuItemTemplateView, new ItemTemplateDA(), "ItemTemplate", "ItemTemplateView", "Item Template", false, true);

        editMenuItemClick(mnuItemAdd, "Item", "Item", FormMode.Save);
        editMenuItemClick(mnuItemUpdate, "Item", "Item", FormMode.Update);
        viewMenuItemClick(mnuItemView, new ItemDA(), "Item", "Item", false, true);

        editMenuItemClick(spmSaleOrderAdd, "SaleOrder", "Sale Orders", FormMode.Save);
        editMenuItemClick(spmSaleOrderUpdate, "SaleOrder", "Sale Orders", FormMode.Update);
        viewMenuItemClick(spmSaleOrderView, new SaleOrderDA(), "SaleOrder", "Sale Orders", false, true);

        editMenuItemClick(spmInvoiceAdd, "Invoice", "Invoice", FormMode.Save);
        editMenuItemClick(spmInvoiceUpdate, "Invoice", "Invoice", FormMode.Update);
        viewMenuItemClick(spmInvoiceView, new InvoiceDA(), "Invoice", "Invoice", false, true);

        editMenuItemClick(spmReceiptAdd, FontAwesomeIcon.MONEY, "Receipt", "Receipt", FormMode.Save);
        editMenuItemClick(spmReceiptUpdate, "Receipt", "Receipt", FormMode.Update);
        viewMenuItemClick(spmReceiptView, new ReceiptDA(), "Receipt", "Receipt", false, true);

    }

    private void setIcons() {
        spmCustomer.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.USER));
        spmInvoicing.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.MONEY));
        spmJournalEntry.setGraphic(GlyphsDude.createIcon(FontAwesomeIcon.BOOK));
    }

}
