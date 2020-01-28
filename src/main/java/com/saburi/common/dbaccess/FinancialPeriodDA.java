/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.dbaccess;

import com.saburi.common.entities.FinancialPeriod;
import javafx.beans.property.*;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import com.saburi.common.utils.SearchColumn;
import com.saburi.common.utils.SearchColumn.SearchDataTypes;
import com.saburi.common.entities.Company;
import java.time.LocalDate;
import static com.saburi.common.utils.Utilities.formatDate;
import com.saburi.common.utils.CommonEnums.OpenStatus;

public class FinancialPeriodDA extends DBAccess {

    private FinancialPeriod financialPeriod;
    private SimpleIntegerProperty idHelper = new SimpleIntegerProperty(this, "idHelper");
    private SimpleStringProperty periodID = new SimpleStringProperty(this, "periodID");
    private SimpleStringProperty description = new SimpleStringProperty(this, "description");
    private SimpleStringProperty companyDisplay = new SimpleStringProperty(this, "companyDisplay");
    private SimpleObjectProperty companyID = new SimpleObjectProperty(this, "companyID");
    private Company company;
    private SimpleObjectProperty startDate = new SimpleObjectProperty(this, "startDate");
    private SimpleStringProperty startDateDisplay = new SimpleStringProperty(this, "startDateDisplay");
    private SimpleObjectProperty endDate = new SimpleObjectProperty(this, "endDate");
    private SimpleStringProperty endDateDisplay = new SimpleStringProperty(this, "endDateDisplay");
    private SimpleObjectProperty status = new SimpleObjectProperty(this, "status");

    public FinancialPeriodDA() {
        createSearchColumns();
    }

    public FinancialPeriodDA(String persistenceUnit) {
        super(persistenceUnit);
    }

    public FinancialPeriodDA(FinancialPeriod financialPeriod) {
        this.financialPeriod = financialPeriod;
        initialseProprties();
        createSearchColumns();
    }

    public FinancialPeriodDA(String persistenceUnit, FinancialPeriod financialPeriod) {
        super(persistenceUnit);
        this.financialPeriod = financialPeriod;
        initialseProprties();
        createSearchColumns();
    }

    public FinancialPeriodDA(String periodID, String description, Company company, LocalDate startDate, LocalDate endDate, OpenStatus status) {
        this.financialPeriod = new FinancialPeriod(getNextIdHelper(), periodID, description, company, startDate, endDate, status);
        initialseProprties();
        createSearchColumns();
    }

    public FinancialPeriodDA(String persistenceUnit, String periodID, String description, Company company, LocalDate startDate, LocalDate endDate, OpenStatus status) {
        super(persistenceUnit);
        this.financialPeriod = new FinancialPeriod(getNextIdHelper(), periodID, description, company, startDate, endDate, status);
        initialseProprties();
        createSearchColumns();
    }

    public int getIdHelper() {
        return idHelper.get();
    }

    public void setIdHelper(int idHelper) {
        financialPeriod.setIdHelper(idHelper);
        this.idHelper.set(idHelper);
    }

    public String getPeriodID() {
        return periodID.get();
    }

    public void setPeriodID(String periodID) {
        financialPeriod.setPeriodID(periodID);
        this.periodID.set(periodID);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        financialPeriod.setDescription(description);
        this.description.set(description);
    }

    public Company getCompany() {
        return company;
    }

    public Object getCompanyID() {
        return companyID.get();
    }

    public String getCompanyDisplay() {
        return companyDisplay.get();
    }

    public CompanyDA getCompanyDA() {
        if (this.company == null) {
            return new CompanyDA();
        } else {
            return new CompanyDA(this.company);
        }
    }

    public Pair<String, Object> getCompanyPair() {
        if (this.getCompanyDA() == null) {
            return new Pair<>("", "");
        } else {
            return this.getCompanyDA().keyValuePair();
        }
    }

    public void setCompany(Company company) {
        financialPeriod.setCompany(company);
        this.company = company;
    }

    public Object getStartDate() {
        return startDate.get();
    }

    public String getStartDateDisplay() {
        return startDateDisplay.get();
    }

    public void setStartDate(LocalDate startDate) {
        financialPeriod.setStartDate(startDate);
        this.startDate.set(startDate);
    }

    public Object getEndDate() {
        return endDate.get();
    }

    public String getEndDateDisplay() {
        return endDateDisplay.get();
    }

    public void setEndDate(LocalDate endDate) {
        financialPeriod.setEndDate(endDate);
        this.endDate.set(endDate);
    }

    public Object getStatus() {
        return status.get();
    }

    public void setStatus(OpenStatus status) {
        financialPeriod.setStatus(status);
        this.status.set(status);
    }

