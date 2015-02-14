package contagionJVM.Entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "pc_search_sites")
public class PCSearchSiteEntity {

    @Id
    @Column(name = "PCSearchSiteID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pcSearchSiteID;

    @Column(name = "PlayerID")
    private String pcID;

    @Column(name = "SearchSiteID")
    private int searchSiteID;

    @Column(name = "UnlockDateTime")
    private Timestamp unlockDateTime;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumns({
        @JoinColumn(name="SearchSiteID"),
        @JoinColumn(name="PlayerID")
    })
    private List<PCSearchSiteItemEntity> searchItems;


    public int getPcSearchSiteID() {
        return pcSearchSiteID;
    }

    public void setPcSearchSiteID(int pcSearchSiteID) {
        this.pcSearchSiteID = pcSearchSiteID;
    }

    public String getPcID() {
        return pcID;
    }

    public void setPcID(String pcID) {
        this.pcID = pcID;
    }

    public int getSearchSiteID() {
        return searchSiteID;
    }

    public void setSearchSiteID(int searchSiteID) {
        this.searchSiteID = searchSiteID;
    }

    public Timestamp getUnlockDateTime() {
        return unlockDateTime;
    }

    public void setUnlockDateTime(Timestamp unlockDateTime) {
        this.unlockDateTime = unlockDateTime;
    }

    public List<PCSearchSiteItemEntity> getSearchItems() {
        return searchItems;
    }

    public void setSearchItems(List<PCSearchSiteItemEntity> searchItems) {
        this.searchItems = searchItems;
    }
}
