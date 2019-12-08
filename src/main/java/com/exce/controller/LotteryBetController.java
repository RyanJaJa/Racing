package com.exce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "lotteryBet")
public class LotteryBetController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

}
