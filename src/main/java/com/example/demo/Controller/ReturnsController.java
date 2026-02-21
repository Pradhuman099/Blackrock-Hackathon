package com.example.demo.Controller;

import com.example.demo.Model.PerformanceDTO;
import com.example.demo.Model.ReturnsRequest;
import com.example.demo.Model.ReturnsResponse;
import com.example.demo.Model.SavingsResult;
import com.example.demo.Model.Transaction;
import com.example.demo.Model.ValidationResponse;
import com.example.demo.Service.ReturnsService;
import com.example.demo.Service.TemporalFilterService;
import com.example.demo.Service.TemporalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.List;

@RestController
@RequestMapping("/blackrock/challenge/v1")
public class ReturnsController {

    @Autowired
    private TemporalService temporalService;

    @Autowired
    private TemporalFilterService temporalFilterService;

    @Autowired
    private ReturnsService returnsService;

    @PostMapping("/returns:nps")
    public ReturnsResponse calculateNps(
            @Valid @RequestBody ReturnsRequest request) {

        // Apply temporal rules with validation (like transactions:filter)
        ValidationResponse validationResponse = temporalFilterService.applyTemporalRules(
                request.getTransactions(),
                request.getQ(),
                request.getP(),
                request.getK(),
                request.getWage()
        );

        // Use only valid transactions for returns calculation
        List<Transaction> validTransactions = validationResponse.getValid();

        // Group valid transactions by K periods to create savings results
        List<SavingsResult> savings = temporalService.groupByKPeriods(
                validTransactions,
                request.getK()
        );

        return returnsService.calculateNps(request, savings);
    }

    @PostMapping("/returns:index")
    public ReturnsResponse calculateIndex(
            @Valid @RequestBody ReturnsRequest request) {

        // Apply temporal rules with validation (like transactions:filter)
        ValidationResponse validationResponse = temporalFilterService.applyTemporalRules(
                request.getTransactions(),
                request.getQ(),
                request.getP(),
                request.getK(),
                request.getWage()
        );

        // Use only valid transactions for returns calculation
        List<Transaction> validTransactions = validationResponse.getValid();

        // Group valid transactions by K periods to create savings results
        List<SavingsResult> savings = temporalService.groupByKPeriods(
                validTransactions,
                request.getK()
        );

        return returnsService.calculateIndex(request, savings);
    }

    @GetMapping("/performance")
    public PerformanceDTO performance() {

        Runtime runtime = Runtime.getRuntime();

        long usedMemoryBytes =
                runtime.totalMemory() - runtime.freeMemory();

        double usedMemoryMB =
                usedMemoryBytes / (1024.0 * 1024.0);

        ThreadMXBean threadBean =
                ManagementFactory.getThreadMXBean();

        int threadCount =
                threadBean.getThreadCount();

        String uptime =
                ManagementFactory.getRuntimeMXBean()
                        .getUptime() + " ms";

        return new PerformanceDTO(
                uptime,
                String.format("%.2f MB", usedMemoryMB),
                threadCount
        );
    }
}