package id.altanovela.funding.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import id.altanovela.funding.base.annotation.FundingRest;
import id.altanovela.funding.base.components.HttpResponse;
import id.altanovela.funding.base.dto.ResponseDto;
import id.altanovela.funding.dao.entities.FundingTenant;
import id.altanovela.funding.model.TenantReq;
import id.altanovela.funding.model.mapper.TenantReqMapper;
import id.altanovela.funding.services.TenantService;

@FundingRest("/api/v1/funding/tenants")
public class TenantController {
    
    @Autowired
    private HttpResponse httpResponse;
    
    @Autowired
    private TenantService tenantService;
    
    @GetMapping
    public ResponseEntity<String> getTenants(
        @RequestParam(name = "pageSize" , defaultValue = "10") Integer size,
        @RequestParam(name = "page"     , defaultValue = "1" ) Integer page,
        @RequestParam(name = "name"     , defaultValue = ""  ) String name
    ){
        return httpResponse.success(
            new ResponseDto<FundingTenant>(
                tenantService.findTenants(size, page < 1 ? 0 : --page, name)
            )
        );
    }
    
    @PostMapping
    public ResponseEntity<String> addUsers(
        @Valid @RequestBody TenantReq payload
    ) {
        FundingTenant result = tenantService.addTenant(
            TenantReqMapper.mapper(payload)
        );
        if(null != result) {
            return httpResponse.success("Tenant successfully saved.");
        }
        return httpResponse.error("Tenant failed to saved.");
    }
}
