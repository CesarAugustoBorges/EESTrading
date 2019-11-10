package data;

import business.AtivoFinanceiro;
import data.DAO;

public interface AtivoFinanceiroDAO extends DAO<AtivoFinanceiro,String> {

	AtivoFinanceiroDAO ativoFinanceiroDAO = new AtivoFincanceiroDAOConcrete();
	static AtivoFinanceiroDAO GetAtivoFinanceiroDAO(){
		return ativoFinanceiroDAO;
	}
}