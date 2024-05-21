package eisiges.generator;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import eisiges.sudden_dao.GenerateDAO;

@Entity
@GenerateDAO(daoName = "MyCustomDao")
public class CustomDaoNameEntity {
	@Id
	@GeneratedValue
	public Long id;

	public String hasCustomDao;
	public String someValue;
}