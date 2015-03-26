package contagionJVM.Entities;

import javax.persistence.*;

@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name="pc_key_items")
public class PCKeyItemEntity {

    @Id
    @Column(name="PCKeyItemID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int pcKeyItemID;

    @Column(name="PlayerID")
    private String playerID;

    @Column(name="KeyItemID")
    private int keyitemID;


    public int getPcKeyItemID() {
        return pcKeyItemID;
    }

    public void setPcKeyItemID(int pcKeyItemID) {
        this.pcKeyItemID = pcKeyItemID;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public int getKeyitemID() {
        return keyitemID;
    }

    public void setKeyitemID(int keyitemID) {
        this.keyitemID = keyitemID;
    }
}
