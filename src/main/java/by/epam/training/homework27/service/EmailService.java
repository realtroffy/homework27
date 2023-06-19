package by.epam.training.homework27.service;

import by.epam.training.homework27.dto.OrderDto;
import by.epam.training.homework27.model.User;

import javax.mail.MessagingException;

public interface EmailService {

    void sendEmail(User user, OrderDto orderDto) throws MessagingException;

}
