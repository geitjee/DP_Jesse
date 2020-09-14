package Domein;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {

    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> ovChipkaarts = new ArrayList<>();

    public Reiziger(int id,String v, String t, String a, Date gb){
        this.id = id;
        this.voorletters = v;
        this.tussenvoegsel = t;
        this.achternaam = a;
        this.geboortedatum = gb;
    }

    public int getId(){ return this.id; }
    public void setId(int id){ this.id = id; }

    public String getVoorletters(){return this.voorletters;}
    public void setVoorletters(String voorletters){ this.voorletters = voorletters;}

    public String getTussenvoegsel(){return this.tussenvoegsel;}
    public void setTussenvoegsel(String tussenvoegsel){ this.tussenvoegsel = tussenvoegsel;}

    public String getAchternaam(){return this.achternaam;}
    public void setAchternaam(String achternaam){this.achternaam = achternaam;}

    public Date getGeboortedatum(){return this.geboortedatum;}
    public void setGeboortedatum(Date geboortedatum){this.geboortedatum = geboortedatum;}

    public Adres getAdres() { return adres; }
    public void setAdres(Adres adres) { this.adres = adres; }

    public String getNaam(){
        if (this.tussenvoegsel == null || this.tussenvoegsel == ""){
            return this.voorletters + " " + this.achternaam;
        }
        else{
            return this.voorletters + " " + this.tussenvoegsel + " " + this.achternaam;
        }
    }

    public List<OVChipkaart> getOvChipkaarts() { return ovChipkaarts; }
    public void setOvChipkaarts(List<OVChipkaart> ovChipkaarts) { this.ovChipkaarts = ovChipkaarts; }

    public String toString() {
        String s = "#" + id + ": " + getNaam() + " (" + geboortedatum + ")";
        if (adres != null) {
            s += adres.toString();
        }
        if (ovChipkaarts.size() >= 1){
            for (OVChipkaart ov : ovChipkaarts){
                s += ov.toString();
            }
        }
        return s;
    }
}
