package hello.entities;

import hello.dtos.OrderItemDTO;
import hello.exceptions.TryingToUpdateIdException;
import hello.dtos.UserDTO;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public User(){}

    public  User(UserDTO userDTO){
        name = userDTO.getName();
        surname = userDTO.getSurname();
        email = userDTO.getEmail();
    }

    public void update(UserDTO userDTO) throws TryingToUpdateIdException {
        if(userDTO.getId() != null){
            throw new TryingToUpdateIdException("User");
        }
        if(!StringUtils.isEmpty(userDTO.getName())) {
            name = userDTO.getName();
        }
        if(!StringUtils.isEmpty(userDTO.getSurname())) {
            surname = userDTO.getSurname();
        }
        if(!StringUtils.isEmpty(userDTO.getEmail())){
            email = userDTO.getEmail();
        }
    }
}
