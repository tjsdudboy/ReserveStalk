package InventoryBox.reserveIn.service;

import InventoryBox.reserveIn.dto.StockDto;
import InventoryBox.reserveIn.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    //ToDo 재고 전체 조회
    public Page<StockDto> getAllStocks(Pageable pageable) {
        return stockRepository.findAll(pageable)
                .map(StockDto::toDto);
    }


    //ToDo 카테고리별 재고 조회

}
