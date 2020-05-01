/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.dbaccess;

import com.saburi.common.entities.County;
import javafx.beans.property.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import com.saburi.common.utils.SearchColumn;
import com.saburi.common.utils.SearchColumn.SearchDataTypes;
import com.saburi.common.entities.LookupData;

public class CountyDA extends DBAccess {

    private County county;
    private SimpleIntegerProperty idHelper = new SimpleIntegerProperty(this, "idHelper");
    private SimpleStringProperty districtDisplay = new SimpleStringProperty(this, "districtDisplay");
    private SimpleObjectProperty districtID = new SimpleObjectProperty(this, "districtID");
    private LookupData district;
    private SimpleStringProperty countyID = new SimpleStringProperty(this, "countyID");
    private SimpleStringProperty countyName = new SimpleStringProperty(this, "countyName");

    public CountyDA() {
        createSearchColumns();
    }

    public CountyDA(String persistenceUnit) {
        super(persistenceUnit);
        createSearchColumns();

    }

    public CountyDA(County county) {
        this.county = county;
        initialseProprties();
        createSearchColumns();
    }

    public CountyDA(String persistenceUnit, County county) {
        super(persistenceUnit);
        this.county = county;
        initialseProprties();
        createSearchColumns();
    }

    public CountyDA(LookupData lookupData, String countyID, String countyName) {
        this.county = new County(getNextIdHelper(lookupData), lookupData, countyID, countyName);
        initialseProprties();
        createSearchColumns();
    }

    public CountyDA(String persistenceUnit, LookupData lookupData, String countyID, String countyName) {
        super(persistenceUnit);
        this.county = new County(getNextIdHelper(lookupData), (LookupData) lookupData, countyID, countyName);
        initialseProprties();
        createSearchColumns();
    }

    public int getIdHelper() {
        return idHelper.get();
    }

    public void setIdHelper(int idHelper) {
        county.setIdHelper(idHelper);
        this.idHelper.set(idHelper);
    }

    public LookupData getDistrict() {
        return district;
    }

    public Object getDistrictID() {
        return districtID.get();
    }

    public String getDistrictDisplay() {
        return districtDisplay.get();
    }

    public LookupDataDA getDistrictDA() {
        if (this.district == null) {
            return new LookupDataDA();
        } else {
            return new LookupDataDA(this.district);
        }
    }

    public Pair<String, Object> getDistrictPair() {
        if (this.getDistrictDA() == null) {
            return new Pair<>("", "");
        } else {
            return this.getDistrictDA().keyValuePair();
        }
    }

    public void setDistrict(LookupData district) {
        county.setDistrict(district);
        this.district = district;
    }

    public String getCountyID() {
        return countyID.get();
    }

    public void setCountyID(String countyID) {
        county.setCountyID(countyID);
        this.countyID.set(countyID);
    }

    public String getCountyName() {
        return countyName.get();
    }

    public void setCountyName(String countyName) {
        county.setCountyName(countyName);
        this.countyName.set(countyName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CountyDA)) {
            return false;
        }

        CountyDA countyDA = (CountyDA) o;

        if (countyDA.getDBEntity() == null || this.getDBEntity() == null) {
            return false;
        }
        return this.getId().equals(countyDA.getId());
    }

    @Override
    public int hashCode() {
        return county.getId().hashCode();
    }

    private void initialseProprties() {
        this.dBEntity = county;
        this.idHelper = new SimpleIntegerProperty(county.getIdHelper());
        this.district = county.getDistrict();
        this.districtID = new SimpleObjectProperty(district.getId());
        this.districtDisplay = new SimpleStringProperty(district.getDisplayKey());
        this.countyID = new SimpleStringProperty(county.getCountyID());
        this.countyName = new SimpleStringProperty(county.getCountyName());
        initCommonProprties();

    }

    private void createSearchColumns() {
        this.searchColumns.add(new SearchColumn("districtID", "District ID", this.districtID.get(), SearchDataTypes.STRING, SearchColumn.SearchType.Equal));
        this.searchColumns.add(new SearchColumn("districtDisplay", "District", this.districtDisplay.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("countyID", "County ID", this.countyID.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("countyName", "County Name", this.countyName.get(), SearchDataTypes.STRING));

    }

    @Override
    public List<SearchColumn> getSearchColumns() {
        return this.searchColumns;
    }

    @Override
    public Object getId() {
        return this.county.getId();
    }

    @Override
    public String getDisplayKey() {
        return this.county.getDisplayKey();
    }

    public static List<CountyDA> getCountyDAs(List<County> countys) {
        List<CountyDA> list = new ArrayList<>();
        countys.forEach((county) -> {
            list.add(new CountyDA(county));
        });
        return list;
    }

    public static List<County> getCountyList(List<CountyDA> countyDAs) {
        List<County> countys = new ArrayList<>();
        countyDAs.forEach(a -> countys.add(a.county));
        return countys;
    }

    public boolean save() throws Exception {
        if (isValid()) {
            return super.persist(this.county);
        }
        return true;

    }

    public boolean update() throws Exception {
        if (isValid()) {
            return super.merge(this.county);
        }
           return false;
    }

    public boolean delete() {
        return super.remove(this.county);

    }

    public County getCounty(String countyID) {
        return (County) super.find(County.class, countyID);
    }

    public List<County> getCountys() {
        return super.find(County.class);
    }

    @Override
    public List<DBAccess> get() {
        List<DBAccess> list = new ArrayList<>();
        List<County> datas = super.find(County.class);
        datas.forEach((data) -> {

            list.add(new CountyDA(data));
        });
        return list;
    }

    public CountyDA get(String countyID) throws Exception {
        County oCounty = (County) super.find(County.class, countyID);
        if (oCounty == null) {
            throw new Exception("No Record with id: " + countyID);
        }
        return new CountyDA(oCounty);
    }

    public List<CountyDA> get(String columName, Object value) {
        List<County> data = super.find(County.class, columName, value);
        List<CountyDA> list = new ArrayList<>();
        data.forEach(da -> list.add(new CountyDA(da)));
        return list;
    }

 
    public int getMax(String columnName) {
        return super.getMax(County.class, columnName);
    }

    public int getMax(String columnName, String comparatorColumn, Object comparatorVaue) {
        return super.getMax(County.class, columnName, comparatorColumn, comparatorVaue);
    }

    public final int getNextIdHelper(LookupData district) {
        return this.getMax("idHelper", "district", district) + 1;
    }

    public String getNextCountyID(int idHelper, String district) {
        return new IDGeneratorDA().getToAppendString(County.class.getSimpleName(), district, idHelper);
    }

    public List<County> getCountys(String columName, Object value) {
        return super.find(County.class, columName, value);
    }

    private boolean isValid() throws Exception {
        List<County> counties = super.find(County.class, "district", this.district, "countyName", county.getCountyName());
        counties.remove(this.county);
        if (!counties.isEmpty()) {
            throw new Exception("The county with name " + county.getCountyName() + " and district " + district.getLookupDataName() + " already exists");
        }
        return true;
    }
    
     public List<County> getCounties(LookupData district) {
        return getCountys("district", district);
    }

}
