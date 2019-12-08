package com.exce.service;

import com.exce.model.parameter.PaymentMode;

import java.io.UnsupportedEncodingException;

public interface FinancialService {
    String getQrCode(PaymentMode mode, String orderNo, String goods, Double orderAmount, String notifyUrl, String returnUrl) throws UnsupportedEncodingException;
    String getBankUrl(String bank, String orderNo, String goods, Double orderAmount, String notifyUrl, String returnUrl) throws UnsupportedEncodingException;
}
