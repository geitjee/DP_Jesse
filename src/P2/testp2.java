package P2;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class testp2 {
    public static void main(String[] args) {
        try {
            System.out.println("\n---------- Test ReizigerDAO -------------");
            ReizigerDAO rdao = new ReizigerDAOPsql(DriverManager.getConnection("jdbc:postgresql://localhost/ovchip?user=postgres&password=jesse123"));
            // Haal alle reizigers op uit de database
            List<Reiziger> reizigers = rdao.findAll();
            System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
            for (Reiziger r : reizigers) {
                System.out.println(r);
            }
            System.out.println();

            // Maak een nieuwe reiziger aan en persisteer deze in de database
            String gbdatum = "1981-03-14";
            Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
            System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
            rdao.save(sietske);
            reizigers = rdao.findAll();
            System.out.println(reizigers.size() + " reizigers\n");
        }catch (SQLException e){
            System.err.println("fout kan lijst niet opvragen: " + e.getMessage());
        }
    }
}
