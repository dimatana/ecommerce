package com.training.ecommerce.mapper;

import com.training.ecommerce.model.Package;
import com.training.ecommerce.model.PackageDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PackageMapper {
    PackageDto toDto(Package aPackage);
    Package toEntity(PackageDto packageDto);
}
