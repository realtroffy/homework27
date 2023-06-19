package by.epam.training.homework27.service;

import by.epam.training.homework27.dto.OrderDto;
import by.epam.training.homework27.model.AttachmentFile;
import by.epam.training.homework27.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface OrderService {

    OrderDto save(OrderDto dto, User user);

    OrderDto getById(long id);

    List<OrderDto> getAll();

    List<OrderDto> getByUserId(long id);

    void addGood(long orderId, long goodId);

    AttachmentFile downloadFile(long orderId);

    void uploadFile(MultipartFile file, long orderId) throws IOException;
}