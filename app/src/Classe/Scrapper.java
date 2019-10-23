package Classe;

import java.util.List;

public abstract class Scrapper {

	public abstract List<CFD> getCFDs();

	/**
	 * 
	 * @param company
	 */
	public abstract List<CFD> getCFDs(String company);

}