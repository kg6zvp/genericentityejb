package eisiges.sudden_dao.criteria;

import eisiges.sudden_dao.SortingBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.SingularAttribute;

/**
 * A class building a find query
 * @author kg6zvp
 * @param <T> The type of Entity being found
 */
public class FindBuilder<T> {
	/**
	 * {@link jakarta.persistence.Entity} class that will be used as both the query root and query result
	 */
	protected Class<T> cArg;
	/**
	 * {@link EntityManager} for query
	 */
	protected EntityManager em;

	/**
	 * criteria builder
	 */
	protected CriteriaBuilder criteriaBuilder;
	/**
	 * criteria query
	 */
	protected CriteriaQuery<T> criteriaQuery;
	/**
	 * Relational query root
	 */
	protected Root<T> queryRoot;

	/**
	 * Create a {@link FindBuilder}
	 * @param cArg {@link jakarta.persistence.Entity} class that will be used as both the query root and query result
	 * @param em {@link EntityManager} for query
	 */
	public FindBuilder(Class<T> cArg, EntityManager em) {
		this.cArg = cArg;
		this.em = em;

		this.criteriaBuilder = this.em.getCriteriaBuilder();
		this.criteriaQuery = this.criteriaBuilder.createQuery(cArg);
		this.queryRoot = this.criteriaQuery.from(cArg);
	}

	/**
	 * append where clause
	 * @param attr {@link SingularAttribute}
	 * @return {@link ClauseBuilder} for attribute
	 * @param <Y> attribute type
	 */
	public <Y extends Comparable<? super Y>> ClauseBuilder<T, Y> where(SingularAttribute<? super T, Y> attr) {
		return new WhereClauseBuilder<>(this, attr);
	}

	/**
	 * Sort the results of the find query by a given attribute
	 * @param <Y> type of attribute being sorted by
	 * @param attribute the attribute to sort by
	 * @return a {@link SortingBuilder} instance for the given attribute
	 */
	public <Y> SortingBuilder<FindBuilder<T>, Y> sortBy(final SingularAttribute<? super T, Y> attribute) {
		final FindBuilder<T> selfReference = this;
		return new SortingBuilder<FindBuilder<T>, Y>() {
			@Override
			public FindBuilder<T> ascending() {
				criteriaQuery.orderBy(criteriaBuilder.asc(queryRoot.get(attribute)));
				return selfReference;
			}
			@Override
			public FindBuilder<T> descending() {
				criteriaQuery.orderBy(criteriaBuilder.desc(queryRoot.get(attribute)));
				return selfReference;
			}
		};
	}

	/**
	 * Build a query that can be run
	 * @return a {@link TypedQuery}
	 */
	public TypedQuery<T> build() {
		return this.em.createQuery(criteriaQuery);
	}
}