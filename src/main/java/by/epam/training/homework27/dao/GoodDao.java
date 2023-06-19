package by.epam.training.homework27.dao;

import by.epam.training.homework27.model.Good;

import java.util.List;
import java.util.Optional;

public interface GoodDao{

    Optional<Good> getById(long id);

    List<Good> getAll();

}
