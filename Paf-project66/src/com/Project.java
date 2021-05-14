package com;
import java.sql.*;
public class Project
{
private Connection connect()
 {
 Connection con = null;
 try
 {
 Class.forName("com.mysql.jdbc.Driver");
 con =
 DriverManager.getConnection(
 "jdbc:mysql://127.0.0.1:3306/test2", "root", "");
 }
 catch (Exception e)
 {
 e.printStackTrace();
 }
 return con;
 }
public String readProjects()
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {
 return "Error while connecting to the database for reading.";
 }
 // Prepare the html table to be displayed
 output = "<table border='1'><tr><th>Proj Code</th><th>Proj Name</th><th>Proj Price</th>" + "<th>Proj Description</th><th>Update</th><th>Remove</th></tr>";
 String query = "select * from projects";
 Statement stmt = con.createStatement();
 ResultSet rs = stmt.executeQuery(query);
 // iterate through the rows in the result set
 while (rs.next())
 {
 String projID = Integer.toString(rs.getInt("projID"));
 String projCode = rs.getString("projCode"); 
 String projName = rs.getString("projName");
 String projPrice = Double.toString(
 rs.getDouble("projPrice"));
 String projDesc = rs.getString("projDesc");
 // Add into the html table
 output += "<tr><td><input id='hidProjectIDUpdate' name='hidProjectIDUpdate' type='hidden' value='" + projID
 + "'>" + projCode + "</td>";
 output += "<td>" + projName + "</td>";
 output += "<td>" + projPrice + "</td>";
 output += "<td>" + projDesc + "</td>";
 // buttons
output += "<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>" + "<td><input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-projid='"
 + projID + "'>" + "</td></tr>";
 }
 con.close();
 // Complete the html table
 output += "</table>";
 }
 catch (Exception e)
 {
 output = "Error while reading the projects.";
 System.err.println(e.getMessage());
 }
 return output;
 }
public String insertProject(String code, String name,
 String price, String desc)
 {
 String output = "";
 try
 {
 Connection con = connect();
 if (con == null)
 {
 return "Error while connecting to the database for inserting.";
 }
 // create a prepared statement
 String query = " insert into projects(`projID`,`projCode`,`projName`,`projPrice`,`projDesc`)"
 
+ " values (?, ?, ?, ?, ?)";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, code);
		 preparedStmt.setString(3, name);
		 preparedStmt.setDouble(4, Double.parseDouble(price));
		 preparedStmt.setString(5, desc);
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 String newProjects = readProjects();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newProjects + "\"}";
		 }
		 catch (Exception e)
		 {
		 output = "{\"status\":\"error\", \"data\":\"Error while inserting the project.\"}";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }
		public String updateProject(String ID, String code, String name,
		 String price, String desc)
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
		 return "Error while connecting to the database for updating.";
		 }
		 // create a prepared statement
		 String query = "UPDATE projects SET projCode=?,projName=?,projPrice=?,projDesc=? WHERE projID=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setString(1, code);
		 preparedStmt.setString(2, name);
		 preparedStmt.setDouble(3, Double.parseDouble(price));
		 preparedStmt.setString(4, desc);
		 preparedStmt.setInt(5, Integer.parseInt(ID)); 
		// execute the statement
		 preparedStmt.execute();
		 con.close();
		 String newProjects = readProjects();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newProjects + "\"}";
		 }
		 catch (Exception e)
		 {
		 output = "{\"status\":\"error\", \"data\":\"Error while updating the project.\"}";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }
		public String deleteProject(String projID)
		 {
		 String output = "";
		 try
		 {
		 Connection con = connect();
		 if (con == null)
		 {
		 return "Error while connecting to the database for deleting.";
		 }
		 // create a prepared statement
		 String query = "delete from projects where projID=?";
		 PreparedStatement preparedStmt = con.prepareStatement(query);
		 // binding values
		 preparedStmt.setInt(1, Integer.parseInt(projID));
		 // execute the statement
		 preparedStmt.execute();
		 con.close();
		 String newProjects = readProjects();
		 output = "{\"status\":\"success\", \"data\": \"" +
		 newProjects + "\"}";
		 }
		 catch (Exception e)
		 {
		 output = "{\"status\":\"error\", \"data\":\"Error while deleting the project.\"}";
		 System.err.println(e.getMessage());
		 }
		 return output;
		 }
		}