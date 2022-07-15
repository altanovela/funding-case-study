package id.altanovela.funding.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Value;

@Value
public class ActivityDto {

    private String tenantName;
    
    private String userName;
    
    private String type;
    
    private BigDecimal amount;
    
    @JsonFormat(
        shape    = JsonFormat.Shape.STRING,
        timezone = "UTC",
        pattern  = "yyyy-MM-dd HH:mm:ss z"
    )
    private Date transactionTime;
}
