package de.johannes.companyadministration.service;

import de.johannes.companyadministration.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICompanyService {
    Company createCompany(Company company);
    Company updateCompany(Company company, Long companyId);
    Company getCompany(Long id);
    void deleteCompany(Long id);
    Page<Company> getCompanies(Pageable pageable);
}
