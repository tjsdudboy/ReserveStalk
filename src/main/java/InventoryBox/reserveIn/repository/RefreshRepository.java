package InventoryBox.reserveIn.repository;

import InventoryBox.reserveIn.entity.login.Refresh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {
    //refresh Token 존재유무확인
    Boolean existsByRefresh(String refresh);

    @Transactional
    void deleteAllByRefresh(String refresh);
}
