/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.dbaccess;

import com.saburi.common.entities.AccountCategory;
import javafx.beans.property.*;
import java.util.ArrayList;
import com.saburi.common.entities.AppRevisionEntity;
import java.util.List;
import com.saburi.common.utils.SearchColumn;
import com.saburi.common.utils.SearchColumn.SearchDataTypes;
import org.hibernate.envers.RevisionType;
import com.saburi.common.utils.CommonEnums.AccountTypes;
import com.saburi.common.entities.AccountCategory;
import com.saburi.common.entities.DBEntity;

public class AccountCategoryDA extends DBAccess {

    private AccountCategory accountCategory = new AccountCategory();
    private final SimpleIntegerProperty idHelper = new SimpleIntegerProperty(this, "idHelper");
    private final SimpleObjectProperty accountType = new SimpleObjectProperty(this, "accountType");
    private final SimpleStringProperty categoryID = new SimpleStringProperty(this, "categoryID");
    private final SimpleStringProperty categoryName = new SimpleStringProperty(this, "categoryName");
    private final SimpleStringProperty parentCategoryDisplay = new SimpleStringProperty(this, "parentCategoryDisplay");
    private final SimpleObjectProperty parentCategoryID = new SimpleObjectProperty(this, "parentCategoryID");
    private AccountCategory parentCategory;
    private final SimpleBooleanProperty readOnly = new SimpleBooleanProperty(this, "readOnly");
    private final SimpleBooleanProperty hidden = new SimpleBooleanProperty(this, "hidden");

    public AccountCategoryDA() {
        createSearchColumns();
    }

    public AccountCategoryDA(String persistenceUnit) {
        super(persistenceUnit);
    }

    public AccountCategoryDA(AccountCategory accountCategory) {
        this.accountCategory = accountCategory;
        initialseProprties();
        createSearchColumns();
    }

    public AccountCategoryDA(String persistenceUnit, AccountCategory accountCategory) {
        super(persistenceUnit);
        this.accountCategory = accountCategory;
        initialseProprties();
        createSearchColumns();
    }

    public AccountCategoryDA(AccountTypes accountType, String categoryID, String categoryName, AccountCategory parentCategory, boolean readOnly, boolean hidden) {
        this.accountCategory = new AccountCategory(getNextIdHelper(accountType), accountType, categoryID, categoryName, parentCategory, readOnly, hidden);
        initialseProprties();
        createSearchColumns();
    }

    public AccountCategoryDA(String persistenceUnit, AccountTypes accountType, String categoryID, String categoryName, AccountCategory parentCategory, boolean readOnly, boolean hidden) {
        super(persistenceUnit);
        this.accountCategory = new AccountCategory(getNextIdHelper(accountType), accountType, categoryID, categoryName, parentCategory, readOnly, hidden);
        initialseProprties();
        createSearchColumns();
    }

    public int getIdHelper() {
        return idHelper.get();
    }

    public void setIdHelper(int idHelper) {
        accountCategory.setIdHelper(idHelper);
        this.idHelper.set(idHelper);
    }

    public Object getAccountType() {
        return accountType.get();
    }

    public void setAccountType(AccountTypes accountType) {
        accountCategory.setAccountType(accountType);
        this.accountType.set(accountType);
    }

    public String getCategoryID() {
        return categoryID.get();
    }

    public void setCategoryID(String categoryID) {
        accountCategory.setCategoryID(categoryID);
        this.categoryID.set(categoryID);
    }

    public String getCategoryName() {
        return categoryName.get();
    }

    public void setCategoryName(String categoryName) {
        accountCategory.setCategoryName(categoryName);
        this.categoryName.set(categoryName);
    }

    public AccountCategory getParentCategory() {
        return parentCategory;
    }

    public Object getParentCategoryID() {
        return parentCategoryID.get();
    }

    public String getParentCategoryDisplay() {
        return parentCategoryDisplay.get();
    }

