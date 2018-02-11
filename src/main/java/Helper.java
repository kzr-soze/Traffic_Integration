import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import javax.json.JsonObject;

public class Helper {

    public static void PrettyPrintJSON(String js){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(js.toString());
        String prettyJsonString = gson.toJson(je);
        System.out.println(prettyJsonString);
    }
}
