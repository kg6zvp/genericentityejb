package eisiges.sudden_dao;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to generate a DAO for an entity class
 * @author kg6zvp
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface GenerateDAO {
	/**
	 * annotations to add to the generated DAO
	 * @return {@link Class}
	 */
	Class<? extends Annotation>[] annotations() default { /*jakarta.enterprise.context.ApplicationScoped.class*/ };
	/**
	 * custom DAO name, if desired. If not specified, an entity named Orders will have a DAO generated named OrdersDAO
	 * @return {@link String}
	 */
	String daoName() default "";
	/**
	 * custom parent class to use for generated DAO, useful for extending functionality but still allowing generator to
	 * assist you
	 * @return {@link Class}
	 */
	Class<? extends GenericPersistenceManager> parentClass() default GenericPersistenceManager.class;
}
