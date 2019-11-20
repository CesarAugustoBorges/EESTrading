package data;

import business.AtivoFinanceiro;
import data.DAO;

import java.util.List;

public interface AtivoFinanceiroDAO extends DAO<String,AtivoFinanceiro> {

	public List<AtivoFinanceiro> getAll();
}