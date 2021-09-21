package de.johannes.companyadministration.service;

import de.johannes.companyadministration.model.Consultant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IConsultantService {
    Consultant createConsultant(Consultant consultant);
    Consultant updateConsultant(Consultant consultant, Long id);
    void deleteConsultant(Long id);
    Page<Consultant> getConsultantsForCompany(long id, Pageable pageable);
    Consultant getConsultant(Long id);
}
