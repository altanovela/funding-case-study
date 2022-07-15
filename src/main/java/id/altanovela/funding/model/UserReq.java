package id.altanovela.funding.model;

import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.time.DateUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter @Getter
public class UserReq {
    
    @NotBlank
    @Size(max = 30, min = 3)
    private String name;
    
    private String dateOfBirth;
    
    @Size(max = 300)
    private String fullAdress;
    
    @JsonIgnore
    private Date dateOfBirthDate;
    
    @AssertTrue(message = "Date is invalid, date format dd/MM/yyyy")
    private boolean isDateOfBirth() {
        try {
            this.dateOfBirthDate = DateUtils.parseDateStrictly(dateOfBirth, "dd/MM/yyyy");
            if(dateOfBirthDate.after(new Date())) {
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
