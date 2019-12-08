package com.exce.service.impl;

import com.exce.model.BetOrderDetail;
import com.exce.repository.BetOrderDetailRepository;
import com.exce.service.BetOrderDetailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Optional;

@Service
@Scope("prototype")
@Transactional
public class BetOrderDetailServiceImpl implements BetOrderDetailService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BetOrderDetailRepository betOrderDetailRepository;

    @Override
    public Optional<BetOrderDetail> save(BetOrderDetail betOrderDetail) {
        return Optional.ofNullable(betOrderDetailRepository.save(betOrderDetail));
    }

    @Override
    public void delete(BigInteger id) {
        betOrderDetailRepository.delete(id);
    }

    @Override
    public void delete(BetOrderDetail betOrderDetail) {
        betOrderDetailRepository.delete(betOrderDetail);
    }

    @Override
    public Optional<BetOrderDetail> findOne(BigInteger id) {
        return Optional.ofNullable(betOrderDetailRepository.findOne(id));
    }
}
