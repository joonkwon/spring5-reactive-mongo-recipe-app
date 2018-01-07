package guru.springframework.repositories;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import guru.springframework.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureReactiveRepositoryIT {

	@Autowired
	private UnitOfMeasureReactiveRepository uomReactiveRepository;
	
	@Before
	public void setUp() throws Exception {
		uomReactiveRepository.deleteAll().block();
	}

	@Test
	public void testSave() {
		//given
		String description = "description";
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription(description);
		//when
		UnitOfMeasure retUom = uomReactiveRepository.save(uom).block();
		//then
		assertEquals(Long.valueOf(1l), uomReactiveRepository.count().block());
		assertEquals(description, retUom.getDescription());
	}
	
	@Test
	public void testFindByDescription() {
		//given
		String description = "description";
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setDescription(description);
		uomReactiveRepository.save(uom).block();
		//when
		UnitOfMeasure fetchedUom = uomReactiveRepository.findByDescription(description).block();
		//then
		assertEquals(description, fetchedUom.getDescription());
	}

}
