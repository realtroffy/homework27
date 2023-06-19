package by.epam.training.homework27.controller;

import by.epam.training.homework27.dto.GoodDto;
import by.epam.training.homework27.service.GoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/goods")
public class GoodController {

    private final GoodService goodService;

    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoodDto> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(goodService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<GoodDto>> getAll() {
        List<GoodDto> goods = goodService.getAll();
        return ResponseEntity.ok(goods);
    }
}
