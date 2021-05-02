package com.example.coctails;

public class Coctails { //apsirasome klases kintamuosius
    private String id;
    private String name;
    private String category;
    private String alcoholic;
    private String glass;

    //kuriame konstruktoriu
    public Coctails(String id, String name, String category, String alcoholic, String glass) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.alcoholic = alcoholic;
        this.glass = glass;
    }

    //kuriame geterius ir seterius
    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAlcoholic() {
        return alcoholic;
    }

    public void setAlcoholic(String alcoholic) {
        this.alcoholic = alcoholic;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    @Override
    public String toString() {  //metodas toString panasiai kaip system.out.println (tik pastarasis atspausdintu negraziai); toString grazina visa irasa su raktais (pozymiais) ir reiksmemis
        return "Coctails{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", alcoholic='" + alcoholic + '\'' +
                ", glass type=" + glass +
                '}';
        //viena karta nusiskaitome is JSON i sarasus ir visi ieskojimai vyksta is sarasu
    }
}
