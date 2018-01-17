package hello.entities;

import hello.exceptions.TryingToUpdateIdException;
import hello.dtos.RestaurantDTO;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "`restaurant`")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String link;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    private List<Order> orders;

    public Restaurant(){}

    public Restaurant(RestaurantDTO restaurantDTO){
        name = restaurantDTO.getName();
        link = restaurantDTO.getLink();
    }

    public void update(RestaurantDTO restaurantDTO) throws TryingToUpdateIdException {
        if(restaurantDTO.getId() != null){
            throw new TryingToUpdateIdException("Restaurant");
        }
        if(!StringUtils.isEmpty(restaurantDTO.getName())) {
            name = restaurantDTO.getName();
        }
        if(!StringUtils.isEmpty(restaurantDTO.getLink())) {
            link = restaurantDTO.getLink();
        }
    }
}
