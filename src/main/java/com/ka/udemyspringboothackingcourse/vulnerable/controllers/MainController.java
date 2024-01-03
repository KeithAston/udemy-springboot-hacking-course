package com.ka.udemyspringboothackingcourse.vulnerable.controllers;

import com.ka.udemyspringboothackingcourse.vulnerable.helpers.MainHelper;
import com.ka.udemyspringboothackingcourse.vulnerable.services.BankingService;
import com.ka.udemyspringboothackingcourse.vulnerable.services.CustomerLookupService;
import com.ka.udemyspringboothackingcourse.vulnerable.services.DNSLookupService;
import com.ka.udemyspringboothackingcourse.vulnerable.services.ReportsService;
import com.ka.udemyspringboothackingcourse.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@RestController("MainControllerV1")
@RequestMapping("/api/v1")
@AllArgsConstructor
public class MainController {

    @Qualifier("BankingServiceV1")
    private BankingService bankingService;
    @Qualifier("DNSLookupServiceV1")
    private DNSLookupService dnsLookupService;
    @Qualifier("CustomerLookupServiceV1")
    private CustomerLookupService customerLookupService;
    @Qualifier("ReportsServiceV1")
    private ReportsService reportsService;

    //SQL Injection Endpoint
    @GetMapping("/balance/inquiry")
    public ResponseEntity<String> getBalance(@RequestParam("username") String username,
                                            @RequestParam("password") String password) throws Exception {
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
    public ResponseEntity<String> getUserInfo(@RequestHeader HttpHeaders httpHeaders) {
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
        if (httpHeaders.containsKey("Cookie")) {
            return new ResponseEntity<>(customerLookupService.setUserInfo(user, httpHeaders.get("Cookie")), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Please provide Cookie in header of request", HttpStatus.BAD_REQUEST);
        }
    }

    //Broken Access Control Endpoint
    @GetMapping("/earningsreport/{reportName}")
    public ResponseEntity<String> getEarningsReport(@PathVariable("reportName") String reportName) {
        return new ResponseEntity<>(reportsService.getEarningsReport(reportName), HttpStatus.OK);
    }


}
