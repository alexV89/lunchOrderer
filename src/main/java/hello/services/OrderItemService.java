package hello.services;

import hello.entities.Order;
import hello.entities.OrderItem;
import hello.entities.User;
import hello.exceptions.IdNotFoundException;
import hello.Utils;
import hello.dtos.OrderItemDTO;
import hello.repositories.OrderItemRepository;
import hello.repositories.OrderRepository;
import hello.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {
    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderItemService(OrderItemRepository orderItemRepository){
        this.orderItemRepository = orderItemRepository;
    }

     @Autowired
     OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Transactional
    public OrderItemDTO addOrderItem(OrderItemDTO orderItemDTO, Long orderId, Long userId){
        Order order = orderRepository.findOne(orderId);
        User user = userRepository.findOne(userId);

        OrderItem newOrderItem = new OrderItem(orderItemDTO, order, user);
        orderItemRepository.save(newOrderItem);
        order.getOrderItems().add(newOrderItem);
        return new OrderItemDTO(newOrderItem);
    }

    @Transactional
    public String removeOrderItem(Long id) throws IdNotFoundException {
        if (!orderItemRepository.exists(id)) {
            throw new IdNotFoundException(id);
        }
        else {
            orderItemRepository.delete(id);
            return Utils.sendDeletedMessage();
        }
    }

    @Transactional
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) throws IdNotFoundException {
        OrderItem orderItemToUpdate = orderItemRepository.findOne(id);
        if(orderItemToUpdate == null){
            throw new IdNotFoundException(id);
        }
        else {
            orderItemToUpdate.update(orderItemDTO);
            orderItemRepository.save(orderItemToUpdate);
            return new OrderItemDTO(orderItemToUpdate);
        }
    }

    public List<OrderItemDTO> getAllOrderItems() {
        List<OrderItem> orderItems = orderItemRepository.findAll();

        return orderItems.stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());
    }
}

