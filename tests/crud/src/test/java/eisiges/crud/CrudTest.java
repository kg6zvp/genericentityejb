package eisiges.crud;

import io.quarkus.test.junit.QuarkusTest;

import java.util.Calendar;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class CrudTest {
	@Inject
	UserGEM users;

	@PersistenceContext
	EntityManager em;

	@BeforeEach
	@Transactional
	public void beforeEach() {
		em.createQuery("DELETE FROM UserModel").executeUpdate();
	}

	@Test
	@Transactional
	public void testCreate() {
		Long userId = users.persist(UserModel
		    .builder()
		    .username("hmcgroin")
		    .fullName("Holden McGroin")
		    .birthDate(Calendar.getInstance())
		    .build()).getId();

		assertNotNull(em.find(UserModel.class, userId));
	}

	@Test
	@Transactional
	public void testGet() {
		UserModel bdUser = UserModel
		    .builder()
		    .username("bdover")
		    .fullName("Ben Dover")
		    .birthDate(Calendar.getInstance())
		    .build();
		em.persist(bdUser);
		em.flush();
		em.clear();

		assertNotNull(users.get(bdUser.getId()));
	}

	@Test
	@Transactional
	public void testFindMatchingWithString() {
		UserModel it = UserModel.builder()
		    .username("pfile")
		    .fullName("Peter File")
		    .birthDate(Calendar.getInstance())
		    .build();
		em.persist(it);
		em.flush();

		UserModel keyObject = UserModel.builder().fullName("Peter File").build();

		UserModel found = users.getMatching(keyObject).get(0);
		assertTrue(found.equals(it));
	}
}

