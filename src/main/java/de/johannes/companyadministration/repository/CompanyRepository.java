package de.johannes.companyadministration.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends CrudRepository<CompanyEntity, Long> {
    Page<CompanyEntity> findAll(Pageable pageable);
}
