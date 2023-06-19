package by.epam.training.homework27.dao.impl;

import by.epam.training.homework27.dao.GoodDao;
import by.epam.training.homework27.model.Good;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Repository
@Transactional(propagation = MANDATORY)
public class GoodDaoImpl implements GoodDao {

    private final EntityManager entityManager;

    public GoodDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public Optional<Good> getById(long id) {
        return getSession()
                .createQuery("from Good where id = :id", Good.class)
                .setParameter("id", id)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public List<Good> getAll() {
        return getSession()
                .createQuery("from Good", Good.class)
                .getResultList();
    }
}

