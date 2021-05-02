package com.example.coctails;

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
        try {   //i try bloka talpinamas kodas, kuriame gali kilti kazkokia isimtis (klaida), o catch blokas gaudo ta klaida, pagauna ir kazka atlieka, kad neuzluztu programa, atvaizduoja vartotojui, kad kazkas yra blogai
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));    //nuskaityti is kazkokiu srautu (pvz. siuo atveju is url linko nuskaitinejame info, taip pat galima is failu nuskaityti)
            String jsonText = readAll(rd);  //nuskaitomas i stringa
            JSONObject json = new JSONObject(jsonText); //konvertuojamas is stringo i json
            return json;
        } finally { //uzdaro ivedimo srautus
            is.close();
        }
    }
    //toliau bus naudojami 3 metodai

    public static ArrayList<Coctails> getList(JSONArray jsonArray) throws JSONException{    //getList metodas paims JSON objekta ir grazins JSON masyva; konvertuojame JSON i musu sarasa; ArrayList yra Coctails klases objektu sarasas
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

    //JSON Object kovertuosime i JSON Array ir ta JSON masyva duosime auksciau esanciam metodui, kuris is to masyvo issitrauks reikiamus duomenis
    public static JSONArray getJSONArray(JSONObject json) throws JSONException{
        //pasalinami nereikalingi meta duomenys
        JSONArray jsonArray = json.getJSONArray("drinks");

        return jsonArray;
    }
    //sita funkcija paims du parametrus: coctailsArrayList, kuris ateis is getList funkcijos, ir coctailName, kuri ives vartotojas
    public static ArrayList<Coctails> getCoctailsByQuery(ArrayList<Coctails> coctailsArrayList, String coctailName){
        ArrayList<Coctails> coctailsByQuery = new ArrayList<Coctails>();    //susikurime sarasa, i kuri desime Coctails klases objektus
        for (Coctails coctails : coctailsArrayList) { //kaireje sukuriamas tos klases objektas, per kurios sarasa iteruojame (desineje)
            if(coctails.getName().contains(coctailName)){ //contains metodas iesko zodzio dalies arba viso zodzio
                coctailsByQuery.add(coctails);
            }
        }
        return coctailsByQuery;
    }

}
