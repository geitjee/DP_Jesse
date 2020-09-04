package P3;

import P2.Reiziger;
import P2.ReizigerDAO;
import P2.ReizigerDAOPsql;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class testAdresDAO {
    public static void main(String[] args) {
        try {
            AdresDAOPsql rdao = new AdresDAOPsql(DriverManager.getConnection("jdbc:postgresql://localhost/ovchip?user=postgres&password=jesse123"));
            String gbdatum = "1999-01-01";
            Reiziger r = new Reiziger(90, "J", null, "Kaas", java.sql.Date.valueOf(gbdatum));
            Adres a = new Adres(50, "H1", "14B", "weg", "utrecht", 90);
            r.setAdres(a);
            new ReizigerDAOPsql(DriverManager.getConnection("jdbc:postgresql://localhost/ovchip?user=postgres&password=jesse123")).save(r);
            System.out.println(a);

            List<Adres> adres = rdao.findAll();
            System.out.println("de findAll() functie geeft het volgende resultaat");
            for (Adres ad : adres){
                System.out.println(ad.toString());
            }

            //toevoegen
            System.out.print("[Test] Eerst " + adres.size() + " adressen, na save() ");
            rdao.save(a);
            adres = rdao.findAll();
            System.out.println(adres.size() + " reizigers\n");

            //aanpassen
            System.out.println("[Test] vooraf de gebruikers gegevens: ");
            System.out.println(rdao.findById(50).toString());
            a.setStraat("langeweg");
            rdao.update(a);
            System.out.println("Achteraf:");
            System.out.println(rdao.findById(50).toString());

            //vind via reizigerID
            System.out.println("[Test] adres vinden via reizigerID:");
            System.out.println(rdao.findByReiziger(90).toString());

            //vinden via adresID
            System.out.println("[Test] adres vinden via adres id");
            System.out.println(rdao.findById(50));

            //verwijderen
            System.out.print("[Test] Eerst " + adres.size() + " adressen, na delete() ");
            rdao.delete(a);
            adres = rdao.findAll();
            System.out.println(adres.size() + " reizigers\n");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
