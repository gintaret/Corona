package com.example.corona;

public class User { //visada is didziosios, nes klase viena
    //1.klases kintamieji(argumentai, pozymiai, arguments)
    private String username;
    private String password;
    private String email;

    //2.konstruktorius
    public User(){
//        bevardis konstruktorius (galima jo ir nekurti - sukuriamas automatiskai)
    }
//    sis konstruktorius skirtas prisijungimo langui
    public User(String username, String password){
        this.username=username;
        this.password=password;
    }
//    sis konstruktorius skirtas registracijos langui
    public User(String username, String password, String email){
        this.username=username;
        this.password=password;
        this.email=email;
    }
    //3.get'eriai, set'eriai
//    get'eris atitinka grazinancia funkcija be parametru
    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
    //    set'eris atitinka negrazinancia funkcija su parametrais
    public void setPassword(String password){
        this.password=password;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email){
        this.email=email;
    }
}
