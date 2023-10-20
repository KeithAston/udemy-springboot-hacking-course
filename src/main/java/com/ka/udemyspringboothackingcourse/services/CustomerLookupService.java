package com.ka.udemyspringboothackingcourse.services;

import com.ka.udemyspringboothackingcourse.integrators.MariaDBIntegrator;
import com.ka.udemyspringboothackingcourse.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
@CommonsLog
@AllArgsConstructor
public class CustomerLookupService {

    private MariaDBIntegrator mariaDBIntegrator;

    public String getUser(List<String> cookies) {
        int userId;
        for (String cookie: cookies){
            if (cookie.startsWith("userid=")){
                userId = Integer.parseInt(cookie.substring(7));
                ResultSet results;
                try {
                    results = mariaDBIntegrator.getUser(userId);
                    if (results.next()){
                        User user = new User();
                        user.setUserId(userId);
                        user.setName(results.getString("Username"));
                        user.setUserType(results.getString("userType"));
                        user.setAddress(results.getString("address"));
                        user.setContactNumber(results.getString("contact_number"));
                        return user.toString();

                    } else {
                        return "No user found with given ID";
                    }
                } catch (SQLException se) {
                    log.error("Problem with SQL syntax, please try again : " + se.getMessage());
                    return "sql";
                } catch (Exception e) {
                    log.error("Exception caught : " + e.getMessage());
                    return null;
                }
            }
        }
        return "Please check provided cookies for syntax errors";


    }
}
