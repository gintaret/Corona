package com.example.coctails;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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

        CheckBox rememberMe = findViewById(R.id.remember_me);

        User user = new User(LoginActivity.this);   //turi duomenis ("pinigine")

        rememberMe.setChecked(user.isRememberedForLogin()); //patikriname, ar paskutini karta vartotojas buvo pazymejes remember me (kokia paskutini karta buvo suteikta reiksme (true arba false))

        if (rememberMe.isChecked()) {    //true arba false; patikriname is karto uzkrovus langa
            usernameET.setText(user.getUsernameForLogin(), TextView.BufferType.EDITABLE);   //sioje vietoje editText irasysime ta reiksme is SharedPreferences, kuria paskutini karta buvo prisijungta
            //bus galima redaguoti(pasikeisti) prisijungimo varda del EDITABLE
            passwordET.setText(user.getPasswordForLogin(), TextView.BufferType.EDITABLE);
        } else {     //jeigu vartotojas paskutini karta nepazymejo checkbox remember me
            usernameET.setText("", TextView.BufferType.EDITABLE);   //jeigu vartotojas nepazymejo, tai neturime isvesti jam prisijungimo duomenu, vartotojas tures is naujo susivesti prisijungimo duomenis, bet musu "pinigineje" islike tie duomenys, nes gali buti, kad veliau prireiks tu duomenu;  i SharedPreferences galima kreiptis is bet kur
            passwordET.setText("", TextView.BufferType.EDITABLE);
        }

        // Cia bus aprasomas kodas, susijes su mygtuko Login paspaudimu
        kaipNoriuTaipVadinuBT.setOnClickListener(new View.OnClickListener() {   //new - kuriamas naujas objektas
            @Override   //paspaudus mygtuka
            public void onClick(View v) {   // onClick pradzia paspaudus mygtuka

                // cia bus rasomas kodas, kuris bus vykdomas ant paspausto mygtuko
                String usernameStr = usernameET.getText().toString(); //String visuomet is didziosios raides
                String passwordStr = passwordET.getText().toString();

                usernameET.setError(null);    //issivalome klaidu zurnala (username)
                passwordET.setError(null);  //issivalome klaidu zurnala (password)

                if (Validation.isUsernameValid(usernameStr) && Validation.isPasswordValid(passwordStr)) {    //if zodziu prasideda salyga, turi buti visada skliausteliuose; jeigu bus validus duomenys, pereisim is vieno lango i kita
                    User user = new User(LoginActivity.this); //sukonstruotas naujas User klases objektas user

                    //issaugosime SharedPref. duomenis

                    user.setUsernameForLogin(usernameStr);
                    user.setPasswordForLogin(passwordStr);

                    if (rememberMe.isChecked()) { //tikriname, ar buvo pazymetas checkbox, kai vartotojas suvede prisijungimo duomenis
                        user.setRemembermeKeyForLogin(true);    //norime ji issaugoti, kad irasytu i SharedPreferences
                    } else {
                        user.setRemembermeKeyForLogin(false);   //kad kita karta nebutu irasyta
                    }

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