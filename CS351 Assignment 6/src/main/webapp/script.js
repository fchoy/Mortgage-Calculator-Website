//validate interest rate, mortgage, and monthly payment

function validate(){
	let interestRate = document.forms["input-form"]["interest-rate"].value / 100;
	let principalAmount = Number(document.forms["input-form"]["principal"].value);
	let monthlyPayment = Number(document.forms["input-form"]["monthly-payment"].value);
	
	let monthlyInterest = principalAmount * (interestRate/12);
	 
	if(isNaN(interestRate) || isNaN(principalAmount) || isNaN(monthlyPayment)){
		alert("One or more of the input fields is not a valid input.");
		return false;
	}
		
	else if(interestRate.toString().trim().length == 0 || principalAmount.toString().trim().length == 0 || monthlyPayment.toString().trim().length == 0){
		alert("One or more of the input fields is not a valid input.");
		return false;
	}
	
	else if(interestRate.toString == "" || monthlyPayment.toString == "" || principalAmount == ""){
		alert("One or more of the input fields is not a valid input.");
		return false;
	}
	
	else if(monthlyPayment < monthlyInterest){
		alert("Your current monthly payment is not enough to cover the monthly interest or make a contribution to your principal.");
		alert("Please enter an amount greater than " + "$" + parseInt(monthlyInterest+monthlyPayment) + " in order to start making a contribution to your principal.");
		return false;
	}
}