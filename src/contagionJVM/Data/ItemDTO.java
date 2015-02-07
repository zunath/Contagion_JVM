package contagionJVM.Data;

public class ItemDTO {
    private int _version;
    private String _resref;
    private String _tag;
    private String _nameFormat;
    private boolean _replace;

    public ItemDTO(String resref, String tag, int version, String nameFormat, boolean replace)
    {
        _tag = tag;
        _version = version;
        _resref = resref;
        _nameFormat = nameFormat;
        _replace = replace;
    }

    public void SetTag(String tag)
    {
        _tag = tag;
    }

    public String GetTag()
    {
        return _tag;
    }

    public int GetVersion()
    {
        return _version;
    }
    public void SetVersion(int version)
    {
        _version = version;
    }

    public String GetResref()
    {
        return _resref;
    }

    public void SetResref(String resref)
    {
        _resref = resref;
    }

    public String GetNameFormat()
    {
        return _nameFormat;
    }

    public void SetNameFormat(String nameFormat)
    {
        _nameFormat = nameFormat;
    }

    public boolean GetReplace()
    {
        return _replace;
    }

    public void SetReplace(boolean replace)
    {
        _replace = replace;
    }
}
