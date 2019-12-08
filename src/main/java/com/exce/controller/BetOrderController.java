package com.exce.controller;


import com.exce.exception.GoldLuckException;
import com.exce.model.BetOrder;

import com.exce.dto.ResponsePayload;
import com.exce.service.BetOrderService;
import com.exce.validation.BetRequestValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * 遊戲下注相關Action說明:
 * getBetDetail: 取得玩家投注詳細內容
 * lotteryBet: 投注單期與追號下單
 * getGameList: 取得遊戲列表
 * getChaseBetDetail: 玩家追號查詢
 *
 * stopChaseBet: 停止追號
 * getBetBalance: 盈虧記錄查詢
 */

@RestController
@RequestMapping(value = "betOrder")
public class BetOrderController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BetOrderService betOrderService;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new BetRequestValidator());
    }

    /**
     * 取得玩家投注詳細內容 <br>
     * request url: http://localhost:port/bet/detail/user/{userId}/game/{gameId}
     * @param
     * @return ResponsePayload<BetOrderResponse>
     * @RequestMethod GET
     */

    @RequestMapping(value = "detail/user/{playerId}/game/{gameId}", method = RequestMethod.GET)
    public @ResponseBody
    ResponsePayload<Optional<BetOrder>> my() {
        //log.info("Controller - getBetDetail request : {} , {} ", playerId , gameId);
        try {

            //Optional<BetOrder> betOrders = betOrderService.getBetDetail(playerId,gameId);

            //payload.setData(betService.getBetDetail(playerId,gameId));
            //payload.setErrorCode(CustomError.OK.getErrorCode());
            //payload.setErrorDescription(CustomError.OK.getErrorDesc());
        } catch (GoldLuckException e) {

            //payload.setErrorCode(e.getErrorCode());
            //payload.setErrorDescription(e.getMessage());
            logger.error("Controller - getBetDetail error : {} ", e.getMessage());
        } catch (Exception e) {

            //payload.setErrorCode(CustomError.API_ERROR.getErrorCode());
            //payload.setErrorDescription(CustomError.API_ERROR.getErrorDesc());
            logger.error("Controller - getBetDetail error : {} ", e.getMessage());
        }
        //log.info("Controller - getBetDetail response : {} ", payload.toString());
        return null;
    }
}

