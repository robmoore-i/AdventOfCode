package com.rrm.aoc;

public class AocProblem04Test extends AocProblem {

    @Override
    protected int testNumber() {
        return -1;
    }

    @Override
    String problemNumber() {
        return "04";
    }

    @Override
    void one() {
        long fullyOverlappingPairAssignments = input().lines()
            .map(CleanupAssignmentPair::fromLine)
            .filter(CleanupAssignmentPair::containsFullOverlap)
            .count();
        System.out.println(fullyOverlappingPairAssignments);
    }

    @Override
    void two() {
        long fullyOverlappingPairAssignments = input().lines()
            .map(CleanupAssignmentPair::fromLine)
            .filter(CleanupAssignmentPair::containsAnyOverlap)
            .count();
        System.out.println(fullyOverlappingPairAssignments);
    }

    private record CleanupAssignmentPair(int firstLower, int firstUpper, int secondLower, int secondUpper) {
        private boolean containsFullOverlap() {
            return firstLower <= secondLower && firstUpper >= secondUpper
                || secondLower <= firstLower && secondUpper >= firstUpper;
        }

        private boolean containsAnyOverlap() {
            return firstUpper >= secondLower && firstLower <= secondUpper;
        }

        private static CleanupAssignmentPair fromLine(String line) {
            String[] split = line.split(",");
            String[] firstSplit = split[0].split("-");
            String[] secondSplit = split[1].split("-");
            return new CleanupAssignmentPair(
                Integer.parseInt(firstSplit[0]),
                Integer.parseInt(firstSplit[1]),
                Integer.parseInt(secondSplit[0]),
                Integer.parseInt(secondSplit[1])
            );
        }
    }
}
