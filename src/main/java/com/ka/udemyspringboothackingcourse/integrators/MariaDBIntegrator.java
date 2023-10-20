package com.ka.udemyspringboothackingcourse.integrators;

import com.ka.udemyspringboothackingcourse.configuration.MariaDBConfig;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
@CommonsLog
@AllArgsConstructor
public class MariaDBIntegrator {

    private MariaDBConfig mariaDBConfig;

    public ResultSet getBalance(String username, String password) throws SQLException {
        Connection conn = DriverManager.getConnection(mariaDBConfig.getJdbcUrl(),
                mariaDBConfig.getUsername(), mariaDBConfig.getPassword());
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM abc_bank_users WHERE Username ='" + username + "' and password = '"
                + password + "'";

        return stmt.executeQuery(sql);
    }

    public ResultSet getUser(int userId) throws SQLException {
        Connection conn = DriverManager.getConnection(mariaDBConfig.getJdbcUrl(),
                mariaDBConfig.getUsername(), mariaDBConfig.getPassword());
        Statement stmt = conn.createStatement();
        String sql = "select * from user_information where userid=" + userId;

        return stmt.executeQuery(sql);
    }

}
