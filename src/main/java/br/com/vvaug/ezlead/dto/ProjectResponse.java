package br.com.vvaug.ezlead.dto;


import br.com.vvaug.ezlead.document.Lead;
import br.com.vvaug.ezlead.document.Project;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(NON_NULL)
public class ProjectResponse implements Serializable {

    private String id;
    private String name;
    private String keyword;
    private String place;
    private String state;
    private String city;
    private String neighbourhood;
    private List<Lead> leads = new ArrayList<>();

    public ProjectResponse(Project project){
        setId(project.getId());
        setName(project.getName());
        setKeyword(project.getKeyword());
        setPlace(project.getPlace());
        setCity(project.getCity());
        setState(project.getState());
        setNeighbourhood(project.getNeighbourhood());
        setLeads(project.getLeads());
    }
}
