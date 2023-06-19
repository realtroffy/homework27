package by.epam.training.homework27.dao.impl;

import by.epam.training.homework27.dao.OrderDao;
import by.epam.training.homework27.model.Order;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Repository
@Transactional(propagation = MANDATORY)
public class OrderDaoImpl implements OrderDao {

    private static final String ORDERS_BY_USER_ID_QUERY = "SELECT o from Order o join o.user u where u.id = :id";

    private final EntityManager entityManager;

    public OrderDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public Order save(Order order) {
        getSession().save(order);
        return order;
    }

    @Override
    public Optional<Order> getById(long id) {
        return getSession()
                .createQuery("from Order where id = :id", Order.class)
                .setParameter("id", id)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public List<Order> getAll() {
        return getSession()
                .createQuery("from Order", Order.class)
                .getResultList();
    }

    @Override
    public List<Order> getByUserId(long id) {
        return getSession()
                .createQuery(ORDERS_BY_USER_ID_QUERY, Order.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public void update(Order order) {
        getSession().saveOrUpdate(order);
    }
}
