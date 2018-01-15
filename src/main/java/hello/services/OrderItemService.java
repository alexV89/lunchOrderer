package hello.services;

import hello.entities.Order;
import hello.entities.OrderItem;
import hello.entities.User;
import hello.exceptions.IdNotFoundException;
import hello.exceptions.TryingToUpdateIdException;
import hello.Utils;
import hello.dtos.OrderItemDTO;
import hello.repositories.OrderItemRepository;
import hello.repositories.OrderRepository;
import hello.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
        /* pod ? */
        Order order = orderRepository.findOne(orderId);
        User user = userRepository.findOne(userId);

        OrderItem newOrderItem = new OrderItem(orderItemDTO, order, user);
        orderItemRepository.save(newOrderItem);
        order.addOrderItem(newOrderItem);
        return new OrderItemDTO(newOrderItem);
    }

    @Transactional
    public String removeOrderItem(Long id) throws IdNotFoundException {
        if (!orderItemRepository.exists(id)) {
            throw new IdNotFoundException(id);
        }
        else {
            orderItemRepository.delete(id);
            return Utils.sendDeletedMessage(id);
        }
    }

    @Transactional
    public OrderItemDTO updateOrderItem(Long id, OrderItemDTO orderItemDTO) throws IdNotFoundException {
        OrderItem orderItemToUpdate = orderItemRepository.findOne(id);
        if(orderItemToUpdate == null){
            throw new IdNotFoundException(id);
        }
        else {
            try {
                orderItemToUpdate.update(orderItemDTO);
            } catch (TryingToUpdateIdException e) {
                e.printStackTrace();
                return orderItemDTO;
            }
            orderItemRepository.save(orderItemToUpdate);
            return new OrderItemDTO(orderItemToUpdate);
        }
    }

    @Transactional
    public List<OrderItemDTO> getAllOrderItems(){
        Iterable<OrderItem> orderItems = orderItemRepository.findAll();
        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        for(OrderItem it : orderItems){
            OrderItemDTO orderItemDTO = new OrderItemDTO(it);
            orderItemDTOs.add(orderItemDTO);
        }

        return orderItemDTOs;
    }
}
