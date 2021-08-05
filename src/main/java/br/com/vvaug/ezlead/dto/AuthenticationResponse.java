package br.com.vvaug.ezlead.dto;

import br.com.vvaug.ezlead.document.User;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(NON_NULL)
public class AuthenticationResponse implements Serializable {

    private String jwt;
    private Integer expiresIn;
    private ExpirationType type;
    private UserResponse user;
}
