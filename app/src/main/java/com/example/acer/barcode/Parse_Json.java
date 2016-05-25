package com.example.acer.barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ACER on 2015-12-23.
 */
public class Parse_Json {

    public static String[] nim;
    public static String[] nama;
    public static String[] siswa;

    public static final String JSON_ARRAY   = "data_siswa";
    public static final String KEY_ID       = "nim";
    public static final String KEY_TITLE    = "nama";
    public static final String KEY_SISWA    = "nama_siswa";


    private JSONArray users = null;

    private String json;

    public Parse_Json(String json){
        this.json   = json;
    }

    protected void parseJSON(){

        JSONObject jsonObject = null;

        try {
            jsonObject  = new JSONObject(json);


            users       = jsonObject.getJSONArray(JSON_ARRAY);

            nim         = new String[users.length()];
            nama      = new String[users.length()];
            siswa = new String[users.length()];

            for (int i=0; i < users.length(); i++){
                JSONObject jo   = users.getJSONObject(i);
                nim[i]          = jo.getString(KEY_ID);
                nama[i]       = jo.getString(KEY_TITLE);
                siswa[i]       = jo.getString(KEY_SISWA);
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
