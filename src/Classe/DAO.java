package Classe;

public interface DAO {

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

}