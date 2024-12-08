package com.rrm.aoc;

import java.util.List;

public class AocProblem03Test extends AocProblem {

    @Override
    protected int testNumber() {
        return -1;
    }

    @Override
    String problemNumber() {
        return "03";
    }

    private final RucksackSearch rucksackSearch = new RucksackSearch();

    @Override
    void one() {
        var prioritySum = input().lines().mapToInt(line -> priority(rucksackSearch.findCommonItem(line))).sum();
        System.out.println(prioritySum);
    }

    @Override
    void two() {
        List<String> lines = input().lines().toList();
        int prioritySum = 0;
        for (int i = 0; i < lines.size(); i += 3) {
            List<String> rucksacks = lines.subList(i, i + 3);
            char commonBadge = rucksackSearch.findCommonBadge(rucksacks.get(0), rucksacks.get(1), rucksacks.get(2));
            prioritySum += priority(commonBadge);
        }
        System.out.println(prioritySum);
    }

    private int priority(char c) {
        return (c - 'A' + 27) % 58;
    }

    private record RucksackSearch() {

        private char findCommonBadge(String one, String two, String three) {
            for (char c : one.toCharArray()) {
                if (two.indexOf(c) != -1 && three.indexOf(c) != -1) {
                    return c;
                }
            }
            throw new RuntimeException("No common item between '" + one + "', '" + two + "', and '" + three + "'");
        }

        private char findCommonItem(String line) {
            var size = line.length();
            if (size % 2 == 0) {
                var firstHalf = line.substring(0, size / 2);
                var secondHalf = line.substring(size / 2);
                for (char c : firstHalf.toCharArray()) {
                    if (secondHalf.indexOf(c) != -1) {
                        return c;
                    }
                }
                throw new RuntimeException("No common item between line halves '" + firstHalf + "' and '" + secondHalf + "'");
            } else {
                throw new RuntimeException("Odd sized line: '" + line + "'");
            }
        }
    }
}
