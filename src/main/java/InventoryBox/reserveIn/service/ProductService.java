package InventoryBox.reserveIn.service;

import InventoryBox.reserveIn.dto.ProductDto;
import InventoryBox.reserveIn.entity.Product;
import InventoryBox.reserveIn.entity.Users;
import InventoryBox.reserveIn.entity.category.Category1;
import InventoryBox.reserveIn.entity.category.Category2;
import InventoryBox.reserveIn.repository.ProductRepository;
import InventoryBox.reserveIn.repository.UserRepository;
import jakarta.persistence.Temporal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    //ToDo 제품 조회
    public Page<ProductDto> findProductById(Pageable pageable, String category1, String category2) {
        Category1 cat1 = category1 != null ? Category1.valueOf(category1) : null;
        Category2 cat2 = category2 != null ? Category2.valueOf(category2) : null;

        //cat1, cat2에 값이 있으면 해당 값을 조회, 하나라도 없으면 전체 조회
        Page<Product> products = cat1 != null && cat2 != null ?
                productRepository.findByCategory1AndCategory2(pageable, category1, category2) : productRepository.findAll(pageable);

        return products.map(ProductDto::toDto);
    }

    //ToDo 제품 Id별 조회
    public ProductDto findProductById(Long id) {
        return productRepository.findById(id).map(ProductDto::toDto).orElseThrow(
                () -> new IllegalArgumentException("아이디 없음"));
    }

    //ToDo 제품 등록
    @Transactional
    public ProductDto saveProduct(ProductDto productDto) {
        //ToDo 유저이름 가져오기
        Users users = userRepository.findByName(productDto.getName()).orElseThrow(
                () -> new IllegalArgumentException("유저 없음"));

        Product product = Product.builder()
                .name(productDto.getName())
                .price(productDto.getPrice())
                .unitPrice(productDto.getUnitPrice())
                .description(productDto.getDescription())
                .category1(productDto.getCategory1())
                .category2(productDto.getCategory2())
                .users(users)
                .build();
        product = productRepository.save(product);

        return ProductDto.toDto(product);
    }

    //ToDo 제품 수정
    @Transactional
    public ProductDto updateProduct(ProductDto productDto, Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디 없음"));
//        product.setName(productDto.getName());//제품 이름 변경 불가
        product.setPrice(productDto.getPrice());
        product.setUnitPrice(productDto.getUnitPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory1(productDto.getCategory1());
        product.setCategory2(productDto.getCategory2());

        return ProductDto.toDto(product);
    }

    //ToDo 제품 삭제
    @Transactional
    public void deleteProduct(List<Long> id){
        productRepository.deleteAllByIdIn(id);
    }

}
