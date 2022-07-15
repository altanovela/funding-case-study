package id.altanovela.funding.base.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import id.altanovela.funding.base.components.HttpResponse;

@ControllerAdvice
public class ErrorAdvice extends ResponseEntityExceptionHandler {
    
    @Autowired
    HttpResponse httpResponse;
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status, WebRequest request
    ) { 
        return httpResponse.error(ex.getBindingResult());
    }
}
