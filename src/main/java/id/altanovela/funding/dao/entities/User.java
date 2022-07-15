package id.altanovela.funding.dao.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import id.altanovela.funding.dao.entities.base.BaseEntity;
import lombok.Data;

@Data
@Entity
@Table(name = "t_user")
public class User extends BaseEntity {
    
    private String name;
    
    @JsonFormat(
        shape    = JsonFormat.Shape.STRING,
        timezone = "Asia/Jakarta",
        pattern  = "dd/MM/yyyy"
    )
    private Date dateOfBirth;
    
    private String fullAdress;
}
