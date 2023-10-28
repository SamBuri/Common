/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

//import static helpers.FXUIUtils.getDouble;
import com.saburi.common.dbaccess.DBAccess;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * @author ClinicMaster13
 */
public class SearchColumn {

    private String name;
    private String caption;
    private Object value;
    private Object dispValue;
    private boolean visible = true;
    private SearchDataTypes dataType;
    private List searchTypes;
    private SearchType defaultSearchType;
//    private LogicalOperators predicateType;

    public static enum SearchDataTypes {
        NUMBER, MONEY, INTEGER, STRING, BOOLEAN, DATE
    }

    public static enum SearchType {
        Contains, Equal, Between, Between_Inclusive, Less, Less_Or_Equal, Greater, Greater_Or_Equal, Before, Before_Or_Equal, After, After_Or_Equal, Ends, Begins, Length_Less,
        Length_Greater, Length_Equal
    }

    public static enum StringSearchTypes {
        Contains, Equal, Begins, Ends, Length_Less, Length_Greater, Length_Equal
    }

    public static enum NunberSearchTypes {
        Contains, Equal, Between, Between_Inclusive, Less, Less_Or_Equal, Greater, Greater_Or_Equal, Begins, Ends, Length_Less, Length_Greater,
        Length_Equal
    }

    public static enum BooleanSearchTypes {
        Equal
    }

    public static enum DateSearchTypes {
        Equal, Between, Between_Inclusive, Before, Before_Or_Equal, After, After_Or_Equal
    }

    public SearchColumn(String name, Object value) {
        this.name = name;
        this.value = value;
    }

//    public SearchColumn(String name, Object value, LogicalOperators predicateType) {
//        this.name = name;
//        this.value = value;
//        this.predicateType = predicateType;
//    }
//
//    public SearchColumn(String name, Object value, SearchType defaultSearchType, LogicalOperators predicateType) {
//        this.name = name;
//        this.value = value;
//        this.defaultSearchType = defaultSearchType;
//        this.predicateType = predicateType;
//    }
    public SearchColumn(String name, Object value, SearchType defaultSearchType) {
        this.name = name;
        this.value = value;
        this.defaultSearchType = defaultSearchType;
    }

//    public SearchColumn(String name, Object value, SearchType defaultSearchType, LogicalOperators predicateType, boolean visible) {
//        this.name = name;
//        this.value = value;
//        this.defaultSearchType = defaultSearchType;
//        this.predicateType = predicateType;
//        this.visible = visible;
//    }
//    public SearchColumn(String name, Object value, SearchType defaultSearchType, boolean visible) {
//        this.name = name;
//        this.value = value;
//        this.defaultSearchType = defaultSearchType;
//        this.visible = visible;
//    }
    public SearchColumn(String name, String caption, Object value, SearchDataTypes dataType) {
        this.name = name;
        this.caption = caption;
        this.dataType = dataType;
        this.value = value;
        this.setDefautSearchType(dataType);
    }

    public SearchColumn(String name, String caption, Object value, SearchDataTypes dataType, boolean visible) {
        this.name = name;
        this.caption = caption;
        this.dataType = dataType;
        this.value = value;
        this.visible = visible;
        switch (dataType) {
            case STRING:
                this.searchTypes = Arrays.asList(StringSearchTypes.values());
                this.defaultSearchType = SearchType.Contains;
                break;
            case NUMBER:
                this.searchTypes = Arrays.asList(NunberSearchTypes.values());
                this.defaultSearchType = SearchType.Equal;
                break;
            case DATE:
                this.searchTypes = Arrays.asList(DateSearchTypes.values());
                this.defaultSearchType = SearchType.Between;
                break;

            case BOOLEAN:
                this.searchTypes = Arrays.asList(BooleanSearchTypes.values());
                this.defaultSearchType = SearchType.Equal;
                break;
            default:
                this.searchTypes = Arrays.asList(StringSearchTypes.values());
                this.defaultSearchType = SearchType.Contains;
                break;
        }
    }

