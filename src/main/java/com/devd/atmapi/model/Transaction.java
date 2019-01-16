//package com.devd.atmapi.model;
//
//import com.devd.atmapi.enums.TransactionType;
//
//import javax.persistence.*;
//import java.util.Date;
//
//@Entity
//public class Transaction {
//
//    @Id
//    private String transactionId;
//
//    @Basic(optional = false)
//    @Column(insertable = false, updatable = false)
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date transactionDate;
//
//    private Integer amount;
//
//    @Enumerated(EnumType.STRING)
//    @Column(length = 10)
//    private TransactionType type;
//
//    @ManyToOne
//    @JoinColumn(name = "account_number", nullable = false)
//    private Account account;
//
//    public String getTransactionId() {
//        return transactionId;
//    }
//
//    public void setTransactionId(String transactionId) {
//        this.transactionId = transactionId;
//    }
//
//    public Date getTransactionDate() {
//        return transactionDate;
//    }
//
//    public void setTransactionDate(Date transactionDate) {
//        this.transactionDate = transactionDate;
//    }
//
//    public Integer getAmount() {
//        return amount;
//    }
//
//    public void setAmount(Integer amount) {
//        this.amount = amount;
//    }
//
//    public TransactionType getType() {
//        return type;
//    }
//
//    public void setType(TransactionType type) {
//        this.type = type;
//    }
//
//    public Account getAccount() {
//        return account;
//    }
//
//    public void setAccount(Account account) {
//        this.account = account;
//    }
//}
