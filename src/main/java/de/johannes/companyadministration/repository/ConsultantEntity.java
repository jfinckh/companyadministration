package de.johannes.companyadministration.repository;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ConsultantEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToOne
    private CompanyEntity companyEntity;
}
