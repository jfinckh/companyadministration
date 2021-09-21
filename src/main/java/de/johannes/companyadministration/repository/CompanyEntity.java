package de.johannes.companyadministration.repository;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String address;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ConsultantEntity> consultantEntities;
}
