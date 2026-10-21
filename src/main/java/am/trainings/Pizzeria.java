package am.trainings;


import org.json.JSONObject;

import java.io.*;

public class Pizzeria {
    public static void main(String[] args) throws IOException {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", "armine");
        jsonObject.put("email", "zakaryanarmine991@gmail.com");


        // Task 1: read order details interactively from the console
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter customer number: ");
        int customerNumber = Integer.parseInt(reader.readLine().trim());

        System.out.print("Enter customer name: ");
        String customerName = reader.readLine().trim();
        Customer customer = new Customer(customerNumber, customerName);

        System.out.println("Available pizza types: " + String.join(", ", PizzaTypeInterface.getTypes()));
        System.out.print("Enter pizza type: ");
        String type = reader.readLine().trim();

        System.out.print("Enter pizza name (4-20 letters): ");
        String pizzaName = reader.readLine().trim();

        System.out.print("Enter quantity: ");
        int quantity = Integer.parseInt(reader.readLine().trim());

        Order order = new Order(customer, type, pizzaName, quantity);

        System.out.print("Enter ingredients, comma-separated (Enter to skip): ");
        String ingredientsLine = reader.readLine();
        if (ingredientsLine != null && !ingredientsLine.isBlank()) {
            for (String ingredient : ingredientsLine.split("\\s*,\\s*")) {
                order.addIngredient(ingredient);
            }
        }

        // Task 2: write the receipt to a file, try-with-resources closes it automatically
        writeReceipt(order);

        System.out.println("Order placed. Receipt written to receipt.txt");
    }

    private static void writeReceipt(Order order) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("receipt.txt"))) {
            writer.println("========= Palmetto Pizzeria =========");
            writer.println("Order: " + order.getOrderDescription());
            writer.println("Pizza details: " + order.getPizza());
            writer.printf("Total price: $%.2f%n", order.getTotalPrice());
            writer.println("======================================");
        } catch (IOException e) {
            System.out.println("Failed to write receipt: " + e.getMessage());
        }
    }

}
