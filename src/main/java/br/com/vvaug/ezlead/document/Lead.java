package br.com.vvaug.ezlead.document;

import br.com.vvaug.ezlead.dto.LeadRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "leads")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Lead {

    @Id
    private String id;
    private String name;
    private String phone;
    private String status;
    private String state;

    public void update(LeadRequest leadRequest) {
        setName(leadRequest.getName() == null ? getName() : leadRequest.getName());
        setPhone(leadRequest.getPhone() == null ? getPhone() : leadRequest.getPhone());
        setStatus(leadRequest.getStatus() == null ? getStatus() : leadRequest.getStatus());
        setState(leadRequest.getState() == null ? getState() : leadRequest.getState());
    }
}
