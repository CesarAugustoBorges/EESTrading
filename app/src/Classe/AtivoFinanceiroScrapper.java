package Classe;

public interface AtivoFinanceiroScrapper {

	List<AtivoFinanceiro> getAtivosFinanceiros();

	/**
	 * 
	 * @param name
	 */
	AtivoFinanceiro getAtivoFinanceiro(String name);

	void isRunning();

	void setRunning();

	void start();

	void stop();

}