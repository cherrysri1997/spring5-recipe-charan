package guru.springframework.repositories;

import guru.springframework.domain.UnitOfMeasure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

//IT => Integration Test
@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository uomRepository;

    @Test
    @DirtiesContext
    // DirtiesContext:
    // If this annotation is used, as soon as this test is completed, Spring Context gets reloaded
    // (reload is actually a bit faster than loading the Spring Context for the first time, comparatively, so it seems pretty speed)
    // Anyways, this actually increases the time taken to test, as a whole, since context gets reloaded
    public void findByDescriptionTeaspoon() {
        Optional<UnitOfMeasure> uomOptional = uomRepository.findByDescription("Teaspoon");
        assertEquals("Teaspoon", uomOptional.orElse(new UnitOfMeasure()).getDescription());
    }

    @Test
    public void findByDescriptionCup() {
        Optional<UnitOfMeasure> uomOptional = uomRepository.findByDescription("Cup");
        assertEquals("Cup", uomOptional.orElse(new UnitOfMeasure()).getDescription());
    }


}