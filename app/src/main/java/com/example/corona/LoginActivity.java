package com.example.corona;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity { //klasės pradžia

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Funkcijos pradžia
        super.onCreate(savedInstanceState); //tuscio lango sukurimas
        setContentView(R.layout.activity_login); //suteik tusciam langui si vaizda (kodas susiejamas su vaizdu)

        EditText username = findViewById(R.id.username); //susiejamas elementas vaizde su kintamuoju kode
        EditText password = findViewById(R.id.password);
        Button kaipNoriuTaipVadinu = findViewById(R.id.login);

        // Cia aprasomas kodas, kuris bus susijes su mygtuko paspaudimu

        kaipNoriuTaipVadinu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // pradzia

                // cia bus rasomas kodas, kuris bus vykdomas ant paspausto mygtuko
                String usernameStr = username.getText().toString(); //String visuomet is didziosios raides
                String passwordStr = password.getText().toString();

                Toast.makeText(LoginActivity.this, "Prisijungimo vardas: "+ //tarp skliausteliu yra parametrai ir sujungia eilutes
                                usernameStr + "\n" + "Slaptažodis: " + passwordStr,
                        Toast.LENGTH_LONG).show();

                Intent goToSearchActivity = new Intent(LoginActivity.this, SearchActivity.class); // is kur (pirmas parametras) i kur (antras parametras); i new Intent() labai nesigilinti kol kas, taip tiesiog reikia. This reiskia, jog siame lange esu, gali buti net be zodziu LoginActivity. o class reiskia, jog i kuri eisime langa
                startActivity(goToSearchActivity);


            } // Onclick pabaiga
        }); //mygtuko paspaudimo funkcijos pabaiga


    } //Funkcijos pabaiga

} //klasės pabaiga