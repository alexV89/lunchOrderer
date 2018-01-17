package hello.services;

import hello.Utils;
import hello.dtos.OrderDTO;
import hello.dtos.OrderItemDTO;
import hello.entities.Order;
import hello.entities.OrderItem;
import hello.entities.Restaurant;
import hello.exceptions.IdNotFoundException;
import hello.exceptions.TryingToUpdateIdException;
import hello.repositories.OrderRepository;
import hello.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional
    public OrderDTO addOrder(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findOne(restaurantId);
        Order order = new Order(restaurant);
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
            order.update(orderDTO);
            orderRepository.save(order);
            return orderDTO;
        }
    }

    @Transactional
    public String removeOrder(Long id) throws IdNotFoundException {
        if(!orderRepository.exists(id)){
            throw new IdNotFoundException(id);
        }
        else{
            orderRepository.delete(id);
            return Utils.sendDeletedMessage();
        }
    }

    @Transactional

    public List<OrderItemDTO> getOrderItems(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        List<OrderItem> orderItems = order.getOrderItems();

        return orderItems.stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();

        return orders.stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }
}
