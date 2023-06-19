package by.epam.training.homework27.service.impl;

import by.epam.training.homework27.dao.UserDao;
import by.epam.training.homework27.dto.UserDto;
import by.epam.training.homework27.exception.UserAlreadyExistException;
import by.epam.training.homework27.model.User;
import by.epam.training.homework27.service.UserService;
import by.epam.training.homework27.converter.UserConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserConverter userConverter;

    public UserServiceImpl(UserDao userDao, UserConverter userConverter) {
        this.userDao = userDao;
        this.userConverter = userConverter;
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        this.getUserByUserName(userDto.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException("User already exists");
                });
        User savedUser = userDao.save(userConverter.convertUserDtoToUser(userDto));
        userDto.setId(savedUser.getId());
        return userDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> getUserByUserName(String username) {
        return userDao.getUserByUserName(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAll() {
        return userDao.getAll().stream()
                .map(userConverter::convertUserToUserDto)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getById(long id) {
        User user = userDao.get(id).orElseThrow(() -> new NoSuchElementException("User was not found by Id = " + id));
        return userConverter.convertUserToUserDto(user);
    }

    @Override
    @Transactional
    public void update(long id, UserDto userDto) {
        if (userDao.get(id).isEmpty()) {
            throw new NoSuchElementException("User was not found by Id = " + id);
        }
        User user = userConverter.convertUserDtoToUser(userDto);
        user.setId(id);
        userDao.update(user);
    }
}
