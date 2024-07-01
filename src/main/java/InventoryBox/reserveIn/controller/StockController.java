package InventoryBox.reserveIn.controller;

import InventoryBox.reserveIn.dto.StockDto;
import InventoryBox.reserveIn.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    //재고 조회
    @GetMapping("/stock")
    public Page<StockDto> getAllStocks(
            @PageableDefault(size = 13,sort = "createAt",direction = Sort.Direction.DESC)Pageable pageable) {
       return stockService.getAllStocks(pageable);

    }
}
