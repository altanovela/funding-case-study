package id.altanovela.funding.base.config;

import java.util.Arrays;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import id.altanovela.funding.base.dto.ResponseDto;
import id.altanovela.funding.base.dto.ResponseDto.Error;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ErrorAdviceException {
    
    private String defaultErrorMsg(Exception exc) {
        if(exc instanceof RuntimeException) {
            return exc.getMessage();
        }
        return "Internal Server Error";
    }
    
    /**
     * Generate Response Entity on Exception
     * @param exception {@link Exception}
     * @param request {@link WebRequest}
     * @return {@link ResponseEntity<Object>}
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleServiceException(RuntimeException exception, WebRequest request) {
        log.error("Funding Apps RuntimeException ", exception);
        return new ResponseEntity<>(
            ResponseDto.error(
                Arrays.asList(
                    Error.builder()
                        .message(defaultErrorMsg(exception))
                    .build()
                )
            ), HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
