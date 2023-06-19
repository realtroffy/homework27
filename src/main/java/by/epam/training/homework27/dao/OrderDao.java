package by.epam.training.homework27.dao;

import by.epam.training.homework27.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Optional<Order> getById(long id);

    List<Order> getAll();

    List<Order> getByUserId(long id);

    Order save(Order order);

    void update(Order order);

}
