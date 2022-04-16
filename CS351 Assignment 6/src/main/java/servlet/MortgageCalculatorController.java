package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class MortgageCalculatorController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private StringBuilder logData;
	private String totalInterestText, yearsMonthsTillPayoff;
	private ArrayList<Log> logList; //will hold the log entries individually
       
    public MortgageCalculatorController() {
        super();
        logData = new StringBuilder();
        totalInterestText = "";
        yearsMonthsTillPayoff = "";
        logList = new ArrayList<Log>(); 
        // TODO Auto-generated constructor stub
    }

	/**
	 *
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("clear-all") != null) {
			//clear interest rate input
			request.getSession().setAttribute("interestRate", 0);
			//clear principal input
			request.getSession().setAttribute("principal", 0);
			//clear monthly payment input
			request.getSession().setAttribute("monthlyPayment", 0);
			//clear result1 
			totalInterestText = "";
			request.getSession().setAttribute("totalInterest", totalInterestText);
			//clear result2 
			yearsMonthsTillPayoff = "";
			request.getSession().setAttribute("yearsMonthsTillPayoff", yearsMonthsTillPayoff);
			//do not clear the log
			request.getSession().setAttribute("log", logData.toString());
			
			request.getRequestDispatcher("mortgagecalculator.jsp").forward(request, response);
		}
		
		else if(request.getParameter("clear-log") != null) {
			//get all inputs, and values on the screen, keeping them the same, but clear the log. 
			double interestRate = 0;
			int principal = 0;
			int monthlyPayment = 0;
			
			//check each parameter to see if they are not empty, null, and only containing whitespace
			if(request.getParameter("interest-rate") != null && !request.getParameter("interest-rate").isBlank() && !request.getParameter("interest-rate").isEmpty() && !request.getParameter("interest-rate").equals("")) {
				interestRate = Double.parseDouble(request.getParameter("interest-rate")); 
			}
						
			if(request.getParameter("principal") != null && !request.getParameter("principal").toString().isBlank() && !request.getParameter("principal").toString().isEmpty() && !request.getParameter("principal").equals("")) {
				principal = Integer.parseInt(request.getParameter("principal")); 
			}
			
			if(request.getParameter("monthlyPayment") != null && !request.getParameter("monthlyPayment").toString().isEmpty() && !request.getParameter("monthlyPayment").toString().isEmpty() && !request.getParameter("monthlyPayment").equals("") ) {
				monthlyPayment = Integer.parseInt(request.getParameter("monthly-payment"));
			}
			
			//double interestRate = Double.parseDouble(request.getParameter("interest-rate")); 
			//int principal = Integer.parseInt(request.getParameter("principal")); 
			//int monthlyPayment = Integer.parseInt(request.getParameter("monthly-payment"));
			
			logList.clear();
			logData.setLength(0); //clear the log	
				
			request.getSession().setAttribute("interestRate", interestRate);
			request.getSession().setAttribute("principal", principal);
			request.getSession().setAttribute("monthlyPayment", monthlyPayment);
			request.getSession().setAttribute("totalInterest", totalInterestText);
			request.getSession().setAttribute("yearsMonthsTillPayoff", yearsMonthsTillPayoff);
	
			request.getSession().setAttribute("log", logData.toString()); //clear the log
			
			request.getRequestDispatcher("mortgagecalculator.jsp").forward(request, response);
		}
		
		else if(request.getParameter("calculate") != null) {
			//first, get input entered by user
			Calculate calculations = new Calculate();
			int principal = 0;
			int monthlyPayment = 0;
			double monthlyInterestRate = 0;
			
			if(request.getParameter("principal") != null && !request.getParameter("principal").isBlank() && !request.getParameter("principal").isEmpty()) {
				principal = Integer.parseInt(request.getParameter("principal"));
			}
			
			if(request.getParameter("monthly-payment") != null && !request.getParameter("monthly-payment").isBlank() && !request.getParameter("monthly-payment").isEmpty()) {
				monthlyPayment = Integer.parseInt(request.getParameter("monthly-payment"));
			}			
			
			if(request.getParameter("interest-rate") != null && !request.getParameter("interest-rate").isBlank() && !request.getParameter("interest-rate").isEmpty()) {
				monthlyInterestRate = (Double.parseDouble(request.getParameter("interest-rate")) / 100) / 12;
			}
			
			calculations.calculate(principal, monthlyPayment, monthlyInterestRate);
			
			//keep values of input
			request.setAttribute("interestRate", (monthlyInterestRate * 12) * 100); //multiply by 12, then 100 to get the original percentage value
			request.setAttribute("principal", principal);
			request.setAttribute("monthlyPayment", monthlyPayment);

			//display results
			totalInterestText = "Total Interest Paid: $" + calculations.getTotalInterestPaid();
			yearsMonthsTillPayoff = calculations.getMonthsToPayoff();
			request.setAttribute("totalInterest", totalInterestText);
			request.setAttribute("yearsMonthsTillPayoff", yearsMonthsTillPayoff);
			
			//create log object, use it to hold each of the three values. Used for keeping track of individual entries.
			Log newLog;
			if(monthlyInterestRate != 0) {
				newLog = new Log(Double.parseDouble(request.getParameter("interest-rate")), principal, monthlyPayment, calculations.getLogOutput());
			}
			else {
				newLog = new Log(0, principal, monthlyPayment, calculations.getLogOutput());
			}
			logList.add(newLog); //add to ArrayList of log objects.
			
			
			//append log from the newly created log object. 
			logData.append(newLog.getLogEntry()); 
			request.setAttribute("log", logData.toString());
			
			request.getRequestDispatcher("mortgagecalculator.jsp").forward(request, response);
		}
		
		else if(request.getParameter("interestRateLog") != null) { //clicked on a log parameter 
			//only change appropriate parameter, keep result, other parameters, and log the same. 
			double interestRate = Double.parseDouble(request.getParameter("interestRateLog"));
			request.setAttribute("interestRate", interestRate);
			
			int principal = Integer.parseInt(request.getParameter("principal"));
			request.setAttribute("principal", principal);
			
			int monthlyPayment = Integer.parseInt(request.getParameter("monthly-payment"));
			request.setAttribute("monthlyPayment", monthlyPayment);
			
			String totalInterestResult = request.getParameter("totalInterest");
			request.setAttribute("totalInterest", totalInterestResult);
			
			String date = request.getParameter("yearsMonthsTillPayoff");
			request.setAttribute("yearsMonthsTillPayoff", date);

			request.setAttribute("log", logData.toString());
			
			request.getRequestDispatcher("mortgagecalculator.jsp").forward(request, response);
		}
		
		else if(request.getParameter("monthlyPaymentLog") != null) { //clicked on a log parameter 
			//only change appropriate parameter, keep result, other parameters, and log the same. 
			double interestRate = Double.parseDouble(request.getParameter("interest-rate"));
			request.setAttribute("interestRate", interestRate);
			
			int principal = Integer.parseInt(request.getParameter("principal"));
			request.setAttribute("principal", principal);
			
			int monthlyPayment = Integer.parseInt(request.getParameter("monthlyPaymentLog"));
			request.setAttribute("monthlyPayment", monthlyPayment);
			
			String totalInterestResult = request.getParameter("totalInterest");
			request.setAttribute("totalInterest", totalInterestResult);
			
			String date = request.getParameter("yearsMonthsTillPayoff");
			request.setAttribute("yearsMonthsTillPayoff", date);

			request.setAttribute("log", logData.toString());
			
			request.getRequestDispatcher("mortgagecalculator.jsp").forward(request, response);
		}
		
		else if(request.getParameter("principalLog") != null) { //clicked on a log parameter 
			//only change appropriate parameter, keep result, other parameters, and log the same. 
			double interestRate = Double.parseDouble(request.getParameter("interest-rate"));
			request.setAttribute("interestRate", interestRate);
			
			int principal = Integer.parseInt(request.getParameter("principalLog"));
			request.setAttribute("principal", principal);
			
			int monthlyPayment = Integer.parseInt(request.getParameter("monthly-payment"));
			request.setAttribute("monthlyPayment", monthlyPayment);
			
			String totalInterestResult = request.getParameter("totalInterest");
			request.setAttribute("totalInterest", totalInterestResult);
			
			String date = request.getParameter("yearsMonthsTillPayoff");
			request.setAttribute("yearsMonthsTillPayoff", date);

			request.setAttribute("log", logData.toString());
			
			request.getRequestDispatcher("mortgagecalculator.jsp").forward(request, response);
		}
		
		else if(request.getParameter("clearEntry") != null) { //delete a specific log entry depending on the parameters entered, keep everything else the same.
			//we need to search the arraylist and delete the index for which the entry is in
			double interestRate = BigDecimal.valueOf(Double.parseDouble(request.getParameter("interest-rate"))).setScale(4, RoundingMode.HALF_UP).doubleValue();  //round the interestRate to 4 decimal places. (more specific)
			int principal = Integer.parseInt(request.getParameter("principal"));
			int monthlyPayment = Integer.parseInt(request.getParameter("monthly-payment"));
			String totalInterestResult = request.getParameter("totalInterest");
			String date = request.getParameter("yearsMonthsTillPayoff");
			
			//search the arrayList, find the first entry that matches all criteria for deletion. 
			for(int i = 0; i < logList.size(); ++i) {
				if(BigDecimal.valueOf(logList.get(i).getInterestRate()).setScale(4, RoundingMode.HALF_UP).doubleValue() == interestRate && logList.get(i).getPrincipal() == principal && logList.get(i).getMonthlyPayment() == monthlyPayment){
					logList.remove(i); //remove the entry from the ArrayList
					break;
				}
			}

			//then, we need to clear logData and iterate through the arrayList of logs, appending each of their logEntry fields.
			logData.setLength(0);
			for(int i = 0; i < logList.size(); ++i) {
				logData.append(logList.get(i).getLogEntry());
			}
			
			//display
			interestRate = Double.parseDouble(request.getParameter("interest-rate")); //change back to original value grabbed from the form
			request.setAttribute("interestRate", interestRate);
			request.setAttribute("principal", principal);
			request.setAttribute("monthlyPayment", monthlyPayment);
			request.setAttribute("totalInterest", totalInterestResult);
			request.setAttribute("yearsMonthsTillPayoff", date);
			request.setAttribute("log", logData.toString());
			request.getRequestDispatcher("mortgagecalculator.jsp").forward(request, response);

		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
