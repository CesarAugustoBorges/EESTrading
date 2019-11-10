package data;

import business.AtivoFinanceiro;
import data.DAO;

public interface AtivoFinanceiroDAO extends DAO<String,AtivoFinanceiro> {

	AtivoFinanceiroDAO ativoFinanceiroDAO = new AtivoFincanceiroDAOConcrete();
	static AtivoFinanceiroDAO GetAtivoFinanceiroDAO(){
		return ativoFinanceiroDAO;
	}
}