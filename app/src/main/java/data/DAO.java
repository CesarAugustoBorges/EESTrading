package data;

public interface DAO<T,K> {

	/**
	 * 
	 * @param obj
	 */
	void put(T obj);

	/**
	 * 
	 * @param id
	 */
	T get(K id);

	/**
	 * 
	 * @param id
	 */
	void delete(K id);

	/**
	 *
	 * @param id
	 * @param obj
	 */
	void replace(K id, T obj);

}