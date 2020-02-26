/*
 Sam Buriima
generated by Saburi Tools
*/
package com.saburi.common.dbaccess;

import com.saburi.common.entities.RequisitionOne;import javafx.beans.property.*;
import java.util.ArrayList;
import com.saburi.common.entities.AppRevisionEntity;
import java.util.List;
import com.saburi.common.utils.SearchColumn;
import com.saburi.common.utils.SearchColumn.SearchDataTypes;
import org.hibernate.envers.RevisionType;
import java.time.LocalDate;
import static com.saburi.common.utils.Utilities.formatDate;
import com.saburi.common.utils.CommonEnums.OpenStatus;
import com.saburi.common.entities.LookupData;


public class RequisitionOneDA  extends DBAccess{

private RequisitionOne requisitionOne= new RequisitionOne();
private final SimpleIntegerProperty idHelper =  new SimpleIntegerProperty(this,"idHelper");
private final SimpleStringProperty requisitionID =  new SimpleStringProperty(this,"requisitionID");
private final SimpleObjectProperty requistionDate =  new SimpleObjectProperty(this,"requistionDate");
private final SimpleStringProperty requistionDateDisplay =  new SimpleStringProperty(this,"requistionDateDisplay");
private final SimpleObjectProperty status =  new SimpleObjectProperty(this,"status");
private final SimpleStringProperty itemGroupDisplay =  new SimpleStringProperty(this,"itemGroupDisplay");
private final SimpleObjectProperty itemGroupID =  new SimpleObjectProperty(this,"itemGroupID");
private LookupData itemGroup;

public  RequisitionOneDA(){
 createSearchColumns();
}
public  RequisitionOneDA(String persistenceUnit){
 super(persistenceUnit);
}
public  RequisitionOneDA(RequisitionOne requisitionOne){
 this.requisitionOne = requisitionOne;
initialseProprties();
createSearchColumns();
}
public  RequisitionOneDA(String persistenceUnit, RequisitionOne requisitionOne){
 super(persistenceUnit);
this.requisitionOne = requisitionOne;
initialseProprties();
createSearchColumns();
}
public  RequisitionOneDA(String requisitionID, LocalDate requistionDate, OpenStatus status, LookupData itemGroup){
 this.requisitionOne = new RequisitionOne(getNextIdHelper(), requisitionID, requistionDate, status, itemGroup);initialseProprties();
createSearchColumns();
}
public  RequisitionOneDA(String persistenceUnit, String requisitionID, LocalDate requistionDate, OpenStatus status, LookupData itemGroup){
 super(persistenceUnit);
this.requisitionOne = new RequisitionOne(getNextIdHelper(), requisitionID, requistionDate, status, itemGroup);initialseProprties();
createSearchColumns();
}

public int getIdHelper(){
 return idHelper.get();
}
public void setIdHelper(int idHelper){
 requisitionOne.setIdHelper(idHelper);
this.idHelper.set(idHelper);
}
public String getRequisitionID(){
 return requisitionID.get();
}
public void setRequisitionID(String requisitionID){
 requisitionOne.setRequisitionID(requisitionID);
this.requisitionID.set(requisitionID);
}
public Object getRequistionDate(){
 return requistionDate.get();
}
public String getRequistionDateDisplay(){
 return requistionDateDisplay.get();
}
public void setRequistionDate(LocalDate requistionDate){
 requisitionOne.setRequistionDate(requistionDate);
this.requistionDate.set(requistionDate);
}
public Object getStatus(){
 return status.get();
}
public void setStatus(OpenStatus status){
 requisitionOne.setStatus(status);
this.status.set(status);
}
public LookupData getItemGroup(){
 return itemGroup;
}
public Object getItemGroupID(){
 return itemGroupID.get();
}
public String getItemGroupDisplay(){
 return itemGroupDisplay.get();
}
public LookupDataDA getItemGroupDA(){
 return this.itemGroup!=null? new LookupDataDA(this.itemGroup):null;
}
public void setItemGroup(LookupData itemGroup){
 requisitionOne.setItemGroup(itemGroup);
this.itemGroup = itemGroup;this.itemGroupID.set(itemGroup.getId());
        this.itemGroupDisplay.set(itemGroup.getDisplayKey());
}

