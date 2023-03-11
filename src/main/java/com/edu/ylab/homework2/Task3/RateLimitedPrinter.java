package com.edu.ylab.homework2.Task3;

public class RateLimitedPrinter {

    private int interval;
    private long lastPrintTime;

    public RateLimitedPrinter(int interval) {
        this.interval = interval;
        this.lastPrintTime = 0;
    }

    public void print(String message) {
        long currentTime = System.currentTimeMillis();
        if (lastPrintTime == 0 || currentTime - lastPrintTime > interval) {
            System.out.println(message);
            lastPrintTime = currentTime;
        }
    }
}
