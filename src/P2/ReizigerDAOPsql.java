package P2;

import P3.Adres;
import P3.AdresDAOPsql;
import P4.OVChipkaartDAOPsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOPsql implements ReizigerDAO {

    private Connection conn;

    public ReizigerDAOPsql(Connection conn) throws SQLException {
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        int id = reiziger.getId();
        String voorletters = reiziger.getVoorletters();
        String tusssenvoegsel = reiziger.getTussenvoegsel();
        String achternaam = reiziger.getAchternaam();
        Date geboortedatum = reiziger.getGeboortedatum();

        PreparedStatement statement = conn.prepareStatement("INSERT INTO reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?);");
        statement.setInt(1, id);
        statement.setString(2, voorletters);
        statement.setString(3, tusssenvoegsel);
        statement.setString(4, achternaam);
        statement.setDate(5, geboortedatum);
        statement.executeUpdate();
        statement.close();
        return true;
    }
    @Override
    public boolean update(Reiziger reiziger) throws SQLException {
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
    }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        int id = reiziger.getId();
        Statement s = conn.createStatement();
        String query = "DELETE FROM reiziger WHERE reiziger_id = " + id;

        s.executeQuery(query);
        s.close();
        return true;
    }

    @Override
    public Reiziger findById(int id) throws SQLException {
        Statement s = conn.createStatement();
        String query = "SELECT * FROM reiziger WHERE reiziger_id = " + id;

        ResultSet resultSet = s.executeQuery(query);
        resultSet.next();
        Reiziger reiziger = new Reiziger(resultSet.getInt("reiziger_id"), resultSet.getString("voorletters"), resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"), resultSet.getDate("geboortedatum"));

        reiziger.setAdres(new AdresDAOPsql(conn).findByReiziger(reiziger));
        reiziger.setOvChipkaarts(new OVChipkaartDAOPsql(conn).findByReiziger(reiziger));
        resultSet.close();
        s.close();
        return reiziger;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException {
        PreparedStatement p = conn.prepareStatement("SELECT * FROM reiziger WHERE geboortedatum = ?");
        p.setDate(1, Date.valueOf(datum));
        ResultSet resultSet = p.executeQuery();
        List<Reiziger> reizigers = new ArrayList<>();
        while (resultSet.next()) {
            Reiziger reiziger = new Reiziger(resultSet.getInt("reiziger_id"), resultSet.getString("voorletters"), resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"), resultSet.getDate("geboortedatum"));

            reiziger.setAdres(new AdresDAOPsql(conn).findByReiziger(reiziger));
            reiziger.setOvChipkaarts(new OVChipkaartDAOPsql(conn).findByReiziger(reiziger));
            reizigers.add(reiziger);
        }
        resultSet.close();
        p.close();
        return reizigers;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException {
        Statement s = conn.createStatement();
        String query = "SELECT * FROM reiziger";

        ResultSet resultSet = s.executeQuery(query);
        List<Reiziger> reizigers = new ArrayList<>();
        while (resultSet.next()) {
            Reiziger reiziger = new Reiziger(resultSet.getInt("reiziger_id"), resultSet.getString("voorletters"), resultSet.getString("tussenvoegsel"), resultSet.getString("achternaam"), resultSet.getDate("geboortedatum"));

            reiziger.setAdres(new AdresDAOPsql(conn).findByReiziger(reiziger));
            reiziger.setOvChipkaarts(new OVChipkaartDAOPsql(conn).findByReiziger(reiziger));
            reizigers.add(reiziger);
        }
        resultSet.close();
        s.close();
        return reizigers;
    }
}
