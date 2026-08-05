package com.StudentLibrary.Studentlibrary.Repositories;


import com.StudentLibrary.Studentlibrary.Model.Payment;
import com.StudentLibrary.Studentlibrary.Model.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface PaymentRepository  extends JpaRepository<Payment, Integer>{

    long countByStatus(PaymentStatus status);

    @Query("SELECT COALESCE(SUM(p.amount),0) FROM Payment p WHERE p.status = com.StudentLibrary.Studentlibrary.Model.PaymentStatus.PAID")
    Double getTotalRevenue();


    List<Payment> findByUsername(String username);

    List<Payment> findByStatus(PaymentStatus status);
    Payment findTopByTransactionIdAndStatusOrderByIdDesc(
            Integer transactionId,
            PaymentStatus status
    );

}
