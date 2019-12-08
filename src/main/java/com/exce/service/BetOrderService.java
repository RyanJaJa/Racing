package com.exce.service;

import com.exce.model.BetOrder;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * 遊戲下注相關Action說明:
 * getBetDetail: 取得玩家投注詳細內容
 * singleBet: 投注單期下單
 * multipleBet: 投注追號下單
 * getGameList: 取得遊戲列表
 * getChaseBetDetail: 玩家追號查詢
 * stopChaseBet: 停止追號
 * getBetBalance: 盈虧記錄查詢
 */
public interface BetOrderService {

    Optional<BetOrder> save(BetOrder betOrder);

    void delete(BigInteger id);

    void delete(BetOrder betOrder);

    Optional<BetOrder> findOne(BigInteger id);

    List<BetOrder> findAll();

    //Optional<BetOrder> Lottery(BetOrder betOrder) throws Exception;
    //Optional<Object> getChaseBetDetail(BigInteger userId, String createTime, String chaseStatus) throws Exception;
    //BetOrderResponse stopChaseBet(BigInteger userId) throws Exception;
    //BetOrderResponse getBetBalance(BigInteger userId) throws Exception;


}
