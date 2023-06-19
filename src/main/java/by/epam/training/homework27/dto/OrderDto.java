package by.epam.training.homework27.dto;

import by.epam.training.homework27.model.Good;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderDto {

    private long id;
    private long userId;
    private BigDecimal totalPrice;
    private List<Good> orderedGoods = new ArrayList<>();

    public OrderDto() {
    }

    public OrderDto(long id, long userId, BigDecimal totalPrice, List<Good> orderedGoods) {
        this.id = id;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderedGoods = orderedGoods;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Good> getOrderedGoods() {
        return orderedGoods;
    }

    public void setOrderedGoods(List<Good> orderedGoods) {
        this.orderedGoods = orderedGoods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return id == orderDto.id && userId == orderDto.userId && Objects.equals(totalPrice, orderDto.totalPrice) && Objects.equals(orderedGoods, orderDto.orderedGoods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, totalPrice, orderedGoods);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalPrice=" + totalPrice +
                ", orderedGoods=" + orderedGoods +
                '}';
    }
}
