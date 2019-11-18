package business;

import business.CFD;

import java.util.LinkedList;
import java.util.List;

public class Utilizador {

	private String username;
	private String password;
	private double money;
	private List<CFD> portfolio;
	private List<CFD> actives;

	public Utilizador(String username, String password,double money){
		this.username = username;
		this.password = password;
		this.money = money;
		this.portfolio = new LinkedList<>();
		this.actives = new LinkedList<>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}


}