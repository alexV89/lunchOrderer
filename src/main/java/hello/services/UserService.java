package hello.services;

import hello.dtos.OrderItemDTO;
import hello.entities.OrderItem;
import hello.exceptions.IdNotFoundException;
import hello.Utils;
import hello.dtos.UserDTO;
import hello.entities.User;
import hello.repositories.OrderItemRepository;
import hello.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            return Utils.sendDeletedMessage();
        }
    }

    @Transactional
    public UserDTO updateUser(Long id, UserDTO userUpdateDTO) throws IdNotFoundException {
        User userToUpdate = userRepository.findOne(id);
        if(userToUpdate == null){
            throw new IdNotFoundException(id);
        }
        else {
            userToUpdate.update(userUpdateDTO);
            userRepository.save(userToUpdate);
            return new UserDTO(userToUpdate);
        }
    }

    @Transactional
    public List<UserDTO> getAllUsers(){
        List<User> restaurants = userRepository.findAll();

        return restaurants.stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OrderItemDTO> getUsersOrderItems(Long userId){
        List<OrderItem> orderItems = orderItemRepository.findAll();

        Stream<OrderItem> orderItemStream = orderItems.stream()
                .filter( orderItem -> (userId == orderItem.getUser().getId()) );

        return orderItemStream
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());
    }
}
