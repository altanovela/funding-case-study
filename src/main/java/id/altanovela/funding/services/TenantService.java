package id.altanovela.funding.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import id.altanovela.funding.dao.entities.FundingTenant;
import id.altanovela.funding.dao.repositories.TenantRespository;
import id.altanovela.funding.services.base.BaseService;

@Service
public class TenantService extends BaseService {
    
    @Autowired
    TenantRespository tenantRepository;
    
    /**
     * <p>
     * Find Tenant contains a Name
     * </p>
     * @param  {@link Integer}
     * @param  {@link Integer}
     * @param  {@link String}
     * @return {@link Page}<{@link FundingTenant}>
     */
    public Page<FundingTenant> findTenants(Integer pageSize, Integer page, String name){
        return tenantRepository.findByNameContaining(
            name, PageRequest.of(page, pageSize, Sort.by("id").descending())
        );
    }
    
    /**
     * <p>
     * Find By Tenant Id
     * </p>
     * @param  {@link Long}
     * @return {@link FundingTenant} 
     */
    public FundingTenant findById(Long id) {
        return tenantRepository.findById(id).orElse(null);
    }
    
    /**
     * <p>
     * Add Tenant, return {@link FundingTenant} with Id, 
     * and <b>null</b> if it's fail.
     * </p>
     * @param  {@link FundingTenant}
     * @return {@link FundingTenant}
     */
    public FundingTenant addTenant(FundingTenant tenant) {
        return geto (
            tenantRepository.save(tenant)
        );
    }
}
