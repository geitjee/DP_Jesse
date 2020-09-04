package P1;

import java.sql.*;

public class P1 {
    public static void main(String[] args){

        String URL = "jdbc:postgresql://localhost/ovchip?user=postgres&password=jesse123";

        try{
            Connection con = DriverManager.getConnection(URL);
            Statement s = con.createStatement();
            String query = "SELECT * FROM reiziger";

            ResultSet resultSet = s.executeQuery(query);

            String naam;
            int reizigerID;
            Date geboortedatum;
            System.out.println("Alle reizigers:");
            while (resultSet.next()){
                reizigerID = resultSet.getInt("reiziger_id");
                if (resultSet.getString("tussenvoegsel") == null ||resultSet.getString("tussenvoegsel") == ""){
                    naam = resultSet.getString("voorletters") + " " + resultSet.getString("achternaam");
                }else{
                    naam = resultSet.getString("voorletters") + " " + resultSet.getString("tussenvoegsel") + " "+ resultSet.getString("achternaam");
                }
                geboortedatum  = resultSet.getDate("geboortedatum");
                System.out.println("    #" + reizigerID + ": " + naam + " (" + geboortedatum + ")");
            }
            resultSet.close();
            s.close();
            con.close();
        }catch (SQLException e){
            System.err.println("fout kan lijst niet opvragen: " + e.getMessage());
        }
    }
}
