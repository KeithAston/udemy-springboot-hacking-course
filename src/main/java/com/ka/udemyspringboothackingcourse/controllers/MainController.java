package com.ka.udemyspringboothackingcourse.controllers;

import com.ka.udemyspringboothackingcourse.helpers.MainHelper;
import com.ka.udemyspringboothackingcourse.models.User;
import com.ka.udemyspringboothackingcourse.services.BankingService;
import com.ka.udemyspringboothackingcourse.services.CustomerLookupService;
import com.ka.udemyspringboothackingcourse.services.DNSLookupService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequestMapping("/api/v1")
@AllArgsConstructor
public class MainController {

    private BankingService bankingService;
    private DNSLookupService dnsLookupService;
    private CustomerLookupService customerLookupService;

    //SQL Injection Endpoint
    @GetMapping("/balance/inquiry")
    public ResponseEntity<String> getBalance(@RequestParam("username") String username,
                                            @RequestParam("password") String password) throws SQLException {
        String response = bankingService.getBalance(username, password);
        if (response.equals(MainHelper.BAD_CREDENTIALS)) {
            return new ResponseEntity<>("Problem with provided credentials, please check and try again", HttpStatus.BAD_REQUEST);
        } else if (response != null) {
            return new ResponseEntity<>(bankingService.getBalance(username,password), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Something went wrong. Service temporarily unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    //Command Execution Endpoint
    @GetMapping("/DNS/lookup")
    public ResponseEntity<String> getDNSInformation(@RequestHeader HttpHeaders httpHeaders) throws IOException {
        String dns = httpHeaders.getFirst("DNS");
        return new ResponseEntity<>("DNS lookup results for " + dns + dnsLookupService.getDNS(dns), HttpStatus.OK);
    }

    //Privilege Escalation Endpoint No.1
    @GetMapping("/customer/lookup")
    public ResponseEntity<String> getUserInfo(@RequestHeader HttpHeaders httpHeaders){
        if (httpHeaders.containsKey("Cookie")) {
            return new ResponseEntity<>(customerLookupService.getUser(httpHeaders.get("Cookie")), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Please provide Cookie in header of request", HttpStatus.BAD_REQUEST);
        }
    }

    //Privilege Escalation Endpoint No.2
    @PostMapping("/customer/set")
    public ResponseEntity<String> setUserInfo(@RequestHeader HttpHeaders httpHeaders,
                                                  @RequestBody User user){
        return new ResponseEntity<>("Successfully updated user info ", HttpStatus.OK);
    }

    //Broken Access Control Endpoint
    @GetMapping("/earningsreport/{fileName}")
    public ResponseEntity<String> getEarningsReport(@PathVariable("fileName") String fileName) {
        return new ResponseEntity<>("Successfully retrieved report titled " + fileName, HttpStatus.OK);
    }


}
