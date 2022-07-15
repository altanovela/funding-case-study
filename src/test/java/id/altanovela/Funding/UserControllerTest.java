package id.altanovela.Funding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import id.altanovela.funding.base.components.HttpResponse;
import id.altanovela.funding.base.dto.ResponseDto;
import id.altanovela.funding.controller.UserController;
import id.altanovela.funding.dao.entities.User;
import id.altanovela.funding.services.UserService;
import id.altanovela.funding.utils.DateUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    UserService userService;
    
    @MockBean
    HttpResponse httpResponse;
    
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    
    @Test
    public void getAllUserNoName_success() throws Exception {
        // Registering Mock Service
        Mockito
            .when(userService.findUsers(10, 0, ""))
            .thenReturn(mockPages());
        Mockito
            .when(httpResponse.success(any(String.class)))
            .thenReturn(
                new ResponseEntity<String>(mockResponseDto().toString(), HttpStatus.OK)
            );
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/v1/funding/users")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk());
    }
    
    /*
     * === Test Data Preparation ===
     */
    
    private User mockUser(String name, Date dateOfBirth, String address) {
        User user = new User();
        user.setName(name);
        user.setDateOfBirth(dateOfBirth);
        user.setFullAdress(address);
        return user;
    }
    
    private Date toDate(String date) {
        return DateUtils.toDate(DATE_PATTERN, date);
    }
    
    private Page<User> mockPages(){
        List<User> lists = new ArrayList<User>();
        lists.add(mockUser("Wawan Setiawan"     , toDate("10/01/1990"), "Kompleks Asia Serasi No 100"));
        lists.add(mockUser("Teguh Sudibyantoro" , toDate("10/02/1991"), "Jalan Pemekaran No 99"));
        lists.add(mockUser("Joko Widodo"        , toDate("10/03/1992"), "Dusun Pisang Rt 10 Rw 20"));
        return new PageImpl<User>(lists);
    }
    
    private ResponseDto<User> mockResponseDto(){
        return new ResponseDto<User>(mockPages());
    }
}
