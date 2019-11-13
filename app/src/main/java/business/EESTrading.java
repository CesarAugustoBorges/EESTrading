package business;

import data.AtivoFinanceiroDAO;
import data.CFDDAO;
import data.UtilizadorDAO;
import scrapper.AtivoFinanceiroScrapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EESTrading {
	private double fee;
	private List<AtivoFinanceiroScrapper> scrappers;

	private CFDDAO cfdDAO = CFDDAO.getCFDDAO();
	private AtivoFinanceiroDAO ativoFinanceiroDAO = AtivoFinanceiroDAO.GetAtivoFinanceiroDAO();
	private UtilizadorDAO utilizadorDAO = UtilizadorDAO.GetUtilizadorDAO();


	public List<AtivoFinanceiro> getAtivos() {
		List<AtivoFinanceiro> res = new LinkedList<>();
		for(AtivoFinanceiroScrapper scrapper : scrappers){
			res.addAll(scrapper.getAtivosFinanceiros());
		}
		return res;
	}

	public void putAtivosFinanceiros(List<AtivoFinanceiro> ativoFinanceiros){
		ativoFinanceiros.forEach(ativo -> ativoFinanceiroDAO.replace(ativo.getCompany(), ativo));
	}


	/**
	 *
	 * @param user
	 */
	public List<CFD> getCFDsOfUser(Utilizador user) {
		return cfdDAO.get(user);
	}

	/**
	 *
	 * @param user
	 * @param pass
	 */
	public Utilizador login(String user, String pass) {
		if(utilizadorDAO.login(user, pass)){
			return utilizadorDAO.get(user);
		}
		return null;
	}

	/**
	 *
	 * @param utilizador
	 * @param cfd
	 */
	public boolean buy(Utilizador utilizador, CFD cfd) {
		int idCFD = cfdDAO.put(cfd);
		if(idCFD > 0){
			double value = utilizadorDAO.addCFD(utilizador, cfd);
			return withdraw(utilizador, value);
		}
		return false;
	}

	/**
	 *
	 * @param utilizador
	 * @param cfd
	 */
	public double sell(Utilizador utilizador, CFD cfd) {
		CFD sould = cfdDAO.get(cfd.getId());
		utilizadorDAO.addMoney(utilizador, cfd.getValue());
		cfdDAO.delete(cfd.getId());
		utilizador = utilizadorDAO.get(utilizador.getUsername());
		return cfd.getValue();
	}

	/**
	 *
	 * @param username
	 * @param pass
	 */
	public Utilizador regist(String username, String pass) {
		Utilizador novo = new Utilizador(username, pass);
		String id =  utilizadorDAO.put(novo);
		System.out.println("DEBUG: Trying to regist user:" + novo.getUsername() + " " + novo.getPassword());
		if(id != null) novo = utilizadorDAO.get(username);
		else novo = null;
		return novo;
	}

	/**
	 *
	 * @param utilizador
	 * @param value
	 */
	public void deposit(Utilizador utilizador, double value) {
		utilizadorDAO.addMoney(utilizador, value);
	}

	/**
	 *
	 * @param utilizador
	 * @param value
	 */
	public boolean withdraw(Utilizador utilizador, double value) {
		if(utilizador.getMoney() < value) return false;
		return utilizadorDAO.removeMoney(utilizador, value);
	}

	public boolean setCFDTopProfit(CFD cfd, double topProfit) {
		if (topProfit > cfd.getValue()) {
			cfd.setTopProfit(topProfit);
			cfdDAO.replace(cfd.getId(), cfd);
			return true;
		}
		return false;
	}

	public boolean setCFDStopLoss(CFD cfd, double stopLoss) {
		if (stopLoss < cfd.getValue()) {
			cfd.setTopProfit(stopLoss);
			cfdDAO.replace(cfd.getId(), cfd);
			return true;
		}
		return false;
	}
}