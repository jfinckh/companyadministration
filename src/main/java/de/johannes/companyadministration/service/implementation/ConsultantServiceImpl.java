package de.johannes.companyadministration.service.implementation;

import de.johannes.companyadministration.model.Consultant;
import de.johannes.companyadministration.repository.ConsultantEntity;
import de.johannes.companyadministration.mapper.ConsultantMapper;
import de.johannes.companyadministration.repository.ConsultantRepository;
import de.johannes.companyadministration.service.IConsultantService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConsultantServiceImpl implements IConsultantService {

    private final ConsultantRepository consultantRepository;
    private final ConsultantMapper consultantMapper = ConsultantMapper.INSTANCE;

    public ConsultantServiceImpl(ConsultantRepository consultantRepository) {
        this.consultantRepository = consultantRepository;
    }

    @Override
    public Consultant createConsultant(Consultant consultant) {
        var consultantEntity = consultantMapper.toEntity(consultant);
        var storedConsultantEntity = consultantRepository.save(consultantEntity);
        return consultantMapper.toModel(storedConsultantEntity);
    }

    @Override
    public Consultant updateConsultant(Consultant consultant, Long id) {
        var oldConsultantOptional = consultantRepository.findById(id);
        if (oldConsultantOptional.isEmpty()) {
            return null;
        }
        var oldConsultant = oldConsultantOptional.get();
        oldConsultant.setName(consultant.getName());
        consultantRepository.save(oldConsultant);
        return consultantMapper.toModel(oldConsultant);
    }

    @Override
    public void deleteConsultant(Long id) {
        consultantRepository.deleteById(id);
    }

    @Override
    public Page<Consultant> getConsultantsForCompany(long id, Pageable pageable) {
        Page<ConsultantEntity> consultantEntities = consultantRepository.findByCompanyEntityId(id, pageable);
        return consultantEntities.map(consultantMapper::toModel);
    }

    @Override
    public Consultant getConsultant(Long id) {
        Optional<ConsultantEntity> consultant = consultantRepository.findById(id);
        return consultant.map(consultantMapper::toModel).orElse(null);
    }
}
