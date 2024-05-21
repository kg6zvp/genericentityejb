package eisiges.generator;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import eisiges.sudden_dao.GenerateDAO;

@Entity
@GenerateDAO(parentClass = CustomParent.class)
public class CustomEntity {
	@Id
	@GeneratedValue
	public Long id;

	public String nameOfEntity;
}