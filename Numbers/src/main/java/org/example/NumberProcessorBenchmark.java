package org.example;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NumberProcessorBenchmark {

    private static NumberProcessor numberProcessor;
    private static List<Integer> numbers;

    static {
        try {
            numberProcessor = new NumberProcessor("src/main/resources/numbers.txt");
            numbers = Files.lines(Paths.get("src/main/resources/numbers.txt"))
                    .flatMap(line -> Arrays.stream(line.split("\\s+")))
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkMin() {
        numberProcessor._min();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkMax() {
        numberProcessor._max();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkSum() {
        numberProcessor._sum();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkMult() {
        numberProcessor._mult();
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void benchmarkCount() {
        numberProcessor._count();
    }


    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(NumberProcessorBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

}