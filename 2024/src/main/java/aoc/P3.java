package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Pattern;

public class P3 {

    public static void main(String[] args) throws IOException {
        var puzzleInput = Paths.get("2024/puzzle_inputs/puzzle_input_3.txt");
        var memory = Files.readString(puzzleInput);
        var pattern = Pattern.compile("mul[(][0-9]{1,3},[0-9]{1,3}[)]");
        var matcher = pattern.matcher(memory);
        var sum = 0;
        while (matcher.find()) {
            var instruction = matcher.group();
            var numbers = instruction
                // mul(X,Y) => X,Y
                .substring("mul(".length(), instruction.length() - 1)
                .split(",");
            sum += Integer.parseInt(numbers[0]) * Integer.parseInt(numbers[1]);
        }
        System.out.println(sum);
    }
}
