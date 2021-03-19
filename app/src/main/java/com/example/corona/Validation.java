package com.example.corona;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static final String USERNAME_REGEX_PATTERN = "^[a-zA-Z0-9]{3,20}$";  //per static prieina; public turi buti, o ne private; ir final turi buti - final galutinis, nebekeicia; capslock su underscore turi buti; tai globalus kintamasis, nes yra klaseje, o ne funkcijoje. jis bus visur matomas}
    public static final String PASSWORD_REGEX_PATTERN = "^[a-zA-Z0-9.!@_]{5,20}$";
    public static final String EMAIL_REGEX_PATTERN = "^[a-zA-Z0-9@._-]{10,50}$";

    public static boolean isUsernameValid(String username){
        Pattern pattern = Pattern.compile(USERNAME_REGEX_PATTERN);  //sukuriamos username validacijai taisyklės pagal nurodyta sablona
        Matcher matcher = pattern.matcher(username);    //pagal anksciau sukurtas taisykles palyginami vartotojo ivesti duomenis (username); matcher - atitikimas
        boolean isUsernameValid = matcher.find(); //jeigu atitinka, grazins true, jeigu ne - false
        return isUsernameValid;
    }//arba true (1), arba false (0); paima parametrus, kur skliausteliuose. Tai antraste.

    public static boolean isPasswordValid(String password){
        Pattern pattern = Pattern.compile(PASSWORD_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(password);
        boolean isPasswordValid = matcher.find();
        return isPasswordValid;
    }

    public static boolean isEmailValid(String email){
        Pattern pattern = Pattern.compile(EMAIL_REGEX_PATTERN);
        Matcher matcher = pattern.matcher(email);
        boolean isEmailValid = matcher.find();
        return isEmailValid;
    }
}