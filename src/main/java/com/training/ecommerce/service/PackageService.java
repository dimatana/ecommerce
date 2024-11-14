package com.training.ecommerce.service;

import com.training.ecommerce.model.Package;

import com.training.ecommerce.repository.PackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageService {
    private final PackageRepository packageRepository;

    public PackageService(PackageRepository packageRepository) {
        this.packageRepository = packageRepository;
    }

    //register a new package
    public Package registerPackage(Package aPackage){
        return packageRepository.save(aPackage);
    }
    //get all packages
    public List<Package> getAllPackages(){
        return packageRepository.findAll();
    }
    //get a package by id
    public Optional<Package> getPackageById(Long id){
        return packageRepository.findById(id);
    }
    //update package details
    public Package updatePackage (Long id, Package packageDetails){
        Package aPackage = packageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("package not found for this id ::" + id));
        aPackage.setDeliveryDate(packageDetails.getDeliveryDate());
        aPackage.setReceiver(packageDetails.getReceiver());
        aPackage.setSender(packageDetails.getSender());
        aPackage.setStatus(packageDetails.getStatus());
        return packageRepository.save(aPackage);
    }
    //delete a package
    public void delletePackage(Long id){
        Package aPackage = packageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("package not found for this id ::" + id));
        packageRepository.delete(aPackage);
    }
}

