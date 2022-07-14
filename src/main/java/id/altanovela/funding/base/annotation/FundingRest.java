package id.altanovela.funding.base.annotation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Target(TYPE)
@Retention(RUNTIME)
@RestController
@RequestMapping(
        produces = MediaType.APPLICATION_JSON_VALUE
)
public @interface FundingRest {
    @AliasFor(annotation = RequestMapping.class)
    String[] value() default {};
}
