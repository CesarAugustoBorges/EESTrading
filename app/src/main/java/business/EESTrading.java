package business;

import java.util.List;
import java.util.Map;

public class EESTrading {

	private Map<Integer, CFD> cfds;
	private Map<String, Utilizador> users;
	private List<AtivoFinanceiro> ativos;
	private double fee;

	public List<CFD> getCFDs() {
		// TODO - implement EESTrading.getCFDs
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param company
	 */
	public List<CFD> getCFDsOfCompany(String company) {
		// TODO - implement EESTrading.getCFDsOfCompany
		throw new UnsupportedOperationException();
	}

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
	public boolean login(String user, String pass) {
		// TODO - implement EESTrading.login
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param username
	 * @param cfd
	 * @param value
	 */
	public void buy(String username, int cfd, double value) {
		// TODO - implement EESTrading.buy
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param username
	 * @param cfd
	 */
	public double sell(String username, int cfd) {
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
	 * @param username
	 * @param value
	 */
	public void deposit(String username, double value) {
		// TODO - implement EESTrading.deposit
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 * @param username
	 * @param value
	 */
	public void withdraw(String username, double value) {
		// TODO - implement EESTrading.withdraw
		throw new UnsupportedOperationException();
	}

	public List<AtivoFinanceiro> getAtivos() {
		// TODO - implement EESTrading.getAtivos
		throw new UnsupportedOperationException();
	}

}