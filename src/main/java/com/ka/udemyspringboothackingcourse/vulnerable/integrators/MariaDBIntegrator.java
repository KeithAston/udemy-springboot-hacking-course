package com.ka.udemyspringboothackingcourse.vulnerable.integrators;

import com.ka.udemyspringboothackingcourse.configuration.MariaDBConfig;
import com.ka.udemyspringboothackingcourse.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service("MariaDBIntegratorV1")
@CommonsLog
@AllArgsConstructor
public class MariaDBIntegrator {

    private MariaDBConfig mariaDBConfig;

    public ResultSet getBalance(String username, String password) throws SQLException {
        Connection conn = getMariaDBConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM abc_bank_users WHERE Username ='" + username + "' and password = '"
                + password + "'";

        return stmt.executeQuery(sql);
    }

    public ResultSet getUser(int userId) throws SQLException {
        Connection conn = getMariaDBConnection();
        Statement stmt = conn.createStatement();
        String sql = "select * from user_information where userid=" + userId;

        return stmt.executeQuery(sql);
    }

    public void setUser(User user) throws SQLException {
        Connection conn = getMariaDBConnection();
        Statement stmt = conn.createStatement();
        String sql = "UPDATE user_information SET Username = '" + user.getName() + "', contact_number = '"
                    + user.getContactNumber() + "', userType = '" + user.getUserType() + "', address = '"
                    + user.getAddress() + "' WHERE userid = " + user.getUserId() + ";";
        stmt.executeQuery(sql);
    }

    public ResultSet getEarningsReport(String reportName) throws SQLException {
        Connection conn = getMariaDBConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * from earnings_reports WHERE reportName='" + reportName
                + "';";
        return stmt.executeQuery(sql);
    }



    private Connection getMariaDBConnection() throws SQLException {
        return DriverManager.getConnection(mariaDBConfig.getJdbcUrl(),
                mariaDBConfig.getUsername(), mariaDBConfig.getPassword());
    }
}
