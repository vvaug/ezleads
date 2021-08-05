package br.com.vvaug.ezlead.dto;

import br.com.vvaug.ezlead.document.Project;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(NON_NULL)
public class ProjectRequest implements Serializable {

    @NotBlank
    private String name;
    @NotBlank
    private String keyword;
    @NotBlank
    private String place;
    @NotBlank
    private String state;
    @NotBlank
    private String city;
    @NotBlank
    private String neighbourhood;

    public Project convert(){
        return Project.builder()
                .name(getName())
                .keyword(getKeyword())
                .place(getPlace())
                .state(getState())
                .city(getCity())
                .neighbourhood(getNeighbourhood())
                .build();
    }
}
