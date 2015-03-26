package contagionJVM.Entities;

import javax.persistence.*;

@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name="key_items")
public class KeyItemEntity {

    @Id
    @Column(name="KeyItemID")
    private int keyItemID;

    @Column(name="KeyItemCategory")
    private int keyItemCategory;

    @Column(name="Name")
    private String Name;

    @Column(name="Description")
    private String Description;


    public int getKeyItemID() {
        return keyItemID;
    }

    public void setKeyItemID(int keyItemID) {
        this.keyItemID = keyItemID;
    }

    public int getKeyItemCategory() {
        return keyItemCategory;
    }

    public void setKeyItemCategory(int keyItemCategory) {
        this.keyItemCategory = keyItemCategory;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
