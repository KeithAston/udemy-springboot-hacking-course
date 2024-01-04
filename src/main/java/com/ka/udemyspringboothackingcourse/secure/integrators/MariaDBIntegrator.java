package com.ka.udemyspringboothackingcourse.secure.integrators;

import com.ka.udemyspringboothackingcourse.models.User;
import com.ka.udemyspringboothackingcourse.configuration.MariaDBConfig;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service("MariaDBIntegratorV2")
@CommonsLog
@AllArgsConstructor
public class MariaDBIntegrator {

    private MariaDBConfig mariaDBConfig;

    public ResultSet getBalance(String username, String password) throws SQLException {
        /*add parameterized statement here similar to below:
        String sql = "SELECT * FROM users WHERE email = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);

        ResultSet results = stmt.executeQuery(sql);
        
        while (results.next())
        {
            // ...do something with the data returned.
        }
        */
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
