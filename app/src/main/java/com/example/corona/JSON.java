package com.example.corona;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSON {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));    //nuskaityti is kazkokiu srautu
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally { //uzdaro ivedimo srautus
            is.close();
        }
    }

    public static ArrayList<Coctails> getList(JSONArray jsonArray) throws JSONException{
        ArrayList<Coctails> coctailsList = new ArrayList<Coctails>();
        //isimti data is JSON ir issaugoti coctails objektu sarase (coctailsList)
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            Coctails coctails = new Coctails(
                    jsonObject.getString("idDrink"),
                    jsonObject.getString("strDrink"),
                    jsonObject.getString("strCategory"),
                    jsonObject.getString("strAlcoholic"),
                    jsonObject.getString("strGlass")
            );
            coctailsList.add(coctails);
        }
        return coctailsList;
    }

    public static JSONArray getJSONArray(JSONObject json) throws JSONException{
        //JSON Object i JSON Array
        JSONArray jsonArray = json.getJSONArray("drinks");

        return jsonArray;
    }

    public static ArrayList<Coctails> getCoctailsByQuery(ArrayList<Coctails> coctailsArrayList, String coctailName){
        ArrayList<Coctails> coctailsByQuery = new ArrayList<Coctails>();
        for (Coctails coctails : coctailsArrayList) { //kaireje sukuriamas tos klases objektas, per kurios sarasa iteruojame (desineje)
            if(coctails.getName().contains(coctailName)){ //contains metodas iesko zodzio dalies arba viso zodzio
                coctailsByQuery.add(coctails);
            }
        }
        return coctailsByQuery;
    }

}
