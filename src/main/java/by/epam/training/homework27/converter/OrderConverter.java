package by.epam.training.homework27.converter;

import by.epam.training.homework27.dto.OrderDto;
import by.epam.training.homework27.model.Good;
import by.epam.training.homework27.model.Order;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class OrderConverter {

    public Order convertOrderDtoToOrder(OrderDto orderDto) {
        Order order = new Order();
        BigDecimal price = orderDto.getOrderedGoods().stream()
                .map(Good::getPrice)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
        order.setTotalPrice(price);
        order.setGoods(orderDto.getOrderedGoods());
        return order;
    }

    public OrderDto convertOrderToOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setOrderedGoods(order.getGoods());
        orderDto.setTotalPrice(order.getTotalPrice());
        orderDto.setUserId(order.getUser().getId());
        return orderDto;
    }
}
