package by.epam.training.homework27.exception;

import org.springframework.security.core.AuthenticationException;

public class UserAlreadyExistException extends AuthenticationException {

    public UserAlreadyExistException(String message) {
        super(message);
    }
}
