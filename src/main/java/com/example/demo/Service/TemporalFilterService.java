package com.example.demo.Service;

import com.example.demo.Model.InvalidTransaction;
import com.example.demo.Model.KPeriod;
import com.example.demo.Model.PPeriod;
import com.example.demo.Model.QPeriod;
import com.example.demo.Model.Transaction;
import com.example.demo.Model.ValidationResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TemporalFilterService {

    public ValidationResponse applyTemporalRules(
            List<Transaction> transactions,
            List<QPeriod> qList,
            List<PPeriod> pList,
            List<KPeriod> kList,
            Double wage) {

        List<Transaction> valid = new ArrayList<>();
        List<InvalidTransaction> invalid = new ArrayList<>();
        List<Transaction> seenTransactions = new ArrayList<>();

        // Step 1: Validate all transactions first
        for (Transaction tx : transactions) {
            try {
                // Check for duplicate timestamp
                if (isDuplicateTimestamp(tx, seenTransactions)) {
                    throw new IllegalArgumentException("Duplicate transaction");
                }

                // Check for negative amount
                if (tx.getAmount() < 0) {
                    throw new IllegalArgumentException("Negative amounts are not allowed");
                }

                // Calculate base ceiling (round up to nearest 100) and remanent
                double ceiling = Math.ceil(tx.getAmount() / 100.0) * 100;
                tx.setCeiling(ceiling);
                tx.setRemanent(ceiling - tx.getAmount());

                valid.add(tx);
                seenTransactions.add(tx);

            } catch (Exception e) {
                invalid.add(new InvalidTransaction(tx, e.getMessage()));
            }
        }

        // Step 2: Apply Q rule to all valid transactions (replace remanent with fixed value)
        for (Transaction tx : valid) {
            applyQRule(tx, qList);
        }

        // Step 3: Apply P rule to all valid transactions (add extra to remanent)
        for (Transaction tx : valid) {
            applyPRule(tx, pList);
        }

        // Step 4: Apply K rule to all valid transactions (set inkPeriod flag)
        for (Transaction tx : valid) {
            boolean isInKPeriod = isInKPeriod(tx, kList);
            tx.setInkPeriod(isInKPeriod);
        }

        List<Transaction> validTxWithout0Remanant = valid.stream().filter(validTx -> validTx.getRemanent() != 0).toList();

        return new ValidationResponse(validTxWithout0Remanant, invalid);
    }

    private boolean isDuplicateTimestamp(Transaction tx, List<Transaction> seenTransactions) {
        return seenTransactions.stream()
                .anyMatch(seen ->
                    seen.getDate() != null &&
                    seen.getDate().equals(tx.getDate()));
    }

    private boolean isInKPeriod(Transaction tx, List<KPeriod> kList) {
        if (kList == null || kList.isEmpty()) {
            return true; // If no K periods defined, all transactions are valid
        }

        return kList.stream()
                .anyMatch(k -> isWithin(tx.getDate(), k.getStart(), k.getEnd()));
    }

    private void applyQRule(Transaction tx, List<QPeriod> qList) {
        if (qList == null || qList.isEmpty()) {
            return;
        }

        // Find all matching Q periods
        List<QPeriod> matchingQ = qList.stream()
                .filter(q -> isWithin(tx.getDate(), q.getStart(), q.getEnd()))
                .toList();

        if (matchingQ.isEmpty()) {
            return;
        }

        // Find the latest start date
        LocalDateTime latestStart = matchingQ.stream()
                .map(QPeriod::getStart)
                .max(LocalDateTime::compareTo)
                .orElse(null);

        // Use the first Q period with the latest start date
        QPeriod selectedQ = matchingQ.stream()
                .filter(q -> q.getStart().equals(latestStart))
                .findFirst()
                .orElse(null);

        if (selectedQ != null) {
            // Replace remanent with fixed value
            tx.setRemanent(selectedQ.getFixed());
        }
    }

    private void applyPRule(Transaction tx, List<PPeriod> pList) {
        if (pList == null || pList.isEmpty()) {
            return;
        }

        // Sum all extra amounts from matching P periods
        double pExtra = pList.stream()
                .filter(p -> isWithin(tx.getDate(), p.getStart(), p.getEnd()))
                .mapToDouble(PPeriod::getExtra)
                .sum();

        if (pExtra > 0) {
            // Add extra to remanent
            tx.setRemanent(tx.getRemanent() + pExtra);
        }
    }

    private boolean isWithin(
            LocalDateTime date,
            LocalDateTime start,
            LocalDateTime end) {

        return !date.isBefore(start) && !date.isAfter(end);
    }
}
