package br.com.vvaug.ezlead.dto;

import br.com.vvaug.ezlead.document.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse implements Serializable {

    private String name;
    private String email;
    private String phone;

    //TODO use ModelMapper API
    public UserResponse(User user){
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPhone(user.getPhone());
    }
}
