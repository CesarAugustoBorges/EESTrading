package data;

import business.AtivoFinanceiro;
import data.DAO;

import java.util.List;

public interface AtivoFinanceiroDAO extends DAO<String,AtivoFinanceiro> {

	AtivoFinanceiroDAO ativoFinanceiroDAO = new AtivoFincanceiroDAOConcrete();
	static AtivoFinanceiroDAO GetAtivoFinanceiroDAO(){
		return ativoFinanceiroDAO;
	}
	public List<AtivoFinanceiro> getAll();
}