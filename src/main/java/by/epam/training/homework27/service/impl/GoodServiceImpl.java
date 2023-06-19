package by.epam.training.homework27.service.impl;

import by.epam.training.homework27.dao.GoodDao;
import by.epam.training.homework27.dto.GoodDto;
import by.epam.training.homework27.model.Good;
import by.epam.training.homework27.service.GoodService;
import by.epam.training.homework27.converter.GoodConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toList;

@Service
public class GoodServiceImpl implements GoodService {

    private final GoodDao goodDao;
    private final GoodConverter goodConverter;


    public GoodServiceImpl(GoodDao goodDao, GoodConverter goodConverter) {
        this.goodDao = goodDao;
        this.goodConverter = goodConverter;
    }

    @Override
    @Transactional(readOnly = true)
    public GoodDto getById(long id) {
        Good good = goodDao.getById(id)
                .orElseThrow(() -> new NoSuchElementException("Good by such Id was not found"));
        return goodConverter.convertGoodToGoodDto(good);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GoodDto> getAll() {
        return goodDao.getAll().stream()
                .map(goodConverter::convertGoodToGoodDto)
                .collect(toList());
    }
}
