package de.johannes.companyadministration.model;

import lombok.Data;

@Data
public class Consultant {
    private Long id;
    private String name;
    private Company company;
}
