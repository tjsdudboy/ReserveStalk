package InventoryBox.reserveIn.service;

import InventoryBox.reserveIn.dto.StockTransactionDto;
import InventoryBox.reserveIn.entity.AllEnum.TransactionType;
import InventoryBox.reserveIn.entity.Product;
import InventoryBox.reserveIn.entity.Stock;
import InventoryBox.reserveIn.entity.StockTransaction;
import InventoryBox.reserveIn.entity.Users;
import InventoryBox.reserveIn.repository.ProductRepository;
import InventoryBox.reserveIn.repository.StockRepository;
import InventoryBox.reserveIn.repository.StockTransactionRepository;
import InventoryBox.reserveIn.repository.UserRepository;
import jakarta.transaction.Transaction;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockTransactionService {

    private final StockTransactionRepository stockTransactionRepository;
    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    //ToDo 입고 등록
    @Transactional
    public StockTransactionDto addInOut(StockTransactionDto stockTransactionDto) {
        int newQuantity;
        //ToDo 사용자 존재 확인
        String Username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = userRepository.findByUsername(Username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));
        System.out.println(" 사용자 확인~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

        if (stockTransactionDto.getProductId() == null) {
            throw new IllegalArgumentException("제품 ID가 null입니다.");
        }

        //TODo 제품 유무 확인
        Product product = productRepository.findById(stockTransactionDto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("제품 없음"));
        System.out.println(" 제품 확인~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");


//        Stock stock = stockRepository.findById(stockTransactionDto.getStockId())
//                .orElseThrow(() -> new IllegalArgumentException("재고 없음"));
        Stock stock = new Stock();
        //ToDo 입출고 처리
        if (stockTransactionDto.getTransactionType() == TransactionType.IN) {
            newQuantity  = stock.getCurrentStock() + stockTransactionDto.getQuantity();
        } else if (stockTransactionDto.getTransactionType() == TransactionType.OUT) {
            //ToDo 재고 유무 확인
            stockRepository.findById(stockTransactionDto.getStockId())
                    .orElseThrow(() -> new IllegalArgumentException("재고 없음"));
            newQuantity = stock.getCurrentStock() - stockTransactionDto.getQuantity();
          if(newQuantity < 0 ){
              throw new IllegalArgumentException("재고가 부족합니다.");
          }
        }else {
            throw new IllegalArgumentException("transactionType 이 잘못되었습니다.");
        }
        stock.setCurrentStock(newQuantity);
        stockRepository.save(stock);

        //ToDo 입출고 기록 저장
        StockTransaction stockTransaction = StockTransaction.builder()
                .product(product)
                .transactionType(stockTransactionDto.getTransactionType())
                .quantity(stockTransactionDto.getQuantity())
                .description(stockTransactionDto.getDescription())
                .stock(stock)
                .users(user)
                .build();

        stockTransaction = stockTransactionRepository.save(stockTransaction);

        return StockTransactionDto.toDto(stockTransaction);
    }

    //ToDo 입출고 기록 조회
    public Page<StockTransactionDto> getAllTransaction(Pageable pageable) {
        return stockTransactionRepository.findStockTransactionWithProductName(pageable);
    }

    //ToDo 입출고 수정
    @Transactional
    public StockTransactionDto updateTransaction(StockTransactionDto stockTransactionDto,Long Id) {
        StockTransaction stockTransaction = stockTransactionRepository.findById(stockTransactionDto.getStockId())
                .orElseThrow(() -> new IllegalArgumentException("ID 없음"));
        Users users = userRepository.findByUsername(stockTransactionDto.getUsername()).orElseThrow();
        stockTransaction.setTransactionType(stockTransactionDto.getTransactionType());
        stockTransaction.setQuantity(stockTransaction.getQuantity());
        stockTransaction.setDescription(stockTransaction.getDescription());
        stockTransaction.setUsers(users);
        return StockTransactionDto.toDto(stockTransaction);
    }

    //ToDo 입출고 삭제
    public void deleteTransaction(Long id){
        if (!stockTransactionRepository.existsById(id)) {
            throw new IllegalArgumentException("입출고 이력 없음");
        }
        stockTransactionRepository.deleteById(id);
    }

    //ToDo 입출고 ID별 조회
    public StockTransactionDto findStById(Long id) {
        return stockTransactionRepository.findById(id)
                .map(StockTransactionDto::toDto)
                .orElseThrow();
    }
}
