package com.exce.service;

import com.exce.model.BetWinningNumber;

import java.math.BigInteger;
import java.util.List;

public interface LotteryService {
    List<BetWinningNumber> getLotteryResult(BigInteger gameId,String createTime) throws Exception;
    BetWinningNumber updateLotteryResult(BigInteger winningId) throws Exception;
}
