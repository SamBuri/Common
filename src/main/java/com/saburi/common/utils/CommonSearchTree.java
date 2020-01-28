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
    protected final TreeItem<SearchItem> triItem = new TreeItem<>(new SearchItem("Item"));
    protected final TreeItem<SearchItem> triItemRev = new TreeItem<>(new SearchItem("Item"));
    protected final TreeItem<SearchItem> triFinance = new TreeItem<>(new SearchItem("Finance"));
    protected final TreeItem<SearchItem> triFinanceRev = new TreeItem<>(new SearchItem("Finance"));
    protected final TreeItem<SearchItem> triPositingGroup = new TreeItem<>(new SearchItem("Posting Groups"));
    protected final TreeItem<SearchItem> triPositingGroupRev = new TreeItem<>(new SearchItem("Posting Groups"));
    protected final TreeItem<SearchItem> triGeographicalLocation = new TreeItem<>(new SearchItem("Geographical Location"));
    protected final TreeItem<SearchItem> triGeographicalLocationRev = new TreeItem<>(new SearchItem("Geographical Location"));
    protected final TreeItem<SearchItem> triGeneral = new TreeItem<>(new SearchItem("General"));
    protected final TreeItem<SearchItem> triGeneralRev = new TreeItem<>(new SearchItem("General"));
    protected final TreeItem<SearchItem> triAccounting = new TreeItem<>(new SearchItem("Accounting"));
    protected final TreeItem<SearchItem> triAccountingRev = new TreeItem<>(new SearchItem("Accounting"));
    protected final TreeItem<SearchItem> triInvoicing = new TreeItem<>(new SearchItem("Invoicing"));
    protected final TreeItem<SearchItem> triInvoicingRev = new TreeItem<>(new SearchItem("Invoicing"));
    protected List<SearchItem> commonSearchObjects = new ArrayList<>();
    protected List<TreeItem> treeItems;
    private Class mainClass  = App.class;
    private final List<SearchItem> sItems = Arrays.asList(
            //           Setup
            new SearchItem(mainClass, new BankAccountDA(), "BankAccount", "Bank Account", true, triSetup),
            new SearchItem(mainClass, new BankAccountDA(), Revision, "BankAccount", "Bank Account", true, triSetupRev),
            new SearchItem(mainClass, new LookupDataDA(), "LookupData", "LookupData", true, triSetup),
            new SearchItem(mainClass, new LookupDataDA(), Revision, "LookupData", "LookupData", true, triSetupRev),
            new SearchItem(mainClass, new StaffDA(), "Staff", "Staff", false, triSetup),
            new SearchItem(mainClass, new StaffDA(), Revision, "Staff", "Staff", false, triSetupRev),
            new SearchItem(mainClass, new VendorDA(), "Vendor", "Vendor", false, triSetup),
            new SearchItem(mainClass, new VendorDA(), Revision, "Vendor", "Vendor", false, triSetupRev),
            new SearchItem(mainClass, new CountyDA(), "County", "County", true, triGeographicalLocation),
            new SearchItem(mainClass, new SubCountyDA(), "SubCounty", "Sub County", true, triGeographicalLocation),
            new SearchItem(mainClass, new ParishDA(), "Parish", "Parish", true, triGeographicalLocation),
            new SearchItem(mainClass, new VillageDA(), "Village", "Village", true, triGeographicalLocation),
            new SearchItem(mainClass, new CountyDA(), Revision, "County", "County", true, triGeographicalLocationRev),
            new SearchItem(mainClass, new SubCountyDA(), Revision, "SubCounty", "Sub County", true, triGeographicalLocationRev),
            new SearchItem(mainClass, new ParishDA(), Revision, "Parish", "Parish", true, triGeographicalLocationRev),
            new SearchItem(mainClass, new VillageDA(), Revision, "Village", "Village", true, triGeographicalLocationRev),
            new SearchItem(mainClass, new CustomerPostingGroupDA(), "CustomerPostingGroup", "Customer Posting Group", true, triPositingGroup),
            new SearchItem(mainClass, new CustomerPostingGroupDA(), Revision, "CustomerPostingGroup", "Customer Posting Group", true, triPositingGroupRev),
            new SearchItem(mainClass, new InventoryPostingGroupDA(), "InventoryPostingGroup", "Inventory Posting Group", true, triPositingGroup),
            new SearchItem(mainClass, new InventoryPostingGroupDA(), Revision, "InventoryPostingGroup", "Inventory Posting Group", true, triPositingGroupRev),
            new SearchItem(mainClass, new GeneralPostingGroupDA(), "GeneralPostingGroup", "General Posting Group", false, triPositingGroup),
            new SearchItem(mainClass, new GeneralPostingGroupDA(), Revision, "GeneralPostingGroup", "General Posting Group", false, triPositingGroupRev),
            new SearchItem(mainClass, new VATPostingGroupDA(), "VATPostingGroup", "VAT Posting Group", true, triPositingGroup),
            new SearchItem(mainClass, new VATPostingGroupDA(), Revision, "VATPostingGroup", "VAT Posting Group", true, triPositingGroupRev),
            new SearchItem(mainClass, new VendorPostingGroupDA(), "VendorPostingGroup", "Vendor Posting Group", true, triPositingGroup),
            new SearchItem(mainClass, new VendorPostingGroupDA(), Revision, "VendorPostingGroup", "Vendor Posting Group", true, triPositingGroupRev),
            new SearchItem(mainClass, new MeasureGroupDA(), "MeasureGroup", "Measure Group", true, triItem),
            new SearchItem(mainClass, new MeasureGroupDA(), Revision, "MeasureGroup", "Measure Group", true, triItemRev),
            new SearchItem(mainClass, new MeasureRelationDA(), "MeasureRelation", "Measure Relation", true, triItem),
            new SearchItem(mainClass, new MeasureRelationDA(), Revision, "MeasureRelation", "Measure Relation", true, triItemRev),
            new SearchItem(mainClass, new ItemCategoryDA(), "ItemCategory", "Item Category", true, triItem),
            new SearchItem(mainClass, new ItemCategoryDA(), Revision, "ItemCategory", "Item Category", true, triItemRev),
            new SearchItem(mainClass, new ItemTemplateDA(), "ItemTemplate", "Item Template", true, triItem),
            new SearchItem(mainClass, new ItemTemplateDA(), Revision, "ItemTemplate", "Item Template", true, triItemRev),
            new SearchItem(mainClass, new ItemDA(), "Item", "Item", true, triItem),
            new SearchItem(mainClass, new ItemDA(), Revision, "Item", "Item", true, triItemRev),
            //            Set up

            //             Accounting

            new SearchItem(mainClass, new BankLedgerDA(), "BankLedger", "Bank Ledger", false, triAccounting),
            new SearchItem(mainClass, new BankLedgerDA(), Revision, "BankLedger", "Bank Ledger", false, triAccountingRev),
            new SearchItem(mainClass, new CustomerLedgerDA(), "CustomerLedger", "Customer Ledger", false, triAccounting),
            new SearchItem(mainClass, new CustomerLedgerDA(), Revision, "CustomerLedger", "Customer Ledger", false, triAccountingRev),
            new SearchItem(mainClass, new GeneralLedgerDA(), "GeneralLedger", "General Ledger", false, triAccounting),
            new SearchItem(mainClass, new GeneralLedgerDA(), Revision, "GeneralLedger", "General Ledger", false, triAccountingRev),
            new SearchItem(mainClass, new VendorLedgerDA(), "VendorLedger", "Vendor Ledger", false, triAccounting),
            new SearchItem(mainClass, new VendorLedgerDA(), Revision, "VendorLedger", "Vendor Ledger", false, triAccountingRev),
            new SearchItem(mainClass, new JournalEntryDA(), "JournalEntry", "Journal Entries", false, triAccounting),
            new SearchItem(mainClass, new JournalEntryDetailDA(), "JournalEntry", "Journal Entry Details", false, triAccounting),
            //            Accounting

            //            Invoicing
            new SearchItem(mainClass, new SaleOrderDA(), "SaleOrder", "Sale Orders", false, triInvoicing),
            new SearchItem(mainClass, new SaleOrderDA(), Revision, "SaleOrder", "Sale Orders", false, triInvoicingRev),
            new SearchItem(mainClass, new SaleOrderDetailDA(), "SaleOrderDetail", "Sale Order Details", false, triInvoicing),
            new SearchItem(mainClass, new SaleOrderDetailDA(), Revision, "SaleOrderDetail", "Sale Order Details", false, triInvoicingRev),
            new SearchItem(mainClass, new InvoiceDA(), "Invoice", "Invoice", false, triInvoicing),
            new SearchItem(mainClass, new InvoiceDA(), Revision, "Invoice", "Invoice", false, triInvoicingRev),
            new SearchItem(mainClass, new InvoiceDetailsDA(), "InvoiceDetails", "Invoice Details", false, triInvoicing),
            new SearchItem(mainClass, new InvoiceDetailsDA(), Revision, "InvoiceDetails", "Invoice Details", false, triInvoicingRev),
            new SearchItem(mainClass, new ReceiptDA(), "Receipt", "Receipt", false, triInvoicing),
            new SearchItem(mainClass, new ReceiptDA(), Revision, "Receipt", "Receipt", false, triInvoicingRev),
            new SearchItem(mainClass, new ReceiptInvoiceDA(), "ReceiptInvoice", "Receipt Invoice", false, triInvoicing),
            new SearchItem(mainClass, new ReceiptInvoiceDA(), Revision, "ReceiptInvoice", "Receipt Invoice", false, triInvoicingRev),
            //            Invoicing End
            //            General
            new SearchItem(mainClass, new CustomerDA(), "Customer", "Customer", false),
            new SearchItem(mainClass, new CustomerDA(), Revision, "Customer", "Customer", false)
    //            General End
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
        triItem.setExpanded(true);
        triItemRev.setExpanded(true);
        triGeneral.setExpanded(true);
        triGeneralRev.setExpanded(true);
        triInvoicing.setExpanded(true);
        triInvoicingRev.setExpanded(true);
        triEntities.setExpanded(true);
        triRevisions.setExpanded(false);

        sItems.forEach(e -> addTreeItem(e));
        triFinance.getChildren().addAll(triPositingGroup);
        triFinanceRev.getChildren().addAll(triPositingGroupRev);
        triSetup.getChildren().addAll(triGeographicalLocation, triFinance, triItem);
        triSetupRev.getChildren().addAll(triGeographicalLocationRev, triFinanceRev, triItemRev);
        triEntities.getChildren().addAll(triSetup, triGeneral, triInvoicing, triAccounting);
        triRevisions.getChildren().addAll(triSetupRev, triGeneralRev, triInvoicingRev, triAccountingRev);

        this.treeItems = Arrays.asList(triEntities, triRevisions);
        return this.treeItems;
    }

}
