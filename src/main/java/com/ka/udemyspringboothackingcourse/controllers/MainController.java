package com.ka.udemyspringboothackingcourse.controllers;

import com.ka.udemyspringboothackingcourse.models.User;
import com.ka.udemyspringboothackingcourse.services.BankingService;
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

    //SQL Injection Endpoint
    @GetMapping("/balance/inquiry")
    public ResponseEntity<String> getBalance(@RequestParam("username") String username,
                                            @RequestParam("password") String password) throws SQLException {
        return new ResponseEntity<>(bankingService.getBalance(username,password), HttpStatus.OK);
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
        return new ResponseEntity<>("User Id : " + httpHeaders.get("Cookie"), HttpStatus.OK);
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
