package Classe;

public interface AtivoFinanceiroDAO extends DAO {

	static AtivoFinanceiroDAO GetAtivoFinanceitoDAO();

	List<AtivoFinanceiro> getAll();

}