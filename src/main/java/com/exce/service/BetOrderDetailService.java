package com.exce.service;

import com.exce.model.BetOrderDetail;

import java.math.BigInteger;
import java.util.Optional;

public interface BetOrderDetailService {

    Optional<BetOrderDetail> save(BetOrderDetail betOrderDetail);

    void delete(BigInteger id);

    void delete(BetOrderDetail betOrderDetail);

    Optional<BetOrderDetail> findOne(BigInteger id);
}
