package com.ka.udemyspringboothackingcourse.services;

import com.ka.udemyspringboothackingcourse.helpers.MainHelper;
import com.ka.udemyspringboothackingcourse.integrators.MariaDBIntegrator;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
@AllArgsConstructor
@CommonsLog
public class ReportsService {

    private MariaDBIntegrator mariaDBIntegrator;

    public String getEarningsReport(String reportName){
        ResultSet results;
        try {
            results = mariaDBIntegrator.getEarningsReport(reportName);
            if (results.next()){
                return constructEarningsReport(results.getString("reportIntro"), results.getDouble("totalCosts"),
                        results.getDouble("totalRevenue"),results.getDouble("totalProfits"));
            } else {
                return "No Earnings Report found. Please check reportName requested";
            }

        } catch (SQLException se) {
            log.error("Problem with SQL syntax, please try again : " + se.getMessage());
            return MainHelper.SQL_EXCEPTION;
        } catch (Exception e) {
            log.error(MainHelper.EXCEPTION_OPENER + e.getMessage());
            return MainHelper.GENERIC_EXCEPTION;
        }


    }

    private String constructEarningsReport(String reportIntro, double costs, double revenue, double profit){
        StringBuilder sb = new StringBuilder();
        sb.append(reportIntro)
        .append("\n")
        .append("Total Costs : €").append(costs)
        .append("\n")
        .append("Total Revenue : €").append(revenue)
        .append("\n")
        .append("Total Profit : €").append(profit);
        return sb.toString();
    }

}
