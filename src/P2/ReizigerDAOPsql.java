package P2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {

    private Connection conn;

    public ReizigerDAOPsql(Connection conn) throws SQLException {
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            int id = reiziger.getId();
            String voorletters = reiziger.getVoorletters();
            String tusssenvoegsel = reiziger.getTussenvoegsel();
            String achternaam = reiziger.getAchternaam();
            Date geboortedatum = reiziger.getGeboortedatum();

            Statement s = conn.createStatement();
            String query = "INSERT INTO reiziger VALUES(" + id + ", '" + voorletters + "', '" + tusssenvoegsel + "', '" + achternaam + "', TO_DATE('" + geboortedatum + "' , 'yyyy/mm/dd')";

            s.executeQuery(query);
            s.close();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            int id = reiziger.getId();
            String voorletters = reiziger.getVoorletters();
            String tusssenvoegsel = reiziger.getTussenvoegsel();
            String achternaam = reiziger.getAchternaam();
            Date geboortedatum = reiziger.getGeboortedatum();

            Statement s = conn.createStatement();
            String query = "UPDATE reiziger " +
                    "SET voorletters = '" + voorletters + "', SET tussenvoegsel = '" + tusssenvoegsel + "', SET achternaam = '" + achternaam + "', SET geboortedatum = TO_DATE('" + geboortedatum + "' , 'yyyy/mm/dd')" +
                    "WHERE reiziger_id = " + id;

            s.executeQuery(query);
            s.close();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            int id = reiziger.getId();
            Statement s = conn.createStatement();
            String query = "DELETE FROM reiziger WHERE reiziger_id = " + id;

            s.executeQuery(query);
            s.close();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            Statement s = conn.createStatement();
            String query = "SELECT * FROM reiziger WHERE reiziger_id = " + id;

            ResultSet resultSet = s.executeQuery(query);
            Reiziger reiziger = new Reiziger();
            if (resultSet.next()) {
                reiziger.setId(resultSet.getInt("reiziger_id"));
                reiziger.setVoorletters(resultSet.getString("voorletters"));
                if (resultSet.getString("tussenvoegsel") == null || resultSet.getString("tussenvoegsel") == "") {
                    reiziger.setTussenvoegsel(null);
                } else {
                    reiziger.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
                }
                reiziger.setAchternaam(resultSet.getString("achternaam"));
                reiziger.setGeboortedatum(resultSet.getDate("geboortedatum"));
            }
            resultSet.close();
            s.close();
            return reiziger;
        }catch (SQLException e){
            System.err.println("fout kan lijst niet opvragen: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        try {
            Statement s = conn.createStatement();
            String query = "SELECT * FROM reiziger WHERE geboortedatum = '" + datum+ "'";

            ResultSet resultSet = s.executeQuery(query);
            List<Reiziger> reizigers = new ArrayList<>();
            if (resultSet.next()) {
                Reiziger reiziger = new Reiziger();
                reiziger.setId(resultSet.getInt("reiziger_id"));
                reiziger.setVoorletters(resultSet.getString("voorletters"));
                if (resultSet.getString("tussenvoegsel") == null || resultSet.getString("tussenvoegsel") == "") {
                    reiziger.setTussenvoegsel(null);
                } else {
                    reiziger.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
                }
                reiziger.setAchternaam(resultSet.getString("achternaam"));
                reiziger.setGeboortedatum(resultSet.getDate("geboortedatum"));
                reizigers.add(reiziger);
            }
            resultSet.close();
            s.close();
            return reizigers;
        }catch (SQLException e){
            System.err.println("fout kan lijst niet opvragen: " + e.getMessage());
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            Statement s = conn.createStatement();
            String query = "SELECT * FROM reiziger";

            ResultSet resultSet = s.executeQuery(query);
            List<Reiziger> reizigers = new ArrayList<>();
            if (resultSet.next()) {
                Reiziger reiziger = new Reiziger();
                reiziger.setId(resultSet.getInt("reiziger_id"));
                reiziger.setVoorletters(resultSet.getString("voorletters"));
                if (resultSet.getString("tussenvoegsel") == null || resultSet.getString("tussenvoegsel") == "") {
                    reiziger.setTussenvoegsel(null);
                } else {
                    reiziger.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
                }
                reiziger.setAchternaam(resultSet.getString("achternaam"));
                reiziger.setGeboortedatum(resultSet.getDate("geboortedatum"));
                reizigers.add(reiziger);
            }
            resultSet.close();
            s.close();
            return reizigers;
        }catch (SQLException e){
            System.err.println("fout kan lijst niet opvragen: " + e.getMessage());
            return null;
        }
    }
}
