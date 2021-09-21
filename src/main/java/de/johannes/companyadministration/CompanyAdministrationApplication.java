package de.johannes.companyadministration;

import de.johannes.companyadministration.repository.CompanyEntity;
import de.johannes.companyadministration.repository.ConsultantEntity;
import de.johannes.companyadministration.repository.CompanyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CompanyAdministrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CompanyAdministrationApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(CompanyRepository companyRepository) {
		return (args) -> {
			ConsultantEntity consultantEntity1 = new ConsultantEntity();
			consultantEntity1.setName("Max Muster");
			ConsultantEntity consultantEntity2 = new ConsultantEntity();
			consultantEntity2.setName("Charlotte Peters");
			ConsultantEntity consultantEntity3 = new ConsultantEntity();
			consultantEntity3.setName("Francesco Miller");

			CompanyEntity companyEntity1 = new CompanyEntity();
			companyEntity1.setName("Famous company");
			companyEntity1.setAddress("street 1 82213 city");
			companyEntity1.setConsultantEntities(List.of(consultantEntity1));

			CompanyEntity companyEntity2 = new CompanyEntity();
			companyEntity2.setName("Boring company");
			companyEntity2.setAddress("street2 12341 city");
			companyEntity2.setConsultantEntities(List.of(consultantEntity2, consultantEntity3));

			consultantEntity1.setCompanyEntity(companyEntity1);
			consultantEntity2.setCompanyEntity(companyEntity2);
			consultantEntity3.setCompanyEntity(companyEntity2);
			companyRepository.save(companyEntity1);
			companyRepository.save(companyEntity2);
		};
	}
}
