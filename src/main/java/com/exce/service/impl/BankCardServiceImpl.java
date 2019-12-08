package com.exce.service.impl;

import com.exce.exception.GoldLuckException;
import com.exce.exception.SystemErrorCode;
import com.exce.model.BankCard;
import com.exce.model.User;
import com.exce.repository.BankCardRepository;
import com.exce.service.BankCardService;
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
public class BankCardServiceImpl implements BankCardService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BankCardRepository bankCardRepository;

    @Override
    public Optional<BankCard> save(BankCard request) {

        User player = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BankCard bankCard = new BankCard();
        bankCard.setWebsite(request.getWebsite());
        bankCard.setBankName(request.getBankName());
        bankCard.setAccountNumber(request.getAccountNumber());
        bankCard.setAccountName(request.getAccountName());
        bankCard.setPlayer((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        bankCard.setTitle(request.getTitle());

        if(bankCardRepository.countByPlayer(player) > 4){
            throw new GoldLuckException(SystemErrorCode.NOT_ADD_BANKCARD,SystemErrorCode.NOT_ADD_BANKCARD.toString());
        }

        if(bankCardRepository.existsByAccountName(bankCard.getAccountName()) && bankCardRepository.existsByPlayer(player)) {
            throw new GoldLuckException(SystemErrorCode.ACCOUNTNAME_ALREADY_EXIST,SystemErrorCode.ACCOUNTNAME_ALREADY_EXIST.toString());
        }

        return Optional.ofNullable(bankCardRepository.save(bankCard));
    }

    @Override
    public void delete(BigInteger id) { bankCardRepository.delete(id); }

    @Override
    public void delete(BankCard bankCard) {
        bankCardRepository.delete(bankCard);
    }

    @Override
    public Optional<BankCard> findOne(BigInteger id) {
        return Optional.ofNullable(bankCardRepository.findOne(id));
    }

    @Override
    public List<BankCard> findAll() {
        return bankCardRepository.findAll();
    }

}
