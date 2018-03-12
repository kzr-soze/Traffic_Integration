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

public class MSDN_Connector implements API_Connector {
    private JSONObject request;
    private String[] args;
    private String get_url = "";
    private String responses = "";


    public MSDN_Connector(String[] args){
        this.args = args;
        constructURL();
    }

    /**
     * Constructs the Json of the url, both to record the request and to later construct the request url
     * Accepted parameters:
     *  mapArea (required) : minLat,maxLong,maxLat,minLong
     *  key (required) : Bing maps key
     *  includeLocationCodes (optional)
     *  severity (optional)
     *  type (optional)
     *  See https://msdn.microsoft.com/en-us/library/hh441726 for more information on parameter specifications
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
    }

    /**
     * gets the Json of the request
     * @return request
     */
    public JSONObject getRequest() {
        return request;
    }


    public void sendRequest() throws IOException {
        get_url = "http://dev.virtualearth.net/REST/v1/Traffic/Incidents/"
                +request.get("mapArea").toString().replace("\"","");


        //Because of the small number of optional parameters, checking the existence of each so that they can be specified
        //correctly is easy, rather than working in a loop with several specific if statements.
        if (request.containsKey("includeLocationCodes")) {
            get_url = get_url + "/" + request.get("includeLocationCodes").toString().replace("\"","") + "?";
        } else {
            get_url = get_url + "?";
        }
        if (request.containsKey("severity")){
            get_url = get_url + "severity=" + request.get("severity").toString().replace("\"","") + "&";
        }
        if (request.containsKey("type")){
            get_url = get_url + "type=" + request.get("type").toString().replace("\"","") + "&";
        }
        get_url = get_url + "key=" + request.get("key").toString().replace("\"","");

        //send the connection
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

    @Override
    public String getResponses() {
        return responses;
    }
}
