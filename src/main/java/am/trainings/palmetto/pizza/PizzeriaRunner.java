package am.trainings.palmetto.pizza;

import am.trainings.palmetto.common.ApplicationContextHolder;
import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.repository.CustomerRepository;
import am.trainings.palmetto.customer.service.OrderService;
import am.trainings.palmetto.customer.service.OrderServiceImpl;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static am.trainings.palmetto.Pizerria.registerCustomer;

public class PizzeriaRunner {

    private final ApplicationContextHolder applicationContextHolder;
    private final Scanner scanner;

    public PizzeriaRunner(
            ApplicationContextHolder applicationContextHolder,
            Scanner scanner
    ) {
        this.applicationContextHolder = applicationContextHolder;
        this.scanner = scanner;
    }

    public void run() {
        System.out.println("Welcome to Pizzeria Palmetto!");
        System.out.println("1. Register customer");
        System.out.println("2. Place order");

        System.out.print("Choose an option: ");
        String option = scanner.nextLine().trim();

        Customer customer;

        switch (option) {
            case "1":
                customer = registerCustomer(
                        applicationContextHolder,
                        scanner
                );
                break;

            case "2":
                customer = findRegisteredCustomer();
                break;

            default:
                System.out.println("Invalid option.");
                return;
        }

        if (customer == null) {
            return;
        }

        System.out.println("\nYou can now place your order.");

        Map<Pizza, Integer> pizzas = readPizzas();

        if (pizzas.isEmpty()) {
            System.out.println("Order cancelled: no pizzas selected.");
            return;
        }

        OrderService orderService =
                new OrderServiceImpl(applicationContextHolder);

        orderService.orderPizza(customer.getUsername(), pizzas);
    }

    private Customer findRegisteredCustomer() {
        CustomerRepository customerRepository =
                applicationContextHolder.getObject(
                        CustomerRepository.class
                );

        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();

        Customer customer = customerRepository
                .findCustomerByUsername(username)
                .orElse(null);

        if (customer == null) {
            System.out.println(
                    "Customer not found. Please register first."
            );
        }

        return customer;
    }

    private Map<Pizza, Integer> readPizzas() {
        Map<Pizza, Integer> pizzas = new LinkedHashMap<>();

        PizzaType[] availablePizzas = PizzaType.values();

        System.out.println("\nAvailable pizzas:");

        for (int i = 0; i < availablePizzas.length; i++) {
            Pizza available = Pizza.fromType(availablePizzas[i].name());
            System.out.println((i + 1) + ". " + available.getName());
        }

        while (true) {
            System.out.print("\nChoose pizza number (0 to finish): ");
            String input = scanner.nextLine().trim();

            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }

            if (choice == 0) {
                break;
            }

            if (choice < 1 || choice > availablePizzas.length) {
                System.out.println("Invalid pizza selection.");
                continue;
            }

            PizzaType pizzaType = availablePizzas[choice - 1];
            Pizza pizza = Pizza.fromType(pizzaType.name());

            System.out.print("Enter quantity: ");
            String quantityInput = scanner.nextLine().trim();

            int quantity;

            try {
                quantity = Integer.parseInt(quantityInput);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid quantity.");
                continue;
            }

            if (quantity <= 0) {
                System.out.println(
                        "Quantity must be greater than zero."
                );
                continue;
            }

            pizzas.merge(pizza, quantity, Integer::sum);

            System.out.println(
                    pizza.getName() + " x " + quantity + " added."
            );
        }

        return pizzas;
    }
}