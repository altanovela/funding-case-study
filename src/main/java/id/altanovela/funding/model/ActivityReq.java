package id.altanovela.funding.model;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

import id.altanovela.funding.dao.entities.FundingTenant;
import id.altanovela.funding.dao.entities.User;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ActivityReq {
    
    @NotNull
    private Long userId;
    
    @NotNull
    private Long tenantId;
    
    @NotNull
    @DecimalMin(value = "1.0", message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    @Pattern(regexp="(?i)(DONATE|WITHDRAW)", message="Type is must be DONATE or WITHDRAW")
    private String type;
    
    @JsonIgnore
    private FundingTenant tenant;
    
    @JsonIgnore
    private User user;
}
