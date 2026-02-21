package com.example.demo.Controller;

import com.example.demo.Model.ExpenseDTO;
import com.example.demo.Model.TemporalFilterRequest;
import com.example.demo.Model.Transaction;
import com.example.demo.Model.TransactionValidationRequest;
import com.example.demo.Model.ValidationResponse;
import com.example.demo.Service.TemporalFilterService;
import com.example.demo.Service.TransactionService;
import com.example.demo.Service.TransactionValidatorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blackrock/challenge/v1")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransactionValidatorService validatorService;

    @Autowired
    private TemporalFilterService filterService;

    @PostMapping("/transactions:parse")
    public List<Transaction> parse(@RequestBody List<ExpenseDTO> expenses) {
        return transactionService.buildTransactions(expenses);
    }

    @PostMapping("/transactions:validator")
    public ValidationResponse validate(
            @Valid @RequestBody TransactionValidationRequest request) {

        return validatorService.validate(
                request.getWage(),
                request.getTransactions()
        );
    }

    @PostMapping("/transactions:filter")
    public ValidationResponse filter(
            @Valid @RequestBody TemporalFilterRequest request) {

        return filterService.applyTemporalRules(
                request.getTransactions(),
                request.getQ(),
                request.getP(),
                request.getK(),
                request.getWage()
        );
    }
}
