package guru.springframework.services.serviceImplementations;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.repositories.UnitOfMeasureRepository;
import guru.springframework.services.UnitOfMeasureService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UnitOfMeasureImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    public UnitOfMeasureImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> findAll() {
        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
        unitOfMeasureRepository.findAll().forEach(unitOfMeasure -> unitOfMeasureCommands.add(
                unitOfMeasureToUnitOfMeasureCommand.convert(unitOfMeasure)
        ));
        return unitOfMeasureCommands;
    }
}
