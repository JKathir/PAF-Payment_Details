<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="model.Payment"%>
<%@page import="com.PaymentAPI" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Payment management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.2.1.min.js"></script>
<script src="Components/main.js"></script>
</head>
<body>
	<nav class="navbar navbar-dark bg-dark">


		<a class="navbar-brand" href="#">ElectroGrid System </a>
		  <form class="form-inline">
			<a href="index.jsp"><input id="btnHome" name="btnHome"
				type="button" value="Home" class="btn btn-lg btn-outline-primary"></a>

			<!-- &nbsp &nbsp &nbsp<input id="btnLogout" name="btnLogout" type="button"
				value="Logout" class="btn btn-lg btn-outline-primary"> -->
		</form>  
	</nav>

	<div class="container">
		<div class="row">
			<div class="col-8">
				<br>
				<h2>Payment Management</h2>
				 	
				 	<form id="formItem" name="formItem" >


		Payment Code:
		<input id="payment_code" name="payment_code" type="text" class="form-control form-control-sm" ><br>
		 Date :
		<input id="date" name="date" type="text" class="form-control form-control-sm"><br>
		 Amount:
		<input id="amount" name="amount" type="text" class="form-control form-control-sm"><br>
	    Customer ID:
		<input id="customerId" name="customerId" type="text"class="form-control form-control-sm"><br>
		 
	
 
		<br>
		
		<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary btn-lg btn-block">
		<input type="hidden" id="hidpayIDSave" name="hidpayIDSave" value="">
		
		
</form> 			
						
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>

				<br> <br>
				<div id="divItemsGrid">
					<%
						Payment payObj = new Payment();
						out.print(payObj.readpaymentdetails());			

					%>
				</div>

			</div>
		</div>

	</div>

</body>
</html>