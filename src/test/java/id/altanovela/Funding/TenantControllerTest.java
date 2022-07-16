package id.altanovela.Funding;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import id.altanovela.funding.controller.TenantController;
import id.altanovela.funding.dao.entities.FundingTenant;
import id.altanovela.funding.model.TenantReq;
import id.altanovela.funding.services.TenantService;
import id.altanovela.funding.utils.JsonUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(TenantController.class)
public class TenantControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    TenantService tenantService;
    
    @Test
    public void getAllTenantNoFilter_thenSuccess() throws Exception {
        // Mocking Data
        List<FundingTenant> lists = new ArrayList<FundingTenant>();
        lists.add(mockTenant("Desa Purwasari" , BigDecimal.ZERO));
        
        // Registering Mock Service
        Mockito
            .when(tenantService.findTenants(10, 0, ""))
            .thenReturn(new PageImpl<FundingTenant>(lists));
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/v1/funding/tenants")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", Matchers.equalTo(Boolean.TRUE)))
            .andExpect(jsonPath("$.data.*" , hasSize(1)));
    }
    
    @Test
    public void addTenant_thenSuccess() throws Exception {
        // Mocking Data
        TenantReq payload = new TenantReq();
        payload.setName("Desa Purwasari");
        payload.setInitialBalance(BigDecimal.ZERO);
        
        FundingTenant tenant = new FundingTenant();
        tenant.setId(1L);
        tenant.setName(payload.getName());
        tenant.setFundingBalance(payload.getInitialBalance());
        
        // Registering Mock Service
        Mockito
            .when(tenantService.addTenant(tenant))
            .thenReturn(tenant);
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/api/v1/funding/tenants")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJsonString(payload))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", Matchers.equalTo(Boolean.TRUE)));
    }
    
    /**
     * Failed due to :
     * Tenant Name less than 3 Character, eg. `De`
     * 
     * @throws {@link Exception}
     */
    @Test
    public void addTenantNameLessThan3Char_thenFailed() throws Exception {
        // Mocking Data
        TenantReq payload = new TenantReq();
        // -- should trigger Name validation, less than 3 character
        payload.setName("De");
        payload.setInitialBalance(BigDecimal.ZERO);
        
        FundingTenant tenant = new FundingTenant();
        tenant.setId(1L);
        tenant.setName(payload.getName());
        tenant.setFundingBalance(payload.getInitialBalance());
        
        // Registering Mock Service
        Mockito
            .when(tenantService.addTenant(tenant))
            .thenReturn(tenant);
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/api/v1/funding/tenants")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJsonString(payload))
            )
            .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.success"        , Matchers.equalTo(Boolean.FALSE)))
            .andExpect(jsonPath("$.errors[*].code" , Matchers.contains("name")));
    }
    
    /**
     * Failed due to :
     * Tenant Initial Balance less than Zero, eg. `-1000`
     * 
     * @throws {@link Exception}
     */
    @Test
    public void addTenantInvalidInitialBalance_thenFailed() throws Exception {
        // Mocking Data
        TenantReq payload = new TenantReq();
        payload.setName("Desa Purwasari");
        // -- should trigger Balance validation, less than 0
        payload.setInitialBalance(BigDecimal.valueOf(-1000));
        
        FundingTenant tenant = new FundingTenant();
        tenant.setId(1L);
        tenant.setName(payload.getName());
        tenant.setFundingBalance(payload.getInitialBalance());
        
        // Registering Mock Service
        Mockito
            .when(tenantService.addTenant(tenant))
            .thenReturn(tenant);
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/api/v1/funding/tenants")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJsonString(payload))
            )
            .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.success"        , Matchers.equalTo(Boolean.FALSE)))
            .andExpect(jsonPath("$.errors[*].code" , Matchers.contains("initialBalance")));
    }
    
    /*
     * === Mock Test Data Preparation ===
     */
    
    private FundingTenant mockTenant(String name, BigDecimal balance) {
        FundingTenant tenant = new FundingTenant();
        tenant.setName(name);
        tenant.setFundingBalance(balance);
        return tenant;
    }
}
