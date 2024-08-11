package com.vserviceu.employee.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "emp_table")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column
    private String dob;

    @Column(length = 10, nullable = false)
    private String gender;

    @Column(name = "job_title", updatable = false, nullable = false)
    private String jobTitle;

    @Column(name = "sal_in_K")
    private Float salary;

    @Column(name = "emp_type", updatable = false, nullable = false)
    private String empType;

    @Column(nullable = false, unique = true, updatable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNo;

    @Embedded
    private Address address;

    private String imageName;
    private String imageType;

    @Lob
    private byte[] image;
}
