package Connectors;

import org.json.simple.JSONObject;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TT_Connector  implements API_Connector{
    public JSONObject request;
    private String[] args;
    private String responses;
    private String get_url = "";

    public TT_Connector(String[] args){
        this.args = args;
        constructURL();
    }

    /**
     * Constructs the Json of the url, both to record the request and to later construct the request url
     * Accepted parameters: (see https://developer.tomtom.com/online-traffic/online-traffic-documentation-online-traffic-incidents/traffic-incident-details
     * for more details)
     *  style (required) : s1,s2,s3,night
     *  boundingBox (required) : minLat,minLong,maxLat,maxLong
     *  zoom (required) : 0,1,...,18
     *  trafficModelID (required) : suggested -1
     *  format (required) : hardcoded to be json for this app
     *  key (required) : TomTom maps key
     *
     */
    public void constructURL() {

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
        if (request.containsKey("format")){
            request.remove("format");
        }
        request.put("format","json");
    }

    public JSONObject getRequest() {
        return request;
    }

    public void sendRequest() throws IOException {
        get_url = "https://api.tomtom.com/traffic/services/4/incidentDetails/";
        get_url = get_url+request.get("style")+"/"+request.get("boundingBox")+"/"+request.get("zoom")+"/"+request.get("trafficModelID")+
                "/"+request.get("format")+"?key="+request.get("key")+"&projection=EPSG4326&expandCluster=";
        if (request.containsKey("expandCluster")){
            get_url = get_url+request.get("expandCluster");
        } else {
            get_url = get_url+"False";
        }

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


    public String getResponses() {
        return responses;
    }
}
