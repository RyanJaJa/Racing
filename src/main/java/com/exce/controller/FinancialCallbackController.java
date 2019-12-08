package com.exce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("callback/financial")
public class FinancialCallbackController {

    private static Logger log = LoggerFactory.getLogger(FinancialCallbackController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String defaultGet() {

        return "defaultGet!";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String defaultPost() {

        return "defaultPost!";
    }
}
