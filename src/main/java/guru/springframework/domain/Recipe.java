package guru.springframework.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jt on 6/13/17.
 */
@Data
@SuppressWarnings({"JpaDataSourceORMInspection", "LombokDataInspection", "LombokEqualsAndHashCodeInspection"})
@EqualsAndHashCode(exclude = {"ingredients", "notes", "categories"})
@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;

    @Lob
    private String directions;

    // Default value of the "value" attribute is "EnumType.ORDINAL" => which stores integer values (starting from 1 by default) in DB.
    @Enumerated(value = EnumType.STRING)
    private Difficulty difficulty;

    @Lob
    private Byte[] image;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipe")
    private Set<Ingredient> ingredients = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Notes notes;

    @ManyToMany
    @JoinTable(
            name = "recipe_category",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    // The attributes: "joinColumns" and "inverseJoinColumns" are used to rename the joining columns of the two tables.
    private Set<Category> categories = new HashSet<>();

}
