package InventoryBox.reserveIn.repository;

import InventoryBox.reserveIn.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByName(String username);


    //아이디 중복 체크
    boolean existsByUsername(String username);
}
