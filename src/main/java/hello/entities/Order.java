package hello.entities;

import hello.dtos.OrderDTO;
import hello.exceptions.TryingToUpdateIdException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    Timestamp timestamp;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Order(){}

    public Order(Restaurant restaurantArg){
        restaurant = restaurantArg;
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public void update(OrderDTO orderDTO) throws TryingToUpdateIdException {
        if(orderDTO.getId() != null){
            throw new TryingToUpdateIdException("order");
        }
        if(orderDTO.getRestaurantId() != null){
            restaurant.setId(orderDTO.getRestaurantId());
        }
    }
}