    public SearchColumn(String name, String caption, Object value, Object dispValue, SearchDataTypes dataType) {
        this.name = name;
        this.caption = caption;
        this.dataType = dataType;
        this.value = value;
        this.dispValue = dispValue;
        this.setDefautSearchType(dataType);
    }

    public SearchColumn(String name, String caption, Object value, Object dispValue, SearchDataTypes dataType, boolean visible) {
        this.name = name;
        this.caption = caption;
        this.dataType = dataType;
        this.value = value;
        this.dispValue = dispValue;
        this.visible = visible;
        this.setDefautSearchType(dataType);
    }

    public SearchColumn(String name, String caption, Object value, SearchDataTypes dataType, SearchType defaultSearchType) {
        this.name = name;
        this.caption = caption;
        this.dataType = dataType;
        this.value = value;
        this.defaultSearchType = defaultSearchType;
        this.setDefautSearchType(dataType);
    }

    public SearchColumn(String name, String caption, Object value, SearchDataTypes dataType, SearchType defaultSearchType, boolean visible) {
        this.name = name;
        this.caption = caption;
        this.dataType = dataType;
        this.value = value;
        this.defaultSearchType = defaultSearchType;
        this.visible = visible;
        this.setDefautSearchType(dataType);
    }

    public SearchColumn(String name, String caption, Object value, Object dispValue, SearchDataTypes dataType, SearchType defaultSearchType) {
        this.name = name;
        this.caption = caption;
        this.dataType = dataType;
        this.value = value;
        this.dispValue = dispValue;
        this.defaultSearchType = defaultSearchType;
        this.setDefautSearchType(dataType);
    }

