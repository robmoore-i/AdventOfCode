package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class P1 {

    public static void main(String[] args) throws IOException {
        var test = args.length > 1;
        var puzzleInput = Paths.get(args[0], "puzzle_input_1" + (test ? "_test" : "") + ".txt");
        var turns = Files.readAllLines(puzzleInput);
        var dial = new Dial(50);
        var zeroes = 0;
        for (var turn : turns) {
            zeroes += dial.zeroes(turn);
        }

        System.out.println("Zeroes: " + zeroes);
    }

    static class Dial {
        int value;

        Dial(int value) {
            this.value = value;
        }

        /**
         * Turns the dial and counts how many times a click ended on zero.
         */
        int zeroes(String turn) {
            var n = Integer.parseInt(turn.substring(1));
            if (turn.charAt(0) == 'L') {
                if (n < value) {
                    value -= n;
                    return 0;
                } else {
                    // Go to zero.
                    var remainingTurns = n - value;
                    // Spin until there are no more hundreds left.
                    var fullSpins = remainingTurns / 100;
                    // Complete the remaining turns
                    remainingTurns = remainingTurns % 100;
                    int zeroes = fullSpins + (value == 0 ? 0 : 1);
                    value = (100 - remainingTurns) % 100;
                    return zeroes;
                }
            } else {
                if (n + value < 100) {
                    value += n;
                    return 0;
                } else {
                    // Go to zero.
                    var remainingTurns = n - (100 - value);
                    // Spin until there are no more hundreds left.
                    var fullSpins = remainingTurns / 100;
                    // Complete the remaining turns
                    remainingTurns = remainingTurns % 100;
                    int zeroes = fullSpins + 1;
                    value = remainingTurns;
                    return zeroes;
                }
            }
        }
    }
}
