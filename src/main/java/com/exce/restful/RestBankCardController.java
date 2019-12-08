package com.exce.restful;

import com.exce.dto.ResponsePayload;
import com.exce.exception.GoldLuckException;
import com.exce.exception.SystemErrorCode;
import com.exce.model.BankCard;
import com.exce.service.BankCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigInteger;
import java.util.Optional;

@RestController
@RequestMapping(value = "rest/bankCard")
public class RestBankCardController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BankCardService bankCardService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<BankCard> get(@PathVariable("id") BigInteger id) {
        try {
            Optional<BankCard> bankCardOptional = bankCardService.findOne(id);
            if (bankCardOptional.isPresent()) {
                return new ResponsePayload<>(bankCardOptional.get(), HttpStatus.OK);
            } else {
                return new ResponsePayload<>(HttpStatus.NOT_FOUND);
            }
        } catch (GoldLuckException e) {
            logger.error("get error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("get error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<BankCard> create(@RequestBody BankCard request) {
        try {
            Optional<BankCard> bankCardOptional = bankCardService.save(request);
            return new ResponsePayload<>(bankCardOptional.get(), HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("create error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("create error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<BankCard> update(@PathVariable("id") BigInteger id, @RequestBody BankCard request) {
        try {

            Optional<BankCard> bankCardOptional = bankCardService.findOne(id);
            if (bankCardOptional.isPresent()) {
                BankCard bankCard = bankCardOptional.get();
                bankCard.setAccountName(request.getAccountName());
                bankCard.setAccountNumber(request.getAccountNumber());
                bankCard.setBankName(request.getBankName());
                bankCard.setTitle(request.getTitle());
                bankCard.setWebsite(request.getWebsite());
                bankCardService.save(bankCard);
                return new ResponsePayload<>(bankCard, HttpStatus.OK);
            } else {
                return new ResponsePayload<>(HttpStatus.NOT_FOUND);
            }

        } catch (GoldLuckException e) {
            logger.error("update error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("update error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<BankCard> delete(@PathVariable("id") BigInteger id) {
        try {
            bankCardService.delete(id);
            return new ResponsePayload<>(HttpStatus.OK);
        } catch (GoldLuckException e) {
            ;
            logger.error("delete error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("delete error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

}
