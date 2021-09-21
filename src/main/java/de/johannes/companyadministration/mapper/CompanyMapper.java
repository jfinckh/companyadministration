package de.johannes.companyadministration.mapper;

import de.johannes.companyadministration.dto.CompanyRequestDto;
import de.johannes.companyadministration.dto.CompanyResponseDto;
import de.johannes.companyadministration.model.Company;
import de.johannes.companyadministration.repository.CompanyEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ConsultantMapper.class)
public interface CompanyMapper {

    CompanyMapper INSTANCE = Mappers.getMapper(CompanyMapper.class);

    Company toModel(CompanyRequestDto companyRequestDto);
    @Mapping(source = "companyEntity.consultantEntities", target = "consultants")
    Company toModel(CompanyEntity companyEntity);
    CompanyResponseDto toResponseDto(Company company);
    @Mapping(source = "company.consultants", target = "consultantEntities")
    CompanyEntity toEntity(Company company);

    @AfterMapping
    default void addLinks(@MappingTarget CompanyResponseDto consultantResponseDto) {
        consultantResponseDto.addLinks();
    }
}
