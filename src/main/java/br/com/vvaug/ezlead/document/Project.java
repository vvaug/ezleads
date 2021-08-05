package br.com.vvaug.ezlead.document;

import br.com.vvaug.ezlead.dto.ProjectRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "projects")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Project implements Serializable {

    @Id
    private String id;
    private String name;
    private String keyword;
    private String place;
    private String state;
    private String city;
    private String neighbourhood;
    private List<Lead> leads = new ArrayList<>();
    private User user;

    public void update(ProjectRequest projectRequest) {
        setName(projectRequest.getName() == null ? getName() : projectRequest.getName());
        setKeyword(projectRequest.getKeyword() == null ? getKeyword() : projectRequest.getKeyword());
        setPlace(projectRequest.getPlace() == null ? getPlace() : projectRequest.getPlace());
        setState(projectRequest.getState() == null ? getState() : projectRequest.getState());
        setCity(projectRequest.getCity() == null ? getCity() : projectRequest.getCity());
        setNeighbourhood(projectRequest.getNeighbourhood() == null ? getNeighbourhood() : projectRequest.getNeighbourhood());
    }
}
