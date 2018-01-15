package hello.controllers;

import hello.dtos.OrderItemDTO;
import hello.exceptions.IdNotFoundException;
import hello.dtos.UserDTO;
import hello.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(path="")
    public UserDTO addNewUser (@RequestBody UserDTO userDto) {
        return userService.addNewUser(userDto);
    }

    @DeleteMapping(path="/{id}")
    public String deleteUserByID (@PathVariable Long id){
        try {
            return userService.deleteUser(id);
        } catch (IdNotFoundException e) {
            return e.getMessage();
        }
    }

    @PutMapping(path="/{id}")
    public UserDTO updateUserByID(@PathVariable Long id
           , @RequestBody UserDTO updateDTO){
        try {
            return userService.updateUser(id, updateDTO);
        } catch (IdNotFoundException e) {
            e.printStackTrace();
            return updateDTO;
        }
    }

    @GetMapping()
    public List<UserDTO> getAllUsers() {
       return userService.getAllUsers();
    }

    @GetMapping("/items/user/{id}")
    public List<OrderItemDTO> getUsersOrderItems(@PathVariable Long id){
        return userService.getUsersOrderItems(id);
    }
}