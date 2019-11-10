package scrapper;

import business.AtivoFinanceiro;
import business.CFD;

import java.util.List;

public interface AtivoFinanceiroScrapper {

	List<AtivoFinanceiro> getAtivosFinanceiros();

	/**
	 * 
	 * @param name
	 */
	AtivoFinanceiro getAtivoFinanceiro(String name);

	boolean isRunning();

	void setRunning(boolean running);

	void stop();

	void start();
}