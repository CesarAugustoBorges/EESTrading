package business;

public class CFD {

	private double boughtValue;
	private double units;
	private double takeProfit;
	private double stopLoss;
	private int id;
	private Utilizador utilizador;
	private AtivoFinanceiro ativoFinanceiro;

	public Utilizador getUtilizador() {
		return utilizador;
	}

	public void setUtilizador(Utilizador utilizador) {
		this.utilizador = utilizador;
	}

	public double getBoughtValue() {
		return boughtValue;
	}

	public void setBoughtValue(double boughtValue) {
		this.boughtValue = boughtValue;
	}

	public double getUnits() {
		return units;
	}

	public void setUnits(double units) {
		this.units = units;
	}

	public double getTakeProfit() {
		return takeProfit;
	}

	public void setTakeProfit(double takeProfit) {
		this.takeProfit = takeProfit;
	}

	public double getStopLoss() {
		return stopLoss;
	}

	public void setStopLoss(double stopLoss) {
		this.stopLoss = stopLoss;
	}

	public AtivoFinanceiro getAtivoFinanceiro() {
		return ativoFinanceiro;
	}

	public void setAtivoFinanceiro(AtivoFinanceiro ativoFinanceiro) {
		this.ativoFinanceiro = ativoFinanceiro;
	}

	public double getValue() {
		return units * ativoFinanceiro.getValue();
	}

	public String getName() {
		return ativoFinanceiro.getCompany();
	}

}