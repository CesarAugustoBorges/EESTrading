package Classe;

public abstract class View {

	EESTrading view;
	private String username;

	/**
	 * 
	 * @param cfd
	 */
	public abstract String print(CFD cfd);

	/**
	 * 
	 * @param cfds
	 */
	public abstract String print(List<CFD> cfds);

}