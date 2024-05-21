package eisiges.sudden_dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

/**
 * QueryBuilder, used to construct complex queries
 * @param <T> result class type param
 */
public class QueryBuilder<T> {
	/**
	 * class of query result
	 */
	protected Class<T> cArg;
	/**
	 * {@link EntityManager} for use in query builder
	 */
	protected EntityManager em;
	/**
	 * {@link StringBuilder} for building the query
	 */
	protected StringBuilder query;

	/**
	 * default constructor
	 * @param cArg class of query result
	 * @param em {@link EntityManager} for use in query builder
	 * @param baseQuery query string to start with before adding where clauses (select statement)
	 */
	public QueryBuilder(Class<T> cArg, EntityManager em, String baseQuery) {
		this.cArg = cArg;
		this.em = em;
		this.query = new StringBuilder(baseQuery);
	}

	/**
	 * Produce query ready for execution
	 * @return {@link TypedQuery}
	 */
	public TypedQuery<T> build() {
		return em.createQuery(query.toString(), cArg);
	}
}