package guru.springframework.repositories;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.Category;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryReactiveRepositoryIT {

		@Autowired
		CategoryReactiveRepository catReactiveRepository;

		@Before
		public void setUp() throws Exception {
			catReactiveRepository.deleteAll().block();
		}
		
		@Test
		public void insertTest() throws Exception {
			Category cat = new Category();
			cat.setDescription("test category description");
			catReactiveRepository.save(cat).block();
			assertEquals(Long.valueOf(1L), catReactiveRepository.count().block());			
		}
		
		@Test
		public void findByDescriptionTest() throws Exception {
			Category cat = new Category();
			String description = "test category description";
			cat.setDescription(description);
			catReactiveRepository.save(cat).block();
			Category retCat = catReactiveRepository.findByDescription(description).block();
			assertEquals(description, retCat.getDescription());
		}
}
