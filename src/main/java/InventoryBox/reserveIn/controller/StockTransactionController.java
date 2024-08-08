package InventoryBox.reserveIn.controller;


import InventoryBox.reserveIn.dto.StockTransactionDto;
import InventoryBox.reserveIn.service.StockTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class StockTransactionController {

    private final StockTransactionService stockTransactionService;

    @Autowired
    public StockTransactionController(StockTransactionService stockTransactionService) {
        this.stockTransactionService = stockTransactionService;
    }

    //입출고 내역 저장
    @PostMapping("/stockInOut")
    public ResponseEntity<StockTransactionDto> addStockTransaction(@RequestBody StockTransactionDto stockTransactionDto) {
        StockTransactionDto stockTransaction = stockTransactionService.addInOut(stockTransactionDto);
        return ResponseEntity.ok(stockTransaction);
    }

    //입출고 내역 조회
    @GetMapping("/stockInOut")
    public Page<StockTransactionDto> getStockTransaction(
            @PageableDefault(size = 13, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable){
                return stockTransactionService.getAllTransaction(pageable);
    }

    //입출고 내역 수정
    @PutMapping("/stockInOut/{id}")
    public ResponseEntity<StockTransactionDto> updateTransaction(@RequestBody StockTransactionDto stockTransactionDto,
                                                                 @PathVariable Long id) {
        StockTransactionDto stockTransaction = stockTransactionService.updateTransaction(stockTransactionDto, id);
        return ResponseEntity.ok(stockTransaction);
    }

    //입출고 내역 삭제
    @DeleteMapping("/stockInOut/{id}")
    public ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        stockTransactionService.deleteTransaction(id);
        return ResponseEntity.ok("삭제");
    }

    //ID별 입출력 내역 조회
    @GetMapping("stockInOut/{id}")
    public ResponseEntity<?> findByStockTranWithId(@PathVariable Long id) {
        stockTransactionService.findStById(id);
        return ResponseEntity.ok("조회");
    }
}
