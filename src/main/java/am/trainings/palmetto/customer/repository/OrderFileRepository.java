package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.common.JsonWriter;
import am.trainings.palmetto.customer.Order;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class OrderFileRepository implements OrderRepository {

    private static final Path FILE = Path.of("data", "orders.json");

    private static final OrderFileRepository INSTANCE = new OrderFileRepository();

    private final Map<Long, Order> orders = new HashMap<>();

    private long orderId = 1L;

    private OrderFileRepository() {
        loadOrders();
    }

    public static OrderFileRepository getInstance() {
        return INSTANCE;
    }

    @Override
    public long save(Order order) {
        Objects.requireNonNull(order, "Order cannot be null");

        long id = orderId++;
        order.setOrderId(id);
        orders.put(id, order);

        saveOrders();
        return id;
    }

    private void loadOrders() {
        List<Order> loaded = JsonWriter.getInstance().read(FILE, Order.class);
        for (Order order : loaded) {
            orders.put(order.getOrderId(), order);
            orderId = Math.max(orderId, order.getOrderId() + 1);
        }
    }

    private void saveOrders() {
        JsonWriter.getInstance().write(FILE, orders.values());
    }
}
