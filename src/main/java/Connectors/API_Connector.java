package Connectors;

import org.json.simple.JSONObject;

import javax.json.JsonObject;
import java.io.IOException;

public interface API_Connector {

    public void constructURL(); //Constructs a JSON of the request

    public JSONObject getRequest(); //Returns a JSON of the request

    public void sendRequest() throws IOException; //Connects to the API and records responses

    public String getResponses(); //Returns responses
}
