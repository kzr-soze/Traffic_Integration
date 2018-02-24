import Connectors.API_Connector;
import Connectors.MQ_Connector;
import Connectors.MSDN_Connector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.json.JsonObject;
import java.io.FileReader;
import java.io.IOException;

public class Helper {

    public static void PrettyPrintJSON(String js){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(js.toString());
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
    }

    public static void demo_MQ(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("C:/Users/tsmurra2/IdeaProjects/Traffic_Integration/resources.txt"));
            JSONObject jsonObject = (JSONObject) obj;
            String key = (String) ((JSONObject) jsonObject.get("Keys")).get("Mapquest");

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void demo_MSDN() {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("C:/Users/tsmurra2/IdeaProjects/Traffic_Integration/resources.txt"));
            JSONObject jsonObject = (JSONObject) obj;
            String key = (String) ((JSONObject) jsonObject.get("Keys")).get("Microsoft");
            String api = "Microsoft";
            String max_lat = "42.024449";
            String min_lng = "-87.943483";
            String min_lat = "41.643428";
            String max_lng = "-87.531496";
            String mapArea = min_lat+","+max_lng+","+max_lat+","+min_lng;
           // String type = "1,3,10";
            String[] args = {"api",api,"key",key,"mapArea",mapArea};
            API_Connector connect = new MSDN_Connector(args);
            Helper.PrettyPrintJSON(connect.getRequest().toString());
            try {
                connect.sendRequest();
            } catch (IOException e) {
                System.out.println("Raised exception: "+e.toString());
            }
            Helper.PrettyPrintJSON(connect.getResponses());

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
