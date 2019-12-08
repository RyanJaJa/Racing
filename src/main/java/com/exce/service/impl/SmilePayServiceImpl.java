package com.exce.service.impl;

import com.exce.exception.PaymentException;
import com.exce.model.parameter.PaymentMode;
import com.exce.service.FinancialService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service("SmilePayService")
@Scope("prototype")
@Transactional
public class SmilePayServiceImpl implements FinancialService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${goldluck.setting.smilePay.merchantId}")
    private String merchantId;
    @Value("${goldluck.setting.smilePay.qrcodeUrl}")
    private String qrcodeUrl;
    @Value("${goldluck.setting.smilePay.bankUrl}")
    private String bankUrl;
    @Value("${goldluck.setting.smilePay.Sha2Key}")
    private String Sha2Key;
    @Value("${goldluck.setting.smilePay.HashIv}")
    private String HashIv;
    final String encodeType = "SHA2";
    private static final List<String> bankCode = Lists.newArrayList("ICBC", "ABC", "BOC", "CCB", "COMM", "CNCB", "CEB", "HXB", "CMBC", "GDB", "PAB", "CMB", "CIB", "SPDB");

    /**
     * @param mode        // 支付渠道
     * @param orderNo     // 訂單號碼
     * @param orderAmount // 價格
     * @param goods       // 商品名
     * @param notifyUrl   // 支付成功後，通知付款結果
     * @param returnUrl   // 支付成功導頁
     * @return
     */
    @Override
    public String getQrCode(final PaymentMode mode, final String orderNo, final String goods, final Double orderAmount, final String notifyUrl, final String returnUrl) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(orderNo)) {
            logger.warn("訂單號傳入值為 Blank or Empty or null");
            throw new PaymentException("訂單號傳入值為 Blank or Empty or null");
        }
        final String bank = "";
        String payMode;
        switch (mode) {
            case WECHAT:
                payMode = "Wechat";
                if (orderAmount < 150 || orderAmount > 5000) {
                    logger.warn("支付超出限制範圍, amount: {}, but wechat limit is : 150-5000", orderAmount.toString());
                    throw new PaymentException("支付超出限制範圍, amount: " + orderAmount + ", but wechat limit is : 150-5000");
                }
                break;
            case ALIPAY:
                payMode = "Alipay";
                //微笑支付尚未完成，無交期
                break;
            case BANK:
                payMode = "Bank";
                break;
            case QQ:
                payMode = "QQ";
                if (orderAmount < 10 || orderAmount > 3000) {
                    logger.warn("支付超出限制範圍, amount: {}, but QQ limit is : 10-3000", orderAmount.toString());
                    throw new PaymentException("支付超出限制範圍, amount: " + orderAmount + ", but QQ limit is : 10-3000");
                }
                break;
            case QQ_H5:
                payMode = "QQH5";
                if (orderAmount < 10 || orderAmount > 3000) {
                    logger.warn("支付超出限制範圍, amount: {}, but QQH5 limit is : 10-3000", orderAmount.toString());
                    throw new PaymentException("支付超出限制範圍, amount: " + orderAmount + ", but QQH5 limit is : 10-3000");
                }
                break;
            default:
                logger.warn("Unsupported payment mode! payMode: {}", mode);
                throw new PaymentException("Unsupported payment mode!");
        }

        // 依英文字母a~z順序排列，最前面加上SHA2Key，最後再加上HashIV
        List<BasicNameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("bank", bank));
        params.add(new BasicNameValuePair("encodeType", encodeType));
        params.add(new BasicNameValuePair("goods", StringUtils.defaultString(goods)));
        params.add(new BasicNameValuePair("merchantId", merchantId));
        params.add(new BasicNameValuePair("notifyUrl", StringUtils.defaultString(notifyUrl)));
        params.add(new BasicNameValuePair("orderAmount", String.valueOf(orderAmount)));
        params.add(new BasicNameValuePair("orderNo", orderNo));
        params.add(new BasicNameValuePair("payMode", payMode));
        params.add(new BasicNameValuePair("returnUrl", StringUtils.defaultString(returnUrl)));
        StringBuilder paramBuilder = new StringBuilder();
        for (BasicNameValuePair item : params) {
            paramBuilder.append(item.getName() + "=" + item.getValue() + "");
            if (params.indexOf(item) != params.size() - 1) {
                paramBuilder.append("&");
            }
        }
        String param = URLEncoder.encode("SHA2Key=" + Sha2Key + "&" + paramBuilder.toString() + "&HashIV=" + HashIv, StandardCharsets.UTF_8.name()).toLowerCase();
        String signSha2 = this.sha256(param).toUpperCase();
        // 此兩參數不需加密
        params.add(new BasicNameValuePair("memo", ""));
        params.add(new BasicNameValuePair("signSHA2", signSha2));

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        params.parallelStream().forEach(item -> multiValueMap.add(item.getName(), item.getValue()));
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(qrcodeUrl, multiValueMap, String.class);
        return result;
    }

    /**
     * @param bank        // 支付渠道
     * @param orderNo     // 訂單號碼
     * @param orderAmount // 價格
     * @param goods       // 商品名
     * @param notifyUrl   // 支付成功後，通知付款結果
     * @param returnUrl   // 支付成功導頁
     * @return
     */
    @Override
    public String getBankUrl(final String bank, final String orderNo, final String goods, final Double orderAmount, final String notifyUrl, final String returnUrl) throws UnsupportedEncodingException {
        if (StringUtils.isBlank(orderNo)) {
            logger.warn("訂單號傳入值為 Blank or Empty or null");
            throw new PaymentException("訂單號傳入值為 Blank or Empty or null");
        }
        if (!bankCode.contains(bank) && StringUtils.isNotBlank(bank)) {
            logger.warn("不支援此一銀行代碼: {}", bank);
            throw new PaymentException("不支援此一銀行代碼: " + bank);
        }
        if (orderAmount < 10 || orderAmount > 10000) {
            logger.warn("支付超出限制範圍, amount: {}, but bank limit is : 10-10000", orderAmount.toString());
            throw new PaymentException("支付超出限制範圍, amount: " + orderAmount + ", but bank limit is : 10-10000");
        }
        final String payMode = "Bank";

        // 依英文字母a~z順序排列，最前面加上SHA2Key，最後再加上HashIV
        List<BasicNameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("bank", bank));
        params.add(new BasicNameValuePair("encodeType", encodeType));
        params.add(new BasicNameValuePair("goods", StringUtils.defaultString(goods)));
        params.add(new BasicNameValuePair("merchantId", merchantId));
        params.add(new BasicNameValuePair("notifyUrl", StringUtils.defaultString(notifyUrl)));
        params.add(new BasicNameValuePair("orderAmount", String.valueOf(orderAmount)));
        params.add(new BasicNameValuePair("orderNo", orderNo));
        params.add(new BasicNameValuePair("payMode", payMode));
        params.add(new BasicNameValuePair("returnUrl", StringUtils.defaultString(returnUrl)));
        StringBuilder paramBuilder = new StringBuilder();
        for (BasicNameValuePair item : params) {
            paramBuilder.append(item.getName() + "=" + item.getValue() + "");
            if (params.indexOf(item) != params.size() - 1) {
                paramBuilder.append("&");
            }
        }
        String param = URLEncoder.encode("SHA2Key=" + Sha2Key + "&" + paramBuilder.toString() + "&HashIV=" + HashIv, StandardCharsets.UTF_8.name()).toLowerCase();
        String signSha2 = this.sha256(param).toUpperCase();
        // 此兩參數不需加密
        params.add(new BasicNameValuePair("memo", ""));
        params.add(new BasicNameValuePair("signSHA2", signSha2));

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        params.parallelStream().forEach(item -> multiValueMap.add(item.getName(), item.getValue()));
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.postForObject(qrcodeUrl, multiValueMap, String.class);
        return result;
    }

    private String sha256(final String strText) {
        return this.sha(strText, "SHA-256");
    }

    private String sha(final String strText, final String strType) {
        String strResult = null;
        if (strText != null && strText.length() > 0) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(strType);
                messageDigest.update(strText.getBytes());
                byte byteBuffer[] = messageDigest.digest();
                StringBuffer strHexString = new StringBuffer();
                for (int i = 0; i < byteBuffer.length; i++) {
                    String hex = Integer.toHexString(0xff & byteBuffer[i]);
                    if (hex.length() == 1) {
                        strHexString.append('0');
                    }
                    strHexString.append(hex);
                }
                strResult = strHexString.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return strResult;
    }
}
