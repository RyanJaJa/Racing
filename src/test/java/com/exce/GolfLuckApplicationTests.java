package com.exce;

import com.exce.service.FinancialService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GolfLuckApplicationTests {

    @Resource(name = "SmilePayService")
    FinancialService smilePayService;

    @Test
    public void contextLoads() {
    }

    //@Test
    public void smilePay() {
        try {
            //smilePayService.getQrCode(PaymentMode.QQ, "test002", "goods", Double.valueOf("10.00"), "http://api-dev.gl5168.net:2052/callback/financial", "https://www.google.com.tw");
            smilePayService.getBankUrl("COMm", "test002", "goods", Double.valueOf("10.00"), "http://api-dev.gl5168.net:2052/callback/financial", "https://www.google.com.tw");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
