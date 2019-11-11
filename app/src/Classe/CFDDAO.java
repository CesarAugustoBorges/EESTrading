package Classe;

public interface CFDDAO extends DAO {

	static CFDDAO getCFDDAO();

	/**
	 * 
	 * @param cfd
	 */
	double sell(CFD cfd);

	/**
	 * 
	 * @param id
	 */
	double getValue(int id);

	/**
	 * 
	 * @param user
	 */
	List<CFD> get(Utilizador user);

}