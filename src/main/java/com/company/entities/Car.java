package com.company.entities;/*
  Author DSR Sosnovsky 
  January 
*/

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Car {

    private String brand;
    private String model;
    private Integer year;
    //@JsonIgnore
    private Integer kilometerRun;
    private String uuid;
}
