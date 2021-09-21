package de.johannes.companyadministration.mapper;

import de.johannes.companyadministration.dto.ConsultantRequestDto;
import de.johannes.companyadministration.dto.ConsultantResponseDto;
import de.johannes.companyadministration.model.Consultant;
import de.johannes.companyadministration.repository.ConsultantEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ConsultantMapper {

    ConsultantMapper INSTANCE = Mappers.getMapper(ConsultantMapper.class);

    Consultant toModel(ConsultantRequestDto consultantRequestDto);
    @Mapping(source = "consultantEntity.companyEntity", target = "company")
    Consultant toModel(ConsultantEntity consultantEntity);
    @Mapping(source = "consultant.company", target = "companyResponseDto")
    ConsultantResponseDto toResponseDto(Consultant consultant);
    @Mapping(source = "consultant.company", target = "companyEntity")
    ConsultantEntity toEntity(Consultant consultant);

    @AfterMapping
    default void addLinks(@MappingTarget ConsultantResponseDto consultantResponseDto) {
        consultantResponseDto.addLinks();
    }
}
