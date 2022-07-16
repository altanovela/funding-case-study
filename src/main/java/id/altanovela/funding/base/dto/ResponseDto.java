package id.altanovela.funding.base.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ResponseDto<T> {
    
    @JsonIgnore
    private ObjectMapper objectMapper = new ObjectMapper();
    private boolean success = Boolean.FALSE;
    private Pagedata pagedata;
    private List<T> data = new ArrayList<T>();
    private List<Error> errors = new ArrayList<>();
    private String requestId = UUID.randomUUID().toString();
    
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss z", timezone="UTC")
    private Date timestamp;
    
    public ResponseDto() {
        this.success = Boolean.TRUE;
    }
    
    public ResponseDto(Boolean success) {
        this.success = success;
    }
    
    public ResponseDto(Page<T> pagination) {
        this.success = Boolean.TRUE;
        this.data = pagination.getContent();
        this.pagedata = new Pagedata(
                pagination.getNumberOfElements(),
                pagination.getNumber() + 1, pagination.getTotalPages(), 
                pagination.getSize()
        );
    }
    
    public static <T> ResponseDto<T> error(List<Error> errs) {
        ResponseDto<T> response = new ResponseDto<>();
        response.setSuccess(Boolean.FALSE);
        response.setErrors(errs);
        return response;
    }
    
    @Getter
    @Setter
    @Builder
    @JsonInclude(Include.NON_NULL)
    public static class Error {
        
        private String code;
        private String message;
        
        public Error(String code, String message) {
            this.code = code;
            this.message = message;
        }
        
    }
    
    @Getter
    @Setter
    public static class Pagedata {
        
        private int totalCount = 0;
        private int currentPage = 1;
        private int pageCount = 1;
        private int pageSize = 1;
        
        public Pagedata(int totalCount, int currentPage, int pageCount, int pageSize) {
            this.totalCount = totalCount;
            this.currentPage = currentPage;
            this.pageCount = pageCount;
            this.pageSize = pageSize;
        }
        
    }
    
    public void setPagedata(int totalCount, int currentPage, int pageCount, int pageSize) {
        this.pagedata = new Pagedata(totalCount, currentPage, pageCount, pageSize);
    }
    
    public List<T> getData() {
        if(this.data!=null && this.data.isEmpty()) {
            return null;
        }
        return this.data;
    }
    
    public Date getTimestamp() {
        return new Date();
    }
    
    public void addData(T data) {
        this.data.add(data);
    }
    
    public String toString() {
        String json = "{\"success\":false,\"error\":{\"code\":\"INTERNAL_SERVER_ERROR\",\"message\":\"Internal Server Error\"}}";
        try {
            json = objectMapper.writeValueAsString(this);
        } catch(Exception e) {
            log.error("Error when parsing response model.", e);
        }
        return json;
    }
    
}
