package de.johannes.companyadministration.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import de.johannes.companyadministration.controller.CompanyController;
import de.johannes.companyadministration.controller.ConsultantController;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Relation(collectionRelation = "consultantResponseDtos", itemRelation = "consultants")
public class ConsultantResponseDto extends RepresentationModel<ConsultantResponseDto> {

    private Long id;
    private String name;
    @JsonIgnore
    private CompanyResponseDto companyResponseDto;

    public void addLinks() {
        this.add(linkTo(methodOn(ConsultantController.class).getSingleConsultant(id, companyResponseDto.getId())).withSelfRel());
        this.add(linkTo(methodOn(CompanyController.class).getSingleCompany(companyResponseDto.getId())).withRel("company"));
    }
}
