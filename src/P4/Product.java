package P4;

import java.util.ArrayList;
import java.util.List;

public class Product {

    private int product_nummer;
    private String naam;
    private String beschrijving;
    private float prijs;
    private List<Integer> kaartnummers = new ArrayList<>();

    public Product (int product_nummer, String naam, String beschrijving, float prijs){
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijs = prijs;
    }

    public void setProduct_nummer(int product_nummer) { this.product_nummer = product_nummer; }
    public int getProduct_nummer() { return product_nummer; }

    public void setNaam(String naam) { this.naam = naam; }
    public String getNaam() { return naam; }

    public void setBeschrijving(String beschrijving) { this.beschrijving = beschrijving;}
    public String getBeschrijving() { return beschrijving; }

    public void setPrijs(float prijs) { this.prijs = prijs; }
    public float getPrijs() { return prijs; }

    public void setOvChipkaartList(List<Integer> kaartnummers) { this.kaartnummers = kaartnummers; }
    public List<Integer> getOvChipkaartList() { return kaartnummers; }
    //misschien een add & delete ovchipkaart functie toevoegen

    @Override
    public String toString() {
        return "Product{" +
                "product_nummer=" + product_nummer +
                ", naam='" + naam + '\'' +
                ", beschrijving='" + beschrijving + '\'' +
                ", prijs=" + prijs +
                '}';
    }
}
