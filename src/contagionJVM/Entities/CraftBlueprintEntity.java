package contagionJVM.Entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "craft_blueprints")
public class CraftBlueprintEntity {

    @Id
    @Column(name = "CraftBlueprintID")
    private int craftBlueprintID;

    @Column(name = "CraftID")
    private int craftID;

    @Column(name = "ItemName")
    private String itemName;

    @Column(name = "ItemResref")
    private String itemResref;

    @Column(name = "Quantity")
    private int quantity;

    @Column(name = "Level")
    private int level;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "blueprint", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<CraftComponentEntity> components;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CraftCategoryID")
    private CraftBlueprintCategoryEntity category;

    public int getCraftBlueprintID() {
        return craftBlueprintID;
    }

    public void setCraftBlueprintID(int craftBlueprintID) {
        this.craftBlueprintID = craftBlueprintID;
    }

    public int getCraftID() {
        return craftID;
    }

    public void setCraftID(int craftID) {
        this.craftID = craftID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemResref() {
        return itemResref;
    }

    public void setItemResref(String itemResref) {
        this.itemResref = itemResref;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<CraftComponentEntity> getComponents() {
        return components;
    }

    public void setComponents(List<CraftComponentEntity> components) {
        this.components = components;
    }

    public CraftBlueprintCategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CraftBlueprintCategoryEntity category) {
        this.category = category;
    }
}
