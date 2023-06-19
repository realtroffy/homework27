package by.epam.training.homework27.service;

import by.epam.training.homework27.dto.GoodDto;

import java.util.List;

public interface GoodService {

    List<GoodDto> getAll();

    GoodDto getById(long id);

}
