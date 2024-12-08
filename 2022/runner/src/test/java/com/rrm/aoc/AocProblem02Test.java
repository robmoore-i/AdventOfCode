package com.rrm.aoc;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class AocProblem02Test extends AocProblem {

    @Override
    String problemNumber() {
        return "02";
    }

    @Override
    void one() {
        // A -> Rock     <- X | 1
        // B -> Paper    <- Y | 2
        // C -> Scissors <- Z | 3
        // Lose: 0, Draw: 3, Win: 6

        var choiceScores = new int['Z' + 1];
        choiceScores['X'] = 1;
        choiceScores['Y'] = 2;
        choiceScores['Z'] = 3;

        var outcomeScores = getOutcomeScores();

        var score = input().lines().mapToInt(line -> {
            var opponentPlays = line.charAt(0);
            var youPlay = line.charAt(2);
            return choiceScores[youPlay] + outcomeScores[youPlay][opponentPlays];
        }).sum();
        System.out.println(score);
    }

    private static int[][] getOutcomeScores() {
        var outcomeScores = new int['Z' + 1]['C' + 1];
        var outcomeScoresX = new int['C' + 1];
        outcomeScoresX['A'] = 3;
        outcomeScoresX['B'] = 0;
        outcomeScoresX['C'] = 6;
        outcomeScores['X'] = outcomeScoresX;
        var outcomeScoresY = new int['C' + 1];
        outcomeScoresY['A'] = 6;
        outcomeScoresY['B'] = 3;
        outcomeScoresY['C'] = 0;
        outcomeScores['Y'] = outcomeScoresY;
        var outcomeScoresZ = new int['C' + 1];
        outcomeScoresZ['A'] = 0;
        outcomeScoresZ['B'] = 6;
        outcomeScoresZ['C'] = 3;
        outcomeScores['Z'] = outcomeScoresZ;
        return outcomeScores;
    }

    @Override
    void two() {
        // A -> Rock     <- X | 1
        // B -> Paper    <- Y | 2
        // C -> Scissors <- Z | 3
        // Lose: 0, Draw: 3, Win: 6

        var outcomeScores = new int['Z' + 1];
        outcomeScores['X'] = 0;
        outcomeScores['Y'] = 3;
        outcomeScores['Z'] = 6;

        var choiceScores = getChoiceScores();

        var score = input().lines().mapToInt(line -> {
            var opponentPlays = line.charAt(0);
            var youPlay = line.charAt(2);
            return outcomeScores[youPlay] + choiceScores[youPlay][opponentPlays];
        }).sum();
        System.out.println(score);
    }

    private static int[][] getChoiceScores() {
        var choiceScores = new int['Z' + 1]['C' + 1];
        var choiceScoresX = new int['C' + 1];
        choiceScoresX['A'] = 3;
        choiceScoresX['B'] = 1;
        choiceScoresX['C'] = 2;
        choiceScores['X'] = choiceScoresX;
        var choiceScoresY = new int['C' + 1];
        choiceScoresY['A'] = 1;
        choiceScoresY['B'] = 2;
        choiceScoresY['C'] = 3;
        choiceScores['Y'] = choiceScoresY;
        var choiceScoresZ = new int['C' + 1];
        choiceScoresZ['A'] = 2;
        choiceScoresZ['B'] = 3;
        choiceScoresZ['C'] = 1;
        choiceScores['Z'] = choiceScoresZ;
        return choiceScores;
    }
}
