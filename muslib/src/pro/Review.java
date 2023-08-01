package pro;

import java.util.ArrayList;


//import java.util.ArrayList;
import java.util.List;
//import java.util.Scanner;
public class Review implements Displayable {
    private int id;
    private int productId;
    private int rating;
    private String comment;
    private String source;

    public Review(int id, int productId, int rating, String comment, String source) {
        this.id = id;
        this.productId = productId;
        this.rating = rating;
        this.comment = comment;
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public void displayInfo() {
        System.out.println("Review ID: " + id);
        System.out.println("Product ID: " + productId);
        System.out.println("Rating: " + rating);
        System.out.println("Comment: " + comment);
        System.out.println("Source: " + source);
    }
}
class Main {
    public static void main(String[] args) {
        Review review1 = new Review(1, 101, 5, "Great product!", "Amazon");
        Review review2 = new Review(2, 102, 4, "Good quality!", "eBay");

        List<Displayable> displayables = new ArrayList<>();
        displayables.add(review1);
        displayables.add(review2);

        for (Displayable displayable : displayables) {
            displayable.displayInfo();
            System.out.println(); // Adding an empty line between each review info
        }
    }
}

