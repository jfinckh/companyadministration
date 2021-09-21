package de.johannes.companyadministration.service.implementation;

import de.johannes.companyadministration.model.Company;
import de.johannes.companyadministration.repository.CompanyEntity;
import de.johannes.companyadministration.mapper.CompanyMapper;
import de.johannes.companyadministration.repository.CompanyRepository;
import de.johannes.companyadministration.service.ICompanyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper = CompanyMapper.INSTANCE;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(Company company) {
        var companyEntity = companyMapper.toEntity(company);
        var storedCompanyEntity = companyRepository.save(companyEntity);
        return companyMapper.toModel(storedCompanyEntity);
    }

    @Override
    public Company updateCompany(Company company, Long companyId) {
        var companyEntity = companyMapper.toEntity(company);
        var oldCompanyOptional = companyRepository.findById(companyId);
        if(oldCompanyOptional.isEmpty()) {
            return null;
        }
        var oldCompany = oldCompanyOptional.get();
        oldCompany.setAddress(companyEntity.getAddress());
        oldCompany.setConsultantEntities(companyEntity.getConsultantEntities());
        oldCompany.setName(company.getName());
        companyRepository.save(oldCompany);
        return companyMapper.toModel(oldCompany);
    }

    @Override
    public Company getCompany(Long id) {
        Optional<CompanyEntity> company = companyRepository.findById(id);
        return company.map(companyMapper::toModel).orElse(null);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Page<Company> getCompanies(Pageable pageable) {
        Page<CompanyEntity> companies = companyRepository.findAll(pageable);
        return companies.map(companyMapper::toModel);
    }
}
