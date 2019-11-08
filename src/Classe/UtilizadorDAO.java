package Classe;

public interface UtilizadorDAO extends DAO {

	static AtivoFinanceiroDAO GetAtivoFinanceitoDAO();

	/**
	 * 
	 * @param user
	 * @param cfd
	 */
	double buyCFD(int user, int cfd);

	/**
	 * 
	 * @param user
	 * @param cfd
	 */
	double sellCFD(int user, int cfd);

	/**
	 * 
	 * @param user
	 * @param cfd
	 */
	void putPortfolio(int user, int cfd);

	/**
	 * 
	 * @param user
	 * @param cfd
	 */
	void registCFDBougth(int user, int cfd);

	/**
	 * 
	 * @param user
	 * @param maxSize
	 */
	List<CFD> getLastCFDBought(int user, int maxSize);

}