/*
 Sam Buriima
generated by Saburi Tools
*/
package com.saburi.common.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;
import javax.persistence.OneToOne;


@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class InventoryPostingGroup extends DBEntity{

@Column(updatable = false)private int idHelper;
@Id
@NotNull(message =  "The field: Inventory Posting Group ID cannot be null")
@Size(max =  100, message =  "The field: Inventory Posting Group ID size cannot be greater than 100")
@Column(length = 100, updatable = false)private String inventoryPostingGroupID;
@NotNull(message =  "The field: Location cannot be null")
@OneToOne
@JoinColumn(name = "locationID",foreignKey = @ForeignKey(name = "fkLocationIDInventoryPostingGroup"))private LookupData location;
@NotNull(message =  "The field: Inventory Group cannot be null")
@OneToOne
@JoinColumn(name = "inventoryGroupID",foreignKey = @ForeignKey(name = "fkInventoryGroupIDInventoryPostingGroup"))private LookupData inventoryGroup;
@OneToOne
@JoinColumn(name = "accountID",foreignKey = @ForeignKey(name = "fkAccountIDInventoryPostingGroup"))private ChartAccount account;

public InventoryPostingGroup(){
}
public  InventoryPostingGroup(int idHelper,String inventoryPostingGroupID,LookupData location,LookupData inventoryGroup,ChartAccount account){
 this.idHelper = idHelper;
this.inventoryPostingGroupID = inventoryPostingGroupID;
this.location = location;
this.inventoryGroup = inventoryGroup;
this.account = account;

}

public int getIdHelper(){
 return idHelper;
}
public void setIdHelper(int idHelper){
this.idHelper = idHelper;
}public String getInventoryPostingGroupID(){
 return inventoryPostingGroupID;
}
public void setInventoryPostingGroupID(String inventoryPostingGroupID){
this.inventoryPostingGroupID = inventoryPostingGroupID;
}public LookupData getLocation(){
 return location;
}
public void setLocation(LookupData location){
this.location = location;
}public LookupData getInventoryGroup(){
 return inventoryGroup;
}
public void setInventoryGroup(LookupData inventoryGroup){
this.inventoryGroup = inventoryGroup;
}public ChartAccount getAccount(){
 return account;
}
public void setAccount(ChartAccount account){
this.account = account;
}
 @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InventoryPostingGroup)) {
            return false;
        }

        InventoryPostingGroup inventoryPostingGroup = (InventoryPostingGroup) o;

        return this.getId().equals(inventoryPostingGroup.getId());
    }
    @Override
    public int hashCode() {
        return this.inventoryPostingGroupID.hashCode();

    } @Override
public Object getId(){
return this.inventoryPostingGroupID;
}
 @Override
public String getDisplayKey(){
return this.location.getDisplayKey()+" "+this.inventoryGroup.getDisplayKey();
}


}
