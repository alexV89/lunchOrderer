package hello.controllers;

import hello.exceptions.IdNotFoundException;
import hello.dtos.OrderItemDTO;
import hello.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/orderItem")
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @PostMapping (path = "/order/{orderId}/user/{userId}")
    public @ResponseBody OrderItemDTO addNewOrderItem (@RequestBody OrderItemDTO added,
                                                 @PathVariable Long orderId,
                                                 @PathVariable Long userId){
        return orderItemService.addOrderItem(added, orderId, userId);
    }

    @PutMapping (path = "/{id}")
    public OrderItemDTO updateOrderItem (@PathVariable Long id, @RequestBody OrderItemDTO orderUpdateDTO){
        try {
            return orderItemService.updateOrderItem(id, orderUpdateDTO);
        } catch (IdNotFoundException e) {
            e.printStackTrace();
            return orderUpdateDTO;
        }
    }

    @DeleteMapping (path = "/{id}")
    public String removeOrderItem (@PathVariable Long id){
        try {
            return orderItemService.removeOrderItem(id);
        } catch (IdNotFoundException e) {
            return e.getMessage();
        }
    }

    @GetMapping()
    public List<OrderItemDTO> getAllOrderItems() {
        return orderItemService.getAllOrderItems();
    }
}