    public FinancialPeriod getFinancialPeriod() {
        return financialPeriod;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FinancialPeriodDA)) {
            return false;
        }

        FinancialPeriodDA financialPeriodDA = (FinancialPeriodDA) o;

        if (financialPeriodDA.getDBEntity() == null || this.getDBEntity() == null) {
            return false;
        }
        return this.getId().equals(financialPeriodDA.getId());
    }

    @Override
    public int hashCode() {
        return financialPeriod.getId().hashCode();
    }

    private void initialseProprties() {
        this.dBEntity = financialPeriod;
        this.idHelper = new SimpleIntegerProperty(financialPeriod.getIdHelper());
        this.periodID = new SimpleStringProperty(financialPeriod.getPeriodID());
        this.description = new SimpleStringProperty(financialPeriod.getDescription());
        this.company = financialPeriod.getCompany();
        if (this.company == null) {
            this.companyID = new SimpleObjectProperty(company.getId());
            this.companyDisplay = new SimpleStringProperty(company.getDisplayKey());
        }
        this.startDate = new SimpleObjectProperty(financialPeriod.getStartDate());
        this.startDateDisplay = new SimpleStringProperty(formatDate(financialPeriod.getStartDate()));
        this.endDate = new SimpleObjectProperty(financialPeriod.getEndDate());
        this.endDateDisplay = new SimpleStringProperty(formatDate(financialPeriod.getEndDate()));
        this.status = new SimpleObjectProperty(financialPeriod.getStatus());
        initCommonProprties();
    }

    private void createSearchColumns() {
        this.searchColumns.add(new SearchColumn("periodID", "Period ID", this.periodID.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("description", "Discription", this.description.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("companyID", "Company No ID", this.companyID.get(), SearchDataTypes.STRING, SearchColumn.SearchType.Equal, false));
        this.searchColumns.add(new SearchColumn("companyDisplay", "Company No", this.companyDisplay.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("startDate", "Start Date", this.startDate.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("endDate", "End Date", this.endDate.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("status", "Status", this.status.get(), SearchDataTypes.STRING, SearchColumn.SearchType.Equal));
        this.searchColumns.addAll(this.getDefaultSearchColumns());
    }

    @Override
    public List<SearchColumn> getSearchColumns() {
        return this.searchColumns;
    }

    @Override
    public Object getId() {
        return this.financialPeriod.getId();
    }

    @Override
    public String getDisplayKey() {
        return this.financialPeriod.getDisplayKey();
    }

    public static List<FinancialPeriodDA> getFinancialPeriodDAs(List<FinancialPeriod> financialPeriods) {
        List<FinancialPeriodDA> list = new ArrayList<>();
        financialPeriods.forEach((financialPeriod) -> {
            list.add(new FinancialPeriodDA(financialPeriod));
        });
        return list;
    }

    public static List<FinancialPeriod> getFinancialPeriodList(List<FinancialPeriodDA> financialPeriodDAs) {
        List<FinancialPeriod> financialPeriods = new ArrayList<>();
        financialPeriodDAs.forEach(a -> financialPeriods.add(a.financialPeriod));
        return financialPeriods;
    }

    public boolean save() throws Exception {
        return super.persist(this.financialPeriod);

    }

    public boolean update() throws Exception {
        return super.merge(this.financialPeriod);

    }

    public boolean delete() {
        return super.remove(this.financialPeriod);

    }

    public FinancialPeriod getFinancialPeriod(String periodID) {
        return (FinancialPeriod) super.find(FinancialPeriod.class, periodID);
    }

    public List<FinancialPeriod> getFinancialPeriods() {
        return super.find(FinancialPeriod.class);
    }

    @Override
    public List<DBAccess> get() {
        List<DBAccess> list = new ArrayList<>();
        List<FinancialPeriod> datas = super.find(FinancialPeriod.class);
        datas.forEach((data) -> {
            list.add(new FinancialPeriodDA(data));
        });
        return list;
    }

    public FinancialPeriodDA get(String periodID) throws Exception {
        FinancialPeriod oFinancialPeriod = getFinancialPeriod(periodID);
        if (oFinancialPeriod == null) {
            throw new Exception("No Record with id: " + periodID);
        }
        return new FinancialPeriodDA(oFinancialPeriod);
    }

    public List<FinancialPeriodDA> get(String columName, Object value) {
        List<FinancialPeriod> data = super.find(FinancialPeriod.class, columName, value);
        List<FinancialPeriodDA> list = new ArrayList<>();
        data.forEach(da -> list.add(new FinancialPeriodDA(da)));
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

    public List<FinancialPeriodDA> toDaList(List<FinancialPeriod> financialPeriods) {
        List<FinancialPeriodDA> financialPeriodDAs = new ArrayList<>();
        financialPeriods.forEach(s -> financialPeriodDAs.add(new FinancialPeriodDA(s)));
        return financialPeriodDAs;
    }

    public List<DBAccess> toDBAccessList(List<FinancialPeriod> financialPeriods) {
        List<DBAccess> dbAccesses = new ArrayList<>();
        financialPeriods.forEach(s -> dbAccesses.add(new FinancialPeriodDA(s)));
        return dbAccesses;
    }

    public int getMax(String columnName) {
        return super.getMax(FinancialPeriod.class, columnName);
    }

    public int getMax(String columnName, String comparatorColumn, Object comparatorVaue) {
        return super.getMax(FinancialPeriod.class, columnName, comparatorColumn, comparatorVaue);
    }

    public final int getNextIdHelper() {
        return this.getMax("idHelper") + 1;
    }

    public String getNextPeriodID(int idHelper) {
        return new IDGeneratorDA().getToAppendString(FinancialPeriod.class.getSimpleName(), idHelper);
    }

    public List<FinancialPeriod> getFinancialPeriods(String columName, Object value) {
        return super.find(FinancialPeriod.class, columName, value);
    }

}
