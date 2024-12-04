package com.training.ecommerce.service;

import com.training.ecommerce.exception.ResourceNotFoundException;
import com.training.ecommerce.mapper.PackageMapper;
import com.training.ecommerce.model.Package;

import com.training.ecommerce.model.PackageDto;
import com.training.ecommerce.model.Product;
import com.training.ecommerce.repository.PackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageService {
    private final PackageRepository packageRepository;
    private final PackageMapper packageMapper;

    public PackageService(PackageRepository packageRepository, PackageMapper packageMapper) {
        this.packageRepository = packageRepository;
        this.packageMapper = packageMapper;
    }

    public PackageDto registerPackage(PackageDto packageDto){
        Package aPackage = packageMapper.toEntity(packageDto);
        Package createdPackage = packageRepository.save(aPackage);
        return packageMapper.toDto(createdPackage);
    }

    public List<PackageDto> getAllPackages(){
        List<Package> packages = packageRepository.findAll();
        return packages.stream().map(packageMapper::toDto).toList();
    }

    public PackageDto getPackageById(Long id){
        return packageRepository.findById(id)
                .map(packageMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("package not found for this id::" +id));
    }

    public PackageDto updatePackage (Long id, PackageDto packageDetails){
        Package aPackage = packageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("package not found for this id ::" + id));
        aPackage.setDeliveryDate(packageDetails.getDeliveryDate());
        aPackage.setReceiver(packageDetails.getReceiver());
        aPackage.setSender(packageDetails.getSender());
        aPackage.setStatus(packageDetails.getStatus());
        Package updatedPackage = packageRepository.save(aPackage);
        return packageMapper.toDto(updatedPackage);
    }

    public void deletePackage(Long id){
        packageRepository.deleteById(id);
    }
}

