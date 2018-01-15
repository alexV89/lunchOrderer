package hello.services;

import hello.Utils;
import hello.dtos.OrderDTO;
import hello.dtos.OrderItemDTO;
import hello.entities.Order;
import hello.entities.OrderItem;
import hello.exceptions.IdNotFoundException;
import hello.exceptions.TryingToUpdateIdException;
import hello.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderDTO addOrder(OrderDTO orderDTO){
        Order order = new Order(orderDTO);
        orderRepository.save(order);
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) throws IdNotFoundException {
        Order order = orderRepository.findOne(id);
        if(order == null){
            throw new IdNotFoundException(id);
        }
        else {
            try {
                order.update(orderDTO);
                return new OrderDTO(order);
            } catch (TryingToUpdateIdException e) {
                e.printStackTrace();
               return orderDTO;
            }
        }
    }

    @Transactional
    public String removeOrder(Long id) throws IdNotFoundException {
        if(!orderRepository.exists(id)){
            throw new IdNotFoundException(id);
        }
        else{
            orderRepository.delete(id);
            return Utils.sendDeletedMessage(id);
        }
    }

    @Transactional
    public List<OrderDTO> getAllOrders(){
        Iterable<Order> orders = orderRepository.findAll();
        List<OrderDTO> orderDTOS = new ArrayList<>();
        for(Order it : orders){
            OrderDTO orderDTO = new OrderDTO(it);
            orderDTOS.add(orderDTO);
        }
        return orderDTOS;
    }

    @Transactional

    public List<OrderItemDTO> getOrderItems(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>(order.getOrderItems().size());
        for(OrderItem it : order.getOrderItems()){
            OrderItemDTO orderItemDTO = new OrderItemDTO(it);
            orderItemDTOS.add(orderItemDTO);
        }

        return orderItemDTOS;
    }
}
