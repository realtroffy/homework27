package by.epam.training.homework27.converter;

import by.epam.training.homework27.dto.UserDto;
import by.epam.training.homework27.model.Role;
import by.epam.training.homework27.model.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    private final PasswordEncoder encoder;

    public UserConverter(@Lazy PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public User convertUserDtoToUser(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRole(Role.ROLE_USER);
        return user;
    }

    public UserDto convertUserToUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        return userDto;
    }
}
