package contagionJVM.NWNX;

import org.nwnx.nwnx2.jvm.NWObject;

public class ChatMessage {
    private int mode;
    private NWObject recipient;
    private String text;

    public ChatMessage()
    {

    }

    public ChatMessage(int mode, NWObject recipient, String text)
    {
        this.mode = mode;
        this.recipient = recipient;
        this.text = text;
    }

    public int getMode()
    {
        return mode;
    }

    public void setMode(int m)
    {
        mode = m;
    }

    public NWObject getRecipient()
    {
        return recipient;
    }

    public void setRecipient(NWObject obj)
    {
        recipient = obj;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String t)
    {
        text = t;
    }


}
