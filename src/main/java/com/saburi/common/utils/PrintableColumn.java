/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import com.saburi.common.utils.CommonEnums.NumericDataTypes;
import javafx.scene.control.TableColumn;

/**
 *
 * @author Hp
 */
public class PrintableColumn {

    private TableColumn<?, ?> tableColumn;
    private String title = "";
    private NumericDataTypes numericDataType;
    private boolean computeTotal = false;
    private double doubleTotal = 0;
    private int intTotal = 0;

    public PrintableColumn(TableColumn<?, ?> tableColumn) {
        this.tableColumn = tableColumn;
    }

    public PrintableColumn(TableColumn<?, ?> tableColumn, NumericDataTypes numericDataType) {
        this.tableColumn = tableColumn;
        this.numericDataType = numericDataType;
    }

    public PrintableColumn(TableColumn<?, ?> tableColumn, NumericDataTypes numericDataType, boolean computeTotal) {
        this.tableColumn = tableColumn;
        this.numericDataType = numericDataType;
        this.computeTotal = computeTotal;
    }

    public PrintableColumn(TableColumn<?, ?> tableColumn, String title) {
        this.tableColumn = tableColumn;
        this.title = title;
    }

    public PrintableColumn(TableColumn<?, ?> tableColumn, String title, NumericDataTypes numericDataType) {
        this.tableColumn = tableColumn;
        this.title = title;
        this.numericDataType = numericDataType;
    }

    public PrintableColumn(TableColumn<?, ?> tableColumn, String title, NumericDataTypes numericDataType, boolean computeTotal) {
        this.tableColumn = tableColumn;
        this.title = title;
        this.numericDataType = numericDataType;
        this.computeTotal = computeTotal;
    }

    public PrintableColumn(String title) {
        this.title = title;
    }

    public PrintableColumn(String title, NumericDataTypes numericDataType) {
        this.title = title;
        this.numericDataType = numericDataType;
    }

    public PrintableColumn(String title, NumericDataTypes numericDataType, boolean computeTotal) {
        this.title = title;
        this.numericDataType = numericDataType;
        this.computeTotal = computeTotal;
    }

    public TableColumn<?, ?> getTableColumn() {
        return tableColumn;
    }

    public String getTitle() {
        return title;
    }

    public NumericDataTypes getNumericDataType() {
        return numericDataType;
    }

    public boolean isComputeTotal() {
        return computeTotal;
    }

    public double getDoubleTotal() {
        return doubleTotal;
    }

    public void setDoubleTotal(double doubleTotal) {
        this.doubleTotal = doubleTotal;
    }

    public int getIntTotal() {
        return intTotal;
    }

    public void setIntTotal(int intTotal) {
        this.intTotal = intTotal;
    }

}
