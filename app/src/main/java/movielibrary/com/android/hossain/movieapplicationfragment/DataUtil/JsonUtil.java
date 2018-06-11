package movielibrary.com.android.hossain.movieapplicationfragment.DataUtil;

import android.support.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {

    public static JSONObject parseToJson(String data){
        JSONObject json;
        try {
            json = new JSONObject(data);
        } catch (JSONException e) {
            json = null;
        }
        return json;
    }

    public static JSONArray parseToJsonArray(String data){
        JSONArray json;
        try {
            json = new JSONArray(data);
        } catch (JSONException e) {
            json = null;
        }
        return json;
    }

    public static JSONArray convertReportToArray(@NonNull String data){
        JSONObject responseJson = parseToJson(data);
        try {
            if(responseJson.has("results")
                    && responseJson.getJSONArray("results").length()>0){
                return responseJson.getJSONArray("results");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }



    public static JSONArray getArray(JSONObject object, String key){
        try{
            return object.getJSONArray(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getInt(JSONObject json, String key){
        try {
            if(json.has(key)
                    && json.getInt(key)> 0)
                return json.getInt(key);
        } catch (Exception e){}
        return 0;
    }


    public static String getString(JSONObject json, String key){
        try{
            if(json.has(key)
                    && json.getString(key) != null)
                return json.getString(key);
        }catch (Exception e){}
        return "";
    }

    public static Double getDouble(JSONObject json, String key){
        try{
            if(json.has(key)
                    && json.getDouble(key) >= 0)
                return json.getDouble(key);
        }catch (Exception e){}
        return 0.0;
    }

    public static boolean getBoolean(JSONObject json, String key){
        try{
            if(json.has(key))
                return json.getBoolean(key);
        }catch (Exception e){}
        return false;
    }

    public static Integer[] getGenryIds(JSONObject jsonObject, String key){
        Integer[] genres = new Integer[0];
        try {
            if(jsonObject.has(key)
                    && jsonObject.getJSONArray(key).length() >0){
                JSONArray genryArrey =  jsonObject.getJSONArray(key);
                int length = genryArrey.length();
                genres = new Integer[length];
                for (int i =0; i<length; i++){
                    genres[i] = (int) genryArrey.get(i);
                }
            }else {
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return genres;
    }

    public static JSONArray ObjectToArrey(JSONObject... jsonObjects){
        JSONArray jsonArray = new JSONArray();
        for (JSONObject json: jsonObjects){
            jsonArray.put(json);
        }
        return jsonArray;
    }

    public static JSONArray ObjectToArrey(JSONArray array, JSONObject... jsonObjects){
        for(JSONObject object: jsonObjects){
            array.put(object);
        }
        return array;
    }
}
