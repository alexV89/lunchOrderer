package hello.services;

import hello.dtos.OrderItemDTO;
import hello.entities.OrderItem;
import hello.exceptions.IdNotFoundException;
import hello.exceptions.TryingToUpdateIdException;
import hello.Utils;
import hello.dtos.UserDTO;
import hello.entities.User;
import hello.repositories.OrderItemRepository;
import hello.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDTO addNewUser(UserDTO newUserDTO){
        User newUser = new User(newUserDTO);
        userRepository.save(newUser);
        return new UserDTO(newUser);
    }

    @Transactional
    public String deleteUser(Long id) throws IdNotFoundException {
        if(!userRepository.exists(id)){
            throw new IdNotFoundException(id);
        }
        else {
            userRepository.delete(id);
            return Utils.sendDeletedMessage(id);
        }
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userUpdateDTO) throws IdNotFoundException {
        User userToUpdate = userRepository.findOne(id);
        if(userToUpdate == null){
            throw new IdNotFoundException(id);
        }
        else {
            try {
                userToUpdate.update(userUpdateDTO);
            } catch (TryingToUpdateIdException e) {
                e.printStackTrace();
                return userUpdateDTO;
            }
            userRepository.save(userToUpdate);
            return new UserDTO(userToUpdate);
        }
    }

    public List<UserDTO> getAllUsers(){
        Iterable<User> users = userRepository.findAll();
        List<UserDTO> userDTOS = new ArrayList<>();

        for(User it : users){
            UserDTO userDTO = new UserDTO(it);
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }


    public List<OrderItemDTO> getUsersOrderItems(Long userId){
        Iterable<OrderItem> orderItems = orderItemRepository.findAll();
        List<OrderItemDTO> orderItemDTOS = new ArrayList<>();
        for(OrderItem it : orderItems){
            if(it.getUser() != null && it.getUser().getId() == userId){
                OrderItemDTO orderItemDTO = new OrderItemDTO(it);
                orderItemDTOS.add(orderItemDTO);
            }
        }
        return orderItemDTOS;
    }
}
