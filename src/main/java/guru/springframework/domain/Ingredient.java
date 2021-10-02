package guru.springframework.domain;

import lombok.Data;
//import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@SuppressWarnings("LombokDataInspection")
@Data
@NoArgsConstructor
//@EqualsAndHashCode(exclude = {"uom"})
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private BigDecimal amount;

    @OneToOne //(fetch = FetchType.EAGER)
    private UnitOfMeasure uom;

    @ManyToOne(targetEntity = Recipe.class)
    private Recipe recipe;

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipe = recipe;
    }

    @Override
    public String toString() {
        return amount.setScale(1, RoundingMode.DOWN) + " " + uom.getDescription() + " of " + description;
    }
}
