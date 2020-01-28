/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.dbaccess;

import com.saburi.common.entities.IDGenerator;
import javafx.beans.property.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import com.saburi.common.utils.SearchColumn;
import com.saburi.common.utils.SearchColumn.SearchDataTypes;
import java.util.Optional;

public class IDGeneratorDA extends DBAccess {

    private IDGenerator iDGenerator;
    private SimpleStringProperty objectName = new SimpleStringProperty(this, "objectName");
    private SimpleStringProperty padChar = new SimpleStringProperty(this, "padChar");
    private SimpleIntegerProperty padLength = new SimpleIntegerProperty(this, "padLength");
    private SimpleStringProperty seperator = new SimpleStringProperty(this, "seperator");
    private SimpleIntegerProperty seporatorPosition = new SimpleIntegerProperty(this, "seporatorPosition");
    private SimpleStringProperty startWith = new SimpleStringProperty(this, "startWith");

    public IDGeneratorDA() {
        createSearchColumns();
    }

    public IDGeneratorDA(String persistenceUnit) {
        super(persistenceUnit);
    }

    public IDGeneratorDA(IDGenerator iDGenerator) {
        this.iDGenerator = iDGenerator;
        initialseProprties();
        createSearchColumns();
    }

    public IDGeneratorDA(String persistenceUnit, IDGenerator iDGenerator) {
        super(persistenceUnit);
        this.iDGenerator = iDGenerator;
        initialseProprties();
        createSearchColumns();
    }

    public IDGeneratorDA(String objectName, String padChar, int padLength, String seperator, int seporatorPosition, String startWith) {
        this.iDGenerator = new IDGenerator(objectName, padChar, padLength, seperator, seporatorPosition, startWith);
        initialseProprties();
        createSearchColumns();
    }

    public IDGeneratorDA(String persistenceUnit, String objectName, String padChar, int padLength, String seperator, int seporatorPosition, String startWith) {
        super(persistenceUnit);
        this.iDGenerator = new IDGenerator(objectName, padChar, padLength, seperator, seporatorPosition, startWith);
        initialseProprties();
        createSearchColumns();
    }

    public String getObjectName() {
        return objectName.get();
    }

    public void setObjectName(String objectName) {
        iDGenerator.setObjectName(objectName);
        this.objectName.set(objectName);
    }

    public String getPadChar() {
        return padChar.get();
    }

    public void setPadChar(String padChar) {
        iDGenerator.setPadChar(padChar);
        this.padChar.set(padChar);
    }

    public int getPadLength() {
        return padLength.get();
    }

    public void setPadLength(int padLength) {
        iDGenerator.setPadLength(padLength);
        this.padLength.set(padLength);
    }

    public String getSeperator() {
        return seperator.get();
    }

    public void setSeperator(String seperator) {
        iDGenerator.setSeperator(seperator);
        this.seperator.set(seperator);
    }

    public int getSeporatorPosition() {
        return seporatorPosition.get();
    }

    public void setSeporatorPosition(int seporatorPosition) {
        iDGenerator.setSeporatorPosition(seporatorPosition);
        this.seporatorPosition.set(seporatorPosition);
    }

    public String getStartWith() {
        return startWith.get();
    }

