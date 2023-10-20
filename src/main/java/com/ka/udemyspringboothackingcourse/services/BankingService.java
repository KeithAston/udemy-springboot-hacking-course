package com.ka.udemyspringboothackingcourse.services;

import com.ka.udemyspringboothackingcourse.configuration.MariaDBConfig;
import com.ka.udemyspringboothackingcourse.helpers.MainHelper;
import com.ka.udemyspringboothackingcourse.integrators.MariaDBIntegrator;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.sql.*;


@Service
@AllArgsConstructor
@CommonsLog
public class BankingService {

    private MariaDBIntegrator mariaDBIntegrator;

    public String getBalance(String username, String password) throws SQLException {
        ResultSet results;
        try {
            results = mariaDBIntegrator.getBalance(username, password);
            double balance = 0;
            if (results.next()){
                    balance = results.getDouble("balance");
            } else {
                return MainHelper.BAD_CREDENTIALS;
            }
            return "Balance for " + username + " : €" + balance;
        } catch (SQLException se) {
            log.error("Problem with SQL syntax, please try again : " + se.getMessage());
            return MainHelper.BAD_CREDENTIALS;
        } catch (Exception e) {
            log.error(MainHelper.EXCEPTION_OPENER + e.getMessage());
            return null;
        }
    }

}
