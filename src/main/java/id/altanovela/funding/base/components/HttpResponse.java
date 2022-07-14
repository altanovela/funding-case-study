package id.altanovela.funding.base.components;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import id.altanovela.funding.base.dto.ResponseDto;

@Component
public class HttpResponse {
    
    public ResponseEntity<String> success() {
        return new ResponseEntity<String>(new ResponseDto<String>().toString(), HttpStatus.OK);
    }
    
    public ResponseEntity<String> success(String message) {
        return new ResponseEntity<String>(new ResponseDto<String>(message, Boolean.TRUE).toString(), HttpStatus.OK);
    }
    
    public ResponseEntity<String> error(String message) {
        return new ResponseEntity<String>(new ResponseDto<String>(message, Boolean.FALSE).toString(), HttpStatus.OK);
    }
    
    public ResponseEntity<String> success(ResponseDto<?> response) {
        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }
    
    public ResponseEntity<String> custom(ResponseDto<?> response, HttpStatus httpStatus) {
        return new ResponseEntity<String>(response.toString(), httpStatus);
    }
}
