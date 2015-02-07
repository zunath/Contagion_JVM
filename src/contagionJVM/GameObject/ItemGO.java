package contagionJVM.GameObject;

public class ItemGO {
    private String tag;
    private String resref;
    private String script;

    public ItemGO(String tag, String resref, String script) {
        this.setTag(tag);
        this.setResref(resref);
        this.setScript(script);
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getResref() {
        return resref;
    }

    public void setResref(String resref) {
        this.resref = resref;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }
}
