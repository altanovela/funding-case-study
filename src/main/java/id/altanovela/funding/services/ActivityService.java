package id.altanovela.funding.services;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import id.altanovela.funding.dao.entities.FundingActivity;
import id.altanovela.funding.dao.repositories.FundingActivityRepository;
import id.altanovela.funding.model.ActivityDto;
import id.altanovela.funding.services.base.BaseService;
import id.altanovela.funding.utils.DateUtils;

@Service
public class ActivityService extends BaseService {
    
    @Autowired
    private FundingActivityRepository fundingActivityRepository;
    
    /**
     * <p>
     * Find Activity with Tenant Id, User Id, and Date (Transaction Time)
     * </p>
     * @param  {@link Integer}
     * @param  {@link Integer}
     * @param  {@link Long}
     * @param  {@link Long}
     * @param  {@link Date}
     * @param  {@link Date}
     * @return {@link Page}<{@link ActivityDto}>
     */
    public Page<ActivityDto> findActivities(Integer pageSize, Integer page, Long tenantId, Long userId, Date startDate, Date endDate){
        return fundingActivityRepository.findActivities(
            tenantId, userId, DateUtils.bod(startDate), DateUtils.eod(endDate), 
            PageRequest.of(page, pageSize, Sort.by("id").descending())
        );
    }
    
    /**
     * <p>
     * Add Activity, return {@link FundingActivity} with Id, 
     * and <b>null</b> if it's fail.
     * </p>
     * @param  {@link FundingActivity}
     * @return {@link FundingActivity}
     */
    @Transactional
    public FundingActivity addActivity(FundingActivity activity) {
        return geto (
            fundingActivityRepository.save(activity)
        );
    }
}
