package contagionJVM.Entities;

import javax.persistence.*;

@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name="player_progression_skills")
public class PlayerProgressionSkillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int playerProgressionSkillID;

    @Column(name="PlayerID")
    private String pcID;

    @Column(name="ProgressionSkillID")
    private int progressionSkillID;

    @Column(name = "UpgradeLevel")
    private int upgradeLevel;

    public int getPlayerProgressionSkillID() {
        return playerProgressionSkillID;
    }

    public void setPlayerProgressionSkillID(int playerProgressionSkillID) {
        this.playerProgressionSkillID = playerProgressionSkillID;
    }

    public String getPcID() {
        return pcID;
    }

    public void setPcID(String pcID) {
        this.pcID = pcID;
    }

    public int getProgressionSkillID() {
        return progressionSkillID;
    }

    public void setProgressionSkillID(int progressionSkillID) {
        this.progressionSkillID = progressionSkillID;
    }

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }
}
