package com.training.ecommerce.service;

import com.training.ecommerce.exception.ResourceNotFoundException;
import com.training.ecommerce.model.Courier;
import com.training.ecommerce.repository.CourierRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourierService {
    private final CourierRepository courierRepository;

    public CourierService(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    //register a new courier
    public Courier registerCourier (Courier courier){
        return courierRepository.save(courier);
    }
    //get all couriers
    public List<Courier> getAllCouriers() {
        return courierRepository.findAll();
    }
    //get a courier by id
    public Optional<Courier> getCourierById(Long id){
        return courierRepository.findById(id);
    }
    //update courier details
    public Courier updateCourier(Long id, Courier courierDetails){
        Courier courier = courierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("courier not found by id ::" + id));
        courier.setName(courierDetails.getName());
        courier.setLocation(courierDetails.getLocation());
        courier.setAvailable(courierDetails.isAvailable());
        return courierRepository.save(courier);
    }
    //delete a courier
    public void deleteCourier(Long id){
        Courier courier = courierRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("courier not found for this id ::" + id));
        courierRepository.delete(courier);
    }
}
