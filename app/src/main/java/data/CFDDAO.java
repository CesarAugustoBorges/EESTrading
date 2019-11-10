package data;

import business.CFD;
import business.Utilizador;

import java.util.List;


public interface CFDDAO extends DAO<Integer, CFD> {

	CFDDAO cfdDAO = new CFDDAOConcrete();
	static CFDDAO getCFDDAO(){return  cfdDAO;}

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

	List<CFD> get(Utilizador user);

}