package business;

public class CFD {

	private double boughtValue;
	private double units;
	private Double topProfit;
	private Double stopLoss;
	private int id;
	private Utilizador utilizador;
	private AtivoFinanceiro ativoFinanceiro;

	public CFD() {
		this.boughtValue =0;
		this.units=0;
		this.stopLoss=0.0;
		this.topProfit=0.0;
		this.id=0;
		this.utilizador=null;
		this.ativoFinanceiro=null;
	}

	public CFD(double units, Double topProfit, Double stopLoss, int id, Utilizador utilizador, AtivoFinanceiro ativoFinanceiro) {
		this.boughtValue=ativoFinanceiro.getValue()*units;
		this.units = units;
		this.topProfit = topProfit;
		this.stopLoss = stopLoss;
		this.id = id;
		this.utilizador = utilizador;
		this.ativoFinanceiro = ativoFinanceiro;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Double getTopProfit() {
		return topProfit;
	}

	public void setTopProfit(double takeProfit) {
		this.topProfit = takeProfit;
	}

	public Double getStopLoss() {
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