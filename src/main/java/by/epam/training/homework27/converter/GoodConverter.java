package by.epam.training.homework27.converter;

import by.epam.training.homework27.dto.GoodDto;
import by.epam.training.homework27.model.Good;
import org.springframework.stereotype.Component;

@Component
public class GoodConverter{

    public GoodDto convertGoodToGoodDto(Good good) {
        GoodDto goodDto = new GoodDto();
        goodDto.setId(good.getId());
        goodDto.setTitle(good.getTitle());
        goodDto.setPrice(good.getPrice());
        return goodDto;
    }
}
