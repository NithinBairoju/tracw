package com.tracw.tracw.repository;

import com.tracw.tracw.model.Transaction;
import com.tracw.tracw.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUser(User user);

    List<Transaction> findByUserAndTimestampBetweenAndType(
            User user,
            LocalDateTime startTime,
            LocalDateTime endTime,
            String type);

    List<Transaction> findByUserOrderByTimestampDesc(User user);
}
