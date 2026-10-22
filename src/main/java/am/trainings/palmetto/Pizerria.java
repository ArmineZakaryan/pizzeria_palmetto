
package am.trainings.palmetto;

import am.trainings.palmetto.common.ApplicationContextHolder;
import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.exceptions.DuplicateUsernameException;
import am.trainings.palmetto.customer.repository.CustomerRepository;
import am.trainings.palmetto.pizza.PizzeriaRunner;

import java.util.Scanner;

public class Pizerria {

    public static void main(String[] args) {
        ApplicationContextHolder contextHolder =
                new ApplicationContextHolder();

        try (Scanner scanner = new Scanner(System.in)) {
            PizzeriaRunner runner =
                    new PizzeriaRunner(contextHolder, scanner);

            runner.run();
        }
    }

    public static Customer registerCustomer(
            ApplicationContextHolder applicationContextHolder,
            Scanner scanner
    ) {
        CustomerRepository customerRepository =
                applicationContextHolder.getObject(
                        CustomerRepository.class
                );

        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine().trim();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine().trim();

        Customer customer = new Customer(fullName, username);

        try {
            customerRepository.registerCustomer(customer);

            System.out.println("\nCustomer registered successfully!");
            System.out.println("Customer ID: " + customer.getCustomerId());
            System.out.println("Full name: " + customer.getFullName());
            System.out.println("Username: " + customer.getUsername());

            return customer;
        } catch (DuplicateUsernameException e) {
            System.out.println(
                    "Registration failed: " + e.getMessage()
            );
            return null;
        }
    }
}
