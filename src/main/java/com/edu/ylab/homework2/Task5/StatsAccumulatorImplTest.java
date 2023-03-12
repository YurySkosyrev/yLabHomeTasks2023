package com.edu.ylab.homework2.Task5;

import com.edu.ylab.homework2.Task5.StatsAccumulator.impl.StatsAccumulatorImpl;

/** Test of StatsAccumulatorImpl.class
 */

public class StatsAccumulatorImplTest {
    public static void main(String[] args) {
        StatsAccumulatorImpl s = new StatsAccumulatorImpl() {
        };
        s.add(3);
        s.add(3);
        s.add(3);

        assert (Math.abs(3.0-s.getAvg()) < 0.001) : "AvgTest failed";

        s.add(2);
        s.add(-2);
        s.add(-10);
        s.add(100);

        assert -10 == s.getMin() : "MinTest failed";
        assert 100 == s.getMax() : "MaxTest failed";

        s.add(4);

        assert 8 == s.getCount() : "CountTest failed";
    }
}
