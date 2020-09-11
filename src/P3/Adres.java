package P3;

import P2.Reiziger;

public class Adres {
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private Reiziger reiziger;

    public Adres(int id, String postcode, String huisnummer, String straat, String woonplaats, Reiziger reiziger){
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger = reiziger;
    }

    public String getPostcode() { return postcode; }
    public void setPostcode(String postcode) { this.postcode = postcode; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getHuisnummer() { return huisnummer; }
    public void setHuisnummer(String huisnummer) { this.huisnummer = huisnummer; }

    public String getStraat() { return straat; }
    public void setStraat(String straat) { this.straat = straat; }

    public String getWoonplaats() { return woonplaats; }
    public void setWoonplaats(String woonplaats) { this.woonplaats = woonplaats; }

    public Reiziger getReiziger_id() { return reiziger; }
    public void setReiziger_id(Reiziger reiziger_id) { this.reiziger = reiziger_id; }

    @Override
    public String toString() {
        return "Adres{" +
                "#" + id +
                " " + postcode + " " + woonplaats + " " + straat + " " + huisnummer + "}";
    }
}
