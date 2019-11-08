package JSON;

import java.util.Comparator;

public abstract class AtivoFinanceiro{
	private String company;
	private double value;

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