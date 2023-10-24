package com.ka.udemyspringboothackingcourse.vulnerable.services;

import com.ka.udemyspringboothackingcourse.vulnerable.helpers.MainHelper;
import com.ka.udemyspringboothackingcourse.vulnerable.integrators.MariaDBIntegrator;
import com.ka.udemyspringboothackingcourse.models.User;
import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service("CustomerLookupServiceV1")
@CommonsLog
@AllArgsConstructor
public class CustomerLookupService {

    @Qualifier("MariaDBIntegratorV1")
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
                        user.setName(results.getString(MainHelper.USERNAME_COLUMN_HEADER));
                        user.setUserType(results.getString(MainHelper.USERTYPE_COLUMN_HEADER));
                        user.setAddress(results.getString(MainHelper.ADDRESS_COLUMN_HEADER));
                        user.setContactNumber(results.getString(MainHelper.CONTACT_NUMBER_COLUMN_HEADER));
                        return user.toString();

                    } else {
                        return MainHelper.USER_DOES_NOT_EXIST;
                    }
                } catch (SQLException se) {
                    log.error("Problem with SQL syntax, please try again : " + se.getMessage());
                    return MainHelper.SQL_EXCEPTION;
                } catch (Exception e) {
                    log.error(MainHelper.EXCEPTION_OPENER + e.getMessage());
                    return MainHelper.GENERIC_EXCEPTION;
                }
            }
        }
        return "Please check provided cookies for syntax errors";
    }

    public String setUserInfo(User user, List<String> cookies) {
        int cookieUserId;
        for (String cookie : cookies) {
            if (cookie.startsWith("userid=")) {
                cookieUserId = Integer.parseInt(cookie.substring(7));
                ResultSet results;
                try {
                    results = mariaDBIntegrator.getUser(cookieUserId);
                    results.next();
                    if (results.getString(MainHelper.USERTYPE_COLUMN_HEADER).equals(MainHelper.USER_TYPE_ADMIN)) {
                        results = mariaDBIntegrator.getUser(user.getUserId());
                        if (results.next()) {
                            mariaDBIntegrator.setUser(user);
                        } else {
                            return MainHelper.USER_DOES_NOT_EXIST;
                        }
                    } else {
                        return "You do not have sufficient privileges to perform this action";
                    }
                    return "User successfully updated";
                } catch (SQLException se) {
                    log.error("Problem with SQL syntax, please try again : " + se.getMessage());
                    se.printStackTrace();
                    return MainHelper.SQL_EXCEPTION;
                } catch (Exception e) {
                    log.error(MainHelper.EXCEPTION_OPENER + e.getMessage());
                    return MainHelper.GENERIC_EXCEPTION;
                }
            }
        }
        return "Please check provided cookies for syntax errors";
    }
}
