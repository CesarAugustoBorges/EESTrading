package data;

import business.CFD;
import business.Utilizador;

import java.util.List;

public interface UtilizadorDAO extends DAO<String, Utilizador> {


	/**
	 * 
	 * @param user
	 * @param cfd
	 */
	double addCFD(Utilizador user, CFD cfd);

	/**
	 * 
	 * @param user
	 * @param cfd
	 */
	double removeCFD(Utilizador user, CFD cfd);

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
	void removeMoney(Utilizador user, double value);

	boolean login(String username, String password);

}