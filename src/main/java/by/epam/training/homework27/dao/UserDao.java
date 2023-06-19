package by.epam.training.homework27.dao;

import by.epam.training.homework27.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> getUserByUserName(String login);

    Optional<User> get(long id);

    List<User> getAll();

    User save(User user);

    void update(User user);

    void delete(long id);
}
