package id.altanovela.funding.dao.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity(name = "t_user")
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    
    @JsonFormat(
        shape    = JsonFormat.Shape.STRING,
        timezone = "Asia/Jakarta",
        pattern  = "dd/MM/yyyy"
    )
    private Date dateOfBirth;
    
    private String fullAdress;
}
