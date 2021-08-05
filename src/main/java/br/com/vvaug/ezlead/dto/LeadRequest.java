package br.com.vvaug.ezlead.dto;

import br.com.vvaug.ezlead.document.Lead;
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
public class LeadRequest implements Serializable {

    @NotBlank
    private String name;
    @NotBlank
    private String phone;
    private String status = "DEFAULT";
    @NotBlank
    private String state;

    public Lead convert(){
        return Lead.builder()
                .name(getName())
                .phone(getPhone())
                .state(getState())
                //.status("")
                .build();
    }
}
