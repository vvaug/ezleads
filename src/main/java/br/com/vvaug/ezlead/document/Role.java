package br.com.vvaug.ezlead.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Document(collection = "roles")
public class Role implements Serializable, GrantedAuthority{

    @MongoId
    private String id;
    private String name;

    @Override
    public String getAuthority() {
        return getName();
    }
}
