package guru.springframework.converters;

import guru.springframework.commands.CategoryCommand;
import guru.springframework.domain.Category;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@SuppressWarnings("ConstantConditions")
@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

    @Override
    @Synchronized
    @Nullable
    public Category convert(CategoryCommand source) {
        if(source == null) {
            return null;
        }
        Category category = new Category();
        category.setId(source.getId());
        category.setDescription(source.getDescription());
        return category;
    }

}