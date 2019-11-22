package business;

public abstract class AtivoFinanceiro {

	private String company;
	private double value;
	private String type;

	public AtivoFinanceiro(String company, double value,String type) {
		this.company = company;
		this.value = value;
		this.type=type;
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

	public String getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) return true;
		if(obj instanceof AtivoFinanceiro){
			return this.company.equals(((AtivoFinanceiro)obj).getCompany());
		}
		return false;
	}
}