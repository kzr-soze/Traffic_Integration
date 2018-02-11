import javax.json.JsonObject;
import java.io.IOException;

public interface API_Connector {

    public void constructURL(); //Constructs a JSON of the request

    public JsonObject getRequest(); //Returns a JSON of the request

    public void sendRequest() throws IOException; //Connects to the API and records responses

    public String getResponses(); //Returns responses
}
