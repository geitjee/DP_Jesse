package P4;

import P2.Reiziger;
import P2.ReizigerDAOPsql;
import P3.AdresDAOPsql;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    private Connection conn;

    public OVChipkaartDAOPsql(Connection conn) throws SQLException {
        this.conn = conn;
    }

    @Override
    public boolean save(OVChipkaart ovChipkaart) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO ov_chipkaart(kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?);");
        statement.setInt(1, ovChipkaart.getKaart_nummer());
        statement.setDate(2, ovChipkaart.getGeldig_tot());
        statement.setInt(3, ovChipkaart.getKlasse());
        statement.setFloat(4, ovChipkaart.getSaldo());
        statement.setInt(5, ovChipkaart.getReiziger().getId());
        statement.executeUpdate();
        statement.close();
        return true;
    }
    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {

        PreparedStatement statement = conn.prepareStatement("UPDATE ov_chipkaart SET geldig_tot = ? , klasse = ?, saldo = ? , reiziger_id = ? WHERE kaart_nummer = ?");
        statement.setDate(1, ovChipkaart.getGeldig_tot());
        statement.setInt(2, ovChipkaart.getKlasse());
        statement.setFloat(3, ovChipkaart.getSaldo());
        statement.setInt(4, ovChipkaart.getReiziger().getId());
        statement.setInt(5, ovChipkaart.getKaart_nummer());

        statement.executeUpdate();
        statement.close();
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovChipkaart) throws SQLException {
        int kaart_nummer = ovChipkaart.getKaart_nummer();
        Statement s = conn.createStatement();
        String query = "DELETE FROM ov_chipkaart WHERE kaartnummer = " + kaart_nummer;
        s.executeQuery(query);
        s.close();
        return true;
    }

    @Override
    public OVChipkaart findById(int kaartnummer) throws SQLException {
        Statement s = conn.createStatement();
        String query = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = " + kaartnummer;

        ResultSet resultSet = s.executeQuery(query);
        resultSet.next();
        OVChipkaart ov = new OVChipkaart(resultSet.getInt("kaart_nummer"), resultSet.getDate("geldig_tot"), resultSet.getInt("klasse"), resultSet.getFloat("saldo"), new ReizigerDAOPsql(conn).findById(resultSet.getInt("reiziger_id")));
        resultSet.close();
        s.close();
        return ov;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        PreparedStatement p = conn.prepareStatement("SELECT * FROM ov_chipkaart WHERE reiziger_id = ?");
        p.setInt(1, reiziger.getId());
        ResultSet resultSet = p.executeQuery();
        List<OVChipkaart> ovChipkaarts = new ArrayList<>();
        while (resultSet.next()) {
            OVChipkaart ov = new OVChipkaart(resultSet.getInt("kaart_nummer"), resultSet.getDate("geldig_tot"), resultSet.getInt("klasse"), resultSet.getFloat("saldo"), new ReizigerDAOPsql(conn).findById(resultSet.getInt("reiziger_id")));
            ovChipkaarts.add(ov);
        }
        resultSet.close();
        p.close();
        return ovChipkaarts;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        Statement s = conn.createStatement();
        String query = "SELECT * FROM ov_chipkaart";

        ResultSet resultSet = s.executeQuery(query);
        List<OVChipkaart> ovChipkaarts = new ArrayList<>();
        while (resultSet.next()) {
            OVChipkaart ov = new OVChipkaart(resultSet.getInt("kaart_nummer"), resultSet.getDate("geldig_tot"), resultSet.getInt("klasse"), resultSet.getFloat("saldo"), new ReizigerDAOPsql(conn).findById(resultSet.getInt("reiziger_id")));
            ovChipkaarts.add(ov);
        }
        resultSet.close();
        s.close();
        return ovChipkaarts;
    }
}
