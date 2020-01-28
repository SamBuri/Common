/*
 Sam Buriima
generated by Saburi Tools
 */
package com.saburi.common.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;
import javax.persistence.OneToOne;

@Entity
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
public class ItemTemplate extends DBEntity {

    @Id
    @NotNull(message = "The field: Template Name cannot be null")
    @Size(max = 40, message = "The field: Template Name size cannot be greater than 40")
    @Column(length = 40, updatable = false)
    private String templateName;
    @OneToOne
    @JoinColumn(name = "itemCategoryID", foreignKey = @ForeignKey(name = "fkItemCategoryIDItemTemplate"))
    private ItemCategory itemCategory;
    @OneToOne
    @JoinColumn(name = "itemGroupID", foreignKey = @ForeignKey(name = "fkItemGroupIDItemTemplate"))
    private LookupData itemGroup;
    @OneToOne
    @JoinColumn(name = "vATItemGroupID", foreignKey = @ForeignKey(name = "fkVATItemGroupIDItemTemplate"))
    private LookupData vATItemGroup;
    @OneToOne
    @JoinColumn(name = "inventoryGroupID", foreignKey = @ForeignKey(name = "fkInventoryGroupIDItemTemplate"))
    private LookupData inventoryGroup;
    @OneToOne
    @JoinColumn(name = "measureGroupID", foreignKey = @ForeignKey(name = "fkMeasureGroupIDItemTemplate"))
    private MeasureGroup measureGroup;

    public ItemTemplate() {
    }

    public ItemTemplate(String templateName, ItemCategory itemCategory, LookupData itemGroup, LookupData vATItemGroup, LookupData inventoryGroup, MeasureGroup measureGroup) {
        this.templateName = templateName;
        this.itemCategory = itemCategory;
        this.itemGroup = itemGroup;
        this.vATItemGroup = vATItemGroup;
        this.inventoryGroup = inventoryGroup;
        this.measureGroup = measureGroup;

    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public ItemCategory getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(ItemCategory itemCategory) {
        this.itemCategory = itemCategory;
    }

    public LookupData getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(LookupData itemGroup) {
        this.itemGroup = itemGroup;
    }

    public LookupData getVATItemGroup() {
        return vATItemGroup;
    }

    public void setVATItemGroup(LookupData vATItemGroup) {
        this.vATItemGroup = vATItemGroup;
    }

    public LookupData getInventoryGroup() {
        return inventoryGroup;
    }

    public void setInventoryGroup(LookupData inventoryGroup) {
        this.inventoryGroup = inventoryGroup;
    }

    public MeasureGroup getMeasureGroup() {
        return measureGroup;
    }

    public void setMeasureGroup(MeasureGroup measureGroup) {
        this.measureGroup = measureGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemTemplate)) {
            return false;
        }

        ItemTemplate itemTemplate = (ItemTemplate) o;

        return this.getId().equals(itemTemplate.getId());
    }

    @Override
    public int hashCode() {
        return this.templateName.hashCode();

    }

    @Override
    public Object getId() {
        return this.templateName;
    }

    @Override
    public String getDisplayKey() {
        return this.templateName;
    }

}
