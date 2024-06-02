package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberProcessor {
    private List<Integer> numbers;

    public NumberProcessor(String filename) throws IOException {
        numbers = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(filename))) {
            for (String part : line.split("\\s+")) {
                Integer i = Integer.valueOf(part);
                numbers.add(i);
            }
        }
    }

    public int _count() {
        return numbers.size();
    }

    public int _min() {
        return Collections.min(numbers);
    }

    public int _max() {
        return Collections.max(numbers);
    }

    public long _sum() {
        long sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum;
    }

    public long _mult() {
        long result = 1;
        for (int number : numbers) {
            result *= number;
        }
        return result;
    }
}
