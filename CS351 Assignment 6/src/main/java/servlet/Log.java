package servlet;

public class Log {
	private double interestRate;
	private int principal;
	private int monthlyPayment;
	private String logEntry;
	
	Log(){
		interestRate = 0;
		principal = 0;
		monthlyPayment = 0;
		logEntry = "";
	}
	
	Log(double interestRate, int principal, int monthlyPayment, String logEntry){
		this.interestRate = interestRate;
		this.principal = principal;
		this.monthlyPayment = monthlyPayment;
		this.logEntry = logEntry;
		
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getPrincipal() {
		return principal;
	}

	public void setPrincipal(int principal) {
		this.principal = principal;
	}

	public int getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(int monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public String getLogEntry() {
		return logEntry;
	}

	public void setLogEntry(String logEntry) {
		this.logEntry = logEntry;
	}
	
	
}
