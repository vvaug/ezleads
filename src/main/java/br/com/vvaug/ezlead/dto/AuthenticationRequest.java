package br.com.vvaug.ezlead.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(NON_NULL)
public class AuthenticationRequest implements Serializable {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
