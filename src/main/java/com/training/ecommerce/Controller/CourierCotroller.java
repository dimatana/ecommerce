package com.training.ecommerce.Controller;

import com.training.ecommerce.Courier;
import com.training.ecommerce.Service.CourierService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/couriers")
public class CourierCotroller {
    private final CourierService courierService;

    public CourierCotroller(CourierService courierService) {
        this.courierService = courierService;
    }
    @PostMapping("/register")
    public Courier registerCourier(@RequestBody Courier courier){
        return courierService.registerCourier(courier);
    }
    @GetMapping
    public List<Courier> getAllCouriers(){
        return courierService.getAllCouriers();
    }
    @GetMapping("/{id}")
    public Optional<Courier> getCourierById(@PathVariable Long id){
        return courierService.getCourierById(id);
    }
    @PutMapping("/{id}")
    public Courier updateCourier(@PathVariable Long id, @RequestBody Courier courierDetails){
        return courierService.updateCourier(id, courierDetails);
    }
    @DeleteMapping("/{id}")
    public void deleteCourier(Long id){
        courierService.deleteCourier(id);
    }
}
