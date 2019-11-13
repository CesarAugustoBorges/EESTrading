package Classe;

public class EESTrading extends Observable {

	private double fee;

	/**
	 * 
	 * @param user
	 */
	public List<CFD> getCFDsOfUser(String user) {
		// TODO - implement EESTrading.getCFDsOfUser
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param user
	 * @param pass
	 */
	public Utilizador login(String user, String pass) {

	}

	/**
	 * 
	 * @param utilizador
	 * @param cfd
	 */
	public boolean buy(Utilizador utilizador, CFD cfd) {
		// TODO - implement EESTrading.buy
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param utilizador
	 * @param cfd
	 */
	public double sell(Utilizador utilizador, CFD cfd) {
		// TODO - implement EESTrading.sell
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param username
	 * @param pass
	 */
	public boolean regist(String username, String pass) {
		// TODO - implement EESTrading.regist
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param utilizador
	 * @param value
	 */
	public void deposit(Utilizador utilizador, double value) {
		// TODO - implement EESTrading.deposit
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param utilizador
	 * @param value
	 */
	public boolean withdraw(Utilizador utilizador, double value) {
		// TODO - implement EESTrading.withdraw
		throw new UnsupportedOperationException();
	}

	public List<AtivoFinanceiro> getAtivos() {
		// TODO - implement EESTrading.getAtivos
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param user
	 */
	public List<CFD> getCFDsOfUser(Utilizador user) {
		// TODO - implement EESTrading.getCFDsOfUser
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param user
	 */
	public List<CFD> getOldCFDsOfUser(Utilizador user) {
		// TODO - implement EESTrading.getOldCFDsOfUser
		throw new UnsupportedOperationException();
	}

	public List<AtivoFinanceiro> getAtivosFinanceiros() {
		// TODO - implement EESTrading.getAtivosFinanceiros
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param cfd
	 * @param value
	 */
	public boolean setCFDTopProfit(CFD cfd, double value) {
		// TODO - implement EESTrading.setCFDTopProfit
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param cfd
	 * @param value
	 */
	public boolean setCFDStopLoss(CFD cfd, double value) {
		// TODO - implement EESTrading.setCFDStopLoss
		throw new UnsupportedOperationException();
	}

}