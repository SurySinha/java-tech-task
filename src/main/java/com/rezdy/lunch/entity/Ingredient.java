package com.rezdy.lunch.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "ingredient")
public class Ingredient implements Serializable {

    @Id
    private String title;
    private LocalDate bestBefore;
    private LocalDate useBy;

}
