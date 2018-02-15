package Connectors;

import javax.json.JsonObject;
import java.io.IOException;

public class MSDN_Connector implements API_Connector {
    private String[] args;

    
    public MSDN_Connector(String[] args){
        this.args = args;
    }

    @Override
    public void constructURL() {

    }

    @Override
    public JsonObject getRequest() {
        return null;
    }

    @Override
    public void sendRequest() throws IOException {

    }

    @Override
    public String getResponses() {
        return null;
    }
}
