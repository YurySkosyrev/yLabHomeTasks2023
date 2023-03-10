package com.edu.ylab.homework2.Task5.StatsAccumulator.impl;

import com.edu.ylab.homework2.Task5.StatsAccumulator.StatsAccumulator;

public class StatsAccumulatorImpl implements StatsAccumulator {

    private int count = 0;
    private int min = 0;
    private int max = 0;
    private int sum = 0;



    @Override
    public void add(int value) {
        if (count == 0) {
            min = value;
            max = value;
            sum = value;
        }
        else {
            min = Math.min(min, value);
            max = Math.max(max, value);
            sum += value;
        }
        count++;
    }

    @Override
    public int getMin() {
        return min;
    }

    @Override
    public int getMax() {
        return max;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public Double getAvg() {
        return sum/(double)count;
    }

}
