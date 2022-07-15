package id.altanovela.funding.model.mapper;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import id.altanovela.funding.dao.entities.FundingActivity;
import id.altanovela.funding.model.ActivityReq;

public class ActivityReqMapper {
    
    public static FundingActivity mapper(ActivityReq activityReq) {
        FundingActivity activity = new FundingActivity();
        activity.setUser(activityReq.getUser());
        activity.setTenant(activityReq.getTenant());
        activity.setTransactionTime(new Date());
        activity.setType(StringUtils.upperCase(activityReq.getType()));
        activity.setAmount(activityReq.getAmount());
        return activity;
    }
}
