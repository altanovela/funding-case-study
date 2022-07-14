package id.altanovela.funding.model.mapper;

import id.altanovela.funding.dao.entities.User;
import id.altanovela.funding.model.UserReq;

public class UserReqMapper {
    
    public static User mapper(UserReq userReq) {
        User user = new User();
        user.setName(userReq.getName());
        user.setDateOfBirth(userReq.getDateOfBirthDate());
        user.setFullAdress(userReq.getFullAdress());
        return user;
    }
}
