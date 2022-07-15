package id.altanovela.funding.dao.repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import id.altanovela.funding.dao.entities.FundingActivity;
import id.altanovela.funding.model.ActivityDto;

public interface FundingActivityRepository extends JpaRepository<FundingActivity, Long>{
    
    @Query(value = " SELECT "
                 + "    new id.altanovela.funding.model.ActivityDto( "
                 + "        fa.tenant.name,"
                 + "        fa.user.name, "
                 + "        fa.type,"
                 + "        fa.amount, "
                 + "        fa.transactionTime "
                 + "    ) "
                 + " FROM "
                 + "    FundingActivity fa "
                 + " WHERE "
                 + "    ("
                 + "        :tenid IS NULL OR ("
                 + "            fa.tenant IS NOT NULL AND fa.tenant.id = :tenid "
                 + "        )"
                 + "    ) AND "
                 + "    ("
                 + "        :usrid IS NULL OR ("
                 + "            fa.user IS NOT NULL AND fa.user.id = :usrid "
                 + "        )"
                 + "    ) AND "
                 + "    ("
                 + "        cast(:edate as date) IS NULL OR fa.transactionTime <= :edate "
                 + "    ) AND "
                 + "    ("
                 + "        cast(:sdate as date) IS NULL OR fa.transactionTime >= :sdate "
                 + "    )")
    Page<ActivityDto> findActivities(
            @Param("tenid") Long tenantId,
            @Param("usrid") Long userId,
            @Param("sdate") Date startDate, 
            @Param("edate") Date endDate,
            Pageable pageable
    );
}
