package com.exce.controller;

import com.exce.dto.Account;
import com.exce.dto.Balance;
import com.exce.exception.GoldLuckException;
import com.exce.exception.SystemErrorCode;
import com.exce.dto.ResponsePayload;
import com.exce.model.TransactionHistory;
import com.exce.service.StoredValueService;
import com.exce.validation.StoredValueRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigInteger;

@RestController
@RequestMapping(value = "storedValue")
public class StoredValueController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StoredValueService storedValueService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new StoredValueRequestValidator());
    }

    /**
     * 取得user的最新一筆儲值餘額 <br>
     * request url: http://localhost:port/storedValue/transactionDetails/{userId}
     *
     * @param userId
     * @return ResponsePayload<Balance>
     * Example:  {
     * "data": {
     * "total": 54.3,
     * "transferAmount": null,
     * "transactionType": 1,
     * "currency": "CNY"
     * },
     * "errorCode": "99SYS00000",
     * "errorDescription": "Completed successfully"
     * }
     * @RequestMethod Get
     */

    @RequestMapping(value = "transactionDetails/{userId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponsePayload<Balance> getBalance(@Valid @PathVariable BigInteger userId) {
        logger.info("getBalance request : {} ", userId);

        try {
            return new ResponsePayload<>(storedValueService.getBalance(userId), HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("getBalance error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getBalance error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    /**
     * 取得user上下分紀錄，依照分頁顯示，依照建立時間排序，最新的在第一列 <br>
     * request url: http://localhost:port/storedValue/transactionDetails/{userId}/pagination?page=x&pageSize=x
     *
     * @param userId   (BigInteger，必填)
     * @param page     (int，第幾頁，從0開始，選填)
     * @param pageSize (int，每頁顯示筆數，選填)
     * @return ResponsePayload<Page<TransactionDetail>>
     * Example:{
     * "data": {
     * "content": [
     * {
     * "createTime": "2017-08-04 05:20:31",
     * "modifyTime": null,
     * "currencyCode": "CNY",
     * "transactionType": 1,
     * "withdrawAmount": null,
     * "depositAmount": 2.3,
     * "balanceBefore": 65.5,
     * "balanceAfter": 67.8
     * }
     * ],
     * "last": false,
     * "totalPages": 1,
     * "totalElements": 2,
     * "number": 0,
     * "size": 10,
     * "sort": null,
     * "numberOfElements": 2,
     * "first": true
     * },
     * "errorCode": "99SYS00000",
     * "errorDescription": "Completed successfully"
     * }
     * @RequestMethod Get
     */

    @RequestMapping(value = "transactionDetails/{userId}/pagination", method = RequestMethod.GET)
    public @ResponseBody
    ResponsePayload<Page<TransactionHistory>> getTransactionDetails(@PathVariable BigInteger userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        logger.info("getTransactionDetails request : {} , {}, {} ", userId, page, pageSize);

        try {
            Pageable pageable = new PageRequest(page, pageSize);
            return new ResponsePayload<>(storedValueService.findDetailsByUserId(userId, pageable), HttpStatus.OK);
        } catch (GoldLuckException e) {

            logger.error("getTransactionDetails error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getTransactionDetails error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    /**
     * 執行下分 <br>
     * request url: http://localhost:port/storedValue/withdraw
     *
     * @return TransactionDetail
     * @RequestMethod Put
     * @RequestBody {
     * "userId" : 1,
     * "balance" :
     * {
     * "transferAmount" : 1.1,
     * "transactionType" : "1",
     * "currency" : "CNY"
     * }
     * }
     * @Return Example:
     * {
     * "data": {
     * "createTime": "2017-08-04 05:32:08",
     * "modifyTime": null,
     * "currencyCode": "CNY",
     * "transactionType": 1,
     * "withdrawAmount": 1.2,
     * "depositAmount": null,
     * "balanceBefore": 54.3,
     * "balanceAfter": 53.1
     * },
     * "errorCode": "99SYS00000",
     * "errorDescription": "Completed successfully"
     * }
     */

    @RequestMapping(value = "withdraw", method = RequestMethod.PUT)
    public @ResponseBody
    ResponsePayload<TransactionHistory> withdraw(@Valid @RequestBody Account account) {
        logger.info("withdraw request : {} ", account.toString());

        try {
            return new ResponsePayload<>(storedValueService.withdraw(account), HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("withdraw error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("withdraw error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    /**
     * 執行上分 <br>
     * request url: http://localhost:port/storedValue/deposit
     *
     * @return TransactionDetail
     * @RequestMethod Put
     * @RequestBody {
     * "userId" : 1,
     * "balance" :
     * {
     * "transferAmount" : 1.1,
     * "transactionType" : "1",
     * "currency" : "CNY"
     * }
     * }
     * @Return Example:
     * {
     * "data": {
     * "createTime": "2017-08-04 05:34:56",
     * "modifyTime": null,
     * "currencyCode": "CNY",
     * "transactionType": 1,
     * "withdrawAmount": null,
     * "depositAmount": 1.3,
     * "balanceBefore": 67.8,
     * "balanceAfter": 69.1
     * },
     * "errorCode": "99SYS00000",
     * "errorDescription": "Completed successfully"
     * }
     */

    @RequestMapping(value = "deposit", method = RequestMethod.PUT)
    public @ResponseBody
    ResponsePayload<TransactionHistory> deposit(@Valid @RequestBody Account account) {
        logger.info("deposit request : {} ", account.toString());
        try {
            return new ResponsePayload<>(storedValueService.deposit(account), HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("deposit error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("deposit error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }
}
