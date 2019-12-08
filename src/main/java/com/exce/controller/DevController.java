package com.exce.controller;

import com.exce.dto.OpenCode;
import com.exce.dto.OpenCodeRawData;
import com.exce.exception.GoldLuckException;
import com.exce.exception.SystemErrorCode;
import com.exce.model.OpenCodeBjpk10;
import com.exce.model.OpenCodeCqssc;
import com.exce.model.OpenCodeMlaft;
import com.exce.dto.ResponsePayload;
import com.exce.service.OpenCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "dev")
public class DevController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name = "OpenCodeBjpk10Service")
    OpenCodeService<OpenCodeBjpk10> openCodeBjpk10Service;
    @Resource(name = "OpenCodeCqsscService")
    OpenCodeService<OpenCodeCqssc> openCodeCqsscService;
    @Resource(name = "OpenCodeMlaftService")
    OpenCodeService<OpenCodeMlaft> openCodeMlaftService;

    @RequestMapping(value = "openCode", method = RequestMethod.GET)
    public ResponsePayload getBjpk10Dev(@RequestParam String code) {
        try {

            OpenCode openCode = new OpenCode();
            openCode.setRows(5);
            openCode.setCode(code);
            openCode.setRemain("dev");
            switch (code) {
                case "bjpk10":
                    List<OpenCodeBjpk10> openCodeBjpk10s = openCodeBjpk10Service.findFirst5ByOrderByExpectDesc();
                    openCodeBjpk10s.stream().forEach(item -> {
                                OpenCodeRawData raw = new OpenCodeRawData();
                                raw.setExpect(item.getExpect());
                                raw.setOpencode(item.getOpenCode());
                                raw.setOpentime(item.getOpenTime());
                                raw.setOpentimestamp(item.getOpenTimestamp());
                                openCode.getData().add(raw);
                            }
                    );
                    break;
                case "mlaft":
                    List<OpenCodeMlaft> openCodeMlafts = openCodeMlaftService.findFirst5ByOrderByExpectDesc();
                    openCodeMlafts.stream().forEach(item -> {
                                OpenCodeRawData raw = new OpenCodeRawData();
                                raw.setExpect(item.getExpect());
                                raw.setOpencode(item.getOpenCode());
                                raw.setOpentime(item.getOpenTime());
                                raw.setOpentimestamp(item.getOpenTimestamp());
                                openCode.getData().add(raw);
                            }
                    );
                    break;
                case "cqssc":
                    List<OpenCodeCqssc> openCodeCqsscs = openCodeCqsscService.findFirst5ByOrderByExpectDesc();
                    openCodeCqsscs.stream().forEach(item -> {
                                OpenCodeRawData raw = new OpenCodeRawData();
                                raw.setExpect(item.getExpect());
                                raw.setOpencode(item.getOpenCode());
                                raw.setOpentime(item.getOpenTime());
                                raw.setOpentimestamp(item.getOpenTimestamp());
                                openCode.getData().add(raw);
                            }
                    );
                    break;
                default:
                    break;
            }
            return new ResponsePayload<>(openCode, HttpStatus.OK);
        } catch (GoldLuckException e) {
            logger.error("getBjpk10 error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, e.getErrorCode(), e.getMessage());
        } catch (Exception e) {
            logger.error("getBjpk10 error : {} ", e.getMessage());
            return new ResponsePayload<>(HttpStatus.EXPECTATION_FAILED, SystemErrorCode.UNKNOWN_EXCEPTION, e.getMessage());
        }
    }
}
