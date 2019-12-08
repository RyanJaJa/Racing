package com.exce.model;


import com.exce.model.parameter.LotteryCategory;
import com.exce.model.parameter.LotteryItem;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;


@Data
@Entity
public class BetOrderDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 2554228764008012876L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, columnDefinition = "BIGINT", nullable = false)
    private BigInteger id;
    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private BetOrder betOrder;
    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private LotteryCategory lotteryCategory;
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    private LotteryItem lotteryItem;
    @Column(nullable = false)
    private String raffleNumber;
    @Column(nullable = false)
    private Double price;
    private Boolean winning;
    private Double odds;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(
            name = "detail_winning_mapping",
            joinColumns = @JoinColumn(name = "bet_order_detail_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "winning_number_id", referencedColumnName = "id"))
    @JsonIgnore
    private Set<BetWinningNumber> betWinningNumbers = Sets.newHashSet();

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BetOrder getBetOrder() {
        return betOrder;
    }

    public void setBetOrder(BetOrder betOrder) {
        this.betOrder = betOrder;
    }

    public LotteryCategory getLotteryCategory() {
        return lotteryCategory;
    }

    public void setLotteryCategory(LotteryCategory lotteryCategory) {
        this.lotteryCategory = lotteryCategory;
    }

    public LotteryItem getLotteryItem() {
        return lotteryItem;
    }

    public void setLotteryItem(LotteryItem lotteryItem) {
        this.lotteryItem = lotteryItem;
    }

    public Set<BetWinningNumber> getBetWinningNumbers() {
        return betWinningNumbers;
    }

    public void setBetWinningNumbers(Set<BetWinningNumber> betWinningNumbers) {
        this.betWinningNumbers = betWinningNumbers;
    }

    public String getRaffleNumber() {
        return raffleNumber;
    }

    public void setRaffleNumber(String raffleNumber) {
        this.raffleNumber = raffleNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getWinning() {
        return winning;
    }

    public void setWinning(Boolean winning) {
        this.winning = winning;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    /*@Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("BetDetail [id=");
        builder.append(getId());
        builder.append(", betOrder=");
        if (getBetOrder() != null) {
            builder.append(getBetOrder().toString());
        } else {
            builder.append(getBetOrder());
        }
        builder.append(", betItem=");
        if (getBetItem() != null) {
            builder.append(getBetItem().toString());
        } else {
            builder.append(getBetItem());
        }
        builder.append(", betType=");
        builder.append(getBetType());
        builder.append(", raffleNumber=");
        builder.append(getRaffleNumber());
        builder.append(", betValue=");
        builder.append(getBetValue());
        builder.append(", winning=");
        builder.append(getWinning());
        builder.append(", odds=");
        builder.append(getOdds());
        builder.append("]");
        return builder.toString();
    }*/
}
