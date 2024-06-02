package org.example;

import org.junit.*;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class NumberProcessorTest {
    private NumberProcessor np;
    private int min, max, sum;
    private long mult;

    @Before
    public void setUp() throws Exception {
        String filename = "src/main/resources/numbers.txt";
        np = new NumberProcessor(filename);

        List<Integer> numbers = new ArrayList<>();
        for (String line : Files.readAllLines(Paths.get(filename))) {
            for (String part : line.split("\\s+")) {
                Integer i = Integer.valueOf(part);
                numbers.add(i);
            }
        }
        min = Collections.min(numbers);
        max = Collections.max(numbers);
        sum = numbers.stream().mapToInt(Integer::intValue).sum();
        mult = numbers.stream().mapToLong(Integer::longValue).reduce(1, (a, b) -> a * b);
    }

    @Test
    public void testMin() {
        assertEquals(min, np._min());
    }

    @Test
    public void testMax() {
        assertEquals(max, np._max());
    }

    @Test
    public void testSum() {
        assertEquals(sum, np._sum());
    }

    @Test
    public void testMult() {
        assertEquals(mult, np._mult());
    }

    @Test
    public void testCount() throws IOException {
        int expectedCount = getCountFromNumbersFile();
        assertEquals(expectedCount, np._count());
    }


    private int getCountFromNumbersFile() throws IOException {
        String filename = "src/main/resources/numbers.txt";
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                count += parts.length;
            }
        }
        return count;
    }
}
