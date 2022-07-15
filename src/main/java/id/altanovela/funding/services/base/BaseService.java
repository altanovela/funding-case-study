package id.altanovela.funding.services.base;

import id.altanovela.funding.dao.entities.base.BaseEntity;

public class BaseService {
    
    /**
     * Get Entity if it has Id
     * @param  <T extends {@link BaseEntity}>
     * @param  entity
     * @return T
     */
    public <T extends BaseEntity> T geto(T entity) {
        if(null != entity && entity.getId() > 0) {
            return entity;
        }
        return null;
    }
}
