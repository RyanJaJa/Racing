package com.exce.restful;

import com.exce.exception.GoldLuckException;
import com.exce.exception.SystemErrorCode;
import com.exce.model.BetOrder;
import com.exce.model.BetOrderDetail;
import com.exce.dto.ResponsePayload;
import com.exce.service.BetOrderDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigInteger;
import java.util.Optional;

@RestController
@RequestMapping(value = "rest/betDetail")
public class RestBetOrderDetailController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BetOrderDetailService betOrderDetailService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<BetOrderDetail> get(@PathVariable("id") BigInteger id) {
        try {
            Optional<BetOrderDetail> betOrderDetailOptional = betOrderDetailService.findOne(id);
            if (betOrderDetailOptional.isPresent()) {
                return new ResponsePayload<>(betOrderDetailOptional.get(), HttpStatus.OK);
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
    public ResponseEntity<BetOrderDetail> create(@PathVariable("id") BigInteger id, @RequestBody BetOrderDetail request) {
        try {
            Optional<BetOrderDetail>  betOrderDetailOptional =  betOrderDetailService.save(request);
            return new ResponsePayload<>(betOrderDetailOptional.get(), HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("create error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("create error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<BetOrderDetail> update(@PathVariable("id") BigInteger id, @RequestBody BetOrderDetail request) {
        try {
            Optional<BetOrderDetail> betOrderDetailOptional = betOrderDetailService.findOne(id);
            if (betOrderDetailOptional.isPresent()) {
                BetOrderDetail betOrderDetail = betOrderDetailOptional.get();
                betOrderDetail.setOdds(request.getOdds());
                betOrderDetail.setRaffleNumber(request.getRaffleNumber());
                betOrderDetail.setBetWinningNumbers(request.getBetWinningNumbers());
                betOrderDetailService.save(betOrderDetail);
                return new ResponsePayload<>(betOrderDetail, HttpStatus.OK);
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
    public ResponseEntity<BetOrderDetail> delete(@PathVariable("id") BigInteger id) {
        try {
            betOrderDetailService.delete(id);
            return new ResponsePayload<>(HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("delete error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("delete error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

}