    private void setDefautSearchType(SearchDataTypes dataType) {
        switch (dataType) {
            case STRING:
                this.searchTypes = Arrays.asList(StringSearchTypes.values());
                if (this.defaultSearchType == null) {
                    this.defaultSearchType = SearchType.Contains;
                }
                break;
            case NUMBER:
                this.searchTypes = Arrays.asList(NunberSearchTypes.values());
                if (this.defaultSearchType == null) {
                    this.defaultSearchType = SearchType.Equal;
                }
                break;
            case INTEGER:
                this.searchTypes = Arrays.asList(NunberSearchTypes.values());
                if (this.defaultSearchType == null) {
                    this.defaultSearchType = SearchType.Equal;
                }
                break;
            case MONEY:
                this.searchTypes = Arrays.asList(NunberSearchTypes.values());
                if (this.defaultSearchType == null) {
                    this.defaultSearchType = SearchType.Equal;
                }
                break;
            case DATE:
                this.searchTypes = Arrays.asList(DateSearchTypes.values());
                if (this.defaultSearchType == null) {
                    this.defaultSearchType = SearchType.Between;
                }
                break;

            case BOOLEAN:
                this.searchTypes = Arrays.asList(BooleanSearchTypes.values());
                if (this.defaultSearchType == null) {
                    this.defaultSearchType = SearchType.Equal;
                }
                break;
            default:
                this.searchTypes = Arrays.asList(StringSearchTypes.values());
                if (this.defaultSearchType == null) {
                    this.defaultSearchType = SearchType.Contains;
                }
                break;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setDataType(SearchDataTypes dataType) {
        this.dataType = dataType;
    }

    public void setSearchTypes(List searchTypes) {
        this.searchTypes = searchTypes;
    }

    public void setDefaultSearchType(SearchType defaultSearchType) {
        this.defaultSearchType = defaultSearchType;
    }

    public String getName() {
        return name;
    }

    public String getCaption() {
        return caption;
    }

    public SearchDataTypes getDataType() {
        return dataType;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getDispValue() {
        return dispValue;
    }

    public void setDispValue(Object dispValue) {
        this.dispValue = dispValue;
    }

//    public LogicalOperators getPredicateType() {
//        return predicateType;
//    }
//
//    public void setPredicateType(LogicalOperators predicateType) {
//        this.predicateType = predicateType;
//    }
    public List getSearchTypes() {
        return searchTypes;
    }

    public SearchType getDefaultSearchType() {
        return defaultSearchType;
    }

    public Object getValue() {
        return value;
    }

    public boolean isVisible() {
        return visible;
    }

    public boolean valueEquals(Object value) {
        if (this.value == null) {
            return false;
        }
        switch (this.dataType) {
            case BOOLEAN:
                return Objects.equals(Boolean.valueOf((this.value.toString())), Boolean.valueOf(String.valueOf(value.toString())));

            case NUMBER:
                return Double.valueOf(this.getValue().toString()).equals(Double.valueOf(String.valueOf(value.toString())));

            case MONEY:
                return Double.valueOf(this.getValue().toString()).equals(Double.valueOf(String.valueOf(value.toString())));
            case INTEGER:
                return Double.valueOf(this.getValue().toString()).equals(Double.valueOf(String.valueOf(value.toString())));
            case DATE:
                return dateValueEqual((LocalDate) value);
            default:
                return this.value.toString().equalsIgnoreCase(String.valueOf(value));

        }

    }

    public boolean valueContains(String value) {
        return (this.value == null ? "" : this.value).toString().toUpperCase().contains(value.toUpperCase());
    }

    public boolean valueBegins(String value) {
        return (this.value == null ? "" : this.value).toString().toUpperCase().startsWith(value.toUpperCase());
    }

    public boolean valueEnds(String value) {
        return (this.value == null ? "" : this.value).toString().toUpperCase().endsWith(value.toUpperCase());
    }

    public boolean lengthLess(int value) {
        if (this.value == null) {
            return value > 0;
        }
        return (this.value == null ? "" : this.value).toString().trim().length() < value;
    }

    public boolean lengthEqual(int value) {
        if (this.value == null) {
            return value == 0;
        }
        return (this.value == null ? "" : this.value).toString().trim().length() == value;
    }

    public boolean lengthGreater(int value) {
        if (this.value == null) {
            return false;
        }
        return this.value.toString().trim().length() > (value);
    }

    public boolean valueEmpty() {
        if (this.value == null) {
            return true;
        } else if (this.value.toString().isBlank()) {
            return true;
        }
        return false;
    }

    public boolean valueBetween(Object value, Object value1) {
        if (this.value == null) {
            return false;
        }
        if (this.dataType == SearchDataTypes.DATE) {
            return dateValueBetween((LocalDate) value, (LocalDate) value1);
        } else {

            double number = Double.valueOf(this.value.toString());
            return (number > Double.valueOf(value.toString()) && number < Double.valueOf(value1.toString()));
        }
    }

    public boolean valueBetweenInclusive(Object value, Object value1) {
        if (this.value == null) {
            return false;
        }
        if (this.dataType == SearchDataTypes.DATE) {
            return dateValueBetweenInclusive((LocalDate) value, (LocalDate) value1);
        } else {

            double number = Double.valueOf(this.value.toString());
            return (number > Double.valueOf(value.toString()) && number < Double.valueOf(value1.toString())
                    || number == Double.valueOf(value.toString()) || number == Double.valueOf(value1.toString()));
        }
    }

    public boolean dateValueEqual(LocalDate localDate) {
        if (this.value == null) {
            return false;
        }
        return (((LocalDate) this.value).equals(localDate));

    }

    public boolean dateValueBefore(LocalDate localDate) {
        if (this.value == null) {
            return false;
        }
        return ((LocalDate) this.value).isBefore(localDate);

    }

    public boolean dateValueAfter(LocalDate localDate) {
        if (this.value == null) {
            return false;
        }
        return (((LocalDate) this.value).isAfter(localDate));

    }

    public boolean dateValueBeforeOrEqual(LocalDate localDate) {
        if (this.value == null) {
            return false;
        }
        return ((LocalDate) this.value).isBefore(localDate) || ((LocalDate) this.value).equals(localDate);

    }

    public boolean dateValueAfterOrEqual(LocalDate localDate) {
        if (this.value == null) {
            return false;
        }
        return (((LocalDate) this.value).isAfter(localDate)) || ((LocalDate) this.value).equals(localDate);

    }

    public boolean dateValueBetween(LocalDate value, LocalDate value1) {
        if (this.value == null) {
            return false;
        }
        LocalDate date = (LocalDate) this.value;
        return date.isAfter(value) && date.isBefore(value1);

    }

    public boolean dateValueBetweenInclusive(LocalDate value, LocalDate value1) {
        if (this.value == null) {
            return false;
        }
        LocalDate date = (LocalDate) this.value;
        return date.isAfter(value) && date.isBefore(value1)
                || date.equals(value) || date.equals(value1);

    }

    public boolean valueGreater(Object value) {
        if (this.value == null) {
            return false;
        }
        switch (this.dataType) {
            case BOOLEAN:
                return false;

            case NUMBER:
                return Double.valueOf(this.value.toString()) > Double.valueOf(String.valueOf(value.toString()));
            case MONEY:
                return Double.valueOf(this.value.toString()) > Double.valueOf(String.valueOf(value.toString()));
            case INTEGER:
                return Double.valueOf(this.value.toString()) > Double.valueOf(String.valueOf(value.toString()));

            case DATE:
                return ((LocalDate) this.value).isBefore(LocalDate.parse(value.toString()));
            default:
                return false;

        }

    }

    public boolean valueGreaterOrEqual(Object value) {
        if (this.value == null) {
            return false;
        }
        switch (this.dataType) {
            case BOOLEAN:
                return false;

            case NUMBER:
                return Double.valueOf(getValue().toString()) > Double.valueOf(String.valueOf(value.toString()))
                        || Double.valueOf(getValue().toString()).equals(Double.valueOf(String.valueOf(value.toString())));
            case MONEY:
                return Double.valueOf(getValue().toString()) > Double.valueOf(String.valueOf(value.toString()))
                        || Double.valueOf(getValue().toString()).equals(Double.valueOf(String.valueOf(value.toString())));
            case INTEGER:
                return Double.valueOf(getValue().toString()) > Double.valueOf(String.valueOf(value.toString()))
                        || Double.valueOf(getValue().toString()).equals(Double.valueOf(String.valueOf(value.toString())));

            case DATE:
                return false;

            default:
                return false;

        }

    }

    public boolean valueLess(Object value) {
        if (this.value == null) {
            return false;
        }
        switch (this.dataType) {
            case BOOLEAN:
                return false;

            case NUMBER:
                return Double.valueOf(this.value.toString()) < Double.valueOf(String.valueOf(value.toString()));
            case MONEY:
                return Double.valueOf(this.value.toString()) < Double.valueOf(String.valueOf(value.toString()));
            case INTEGER:
                return Double.valueOf(this.value.toString()) < Double.valueOf(String.valueOf(value.toString()));

            case DATE:
                return ((LocalDate) this.value).isAfter(LocalDate.parse(value.toString()));
            default:
                return false;

        }

    }

    public boolean valueLessOrEqual(Object value) {
        if (this.value == null) {
            return false;
        }
        switch (this.dataType) {
            case BOOLEAN:
                return false;

            case NUMBER:
                return Double.valueOf(getValue().toString()) < Double.valueOf(String.valueOf(value.toString()))
                        || Double.valueOf(getValue().toString()).equals(Double.valueOf(String.valueOf(value.toString())));
            case MONEY:
                return Double.valueOf(getValue().toString()) < Double.valueOf(String.valueOf(value.toString()))
                        || Double.valueOf(getValue().toString()).equals(Double.valueOf(String.valueOf(value.toString())));
            case INTEGER:
                return Double.valueOf(getValue().toString()) < Double.valueOf(String.valueOf(value.toString()))
                        || Double.valueOf(getValue().toString()).equals(Double.valueOf(String.valueOf(value.toString())));

            case DATE:
                return false;

            default:
                return false;

        }

    }

    public Predicate<DBAccess> equalsPrediacte(SearchColumn selectedSearchColumn, Object value) {
        return (p) -> p.getSearchColumns().parallelStream().filter((searchColumn)
                -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn)
                -> (searchColumn.valueEquals(value)));

    }

    public Predicate<DBAccess> containsPrediacte(SearchColumn selectedSearchColumn, String value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.valueContains(value)));

    }

