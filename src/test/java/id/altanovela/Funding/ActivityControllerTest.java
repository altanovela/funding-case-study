package id.altanovela.Funding;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
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

import id.altanovela.funding.controller.ActivityController;
import id.altanovela.funding.dao.entities.FundingActivity;
import id.altanovela.funding.dao.entities.FundingTenant;
import id.altanovela.funding.dao.entities.User;
import id.altanovela.funding.model.ActivityDto;
import id.altanovela.funding.model.ActivityReq;
import id.altanovela.funding.services.ActivityService;
import id.altanovela.funding.services.TenantService;
import id.altanovela.funding.services.UserService;
import id.altanovela.funding.utils.DateUtils;
import id.altanovela.funding.utils.JsonUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(ActivityController.class)
public class ActivityControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @MockBean
    private TenantService tenantService;
    
    @MockBean
    private ActivityService activityService;
    
    @Test
    public void getAllActivityNoFilter_thenSuccess() throws Exception {
        // Mocking Data
        List<ActivityDto> lists = new ArrayList<ActivityDto>();
        lists.add(mockActivityDto("Desa Purwasari", "Wawan Setiawan"     , "DONATE"   , BigDecimal.valueOf(1000000), "17/08/2020"));
        lists.add(mockActivityDto("Desa Purwasari", "Teguh Sudibyantoro" , "DONATE"   , BigDecimal.valueOf(5000000), "18/08/2020"));
        lists.add(mockActivityDto("Desa Purwasari", "Joko Widodo"        , "WITHDRAW" , BigDecimal.valueOf(2000000), "30/09/2020"));
        lists.add(mockActivityDto("Desa Purwasari", "Joko Widodo"        , "DONATE"   , BigDecimal.valueOf(1000000), "10/11/2020"));
        lists.add(mockActivityDto("Desa Purwasari", "Wawan Setiawan"     , "DONATE"   , BigDecimal.valueOf(5000000), "01/12/2020"));
        lists.add(mockActivityDto("Desa Purwasari", "Teguh Sudibyantoro" , "WITHDRAW" , BigDecimal.valueOf(2000000), "01/12/2020"));
        
        // Registering Mock Service
        Mockito
            .when(activityService.findActivities(10, 0, null, null, null, null))
            .thenReturn(new PageImpl<ActivityDto>(lists));
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/v1/funding/activities")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", Matchers.equalTo(Boolean.TRUE)))
            .andExpect(jsonPath("$.data.*" , hasSize(6)));
    }
    
    @Test
    public void addActivityDonateInitialBalanceZero_thenSuccess() throws Exception {
        // Mocking Data
        ActivityReq payload = new ActivityReq();
        payload.setUserId(MOCK_USER_ID);
        payload.setTenantId(MOCK_TENANT_ID);
        payload.setAmount(BigDecimal.valueOf(1000000));
        payload.setType("DONATE");
        
        FundingActivity activity = mockActivity(payload.getType(), payload.getAmount());
        
        // Registering Mock Service
        Mockito
            .when(userService.findById(MOCK_USER_ID))
            .thenReturn(mockUser());
        Mockito
            .when(tenantService.findById(MOCK_TENANT_ID))
            .thenReturn(mockTenant(BigDecimal.ZERO));
        Mockito
            .when(activityService.addActivity(org.mockito.ArgumentMatchers.any(FundingActivity.class)))
            .thenReturn(activity);
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/api/v1/funding/activities")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJsonString(payload))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", Matchers.equalTo(Boolean.TRUE)));
    }
    
    /**
     * Failed due to :
     * Donate Rp. -1.000.000, Less than Zero.
     * Donate and Withdraw should be at least Rp. 1
     * 
     * @throws {@link Exception}
     */
    @Test
    public void addActivityDonateNegativeAmount_thenFailed() throws Exception {
        // Mocking Data
        ActivityReq payload = new ActivityReq();
        payload.setUserId(MOCK_USER_ID);
        payload.setTenantId(MOCK_TENANT_ID);
        payload.setAmount(BigDecimal.valueOf(-1000000));
        payload.setType("DONATE");
        
        FundingActivity activity = mockActivity(payload.getType(), payload.getAmount());
        
        // Registering Mock Service
        Mockito
            .when(userService.findById(MOCK_USER_ID))
            .thenReturn(mockUser());
        Mockito
            .when(tenantService.findById(MOCK_TENANT_ID))
            .thenReturn(mockTenant(BigDecimal.ZERO));
        Mockito
            .when(activityService.addActivity(org.mockito.ArgumentMatchers.any(FundingActivity.class)))
            .thenReturn(activity);
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/api/v1/funding/activities")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJsonString(payload))
            )
            .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.success", Matchers.equalTo(Boolean.FALSE)))
            .andExpect(jsonPath("$.errors[*].code", Matchers.contains("amount")));
    }
    
    /**
     * Failed due to :
     * Withdraw Rp. 1.000.000, but Tenant Balance is Rp. 500.000
     * 
     * @throws {@link Exception}
     */
    @Test
    public void addActivityWithdrawAmountExceedTenantBalance_thenFailed() throws Exception {
        // Mocking Data
        ActivityReq payload = new ActivityReq();
        payload.setUserId(MOCK_USER_ID);
        payload.setTenantId(MOCK_TENANT_ID);
        payload.setAmount(BigDecimal.valueOf(1000000));
        payload.setType("WITHDRAW");
        
        FundingActivity activity = mockActivity(payload.getType(), payload.getAmount());
        
        // Registering Mock Service
        Mockito
            .when(userService.findById(MOCK_USER_ID))
            .thenReturn(mockUser());
        Mockito
            .when(tenantService.findById(MOCK_TENANT_ID))
            .thenReturn(mockTenant(BigDecimal.valueOf(500000)));
        Mockito
            .when(activityService.addActivity(org.mockito.ArgumentMatchers.any(FundingActivity.class)))
            .thenReturn(activity);
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/api/v1/funding/activities")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJsonString(payload))
            )
            .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.success", Matchers.equalTo(Boolean.FALSE)))
            .andExpect(jsonPath("$.errors[*].code", Matchers.contains("amount")));
    }
    
    /*
     * === Mock Test Data Preparation ===
     */
    private static Long MOCK_USER_ID     = 11L;
    private static Long MOCK_TENANT_ID   = 22L;
    private static Long MOCK_ACTIVITY_ID = 33L;
    
    private ActivityDto mockActivityDto(
        String tenantName, String userName, String type, BigDecimal amount, String transactionTime
    ) {
        return new ActivityDto(
            tenantName, userName, type, amount, toDate(transactionTime)
        );
    }
    
    /*
     * Assume Activities only come in One Tenant, and from One User
     */
    private FundingActivity mockActivity(String type, BigDecimal amount) {
        FundingActivity activity = new FundingActivity();
        activity.setId(MOCK_ACTIVITY_ID);
        activity.setTenant(mockTenant(amount));
        activity.setUser(mockUser());
        activity.setTransactionTime(new Date());
        activity.setType(type);
        activity.setAmount(amount);
        return activity;
    }
    
    private User mockUser() {
        User user = new User();
        user.setId(MOCK_USER_ID);
        user.setName("Wawan Setiawan");
        user.setDateOfBirth(toDate("10/01/1990"));
        user.setFullAdress("Kompleks Asia Serasi No 100");
        return user;
    }
    
    private FundingTenant mockTenant(BigDecimal balance) {
        FundingTenant tenant = new FundingTenant();
        tenant.setId(MOCK_TENANT_ID);
        tenant.setName("Desa Purwasari");
        tenant.setFundingBalance(balance);
        return tenant;
    }
    
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    
    private Date toDate(String date) {
        return DateUtils.toDate(DATE_PATTERN, date);
    }
}
