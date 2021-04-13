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

    public static ArrayList<Corona> getList(JSONArray jsonArray) throws JSONException{
        ArrayList<Corona> coronaList = new ArrayList<Corona>();
        //isimti data is JSON ir issaugoti corona objektu sarase (coronaList)
        for (int i=0; i<jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Corona corona = new Corona(
//                public Corona(String country, String lastUpdate, String keyID, int confirmed, int deaths)
                    jsonObject.getString("country"),
                    jsonObject.getString("lastUpdate"),
                    jsonObject.getString("keyId"),
                    jsonObject.getInt("confirmed"),
                    jsonObject.getInt("deaths")
            );
            coronaList.add(corona);
        }

        return coronaList;
    }

    public static JSONArray getJSONArray(JSONObject jsonObject) throws JSONException{
        //pasalinama is JSON visa nereikalinga informacija (metaduomenys), paliekant tik covid19Stats masyva
        int jsonLength = jsonObject.toString().length();
        String covid19Stats = "{" + jsonObject.toString().substring(96, jsonLength) + "}"; //substring iskerpa dali simboliu is eilutes, prades nuo 96 iki pacio galo

        // string i JSON object
        JSONObject jsonObject1 = new JSONObject(covid19Stats);

        //JSON Object i JSON Array
        JSONArray jsonArray = jsonObject1.getJSONArray("covid19Stats");

        return jsonArray;
    }

    public static ArrayList<Corona> getCoronaListByCountry(ArrayList<Corona> coronaArrayList, String country){
        ArrayList<Corona> coronaListByCountry = new ArrayList<Corona>();
        for (Corona corona : coronaArrayList) { //kaireje sukuriamas tos klases objektas, per kurios sarasa iteruojame (desineje)
            if(corona.getKeyID().contains(country)){ //contains metodas iesko zodzio dalies
                coronaListByCountry.add(corona);
            }
        }
        return coronaListByCountry;
    }

}
