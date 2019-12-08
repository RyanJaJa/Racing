package com.exce.service.impl;

import com.exce.model.BetOrder;
import com.exce.model.User;
import com.exce.repository.BetOrderRepository;
import com.exce.service.BetOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;


@Service
@Scope("prototype")
@Transactional
public class BetOrderServiceImpl implements BetOrderService {

    private final Logger logger = LoggerFactory.getLogger(BetOrderServiceImpl.class);

    @Autowired
    BetOrderRepository betOrderRepository;

    @Override
    public Optional<BetOrder> save(BetOrder request) {
        BetOrder betOrder = new BetOrder();
        betOrder.setGame(request.getGame());
        betOrder.setPlayer((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        if (request.getChaseCount() != null) {
            betOrder.setChaseCount(request.getChaseCount());
        }
        betOrder.setChaseStatus(request.getChaseStatus());
        betOrder.setTotal(request.getTotal());
        betOrder.setBetOrderDetails(request.getBetOrderDetails());

        betOrder.getBetOrderDetails().parallelStream().forEach(item -> item.setBetOrder(betOrder));
        return Optional.ofNullable(betOrderRepository.save(betOrder));
    }

    @Override
    public void delete(BigInteger id) {
        betOrderRepository.delete(id);
    }

    @Override
    public void delete(BetOrder betOrder) {
        betOrderRepository.delete(betOrder);
    }

    @Override
    public Optional<BetOrder> findOne(BigInteger id) {
        return Optional.ofNullable(betOrderRepository.findOne(id));
    }

    @Override
    public List<BetOrder> findAll() {
        return betOrderRepository.findAll();
    }

}
