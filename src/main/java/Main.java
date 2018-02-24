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
        Helper.demo_MSDN();
    }


}
