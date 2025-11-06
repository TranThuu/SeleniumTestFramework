package com.orangehrm.utilities;

import com.mysql.cj.protocol.Resultset;
import com.orangehrm.base.BaseClass;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Logger;

public class DBConnection {
    public static final Logger logger = BaseClass.logger;
    private static final String DB_URL="jdbc:mysql://localhost:3306/orangehrmthu";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "";

    public static Connection getDBConnection(){
        try {
            logger.info("Starting DB Connection...");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("DB connection Successful");
            return conn;
        }catch (Exception e){
            logger.info("Error while establishing the DB Connection: " + e);
            e.printStackTrace();
            return null;
        }
    }

    //Get the employee from the database and store in a map
    public static Map<String,String> getEmployeeDetails(String employeeId){
        String query = "SELECT emp_firstname, emp_middle_name, emp_lastname FROM hs_hr_employee WHERE employee_id = "+employeeId;
        Map<String,String> employeeDetails = new HashMap<>();
        try (Connection conn = getDBConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)){
            logger.info("Executing query: " + query);
            if(rs.next()){
                String firstName = rs.getString("emp_firstname");
                String middleName = rs.getString("emp_middle_name");
                String lastName = rs.getString("emp_lastname");

                //Store in a map
                employeeDetails.put("firstName", firstName);
                employeeDetails.put("lastName", lastName);
                employeeDetails.put("middleName", middleName!=null?middleName:"");

                logger.info("Query Executed Successfully");
                logger.info("Employee Data Fetched " + employeeId);
            }else {
                logger.info("Employee not found");
            }
        }catch (Exception e){
            logger.error("Error while executing query:" + e);
            e.printStackTrace();
        }
        return employeeDetails;
    }
}
