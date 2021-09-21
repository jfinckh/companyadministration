package de.johannes.companyadministration.controller;

import de.johannes.companyadministration.dto.CompanyResponseDto;
import de.johannes.companyadministration.dto.ConsultantRequestDto;
import de.johannes.companyadministration.dto.ConsultantResponseDto;
import de.johannes.companyadministration.mapper.CompanyMapper;
import de.johannes.companyadministration.mapper.ConsultantMapper;
import de.johannes.companyadministration.service.ICompanyService;
import de.johannes.companyadministration.service.IConsultantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ConsultantController {

    private final IConsultantService consultantService;
    private final ICompanyService companyService;
    private final ConsultantMapper consultantMapper = ConsultantMapper.INSTANCE;
    private final CompanyMapper companyMapper = CompanyMapper.INSTANCE;

    public ConsultantController(IConsultantService consultantService, ICompanyService companyService) {
        this.consultantService = consultantService;
        this.companyService = companyService;
    }

    /**
     * Returns all Consultants for a single company depending on the pagination.
     *
     * @param companyId the id of the company
     * @param page startpage
     * @param size size of te page
     * @return page of consultants
     * */
    @GetMapping(value = "/companies/{companyId}/consultants")
    public HttpEntity<Page<ConsultantResponseDto>> getConsultantsForCompany(@PathVariable Long companyId,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        var consultants = consultantService.getConsultantsForCompany(companyId, paging);
        return new ResponseEntity<>(consultants.map(consultantMapper::toResponseDto), HttpStatus.OK);
    }

    /**
     * Returns a single consultant.
     *
     * @param companyId the company of the consultant
     * @param id the id of the consultant
     * @return single consultant
     * */
    @GetMapping(value="/companies/{companyId}/consulants/{id}", produces = { "application/hal+json" })
    public ResponseEntity<ConsultantResponseDto> getSingleConsultant(
            @PathVariable Long companyId, @PathVariable Long id) {
        var consultant = consultantService.getConsultant(id);
        return new ResponseEntity<>(consultantMapper.toResponseDto(consultant), HttpStatus.OK);
    }

    /**
     * Updates a consultant.
     *
     * @param companyId the id of the company of the consultant
     * @param consultantId the id of the consultant
     * @param consultantRequestDto the updated consultant
     * @return the updated consultant
     * */
    @PutMapping(value = "/companies/{companyId}/consulants/{consultantId}", produces = { "application/hal+json" })
    public ResponseEntity<ConsultantResponseDto> updateConsultant(
            @PathVariable Long companyId,
            @PathVariable Long consultantId,
            @RequestBody ConsultantRequestDto consultantRequestDto) {
        var consultant = consultantMapper.toModel(consultantRequestDto);
        var storeConsultant = consultantService.updateConsultant(consultant, consultantId);
        return new ResponseEntity<>(consultantMapper.toResponseDto(storeConsultant), HttpStatus.OK);
    }

    /**
     * Deletes a consultant.
     *
     * @param companyId the id of the company of the consultant
     * @param id the id of the consultant
     * @return empty response
     * */
    @DeleteMapping(value = "/companies/{companyId}/consulants/{id}", produces = { "application/hal+json" })
    public ResponseEntity<ConsultantResponseDto> deleteConsultant(@PathVariable Long companyId, @PathVariable Long id) {
        var storedConsultant = consultantService.getConsultant(id);
        var companyOfConsultant = companyService.getCompany(companyId);
        if(companyOfConsultant != null) {
            companyOfConsultant.getConsultants().remove(storedConsultant);
            companyService.updateCompany(companyOfConsultant, companyId);
        }
        consultantService.deleteConsultant(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    /**
     * Creates a consultant for a company.
     *
     * @param companyId the id of the company.
     * @param consultantRequestDto the consultant to create
     * @return the created consultant
     * */
    @PostMapping(value = "/companies/{companyId}/consulants", produces = { "application/hal+json" })
    public ResponseEntity<CompanyResponseDto> createConsultantForCompany(
            @PathVariable Long companyId, @RequestBody ConsultantRequestDto consultantRequestDto) {
        var consultant = consultantMapper.toModel(consultantRequestDto);
        var company = companyService.getCompany(companyId);
        consultant.setCompany(company);
        company.getConsultants().add(consultant);
        var storedCompany = companyService.createCompany(company);
        return new ResponseEntity<>(companyMapper.toResponseDto(storedCompany), HttpStatus.OK);
    }
}
