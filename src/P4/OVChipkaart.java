package P4;

import Domein.Reiziger;
import P4.Product;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaart {

    private int kaart_nummer;
    private Date geldig_tot;
    private int klasse;
    private float saldo;
    private Reiziger reiziger;
    private List<Product> productList = new ArrayList<>();

    public OVChipkaart(int kaart_nummer, Date geldig_tot, int klasse, float saldo, Reiziger reiziger){
        this.kaart_nummer = kaart_nummer;
        this.geldig_tot = geldig_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
    }

    public int getKaart_nummer() { return kaart_nummer; }
    public void setKaart_nummer(int kaart_nummer) { this.kaart_nummer = kaart_nummer; }

    public Date getGeldig_tot() { return geldig_tot; }
    public void setGeldig_tot(Date geldig_tot) { this.geldig_tot = geldig_tot; }

    public int getKlasse() { return klasse; }
    public void setKlasse(int klasse) { this.klasse = klasse; }

    public float getSaldo() { return saldo; }
    public void setSaldo(float saldo) { this.saldo = saldo; }

    public Reiziger getReiziger() { return reiziger; }
    public void setReiziger(Reiziger reiziger) { this.reiziger = reiziger; }

    public List<Product> getProduct() { return productList; }
    public void setProduct(List<Product> product) { this.productList = product; }

    @Override
    public String toString() {
        return "OVChipkaart{" +
                "kaart_nummer=" + kaart_nummer +
                ", geldig_tot=" + geldig_tot +
                ", klasse=" + klasse +
                ", saldo=" + saldo +
                '}';
    }
}