    public void setStartWith(String startWith) {
        iDGenerator.setStartWith(startWith);
        this.startWith.set(startWith);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IDGeneratorDA)) {
            return false;
        }

        IDGeneratorDA iDGeneratorDA = (IDGeneratorDA) o;

        if (iDGeneratorDA.getDBEntity() == null || this.getDBEntity() == null) {
            return false;
        }
        return this.getId().equals(iDGeneratorDA.getId());
    }

    @Override
    public int hashCode() {
        return iDGenerator.getId().hashCode();
    }

    private void initialseProprties() {
        this.dBEntity = iDGenerator;
        this.objectName = new SimpleStringProperty(iDGenerator.getObjectName());
        this.padChar = new SimpleStringProperty(iDGenerator.getPadChar());
        this.padLength = new SimpleIntegerProperty(iDGenerator.getPadLength());
        this.seperator = new SimpleStringProperty(iDGenerator.getSeperator());
        this.seporatorPosition = new SimpleIntegerProperty(iDGenerator.getSeporatorPosition());
        this.startWith = new SimpleStringProperty(iDGenerator.getStartWith());
        initCommonProprties();
    }

    private void createSearchColumns() {
        this.searchColumns.add(new SearchColumn("objectName", "Object Name", this.objectName.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("padChar", "Pad Char", this.padChar.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("padLength", "PadLength", this.padLength.get(), SearchDataTypes.NUMBER));
        this.searchColumns.add(new SearchColumn("seperator", "Separator", this.seperator.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("seporatorPosition", "Separator Position", this.seporatorPosition.get(), SearchDataTypes.NUMBER));
        this.searchColumns.add(new SearchColumn("startWith", "Start With", this.startWith.get(), SearchDataTypes.STRING));
        this.searchColumns.addAll(this.getDefaultSearchColumns());
    }

    @Override
    public List<SearchColumn> getSearchColumns() {
        return this.searchColumns;
    }

    @Override
    public Object getId() {
        return this.iDGenerator.getId();
    }

    @Override
    public String getDisplayKey() {
        return this.iDGenerator.getDisplayKey();
    }

    public static List<IDGeneratorDA> getIDGeneratorDAs(List<IDGenerator> iDGenerators) {
        List<IDGeneratorDA> list = new ArrayList<>();
        iDGenerators.forEach((iDGenerator) -> {
            list.add(new IDGeneratorDA(iDGenerator));
        });
        return list;
    }

    public static List<IDGenerator> getIDGeneratorList(List<IDGeneratorDA> iDGeneratorDAs) {
        List<IDGenerator> iDGenerators = new ArrayList<>();
        iDGeneratorDAs.forEach(a -> iDGenerators.add(a.iDGenerator));
        return iDGenerators;
    }

    public boolean save() throws Exception {
        return super.persist(this.iDGenerator);

    }

    public boolean update() throws Exception {
        return super.merge(this.iDGenerator);

    }

    public boolean delete() {
        return super.remove(this.iDGenerator);

    }

    public IDGenerator getIDGenerator(String objectName) {
        return (IDGenerator) super.find(IDGenerator.class, objectName);
    }

    public List<IDGenerator> getIDGenerators() {
        return super.find(IDGenerator.class);
    }

    @Override
    public List<DBAccess> get() {
        List<DBAccess> list = new ArrayList<>();
        List<IDGenerator> datas = super.find(IDGenerator.class);
        datas.forEach((data) -> {
            list.add(new IDGeneratorDA(data));
        });
        return list;
    }

    public IDGeneratorDA get(String objectName) throws Exception {
        IDGenerator oIDGenerator = (IDGenerator) super.find(IDGenerator.class, objectName);
        if (oIDGenerator == null) {
            throw new Exception("No Record with id: " + objectName);
        }
        return new IDGeneratorDA(oIDGenerator);
    }

    public List<IDGeneratorDA> get(String columName, Object value) {
        List<IDGenerator> data = super.find(IDGenerator.class, columName, value);
        List<IDGeneratorDA> list = new ArrayList<>();
        data.forEach(da -> list.add(new IDGeneratorDA(da)));
        return list;
    }

    public List<Pair<String, Object>> keyValuePairs() {
        List<Pair<String, Object>> pairs = new ArrayList<>();
        this.get().forEach((t) -> pairs.add(t.keyValuePair()));
        return pairs;
    }

    public List<Pair<String, Object>> keyValuePairs(String columName, Object value) {
        List<Pair<String, Object>> pairs = new ArrayList<>();
        this.get(columName, value).forEach((t) -> pairs.add(t.keyValuePair()));
        return pairs;
    }

    public int getMax(String columnName) {
        return super.getMax(IDGenerator.class, columnName);
    }

    public int getMax(String columnName, String comparatorColumn, Object comparatorVaue) {
        return super.getMax(IDGenerator.class, columnName, comparatorColumn, comparatorVaue);
    }

    public List<IDGenerator> getIDGenerators(String columName, Object value) {
        return super.find(IDGenerator.class, columName, value);
    }
    
    public String getToAppendString(String objectName, Object helperValue) {
        String string = String.valueOf(helperValue);
        try {
            IDGeneratorDA _IDGeneratorDA = this.get(objectName);
            Optional<IDGeneratorDA> oIDGenerator = Optional.of(_IDGeneratorDA);
            String paddChar = oIDGenerator.map(x -> x.getPadChar()).orElse("0");
            int paddLength = oIDGenerator.map(x -> x.getPadLength()).orElse(0);
            String seperater = oIDGenerator.map(x -> x.getSeperator()).orElse("");
            int seperatorPosition = oIDGenerator.map(x -> x.getSeporatorPosition()).orElse(2)-1;
            String startsWith = oIDGenerator.map(x -> x.getStartWith()).orElse("");

//            for (int i = 1; i < paddLength; i++) {
//                paddChar += paddChar;
//            }

             String concated = String.format("%"+paddLength+"d", helperValue).replace(" ", paddChar);
            string = startsWith.concat(paddChar.concat(concated));
            return new StringBuffer(string).insert(seperatorPosition, seperater).toString();

        } catch (Exception e) {

        }
        return string;
    }
    
     public String getToAppendString(String objectName,Object generatorValue, int helperValue) {
        String string = String.valueOf(helperValue);
        try {
            IDGeneratorDA _IDGeneratorDA = this.get(objectName);
            Optional<IDGeneratorDA> oIDGenerator = Optional.of(_IDGeneratorDA);
            String paddChar = oIDGenerator.map(x -> x.getPadChar()).orElse("0");
            int paddLength = oIDGenerator.map(x -> x.getPadLength()).orElse(0);
            String seperater = oIDGenerator.map(x -> x.getSeperator()).orElse("");
            int seperatorPosition = (oIDGenerator.map(x -> x.getSeporatorPosition()).orElse(2))-1;
            String startsWith = oIDGenerator.map(x -> x.getStartWith()).orElse("");

//            for (int i = 1; i < paddLength; i++) {
//                paddChar += paddChar;
//            }
            String concated = String.format("%"+paddLength+"d", helperValue).replace(" ", paddChar);
            string = startsWith.concat(generatorValue.toString()).concat(concated);
            if(!seperater.equals(""))
            string= new StringBuffer(string).insert(seperatorPosition, seperater).toString();

        } catch (Exception e) {

        }
        return string;
    }


}
