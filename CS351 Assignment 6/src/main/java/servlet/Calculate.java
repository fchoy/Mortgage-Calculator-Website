package servlet;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculate { //class to do calculations and determine the months required to pay off the principal.
	private int monthsToPayoff;
	private String date; //tells the years and months until payment is over
	private double totalInterestPaid; //keeps track of the total interest amount needed to be paid (sum all interestAmounts in array list)
	private StringBuilder logOutput; //creates the log for this calculation.
	
	Calculate(){
		date = "";
		totalInterestPaid = 0;
		monthsToPayoff = 0; //initially at 0 months
		logOutput = new StringBuilder();
	}
	
	public void calculate(int principal, int monthlyPayment, double monthlyInterestRate) {
		//get principal, interest rate, and monthly payment from input
		
		double totalPrincipal = (double) principal; //keeps track of principal remaining
		double interestThisMonth; //keeps track of interest for each payment made.
		
		//first, find total interest amount that needs to be paid.
		while(totalPrincipal > 0) { //If we still have principal to pay, that means that we also still have interest to pay. 
			//multiple total principal by monthly interest rate to get interest per month. (Decreases w/ each month)
			interestThisMonth = (totalPrincipal * monthlyInterestRate); 
			
			//add to total interest
			totalInterestPaid += interestThisMonth; 
			
			totalPrincipal -= (monthlyPayment - interestThisMonth); //subtract payment towards principal
			monthsToPayoff++; //add one month
		}
		
		//calculate the amount of months and years to pay off and save it as a string.
		int years = Math.floorDiv(monthsToPayoff, 12);
		int months = (monthsToPayoff % 12); 
		date =  "Your payment will take " + years + " years and " + months + " months to pay off.";
		
		//create the log w/ the three parameters and two results
		logOutput.append("Interest Rate: <input type='submit' name='interestRateLog' value='" + ((monthlyInterestRate * 12) * 100) + "'>"); //make into clickable input
		logOutput.append("<br> <br>"); 		
		logOutput.append("Principal: $ <input type='submit' name='principalLog' value='" + principal + "'>");
		logOutput.append("<br> <br>"); 		
		logOutput.append("Monthly Payment: $ <input type='submit' name='monthlyPaymentLog' value='" + monthlyPayment + "'>");
		logOutput.append("<br> <br>"); 		
		logOutput.append("<input type=text name='totalInterest' style='border:none; border-color: transparent; width: 40%;' readonly value='Total Interest : $" + getTotalInterestPaid() + "'>");
		logOutput.append("<br> <br>"); 		
		logOutput.append("<input type=text name='monthsToPayoff' style='border:none; border-color: transparent; width: 35%;' readonly value='" + getMonthsToPayoff() + "'>");
		//logOutput.append("<input type='submit' name='clearEntry' value='clear-entry'>");
		logOutput.append("<hr>");
		return;
	}
	
	//getter for string to display years and months till principal is paid off 
	public String getMonthsToPayoff() {
		return date;
	}
	
	//getter for total total interest paid
	public double getTotalInterestPaid() {
		return BigDecimal.valueOf(totalInterestPaid).setScale(2, RoundingMode.HALF_UP).doubleValue(); //returns a decimal of 2 decimal places, rounded up.
	}
	
	public String getLogOutput() {
		return logOutput.toString();
	}
}
