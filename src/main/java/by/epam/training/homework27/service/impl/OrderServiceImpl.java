package by.epam.training.homework27.service.impl;

import by.epam.training.homework27.dao.GoodDao;
import by.epam.training.homework27.dao.OrderDao;
import by.epam.training.homework27.dto.OrderDto;
import by.epam.training.homework27.exception.InvalidFilExtensionException;
import by.epam.training.homework27.model.AttachmentFile;
import by.epam.training.homework27.model.Good;
import by.epam.training.homework27.model.Order;
import by.epam.training.homework27.model.User;
import by.epam.training.homework27.service.OrderService;
import by.epam.training.homework27.converter.OrderConverter;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String ORDER_WAS_NOT_FOUND_BY_SUCH_ID_MESSAGE = "Order was not found by such Id";
    private static final List<String> VALID_EXTENSIONS = List.of("jpg", "jpeg", "bmp", "png");

    private final OrderDao orderDao;
    private final GoodDao goodDao;
    private final OrderConverter orderConverter;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, GoodDao goodDao, OrderConverter orderConverter) {
        this.orderDao = orderDao;
        this.goodDao = goodDao;
        this.orderConverter = orderConverter;
    }

    @Override
    @Transactional
    public OrderDto save(OrderDto orderDto, User user) {
        if (orderDto.getOrderedGoods() == null) {
            throw new NoSuchElementException("Order is null");
        }
        Order order = orderConverter.convertOrderDtoToOrder(orderDto);
        order.setUser(user);
        Order createdOrder = orderDao.save(order);
        user.addOrderToUser(order);
        return orderConverter.convertOrderToOrderDto(createdOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getById(long id) {
        Order order = orderDao.getById(id)
                .orElseThrow(() -> new NoSuchElementException(ORDER_WAS_NOT_FOUND_BY_SUCH_ID_MESSAGE));
        return orderConverter.convertOrderToOrderDto(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getAll() {
        return orderDao.getAll().stream()
                .map(orderConverter::convertOrderToOrderDto)
                .collect(toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getByUserId(long id) {
        List<Order> orders = orderDao.getByUserId(id);
        if (orders.isEmpty()) {
            throw new NoSuchElementException(ORDER_WAS_NOT_FOUND_BY_SUCH_ID_MESSAGE);
        }

        return orders.stream()
                .map(orderConverter::convertOrderToOrderDto)
                .collect(toList());
    }

    @Override
    @Transactional
    public void addGood(long orderId, long goodId) {
        Order order = orderDao.getById(orderId)
                .orElseThrow(() -> new NoSuchElementException(ORDER_WAS_NOT_FOUND_BY_SUCH_ID_MESSAGE));
        Good orderedGood = goodDao.getById(goodId)
                .orElseThrow(() -> new NoSuchElementException("Good was not found by such Id"));

        order.addGoodToOrder(orderedGood);
        updateTotalPrice(order);
        orderDao.update(order);
    }

    @Override
    @Transactional
    public void uploadFile(MultipartFile file, long orderId) throws IOException {
        Order order = orderDao.getById(orderId)
                .orElseThrow(() -> new NoSuchElementException(ORDER_WAS_NOT_FOUND_BY_SUCH_ID_MESSAGE));
        if (!isValidExtension(file)){
            throw new InvalidFilExtensionException("Please upload file with valid extension - jpg, jpeg, bmp, png");
        }
            AttachmentFile attachmentFile = new AttachmentFile();
            attachmentFile.setFile(file.getBytes());
            attachmentFile.setName(file.getOriginalFilename());
            attachmentFile.setType(file.getContentType());
            order.setAttachmentFile(attachmentFile);
    }

    @Override
    @Transactional
    public AttachmentFile downloadFile(long orderId) {
        Order order = orderDao.getById(orderId)
                .orElseThrow(() -> new NoSuchElementException(ORDER_WAS_NOT_FOUND_BY_SUCH_ID_MESSAGE));
        if (order.getAttachmentFile() == null) {
            throw new NoSuchElementException("File not found");
        }
        return order.getAttachmentFile();
    }

    private boolean isValidExtension(MultipartFile file) {

        String uploadFileExtension = getExtensionByGuava(file.getOriginalFilename());

        for (String validExtension : VALID_EXTENSIONS) {
            if (uploadFileExtension.equals(validExtension)) {
                return true;
            }
        }

        return false;
    }

    private String getExtensionByGuava(String filename) {
        return Files.getFileExtension(filename);
    }

    private void updateTotalPrice(Order order) {
        BigDecimal price = order.getGoods().stream()
                .map(Good::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        order.setTotalPrice(price);
    }
}
