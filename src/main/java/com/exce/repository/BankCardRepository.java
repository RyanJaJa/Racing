package com.exce.repository;

import com.exce.model.BankCard;
import com.exce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigInteger;

public interface BankCardRepository extends JpaRepository<BankCard, BigInteger> {

    Long countByPlayer(User user);

    boolean existsByAccountName(String accountName);

    boolean existsByPlayer(User user);

}
