package contagionJVM.Entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@SuppressWarnings("UnusedDeclaration")
@Entity
@Table(name="playercharacters")
public class PlayerEntity {

    @Id
    @Column(name="PlayerID")
    private String pcID;
    @Column(name="CharacterName")
    private String characterName;
    @Column(name="HitPoints")
    private int hitPoints;
    @Column(name="LocationAreaTag")
    private String locationAreaTag;
    @Column(name="LocationX")
    private double locationX;
    @Column(name="LocationY")
    private double locationY;
    @Column(name="LocationZ")
    private double locationZ;
    @Column(name="LocationOrientation")
    private double locationOrientation;
    @Column(name="CreateTimestamp")
    private Date createTimestamp;
    @Column(name="InfectionCap")
    private int infectionCap;
    @Column(name="CurrentInfection")
    private int currentInfection;
    @Column(name="InfectionRemovalTick")
    private int infectionRemovalTick;
    @Column(name="MaxHunger")
    private int maxHunger;
    @Column(name="CurrentHunger")
    private int currentHunger;
    @Column(name="MaxThirst")
    private int maxThirst;
    @Column(name="CurrentThirst")
    private int currentThirst;


    public PlayerEntity()
    {

    }

    public String getPCID()
    {
        return pcID;
    }

    public void setPCID(String pcID)
    {
        this.pcID = pcID;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String _characterName) {
        this.characterName = _characterName;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public void setHitPoints(int _hitPoints) {
        this.hitPoints = _hitPoints;
    }

    public String getLocationAreaTag() {
        return locationAreaTag;
    }

    public void setLocationAreaTag(String _locationAreaTag) {
        this.locationAreaTag = _locationAreaTag;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(float _locationX) {
        this.locationX = _locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(float _locationY) {
        this.locationY = _locationY;
    }

    public double getLocationZ() {
        return locationZ;
    }

    public void setLocationZ(float _locationZ) {
        this.locationZ = _locationZ;
    }

    public double getLocationOrientation() {
        return locationOrientation;
    }

    public void setLocationOrientation(float _locationOrientation) {
        this.locationOrientation = _locationOrientation;
    }

    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    public void setCreateTimestamp(Date _createTimestamp) {
        this.createTimestamp = _createTimestamp;
    }

    public int getInfectionCap(){
        return infectionCap;
    }

    public void setInfectionCap(int value)
    {
        infectionCap = value;
    }

    public int getMaxHunger() {
        return maxHunger;
    }

    public void setMaxHunger(int maxHunger) {
        this.maxHunger = maxHunger;
    }

    public int getCurrentHunger() {
        return currentHunger;
    }

    public void setCurrentHunger(int currentHunger) {
        this.currentHunger = currentHunger;
    }

    public int getMaxThirst() {
        return maxThirst;
    }

    public void setMaxThirst(int maxThirst) {
        this.maxThirst = maxThirst;
    }

    public int getCurrentThirst() {
        return currentThirst;
    }

    public void setCurrentThirst(int currentThirst) {
        this.currentThirst = currentThirst;
    }

    public int getInfectionRemovalTick() {
        return infectionRemovalTick;
    }

    public void setInfectionRemovalTick(int infectionRemovalTick) {
        this.infectionRemovalTick = infectionRemovalTick;
    }

    public int getCurrentInfection() {
        return currentInfection;
    }

    public void setCurrentInfection(int currentInfection) {
        if(currentInfection < 0) currentInfection = 0;
        else if(currentInfection > infectionCap) currentInfection = infectionCap;

        this.currentInfection = currentInfection;
    }
}
