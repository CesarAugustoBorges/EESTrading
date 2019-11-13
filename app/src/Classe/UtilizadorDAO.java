package Classe;

public interface UtilizadorDAO extends DAO {

	static AtivoFinanceiroDAO GetAtivoFinanceitoDAO();

	/**
	 * 
	 * @param user
	 * @param cfd
	 */
	void putPortfolio(Utilizador user, CFD cfd);

	/**
	 * 
	 * @param user
	 * @param maxSize
	 */
	List<CFD> getLastCFDBought(Utilizador user, int maxSize);

	/**
	 * 
	 * @param user
	 * @param value
	 */
	void addMoney(Utilizador user, double value);

	/**
	 * 
	 * @param user
	 * @param value
	 */
	boolean removeMoney(Utilizador user, double value);

	/**
	 * 
	 * @param username
	 * @param password
	 */
	boolean login(String username, int password);

}