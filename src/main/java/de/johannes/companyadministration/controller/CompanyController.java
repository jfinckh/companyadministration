package de.johannes.companyadministration.controller;

import de.johannes.companyadministration.dto.CompanyRequestDto;
import de.johannes.companyadministration.dto.CompanyResponseDto;
import de.johannes.companyadministration.mapper.CompanyMapper;
import de.johannes.companyadministration.service.ICompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    private final ICompanyService companyService;
    private final CompanyMapper companyMapper = CompanyMapper.INSTANCE;

    public CompanyController(ICompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * Returns a Page containing all companies (depending on size).
     *
     * @param page startpage
     * @param size size of page
     * @return page of companyResponses
     * */
    @GetMapping(produces = { "application/hal+json" })
    public HttpEntity<Page<CompanyResponseDto>> getCompanies(
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Pageable paging = PageRequest.of(page, size);
        var companyResponseDtos = companyService.getCompanies(paging)
                .map(companyMapper::toResponseDto);
        return new ResponseEntity<>(companyResponseDtos, HttpStatus.OK);
    }

    /**
     * Returns a single company.
     *
     * @param id id of the company
     * @return Single Company
     * */
    @GetMapping(value = "/{id}", produces = { "application/hal+json" })
    public HttpEntity<CompanyResponseDto> getSingleCompany(@PathVariable Long id) {
        var company = companyService.getCompany(id);
        var companyDto = companyMapper.toResponseDto(company);
        return new ResponseEntity<>(companyDto, HttpStatus.OK);
    }

    /**
     * Creates a company.
     *
     * @param companyRequestDto The company to create
     * @return the created company
     * */
    @PostMapping(produces = { "application/hal+json" })
    public HttpEntity<CompanyResponseDto> createCompany(
            @RequestBody CompanyRequestDto companyRequestDto) {
        var company = companyMapper.toModel(companyRequestDto);
        var storedCompany = companyService.createCompany(company);
        var companyDto = companyMapper.toResponseDto(storedCompany);
        return new ResponseEntity<>(companyDto, HttpStatus.OK);
    }

    /**
     * Deletes a company.
     *
     * @param id the id of the company
     * @return empty response
     * */
    @DeleteMapping(value= "/{id}", produces = { "application/hal+json" })
    public HttpEntity<CompanyResponseDto> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    /**
     * Updates the company.
     *
     * @param companyRequestDto The updated company
     * @param id the id of the company to update
     * @return updated company
     * */
    @PutMapping(value = "/{id}", produces = { "application/hal+json" })
    public HttpEntity<CompanyResponseDto> updateCompany(
            @RequestBody CompanyRequestDto companyRequestDto,
            @PathVariable Long id) {
        var companyToUpdate = companyMapper.toModel(companyRequestDto);
        var updatedCompany = companyService.updateCompany(companyToUpdate, id);
        var companyDto = companyMapper.toResponseDto(updatedCompany);
        return new ResponseEntity<>(companyDto, HttpStatus.OK);
    }
}
