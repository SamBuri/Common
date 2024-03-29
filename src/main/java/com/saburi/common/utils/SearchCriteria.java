/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.utils;

import com.saburi.common.dbaccess.DBAccess;
import javafx.beans.property.*;
import com.saburi.common.utils.CommonEnums.LogicalOperators;
import com.saburi.common.utils.SearchColumn.SearchDataTypes;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class SearchCriteria {

    private final SimpleObjectProperty operator = new SimpleObjectProperty(this, "operator");
    private final SimpleStringProperty searchColumnDisplay = new SimpleStringProperty(this, "searchColumnDisplay");
    private final SimpleObjectProperty searchColumnID = new SimpleObjectProperty(this, "searchColumnID");
    private SearchColumn searchColumn;
    private final SimpleStringProperty columnName = new SimpleStringProperty(this, "columnName");
    private final SimpleStringProperty searchType = new SimpleStringProperty(this, "searchType");

    private final SimpleObjectProperty value = new SimpleObjectProperty(this, "value");
    private final SimpleObjectProperty secondValue = new SimpleObjectProperty(this, "secondValue");
    private final SimpleBooleanProperty negate = new SimpleBooleanProperty(this, "negate");
    private final SimpleStringProperty valueDisplay = new SimpleStringProperty(this, "valueDisplay");
    private final SimpleStringProperty secondValueDisplay = new SimpleStringProperty(this, "secondValueDisplay");

    public SearchCriteria(SearchColumn searchColumn, String searchType, String value, String secondValue, boolean negate) {
        this.searchColumn = searchColumn;
        if (this.searchColumn != null) {
            this.columnName.set(searchColumn.getCaption());
        }
        this.searchType.set(searchType);
        this.value.set(value);
        this.secondValue.set(secondValue);
        this.setDisplay();
        this.negate.set(negate);
    }

    public SearchCriteria(LogicalOperators operator, SearchColumn searchColumn, String searchType, Object value, Object secondValue, boolean negate) {
        this.operator.set(operator);
        this.searchColumn = searchColumn;
        if (this.searchColumn != null) {
            this.columnName.set(searchColumn.getCaption());
        }
        this.searchType.set(searchType);
        this.value.set(value);
        this.secondValue.set(secondValue);
        this.setDisplay();
        this.negate.set(negate);
    }

    private void setDisplay() {
        SearchDataTypes searchDataType = this.searchColumn.getDataType();
        switch (searchDataType) {
            case DATE:

                this.valueDisplay.set(Utilities.formatDate((LocalDate) value.get()));

                this.secondValueDisplay.set(Utilities.formatDate((LocalDate) this.secondValue.get()));
                break;
            case NUMBER:
                if (Utilities.isDouble(value.get())) {
                    this.valueDisplay.set(Utilities.formatNumber((double) this.value.get()));
                } else {
                    this.valueDisplay.set("");
                }
                if (Utilities.isDouble(this.secondValue.get())) {
                    this.secondValueDisplay.set(Utilities.formatNumber((double) this.secondValue.get()));
                } else {
                    this.secondValueDisplay.set("");
                }
                break;
            case MONEY:
                if (Utilities.isDouble(value.get())) {
                    this.valueDisplay.set(Utilities.formatNumber((double) this.value.get()));
                } else {
                    this.valueDisplay.set("");
                }
                if (Utilities.isDouble(this.secondValue.get())) {
                    this.secondValueDisplay.set(Utilities.formatNumber((double) this.secondValue.get()));
                } else {
                    this.secondValueDisplay.set("");
                }
                break;
            case INTEGER:
                if (Utilities.isDouble(value.get())) {
                    this.valueDisplay.set(Utilities.formatInteger((double) this.value.get()));
                } else {
                    this.valueDisplay.set("");
                }

                if (Utilities.isDouble(this.secondValue.get())) {
                    this.secondValueDisplay.set(Utilities.formatInteger((double) this.secondValue.get()));
                } else {
                    this.secondValueDisplay.set("");
                }
                break;
            default:
                valueDisplay.set(String.valueOf(value.get()));
                secondValueDisplay.set(String.valueOf(secondValue.get()));
                break;
        }

    }

    public Object getOperator() {
        return operator.get();
    }

    public void setOperator(LogicalOperators operator) {
        this.operator.set(operator);
    }

    public SearchColumn getSearchColumn() {
        return searchColumn;
    }

    public Object getSearchColumnID() {
        return searchColumnID.get();
    }

    public String getSearchColumnDisplay() {
        return searchColumnDisplay.get();
    }

    public String getColumnName() {
        return columnName.get();
    }

    public void setColumnName(String columnName) {
        this.columnName.set(columnName);
    }

    public String getSearchType() {
        return searchType.get();
    }

    public void setSearchType(String searchType) {
        this.searchType.set(searchType);

    }

    public Object getValue() {
        return value.get();
    }

    public String getValueDisplay() {
        return valueDisplay.get();
    }

    public Object getSecondValue() {
        return secondValue.get();
    }

    public void setSecondValue(Object secondValue) {
        this.secondValue.set(secondValue);
    }

    public void setSecondValueDisplay(String secondValueDisplay) {
        this.secondValueDisplay.set(secondValueDisplay);
    }

    public String getSecondValueDisplay() {
        return secondValueDisplay.get();
    }

    public boolean isNegate() {
        return negate.get();
    }

    public void setNegate(boolean negate) {
        this.negate.set(negate);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.operator);
        hash = 43 * hash + Objects.hashCode(this.searchColumnDisplay);
        hash = 43 * hash + Objects.hashCode(this.searchColumnID);
        hash = 43 * hash + Objects.hashCode(this.searchColumn);
        hash = 43 * hash + Objects.hashCode(this.columnName);
        hash = 43 * hash + Objects.hashCode(this.searchType);
        hash = 43 * hash + Objects.hashCode(this.value);
        hash = 43 * hash + Objects.hashCode(this.secondValue);
        hash = 43 * hash + Objects.hashCode(this.negate);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SearchCriteria other = (SearchCriteria) obj;
        if (!Objects.equals(this.operator, other.operator)) {
            return false;
        }
        if (!Objects.equals(this.searchColumnDisplay, other.searchColumnDisplay)) {
            return false;
        }
        if (!Objects.equals(this.searchColumnID, other.searchColumnID)) {
            return false;
        }
        if (!Objects.equals(this.searchColumn, other.searchColumn)) {
            return false;
        }
        if (!Objects.equals(this.columnName, other.columnName)) {
            return false;
        }
        if (!Objects.equals(this.searchType, other.searchType)) {
            return false;
        }
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        if (!Objects.equals(this.secondValue, other.secondValue)) {
            return false;
        }
        if (!Objects.equals(this.negate, other.negate)) {
            return false;
        }
        return true;
    }

    public Predicate<DBAccess> makePredicate() {
        Predicate<DBAccess> cp = (p) -> true;
        String cType = this.searchType.get();
        String valueString = this.getValue().toString();

        if (cType.equalsIgnoreCase(SearchColumn.SearchType.Equal.name())) {
            cp = this.searchColumn.equalsPrediacte(searchColumn, value.get());
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Contains.name())) {
            cp = this.searchColumn.containsPrediacte(searchColumn, valueString);
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Less.name())) {
            cp = this.searchColumn.lessPrediacte(searchColumn, valueString);
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Less_Or_Equal.name())) {
            cp = this.searchColumn.lessOrEqualPrediacte(searchColumn, valueString);
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Greater.name())) {
            cp = this.searchColumn.greaterPrediacte(searchColumn, valueString);
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Greater_Or_Equal.name())) {
            cp = this.searchColumn.greaterOrEqualPrediacte(searchColumn, valueString);
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Between.name())) {
            cp = this.searchColumn.betweenPredicate(searchColumn, value.get(), secondValue.get());
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Between_Inclusive.name())) {
            cp = this.searchColumn.betweenInclusivePredicate(searchColumn, value.get(), secondValue.get());
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Begins.name())) {
            cp = this.searchColumn.beginsPrediacte(searchColumn, valueString);
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Ends.name())) {
            cp = this.searchColumn.endsPrediacte(searchColumn, valueString);
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Length_Equal.name())) {
            cp = this.searchColumn.lengthEqualPredicate(searchColumn, valueString);
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Length_Greater.name())) {
            cp = this.searchColumn.lengthGreaterPredicate(searchColumn, valueString);
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Length_Less.name())) {
            cp = this.searchColumn.lengthLessPrediacte(searchColumn, valueString);
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.After.name())) {
            cp = this.searchColumn.dateAfterPredicate(searchColumn, (LocalDate) getValue());
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Before.name())) {
            cp = this.searchColumn.dateBeforePredicate(searchColumn, (LocalDate) getValue());
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.After_Or_Equal.name())) {
            cp = this.searchColumn.dateAfterPredicateOrEqual(searchColumn, (LocalDate) getValue());
        } else if (cType.equalsIgnoreCase(SearchColumn.SearchType.Before_Or_Equal.name())) {
            cp = this.searchColumn.dateBeforeOrEqualPredicate(searchColumn, (LocalDate) getValue());
        }

        if (this.isNegate()) {
//            cp.negate();
            return Predicate.not(cp);

        }

        return cp;
    }

    public static Predicate<DBAccess> makePredicate(List<SearchColumn> searchColumns, String value, String searchType, boolean negate) {

        
        List<SearchColumn> filteredColumns = searchColumns;
        filteredColumns.removeIf((p)->p==null);
        filteredColumns.removeIf((p)->p.getName().isBlank());
        if (filteredColumns.isEmpty()) {
            return (p) -> true;
        }
        SearchColumn sColumn =  filteredColumns.get(0);
        SearchColumn search = new SearchColumn(sColumn.getName(),sColumn.getCaption(), sColumn.getValue(), SearchDataTypes.STRING);
        SearchCriteria sc = new SearchCriteria(LogicalOperators.AND, search, searchType, value, "", negate);
        Predicate<DBAccess> predicate = sc.makePredicate();
        for (SearchColumn searchColum : filteredColumns) {
            SearchColumn column = new SearchColumn(searchColum.getName(),searchColum.getCaption(), searchColum.getValue(), SearchDataTypes.STRING);
            SearchCriteria searchCriteria = new SearchCriteria(LogicalOperators.OR, column, searchType, value, "", negate);
            predicate = predicate.or(searchCriteria.makePredicate());

        }
        return predicate;
    }

}
