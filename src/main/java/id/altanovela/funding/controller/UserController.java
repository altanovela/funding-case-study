package id.altanovela.funding.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import id.altanovela.funding.base.annotation.FundingRest;
import id.altanovela.funding.base.components.HttpResponse;
import id.altanovela.funding.base.dto.ResponseDto;
import id.altanovela.funding.dao.entities.User;
import id.altanovela.funding.model.UserReq;
import id.altanovela.funding.model.mapper.UserReqMapper;
import id.altanovela.funding.services.UserService;

@FundingRest("/api/v1/funding/users")
public class UserController {
    
    @Autowired
    UserService userService;
    
    @GetMapping
    public ResponseEntity<String> getUsers(
        @RequestParam(name = "pageSize" , defaultValue = "10") Integer size,
        @RequestParam(name = "page"     , defaultValue = "1" ) Integer page,
        @RequestParam(name = "name"     , defaultValue = ""  ) String name
    ) {
        
        return HttpResponse.success(
            new ResponseDto<User>(
                userService.findUsers(size, page < 1 ? 0 : --page, name)
            )
        );
    }
    
    @PostMapping
    public ResponseEntity<String> addUsers(
        @Valid @RequestBody UserReq payload
    ) {
        User result = userService.addUser(
            UserReqMapper.mapper(payload)
        );
        if(null != result) {
            return HttpResponse.success();
        }
        return HttpResponse.error();
    }
    
}
