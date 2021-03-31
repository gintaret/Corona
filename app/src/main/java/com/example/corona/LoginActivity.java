package com.example.corona;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {  //klasės pradžia

    @Override
    protected void onCreate(Bundle savedInstanceState) { //Funkcijos pradžia
        super.onCreate(savedInstanceState); //tuscio lango sukurimas
        setContentView(R.layout.activity_login); //suteik tusciam langui si vaizda (kodas susiejamas su vaizdu, vaizda is xml uzdedame ant tuscio lango), jei nebutu sitos eilutes, nereiketu ir pries tai esancios

        EditText usernameET = findViewById(R.id.username); //susiejamas elementas vaizde su kintamuoju kode; EditText yra tipas, o username - kintamojo pavadinimas; pagal id is to elemento issiimsim tai, ka vartotojas suvede; kabliataskis zymi sakinio pabaiga
        EditText passwordET = findViewById(R.id.password);
        Button kaipNoriuTaipVadinuBT = findViewById(R.id.login);
        Button RegisterBT = findViewById(R.id.register);

//        System.out.println("I KITM istojo Jonas, kuris uzpilde tokius duomenis i sistema:");
////        sukuriamas Jono objektas
//        User user=new User("Jonas", "Jonukas");
//        System.out.println(user.getUsername()+" suvede si slaptazodi i sistema:"+user.getPassword());
//        System.out.println("bjaurybe "+user.getUsername()+" pasikeite slaptazodi is "+user.getPassword()+" i ");
//        user.setPassword("Jonaitis");
//        System.out.println("nuo siol "+user.getUsername() +" slaptazodis yra "+user.getPassword());
        // Cia bus aprasomas kodas, susijes su mygtuko Login paspaudimu

        kaipNoriuTaipVadinuBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {   // onClick pradzia

                // cia bus rasomas kodas, kuris bus vykdomas ant paspausto mygtuko
                String usernameStr = usernameET.getText().toString(); //String visuomet is didziosios raides
                String passwordStr = passwordET.getText().toString();

                usernameET.setError(null);    //issivalome klaidu zurnala (username)
                passwordET.setError(null);  //issivalome klaidu zurnala (password)
                if (Validation.isUsernameValid(usernameStr) && Validation.isPasswordValid(passwordStr)) {    //if zodziu prasideda salyga, turi buti visada skliausteliuose; jeigu bus validus duomenys, pereisim is vieno lango i kita
                    User user = new User(usernameStr, passwordStr); //sukonstruotas naujas objektas
//                    System.out.println("prisijungimo vardas: " + user.getUsername() + "\n" + "slaptazodis: " + user.getPassword());
//                    Toast.makeText(LoginActivity.this, "prisijungimo vardas: " + user.getUsername() + "\n" + "slaptazodis: " + user.getPassword(),Toast.LENGTH_LONG).show();
                    Intent goToSearchActivity = new Intent(LoginActivity.this, SearchActivity.class);   // is kur (pirmas parametras) i kur (antras parametras); i new Intent() labai nesigilinti kol kas, taip tiesiog reikia. This reiskia, jog siame lange esu, gali buti net be zodziu LoginActivity. o class reiskia, jog i kuri eisime langa
                    startActivity(goToSearchActivity);
                } else {  //be skliausteliu () reiskia "visais kitais atvejais", nes mes tu visu atveju negalime nurodyti skliausteliuose; ir cia salyga, todel skliausteliu nereikia
                    usernameET.setError(getResources().getString(R.string.login_invalid_credentials));
                    usernameET.requestFocus();
                }

            }   // onClick pabaiga
        }); //mygtuko paspaudimo funkcijos pabaiga - visada bus sie trys simboliai su mygtuko paspaudimo funkcijos pabaiga

        // Cia bus aprasomas kodas, susijes su mygtuko Register paspaudimu

        RegisterBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToRegisterActivity = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goToRegisterActivity);
            }
        });

    }   //Funkcijos pabaiga

}   //klasės pabaiga