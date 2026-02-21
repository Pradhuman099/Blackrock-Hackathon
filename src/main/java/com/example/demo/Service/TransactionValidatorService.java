package com.example.demo.Service;

import com.example.demo.Model.InvalidTransaction;
import com.example.demo.Model.Transaction;
import com.example.demo.Model.ValidationResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class TransactionValidatorService {

    public ValidationResponse validate(double wage, List<Transaction> txs) {

        Set<LocalDateTime> seen = new HashSet<>();
        List<Transaction> valid = new ArrayList<>();
        List<InvalidTransaction> invalid = new ArrayList<>();

        double maxAllowed = Math.min(wage * 0.10, 200000);

        for (Transaction tx : txs) {

            if (!seen.add(tx.getDate())) {
                invalid.add(new InvalidTransaction(tx, "Duplicate timestamp"));
                continue;
            }

            if (tx.getRemanent() < 0) {
                invalid.add(new InvalidTransaction(tx, "Negative remanent"));
                continue;
            }

            if (tx.getRemanent() > maxAllowed) {
                invalid.add(new InvalidTransaction(tx, "Exceeds max investable limit"));
                continue;
            }

            if (tx.getAmount()<0){
                invalid.add(new InvalidTransaction(tx,"Negative amounts are not allowed"));
                continue;
            }

            valid.add(tx);
        }

        return new ValidationResponse(valid, invalid);
    }
}