    public Predicate<DBAccess> emptyPrediacte(SearchColumn selectedSearchColumn) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.valueEmpty()));

    }

    public Predicate<DBAccess> greaterPrediacte(SearchColumn selectedSearchColumn, String value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().
                equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.valueGreater(value)));

    }

    public Predicate<DBAccess> greaterOrEqualPrediacte(SearchColumn selectedSearchColumn, String value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().
                equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.valueGreaterOrEqual(value)));

    }

    public Predicate<DBAccess> lessPrediacte(SearchColumn selectedSearchColumn, String value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().
                equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.valueLess(value)));

    }

    public Predicate<DBAccess> lessOrEqualPrediacte(SearchColumn selectedSearchColumn, String value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().
                equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.valueLessOrEqual(value)));

    }

    public Predicate<DBAccess> beginsPrediacte(SearchColumn selectedSearchColumn, String value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.valueBegins(value)));

    }

    public Predicate<DBAccess> endsPrediacte(SearchColumn selectedSearchColumn, String value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.valueEnds(value)));

    }

    public Predicate<DBAccess> lengthLessPrediacte(SearchColumn selectedSearchColumn, String value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.lengthLess(Integer.parseInt(value))));

    }

    public Predicate<DBAccess> lengthEqualPredicate(SearchColumn selectedSearchColumn, String value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.lengthEqual(Integer.parseInt(value))));

    }

    public Predicate<DBAccess> lengthGreaterPredicate(SearchColumn selectedSearchColumn, String value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.lengthGreater(Integer.parseInt(value))));
    }

    public Predicate<DBAccess> betweenPredicate(SearchColumn selectedSearchColumn, Object value, Object value1) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.valueBetween(value, value1)));

    }

    public Predicate<DBAccess> betweenInclusivePredicate(SearchColumn selectedSearchColumn, Object value, Object value1) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.valueBetweenInclusive(value, value1)));

    }

    public Predicate<DBAccess> dateEqualPerdicate(SearchColumn selectedSearchColumn, LocalDate value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.dateValueEqual(value)));

    }

    public Predicate<DBAccess> dateBeforePredicate(SearchColumn selectedSearchColumn, LocalDate value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.dateValueBefore(value)));

    }

    public Predicate<DBAccess> dateAfterPredicate(SearchColumn selectedSearchColumn, LocalDate value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.dateValueAfter(value)));

    }

    public Predicate<DBAccess> dateBeforeOrEqualPredicate(SearchColumn selectedSearchColumn, LocalDate value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.dateValueBeforeOrEqual(value)));

    }

    public Predicate<DBAccess> dateAfterPredicateOrEqual(SearchColumn selectedSearchColumn, LocalDate value) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.dateValueAfterOrEqual(value)));

    }

    public Predicate<DBAccess> dateBetweenPredicate(SearchColumn selectedSearchColumn, LocalDate value, LocalDate value1) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.dateValueBetween(value, value1)));

    }

    public Predicate<DBAccess> dateBetweenInclusivePredicate(SearchColumn selectedSearchColumn, LocalDate value, LocalDate value1) {
        return (p) -> p.getSearchColumns().stream().filter((searchColumn) -> (searchColumn.getName().equalsIgnoreCase(selectedSearchColumn.getName()))).anyMatch((searchColumn) -> (searchColumn.dateValueBetweenInclusive(value, value1)));

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.name);
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
        final SearchColumn other = (SearchColumn) obj;
        return Objects.equals(this.name, other.name);
    }

}
