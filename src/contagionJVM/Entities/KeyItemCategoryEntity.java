package contagionJVM.Entities;

import javax.persistence.*;

@Entity
@Table(name="key_item_categories")
@SuppressWarnings("UnusedDeclaration")
public class KeyItemCategoryEntity {

    @Id
    @Column(name="KeyItemCategoryID")
    private int keyItemCategoryID;

    @Column(name="Name")
    private String name;

    public int getKeyItemCategoryID() {
        return keyItemCategoryID;
    }

    public void setKeyItemCategoryID(int keyItemCategoryID) {
        this.keyItemCategoryID = keyItemCategoryID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}