    public AccountCategoryDA getParentCategoryDA() {
        return this.parentCategory != null ? new AccountCategoryDA(this.parentCategory) : null;
    }

    public void setParentCategory(AccountCategory parentCategory) {
        accountCategory.setParentCategory(parentCategory);
        this.parentCategory = parentCategory;
        this.parentCategoryID.set(parentCategory.getId());
        this.parentCategoryDisplay.set(parentCategory.getDisplayKey());
    }

    public boolean isReadOnly() {
        return readOnly.get();
    }

    public void setReadOnly(boolean readOnly) {
        accountCategory.setReadOnly(readOnly);
        this.readOnly.set(readOnly);
    }

    public boolean isHidden() {
        return hidden.get();
    }

    public void setHidden(boolean hidden) {
        accountCategory.setHidden(hidden);
        this.hidden.set(hidden);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountCategoryDA)) {
            return false;
        }

        AccountCategoryDA accountCategoryDA = (AccountCategoryDA) o;

        if (accountCategoryDA.getDBEntity() == null || this.getDBEntity() == null) {
            return false;
        }
        return this.getId().equals(accountCategoryDA.getId());
    }

    @Override
    public int hashCode() {
        return accountCategory.getId().hashCode();
    }

    private void initialseProprties() {
        this.dBEntity = accountCategory;
        this.idHelper.set(accountCategory.getIdHelper());
        this.accountType.set(accountCategory.getAccountType());
        this.categoryID.set(accountCategory.getCategoryID());
        this.categoryName.set(accountCategory.getCategoryName());
        this.parentCategory = accountCategory.getParentCategory();
        if (this.parentCategory != null) {
            this.parentCategoryID.set(parentCategory.getId());
            this.parentCategoryDisplay.set(parentCategory.getDisplayKey());
        }
        this.readOnly.set(accountCategory.isReadOnly());
        this.hidden.set(accountCategory.isHidden());
        initCommonProprties();
    }

    private void createSearchColumns() {
        this.searchColumns.add(new SearchColumn("accountType", "Account Type", this.accountType.get(), SearchDataTypes.STRING, SearchColumn.SearchType.Equal));
        this.searchColumns.add(new SearchColumn("categoryID", "Category ID", this.categoryID.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("categoryName", "Category Name", this.categoryName.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("parentCategoryID", "Parent Category ID", this.parentCategoryID.get(), SearchDataTypes.STRING, SearchColumn.SearchType.Equal, false));
        this.searchColumns.add(new SearchColumn("parentCategoryDisplay", "Parent Category", this.parentCategoryDisplay.get(), SearchDataTypes.STRING));
        this.searchColumns.add(new SearchColumn("readOnly", "Read Only", this.readOnly.get(), SearchDataTypes.BOOLEAN));
        this.searchColumns.add(new SearchColumn("hidden", "Hidden", this.hidden.get(), SearchDataTypes.BOOLEAN));
        this.searchColumns.addAll(this.getDefaultSearchColumns());
    }

    @Override
    public List<SearchColumn> getSearchColumns() {
        return this.searchColumns;
    }

    @Override
    public Object getId() {
        return this.accountCategory.getId();
    }

    @Override
    public String getDisplayKey() {
        return this.accountCategory.getDisplayKey();
    }

    public static List<AccountCategoryDA> getAccountCategoryDAs(List<AccountCategory> accountCategorys) {
        List<AccountCategoryDA> list = new ArrayList<>();
        accountCategorys.forEach((accountCategory) -> {
            list.add(new AccountCategoryDA(accountCategory));
        });
        return list;
    }

    public static List<AccountCategory> getAccountCategoryList(List<AccountCategoryDA> accountCategoryDAs) {
        List<AccountCategory> accountCategorys = new ArrayList<>();
        accountCategoryDAs.forEach(a -> accountCategorys.add(a.accountCategory));
        return accountCategorys;
    }

    public boolean save() throws Exception {
        return super.persist(this.accountCategory);

    }

    public boolean update() throws Exception {
        return super.merge(this.accountCategory);

    }

    public boolean delete() {
        return super.remove(this.accountCategory);

    }

    public AccountCategory getAccountCategory(String categoryID) {
        return (AccountCategory) super.find(AccountCategory.class, categoryID);
    }

    public AccountCategory getAccountCategory() {
        return this.accountCategory;
    }

    public List<AccountCategory> getAccountCategorys() {
        return super.find(AccountCategory.class);
    }

    @Override
    public List<DBAccess> get() {
        List<DBAccess> list = new ArrayList<>();
        selectQuery(AccountCategory.class).forEach(o -> list.add(new AccountCategoryDA((AccountCategory) o)));
        if (entityManager != null) {
            entityManager.close();
        }
        return list;
    }

    public AccountCategoryDA get(String categoryID) throws Exception {
        AccountCategory oAccountCategory = getAccountCategory(categoryID);
        if (oAccountCategory == null) {
            throw new Exception("No Record with id: " + categoryID);
        }
        return new AccountCategoryDA(oAccountCategory);
    }

    public List<AccountCategoryDA> get(String columName, Object value) {
        List<AccountCategoryDA> list = new ArrayList<>();
        super.selectQuery(AccountCategory.class, columName, value).forEach(da -> list.add(new AccountCategoryDA((AccountCategory) da)));
        if (entityManager != null) {
            entityManager.close();
        }
        return list;
    }

    public List<AccountCategoryDA> toDaList(List<AccountCategory> accountCategorys) {
        List<AccountCategoryDA> accountCategoryDAs = new ArrayList<>();
        accountCategorys.forEach(s -> accountCategoryDAs.add(new AccountCategoryDA(s)));
        return accountCategoryDAs;
    }

    public List<DBAccess> toDBAccessList(List<AccountCategory> accountCategorys) {
        List<DBAccess> dbAccesses = new ArrayList<>();
        accountCategorys.forEach(s -> dbAccesses.add(new AccountCategoryDA(s)));
        return dbAccesses;
    }

    public int getMax(String columnName) {
        return super.getMax(AccountCategory.class, columnName);
    }

    public int getMax(String columnName, String comparatorColumn, Object comparatorVaue) {
        return super.getMax(AccountCategory.class, columnName, comparatorColumn, comparatorVaue);
    }

    public final int getNextIdHelper(Object accountType) {
        return this.getMax("idHelper", "accountType", accountType) + 1;
    }

    public String getNextCategoryID(int idHelper, AccountTypes accountType) {
        return new IDGeneratorDA().getToAppendString(AccountCategory.class.getSimpleName(), (accountType.ordinal() + 1), idHelper);
    }

    public List<AccountCategory> getAccountCategorys(String columName, Object value) {
        return super.find(AccountCategory.class, columName, value);
    }

    @Override
    public List<DBAccess> getRevisions() {
        try {

            List<Object[]> list = getEntityRevisions(AccountCategory.class);
            List<DBAccess> dBAccesses = new ArrayList<>();
            list.forEach(e -> {

                AccountCategoryDA accountCategoryDA = new AccountCategoryDA((AccountCategory) e[0]);
                accountCategoryDA.revisionEntity = (AppRevisionEntity) e[1];
                accountCategoryDA.oRevisionType = (RevisionType) e[2];
                accountCategoryDA.initRevProprties();
                accountCategoryDA.searchColumns.addAll(accountCategoryDA.getRevSearchColumns());
                dBAccesses.add(accountCategoryDA);

            });

            return dBAccesses;
        } catch (Exception e) {
            throw e;
        } finally {
            if (entityManager == null) {
                entityManager.close();
            }
        }

    }

    public List<AccountCategory> getAccountCategories(AccountTypes accountType) {
        return getAccountCategorys("accountType", accountType);
    }

}
