package business;

import business.CFD;

import java.util.LinkedList;
import java.util.List;

public class Utilizador {

	private int id;
	private String username;
	private String password;
	private List<CFD> portfolio;
	private List<CFD> actives;

	public Utilizador(String username, String password){
		this.username = username;
		this.password = password;
		this.id = -1;
		this.portfolio = new LinkedList<>();
		this.actives = new LinkedList<>();
	}

	public int getId(){
		return id;
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

	private double money;
}