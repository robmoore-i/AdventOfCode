package com.rrm.aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class AocProblem05Test extends AocProblem {

    @Override
    protected int testNumber() {
        return 1;
    }

    @Override
    String problemNumber() {
        return "05";
    }

    @Override
    void one() {
        String input = input();
        var splitPoint = input.indexOf("\n\n");
        String initialConfiguration = input.substring(0, splitPoint);
        List<String> initialConfigurationLines = initialConfiguration.lines().toList();
        List<Integer> stackNumbers = Arrays.stream(initialConfigurationLines.get(initialConfigurationLines.size() - 1).split(" "))
            .filter(s -> !s.isBlank())
            .map(Integer::parseInt)
            .toList();
        List<String> stackEntries = initialConfigurationLines.subList(0, initialConfigurationLines.size() - 1);
        CrateStacks crateStacks = new CrateStacks(stackEntries.stream()
            .map(line -> stackNumbers.stream()
                .map(i -> ((i - 1) * 4) + 1)
                .map(index -> {
                    if (index >= line.length()) {
                        return ' ';
                    }
                    return line.charAt(index);
                }).toList())
            .toList());
        String moves = input.substring(splitPoint);
        moves.lines().filter(s -> !s.isBlank()).forEach(line -> {
            String[] split = line.split(" ");
            crateStacks.move(
                Integer.parseInt(split[1]),
                Integer.parseInt(split[3]),
                Integer.parseInt(split[5])
            );
        });
        System.out.println(crateStacks);
        System.out.println(crateStacks.topOfEach());
    }

    @Override
    void two() {
    }

    private static class CrateStacks {
        private final List<Stack<Character>> stacks;

        private CrateStacks(List<List<Character>> stackEntryLists) {
            List<List<Character>> stacksList = new ArrayList<>();
            int numberOfStacks = stackEntryLists.get(0).size();
            for (int i = 0; i < numberOfStacks; i++) {
                stacksList.add(new ArrayList<>());
            }
            stackEntryLists.forEach(rowOfChars -> {
                for (int i = 0; i < rowOfChars.size(); i++) {
                    Character c = rowOfChars.get(i);
                    if (c != ' ') {
                        stacksList.get(i).add(c);
                    }
                }
            });
            this.stacks = stacksList.stream().map(listOfChars -> {
                Stack<Character> stack = new Stack<>();
                for (int i = listOfChars.size() - 1; i >= 0; i--) {
                    stack.push(listOfChars.get(i));
                }
                return stack;
            }).toList();
        }


        public void move(int howMany, int from, int to) {
            ArrayList<Character> movedCrates = new ArrayList<>();
            for (int i = 0; i < howMany; i++) {
                movedCrates.add(stacks.get(from - 1).pop());
            }
            movedCrates.forEach(c -> stacks.get(to - 1).push(c));
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Stack<Character> stack : stacks) {
                sb.append(stack).append("\n");
            }
            return sb.toString();
        }

        public String topOfEach() {
            StringBuilder sb = new StringBuilder();
            stacks.forEach(stack -> {
                sb.append(stack.peek());
            });
            return sb.toString();
        }
    }
}
