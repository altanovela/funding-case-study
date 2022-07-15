package id.altanovela.funding.controller;

import java.util.Date;

import javax.validation.Valid;

import static ir.cafebabe.math.utils.BigDecimalUtils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import id.altanovela.funding.base.annotation.FundingRest;
import id.altanovela.funding.base.components.HttpResponse;
import id.altanovela.funding.base.dto.ResponseDto;
import id.altanovela.funding.dao.entities.FundingActivity;
import id.altanovela.funding.dao.entities.FundingTenant;
import id.altanovela.funding.dao.entities.User;
import id.altanovela.funding.model.ActivityDto;
import id.altanovela.funding.model.ActivityReq;
import id.altanovela.funding.model.mapper.ActivityReqMapper;
import id.altanovela.funding.services.ActivityService;
import id.altanovela.funding.services.TenantService;
import id.altanovela.funding.services.UserService;

@FundingRest("/api/v1/funding/activities")
public class ActivityController {
    
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    
    @Autowired
    private HttpResponse httpResponse;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TenantService tenantService;
    
    @Autowired
    private ActivityService activityService;
    
    @GetMapping
    public ResponseEntity<String> getActivities(
        @RequestParam(name = "pageSize" , defaultValue = "10") Integer size,
        @RequestParam(name = "page"     , defaultValue = "1") Integer page,
        @RequestParam(name = "tenantId" , required = false) Long tenantId,
        @RequestParam(name = "userId"   , required = false) Long userId,
        @RequestParam(name = "starDate" , required = false) 
            @DateTimeFormat(pattern = DATE_PATTERN) Date startDate,
        @RequestParam(name = "endDate"  , required = false) 
            @DateTimeFormat(pattern = DATE_PATTERN) Date endDate
    ){
        return httpResponse.success(
            new ResponseDto<ActivityDto>(
                activityService.findActivities(size, page < 1 ? 0 : --page, tenantId, userId, startDate, endDate)
            )
        );
    }
    
    @PostMapping
    public ResponseEntity<String> addActivities(
        @Valid @RequestBody ActivityReq activityReq,
        BindingResult bindingResult
    ){
        /*
         * Additional Validation
         */
        User user = userService.findById(activityReq.getUserId());
        if(null == user) {
            bindingResult.addError(
                new ObjectError("userId", "User not found.")
            );
        }
        FundingTenant tenant = tenantService.findById(activityReq.getTenantId());
        if(null == tenant) {
            bindingResult.addError(
                new ObjectError("tenantId", "Tenant not found.")
            );
        } else {
            /*
             * Withdrawal can not bigger than balance. 
             */
            if(StringUtils.equalsIgnoreCase("WITHDRAW", activityReq.getType())) {
                if(is(tenant.getFundingBalance()).lt(activityReq.getAmount())){
                    bindingResult.addError(
                        new ObjectError("amount", "Amount is exceed current balance.")
                    );
                } else {
                    tenant.setFundingBalance(
                        tenant.getFundingBalance().subtract(activityReq.getAmount())
                    );
                }
            } else {
                tenant.setFundingBalance(
                    tenant.getFundingBalance().add(activityReq.getAmount())
                );
            }
        }
        
        if(bindingResult.hasErrors()) {
            return httpResponse.error(bindingResult);
        }
        
        // Set References
        activityReq.setUser(user);
        activityReq.setTenant(tenant);
        
        FundingActivity result = activityService.addActivity(
            ActivityReqMapper.mapper(activityReq)
        );
        if(null != result) {
            return httpResponse.success("Activity successfully saved.");
        }
        return httpResponse.error("Activity failed to saved.");
    }
}
