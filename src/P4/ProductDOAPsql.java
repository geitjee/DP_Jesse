package P4;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDOAPsql implements ProductDAO{
    private Connection conn;

    public ProductDOAPsql(Connection conn) throws SQLException {
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("INSERT INTO product(product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?);");
        statement.setInt(1, product.getProduct_nummer());
        statement.setString(2, product.getNaam());
        statement.setString(3, product.getBeschrijving());
        statement.setFloat(4, product.getPrijs());
        statement.executeUpdate();
        if (product.getOvChipkaartList().size() >= 1) {
            for (Integer ov : product.getOvChipkaartList()) {
                PreparedStatement statement2 = conn.prepareStatement("INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer) VALUES (?, ?);");
                statement2.setInt(1, ov);
                statement2.setInt(2, product.getProduct_nummer());
                statement2.executeUpdate();
                statement2.close();
            }
        }
        statement.close();
        return true;
    }
    @Override
    public boolean update(Product product) throws SQLException {
        Statement s = conn.createStatement();
        String query = "SELECT * FROM product WHERE product_nummer = " + product.getProduct_nummer();
        ResultSet resultSet = s.executeQuery(query);
        if(resultSet.next() == false){
            return save(product);
        }
        resultSet.close();
        PreparedStatement statement = conn.prepareStatement("UPDATE product SET naam = ? , beschrijving = ?, prijs = ? WHERE product_nummer = ?");
        statement.setString(1, product.getNaam());
        statement.setString(2, product.getBeschrijving());
        statement.setFloat(3, product.getPrijs());
        statement.setInt(4, product.getProduct_nummer());
        statement.executeUpdate();
        for (Integer ov : product.getOvChipkaartList()){
            String query2 = "SELECT * FROM ov_chipkaart_product WHERE kaart_nummer = "+ ov + " product_nummer = " + product.getProduct_nummer();
            ResultSet rs = s.executeQuery(query2);
            if(rs.next() == false){
                PreparedStatement statement2 = conn.prepareStatement("INSERT INTO ov_chipkaart_product(kaart_nummer, product_nummer) VALUES (?, ?);");
                statement2.setInt(1, ov);
                statement2.setInt(2, product.getProduct_nummer());
                statement2.executeUpdate();
                statement2.close();
            }
            resultSet.close();
        }
        s.close();
        statement.close();
        return true;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        Statement s = conn.createStatement();
        String query = "DELETE FROM product WHERE product_nummer = " + product.getProduct_nummer();
        s.executeUpdate(query);
        if (product.getOvChipkaartList().size() >= 1) {
            String query2 = "DELETE FROM ov_chipkaart_product WHERE product_nummer = " + product.getProduct_nummer();
            s.executeUpdate(query2);
        }
        s.close();
        return true;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        Statement s = conn.createStatement();
        String query = "select p.*, chip.* from product p INNER JOIN ov_chipkaart_product ov ON ov.product_nummer = p.product_nummer inner join ov_chipkaart chip ON chip.kaart_nummer = ov.kaart_nummer";
        ResultSet resultSet = s.executeQuery(query);
        List<Product> productList = new ArrayList<Product>();
        while (resultSet.next()){
            Product p = new Product(resultSet.getInt("product_nummer"), resultSet.getString("naam"), resultSet.getString("beschrijving"), resultSet.getFloat("prijs"));
            String str = "select * from ov_chipkaart_product WHERE product_nummer = " + resultSet.getInt("product_nummer");
            ResultSet rs = s.executeQuery(str);
            List<Integer> kaartnummers = new ArrayList<>();
            while (rs.next()){
                int ov = resultSet.getInt("kaart_nummer");
                kaartnummers.add(ov);
            }
            p.setOvChipkaartList(kaartnummers);
            productList.add(p);
            rs.close();
        }
        resultSet.close();
        s.close();
        return productList;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        Statement s = conn.createStatement();
        String query = "select * from product";
        ResultSet resultSet = s.executeQuery(query);
        List<Product> productList = new ArrayList<Product>();
        while (resultSet.next()){
            Product p = new Product(resultSet.getInt("product_nummer"), resultSet.getString("naam"), resultSet.getString("beschrijving"), resultSet.getFloat("prijs"));
            String str = "select * from ov_chipkaart_product WHERE product_nummer = " + resultSet.getInt("product_nummer");
            ResultSet rs = s.executeQuery(str);
            List<Integer> kaartnummers = new ArrayList<>();
            while (rs.next()){
                int ov = resultSet.getInt("kaart_nummer");
                kaartnummers.add(ov);
            }
            p.setOvChipkaartList(kaartnummers);
            productList.add(p);
            rs.close();
        }
        resultSet.close();
        s.close();
        return productList;
    }
}
