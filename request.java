package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class request extends AppCompatActivity {

    // Start API class section

    static class ApiReq extends AsyncTask<String, Void, String>{

        String res = "";

        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                int data = inputStreamReader.read();
                while (data != -1) {
                    char cc = (char) data;
                    res += cc;
                    data = inputStreamReader.read();
                }
                return res;
            } catch (Exception e) {
                e.printStackTrace();
                return "Connection Failed";
            }
        }

    }

    // End API class section

    // Start request method section
    /**
     *** URl Param set the requested link
     *** keys param set the get requested keys
     *** values param set the get requested values
     *** Example:-
     *** url = https://www.google.com/search;keys = q;values = youtube
     *** req(https://www.google.com/search?q=youtube)
     *** use multi keys and values and separate them using , without spacing
     *** Example:- produect=1,produect=2,produect=3,produect=4 etc...
     **/

    static String req(String URL, String keys, String values) {
        ApiReq apiReq = new ApiReq();
        String reqString = "?";
        ArrayList<String> requests = new ArrayList<String>();
        String[] arrayKeys = keys.trim().split(",");
        String[] arrayValues = values.trim().split(",");
        if(arrayKeys.length == arrayValues.length) {

            for(int i = 0;i < arrayKeys.length;i++) {
                if(arrayKeys.length-1 != i) {
                    reqString += arrayKeys[i] + "=" + arrayValues[i] + "&";
                } else {
                    reqString += arrayKeys[i] + "=" + arrayValues[i];
                }
            }

            String res = "";
            try {
                ApiReq req = new ApiReq();
                res = req.execute(URL+reqString).get();
                return res;
            } catch (Exception e) {
                e.printStackTrace();
                return "Connection Error";
            }

        } else {
            return "You must assign value for every key";
        }

    }

    // End request method section

    // Start JSONValue method section

    /**
     *** jsonValue method return json object and control the content by setting the return response text from req method
     *** JSONText param  set string that return from req method
     *** index param set array index to specifiy array
     *** key param set the specific value that you want to return
     *** example:-
     *** String res = req("https://www.google.com/search", "q", "youtube")
     *** String JSONText = JSONValue(res, 0, "")
     **/

    static String JSONValue(String JSONText, int index, String key) {
        try {
            JSONArray jsonArray = new JSONArray(JSONText);
            if(jsonArray.length() > 0) {
                String jsonArrayString = jsonArray.getString(index);
                JSONObject jsonObject = new JSONObject(jsonArrayString);
                if (key.length() != 0) {
                    return jsonObject.getString(key);
                } else {
                    return String.valueOf(jsonObject);
                }
            } else {
                return "Check your json text ( There is no json text )";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return "json failed";
        }
    }

    // End JSONValue method section

}
