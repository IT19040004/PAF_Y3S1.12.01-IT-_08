package model;

import java.sql.*;
import java.util.regex.Pattern;

public class Fund { // A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/funddb", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertFund(String name, String nic, String address, String phone, String email, String amount,
			String description) {
		String output = "";
		try {
			
			if(phone.length() == 10) {
				if(isValid(email)) {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into funds (`fundID`,`name`,`nic`,`address`,`phone`,`email`,`amount`,`description`)"
					+ " values (?, ?, ?, ?, ?,?,?,?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, nic);
			preparedStmt.setString(4, address);
			preparedStmt.setInt(5, Integer.parseInt(phone));
			preparedStmt.setString(6, email);
			preparedStmt.setDouble(7, Double.parseDouble(amount));
			preparedStmt.setString(8, description);
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";
				}else {
					output = "Inserted Unsuccessfully..Invalid email";
				}
			}else {
				output = "Inserted Unsuccessfully..Invalid Phone Number";
			}
		
		} catch (Exception e) {
			output = "Error while inserting the data.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
                              
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

	public String readItems() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Name</th><th>NIC</th><th>Address</th><th>Phone</th><th>Email</th><th>Amount</th><th>Description</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from funds";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String fundID = Integer.toString(rs.getInt("fundID"));
				String name = rs.getString("name");
				String nic = rs.getString("nic");
				String address = rs.getString("address");
				String phone = Integer.toString(rs.getInt("phone"));
				String email = rs.getString("email");
				String amount = Double.toString(rs.getDouble("amount"));
				String description = rs.getString("description");
				// Add into the html table
				output += "<tr><td>" + name + "</td>";
				output += "<td>" + nic + "</td>";
				output += "<td>" + address + "</td>";
				output += "<td>" + phone + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + amount + "</td>";
				output += "<td>" + description + "</td>";
				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\" value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"funds.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\" class=\"btn btn-danger\">"
						+ "<input name=\"fundID\" type=\"hidden\" value=\"" + fundID + "\">" + "</form></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateFund(String fundID, String name, String nic, String address, String phone, String email,
			String amount, String description) {
		String output = "";
		try {
			if(phone.length() == 10) {
				if(isValid(email)) {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE funds SET name=?,nic=?,address=?,phone=?,email=?,amount=?,description=? WHERE fundID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, nic);
			preparedStmt.setString(3, address);
			preparedStmt.setInt(4, Integer.parseInt(phone));
			preparedStmt.setString(5, email);
			preparedStmt.setDouble(6, Double.parseDouble(amount));
			preparedStmt.setString(7, description);
			preparedStmt.setInt(8, Integer.parseInt(fundID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";
				}else {
					output = "Inserted Unsuccessfully..Invalid email";
				}
			}else {
				output = "Inserted Unsuccessfully..Invalid Phone Number";
			}
		} catch (Exception e) {
			output = "Error while updating the funds.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deletefunds(String fundID) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from funds where fundID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(fundID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";
		} catch (Exception e) {
			output = "Error while deleting the fund.";
			System.err.println(e.getMessage());
		}
		return output;
	}
}