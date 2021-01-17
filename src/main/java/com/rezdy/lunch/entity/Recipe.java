package com.rezdy.lunch.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="recipe")
public class Recipe implements Serializable {

    @Id
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "recipe_ingredient",
            joinColumns = {@JoinColumn(name="recipe", referencedColumnName = "title")},
            inverseJoinColumns = {@JoinColumn(name="ingredient", referencedColumnName = "title")}
    )
    private Set<Ingredient> ingredients = new HashSet<>();
}
