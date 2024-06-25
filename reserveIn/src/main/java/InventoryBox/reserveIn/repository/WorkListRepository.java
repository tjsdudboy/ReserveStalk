package InventoryBox.reserveIn.repository;

import InventoryBox.reserveIn.entity.WorkList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkListRepository extends JpaRepository<WorkList, Long> {
}
