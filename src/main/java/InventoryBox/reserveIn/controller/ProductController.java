package InventoryBox.reserveIn.controller;

import InventoryBox.reserveIn.dto.ProductDto;
import InventoryBox.reserveIn.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //제품 조회
    @GetMapping("/products")
    public Page<ProductDto> getProducts(
            @PageableDefault (size = 13, sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "category1", required = false) String category1,
            @RequestParam(value = "category2", required = false) String category2
    ){
        return productService.findProductById(pageable, category1, category2);
    }

    //Id별 제품 조회
    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> findProductById(@PathVariable Long id) {
        ProductDto product =  productService.findProductById(id);
        return ResponseEntity.ok(product);
    }

    //제품 저장
    @PostMapping("/products")
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
        ProductDto product = productService.saveProduct(productDto);
        log.info("제품 저장");
        return ResponseEntity.ok(product);
    }

    //제품 수정
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto,
                                                    @PathVariable Long id) {
        try{
            ProductDto product = productService.updateProduct(productDto, id);
            return ResponseEntity.ok(product);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    //제품 삭제
    @DeleteMapping("/product")
    public ResponseEntity<?> deleteProduct(@RequestBody List<Long> id){
        try{
            productService.deleteProduct(id);
            return ResponseEntity.ok("삭제완료");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
