package com.example.corona;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

public class SearchActivity extends AppCompatActivity {
    public static final String COVID_API = "https://covid19-api.weedmark.systems/api/v1/stats";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //bus paleidziama nauja gija (thread) - JSON'o nuskaitymui is API
        AsyncFetch asyncFetch = new AsyncFetch();   //kuriames klase
        asyncFetch.execute();
    }

    private class AsyncFetch extends AsyncTask<String, String, JSONObject> {    //privati klase, ji yra vidine; lygiagrecios klases gali buti privacios
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);

        @Override
        protected void onPreExecute() { //sis metodas bus vykdomas pries do it background. Paprasysime vartotojo palaukti, kol gausime duomenis is API (bus besisukantis vaizdas)
            super.onPreExecute();
            progressDialog.setMessage(getResources().getString(R.string.search_loading_data));
            progressDialog.setCancelable(false);    //turi islaukti, kol gris kazkokia info
            progressDialog.show();  //kad matytu vaizda
        }

        @Override   // Jis skirtas gavimui JSON is API
        protected JSONObject doInBackground(String... strings) {    //jis bus vykdomas tuo metu, kai vartotojas matys besisukanti (progress) dialoga.
            try {
                JSONObject jsonObject = JSON.readJsonFromUrl(COVID_API); //sioje vietoje perduosime URL
                return jsonObject;
            } catch (IOException e) {
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            } catch (JSONException e) {
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
            return null;
        }   //doInBackground pabaiga

        @Override
        protected void onPostExecute(JSONObject jsonObject) {    //execute atitinka doInBackground'a
            progressDialog.dismiss();

            int statusCode = 0;
            try {
                statusCode = jsonObject.getInt("statusCode");
            } catch (JSONException e) {
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
            if(statusCode == HttpURLConnection.HTTP_OK) {
                System.err.println(jsonObject.toString());   //spausdina terminala, pasitikrinimui; bandys spausdinti kaip klaida, kita spalva
                Toast.makeText(
                        SearchActivity.this,
                       jsonObject.toString(),
                        Toast.LENGTH_LONG
                ).show();
            } else {   // kazkas nepavyko (serveris negrazino 200 code)
              String message = null;
                try {
                    message = jsonObject.getString("message");
                } catch (JSONException e) {
                    Toast.makeText( //cia apdorojame JSON exceptiona
                            SearchActivity.this,
                            getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
                Toast.makeText( //sioje vietoje jau perduodame zinute
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + message,
                        Toast.LENGTH_LONG
                ).show();
            }   //baigiasi else
        }   //baigiasi onPostExecute
    }   //baigiasi AsyncFetch klase

    //sioje vietoje gales buti kazkokie metodai

}   //baigiasi SearchActivity java class