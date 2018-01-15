package hello.entities;

import hello.exceptions.TryingToUpdateIdException;
import hello.dtos.OrderItemDTO;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "`orderItem`")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private User user;

    private String description;
    private Integer price;

    public OrderItem(){}

    public OrderItem(OrderItemDTO orderItemDTO, Order orderArg, User userArg){
        description = orderItemDTO.getDescription();
        price = orderItemDTO.getPrice();
        order = orderArg;
        user = userArg;
    }

    public void update(OrderItemDTO orderItemDTO) throws TryingToUpdateIdException {
        if(orderItemDTO.getId() != null){
            throw new TryingToUpdateIdException("OrderItem, id");
        }
        if(orderItemDTO.getOrderId() != null) {
            throw new TryingToUpdateIdException("OrderItem, orderId");
        }
        if(orderItemDTO.getUserId() != null) {
            throw new TryingToUpdateIdException("OrderItem, userId");
        }
        if(!StringUtils.isEmpty(orderItemDTO.getDescription())) {
            description = orderItemDTO.getDescription();
        }
        if(orderItemDTO.getPrice() != null){
           price = orderItemDTO.getPrice();
        }
    }
}
