package P4;

import Data.OVChipkaartDAO;
import Data.ReizigerDAOPsql;
import P4.OVChipkaart;
import Domein.Reiziger;
import P4.Product;
import P4.ProductDOAPsql;

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
        if (ovChipkaart.getProduct().size() >= 1) {
            for (Product product : ovChipkaart.getProduct()) {
                Statement s = conn.createStatement();
                String query = "SELECT * FROM product WHERE product_nummer = " + product.getProduct_nummer();
                ResultSet rs = s.executeQuery(query);
                if (rs.next() == false){
                    new ProductDOAPsql(conn).save(product);
                }
                PreparedStatement statement2 = conn.prepareStatement("INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer) VALUES (?, ?);");
                statement2.setInt(1, ovChipkaart.getKaart_nummer());
                statement2.setInt(2, product.getProduct_nummer());
                statement2.executeUpdate();
                statement2.close();
            }
        }
        statement.close();
        return true;
    }
    @Override
    public boolean update(OVChipkaart ovChipkaart) throws SQLException {
        Statement s = conn.createStatement();
        String query = "SELECT * FROM ov_chipkaart WHERE kaart_nummer = " + ovChipkaart.getKaart_nummer();
        ResultSet resultSet = s.executeQuery(query);
        if(resultSet.next() == false){
            return save(ovChipkaart);
        }
        resultSet.close();
        s.close();
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
        s.executeUpdate(query);
        if (ovChipkaart.getProduct().size() >= 1) {
            String query2 = "DELETE FROM ov_chipkaart_product WHERE kaart_nummer = " + ovChipkaart.getKaart_nummer();
            s.executeUpdate(query2);
        }
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
        ov.setProduct(new ProductDOAPsql(conn).findByOVChipkaart(ov));
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
            OVChipkaart ov = new OVChipkaart(resultSet.getInt("kaart_nummer"), resultSet.getDate("geldig_tot"), resultSet.getInt("klasse"), resultSet.getFloat("saldo"), reiziger);
            ov.setProduct(new ProductDOAPsql(conn).findByOVChipkaart(ov));
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
            ov.setProduct(new ProductDOAPsql(conn).findByOVChipkaart(ov));
            ovChipkaarts.add(ov);
        }
        resultSet.close();
        s.close();
        return ovChipkaarts;
    }
}
