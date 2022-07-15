package id.altanovela.funding.dao.entities;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import id.altanovela.funding.dao.entities.base.BaseEntity;
import lombok.Data;

@Data
@Entity
@Table(name = "t_fund_funding_activities")
public class FundingActivity extends BaseEntity {
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name="tenant_id")
    private FundingTenant tenant;
    
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;
    
    private String type;
    
    private BigDecimal amount;
    
    private Date transactionTime;
}
