package id.altanovela.funding.model.mapper;

import id.altanovela.funding.dao.entities.FundingTenant;
import id.altanovela.funding.model.TenantReq;

public class TenantReqMapper {
    
    public static FundingTenant mapper(TenantReq tenantReq) {
        FundingTenant tenant = new FundingTenant();
        tenant.setName(tenantReq.getName());
        tenant.setFundingBalance(tenantReq.getInitialBalance());
        return tenant;
    }
}
