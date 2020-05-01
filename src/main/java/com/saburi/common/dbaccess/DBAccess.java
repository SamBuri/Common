/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.dbaccess;

import com.saburi.common.entities.AppRevisionEntity;
import com.saburi.common.entities.DBEntity;
import com.saburi.common.utils.CommonEnums;
import com.saburi.common.utils.FXUIUtils;
import com.saburi.common.utils.HQLBuilder;
import com.saburi.common.utils.SearchColumn;
import com.saburi.common.utils.SearchColumn.SearchDataTypes;
import com.saburi.common.utils.SearchOrder.SearchOrders;
import com.saburi.common.utils.SearchColumn.SearchType;
import com.saburi.common.utils.SearchOrder;
import com.saburi.common.utils.Utilities;
import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Pair;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.hibernate.Session;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.exception.AuditException;

/**
 *
 * @author ClinicMaster13
 */
public class DBAccess {

    protected EntityManager entityManager;
    protected static String persistenceUnit; // = "com.saburi.mysql";
    protected List<SearchColumn> searchColumns = new ArrayList<>();
    protected List<SearchColumn> miniSearchColumns = new ArrayList<>();
    protected List<SearchColumn> revSearchColumns = new ArrayList<>();
    protected static EntityManagerFactory entityManagerFactory = EntityManagerLoader.getInstance(); //Persistence.createEntityManagerFactory(persistenceUnit);;
    protected ValidatorFactory factory = Validation.buildDefaultValidatorFactory();// initialises the instance of bean validation
    protected Validator validator = factory.getValidator();
    protected DBEntity dBEntity;
    protected AppRevisionEntity revisionEntity;
    protected RevisionType oRevisionType;
    protected Object id;
    protected String displayKey;
    protected Pair keyValuePair;
    protected List<SearchColumn> defaultSearchColumns = new ArrayList<>();
    protected SimpleStringProperty userID = new SimpleStringProperty(this, "userID");
    protected SimpleStringProperty userFullName = new SimpleStringProperty(this, "userFullName");
    protected SimpleStringProperty clientMachine = new SimpleStringProperty(this, "clientMachine");
    protected SimpleObjectProperty recordDateTime = new SimpleObjectProperty(this, "recordDateTime");
    protected SimpleStringProperty recordDateTimeDisplay = new SimpleStringProperty(this, "recordDateTimeDisplay");
    protected SimpleObjectProperty lastUpdateDateTime = new SimpleObjectProperty(this, "lastUpdateDateTime");
    protected SimpleStringProperty lastUpdateDateTimeDisplay = new SimpleStringProperty(this, "lastUpdateDateTimeDisplay");

    protected SimpleIntegerProperty revisionNumber = new SimpleIntegerProperty(this, "revisionNumber");
    protected SimpleObjectProperty revisionType = new SimpleObjectProperty(this, "revisionType");
    protected SimpleStringProperty revUserID = new SimpleStringProperty(this, "revUserID");
    protected SimpleStringProperty revClientMachine = new SimpleStringProperty(this, "revClientMachine");
    protected SimpleObjectProperty revDateTime = new SimpleObjectProperty(this, "revDateTime");
    protected SimpleStringProperty revDateTimeDisplay = new SimpleStringProperty(this, "revDateTimeDisplay");

    public DBAccess() {

    }

    public DBAccess(DBEntity entity) {
        this.dBEntity = entity;
    }

