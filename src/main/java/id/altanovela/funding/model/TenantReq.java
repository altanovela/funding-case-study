package id.altanovela.funding.model;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class TenantReq {
    
    @NotBlank
    @Size(max = 30, min = 3)
    private String name;
    
    @DecimalMin(value = "0.0", message = "Amount must be at least 0")
    private BigDecimal initialBalance;
}
