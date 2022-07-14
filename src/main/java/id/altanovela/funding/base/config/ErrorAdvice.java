package id.altanovela.funding.base.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

import id.altanovela.funding.base.dto.ResponseDto;
import id.altanovela.funding.base.dto.ResponseDto.Error;

@Slf4j
@ControllerAdvice
public class ErrorAdvice extends ResponseEntityExceptionHandler {
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status, WebRequest request) { 
        
        List<Error> errorList = new ArrayList<>();
        if(null != ex.getBindingResult().getFieldErrors()) {
            for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
                errorList.add(
                    Error.builder()
                        .code(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                    .build()
                );
            }
        }
        
        return new ResponseEntity<>(
            ResponseDto.error(errorList), 
            HttpStatus.BAD_REQUEST
        );
    }
}
