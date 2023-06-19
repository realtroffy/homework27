package by.epam.training.homework27.service;



import by.epam.training.homework27.dto.UserDto;
import by.epam.training.homework27.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    UserDto save(UserDto userDto);

    Optional<User> getUserByUserName(String userName);

    List<UserDto> getAll();

    UserDto getById(long id);

    void update(long id, UserDto userDto);

}
