package contagionJVM.Entities;

import javax.persistence.*;
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
    private float locationX;
    @Column(name="LocationY")
    private float locationY;
    @Column(name="LocationZ")
    private float locationZ;
    @Column(name="LocationOrientation")
    private float locationOrientation;
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
    @Column(name="CurrentHungerTick")
    private int currentHungerTick;
    @Column(name = "UnallocatedSP")
    private int unallocatedSP;
    @Column(name = "Level")
    private int level;
    @Column(name = "Experience")
    private int experience;
    @Column(name ="NextSPResetDate")
    private Date nextSPResetDate;
    @Column(name="NumberOfSPResets")
    private int numberOfSPResets;
    @Column(name = "ResetTokens")
    private int resetTokens;
    @Column(name = "NextResetTokenReceiveDate")
    private Date nextResetTokenReceiveDate;
    @Column(name="HPRegenerationAmount")
    private int hpRegenerationAmount;
    @Column(name="InventorySpaceBonus")
    private int inventorySpaceBonus;
    @Column(name="RegenerationTick")
    private int regenerationTick;
    @Column(name="RegenerationRate")
    private int regenerationRate;
    @Column(name="ZombieKillCount")
    private int zombieKillCount;
    @Column(name = "VersionNumber")
    private int versionNumber;


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

    public float getLocationX() {
        return locationX;
    }

    public void setLocationX(float _locationX) {
        this.locationX = _locationX;
    }

    public float getLocationY() {
        return locationY;
    }

    public void setLocationY(float _locationY) {
        this.locationY = _locationY;
    }

    public float getLocationZ() {
        return locationZ;
    }

    public void setLocationZ(float _locationZ) {
        this.locationZ = _locationZ;
    }

    public float getLocationOrientation() {
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
        if(currentHunger < 0) currentHunger = 0;

        this.currentHunger = currentHunger;
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

    public int getUnallocatedSP() {
        return unallocatedSP;
    }

    public void setUnallocatedSP(int unallocatedSP) {
        this.unallocatedSP = unallocatedSP;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Date getNextSPResetDate() {
        return nextSPResetDate;
    }

    public void setNextSPResetDate(Date nextSPResetDate) {
        this.nextSPResetDate = nextSPResetDate;
    }

    public int getNumberOfSPResets() {
        return numberOfSPResets;
    }

    public void setNumberOfSPResets(int numberOfSPResets) {
        this.numberOfSPResets = numberOfSPResets;
    }

    public int getHpRegenerationAmount() {
        return hpRegenerationAmount;
    }

    public void setHpRegenerationAmount(int hpRegenerationAmount) {
        this.hpRegenerationAmount = hpRegenerationAmount;
    }

    public int getInventorySpaceBonus() {
        return inventorySpaceBonus;
    }

    public void setInventorySpaceBonus(int inventorySpaceBonus) {
        this.inventorySpaceBonus = inventorySpaceBonus;
    }

    public int getRegenerationTick() {
        return regenerationTick;
    }

    public void setRegenerationTick(int regenerationTick) {
        this.regenerationTick = regenerationTick;
    }

    public int getCurrentHungerTick() {
        return currentHungerTick;
    }

    public void setCurrentHungerTick(int currentHungerTick) {
        this.currentHungerTick = currentHungerTick;
    }

    public int getRegenerationRate() {
        return regenerationRate;
    }

    public void setRegenerationRate(int regenerationRate) {
        this.regenerationRate = regenerationRate;
    }

    public int getZombieKillCount() {
        return zombieKillCount;
    }

    public void setZombieKillCount(int zombieKillCount) {
        this.zombieKillCount = zombieKillCount;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getResetTokens() {
        return resetTokens;
    }

    public void setResetTokens(int resetTokens) {
        this.resetTokens = resetTokens;
    }

    public Date getNextResetTokenReceiveDate() {
        return nextResetTokenReceiveDate;
    }

    public void setNextResetTokenReceiveDate(Date nextResetTokenReceiveDate) {
        this.nextResetTokenReceiveDate = nextResetTokenReceiveDate;
    }
}
