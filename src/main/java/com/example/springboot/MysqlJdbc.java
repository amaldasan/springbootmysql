package com.example.springboot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class MysqlJdbc {

	private static String empid = "EMPID";
	private static String firstname = "FIRSTNAME";
	private static String lastname = "LASTNAME";
	private static Properties properties = null;
	@GetMapping("/Data")//request url name
	public String getEmployeeDetails(int empID) throws SQLException  {
		//connection with mysql
		String 	DRIVER= "DRIVER";
		String URL = "URL";
		String UNAME = "UNAME";
		String PASS = "PASS";
	
		
		//we can use stroe procedure or mysl query
		String query = "select empid, firstname,lastname from employeee where empid ?";
		String userData = "";
		try {

			System.out.println("empID" + empID);
			properties= new Properties();
			properties.load(new FileInputStream("src/main/java/com/example/springboot/dbconfig.properties"));
			
			Class.forName(properties.getProperty(DRIVER));//load and register
			
			
			Connection con = DriverManager.getConnection(properties.getProperty(URL),properties.getProperty(UNAME),properties.getProperty(PASS));
			CallableStatement st = con.prepareCall("{call empids(?)}");
			st.setInt(1,empID);
			ResultSet rs = st.executeQuery();
			
			
			
			while(rs.next())
			{
				userData = rs.getInt(empid)+":"+rs.getString(firstname)+""+rs.getString(lastname);
				System.out.println(userData);

				int empid = rs.getInt("empid");
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");


				String line = String.format("%d,%s,%s",
						empid, firstname, lastname);

				
			}

			

			// closing the statement
			st.close();
			con.close();



		}


		catch(Exception e) {
			e.printStackTrace();
		}


		
		return userData;
	}
}
	
