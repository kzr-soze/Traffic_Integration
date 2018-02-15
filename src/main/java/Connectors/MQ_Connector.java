package Connectors;

import javax.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MQ_Connector implements API_Connector{
    public JsonObject request;
    private int numFilters;
    private String[] args;
    String responses;
    String get_url = "";

    /*
    args should be of the form ["Mapquest",key,max_lat,min_lat,max_lng,min_lng,filter1,filter2,...]
     */


    public MQ_Connector(String[] args){
        this.args = args;
        numFilters = args.length - 6;
        this.constructURL();

    }

    /*
    Creates a JSON of the url for readability
     */
    public void constructURL(){
        JsonObject ul = Json.createObjectBuilder()
                .add("lat",Double.parseDouble(args[2]))
                .add("lng",Double.parseDouble(args[5]))
                .build();
        JsonObject lr = Json.createObjectBuilder()
                .add("lat",Double.parseDouble(args[3]))
                .add("lng",Double.parseDouble(args[4]))
                .build();
        JsonArrayBuilder builder = Json.createArrayBuilder();
        for(int i = 0; i < numFilters; i++){
            builder.add(args[6+i]);
        }
        JsonArray filters = builder.build();
        JsonObject boundingBox = Json.createObjectBuilder()
                .add("ul",ul)
                .add("lr",lr)
                .build();
        request = Json.createObjectBuilder()
                .add("boundingBox",boundingBox)
                .add("filters", filters)
                .build();
    }

    public JsonObject getRequest(){
        return request;
    }

    private String getBoundingBox(){
        String box = args[2]+","+args[5]+","+args[3]+","+args[4];
        return box;
    }

    private String getFilters(){
        String fil = args[6];
        for(int i = 0; i < numFilters-1; i++){
            fil = ","+args[7+i];
        }
        return fil;
    }

    public void sendRequest() throws IOException{
        get_url = "http://www.mapquestapi.com/traffic/v2/incidents?boundingBox="+getBoundingBox()+"&filters="+getFilters()+"&key="+args[1];
        URL obj = new URL(get_url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        System.out.println("Constructed connection: "+con.toString());
        int responseCode = con.getResponseCode();
        String responseMessage = con.getResponseMessage();
        System.out.println("GET Response Code :: " + responseCode);
        System.out.println("GET Response Message :: " + responseMessage);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
//            System.out.println(response.toString());
            responses = response.toString();
        } else {
            System.out.println("GET request not worked");
        }
    }

    public String getResponses(){
        return responses;
    }

}
