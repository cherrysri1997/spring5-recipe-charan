package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.services.serviceImplementations.RecipeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    @Mock
    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    Model model;

    IndexController controller;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new IndexController(recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        mockMvc.perform(get("/index/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
//         If the urlTemplate in mockMvc.perform(get()) is provided with "/index", it will end up in failure of testcase
//         https://stackoverflow.com/a/18815059/9504383 => Reason is mentioned here. Adding an extra '/',
//         at the end of the url solves the issue ("/index/") => https://stackoverflow.com/a/55247840/9504383.

    }

    //    @SuppressWarnings("unchecked")
    @Test
    public void getIndexPage() {

        // given
        Set<Recipe> recipes = new HashSet<>();

        Recipe recipe1 = new Recipe();
        recipe1.setDescription("A");

        Recipe recipe2 = new Recipe();
        recipe2.setDescription("B");

//        assertEquals(recipe1, recipe2);
//        If both recipe1 and recipe2 are declared as new Recipe objects with no properties set,
//        then recipe1 and recipe2 are considered as same instantiations.

        recipes.add(recipe1);
        recipes.add(recipe2);

        when(recipeService.getRecipes()).thenReturn(recipes);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        // when
        String page = controller.getIndexPage(model);
        assertEquals("index", page);
        verify(recipeService, times(1)).getRecipes();

//        verify(model, times(1)).addAttribute(eq("recipes"), anySet());

        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> recipes1 = argumentCaptor.getValue();
        recipes1.forEach(recipe -> System.out.println(recipe.getDescription() + " Hello!"));

        assertEquals(2, recipes1.size());

        verify(recipeRepository, times(0)).findAll();
        // Actually, recipeService.getRecipes() should invoke the recipeRepository.findAll() method.
        // So, I thought this findAll() method will be called once.
        // But I see that I'm wrong because it was never called this method.
        // Hence, I think of two possible ways for this to happen.
        // Either an object for repo is never created as we are simply mocking the service or
        // the repo wired in the service is a different instance to that of the repo I created by using @Mock.

        // @Mock doesn't call the constructor of the class to create an instance.
        // https://stackoverflow.com/questions/3138153/how-does-mockito-create-an-instance-of-the-mock-object
    }
}
