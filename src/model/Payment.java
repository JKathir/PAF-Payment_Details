package model;

import com.PaymentAPI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


import org.json.JSONArray;
import org.json.JSONObject;


public class Payment {
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ceb_power_usage", "root", "1234");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertpaymentdetails(String payment_code, String date, int amount, int customerId) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into payment (`payment_id`,`payment_code`, `date`,`amount`,`customer_id`)" + " values ( ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, payment_code);
			preparedStmt.setString(3, date);
			preparedStmt.setInt(4, amount);
			preparedStmt.setInt(5, customerId);
			preparedStmt.execute();
			con.close();

			
			String newItems = readpaymentdetails();
			 output = "{\"status\":\"success\", \"data\": \"" + newItems + "\"}"; 

		} catch (Exception e) {
			output = "{\"status\":\"error\",\"data\":\"Error while Inserting the details.\"}";
					 
					 System.err.println(e.getMessage()); 

		}

		return output;
	}

	public String readpaymentdetails() {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Payment Code</th><th>Date</th><th>Amount</th><th>Customer Id</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from payment";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String paymentId = Integer.toString(rs.getInt("payment_id"));
				String payment_code = rs.getString("payment_code");
				String date = rs.getString("date");
				int amount = rs.getInt("amount");
				int customerId = rs.getInt("customer_id");

				// Add into the html table
				output += "<tr><td> " + payment_code+ "</td>";	
				output += "<td>" + date + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + customerId + "</td>";
				
				//buttons 
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-success' data-itemid='" + paymentId + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-itemid='" + paymentId + "'></td></tr>";
					

			}

			con.close();

			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the payment.";
			System.err.println(e.getMessage());
		}

		return output;
	}

	public String updatepaymentdetails(String paymentId,String payment_code, String date, int amount, int customerId) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE payment SET payment_code=?,date=?,amount=?,customer_id=?   WHERE payment_id=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, payment_code);
			preparedStmt.setString(2, date);
			preparedStmt.setInt(3, amount);
			preparedStmt.setInt(4, customerId);
			preparedStmt.setInt(5, Integer.parseInt(paymentId));

			// execute the statement
			preparedStmt.execute();
			con.close();

			String newItems = readpaymentdetails();
			 output = "{\"status\":\"success\", \"data\": \"" +  newItems + "\"}"; 

		} catch (Exception e) {
			output = "{\"status\":\"error\",\"data\":\"Error while Updating the details.\"}";
					 
					 System.err.println(e.getMessage()); 

		}

		return output;
	}

	public String deletepaymentdetails(String paymentId) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from payment where payment_id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(paymentId));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItems = readpaymentdetails();
			 output = "{\"status\":\"success\", \"data\": \"" +  newItems + "\"}"; 

		} catch (Exception e) {
			output = "{\"status\":\"error\",\"data\":\"Error while deleting the details.\"}";
					 
					 System.err.println(e.getMessage()); 

		}
		return output;
	}
	
	public String getCustomerPowerUsageByEmployee(int customerId)
	{
		String output = "";
		 JSONArray jsonArray = new JSONArray();
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			String query = "select c.customer_name,c.address,c.telepohne_no,p.amount, p.date from payment p, customer c where p.customer_id = c.customer_id AND c.customer_id = " + customerId;
			PreparedStatement preparedStmt = con.prepareStatement(query);
//			preparedStmt.setInt(1, customerId);
			// binding values
			ResultSet rs = preparedStmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				int columns = rs.getMetaData().getColumnCount();
				JSONObject obj = new JSONObject();
				for (int i = 0; i < columns; i++)
		            obj.put(rs.getMetaData().getColumnLabel(i + 1).toLowerCase(), rs.getObject(i + 1));
		 
		        jsonArray.put(obj);
			}
			con.close();
			// Complete the html table
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return jsonArray.toString();
	}

}