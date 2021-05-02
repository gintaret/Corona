package com.example.coctails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity { //cia yra globalus kintamieji, kurie aprasomi klases virsuje
    public static final String COCTAILS_API = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s="; //kreipiames i URL, kad duotu si API

    private ArrayList<Coctails> coctailsList = new ArrayList<Coctails>();

    private RecyclerView recyclerView;  //korteliu vaizdas
    private Adapter adapter;    //tarpininkas tarp SearchActivity(kur vartotojas gales kazka ivesti) ir container_coctails.xml (kur piesime korteles), apjungia dvi skirtingas klases adapteris

    private SearchView searchView = null;   //paieskos vaizdas, kuriame piesime

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //bus paleidziama nauja gija (thread) - JSON'o nuskaitymui is API
        AsyncFetch asyncFetch = new AsyncFetch();   //kuriames klase
        asyncFetch.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){// si metoda perraseme, nes norime, kad antraste butu su galimybe atlikti paieska joje, o ne paprasta antraste
        // adds item to action bar
        getMenuInflater().inflate(R.menu.search, menu);
        // Get Search item from action bar and Get Search service
        MenuItem searchItem = menu.findItem(R.id.action_search);    //cia suranda search item'a, i kuri vartotojas gales irasyti kokteilio pavadinima

        //toliau kreipiasi i bibliotekas; du if reikalingi, kad butu sukurta meniu juostele su galimybe irasyti ir veiktu paieskos langelis
        SearchManager searchManager = (SearchManager) SearchActivity.this.getSystemService(Context.SEARCH_SERVICE);
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
            searchView.setIconified(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {   //atsiranda padidinimo stiklas; kartais naudojamas aplikacijose, kur 3 taskai buna desiniame kampe
        return super.onOptionsItemSelected(item);
    }

    // Every time when you press search button on keypad an Activity is recreated which in turn calls this function
    @Override
    protected void onNewIntent(Intent intent) {
        // Get search query
        super.onNewIntent(intent);
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {  //issitraukiame, ka vartotojas ivede
            String query = intent.getStringExtra(SearchManager.QUERY);  //jei vartotojas ives Margarita, tai tas query ir bus Margarita (issitraukiame, ka vartotojas ivede)
            if (searchView != null) {
                searchView.clearFocus();    //isvalo kursoriu, kad nemirksetu
            }
            //is visu kokteiliu saraso sukuriamas sarasas pagal vartotojo ieskoma kokteili (query)
            ArrayList<Coctails> coctailsByQuery = JSON.getCoctailsByQuery(coctailsList, query);    //sis metodas grazina irasus to kokteilio, kuri ivede

            if (coctailsByQuery.size() == 0) {
                Toast.makeText(this, getResources().getString(R.string.search_no_results) + query, Toast.LENGTH_SHORT).show();
            }

            //duomenu perdavimas adapteriui ir recyclerView sukurimas
            recyclerView = (RecyclerView) findViewById(R.id.coctails_list);
            adapter = new Adapter(SearchActivity.this, coctailsByQuery);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));    //korteles issidestys linijiniu budu
        }
    }

    private class AsyncFetch extends AsyncTask<String, String, JSONObject> {    //privati klase, ji yra vidine; lygiagrecios klases gali buti privacios
        ProgressDialog progressDialog = new ProgressDialog(SearchActivity.this);

        @Override
        protected void onPreExecute() { //sis metodas bus vykdomas pries do it background. Paprasysime vartotojo palaukti, kol gausime duomenis is API (bus besisukantis vaizdas)
            super.onPreExecute();
            progressDialog.setMessage(getResources().getString(R.string.search_loading_data));
            progressDialog.setCancelable(false);    //turi islaukti, kol gris kazkokia info
            progressDialog.show();  //kad matytu besisukanti vaizda
        }

        @Override   // Jis skirtas gavimui JSON is API
        protected JSONObject doInBackground(String... strings) {    //jis bus vykdomas tuo metu, kai vartotojas matys besisukanti (progress) dialoga.
            try {   //apdorojamos isimtys
                JSONObject jsonObject = JSON.readJsonFromUrl(COCTAILS_API); //sioje vietoje perduosime URL
                return jsonObject;  //jeigu viskas bus gerai, grazins JSON object
            } catch (IOException e) {   //kad programa neuzluztu apdorojant tas isimtis (input out exception)
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            } catch (JSONException e) { //JSON exception
                Toast.makeText(
                        SearchActivity.this,
                        getResources().getString(R.string.search_error_reading_data) + e.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
            return null;    //grazinsime null, nera JSON, jo negavome
        }   //doInBackground pabaiga

        @Override
        protected void onPostExecute(JSONObject jsonObject) {    //execute atitinka doInBackground'a
            progressDialog.dismiss();   //panaikiname besisukanti dialoga, nes jau gavome background, jau atvaizduosime kazka vartotojui

                JSONArray jsonArray = null;
                try {
                    jsonArray = JSON.getJSONArray(jsonObject);
                    coctailsList = JSON.getList(jsonArray);

                } catch (JSONException e) {
                    Toast.makeText(SearchActivity.this,
                            getResources().getString(R.string.search_error_reading_data) + e.getMessage(), //ismesime pranesima apie error'a, getMessage - pateiksime papildomos info
                            Toast.LENGTH_LONG
                    ).show();
                }

        }   //baigiasi onPostExecute
    }   //baigiasi AsyncFetch klase

    //sioje vietoje galetu buti kazkokie metodai

}   //baigiasi SearchActivity java class