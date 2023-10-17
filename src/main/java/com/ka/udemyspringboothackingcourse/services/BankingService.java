package com.ka.udemyspringboothackingcourse.services;

import com.ka.udemyspringboothackingcourse.configuration.MariaDBConfig;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.sql.*;


@Service
@AllArgsConstructor
@CommonsLog
public class BankingService {

    private MariaDBConfig mariaDBConfig;

    public String getBalance(String username, String password) throws SQLException {

        Connection conn = DriverManager.getConnection(mariaDBConfig.getJdbcUrl(),
                mariaDBConfig.getUsername(), mariaDBConfig.getPassword());
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM abc_bank_users WHERE Username ='" + username + "' and password = '"
                        + password + "'";

        ResultSet results = stmt.executeQuery(sql);
        double balance = 0;

        if (results.next()){
            results.beforeFirst();
            while (results.next()) {
                balance = results.getDouble("balance");
                break;
            }
        } else {
            return "Bad Credentials";
        }

        return "Balance for " + username + " : €" + balance;
    }

}
