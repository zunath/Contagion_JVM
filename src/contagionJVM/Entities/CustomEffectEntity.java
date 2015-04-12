package contagionJVM.Entities;

import javax.persistence.*;

@Entity
@Table(name = "custom_effects")
public class CustomEffectEntity {

    @Id
    @Column(name = "CustomEffectID")
    private int customEffectID;

    @Column(name = "Name")
    private String name;

    @Column(name = "IconID")
    private int iconID;

    @Column(name = "ScriptHandler")
    private String scriptHandler;

    public int getCustomEffectID() {
        return customEffectID;
    }

    public void setCustomEffectID(int customEffectID) {
        this.customEffectID = customEffectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconID() {
        return iconID;
    }

    public void setIconID(int iconID) {
        this.iconID = iconID;
    }

    public String getScriptHandler() {
        return scriptHandler;
    }

    public void setScriptHandler(String scriptHandler) {
        this.scriptHandler = scriptHandler;
    }
}
