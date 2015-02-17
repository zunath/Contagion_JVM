package contagionJVM.Dialog;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedDeclaration")
public class DialogPage {

    private String header;
    private List<DialogResponse> responses;

    public DialogPage()
    {
        responses = new ArrayList<>();
    }

    public DialogPage(String header, String... responseOptions)
    {
        responses = new ArrayList<>();
        this.header = header;

        for(String response : responseOptions)
        {
            this.responses.add(new DialogResponse(response));
        }
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<DialogResponse> getResponses() {
        return responses;
    }

    public void setResponses(List<DialogResponse> responses) {
        this.responses = responses;
    }

    public int getNumberOfResponses()
    {
        return responses.size();
    }

}
