package com.course.reliability.model;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class CustomerInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="customer_information_id_seq")
    @SequenceGenerator(name="customer_information_id_seq", sequenceName="customer_information_id_seq", allocationSize=1)
    private Integer id;
    private Integer age;
    private Double salary;
    private Integer realEstate;
    private Double debt;
    private Double elec;
    private Double gas;
    private Double reliability;
    private String name;
    private String address;
    private String phone_number;

}
