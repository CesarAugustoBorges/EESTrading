package business;

import business.CFD;

import java.util.List;

public class Utilizador {

	private String username;
	private String password;
	private List<CFD> portfolio;
	private List<CFD> actives;

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

	private double money;
}