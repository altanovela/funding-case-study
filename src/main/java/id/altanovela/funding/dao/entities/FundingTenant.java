package id.altanovela.funding.dao.entities;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import id.altanovela.funding.dao.entities.base.BaseEntity;
import lombok.Data;

@Data
@Entity
@Table(name = "t_fund_funding_tenant")
public class FundingTenant extends BaseEntity {
    
    private String name;
    
    private BigDecimal fundingBalance;
}
