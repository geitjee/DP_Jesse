package P3;

import P2.Reiziger;
import P2.ReizigerDAOPsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {

    private Connection conn;
    public AdresDAOPsql(Connection connection) throws SQLException{
        conn = connection;
    }
    @Override
    public boolean save(Adres adres) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO adres(adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES (?, ?, ?, ?, ?, ?);");
        statement.setInt(1, adres.getId());
        statement.setString(2, adres.getPostcode());
        statement.setString(3, adres.getHuisnummer());
        statement.setString(4, adres.getStraat());
        statement.setString(5, adres.getWoonplaats());
        statement.setInt(6, adres.getReiziger_id().getId());
        statement.executeUpdate();
        statement.close();
        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id = ?");
        statement.setString(1, adres.getPostcode());
        statement.setString(2, adres.getHuisnummer());
        statement.setString(3, adres.getStraat());
        statement.setString(4, adres.getWoonplaats());
        statement.setInt(5, adres.getReiziger_id().getId());
        statement.setInt(6, adres.getId());
        statement.executeUpdate();
        statement.close();
        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
        Statement s = conn.createStatement();
        String query = "DELETE FROM adres WHERE adres_id = " + adres.getId();

        s.executeQuery(query);
        s.close();
        return true;
    }

    @Override
    public Adres findById(int id) throws SQLException {
        Statement s = conn.createStatement();
        String query = "SELECT * FROM adres WHERE adres_id = " + id;

        ResultSet resultSet = s.executeQuery(query);
        resultSet.next();
        Adres adres = new Adres(resultSet.getInt("adres_id"), resultSet.getString("postcode"), resultSet.getString("huisnummer"), resultSet.getString("straat"), resultSet.getString("woonplaats"), new ReizigerDAOPsql(conn).findById(resultSet.getInt("reiziger_id")));
        resultSet.close();
        s.close();
        return adres;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Statement s = conn.createStatement();
        String query = "SELECT * FROM adres WHERE reiziger_id = " + reiziger.getId();
        ResultSet resultSet = s.executeQuery(query);
        if (resultSet.next() == false) {
            return null;
        }
        Adres a = new Adres(resultSet.getInt("adres_id"), resultSet.getString("postcode"), resultSet.getString("huisnummer"), resultSet.getString("straat"), resultSet.getString("woonplaats"), new ReizigerDAOPsql(conn).findById(resultSet.getInt("reiziger_id")));
        resultSet.close();
        s.close();
        return a;

    }

    @Override
    public List<Adres> findAll() throws SQLException {
        Statement s = conn.createStatement();
        String query = "SELECT * FROM adres";

        ResultSet resultSet = s.executeQuery(query);
        List<Adres> adres = new ArrayList<>();
        while (resultSet.next()) {
            Adres a = new Adres(resultSet.getInt("adres_id"), resultSet.getString("postcode"), resultSet.getString("huisnummer"), resultSet.getString("straat"), resultSet.getString("woonplaats"), new ReizigerDAOPsql(conn).findById(resultSet.getInt("reiziger_id")));
            adres.add(a);
        }
        resultSet.close();
        s.close();
        return adres;
    }
}
