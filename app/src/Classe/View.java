package Classe;

import java.util.List;

public abstract class View {

	private EESTrading view;
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