package com.ka.udemyspringboothackingcourse.vulnerable.services;

import com.ka.udemyspringboothackingcourse.vulnerable.helpers.MainHelper;
import com.ka.udemyspringboothackingcourse.vulnerable.integrators.MariaDBIntegrator;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.*;


@Service("BankingServiceV1")
@AllArgsConstructor
@CommonsLog
public class BankingService {

    @Qualifier("MariaDBIntegratorV1")
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
