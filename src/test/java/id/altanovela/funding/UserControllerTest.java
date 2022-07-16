package id.altanovela.funding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import id.altanovela.funding.controller.UserController;
import id.altanovela.funding.dao.entities.User;
import id.altanovela.funding.model.UserReq;
import id.altanovela.funding.services.UserService;
import id.altanovela.funding.utils.DateUtils;
import id.altanovela.funding.utils.JsonUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @MockBean
    UserService userService;
    
    @Test
    public void getAllUserNoFilter_thenSccess() throws Exception {
        // Mocking Data
        List<User> lists = new ArrayList<User>();
        lists.add(mockUser("Wawan Setiawan"     , "10/01/1990", "Kompleks Asia Serasi No 100"));
        lists.add(mockUser("Teguh Sudibyantoro" , "10/02/1991", "Jalan Pemekaran No 99"));
        lists.add(mockUser("Joko Widodo"        , "10/03/1992", "Dusun Pisang Rt 10 Rw 20"));
        
        // Registering Mock Service
        Mockito
            .when(userService.findUsers(10, 0, ""))
            .thenReturn(new PageImpl<User>(lists));
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/api/v1/funding/users")
                .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", Matchers.equalTo(Boolean.TRUE)))
            .andExpect(jsonPath("$.data.*" , hasSize(3)));
    }
    
    @Test
    public void addUser_thenSuccess() throws Exception {
        // Mocking Data
        UserReq payload = new UserReq();
        payload.setName("Wawan Setiawan");
        payload.setDateOfBirth("10/01/1990");
        payload.setFullAdress("Kompleks Asia Serasi No 100");
        
        User user = new User();
        user.setId(1L);
        user.setName(payload.getName());
        user.setDateOfBirth(toDate(payload.getDateOfBirth()));
        user.setFullAdress(payload.getFullAdress());
        
        // Registering Mock Service
        Mockito
            .when(userService.addUser(user))
            .thenReturn(user);
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/api/v1/funding/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJsonString(payload))
            )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", Matchers.equalTo(Boolean.TRUE)));
    }
    
    /**
     * Failed due to :
     * User Name less than 3 Character, eg. `Wa`
     * 
     * @throws {@link Exception}
     */
    @Test
    public void addUserNameLessThan3Char_thenFailed() throws Exception {
        // Mocking Data
        UserReq payload = new UserReq();
        // -- should trigger Name validation, less than 3 character
        payload.setName("Wa");
        payload.setDateOfBirth("10/01/1990");
        payload.setFullAdress("Kompleks Asia Serasi No 100");
        
        User user = new User();
        user.setId(1L);
        user.setName(payload.getName());
        user.setDateOfBirth(toDate(payload.getDateOfBirth()));
        user.setFullAdress(payload.getFullAdress());
        
        // Registering Mock Service
        Mockito
            .when(userService.addUser(user))
            .thenReturn(user);
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/api/v1/funding/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJsonString(payload))
            )
            .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.success"        , Matchers.equalTo(Boolean.FALSE)))
            .andExpect(jsonPath("$.errors[*].code" , Matchers.contains("name")));
    }
    
    /**
     * Failed due to :
     * User Date Of Birth is invalid, eg. Date of Birth is in the Future `10/01/19900`
     * 
     * @throws {@link Exception}
     */
    @Test
    public void addUserInvalidDateOfBirth_thenFailed() throws Exception {
        // Mocking Data
        UserReq payload = new UserReq();
        payload.setName("Wawan Setiawan");
        // -- should trigger Date validation, Date is in the future
        payload.setDateOfBirth("10/01/19900");
        payload.setFullAdress("Kompleks Asia Serasi No 100");
        
        User user = new User();
        user.setId(1L);
        user.setName(payload.getName());
        user.setDateOfBirth(toDate(payload.getDateOfBirth()));
        user.setFullAdress(payload.getFullAdress());
        
        // Registering Mock Service
        Mockito
            .when(userService.addUser(user))
            .thenReturn(user);
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/api/v1/funding/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJsonString(payload))
            )
            .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.success"        , Matchers.equalTo(Boolean.FALSE)))
            .andExpect(jsonPath("$.errors[*].code" , Matchers.contains("dateOfBirth")));
    }
    
    /**
     * Failed due to :
     * User Full Address is blank/empty
     * 
     * @throws {@link Exception}
     */
    @Test
    public void addUserInvalidAddres_thenFailed() throws Exception {
        // Mocking Data
        UserReq payload = new UserReq();
        payload.setName("Wawan Setiawan");
        payload.setDateOfBirth("10/01/1990");
        // -- should trigger Address validation, Address can not be blank
        payload.setFullAdress("");
        
        User user = new User();
        user.setId(1L);
        user.setName(payload.getName());
        user.setDateOfBirth(toDate(payload.getDateOfBirth()));
        user.setFullAdress(payload.getFullAdress());
        
        // Registering Mock Service
        Mockito
            .when(userService.addUser(user))
            .thenReturn(user);
        
        // Perform Web MVC Mock Request
        mockMvc
            .perform(
                MockMvcRequestBuilders
                    .post("/api/v1/funding/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtils.toJsonString(payload))
            )
            .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
            .andExpect(jsonPath("$.success"        , Matchers.equalTo(Boolean.FALSE)))
            .andExpect(jsonPath("$.errors[*].code" , Matchers.contains("fullAdress")));
    }
    
    /*
     * === Mock Test Data Preparation ===
     */
    
    private User mockUser(String name, String dateOfBirth, String address) {
        User user = new User();
        user.setName(name);
        user.setDateOfBirth(toDate(dateOfBirth));
        user.setFullAdress(address);
        return user;
    }
    
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    
    private Date toDate(String date) {
        return DateUtils.toDate(DATE_PATTERN, date);
    }
}
