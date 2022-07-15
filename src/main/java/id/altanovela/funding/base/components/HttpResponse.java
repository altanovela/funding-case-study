package id.altanovela.funding.base.components;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import id.altanovela.funding.base.dto.ResponseDto;
import id.altanovela.funding.base.dto.ResponseDto.Error;

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
    
    public ResponseEntity error(BindingResult binding) {
        List<Error> errorList = new ArrayList<>();
        if(binding.hasErrors()) {
            if(null != binding.getFieldErrors()) {
                for (FieldError fieldError : binding.getFieldErrors()) {
                    errorList.add(
                        Error.builder()
                            .code(fieldError.getField())
                            .message(fieldError.getDefaultMessage())
                        .build()
                    );
                }
            }
            if(null != binding.getGlobalErrors()) {
                for (ObjectError objectError : binding.getGlobalErrors()) {
                    errorList.add(
                        Error.builder()
                            .code(objectError.getObjectName())
                            .message(objectError.getDefaultMessage())
                        .build()
                    );
                }
            }
        }
        return new ResponseEntity<Object>(
            ResponseDto.error(errorList), 
            HttpStatus.BAD_REQUEST
        );
    }
    
    public ResponseEntity<String> error(ResponseDto<?> response, HttpStatus httpStatus) {
        return new ResponseEntity<String>(response.toString(), httpStatus);
    }
    
    public ResponseEntity<String> success(ResponseDto<?> response) {
        return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
    }
    
    public ResponseEntity<String> custom(ResponseDto<?> response, HttpStatus httpStatus) {
        return new ResponseEntity<String>(response.toString(), httpStatus);
    }
}
