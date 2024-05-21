package eisiges.sudden_dao.criteria;

/**
 * @author kg6zvp
 */
public interface ClauseBuilder<T, Y> {
	/**
	 * append notEqual to given value and return
	 * @param value given value
	 * @return {@link FindBuilder}
	 */
	FindBuilder<T> notEqual(Y value);
	/**
	 * append notEqual to given value and return
	 * @param value given value
	 * @return {@link FindBuilder}
	 */
	default FindBuilder<T> ne(Y value) {
		return notEqual(value);
	}
	/**
	 * append equal to given value and return
	 * @param value given value
	 * @return {@link FindBuilder}
	 */
	FindBuilder<T> equal(Y value);
	/**
	 * append equal to given value and return
	 * @param value given value
	 * @return {@link FindBuilder}
	 */
	default FindBuilder<T> eq(Y value) {
		return equal(value);
	}
	/**
	 * append greaterThan to given value and return
	 * @param value given value
	 * @return {@link FindBuilder}
	 */
	FindBuilder<T> greaterThan(Y value);
	/**
	 * append greaterThan to given value and return
	 * @param value given value
	 * @return {@link FindBuilder}
	 */
	default FindBuilder<T> gt(Y value) {
		return greaterThan(value);
	}
	/**
	 * append lessThan to given value and return
	 * @param value given value
	 * @return {@link FindBuilder}
	 */
	FindBuilder<T> lessThan(Y value);
	/**
	 * append lessThan to given value and return
	 * @param value given value
	 * @return {@link FindBuilder}
	 */
	default FindBuilder<T> lt(Y value) {
		return lessThan(value);
	}
	/**
	 * append isNull and return
	 * @return {@link FindBuilder}
	 */
	FindBuilder<T> isNull();
	/**
	 * append isNotNull and return
	 * @return {@link FindBuilder}
	 */
	FindBuilder<T> isNotNull();
}
