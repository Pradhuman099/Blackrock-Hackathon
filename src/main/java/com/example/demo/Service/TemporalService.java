package com.example.demo.Service;

import com.example.demo.Model.KPeriod;
import com.example.demo.Model.PPeriod;
import com.example.demo.Model.QPeriod;
import com.example.demo.Model.SavingsResult;
import com.example.demo.Model.Transaction;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class TemporalService {

    public List<SavingsResult> groupByKPeriods(
            List<Transaction> transactions,
            List<KPeriod> kList) {

        if (kList == null || kList.isEmpty()) {
            return new ArrayList<>();
        }

        // Sort transactions by date
        List<Transaction> sortedTxs = new ArrayList<>(transactions);
        sortedTxs.sort(Comparator.comparing(Transaction::getDate));

        // Calculate prefix sum of remanent values
        List<Double> prefix = new ArrayList<>();
        double sum = 0;
        for (Transaction tx : sortedTxs) {
            sum += tx.getRemanent();
            prefix.add(sum);
        }

        // Group by K periods and calculate total savings for each period
        List<SavingsResult> results = new ArrayList<>();

        for (KPeriod k : kList) {
            int startIdx = lowerBound(sortedTxs, k.getStart());
            int endIdx = upperBound(sortedTxs, k.getEnd());

            double total = 0;
            if (startIdx <= endIdx) {
                total = prefix.get(endIdx)
                        - (startIdx > 0 ? prefix.get(startIdx - 1) : 0);
            }

            results.add(new SavingsResult(k.getStart(), k.getEnd(), total));
        }

        return results;
    }

    private int lowerBound(List<Transaction> txs, LocalDateTime target) {

        int left = 0;
        int right = txs.size() - 1;
        int result = txs.size();

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (!txs.get(mid).getDate().isBefore(target)) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return result;
    }

    private int upperBound(List<Transaction> txs, LocalDateTime target) {

        int left = 0;
        int right = txs.size() - 1;
        int result = -1;

        while (left <= right) {

            int mid = left + (right - left) / 2;

            if (!txs.get(mid).getDate().isAfter(target)) {
                result = mid;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }
}
