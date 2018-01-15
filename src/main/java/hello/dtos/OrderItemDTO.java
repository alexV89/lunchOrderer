package hello.dtos;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import hello.entities.Order;
import hello.entities.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

    private Long id;
    private Long orderId;
    private Long userId;
    private String description;

    private Integer price;

    public OrderItemDTO(){}

    public OrderItemDTO(OrderItem orderItem) {
        id = orderItem.getId();
        description = orderItem.getDescription();
        price = orderItem.getPrice();

        if(orderItem.getOrder() != null){
            orderId = orderItem.getOrder().getId();
        }
    }
}
