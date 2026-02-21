package com.example.demo.Service;

import com.example.demo.Model.ExpenseDTO;
import com.example.demo.Model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    public List<Transaction> buildTransactions(List<ExpenseDTO> expenses) {
        return expenses.stream()
                .map(e -> {
                    double ceiling = Math.ceil(e.getAmount() / 100.0) * 100;
                    double remanent = ceiling - e.getAmount();
                    return Transaction.builder()
                            .date(e.getDate())
                            .amount(e.getAmount())
                            .ceiling(ceiling)
                            .remanent(remanent)
                            .build();
                })
                .toList();
    }
}
