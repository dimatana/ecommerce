package com.training.ecommerce.Service;

import com.training.ecommerce.HubRate;
import com.training.ecommerce.Repository.HubRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HubRateService {
    private final HubRateRepository hubRateRepository;

    public HubRateService(HubRateRepository hubRateRepository) {
        this.hubRateRepository = hubRateRepository;
    }

    //register a new hub rate;
    public HubRate registerHubRate(HubRate hubRate){
        return hubRateRepository.save(hubRate);
    }
    //get all hub rates
    public List<HubRate> getAllHubRates(){
        return hubRateRepository.findAll();
    }
    //get a hub rate by id
    public Optional<HubRate> getHubRateById(Long id) {
        return hubRateRepository.findById(id);
    }
    //update hub rate details
    public HubRate updateHubRate(Long id, HubRate hubRateDetails){
        HubRate hubRate = hubRateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("hub rate not found for this id ::" + id));
        hubRate.setHubName(hubRateDetails.getHubName());
        hubRate.setRate(hubRateDetails.getRate());
        return hubRateRepository.save(hubRate);
    }
    //delete  a hub rate
    public void deleteHubRate(Long id){
        HubRate hubRate = hubRateRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("hob rate not found for this id::" + id));
        hubRateRepository.delete(hubRate);
    }
}
