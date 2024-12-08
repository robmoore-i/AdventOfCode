package com.rrm.aoc;

import java.util.Arrays;

public class AocProblem01Test extends AocProblem {

    @Override
    String problemNumber() {
        return "01";
    }

    @Override
    void one() {
        var mostCalories = Arrays.stream(input().split("\n\n"))
            .mapToInt(elvenInventory ->
                Arrays.stream(elvenInventory.split("\n")).mapToInt(Integer::parseInt).sum()
            )
            .max()
            .orElseThrow();
        System.out.println(mostCalories);
    }

    @Override
    void two() {
        var sortedCalorieCounts = Arrays.stream(input().split("\n\n"))
            .mapToInt(elvenInventory ->
                Arrays.stream(elvenInventory.split("\n")).mapToInt(Integer::parseInt).sum()
            )
            .sorted()
            .toArray();
        var n = sortedCalorieCounts.length - 1;
        var topThreeCalorieCounts = sortedCalorieCounts[n] + sortedCalorieCounts[n - 1] + sortedCalorieCounts[n - 2];
        System.out.println(topThreeCalorieCounts);
    }
}
