package by.epam.training.homework27.dao.impl;

import by.epam.training.homework27.dao.UserDao;
import by.epam.training.homework27.model.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Repository
@Transactional(propagation = MANDATORY)
public class UserDaoImpl implements UserDao {

    private final EntityManager entityManager;

    public UserDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public User save(User user) {
        getSession().save(user);
        return user;
    }

    @Override
    public Optional<User> getUserByUserName(String username) {
        User user = getSession()
                .createQuery("FROM User where username = :username", User.class)
                .setParameter("username", username)
                .uniqueResult();

        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> get(long id) {
        return getSession()
                .createQuery("from User where id = :id", User.class)
                .setParameter("id", id)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public List<User> getAll() {
        return getSession()
                .createQuery("from User", User.class)
                .getResultList();
    }

    @Override
    public void update(User user) {
        getSession().update(user);
    }

    @Override
    public void delete(long id) {
        getSession()
                .createQuery("delete from User where id =:id")
                .setParameter("id", id)
                .executeUpdate();
    }
}

