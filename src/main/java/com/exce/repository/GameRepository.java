package com.exce.repository;

import com.exce.model.BetOrder;
import com.exce.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface GameRepository extends JpaRepository<Game, BigInteger> {

}