         @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RequisitionOneDA)) {
            return false;
        }
        
        RequisitionOneDA requisitionOneDA = (RequisitionOneDA) o;
        
        if(requisitionOneDA.getDBEntity()== null||this.getDBEntity() == null){
            return false;
        }
        return this.getId().equals(requisitionOneDA.getId());    } @Override
            public int hashCode
            
                () {
        return requisitionOne.getId().hashCode();
            }private void initialseProprties(){
 this.dBEntity = requisitionOne;
this.idHelper.set(requisitionOne. getIdHelper());
this.requisitionID.set(requisitionOne. getRequisitionID());
this.requistionDate.set(requisitionOne. getRequistionDate());
this.requistionDateDisplay.set(formatDate(requisitionOne. getRequistionDate()));
this.status.set(requisitionOne. getStatus());
this.itemGroup= requisitionOne. getItemGroup();
if(this.itemGroup!= null){this.itemGroupID.set(itemGroup.getId());
this.itemGroupDisplay.set(itemGroup.getDisplayKey());
}
initCommonProprties();
}
private void createSearchColumns(){
 this.searchColumns.add(new SearchColumn("requisitionID", "Requistion ID", this.requisitionID.get(), SearchDataTypes.STRING));
this.searchColumns.add(new SearchColumn("requistionDate", "Requisition Date", this.requistionDate.get(), SearchDataTypes.STRING));
this.searchColumns.add(new SearchColumn("status", "Status", this.status.get(), SearchDataTypes.STRING, SearchColumn.SearchType.Equal));
this.searchColumns.add(new SearchColumn("itemGroupID", "Item Group ID", this.itemGroupID.get(), SearchDataTypes.STRING, SearchColumn.SearchType.Equal,false));
this.searchColumns.add(new SearchColumn("itemGroupDisplay", "Item Group", this.itemGroupDisplay.get(), SearchDataTypes.STRING));
this.searchColumns.addAll(this.getDefaultSearchColumns());
}
 @Override
    public List<SearchColumn> getSearchColumns() {
       return this.searchColumns;
    }
 @Override
    public Object getId() {
        return this.requisitionOne.getId();
    }@Override
    public String getDisplayKey() {
        return this.requisitionOne.getDisplayKey();
    } public static List<RequisitionOneDA> getRequisitionOneDAs(List<RequisitionOne> requisitionOnes) {
        List<RequisitionOneDA> list = new ArrayList<>();
        requisitionOnes.forEach((requisitionOne) -> {
            list.add(new RequisitionOneDA(requisitionOne));
        });
        return list;
    } public static List<RequisitionOne> getRequisitionOneList(List<RequisitionOneDA> requisitionOneDAs){
       List<RequisitionOne> requisitionOnes = new ArrayList<>();
       requisitionOneDAs.forEach(a->requisitionOnes.add(a.requisitionOne));
       return requisitionOnes;
    }public boolean save() throws Exception{
 return super.persist(this.requisitionOne);

}
 public boolean update() throws Exception{
return super.merge(this.requisitionOne);

}
 public boolean delete() {
return super.remove(this.requisitionOne);

}
public RequisitionOne getRequisitionOne(String requisitionID){
 return (RequisitionOne) super.find(RequisitionOne.class, requisitionID);
}
public RequisitionOne getRequisitionOne(){
        return this.requisitionOne;
    }public List<RequisitionOne> getRequisitionOnes() {
return super.find(RequisitionOne.class);
} @Override
    public List<DBAccess> get() {
        List<DBAccess> list = new ArrayList<>();
        selectQuery(RequisitionOne.class).forEach(o -> list.add(new RequisitionOneDA((RequisitionOne) o)));
        if (entityManager != null) {
            entityManager.close();
        }
        return list;
    }public RequisitionOneDA get(String requisitionID) throws Exception {
       RequisitionOne oRequisitionOne = getRequisitionOne(requisitionID);
       if(oRequisitionOne == null) throw new Exception("No Record with id: "+requisitionID);
        return new RequisitionOneDA(oRequisitionOne);
    }
 public List<RequisitionOneDA> get(String columName, Object value) {
        List<RequisitionOneDA> list = new ArrayList<>();
        super.selectQuery(RequisitionOne.class, columName, value).forEach(da -> list.add(new RequisitionOneDA((RequisitionOne) da)));
        if (entityManager != null) {
            entityManager.close();
        }
        return list;
    }public List<RequisitionOneDA> toDaList(List<RequisitionOne> requisitionOnes) {
        List<RequisitionOneDA> requisitionOneDAs = new ArrayList<>();
        requisitionOnes.forEach(s -> requisitionOneDAs.add(new RequisitionOneDA(s)));
        return requisitionOneDAs;
    }public List<DBAccess> toDBAccessList(List<RequisitionOne> requisitionOnes) {
        List<DBAccess> dbAccesses = new ArrayList<>();
        requisitionOnes.forEach(s -> dbAccesses.add(new RequisitionOneDA(s)));
        return dbAccesses;
    } public int getMax(String columnName) {
return super.getMax(RequisitionOne.class, columnName);
}
public int getMax(String columnName, String comparatorColumn, Object comparatorVaue) {
        return super.getMax(RequisitionOne.class, columnName, comparatorColumn, comparatorVaue);
    }
public final int getNextIdHelper() {
        return this.getMax("idHelper") + 1;
    }
public String getNextRequisitionID(int idHelper ) {
        return new IDGeneratorDA().getToAppendString(RequisitionOne.class.getSimpleName(), idHelper);
    }
public List<RequisitionOne> getRequisitionOnes(String columName, Object value) {
        return  super.find(RequisitionOne.class, columName, value);}
 @Override
    public List<DBAccess> getRevisions() {
        try {

            List<Object[]> list = getEntityRevisions(RequisitionOne.class);
            List<DBAccess> dBAccesses = new ArrayList<>();
            list.forEach(e -> {

                RequisitionOneDA requisitionOneDA = new RequisitionOneDA((RequisitionOne) e[0]);
                requisitionOneDA.revisionEntity = (AppRevisionEntity) e[1];
                requisitionOneDA.oRevisionType = (RevisionType) e[2];
                requisitionOneDA.initRevProprties();
                requisitionOneDA.searchColumns.addAll(requisitionOneDA.getRevSearchColumns());
                dBAccesses.add(requisitionOneDA);

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


}
