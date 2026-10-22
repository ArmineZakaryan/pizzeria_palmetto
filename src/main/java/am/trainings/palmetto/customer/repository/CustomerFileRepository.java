package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.common.JsonWriter;
import am.trainings.palmetto.customer.Customer;
import am.trainings.palmetto.customer.exceptions.DuplicateUsernameException;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


public final class CustomerFileRepository implements CustomerRepository {

    private static final Path FILE = Path.of("data", "customers.json");

    private static final CustomerFileRepository INSTANCE = new CustomerFileRepository();

    private final Map<String, Customer> customers = new HashMap<>();

    private long customerId = 1L;

    private CustomerFileRepository() {
        loadCustomers();
    }

    public static CustomerFileRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public String registerCustomer(Customer customer) {
        Objects.requireNonNull(customer, "Customer cannot be null!");

        String username = customer.getUsername();

        if (customers.containsKey(username)) {
            throw new DuplicateUsernameException(
                    "Username: " + username + " already exists"
            );
        }
        customer.setCustomerId(customerId++);
        customers.put(username, customer);
        saveCustomers();
        return username;
    }

    @Override
    public Optional<Customer> findCustomerByUsername(String username) {
        return Optional.ofNullable(customers.get(username));
    }

    private void loadCustomers() {
        List<Customer> loaded = JsonWriter.getInstance().read(FILE, Customer.class);
        for (Customer customer : loaded) {
            customers.put(customer.getUsername(), customer);
            customerId = Math.max(customerId, customer.getCustomerId() + 1);
        }
    }

    private void saveCustomers() {
        JsonWriter.getInstance().write(FILE, customers.values());
    }
}
