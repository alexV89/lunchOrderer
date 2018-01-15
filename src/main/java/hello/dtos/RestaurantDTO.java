package hello.dtos;
import hello.entities.Restaurant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDTO {

    private Long id;
    private String name;
    private String link;

    public RestaurantDTO() {}

    public RestaurantDTO(Restaurant restaurant) {
        id = restaurant.getId();
        name = restaurant.getName();
        link = restaurant.getLink();
    }
}
