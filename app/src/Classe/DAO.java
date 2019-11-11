package Classe;

public interface DAO {

	/**
	 * 
	 * @param obj
	 */
	K put(T obj);

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