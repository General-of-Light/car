package com.basilio.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping(path="/basilio")
public class MainController {
    @Autowired

    private CarRepository carRepository;

    @GetMapping(path = "/all") // Retrieve all cars
    public @ResponseBody Iterable<Car> getAllCars() {
        return carRepository.findAll();
    }

    @PostMapping(path = "/new") // Add a new car
    public @ResponseBody String newCar(
            @RequestParam String brand,
            @RequestParam String model,
            @RequestParam String body,
            @RequestParam String price
    ) {
        Car car = new Car();
        car.setBrand(brand);
        car.setModel(model);
        car.setBody(body);
        car.setPrice(price);
        carRepository.save(car);
        return "Success adding new car";
    }


    @PutMapping(path = "/{id}") // Edit a car base on id
    public @ResponseBody String updateCar(@PathVariable int id,
                                          @RequestParam String brand,
                                          @RequestParam String model,
                                          @RequestParam String body,
                                          @RequestParam String price) {

        Car car = carRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find car with given ID"));
        car.setBrand(brand);
        car.setModel(model);
        car.setBody(body);
        car.setPrice(price);
        carRepository.save(car);
        return "Success editing car";
    }


    @DeleteMapping(path = "{id}") // Delete a Car base on id
    public @ResponseBody String deleteCar(@PathVariable int id) {
        carRepository.deleteById(id);
        return "Success deleting car";
    }

    @GetMapping(path = "{id}")// Get a car by id
    public @ResponseBody Car getCar(@PathVariable int id) {
        return carRepository.findById(id).orElseThrow(() -> new RuntimeException("Cannot find car with given ID"));
    }


    @GetMapping(path = "/basilio/search") // Get cars that contains key in id, brand, model, body
    public @ResponseBody List<Car> search(@RequestParam String key) {

        Iterable<Car> cars = carRepository.findAll();
        List<Car> result = new ArrayList<>();
        for (Car car : cars) {
            if (car.getBrand().equals(key) ||
                    car.getModel().equals(key) ||
                    car.getBody().equals(key) ||
                    car.getPrice().equals(key)) {
                result.add(car);
            }
        }
        return result;
    }
}