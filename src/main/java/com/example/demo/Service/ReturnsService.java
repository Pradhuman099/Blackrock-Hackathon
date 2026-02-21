package com.example.demo.Service;

import com.example.demo.Model.ReturnsRequest;
import com.example.demo.Model.ReturnsResponse;
import com.example.demo.Model.SavingsResult;
import com.example.demo.Model.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnsService {

    private static final double NPS_RATE = 0.0711;
    private static final double INDEX_RATE = 0.1449;

    public ReturnsResponse calculateNps(
            ReturnsRequest req,
            List<SavingsResult> savings) {

        int years = req.getAge() < 60 ? 60 - req.getAge() : 5;

        List<SavingsResult> enriched = savings.stream()
                .map(s -> {

                    double future =
                            s.getAmount() *
                                    Math.round(Math.pow(1 + NPS_RATE, years) * 100.0) / 100.0;

                    double real = Math.round(future / Math.pow(1.055, years) * 100.0) / 100.0;

                    double taxBenefit =
                            calculateTaxBenefit(
                                    req.getWage(),
                                    s.getAmount()
                            );

                    return new SavingsResult(
                            s.getStart(),
                            s.getEnd(),
                            s.getAmount(),
                            Math.round((real-s.getAmount()) * 100.0) / 100.0,
                            taxBenefit
                    );
                })
                .toList();

        return buildResponse(req.getTransactions(), enriched);
    }

    public ReturnsResponse calculateIndex(
            ReturnsRequest req,
            List<SavingsResult> savings) {

        int years = req.getAge() < 60 ? 60 - req.getAge() : 5;

        List<SavingsResult> enriched = savings.stream()
                .map(s -> {

                    double future =
                            s.getAmount() *
                                    Math.round(Math.pow(1 + INDEX_RATE, years) * 100.0) / 100.0;

                    double real = Math.round(future / Math.pow(1.055, years) * 100.0) / 100.0;

                    return new SavingsResult(
                            s.getStart(),
                            s.getEnd(),
                            s.getAmount(),
                            Math.round((real-s.getAmount()) * 100.0) / 100.0,
                            0.0
                    );
                })
                .toList();

        return buildResponse(req.getTransactions(), enriched);
    }

    private ReturnsResponse buildResponse(
            List<Transaction> txs,
            List<SavingsResult> savings) {

        double totalAmount =
                txs.stream().mapToDouble(Transaction::getAmount).filter(amt -> amt>0).sum();

        double totalCeiling =
                txs.stream().mapToDouble(Transaction::getCeiling).filter(amt -> amt>0).sum();

        return new ReturnsResponse(
                totalAmount,
                totalCeiling,
                savings
        );
    }

    private double calculateTaxBenefit(double income, double invested) {

        double deduction = Math.min(
                invested,
                Math.min(income * 0.10, 200000)
        );

        return tax(income) - tax(income - deduction);
    }

    private double tax(double income) {

        if (income <= 700000) return 0;
        if (income <= 1000000) return (income - 700000) * 0.10;
        if (income <= 1200000) return 30000 + (income - 1000000) * 0.15;
        if (income <= 1500000) return 60000 + (income - 1200000) * 0.20;

        return 120000 + (income - 1500000) * 0.30;
    }
}