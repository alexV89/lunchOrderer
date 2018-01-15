package hello.dtos;

import hello.entities.Order;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class OrderDTO {
    private Long id;
    private Long restaurantId;
    private Timestamp timestamp;

    public OrderDTO(){}
    public OrderDTO(Order order){
        id = order.getId();
        restaurantId = order.getRestaurant_id();
        timestamp = order.getTimestamp();
    }
}
