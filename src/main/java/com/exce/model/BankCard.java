package com.exce.model;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Data
@Entity
public class BankCard extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4329379902210519823L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, columnDefinition = "BIGINT", nullable = false)
    private BigInteger id;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "player_id")
    private User player;

    @Column(length = 100)
    private String title;

    @Column(length = 50)
    private String bankName;

    @Column(length = 256)
    private String website;

    @Column(length = 100)
    private String accountName;

    @Column(length = 20)
    private String accountNumber;

    public BigInteger getId() { return id; }

    public void setId(BigInteger id) { this.id = id; }

    public User getPlayer() { return player; }

    public void setPlayer(User player) { this.player = player; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getBankName() { return bankName; }

    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getWebsite() { return website; }

    public void setWebsite(String website) { this.website = website; }

    public String getAccountName() { return accountName; }

    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getAccountNumber() { return accountNumber; }

    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

}
