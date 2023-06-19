package by.epam.training.homework27.controller;

import by.epam.training.homework27.dto.OrderDto;
import by.epam.training.homework27.model.AttachmentFile;
import by.epam.training.homework27.model.User;
import by.epam.training.homework27.service.EmailService;
import by.epam.training.homework27.service.OrderService;
import by.epam.training.homework27.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final EmailService emailService;

    public OrderController(OrderService orderService, UserService userService, EmailService emailService) {
        this.orderService = orderService;
        this.userService = userService;
        this.emailService = emailService;
    }

    @PostMapping("/{orderId}/file")
    public ResponseEntity<Void> uploadAttachment(@RequestParam MultipartFile file,
                                                 @PathVariable long orderId) throws IOException {
        orderService.uploadFile(file, orderId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{orderId}/file")
    public ResponseEntity<Resource> downloadAttachment(@PathVariable long orderId) {

        AttachmentFile file = orderService.downloadFile(orderId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getType()))
                .body(new ByteArrayResource(file.getFile()));
    }

    @PostMapping
    public ResponseEntity<OrderDto> save(@RequestBody OrderDto dto, Principal principal) throws MessagingException {

        if (principal == null) {
            throw new NoSuchElementException("Not found authorized user");
        }
        User user = userService.getUserByUserName(principal.getName())
                .orElseThrow(() -> new NoSuchElementException("User was not found"));

        OrderDto savedOrder = orderService.save(dto, user);

        emailService.sendEmail(user,savedOrder);

        return ResponseEntity.status(CREATED)
                .body(savedOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersByUserId(@PathVariable("userId") long userId) {
        return ResponseEntity.ok(orderService.getByUserId(userId));
    }

    @PutMapping("/{orderId}/goods/{goodId}")
    public ResponseEntity<OrderDto> updateOrder(@PathVariable("orderId") long orderId,
                                                @PathVariable("goodId") long goodId) {
        orderService.addGood(orderId, goodId);
        return ResponseEntity.noContent().build();
    }
}
