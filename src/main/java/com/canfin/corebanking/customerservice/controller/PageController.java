package com.canfin.corebanking.customerservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {
        return "forward:/login.html";
    }

    @GetMapping("/customer")
    public String customer() {
        return "forward:/customer.html";
    }

    @GetMapping("/deposit")
    public String deposit() {
        return "forward:/deposit.html";
    }

    @GetMapping("/savings")
    public String savings() {
        return "forward:/savings.html";
    }

    @GetMapping("/loan-against-fd")
    public String loanAgainstFD() {
        return "forward:/loan-against-fd.html";
    }
}
