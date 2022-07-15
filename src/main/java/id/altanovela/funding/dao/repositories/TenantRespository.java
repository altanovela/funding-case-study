package id.altanovela.funding.dao.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import id.altanovela.funding.dao.entities.FundingTenant;

public interface TenantRespository extends JpaRepository<FundingTenant, Long>{
    
    Page<FundingTenant> findByNameContaining(String name, Pageable pageable);
    
}
