package com.tracw.tracw.service;

import com.tracw.tracw.model.Transaction;
import com.tracw.tracw.model.User;
import com.tracw.tracw.repository.TransactionRepository;
import com.tracw.tracw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void deposit(User user, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }

        // Update user balance
        user.setBalance(user.getBalance().add(amount));
        userRepository.save(user);

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType("DEPOSIT");
        transaction.setAmount(amount);
        transaction.setBalanceAfter(user.getBalance());
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    @Transactional
    public void withdraw(User user, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (user.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }

        // Update user balance
        user.setBalance(user.getBalance().subtract(amount));
        userRepository.save(user);

        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setUser(user);
        transaction.setType("WITHDRAW");
        transaction.setAmount(amount);
        transaction.setBalanceAfter(user.getBalance());
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
    }

    @Transactional
    public void transfer(User sender, User recipient, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds for transfer");
        }

        if (sender.getId().equals(recipient.getId())) {
            throw new IllegalArgumentException("Cannot transfer money to yourself");
        }

        // Deduct from sender
        sender.setBalance(sender.getBalance().subtract(amount));
        userRepository.save(sender);

        // Add to recipient
        recipient.setBalance(recipient.getBalance().add(amount));
        userRepository.save(recipient);

        // Create transaction record for sender (TRANSFER_OUT)
        Transaction senderTransaction = new Transaction();
        senderTransaction.setUser(sender);
        senderTransaction.setType("TRANSFER_OUT");
        senderTransaction.setAmount(amount);
        senderTransaction.setBalanceAfter(sender.getBalance());
        senderTransaction.setTimestamp(LocalDateTime.now());
        senderTransaction.setDescription("Transfer to user ID: " + recipient.getUsername());
        transactionRepository.save(senderTransaction);

        // Create transaction record for recipient (TRANSFER_IN)
        Transaction recipientTransaction = new Transaction();
        recipientTransaction.setUser(recipient);
        recipientTransaction.setType("TRANSFER_IN");
        recipientTransaction.setAmount(amount);
        recipientTransaction.setBalanceAfter(recipient.getBalance());
        recipientTransaction.setTimestamp(LocalDateTime.now());
        recipientTransaction.setDescription("Transfer from user ID: " + sender.getUsername());
        transactionRepository.save(recipientTransaction);
    }

    public List<Transaction> getUserTransactions(User user) {
        return transactionRepository.findByUserOrderByTimestampDesc(user);
    }
}
