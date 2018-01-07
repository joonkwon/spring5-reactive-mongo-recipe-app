package guru.springframework.repositories;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.Recipe;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryIT {

	@Autowired
	RecipeReactiveRepository recipeReactiveRepository;
	
	@Before
	public void setUp() throws Exception {
		recipeReactiveRepository.deleteAll().block();
	}

	@Test
	public void testSave() throws Exception {
		// given
		Recipe recipe = new Recipe();
		recipe.setDescription("new description");
		recipe.setPrepTime(3);
		//when
		Recipe retRecipe = recipeReactiveRepository.save(recipe).block();
		//then
		Long count = recipeReactiveRepository.count().block();
		assertEquals(Long.valueOf(1l), count);
		assertEquals("new description", retRecipe.getDescription());
		
	}

}
