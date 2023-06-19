package by.epam.training.homework27.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class GoodDto {

    private long id;
    private String title;
    private BigDecimal price;

    public GoodDto() {
    }

    public GoodDto(long id, String title, BigDecimal price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodDto goodDto = (GoodDto) o;
        return id == goodDto.id && Objects.equals(title, goodDto.title) && Objects.equals(price, goodDto.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price);
    }

    @Override
    public String toString() {
        return "GoodDto{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                '}';
    }
}
