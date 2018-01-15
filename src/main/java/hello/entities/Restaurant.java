package hello.entities;

import hello.exceptions.TryingToUpdateIdException;
import hello.dtos.RestaurantDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String name;
    String link;

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
