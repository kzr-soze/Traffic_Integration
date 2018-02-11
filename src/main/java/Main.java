import com.google.gson.*;

import java.io.IOException;

public class Main {


    /*
    args[0] = site to connect to: "Mapquest
    args[1] = connection key
    Remaining args specified in connector class.
     */

    public static void main(String[] args) {
        try {
            demo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void demo() throws IOException{
        String api = "Mapquest";
        String max_lat = "42.024449";
        String min_lng = "-87.943483";
        String min_lat = "41.643428";
        String max_lng = "-87.531496";
        String filt1 = "incidents";
        String filt2 = "construction";
        String filt3 = "congestion";
        String key = "YOUR_KEY";
        String[] args = {api,key,max_lat,min_lat,max_lng,min_lng,filt1,filt2,filt3};
        API_Connector connect = new MQ_Connector(args);
        Helper.PrettyPrintJSON(connect.getRequest().toString());
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        JsonParser jp = new JsonParser();
//        JsonElement je = jp.parse(connect.getRequest().toString());
//        String prettyJsonString = gson.toJson(je);
//        System.out.println(prettyJsonString);
        try {
            connect.sendRequest();
        } catch (Exception e) {
            System.out.println("Raised exception: "+e.toString());
        }
//        jp = new JsonParser();
//        je = jp.parse(connect.getResponses());
//        prettyJsonString = gson.toJson(je);
//        System.out.println(prettyJsonString);
        Helper.PrettyPrintJSON(connect.getResponses());
    }

}
