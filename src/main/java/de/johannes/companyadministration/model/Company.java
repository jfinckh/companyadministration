package de.johannes.companyadministration.model;

import lombok.Data;

import java.util.List;

@Data
public class Company {

    private Long id;
    private String name;
    private String address;
    private List<Consultant> consultants;
}
