package com.rrm.aoc;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AocProblem {

    abstract String problemNumber();

    abstract void one();

    abstract void two();

    protected int testNumber() {
        return -1;
    }

    @Test
    void tOne() {
        one();
    }

    @Test
    void tTwo() {
        two();
    }

    protected String input() {
        if (testNumber() == -1) {
            return puzzleInput();
        } else {
            return testInput(testNumber());
        }
    }

    private String puzzleInput() {
        return testResourceFileContent("input-2022-" + problemNumber(),
            () -> new RuntimeException("Couldn't get puzzle input."));
    }

    @SuppressWarnings("SameParameterValue")
    private String testInput(int testNumber) {
        return testResourceFileContent("input-2022-" + problemNumber() + "-test-" + testNumber,
            () -> new RuntimeException("Couldn't get test input."));
    }

    private String testResourceFileContent(String filename, Supplier<RuntimeException> onFail) {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (resourceAsStream != null) {
            return new BufferedReader(new InputStreamReader(resourceAsStream)).lines().collect(Collectors.joining("\n"));
        }
        throw onFail.get();
    }
}
