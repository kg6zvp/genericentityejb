package eisiges.crud;

import eisiges.sudden_dao.GenericPersistenceManager;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserGEM extends GenericPersistenceManager<UserModel, Long> {
	public UserGEM(){
		super(UserModel.class);
	}
}