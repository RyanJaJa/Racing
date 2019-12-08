package com.exce.repository;

import com.exce.model.BetOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface BetOrderDetailRepository extends JpaRepository<BetOrderDetail, BigInteger> {
}