    public DBAccess(String persistenceUnit) {
        try {
            DBAccess.persistenceUnit = persistenceUnit;
            DBAccess.entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);

        } catch (Exception e) {
            FXUIUtils.errorMessage("Connection problem", e);
        }

    }

    public void getConnection(String serverName, String port, String databaseName, String username, String password) {
        Map<String, String> properties = new HashMap<>();
        properties.put(port, port);
        properties.put(port, port);
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit, properties);

    }

    public List<SearchColumn> getSearchColumns() {
        return this.searchColumns;
    }

    public List<SearchColumn> getMiniSearchColumns() {
        return this.miniSearchColumns;
    }

    public List<SearchColumn> getRevSearchColumns() {

        this.revSearchColumns.add(new SearchColumn("revisionNumber", "Rev No.", this.revisionNumber.get(), SearchDataTypes.NUMBER, SearchColumn.SearchType.Equal));
        this.revSearchColumns.add(new SearchColumn("revisionType", "Rev Type", this.revisionType.get(), SearchDataTypes.STRING));
        this.revSearchColumns.add(new SearchColumn("revUserID", "User ID", this.revUserID.get(), SearchDataTypes.STRING));
        this.revSearchColumns.add(new SearchColumn("revDateTime", "Rev Date", this.revDateTime.get(), SearchDataTypes.DATE));
        this.revSearchColumns.add(new SearchColumn("revClientMachine", "Client Machine", this.revClientMachine.get(), SearchDataTypes.STRING));
        return this.revSearchColumns;
    }

    public List<SearchColumn> getDefaultSearchColumns() {

        this.defaultSearchColumns.add(new SearchColumn("userID", "User ID", this.userID.get(), SearchDataTypes.STRING, SearchColumn.SearchType.Equal));
        this.defaultSearchColumns.add(new SearchColumn("userFullName", "User Full Name", this.userFullName.get(), SearchDataTypes.STRING));
        this.defaultSearchColumns.add(new SearchColumn("clientMachine", "Client Machine", this.clientMachine.get(), SearchDataTypes.STRING));
        this.defaultSearchColumns.add(new SearchColumn("recordDateTime", "Record Date Time", this.recordDateTime.get(), this.recordDateTimeDisplay.get(), SearchDataTypes.DATE));
        this.defaultSearchColumns.add(new SearchColumn("lastUpdateDateTime", "Last Update Date Time", this.lastUpdateDateTime.get(), this.lastUpdateDateTimeDisplay.get(), SearchDataTypes.DATE));
        return this.defaultSearchColumns;
    }

    protected boolean validateBean(DBEntity entity) throws Exception {
        validator = factory.getValidator();
        Set<ConstraintViolation<DBEntity>> constraintViolations = validator.validate(entity);
        String message = "";
        message = constraintViolations.stream().map((constraintViolation) -> constraintViolation.getMessage()).reduce(message, String::concat);
        if (!message.isBlank()) {
            throw new Exception(message);
        }
        return true;
    }

    protected boolean persist(DBEntity entity) throws Exception, Exception {

        entityManager = entityManagerFactory.createEntityManager();

        boolean saved = false;
        try {
            entityManager.getTransaction().begin();
            if (entityManager.find(entity.getClass(), entity.getId()) != null) {
                throw new Exception("The record with id " + entity.getId() + " already exists in " + entity.getClass().getSimpleName());
            }

            if (validateBean(entity)) {
                entityManager.persist(entity);
                entityManager.getTransaction().commit();
            }
            saved = true;
        } catch (Exception e) {

            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
        return saved;
    }

    protected int persist(List<DBEntity> entities) throws Exception {

        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {

            entities.forEach((entity) -> {
                try {
                    if (validateBean(entity)) {
                        if (entityManager.find(entity.getClass(), entity.getId()) != null) {
                            throw new Exception("The record with id " + entity.getId() + " already exists in " + entity.getClass().getSimpleName());
                        }
                        entityManager.persist(entity);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            entityManager.getTransaction().commit();
            return entities.size();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }

    }

    public int processBatch(Map<? extends DBEntity, CommonEnums.Rights> map) throws Exception {

        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String errorMessage = "";
        try {

            for (Map.Entry<? extends DBEntity, CommonEnums.Rights> entry : map.entrySet()) {
                DBEntity entity = entry.getKey();
                if (entry.getValue().equals(CommonEnums.Rights.Create)) {

                    if (validateBean(entity)) {
                        if (entityManager.find(entity.getClass(), entity.getId()) != null) {
                            errorMessage += "The record with id " + entity.getId() + " already exists in " + entity.getClass().getSimpleName();
                        }
                        entityManager.persist(entity);
                    }

                }
                if (entry.getValue().equals(CommonEnums.Rights.Update)) {

                    if (validateBean(entity)) {
                        entityManager.merge(entity);
                    }

                }
                if (entry.getValue().equals(CommonEnums.Rights.Delete)) {

                    entity = entityManager.find(entity.getClass(), entity.getId());
                    entityManager.remove(entity);

                }
            }

            if (!errorMessage.isBlank()) {
                entityManager.getTransaction().rollback();
                throw new Exception(errorMessage);

            }

            entityManager.getTransaction().commit();
            return map.size();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }

    }

    public int processBatchList(Map<List<? extends DBEntity>, CommonEnums.Rights> map) throws Exception {

        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        String errorMessage = "";
        try {

            for (Map.Entry<List<? extends DBEntity>, CommonEnums.Rights> entry : map.entrySet()) {
                List<? extends DBEntity> entities = entry.getKey();
                if (entry.getValue().equals(CommonEnums.Rights.Create)) {
                    for (DBEntity entity : entities) {

                        if (validateBean(entity)) {
                            if (entityManager.find(entity.getClass(), entity.getId()) != null) {
                                errorMessage += "The record with id " + entity.getId() + " already exists in " + entity.getClass().getSimpleName();
                            }
                            entityManager.persist(entity);
                        }

                    }
                }
                if (entry.getValue().equals(CommonEnums.Rights.Update)) {
                    for (DBEntity entity : entities) {
                        if (validateBean(entity)) {

                            entityManager.merge(entity);
                        }
                    }

                }
                if (entry.getValue().equals(CommonEnums.Rights.Delete)) {
                    for (DBEntity entity : entities) {
                        entity = entityManager.find(entity.getClass(), entity.getId());
                        entityManager.remove(entity);

                    }
                }
            }

            if (!errorMessage.isBlank()) {
                entityManager.getTransaction().rollback();
                throw new Exception(errorMessage);

            }

            entityManager.getTransaction().commit();
            return map.size();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected int persistDBAccess(List<DBAccess> dBAccesses) throws Exception {

        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {

            for (DBAccess dBAccess : dBAccesses) {
                if (entityManager.find(dBAccess.dBEntity.getClass(), dBEntity.getId()) != null) {
                    throw new Exception("The record with id " + dBAccess.dBEntity.getId() + " already exists in " + dBAccess.dBEntity.getClass().getSimpleName());

                }
                entityManager.persist(dBAccess.dBEntity);
            }
            entityManager.getTransaction().commit();
            return dBAccesses.size();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected Boolean merge(DBEntity entity) throws Exception, Exception {
        boolean updated = false;
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            if (validateBean(entity)) {
                entityManager.merge(entity);
                entityManager.getTransaction().commit();
            }

            updated = true;
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
        return updated;

    }

    protected int merge(List<DBEntity> entities) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {

            entities.forEach((entity) -> {
                entityManager.merge(entity);
            });

            entityManager.getTransaction().commit();
            return entities.size();
        } catch (Exception e) {

            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected int mergeDBAccess(List<DBAccess> dBAccesses) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {

            dBAccesses.forEach((dbaccess) -> {
                entityManager.merge(dbaccess.dBEntity);
            });

            entityManager.getTransaction().commit();
            return dBAccesses.size();
        } catch (Exception e) {

            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }

    }

    public boolean remove(DBEntity entity) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {
            entity = entityManager.find(entity.getClass(), entity.getId());
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
            return true;
        } catch (Exception e) {

            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected int remove(List<DBEntity> entities) {
        entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        try {

            entities.forEach((entity) -> {
                entity = entityManager.find(entity.getClass(), entity.getId());
                entityManager.remove(entity);
            });

            entityManager.getTransaction().commit();
            return entities.size();
        } catch (Exception e) {

            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected DBEntity find(Class entity, Object id) {

        try {

            entityManager = entityManagerFactory.createEntityManager();

            return (DBEntity) entityManager.find(entity, id);

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected DBEntity findJoin(Class entity, Object id, String join) {

        try {

            entityManager = entityManagerFactory.createEntityManager();
            EntityGraph entityGraph = entityManager.createEntityGraph(entity);
            entityGraph.addAttributeNodes(join);
            Map<String, Object> graphMap = new HashMap<>();
            graphMap.put("javax.persistence.loadgraph", entityGraph);

            return (DBEntity) entityManager.find(entity, id, graphMap);

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected DBEntity findJoin(Class entity, Object id, List<String> relatedEnties) {

        try {

            entityManager = entityManagerFactory.createEntityManager();
            EntityGraph entityGraph = entityManager.createEntityGraph(entity);
            relatedEnties.forEach(e -> entityGraph.addAttributeNodes(e));
            Map<String, Object> graphMap = new HashMap<>();
            graphMap.put("javax.persistence.loadgraph", entityGraph);

            return (DBEntity) entityManager.find(entity, id, graphMap);

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected boolean builkupdate(Class entity, String column, Object value, Object newValue) {

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaUpdate<DBEntity> criteriaUpdate = builder.createCriteriaUpdate(entity);
            Root<DBEntity> criteria = criteriaUpdate.from(entity);
            criteriaUpdate.set(criteria.get(column), newValue);
            criteriaUpdate.where(builder.and(builder.equal(criteria.get(column), value)));
            entityManager.createQuery(criteriaUpdate).executeUpdate();
            return true;
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected List find(Class className) {
        List entityList = new ArrayList<>();
        entityManager = entityManagerFactory.createEntityManager();
        try {

            CriteriaBuilder criteriaBuilderObj = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> queryObj = criteriaBuilderObj.createQuery();
            Root<DBEntity> from = queryObj.from(className);
            CriteriaQuery<Object> selectQuery = queryObj.select(from);
            selectQuery.orderBy(criteriaBuilderObj.desc(from.get("recordDateTime")));
            TypedQuery<Object> typedQuery = entityManager.createQuery(selectQuery);
            entityList = typedQuery.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

        return entityList;
    }

    protected List findDesc(Class className, String orderColumn) {
        List entityList = new ArrayList<>();
        entityManager = entityManagerFactory.createEntityManager();
        try {

            CriteriaBuilder criteriaBuilderObj = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> queryObj = criteriaBuilderObj.createQuery();
            Root<DBEntity> from = queryObj.from(className);
            CriteriaQuery<Object> selectQuery = queryObj.select(from);
            selectQuery.orderBy(criteriaBuilderObj.desc(from.get(orderColumn)));
            TypedQuery<Object> typedQuery = entityManager.createQuery(selectQuery);
            entityList = typedQuery.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

        return entityList;
    }

    protected List findAsc(Class className, String orderColumn) {
        List entityList = new ArrayList<>();
        entityManager = entityManagerFactory.createEntityManager();
        try {

            CriteriaBuilder criteriaBuilderObj = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> queryObj = criteriaBuilderObj.createQuery();
            Root<DBEntity> from = queryObj.from(className);
            CriteriaQuery<Object> selectQuery = queryObj.select(from);
            selectQuery.orderBy(criteriaBuilderObj.asc(from.get(orderColumn)));
            TypedQuery<Object> typedQuery = entityManager.createQuery(selectQuery);
            entityList = typedQuery.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

        return entityList;
    }

    protected List find(Class entity, String column, Object value) {
        List entityList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DBEntity> criteriaQuery = builder.createQuery(entity);
            Root<DBEntity> criteria = criteriaQuery.from(entity);

            criteriaQuery.where(builder.and(builder.equal(criteria.get(column), value)));
            TypedQuery<DBEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            entityList = typedQuery.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected List find(Class entity, String column, Object value, String column1, Object value1) {
        List entityList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DBEntity> criteriaQuery = builder.createQuery(entity);
            Root<DBEntity> criteria = criteriaQuery.from(entity);

            criteriaQuery.where(builder.and(builder.equal(criteria.get(column), value), builder.equal(criteria.get(column1), value1)));
            TypedQuery<DBEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            entityList = typedQuery.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected List find(Class className, SearchOrder searchOrder) {
        List entityList = new ArrayList<>();
        entityManager = entityManagerFactory.createEntityManager();
        try {

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> queryObj = builder.createQuery();
            Root<DBEntity> criteriaRoot = queryObj.from(className);
            CriteriaQuery<Object> selectQuery = queryObj.select(criteriaRoot);
            SearchOrders searchOrders = searchOrder.getSearchOrder();
            if (searchOrders.equals(SearchOrders.ASC)) {
                builder.asc(criteriaRoot.get(searchOrder.getColumnName()));
            }

            if (searchOrders.equals(SearchOrders.DESC)) {
                builder.desc(criteriaRoot.get(searchOrder.getColumnName()));
            }
            TypedQuery<Object> typedQuery = entityManager.createQuery(selectQuery);
            entityList = typedQuery.getResultList();
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

        return entityList;
    }

    protected List find(Class entity, String column, Object value, SearchOrder searchOrder) {
        List entityList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DBEntity> criteriaQuery = builder.createQuery(entity);
            Root<DBEntity> criteriaRoot = criteriaQuery.from(entity);

            criteriaQuery.where(builder.and(builder.equal(criteriaRoot.get(column), value)));
            SearchOrders searchOrders = searchOrder.getSearchOrder();
            if (searchOrders.equals(SearchOrders.ASC)) {
                builder.asc(criteriaRoot.get(searchOrder.getColumnName()));
            }

            if (searchOrders.equals(SearchOrders.DESC)) {
                builder.desc(criteriaRoot.get(searchOrder.getColumnName()));
            }
            TypedQuery<DBEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            entityList = typedQuery.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected List find(Class entity, String column, Object value, String column1, Object value1, SearchOrder searchOrder) {
        List entityList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DBEntity> criteriaQuery = builder.createQuery(entity);
            Root<DBEntity> criteriaRoot = criteriaQuery.from(entity);

            criteriaQuery.where(builder.and(builder.equal(criteriaRoot.get(column), value), builder.equal(criteriaRoot.get(column1), value1)));
            SearchOrders searchOrders = searchOrder.getSearchOrder();
            if (searchOrders.equals(SearchOrders.ASC)) {
                builder.asc(criteriaRoot.get(searchOrder.getColumnName()));
            }

            if (searchOrders.equals(SearchOrders.DESC)) {
                builder.desc(criteriaRoot.get(searchOrder.getColumnName()));
            }
            TypedQuery<DBEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            entityList = typedQuery.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected List find(Class entity, String column, Object value, String column1, Object value1,
            String column2, Object value2, SearchOrder searchOrder) {
        List entityList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DBEntity> criteriaQuery = builder.createQuery(entity);
            Root<DBEntity> criteriaRoot = criteriaQuery.from(entity);

            criteriaQuery.where(builder.and(builder.equal(criteriaRoot.get(column), value),
                    builder.equal(criteriaRoot.get(column1), value1),
                    builder.equal(criteriaRoot.get(column2), value2)
            ));
            if (searchOrder != null) {
                SearchOrders searchOrders = searchOrder.getSearchOrder();
                if (searchOrders.equals(SearchOrders.ASC)) {
                    builder.asc(criteriaRoot.get(searchOrder.getColumnName()));
                }

                if (searchOrders.equals(SearchOrders.DESC)) {
                    builder.desc(criteriaRoot.get(searchOrder.getColumnName()));
                }
            }
            TypedQuery<DBEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            entityList = typedQuery.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected List findGreater(Class entity, String column, double value) {
        List entityList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DBEntity> criteriaQuery = builder.createQuery(entity);
            Root<DBEntity> criteria = criteriaQuery.from(entity);

            criteriaQuery.where(builder.and(builder.gt(criteria.get(column), value)));
            TypedQuery<DBEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            entityList = typedQuery.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected List find(HQLBuilder builder) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            String queryText = builder.getQueryText();
            SearchColumn defaultSearchColumn = builder.getDefaultSearchColumn();
            Query query = entityManager.createQuery(queryText);
            query.setParameter(defaultSearchColumn.getName(), defaultSearchColumn.getValue());
            builder.getSearchColumns().forEach(sc -> query.setParameter(sc.getName(), sc.getValue()));
            return query.getResultList();

        } catch (Exception e) {
            throw e;
        }
    }

    protected List find(Class entity, List<SearchColumn> SearchColumns) {
        List entityList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DBEntity> criteriaQuery = builder.createQuery(entity);
            Root<DBEntity> criteriaRoot = criteriaQuery.from(entity);
            List<Predicate> conditions = new ArrayList<>();

            searchColumns.forEach((searchColumn) -> {
                String columnName = searchColumn.getName();
                Object value = searchColumn.getValue();
                SearchDataTypes dataType = searchColumn.getDataType();
                SearchType searchType = searchColumn.getDefaultSearchType();
                switch (searchType) {
                    case Equal:
                        conditions.add(builder.equal(criteriaRoot.get(columnName), value));
                        break;
                    case Contains:
                        conditions.add(builder.like(criteriaRoot.get(columnName), "%" + value + "%"));
                        break;
                    case Greater:
                        switch (dataType) {
                            case STRING: {
                                String passedValue = value.toString();
                                conditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case NUMBER: {
                                double passedValue = Double.valueOf(value.toString());
                                conditions.add(builder.gt(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case DATE: {
                                LocalDate passedValue = LocalDate.parse(value.toString());
                                conditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            default: {
                                String passedValue = value.toString();
                                conditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                        }

                        break;

                    case Less:
                        switch (dataType) {
                            case STRING: {
                                String passedValue = value.toString();
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case NUMBER: {
                                double passedValue = Double.valueOf(value.toString());
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case DATE: {
                                LocalDate passedValue = LocalDate.parse(value.toString());
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            default: {
                                String passedValue = value.toString();
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                        }

                        break;

                    default:
                        break;
                }
            });

            criteriaQuery.where(builder.and(conditions.toArray(new Predicate[conditions.size()])));
            TypedQuery<DBEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            entityList = typedQuery.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected List find(Class entity, List<SearchColumn> andSearchColumns, List<SearchColumn> orSearchColumns) {
        List entityList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DBEntity> criteriaQuery = builder.createQuery(entity);
            Root<DBEntity> criteriaRoot = criteriaQuery.from(entity);
            List<Predicate> conditions = new ArrayList<>();
            List<Predicate> orConditions = new ArrayList<>();

            andSearchColumns.forEach((searchColumn) -> {
                String columnName = searchColumn.getName();
                Object value = searchColumn.getValue();
                SearchDataTypes dataType = searchColumn.getDataType();
                SearchType searchType = searchColumn.getDefaultSearchType();
                switch (searchType) {
                    case Equal:
                        conditions.add(builder.equal(criteriaRoot.get(columnName), value));
                        break;
                    case Contains:
                        conditions.add(builder.like(criteriaRoot.get(columnName), "%" + value + "%"));
                        break;
                    case Greater:
                        switch (dataType) {
                            case STRING: {
                                String passedValue = value.toString();
                                conditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case NUMBER: {
                                double passedValue = Double.valueOf(value.toString());
                                conditions.add(builder.gt(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case DATE: {
                                LocalDate passedValue = LocalDate.parse(value.toString());
                                conditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            default: {
                                String passedValue = value.toString();
                                conditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                        }

                        break;

                    case Less:
                        switch (dataType) {
                            case STRING: {
                                String passedValue = value.toString();
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case NUMBER: {
                                double passedValue = Double.valueOf(value.toString());
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case DATE: {
                                LocalDate passedValue = LocalDate.parse(value.toString());
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            default: {
                                String passedValue = value.toString();
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                        }

                        break;

                    default:
                        break;
                }
            });

            orSearchColumns.forEach((searchColumn) -> {
                String columnName = searchColumn.getName();
                Object value = searchColumn.getValue();
                SearchDataTypes dataType = searchColumn.getDataType();
                SearchType searchType = searchColumn.getDefaultSearchType();
                switch (searchType) {
                    case Equal:
                        orConditions.add(builder.equal(criteriaRoot.get(columnName), value));
                        break;
                    case Contains:
                        orConditions.add(builder.like(criteriaRoot.get(columnName), "%" + value + "%"));
                        break;
                    case Greater:
                        switch (dataType) {
                            case STRING: {
                                String passedValue = value.toString();
                                orConditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case NUMBER: {
                                double passedValue = Double.valueOf(value.toString());
                                orConditions.add(builder.gt(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case DATE: {
                                LocalDate passedValue = LocalDate.parse(value.toString());
                                orConditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            default: {
                                String passedValue = value.toString();
                                orConditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                        }

                        break;

                    case Less:
                        switch (dataType) {
                            case STRING: {
                                String passedValue = value.toString();
                                orConditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case NUMBER: {
                                double passedValue = Double.valueOf(value.toString());
                                orConditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case DATE: {
                                LocalDate passedValue = LocalDate.parse(value.toString());
                                orConditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            default: {
                                String passedValue = value.toString();
                                orConditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                        }

                        break;

                    default:
                        break;
                }
            });

            criteriaQuery.where(builder.and(conditions.toArray(new Predicate[conditions.size()])),
                    builder.or(orConditions.toArray(new Predicate[orConditions.size()])));

            TypedQuery<DBEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            entityList = typedQuery.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected List find(Class entity, List<SearchColumn> SearchColumns, SearchOrder searchOrder) {
        List entityList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DBEntity> criteriaQuery = builder.createQuery(entity);
            Root<DBEntity> criteriaRoot = criteriaQuery.from(entity);
            List<Predicate> conditions = new ArrayList<>();

            searchColumns.forEach((searchColumn) -> {
                String columnName = searchColumn.getName();
                Object value = searchColumn.getValue();
                SearchDataTypes dataType = searchColumn.getDataType();
                SearchType searchType = searchColumn.getDefaultSearchType();
                switch (searchType) {
                    case Equal:
                        conditions.add(builder.equal(criteriaRoot.get(columnName), value));
                        break;
                    case Contains:
                        conditions.add(builder.like(criteriaRoot.get(columnName), "%" + value + "%"));
                        break;
                    case Greater:
                        switch (dataType) {
                            case STRING: {
                                String passedValue = value.toString();
                                conditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case NUMBER: {
                                double passedValue = Double.valueOf(value.toString());
                                conditions.add(builder.gt(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case DATE: {
                                LocalDate passedValue = LocalDate.parse(value.toString());
                                conditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            default: {
                                String passedValue = value.toString();
                                conditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                        }

                        break;

                    case Less:
                        switch (dataType) {
                            case STRING: {
                                String passedValue = value.toString();
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case NUMBER: {
                                double passedValue = Double.valueOf(value.toString());
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case DATE: {
                                LocalDate passedValue = LocalDate.parse(value.toString());
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            default: {
                                String passedValue = value.toString();
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                        }

                        break;

                    default:
                        break;
                }
            });

            criteriaQuery.where(builder.and(conditions.toArray(new Predicate[conditions.size()])));
            SearchOrders searchOrders = searchOrder.getSearchOrder();
            if (searchOrders.equals(SearchOrders.ASC)) {
                builder.asc(criteriaRoot.get(searchOrder.getColumnName()));
            }

            if (searchOrders.equals(SearchOrders.DESC)) {
                builder.desc(criteriaRoot.get(searchOrder.getColumnName()));
            }
            TypedQuery<DBEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            entityList = typedQuery.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected List find(Class entity, List<SearchColumn> searchColumns, List<SearchColumn> orSearchColumns, SearchOrder searchOrder) {
        List entityList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DBEntity> criteriaQuery = builder.createQuery(entity);
            Root<DBEntity> criteriaRoot = criteriaQuery.from(entity);
            List<Predicate> conditions = new ArrayList<>();
            List<Predicate> orConditions = new ArrayList<>();

            searchColumns.forEach((searchColumn) -> {
                String columnName = searchColumn.getName();
                Object value = searchColumn.getValue();
                SearchDataTypes dataType = searchColumn.getDataType();
                SearchType searchType = searchColumn.getDefaultSearchType();
                switch (searchType) {
                    case Equal:
                        conditions.add(builder.equal(criteriaRoot.get(columnName), value));
                        break;
                    case Contains:
                        conditions.add(builder.like(criteriaRoot.get(columnName), "%" + value + "%"));
                        break;
                    case Greater:
                        switch (dataType) {
                            case STRING: {
                                String passedValue = value.toString();
                                conditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case NUMBER: {
                                double passedValue = Double.valueOf(value.toString());
                                conditions.add(builder.gt(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case DATE: {
                                LocalDate passedValue = LocalDate.parse(value.toString());
                                conditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            default: {
                                String passedValue = value.toString();
                                conditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                        }

                        break;

                    case Less:
                        switch (dataType) {
                            case STRING: {
                                String passedValue = value.toString();
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case NUMBER: {
                                double passedValue = Double.valueOf(value.toString());
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case DATE: {
                                LocalDate passedValue = LocalDate.parse(value.toString());
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            default: {
                                String passedValue = value.toString();
                                conditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                        }

                        break;

                    default:
                        break;
                }
            });

            orSearchColumns.forEach((searchColumn) -> {
                String columnName = searchColumn.getName();
                Object value = searchColumn.getValue();
                SearchDataTypes dataType = searchColumn.getDataType();
                SearchType searchType = searchColumn.getDefaultSearchType();
                switch (searchType) {
                    case Equal:
                        orConditions.add(builder.equal(criteriaRoot.get(columnName), value));
                        break;
                    case Contains:
                        orConditions.add(builder.like(criteriaRoot.get(columnName), "%" + value + "%"));
                        break;
                    case Greater:
                        switch (dataType) {
                            case STRING: {
                                String passedValue = value.toString();
                                orConditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case NUMBER: {
                                double passedValue = Double.valueOf(value.toString());
                                orConditions.add(builder.gt(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case DATE: {
                                LocalDate passedValue = LocalDate.parse(value.toString());
                                orConditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            default: {
                                String passedValue = value.toString();
                                orConditions.add(builder.greaterThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                        }

                        break;

                    case Less:
                        switch (dataType) {
                            case STRING: {
                                String passedValue = value.toString();
                                orConditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case NUMBER: {
                                double passedValue = Double.valueOf(value.toString());
                                orConditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            case DATE: {
                                LocalDate passedValue = LocalDate.parse(value.toString());
                                orConditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                            default: {
                                String passedValue = value.toString();
                                orConditions.add(builder.lessThan(criteriaRoot.get(columnName), passedValue));
                                break;
                            }
                        }

                        break;

                    default:
                        break;
                }
            });

            criteriaQuery.where(builder.and(conditions.toArray(new Predicate[conditions.size()])),
                    builder.or(orConditions.toArray(new Predicate[conditions.size()])));
            SearchOrders searchOrders = searchOrder.getSearchOrder();
            if (searchOrders.equals(SearchOrders.ASC)) {
                builder.asc(criteriaRoot.get(searchOrder.getColumnName()));
            }

            if (searchOrders.equals(SearchOrders.DESC)) {
                builder.desc(criteriaRoot.get(searchOrder.getColumnName()));
            }
            TypedQuery<DBEntity> typedQuery = entityManager.createQuery(criteriaQuery);
            entityList = typedQuery.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected List listColumn(Class entity, Class type, String attribute, String column) {

        try {

            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = builder.createQuery(type);
            Root criteriaRoot = criteriaQuery.from(entity);
            criteriaQuery.select(criteriaRoot.get(attribute));
            return entityManager.createQuery(criteriaQuery).getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected List listColumn(Class entity, Class type, String attribute, String column, Object value) {

        try {

            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = builder.createQuery(type);
            Root criteriaRoot = criteriaQuery.from(entity);
            criteriaQuery.select(criteriaRoot.get(attribute));
            criteriaQuery.where(builder.equal(criteriaRoot.get(column), value));
            return entityManager.createQuery(criteriaQuery).getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected List listColumn(Class entity, Class type, String attribute, String column, Object value, String column1, Object value1) {

        try {

            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = builder.createQuery(type);
            Root criteriaRoot = criteriaQuery.from(entity);
            criteriaQuery.select(criteriaRoot.get(attribute));
            criteriaQuery.where(builder.and(builder.equal(criteriaRoot.get(column), value), builder.equal(criteriaRoot.get(column1), value1)));

            return entityManager.createQuery(criteriaQuery).getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected List listColumn(Class entity, Class type, String attribute, String column, Object value, String column1, Object value1, String column2, Object value2) {

        try {

            entityManager = entityManagerFactory.createEntityManager();
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery criteriaQuery = builder.createQuery(type);
            Root criteriaRoot = criteriaQuery.from(entity);
            criteriaQuery.select(criteriaRoot.get(attribute));
            criteriaQuery.where(builder.and(builder.equal(criteriaRoot.get(column), value), builder.equal(criteriaRoot.get(column1), value1), builder.equal(criteriaRoot.get(column2), value2)));
            return entityManager.createQuery(criteriaQuery).getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected Stream<?> selectQuery(Class className) {

        try {
            entityManager = entityManagerFactory.createEntityManager();
            String query = "from " + className.getSimpleName() + " order by recordDateTime desc";
            return entityManager.unwrap(Session.class).createQuery(query).stream();
        } catch (Exception e) {
            throw e;
        }
    }

    protected Stream<?> selectQueryOrdered(Class className, String orderColumn) {

        try {
            entityManager = entityManagerFactory.createEntityManager();
            String query = "from " + className.getSimpleName() + " order by " + orderColumn;
            return entityManager.unwrap(Session.class).createQuery(query).stream();
        } catch (Exception e) {
            throw e;
        }
    }

    protected Stream<?> selectQuery(String query) {

        try {
            return entityManager.unwrap(Session.class).createQuery(query).stream();
        } catch (Exception e) {
            throw e;
        }
    }

    protected Stream<?> selectQuery(Class className, String column, Object value) {

        try {
            entityManager = entityManagerFactory.createEntityManager();
            String query = "from " + className.getSimpleName() + " "
                    + "where " + column + "= :" + value;
            return entityManager.unwrap(Session.class).createQuery(query)
                    .setParameter(column, value)
                    .stream();

        } catch (Exception e) {
            throw e;
        }

    }

    protected Stream<?> selectQuery(Class className, String column, Object value, String column1, Object value1) {

        try {
            entityManager = entityManagerFactory.createEntityManager();
            String query = "from " + className.getSimpleName() + " "
                    + "where " + column + "= :" + value + " and " + column1 + "= :" + value1;
            return entityManager.unwrap(Session.class).createQuery(query)
                    .setParameter(column, value)
                    .setParameter(column1, value1)
                    .stream();

        } catch (Exception e) {
            throw e;
        }

    }

    protected Stream<?> selectQuery(Class className, String column, Object value, String column1, Object value1, String column2, String value2) {

        try {
            entityManager = entityManagerFactory.createEntityManager();
            String query = "from " + className.getSimpleName() + " "
                    + "where " + column + "= :" + value + " and " + column1 + "= :" + value1 + "and " + column2 + "= :" + value2;
            return entityManager.unwrap(Session.class).createQuery(query)
                    .setParameter(column, value)
                    .setParameter(column1, value1)
                    .setParameter(column2, value2)
                    .stream();

        } catch (Exception e) {
            throw e;
        }

    }

    protected Stream selectQuery(HQLBuilder builder) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            String queryText = builder.getQueryText();
            SearchColumn defaultSearchColumn = builder.getDefaultSearchColumn();
            org.hibernate.query.Query query = entityManager.unwrap(Session.class).createQuery(queryText);
            query.setParameter(defaultSearchColumn.getName(), defaultSearchColumn.getValue());
            builder.getSearchColumns().forEach(sc -> query.setParameter(sc.getName(), sc.getValue()));
            return query.stream();

        } catch (Exception e) {
            throw e;
        }
    }

    protected List storedPeocedureList(String procedureName) {
        List entityList = new ArrayList();

        try {
            entityManager = entityManagerFactory.createEntityManager();

            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(procedureName);
            entityList = storedProcedure.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected List storedPeocedureList(String procedureName, String columnName, Class type, ParameterMode mode) {
        List entityList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();

            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(procedureName);
            storedProcedure.registerStoredProcedureParameter(columnName, type, mode);
            entityList = storedProcedure.getResultList();

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected boolean saveStoredPeocedure(String procedureName, String columnName, Class type, ParameterMode mode) {

        try {
            entityManager = entityManagerFactory.createEntityManager();

            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(procedureName);
            storedProcedure.registerStoredProcedureParameter(columnName, type, mode);
            storedProcedure.execute();
            return true;
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected List storedPeocedureSave(String procedureName, String columnName, Class type, ParameterMode mode) {
        List entityList = new ArrayList<>();

        try {
            entityManager = entityManagerFactory.createEntityManager();

            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(procedureName);
            storedProcedure.registerStoredProcedureParameter(columnName, type, mode);

        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }
        return entityList;
    }

    protected boolean exists(Class entity, Object id) {
        if (id == null) {
            return false;
        }
        return this.find(entity, id) != null;

    }

    protected boolean exists(DBEntity entity) {
        if (entity == null) {
            return false;
        }
        if (entity.getId() == null) {
            return false;
        }
        return this.find(entity.getClass(), entity.getId()) != null;

    }

    protected int getMax(Class className, String columnName) {
        entityManager = entityManagerFactory.createEntityManager();
        try {

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> criteriaQuery = builder.createQuery(className);
            Root<Object> root = criteriaQuery.from(className);
            criteriaQuery.select(builder.max(root.get(columnName)));
            Object result = entityManager.createQuery(criteriaQuery).getSingleResult();
            if (result == null) {
                return 0;
            } else {
                return (int) result;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected int getMax(Class className, String columnName, String compColumn, Object compValue) {
        entityManager = entityManagerFactory.createEntityManager();
        try {

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> criteriaQuery = builder.createQuery(className);
            Root<Object> root = criteriaQuery.from(className);
            criteriaQuery.select(builder.max(root.get(columnName)));
            criteriaQuery.where(builder.and(builder.equal(root.get(compColumn), compValue)));
            Object result = entityManager.createQuery(criteriaQuery).getSingleResult();
            return result == null ? 0 : (int) result;
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected int count(Class className, String countColumn) {
        entityManager = entityManagerFactory.createEntityManager();
        try {

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            Root root = criteriaQuery.from(className);
            criteriaQuery.select(builder.count(root.get(countColumn)));
            long result = entityManager.createQuery(criteriaQuery).getSingleResult();
            return (int) result;
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected int count(Class className, String countColumn, String whereColumn, Object whereValue) {
        entityManager = entityManagerFactory.createEntityManager();
        try {

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            Root root = criteriaQuery.from(className);
            criteriaQuery.select(builder.count(root.get(countColumn)));
            criteriaQuery.where(builder.equal(root.get(whereColumn), whereValue));
            long result = entityManager.createQuery(criteriaQuery).getSingleResult();
            return (int) result;
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected int count(Class className, String countColumn, String whereColumn, Object whereValue,
            String whereColumn1, Object whereValue1) {
        entityManager = entityManagerFactory.createEntityManager();
        try {

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            Root root = criteriaQuery.from(className);
            criteriaQuery.select(builder.count(root.get(countColumn)));
            criteriaQuery.where(builder.and(builder.equal(root.get(whereColumn), whereValue)),
                    builder.and(builder.equal(root.get(whereColumn1), whereValue1)));
            long result = entityManager.createQuery(criteriaQuery).getSingleResult();
            return (int) result;
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    protected int count(Class className, String countColumn, String whereColumn, Object whereValue,
            String whereColumn1, Object whereValue1, String whereColumn2, Object whereValue2) {
        entityManager = entityManagerFactory.createEntityManager();
        try {

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            Root root = criteriaQuery.from(className);
            criteriaQuery.select(builder.count(root.get(countColumn)));
            criteriaQuery.where(builder.and(builder.equal(root.get(whereColumn), whereValue)),
                    builder.and(builder.equal(root.get(whereColumn1), whereValue1)),
                    builder.and(builder.equal(root.get(whereColumn2), whereValue2)));
            long result = entityManager.createQuery(criteriaQuery).getSingleResult();
            return (int) result;
        } catch (Exception e) {
            throw e;
        } finally {
            entityManager.close();
        }

    }

    public void t(Class entity) {
        double priceFrom = 30d;
        double priceTo = 50d;
        String color = null;
        String name = null;
        String likeName = "e";
        Date addedDate = null;
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<DBEntity> criteriaQuery = builder.createQuery(entity);
        Root<DBEntity> criteriaProducts = criteriaQuery.from(entity);
        List<Predicate> conditions = new ArrayList<>();
        if (priceFrom > 0) {

            conditions.add(builder.ge(criteriaProducts.get(""), priceFrom));
        }
        if (priceTo > 0) {
            conditions.add(builder.le(criteriaProducts.get(""), priceTo));
        }
        if (color != null && !color.equals("")) {
            conditions.add(builder.equal(criteriaProducts.get(""), color));
        }
        if (name != null && !name.equals("")) {
            conditions.add(builder.equal(criteriaProducts.get(""), name));
        } else if (likeName != null && !likeName.equals("")) {
            conditions.add(builder.like(criteriaProducts.get(""), "%" + likeName + "%"));
        }
        if (addedDate != null) {
            conditions.add(builder.equal(criteriaProducts.get(""), addedDate));
            conditions.add(builder.or(builder.equal(criteriaProducts, criteriaProducts)));
        }

        // get results
        criteriaQuery.where(builder.and(conditions.toArray(new Predicate[conditions.size()])));
        TypedQuery<DBEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        List<DBEntity> products = typedQuery.getResultList();
//  assertTrue("Any product found in the database", products != null);
//  assertTrue("The query should find 2 products but "+products.size() + " were found instead ("+products+")", 
//    products.size() == 2);
    }

    public List<DBAccess> get() {
        List<DBAccess> list = new ArrayList<>();
        if (dBEntity == null) {
            return list;
        }
        List<DBEntity> datas = find(this.dBEntity.getClass());
        datas.forEach((data) -> {
            list.add(new DBAccess(data));
        });
        return list;
    }

    public List<DBAccess> getRevisions() {
        List<DBAccess> list = new ArrayList<>();

        return list;
    }

    public List getEntityRevisions(Class entity) {
        try {
            entityManager = entityManagerFactory.createEntityManager();
            AuditReader auditReader = AuditReaderFactory.get(entityManager);
            return auditReader.createQuery().forRevisionsOfEntity(entity, false, true).getResultList();

        } catch (AuditException e) {
            throw e;
        }

    }

    public Object getId() {
        return this.id;
    }

    public String getDisplayKey() {
        return this.displayKey;
    }

    public DBEntity getDBEntity() {
        return this.dBEntity;
    }

    public DBEntity getOptionalDBEntity(DBAccess dbAccess) {
        if (dbAccess == null) {
            return null;
        }
        return dbAccess.getDBEntity();
    }

    public void setdBEntity(DBEntity dBEntity) {
        this.dBEntity = dBEntity;
    }

    protected void initCommonProprties() {

        this.userID.set(this.dBEntity.getUserID());
        this.userFullName.set(this.dBEntity.getUserFullName());
        this.clientMachine = new SimpleStringProperty(this.dBEntity.getClientMachine());
        this.recordDateTime = new SimpleObjectProperty(this.dBEntity.getRecordDateTime());
        this.recordDateTimeDisplay = new SimpleStringProperty(Utilities.formatNullDateTime(this.dBEntity.getRecordDateTime()));
        this.lastUpdateDateTime = new SimpleObjectProperty(this.dBEntity.getLastUpdateDateTime());
        this.lastUpdateDateTimeDisplay.set(Utilities.formatNullDateTime(this.dBEntity.getLastUpdateDateTime()));
        this.displayKey = dBEntity.getDislaykey();
        this.id = dBEntity.getId();
    }

    protected void initRevProprties() {

        this.revisionNumber.set(this.revisionEntity.getId());
        this.revisionType.set(this.oRevisionType);
        this.revUserID.set(this.revisionEntity.getUserID());
        this.revDateTime.set(this.revisionEntity.getRevisionDate());
        this.revDateTimeDisplay.set(Utilities.formatNullDateTime(this.getRevDateTime()));
        this.revClientMachine.set(this.revisionEntity.getClienMachine());

    }

    public String getUserID() {
        return userID.get();
    }

    public String getUserFullName() {
        return userFullName.get();
    }

    public String getClientMachine() {
        return clientMachine.get();
    }

    public Object getRecordDateTime() {
        return recordDateTime.get();
    }

    public String getRecordDateTimeDisplay() {
        return recordDateTimeDisplay.get();
    }

    public Object getLastUpdateDateTime() {
        return lastUpdateDateTime.get();
    }

    public String getLastUpdateDateTimeDisplay() {
        return lastUpdateDateTimeDisplay.get();
    }

    public int getRevisionNumber() {
        return revisionNumber.get();
    }

    public RevisionType getRevisionType() {
        return (RevisionType) this.revisionType.get();
    }

    public String getRevUserID() {
        return revUserID.get();
    }

    public Date getRevDateTime() {
        return (Date) revDateTime.get();
    }

    public String getRevDateTimeDisplay() {
        return revDateTimeDisplay.get();
    }

    public String getRevClientMachine() {
        return revClientMachine.get();
    }

    public Pair<String, Object> keyValuePair() {
        if (this.dBEntity == null) {
            return new Pair("", "");
        } else {
            return new Pair(this.dBEntity.getDisplayKey(), this.dBEntity.getId());
        }
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (!(o instanceof DBAccess)) {
//            return false;
//        }
//        DBAccess dBAccess = (DBAccess) o;
//
//        return this.id.equals(dBAccess.id);
//    }
//
//    @Override
//    public int hashCode() {
//        return id.hashCode();
//    }
}
