package de.johannes.companyadministration.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultantRepository extends CrudRepository<ConsultantEntity, Long> {
    Page<ConsultantEntity> findByCompanyEntityId(Long id, Pageable pageable);
}
