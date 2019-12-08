package com.exce.service;

import com.exce.model.BankCard;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

public interface BankCardService {

    Optional<BankCard> save(BankCard bankCard);

    void delete(BigInteger id);

    void delete(BankCard bankCard);

    Optional<BankCard> findOne(BigInteger id);

    List<BankCard> findAll();

}
