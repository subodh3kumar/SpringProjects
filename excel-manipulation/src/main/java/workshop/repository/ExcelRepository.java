package workshop.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import workshop.entity.Customer;

@Repository
public class ExcelRepository {

	private static final Logger log = LoggerFactory.getLogger(ExcelRepository.class);

	private EntityManagerFactory emf;

	public ExcelRepository(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public void save(List<Customer> customers) {
		log.info("save() method called");

		EntityManager entityManager = emf.createEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();

		try {
			transaction.begin();
			for (Customer customer : customers) {
				entityManager.persist(customer);
				entityManager.flush();
				entityManager.clear();
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
		log.info("number of customers persist sucessfully: {}", customers.size());
	}
}
