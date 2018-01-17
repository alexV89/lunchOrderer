package hello.services;

import hello.dtos.OrderDTO;
import hello.dtos.OrderItemDTO;
import hello.entities.Order;
import hello.exceptions.IdNotFoundException;
import hello.exceptions.TryingToUpdateIdException;
import hello.Utils;
import hello.dtos.RestaurantDTO;
import hello.entities.Restaurant;
import hello.repositories.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {
    private RestaurantRepository restaurantRepository;

    @Autowired
    RestaurantService(RestaurantRepository restaurantRepository){
        this.restaurantRepository = restaurantRepository;
    }

    @Transactional
    public RestaurantDTO addNewRestaurant(RestaurantDTO newRestaurantDTO){
        Restaurant newRestaurant = new Restaurant(newRestaurantDTO);
        restaurantRepository.save(newRestaurant);
        return new RestaurantDTO(newRestaurant);
    }

    @Transactional
    public String deleteRestaurant(Long id) throws IdNotFoundException {
        if(!restaurantRepository.exists(id)) {
           throw new IdNotFoundException(id);
        }
       restaurantRepository.delete(id);
       return Utils.sendDeletedMessage();
    }

    @Transactional
    public RestaurantDTO updateRestaurant(Long id, RestaurantDTO restaurantUpdateDTO) throws IdNotFoundException {
        Restaurant restaurant = restaurantRepository.findOne(id);
        if(restaurant == null){
            throw new IdNotFoundException(id);
        }
        else {
            restaurant.update(restaurantUpdateDTO);
            restaurantRepository.save(restaurant);
            return new RestaurantDTO(restaurant);
        }
    }

    @Transactional
    public List<RestaurantDTO> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        return restaurants.stream()
                .map(RestaurantDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<OrderDTO> getAllRestaurantOrders(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findOne(restaurantId);
        List<Order> orders = restaurant.getOrders();

        return orders.stream()
                .map(OrderDTO::new)
                .collect(Collectors.toList());
    }
}
