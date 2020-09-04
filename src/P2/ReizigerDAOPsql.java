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

            PreparedStatement statement = conn.prepareStatement("INSERT INTO reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?);" );
            statement.setInt(1, id);
            statement.setString(2, voorletters);
            statement.setString(3, tusssenvoegsel);
            statement.setString(4, achternaam);
            statement.setDate(5, geboortedatum);
            statement.executeUpdate();
            statement.close();
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

            PreparedStatement statement = conn.prepareStatement("UPDATE reiziger SET voorletters = ? , tussenvoegsel = ?, achternaam = ? , geboortedatum = ? WHERE reiziger_id = ?");
            statement.setString(1, voorletters);
            statement.setString(2, tusssenvoegsel);
            statement.setString(3, achternaam);
            statement.setDate(4, geboortedatum);
            statement.setInt(5, id);

            statement.executeUpdate();
            statement.close();
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
            resultSet.next();
            reiziger.setId(resultSet.getInt("reiziger_id"));
            reiziger.setVoorletters(resultSet.getString("voorletters"));
            if (resultSet.getString("tussenvoegsel") == null || resultSet.getString("tussenvoegsel") == "") {
                reiziger.setTussenvoegsel(null);
            } else {
                reiziger.setTussenvoegsel(resultSet.getString("tussenvoegsel"));
            }
            reiziger.setAchternaam(resultSet.getString("achternaam"));
            reiziger.setGeboortedatum(resultSet.getDate("geboortedatum"));
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
            PreparedStatement p = conn.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?");
            p.setDate(1, Date.valueOf(datum));
            ResultSet resultSet = p.executeQuery();
            List<Reiziger> reizigers = new ArrayList<>();
            while (resultSet.next()) {
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
            p.close();
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
            while (resultSet.next()) {
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
