package com.vserviceu.employee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class Address {
    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(updatable = false, nullable = false)
    private String country;

    private Integer zip;
}
