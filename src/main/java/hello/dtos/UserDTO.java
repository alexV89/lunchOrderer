package hello.dtos;

import hello.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    Long id;
    String name;
    String surname;
    String email;

    public UserDTO(){}

    public UserDTO(User user) {
        id = user.getId();
        name = user.getName();
        surname = user.getSurname();
        email = user.getEmail();
    }
}
