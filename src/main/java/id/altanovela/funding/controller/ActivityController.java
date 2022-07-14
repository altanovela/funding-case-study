package id.altanovela.funding.controller;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import id.altanovela.funding.base.annotation.FundingRest;

@FundingRest("/api/funding/activities")
public class ActivityController {
    
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    
    @GetMapping("/")
    public ResponseEntity<String> getUsers(
            @RequestParam(name = "pageSize" , defaultValue = "10") Integer size,
            @RequestParam(name = "page"     , defaultValue = "1") Integer page,
            @RequestParam(name = "starDate" , required = false) 
                @DateTimeFormat(pattern = DATE_PATTERN) Date startDate,
            @RequestParam(name = "endDate"  , required = false) 
                @DateTimeFormat(pattern = DATE_PATTERN) Date endDate
    ){
        return null;
    }
}
