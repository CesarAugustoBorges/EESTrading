package data;

import business.CFD;
import business.Utilizador;

import java.util.List;


public interface CFDDAO extends DAO<Integer, CFD> {

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

	List<CFD> get(Utilizador user);

	List<CFD> getPortfolio(Utilizador u);

	List<CFD> getVendidos(Utilizador u);
}