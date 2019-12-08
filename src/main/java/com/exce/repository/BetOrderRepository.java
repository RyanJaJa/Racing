package com.exce.repository;

import com.exce.model.BetOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.Optional;

public interface BetOrderRepository extends JpaRepository<BetOrder, BigInteger>{


//    Optional<BetOrder> findByPlayerAndGameOrderByCreateTimeDesc(User user, Game game);
//
//
//
//    @Query(value="select o.createTime,o.game.name,o.chaseCount,o.chaseStatus,d.betItem,d.betType,d.raffleNumber from BetOrder o LEFT JOIN o.betOrderDetails d where o.player = ?1 and o.createTime between ?2 and ?3")
//    Optional<Object> findByPlayerAndCreateTimeBetween(User player, Calendar calendarStart, Calendar calendarEnd);
//
//    @Query(value="select o.createTime,o.game.name,o.chaseCount,o.chaseStatus,d.betItem,d.betType,d.raffleNumber from BetOrder o LEFT JOIN o.betOrderDetails d where o.player = ?1 and o.chaseStatus =?2 and o.createTime between ?3 and ?4")
//    Optional<Object> findByPlayerAndChaseStatusAndCreateTimeBetween(User player,String chaseStatus, Calendar calendarStart, Calendar calendarEnd);

}
