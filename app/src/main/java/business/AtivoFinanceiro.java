package business;

public abstract class AtivoFinanceiro {

	private String company;
	private double value;

	public AtivoFinanceiro(String company, double value) {
		this.company = company;
		this.value = value;
	}

	public AtivoFinanceiro(){
		this.company=null;
		this.value=0;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}


}