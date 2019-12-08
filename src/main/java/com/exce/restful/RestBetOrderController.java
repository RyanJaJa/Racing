package com.exce.restful;

import com.exce.exception.GoldLuckException;
import com.exce.exception.SystemErrorCode;
import com.exce.model.BetOrder;
import com.exce.dto.ResponsePayload;
import com.exce.service.BetOrderService;
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
@RequestMapping(value = "rest/betOrder")
public class RestBetOrderController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BetOrderService betOrderService;

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<BetOrder> get(@PathVariable("id") BigInteger id) {
        try {
            Optional<BetOrder> betOrderOptional = betOrderService.findOne(id);
            if (betOrderOptional.isPresent()) {
                return new ResponsePayload<>(betOrderOptional.get(), HttpStatus.OK);
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
    public ResponseEntity<BetOrder> create(@RequestBody BetOrder request) {
        try {
            Optional<BetOrder> betOrderOptional = betOrderService.save(request);
            return new ResponsePayload<>(betOrderOptional.get(), HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("create error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("create error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<BetOrder> update(@PathVariable("id") BigInteger id, @RequestBody BetOrder request) {
        try {

            Optional<BetOrder> betOrderOptional = betOrderService.findOne(id);
            if (betOrderOptional.isPresent()) {
                BetOrder betOrder = betOrderOptional.get();
                betOrder.setTotal(request.getTotal());
                betOrder.setStatus(request.getStatus());
                betOrder.setChaseStatus(request.getChaseStatus());
                if (request.getChaseCount() != null) {
                    betOrder.setChaseCount(request.getChaseCount());
                }
                betOrderService.save(betOrder);
                return new ResponsePayload<>(betOrder, HttpStatus.OK);
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
    public ResponseEntity<BetOrder> delete(@PathVariable("id") BigInteger id) {
        try {
            betOrderService.delete(id);
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
