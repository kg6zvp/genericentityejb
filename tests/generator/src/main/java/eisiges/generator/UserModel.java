package eisiges.generator;

import java.util.Calendar;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import eisiges.sudden_dao.GenerateDAO;

@GenerateDAO(annotations = {
	ApplicationScoped.class,
	Deprecated.class
})
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"birthDate"})
@ToString
public class UserModel {
	@Id
	@GeneratedValue
	@Getter @Setter Long id;

	@Getter @Setter String username;

	@Getter @Setter String fullName;

	@Temporal(TemporalType.DATE)
	@Getter @Setter Calendar birthDate;
}