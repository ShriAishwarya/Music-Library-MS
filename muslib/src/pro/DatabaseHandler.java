package pro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler {
    private static final String DB_URL = "jdbc:mysql://localhost/Music";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "hualian800";

    private Connection connection;

    public DatabaseHandler() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProduct(Product product) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO products (name, description, source) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setString(3, product.getSource());
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    product.setId(generatedId);
                }
            }

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addReview(Review review) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO reviews (productId, rating, comment, source) VALUES (?, ?, ?, ?)"
            );
            statement.setInt(1, review.getProductId());
            statement.setInt(2, review.getRating());
            statement.setString(3, review.getComment());
            statement.setString(4, review.getSource());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM products");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String source = resultSet.getString("source");
                Product product = new Product(id, name, description, source);
                products.add(product);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Review> getReviewsForProduct(int productId) {
        List<Review> reviews = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM reviews WHERE productId = ?"
            );
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int rating = resultSet.getInt("rating");
                String comment = resultSet.getString("comment");
                String source = resultSet.getString("source");
                Review review = new Review(id, productId, rating, comment, source);
                reviews.add(review);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }
    public void updateProduct(Product product) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                "UPDATE products SET name = ?, description = ?, source = ? WHERE id = ?"
            );
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setString(3, product.getSource());
            statement.setInt(4, product.getId());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteProduct(int productId) {
        try {
            // First, delete reviews associated with the product
            PreparedStatement reviewDeleteStatement = connection.prepareStatement(
                "DELETE FROM reviews WHERE productId = ?"
            );
            reviewDeleteStatement.setInt(1, productId);
            reviewDeleteStatement.executeUpdate();
            reviewDeleteStatement.close();

            // Then, delete the product itself
            PreparedStatement productDeleteStatement = connection.prepareStatement(
                "DELETE FROM products WHERE id = ?"
            );
            productDeleteStatement.setInt(1, productId);
            productDeleteStatement.executeUpdate();
            productDeleteStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
   

    public int getNextProductId() {
        int nextId = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM products");
            if (resultSet.next()) {
                nextId = resultSet.getInt(1) + 1;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }
    public int getNextReviewId() {
        int nextId = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT MAX(id) FROM reviews");
            if (resultSet.next()) {
                nextId = resultSet.getInt(1) + 1;
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nextId;
    }
    
    public Product getProductById(int productId) {
        Product product = null;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM products WHERE id = ?"
            );
            statement.setInt(1, productId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String source = resultSet.getString("source");
                product = new Product(productId, name, description, source);
            }
            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> searchProducts(String searchTerm) {
        List<Product> products = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM products WHERE name LIKE ? OR description LIKE ? OR source LIKE ?"
            );
            statement.setString(1, "%" + searchTerm + "%");
            statement.setString(2, "%" + searchTerm + "%");
            statement.setString(3, "%" + searchTerm + "%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String source = resultSet.getString("source");
                Product product = new Product(id, name, description, source);
                products.add(product);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}

