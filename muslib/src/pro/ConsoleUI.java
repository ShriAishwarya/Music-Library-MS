package pro;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private static DatabaseHandler dbHandler = new DatabaseHandler();
    private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("Welcome to the Product Review Aggregator!");

        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add a Song");
            System.out.println("2. Add a review");
            System.out.println("3. Search Songs");
            System.out.println("4. Update a Song");
            System.out.println("5. Delete a Song");
            System.out.println("6. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character from the input buffer

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    addReview();
                    break;         
                case 3:
                    searchProducts();
                    break;
                case 4:
                    updateProduct();
                    break;
                case 5:
                    deleteProduct();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    private static void addProduct() {
        System.out.println("Enter the song name:");
        String name = scanner.nextLine();

        System.out.println("Enter the song description:");
        String description = scanner.nextLine();

        System.out.println("Enter the Source:");
        String source = scanner.nextLine();

        int nextId = dbHandler.getNextProductId();
        Product product = new Product(nextId, name, description, source);
        dbHandler.addProduct(product);


        System.out.println("Song added successfully!");
    }


    private static void addReview() {
        System.out.println("Enter the Song ID for which you want to add a review:");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Clear the newline character from the input buffer

        System.out.println("Enter the rating (1 to 5) for the product:");
        int rating = scanner.nextInt();
        scanner.nextLine(); // Clear the newline character from the input buffer

        System.out.println("Enter your review comment:");
        String comment = scanner.nextLine();

        System.out.println("Enter the review source:");
        String source = scanner.nextLine();

        int nextId = dbHandler.getNextReviewId(); // Assuming you have a similar method to get the next available review ID
        Review review = new Review(nextId, productId, rating, comment, source);
        dbHandler.addReview(review);
        

        System.out.println("Review added successfully!");
    }

    
    private static void updateProduct() {
        System.out.println("Enter the Song ID to update:");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Clear the newline character from the input buffer

        Product product = dbHandler.getProductById(productId);
        if (product == null) {
            System.out.println("Song with ID " + productId + " not found.");
            return;
        }

        System.out.println("Current Song details:");
        System.out.println("Product ID: " + product.getId());
        System.out.println("Product Name: " + product.getName());
        System.out.println("Description: " + product.getDescription());
        System.out.println("Source: " + product.getSource());

        System.out.println("\nEnter the updated Song name:");
        String name = scanner.nextLine();

        System.out.println("Enter the updated Song description:");
        String description = scanner.nextLine();

        System.out.println("Enter the updated Song source:");
        String source = scanner.nextLine();

        product.setName(name);
        product.setDescription(description);
        product.setSource(source);

        dbHandler.updateProduct(product);
       

        System.out.println("Song updated successfully!");
    }

    private static void deleteProduct() {
        System.out.println("Enter the Song ID to delete:");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Clear the newline character from the input buffer

        Product product = dbHandler.getProductById(productId);
        if (product == null) {
            System.out.println("Song with ID " + productId + " not found.");
            return;
        }

        System.out.println("Product details:");
        System.out.println("Product ID: " + product.getId());
        System.out.println("Product Name: " + product.getName());
        System.out.println("Description: " + product.getDescription());
        System.out.println("Source: " + product.getSource());

        System.out.println("\nAre you sure you want to delete this product? (Y/N)");
        String confirmation = scanner.nextLine();
       

        if (confirmation.equalsIgnoreCase("Y")) {
            dbHandler.deleteProduct(productId);
            System.out.println("Song deleted successfully!");
        } else {
            System.out.println("Song deletion canceled.");
        }
    }
    private static void searchProducts() {
        System.out.println("Enter the Song name to search for:");
        String searchTerm = scanner.nextLine();

        List<Product> products = dbHandler.getAllProducts();
        boolean found = false;

        for (Product product : products) {
            if (product.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                found = true;
                System.out.println("\nProduct Details:");
                System.out.println("Song ID: " + product.getId());
                System.out.println("Song Name: " + product.getName());
                System.out.println("Description: " + product.getDescription());
                System.out.println("Source: " + product.getSource());

                List<Review> reviews = dbHandler.getReviewsForProduct(product.getId());
                if (!reviews.isEmpty()) {
                    System.out.println("Reviews Details:");
                    for (Review review : reviews) {
                        System.out.println("Review ID: " + review.getId());
                        System.out.println("Rating: " + review.getRating());
                        System.out.println("Comment: " + review.getComment());
                        System.out.println("Source: " + review.getSource());
                        System.out.println("-----------");
                    }
                } 
                else {
                    System.out.println("No reviews found for this product.");
                }

                System.out.println("--------------------------------------------------");
            }
        }

        if (!found) {
            System.out.println("No Songs found matching the search term.");
        }
    }
}