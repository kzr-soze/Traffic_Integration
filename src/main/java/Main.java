import Connectors.API_Connector;
import Connectors.MQ_Connector;

import java.io.FileReader;
import java.io.IOException;
import javax.json.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {


    /*
    args[0] = site to connect to: "Mapquest
    args[1] = connection key
    Remaining args specified in connector class.
     */

    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("C:/Users/tsmurra2/IdeaProjects/Traffic_Integration/resources.txt"));
            JSONObject jsonObject = (JSONObject) obj;
            String key = (String) ((JSONObject) jsonObject.get("Keys")).get("Mapquest");
            demo_MQ(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void demo_MQ(String key) throws IOException{
        String api = "Mapquest";
        String max_lat = "42.024449";
        String min_lng = "-87.943483";
        String min_lat = "41.643428";
        String max_lng = "-87.531496";
        String filt1 = "incidents";
        String filt2 = "construction";
        String filt3 = "congestion";
        String[] args = {api,key,max_lat,min_lat,max_lng,min_lng,filt1,filt2,filt3};
        API_Connector connect = new MQ_Connector(args);
        Helper.PrettyPrintJSON(connect.getRequest().toString());
        try {
            connect.sendRequest();
        } catch (Exception e) {
            System.out.println("Raised exception: "+e.toString());
        }
        Helper.PrettyPrintJSON(connect.getResponses());
    }

}
