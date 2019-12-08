package com.exce.controller;

import com.exce.dto.ResponsePayload;
import com.exce.exception.GoldLuckException;
import com.exce.exception.SystemErrorCode;
import com.exce.model.OpenCodeBjpk10;
import com.exce.model.OpenCodeCqssc;
import com.exce.model.OpenCodeMlaft;
import com.exce.service.OpenCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "lottery/openCode")
public class OpenCodeController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "OpenCodeBjpk10Service")
    OpenCodeService<OpenCodeBjpk10> openCodeBjpk10Service;
    @Resource(name = "OpenCodeCqsscService")
    OpenCodeService<OpenCodeCqssc> openCodeCqsscService;
    @Resource(name = "OpenCodeMlaftService")
    OpenCodeService<OpenCodeMlaft> openCodeMlaftService;

    @RequestMapping(value = "bjpk10", method = RequestMethod.GET)
    public ResponsePayload getBjpk10(@PageableDefault(value = 12, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            return new ResponsePayload<>(openCodeBjpk10Service.findAll(pageable), HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("getBjpk10 error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getBjpk10 error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    @RequestMapping(value = "bjpk10Video", method = RequestMethod.GET)
    public ResponsePayload getBjpk10Video() {
        try {
            return new ResponsePayload<>(openCodeBjpk10Service.getOpenCodeVideo(false), HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("getBjpk10 error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getBjpk10 error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    @RequestMapping(value = "cqssc", method = RequestMethod.GET)
    public ResponsePayload getCqssc(@PageableDefault(value = 12, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            return new ResponsePayload<>(openCodeCqsscService.findAll(pageable), HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("getCqssc error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getCqssc error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }

    @RequestMapping(value = "mlaft", method = RequestMethod.GET)
    public ResponsePayload getMlaft(@PageableDefault(value = 12, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        try {
            return new ResponsePayload<>(openCodeMlaftService.findAll(pageable), HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("getMlaft error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getMlaft error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }
}
