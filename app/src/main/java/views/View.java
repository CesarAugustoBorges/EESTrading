package views;

import business.AtivoFinanceiro;
import business.CFD;
import business.EESTrading;
import business.Utilizador;

import java.util.*;

public abstract class View {

	private EESTrading trading;
	private String username;
	private int currentMenu;

	public abstract void menuInicial();

	public abstract void menuRegistar();

	public abstract void layout(String title);

	/**
	 *
	 * @param utilizador
	 */
	public abstract void menuUtilizador(Utilizador utilizador);

	/**
	 *
	 * @param utilizador
	 * @param cfds
	 */
	public abstract void menuMeusCFDs(Utilizador utilizador, List<CFD> cfds);

	/**
	 *
	 * @param utilizador
	 * @param ativos
	 */
	public abstract void menuAtivosDisponiveis(Utilizador utilizador, List<AtivoFinanceiro> ativos);

	/**
	 *
	 * @param utilizador
	 * @param cfd
	 */
	public abstract void menuCFDPossuido(Utilizador utilizador, CFD cfd);

	/**
	 *
	 * @param utilizador
	 * @param cfd
	 */
	public abstract void menuDeCompraCFD(Utilizador utilizador, CFD cfd);

	/**
	 *
	 * @param obj
	 * @param arg1
	 */
	private void update(Object obj, Object arg1) {
		// TODO - implement View.update
		throw new UnsupportedOperationException();
	}

}