package hello.controllers;

import hello.dtos.OrderDTO;
import hello.dtos.OrderItemDTO;
import hello.exceptions.IdNotFoundException;
import hello.services.OrderItemService;
import hello.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseStatus
@RequestMapping(path = "api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    @PostMapping(path = "/restaurant/{restaurantId}")
    public OrderDTO addNewOrder(@PathVariable Long restaurantId){
        return orderService.addOrder(restaurantId);
    }

    @PutMapping(path = "/{id}")
    public OrderDTO updateOrder(@PathVariable Long id, @RequestBody OrderDTO orderDTO) throws IdNotFoundException {
        try {
            return orderService.updateOrder(id, orderDTO);
        } catch (IdNotFoundException e) {
            throw e;
        }
    }

    @DeleteMapping(path = "/{id}")
    public String removeOrder(@PathVariable Long id){
        return orderService.removeOrder(id);
    }

    @GetMapping()
    public List<OrderDTO> getAllOrders(){
        return  orderService.getAllOrders();
    }

    @PostMapping("/addItem/order/{orderId}/user/{userId}")
    public OrderItemDTO addOrderItem(@RequestBody OrderItemDTO orderItemDTO,
                               @PathVariable Long orderId,
                               @PathVariable Long userId){
        return orderItemService.addOrderItem(orderItemDTO, orderId, userId);
    }


   @GetMapping("/items/order/{orderId}")
    public List<OrderItemDTO> getAllOrderItems(@PathVariable Long orderId){
        return orderService.getOrderItems(orderId);
    }
}
