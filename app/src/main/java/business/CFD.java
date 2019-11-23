package business;


import java.time.LocalDateTime;

public class CFD {
	private double boughtValue;
	private double units;
	private Double topProfit;
	private Double stopLoss;
	private int id;
	private Utilizador utilizador;
	private AtivoFinanceiro ativoFinanceiro;
	private LocalDateTime data;


	public CFD() {

	}

	public CFD(CFD cfd){
		this.setBoughtValue(cfd.getBoughtValue());
		this.setUnits(cfd.getUnits());
		this.setTopProfit(cfd.getTopProfit());
		this.setStopLoss(cfd.getStopLoss());
		this.setId(cfd.getId());
		this.setAtivoFinanceiro(cfd.getAtivoFinanceiro());
		this.setUtilizador(cfd.getUtilizador());
		this.setData(cfd.getData());
	}

	public CFD(double boughtValue, double units, Double topProfit, Double stopLoss, int id, Utilizador utilizador,
			   AtivoFinanceiro ativoFinanceiro, LocalDateTime data) {
		this.boughtValue= boughtValue;
		this.units = units;
		this.topProfit = topProfit;
		this.stopLoss = stopLoss;
		this.id = id;
		this.utilizador = utilizador;
		this.ativoFinanceiro = ativoFinanceiro;
		this.data = data;
	}


	public CFD(double boughtValue, double units, Double topProfit, Double stopLoss, int id, Utilizador utilizador,
			   AtivoFinanceiro ativoFinanceiro) {
		this( boughtValue ,units,topProfit,stopLoss,id,utilizador,
				ativoFinanceiro, LocalDateTime.now());
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
		return units*ativoFinanceiro.getValue();
	}

	public String getName() {
		return ativoFinanceiro.getCompany();
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public boolean checkTopprofit(){
		return topProfit < getValue();
	}

	public boolean checkStopLoss(){
		return stopLoss > getValue();
	}

	@Override
	public String toString(){
		return ativoFinanceiro.toString() + " comprado ( " + boughtValue + "$ ) em " +
				data.getYear() + "-" + data.getMonth() + "-" + data.getDayOfMonth() + " | valor atual -" + getValue() + "$";
	}

}