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

    public static enum AggregateFunctions {
        Sum, Average, Minimum, Maximum
    }

    public static enum NumericSearchTypes {
        Equal, Between, Between_Inclusive, Less, Less_Or_Equal, Greater, Greater_Or_Equal
    }

    public static enum LogicalOperators {
        OR, AND
    }

    public enum Rights {
        Create, Read, Update, Delete, Print
    }
    public enum ViewMenuTypes{All, EditOnly,AddOnly, Print, PrintNew, Preview, None}

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

    
    public enum YesNo {
        Yes, No
    };

    public enum LicenseTypes {
        Secondary, Primary, SaacoLine
    }

    public enum SearchItemTypes {
        Entinty, Revision
    }

      public enum ApprovalModes {
        Auto, Manual
    }
      
      public enum EntryModes {
        Manual, System
    }
      
      public enum BusinessTypes{Product, Service, Mixed}
      

}
