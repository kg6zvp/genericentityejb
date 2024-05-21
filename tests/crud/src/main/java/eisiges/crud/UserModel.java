package eisiges.crud;

import java.util.Calendar;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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