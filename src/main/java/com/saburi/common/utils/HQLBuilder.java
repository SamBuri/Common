/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author CLINICMASTER13
 */
public class HQLBuilder {

    private Class type;
    private  String className;
    private  String queryText;
    private  SearchColumn defaultSearchColumn;
    List<SearchColumn> searchColumns= new ArrayList<>();
   
    
//    public HQLBuilder(Class type, SearchColumn defaultSearchColumn) {
//        this.type = type;
//        this.className = type.getSimpleName();
//        this.defaultSearchColumn = defaultSearchColumn;
//        this.queryText = "from " + className+" where "+defaultSearchColumn.getSQLString();
//        System.out.println(this.queryText);
//    }
    
    public String getClassName() {
        return className;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getQueryText() {
        return queryText;
    }

    public SearchColumn getDefaultSearchColumn() {
        return defaultSearchColumn;
    }

   
    public List<SearchColumn> getSearchColumns() {
        return searchColumns;
    }

//    public void setSearchColumns(List<SearchColumn> searchColumns) {
//        this.searchColumns = searchColumns;
//        searchColumns.forEach(s->s.getSQLString());
//        System.out.println(this.queryText);
//    }
    
}
