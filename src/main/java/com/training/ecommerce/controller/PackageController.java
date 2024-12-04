package com.training.ecommerce.controller;

import com.training.ecommerce.api.PackagesApi;
import com.training.ecommerce.model.PackageDto;
import com.training.ecommerce.service.PackageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PackageController implements PackagesApi {
    private final PackageService packageService;

    public PackageController(PackageService packageService) {
        this.packageService = packageService;
    }

    @Override
    public ResponseEntity<PackageDto> createPackage(PackageDto packageDto) {
        PackageDto createdPackage = packageService.registerPackage(packageDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPackage);
    }

    @Override
    public ResponseEntity<Void> deletePackage(Long id) {
        packageService.deletePackage(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<PackageDto>> getAllPackages() {
        return ResponseEntity.ok(packageService.getAllPackages());
    }

    @Override
    public ResponseEntity<PackageDto> getPackageById(Long id) {
        return ResponseEntity.ok(packageService.getPackageById(id));
    }

    @Override
    public ResponseEntity<PackageDto> updatePackage(Long id, PackageDto packageDto) {
        PackageDto updatedPackage = packageService.updatePackage(id, packageDto);
        return ResponseEntity.ok(updatedPackage);
    }
}
