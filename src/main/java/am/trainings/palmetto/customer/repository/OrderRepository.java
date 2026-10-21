package am.trainings.palmetto.customer.repository;

import am.trainings.palmetto.customer.Order;
import am.trainings.palmetto.common.BaseRepository;

public interface OrderRepository extends BaseRepository {

    long save(Order order);

}
