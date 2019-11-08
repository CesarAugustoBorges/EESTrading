package Classe;

public interface CFDDAO extends DAO {

	static CFDDAO getCFDDAO();

	/**
	 * 
	 * @param id
	 */
	double sell(int id);

	/**
	 * 
	 * @param id
	 */
	double getValue(int id);

}