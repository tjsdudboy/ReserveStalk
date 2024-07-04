package InventoryBox.reserveIn.repository;

import InventoryBox.reserveIn.entity.Product;
import InventoryBox.reserveIn.entity.category.Category1;
import InventoryBox.reserveIn.entity.category.Category2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategory1AndCategory2(Pageable pageable, String category1, String category2);
    Page<Product> findByCategory1 (Pageable pageable, String category1);
    Page<Product> findByCategory2 (Pageable pageable, String category2);


    void deleteAllByIdIn(List<Long> id);
}
