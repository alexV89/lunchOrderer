package hello.entities;

import hello.dtos.OrderDTO;
import hello.dtos.OrderItemDTO;
import hello.exceptions.TryingToUpdateIdException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table (name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    Long restaurant_id;
    Timestamp timestamp;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    public Order(){}

    public Order(OrderDTO orderDTO){
        restaurant_id = orderDTO.getRestaurantId();
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
    }

    public void removeOrderItem(OrderItem orderItem) {orderItems.remove(orderItem);}

    public void update(OrderDTO orderDTO) throws TryingToUpdateIdException {
        if(orderDTO.getId() != null){
            throw new TryingToUpdateIdException("order");
        }
        if(orderDTO.getRestaurantId() != null){
            restaurant_id = orderDTO.getRestaurantId();
        }
    }
}
