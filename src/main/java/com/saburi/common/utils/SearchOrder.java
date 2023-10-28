/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;


/**
 *
 * @author CLINICMASTER13
 */
public class SearchOrder {
     public static  enum SearchOrders{ASC, DESC}
    private String columnName;
    private SearchOrders searchOrder;

    public SearchOrder(String columnName, SearchOrders searchOrder) {
        this.columnName = columnName;
        this.searchOrder = searchOrder;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public SearchOrders getSearchOrder() {
        return searchOrder;
    }

    public void setSearchOrder(SearchOrders searchOrder) {
        this.searchOrder = searchOrder;
    }
    
    
    
}
