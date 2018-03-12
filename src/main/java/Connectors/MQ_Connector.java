package Connectors;

import org.json.simple.JSONObject;

import javax.json.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MQ_Connector implements API_Connector{
    public JSONObject request;
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
        request = new JSONObject();
        int index = 0;
        int argLength = args.length;
        boolean go = true;
        while (go) {
            String parameter = args[index];
            request.put(parameter,args[index+1]);
            index = index+2;
            if (index >= argLength){
                go = false;
            }
        }
    }

    public JSONObject getRequest(){
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
        get_url = "http://www.mapquestapi.com/traffic/v2/incidents?boundingBox="+request.get("boundingBox")+"&filters="+request.get("filters")+"&key="+request.get("key");
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
