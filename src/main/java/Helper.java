import Connectors.API_Connector;
import Connectors.MQ_Connector;
import Connectors.MSDN_Connector;
import Connectors.TT_Connector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.json.JsonObject;
import java.io.File;
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
            String filename = "resources.txt";
            String workingDirectory = System.getProperty("user.dir");
            String absoluteFilePath = workingDirectory + File.separator + filename;
            Object obj = parser.parse(new FileReader(absoluteFilePath));
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
            String[] args = {"api",api,"key",key,"boundingBox",""+max_lat+","+min_lng+","+min_lat+","+max_lng,"filters",filt1+","+filt2+","+filt3};
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
            String filename = "resources.txt";
            String workingDirectory = System.getProperty("user.dir");
            String absoluteFilePath = workingDirectory + File.separator + filename;
            Object obj = parser.parse(new FileReader(absoluteFilePath));
            JSONObject jsonObject = (JSONObject) obj;
            String key = (String) ((JSONObject) jsonObject.get("Keys")).get("Microsoft");
            String api = "Microsoft";
            String max_lat = "42.024449";
            String min_lng = "-87.943483";
            String min_lat = "41.643428";
            String max_lng = "-87.531496";
            String mapArea = min_lat+","+max_lng+","+max_lat+","+min_lng;
            String type = "1,2,3,5,10";
            String[] args = {"api",api,"key",key,"mapArea",mapArea,"type",type};
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

    public static void demo_TT(){
        JSONParser parser = new JSONParser();
        try {
            String filename = "resources.txt";
            String workingDirectory = System.getProperty("user.dir");
            String absoluteFilePath = workingDirectory + File.separator + filename;
            Object obj = parser.parse(new FileReader(absoluteFilePath));
            JSONObject jsonObject = (JSONObject) obj;
            String key = (String) ((JSONObject) jsonObject.get("Keys")).get("TomTom");

            String api = "TomTom";
            String max_lat = "42.024449";
            String min_lng = "-87.943483";
            String min_lat = "41.643428";
            String max_lng = "-87.531496";
            String zoom = "10";
            String trafficModelID = "-1";
            String expandCluster = "False";
            String style = "s3";
            String[] args = {"api",api,"key",key,"boundingBox",""+min_lat+","+min_lng+","+max_lat+","+max_lng,"style",style,"zoom",zoom,"trafficModelID",trafficModelID};
            API_Connector connect = new TT_Connector(args);
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
}
