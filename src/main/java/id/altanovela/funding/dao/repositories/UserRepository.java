package id.altanovela.funding.dao.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import id.altanovela.funding.dao.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Page<User> findByNameContaining(String name, Pageable pageable);
    
}
