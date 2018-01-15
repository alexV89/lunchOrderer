package hello.controllers;

import hello.exceptions.IdNotFoundException;
import hello.dtos.RestaurantDTO;
import hello.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/restaurant")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping()
    public RestaurantDTO addNewRestaurant (@RequestBody RestaurantDTO added) {
        return restaurantService.addNewRestaurant(added);
    }

    @DeleteMapping(path="/{id}")
    public String deleteUserByID (@PathVariable Long id){
        try {
            return restaurantService.deleteRestaurant(id);
        } catch (IdNotFoundException e) {
            return e.getMessage();
        }
    }

    @PutMapping(path="/{id}")
    public RestaurantDTO updateUserByID(@PathVariable Long id
            , @RequestBody RestaurantDTO updateDTO){
        try {
            return restaurantService.updateRestaurant(id, updateDTO);
        } catch (IdNotFoundException e) {
            e.printStackTrace();
            return updateDTO;
        }
    }

    @GetMapping()
    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }
}
