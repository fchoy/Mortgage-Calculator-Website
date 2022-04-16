<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Mortgage Calculator</title>
<script src="script.js"></script>
<link href="styles.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div id="body-div">
		<div id="form-div">
			<form action="MortgageCalculatorController" method="get" name="input-form" onsubmit="return validate()" style="width: 60%; position: relative; left:400px; top: 50px;">
				<label for="interest-rate-input">Interest Rate :</label>
				<input type="text" id="interest-rate-input" name="interest-rate" value="${interestRate}"> %
				<br/>
				
				<label for="principal-input">Principal :</label>
				$ <input type="text" id="principal-input" name="principal" value="${principal}">
				<br/>
				
				<label for="monthly-payment-input">Monthly Payment :</label>
				$ <input type="text" id="monthly-payment-input" name="monthly-payment" value="${monthlyPayment}">
				<br/>
				
				<input type="submit" value="Submit" name="calculate">
				<input type="submit" value="Clear all" name="clear-all">
				<br/><br/>
				
				<input type="text" style="border:none; border-color: transparent; width:40%" name="totalInterest" value="${totalInterest}" readonly>
				<br/><br/>
				<input type="text" style="border:none; border-color: transparent; width:40%" name="yearsMonthsTillPayoff" value="${yearsMonthsTillPayoff}" readonly>
				<br/><br/>
								
				<div id="log-div" style=" position: relative; left: 600px; bottom: 195px;">
					<input type="submit" value="Clear log" name="clear-log">
					<input type="submit" style="position:relative; left: 320px"value="clear an entry" name="clearEntry">
					<hr>
					${log}
				</div>
			</form>		
		</div> 
	</div>
</body>
</html>