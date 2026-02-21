package com.example.demo.Model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class QPeriod {
    private double fixed;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime start;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime end;

    public double getFixed() {
        return fixed;
    }

    public void setFixed(double fixed) {
        this.fixed = fixed;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public QPeriod(double fixed, LocalDateTime start, LocalDateTime end) {
        this.fixed = fixed;
        this.start = start;
        this.end = end;
    }

    public QPeriod() {
    }
}
