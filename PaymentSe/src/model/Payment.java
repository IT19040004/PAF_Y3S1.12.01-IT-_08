package model;
import java.sql.*;

public class Payment
{ //A common method to connect to the DB
private Connection connect() 
 { 
 Connection con = null; 
 try
 { 
 Class.forName("com.mysql.jdbc.Driver"); 
 
 //Provide the correct details: DBServer/DBName, username, password 
 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/paymentdb", "root", ""); 
 } 
 catch (Exception e) 
 {e.printStackTrace();} 
 return con; 
 } 
public String insertPayment(String ItemID, String quantity, String payer, String paymentMethod,String date,String amount) 
 { 
 String output = ""; 
 try
 { 
	 
  int x = Integer.parseInt(quantity);
  if(ItemID.isEmpty() || quantity.isEmpty()||payer.isEmpty()||paymentMethod.isEmpty()||date.isEmpty()||amount.isEmpty()) {
	  output = "Inserted Unsuccessfully You have empty Value";
  }else {
	 if(x == (int)x) {
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for inserting."; } 
 // create a prepared statement
 String query = " insert into payment (`paymentID`,`ItemID`,`quantity`,`payer`,`paymentMethod`,`date`,`amount`)"
 + " values (?, ?, ?, ?, ?,?,?)"; 
 PreparedStatement preparedStmt = con.prepareStatement(query); 
 // binding values
 preparedStmt.setInt(1, 0); 
 preparedStmt.setString(2, ItemID); 
 preparedStmt.setString(3, quantity); 
 preparedStmt.setString(4, payer); 
 preparedStmt.setString(5, paymentMethod); 
 preparedStmt.setString(6, date);
 preparedStmt.setDouble(7, Double.parseDouble(amount)); 
  
 // execute the statement
 preparedStmt.execute(); 
 con.close(); 
 output = "Inserted successfully";
	 }else {
		 output = "Inserted Unsuccessfully..Reenter ";
	 }
  }
 } 
 catch (Exception e) 
 { 
 output = "Error while inserting the Payment."; 
 System.err.println(e.getMessage()); 
 } 
 return output; 
 } 

public String readPayment() 
 { 
 String output = ""; 
 try
 { 
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for reading."; } 
 // Prepare the html table to be displayed
 output = "<table border=\"1\"><tr><th>ItemID</th><th>quantity</th><th>payer</th><th>paymentMethod</th><th>date</th><th>amount</th><th>Update</th><th>Remove</th></tr>"; 
 String query = "select * from payment"; 
 Statement stmt = con.createStatement(); 
 ResultSet rs = stmt.executeQuery(query); 
 // iterate through the rows in the result set
 while (rs.next()) 
 { 
 String paymentID = Integer.toString(rs.getInt("paymentID")); 
 String ItemID = rs.getString("ItemID"); 
 String quantity = rs.getString("quantity"); 
 String payer = rs.getString("payer");
 String paymentMethod = rs.getString("paymentMethod");
 String date = rs.getString("date");
 String amount = Double.toString(rs.getDouble("amount")); 
  
 // Add into the html table
 output += "<tr><td>" + ItemID + "</td>"; 
 output += "<td>" + quantity + "</td>"; 
 output += "<td>" + payer + "</td>"; 
 output += "<td>" + paymentMethod + "</td>"; 
 output += "<td>" + date + "</td>"; 
 output += "<td>" + amount + "</td>"; 
 // buttons
 output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>" + "<td><form method=\"post\" action=\"items.jsp\">"+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
 + "<input name=\"paymentID\" type=\"hidden\" value=\"" + paymentID
 + "\">" + "</form></td></tr>"; 
 } 
 con.close(); 
 // Complete the html table
 output += "</table>"; 
 } 
 catch (Exception e) 
 { 
 output = "Error while reading the payment."; 
 System.err.println(e.getMessage()); 
 } 
 return output; 
 } 
public String updatePayment(String paymentID, String ItemID, String quantity, String payer, String paymentMethod,String date,String amount) 
 { 
 String output = ""; 
 try
 { 
	 if(ItemID.isEmpty() || quantity.isEmpty()||payer.isEmpty()||paymentMethod.isEmpty()||date.isEmpty()||amount.isEmpty()) {
		  output = "Inserted Unsuccessfully You have empty Value";
	  }else {
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for updating."; } 
 // create a prepared statement
 String query = "UPDATE payment SET ItemID=?,quantity=?,payer=?,paymentMethod=?,date=?,amount=? WHERE paymentID=?"; 
 PreparedStatement preparedStmt = con.prepareStatement(query); 
 // binding values
 preparedStmt.setString(1, ItemID); 
 preparedStmt.setInt(2, Integer.parseInt(quantity)); 
 preparedStmt.setString(3, payer);
 preparedStmt.setString(4, paymentMethod);
 preparedStmt.setString(5, date);
 preparedStmt.setDouble(6, Double.parseDouble(amount));  
 preparedStmt.setInt(7, Integer.parseInt(paymentID)); 
 // execute the statement
 preparedStmt.execute(); 
 con.close(); 
 output = "Updated successfully"; 
	  }
 } 
 catch (Exception e) 
 { 
 output = "Error while updating the payment."; 
 System.err.println(e.getMessage()); 
 } 
 return output; 
 } 

public String deletePayment(String paymentID) 
 { 
 String output = ""; 
 try
 { 
 Connection con = connect(); 
 if (con == null) 
 {return "Error while connecting to the database for deleting."; } 
 // create a prepared statement
 String query = "delete from payment where paymentID=?"; 
 PreparedStatement preparedStmt = con.prepareStatement(query); 
 // binding values
 preparedStmt.setInt(1, Integer.parseInt(paymentID)); 
 // execute the statement
 preparedStmt.execute(); 
 con.close(); 
 output = "Deleted successfully"; 
 } 
 catch (Exception e) 
 { 
 output = "Error while deleting the payment."; 
 System.err.println(e.getMessage()); 
 } 
 return output; 
 } 
} 

