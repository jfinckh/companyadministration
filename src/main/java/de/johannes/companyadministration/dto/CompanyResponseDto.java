package de.johannes.companyadministration.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.johannes.companyadministration.controller.CompanyController;
import de.johannes.companyadministration.controller.ConsultantController;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyResponseDto extends RepresentationModel<CompanyResponseDto> {
    private Long id;
    private String name;
    private String address;

    public void addLinks() {
        this.add(linkTo(methodOn(CompanyController.class).getSingleCompany(id)).withSelfRel());
        this.add(linkTo(methodOn(ConsultantController.class).getConsultantsForCompany(id, 1, 10)).withRel("consultants"));
        this.add(linkTo(methodOn(ConsultantController.class).createConsultantForCompany(id,null)).withRel("createconsultant"));
    }
}
