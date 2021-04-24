package model;

import java.sql.*;

public class Register {

	//A common method to connect to the DB
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/registerdb", "root", "");
	 }
	 catch (Exception e)
	 {e.printStackTrace();}
	 return con;
	 }
	public String insertRegister(String name, String email, String password, String repassword)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for inserting."; }
	 // create a prepared statement
	 String query = " insert into register(registerid,name,email,password,repassword)"+ " values (?,?, ?, ?, ?)";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, 0);
	 preparedStmt.setString(2, name);
	 preparedStmt.setString(3, email);
	 preparedStmt.setString(4, password);
	 preparedStmt.setString(5, repassword);
	// execute the statement
	 
	 preparedStmt.execute();
	 con.close();
	 output = "Inserted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while inserting the register.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	public String readItems()
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for reading."; }
	 // Prepare the html table to be displayed
	 output = "<table border='1'><tr><th>Name</th><th>Email</th>" +
	 "<th>Password</th>" +
	 "<th>Renter Password</th>" +
	 "<th>Update</th><th>Remove</th></tr>";

	 String query = "select * from register";
	 Statement stmt = con.createStatement();
	 ResultSet rs = stmt.executeQuery(query);
	 // iterate through the rows in the result set
	 while (rs.next())
	 {
	 String registerid = Integer.toString(rs.getInt("registerid"));
	 String name = rs.getString("name");
	 String email = rs.getString("email");
	 String password = rs.getString("password");
	 String repassword = rs.getString("repassword");
	 // Add into the html table
	 output += "<tr><td>" + name + "</td>";
	 output += "<td>" + email + "</td>";
	 output += "<td>" + password + "</td>";
	 output += "<td>" + repassword + "</td>";
	 // buttons
	 output += "<td><input name='btnUpdate' type='button' value='Update'class='btn btn-secondary'></td>"
	 + "<td><form method='post' action='items.jsp'>"+ "<input name='btnRemove' type='submit' value='Remove'class='btn btn-danger'>"
	 + "<input name='registerid' type='hidden' value='" + registerid
	 + "'>" + "</form></td></tr>";
	 }
	 con.close();
	 // Complete the html table
	 output += "</table>";
	 }
	 catch (Exception e)
	 {
	 output = "Error while reading the items.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	
	public String updateRegister(String registerid, String name, String email, String password, String repassword)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for updating."; }
	 // create a prepared statement
	 String query = "UPDATE register SET name=?,email=?,password=?,repassword=? WHERE registerid=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 
	 preparedStmt.setString(1, name);
	 preparedStmt.setString(2, email);
	 preparedStmt.setString(3, password);
	 preparedStmt.setString(4, repassword);
	 preparedStmt.setInt(5, Integer.parseInt(registerid));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Updated successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while updating the register.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	public String deleteRegister(String registerid)
	 {
	 String output = "";
	 try
	 {
	 Connection con = connect();
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; }
	 // create a prepared statement
	 String query = "delete from register where registerid=?";
	 PreparedStatement preparedStmt = con.prepareStatement(query);
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(registerid));
	 // execute the statement
	 preparedStmt.execute();
	 con.close();
	 output = "Deleted successfully";
	 }
	 catch (Exception e)
	 {
	 output = "Error while deleting the register.";
	 System.err.println(e.getMessage());
	 }
	 return output;
	 }
	} 

