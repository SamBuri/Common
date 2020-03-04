/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

/**
 *
 * @author ClinicMaster13
 */
public class CommonEnums {

    public static enum DataTypes {
        NUMBER, STRING, BOOLEAN, DATE
    }
    
    public static enum NumericDataTypes {
        INTEGER, FLOAT, DOUBLE
    }

    public enum Rights {
        Create, Read, Update, Delete, Print
    }

    public enum ObjectTypes {
        Table, View, Form
    };

    public static enum Gender {
        Male, Female
    };

    public enum OpenStatus {
        Open, Closed
    };

    public enum ActiveStatus {
        Active, In_Active
    };

    public enum AccountActions {
        Debit, Credit
    };

    public enum AccountReports {
        Balance_Sheet, Financial_Statement
    };

    public enum AccountTypes {
        Asset, Liability, Equity, Income, Expense
    };

    public enum BusinessTypes {
        Product, Service, Mixed
    };

    public enum DocumentTypes {
        Invoice, Receipt, Credit_Memo, Refund
    };

    public enum PostStatus {
        Posted, Pending
    };

    public enum YesNo {
        Yes, No
    };

    public enum LicenceTypes {
        Secondary, Primary, SaacoLine
    }

    public enum SearchItemTypes {
        Entinty, Revision
    }

    public enum InvoiceStatus {
        Pending, Invoiced, Cancelled
    }

    public enum InvoiceTypes {
        Direct, Ordered
    }

    public enum EntryPoints {
        Billing, Invoicing, Receipting, Registration
    }

    public enum PayModes {
        Cash, Bank
    }

    public enum BankAccountTypes {
        Cash, Bank
    }

    public enum EntryModes {
        Mannual, System
    }

    public enum ItemCategoryGroups {
        Inventory, Service
    }

    public enum UnitMeasureUsages {
        Invoicing, Purchasing
    }

    public enum AccountGroups {
        COA, Customer, Vender, Bank, Item
    }

    public enum JournalTypes {
       General, Sales, Purchases, Bank, Items
    }

}
