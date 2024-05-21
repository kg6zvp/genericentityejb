package eisiges.sudden_dao.criteria;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.metamodel.SingularAttribute;

/**
 * Where clause builder for queries
 * @author kg6zvp
 */
public class WhereClauseBuilder<T, Y extends Comparable<? super Y>> implements ClauseBuilder<T, Y> {
	final FindBuilder<T> ref;
	SingularAttribute<? super T, Y> attr;

	/**
	 * default constructor for where clause builder
	 * @param reference {@link FindBuilder}
	 * @param attr {@link SingularAttribute}
	 */
	public WhereClauseBuilder(final FindBuilder<T> reference, SingularAttribute<? super T, Y> attr) {
		this.ref = reference;
		this.attr = attr;
	}

	@Override
	public FindBuilder<T> notEqual(Y value) {
		ref.criteriaQuery.where(
			ref.criteriaBuilder.notEqual(
				ref.queryRoot.get(attr)
				, value
			)
		);
		return ref;
	}

	@Override
	public FindBuilder<T> equal(Y value) {
		ref.criteriaQuery.where(
			ref.criteriaBuilder.equal(
				ref.queryRoot.get(attr)
				, value
			)
		);
		return ref;
	}

	@Override
	public FindBuilder<T> greaterThan(Y value) {
		ref.criteriaQuery.where(
			ref.criteriaBuilder.greaterThan(
				(Expression<Y>)ref.queryRoot.get(attr)
				, value
			)
		);
		return ref;
	}

	@Override
	public FindBuilder<T> lessThan(Y value) {
		ref.criteriaQuery.where(
			ref.criteriaBuilder.lessThan(
				(Expression<Y>)ref.queryRoot.get(attr)
				, value
			)
		);
		return ref;
	}

	@Override
	public FindBuilder<T> isNull() {
		ref.criteriaQuery.where(
			ref.criteriaBuilder.isNull(
				ref.queryRoot.get(attr)
			)
		);
		return ref;
	}

	@Override
	public FindBuilder<T> isNotNull() {
		ref.criteriaQuery.where(
			ref.criteriaBuilder.isNotNull(
				ref.queryRoot.get(attr)
			)
		);
		return ref;
	}
	
}
