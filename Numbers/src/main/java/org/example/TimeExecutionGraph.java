package org.example;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TimeExecutionGraph {

    public static void main(String[] args) {
        List<Integer> fileSizes = new ArrayList<>();
        List<Long> executionTimes = new ArrayList<>();

        try {
            Files.walk(Paths.get("src/main/resources/"))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("numbers_"))
                    .forEach(path -> {
                        int fileSize = countNumbersInFile(path.toString());
                        fileSizes.add(fileSize);

                        long startTime = System.nanoTime();
                        measureExecutionTime(path.toString());
                        long endTime = System.nanoTime();
                        long duration = (endTime - startTime) / 1_000_000;
                        executionTimes.add(duration);
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("Execution Time vs Number of Numbers");

        for (int i = 0; i < fileSizes.size(); i++) {
            series.add(fileSizes.get(i), executionTimes.get(i));
        }

        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Execution Time vs Number of Numbers",
                "Number of Numbers",
                "Execution Time (ms)",
                dataset
        );
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Execution Time Graph");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(new ChartPanel(chart), BorderLayout.CENTER);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }


    private static int countNumbersInFile(String filename) {
        try {
            return (int) Files.lines(Paths.get(filename))
                    .flatMap(line -> List.of(line.split("\\s+")).stream())
                    .count();
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }


    private static void measureExecutionTime(String filename) {
        try {
            Files.lines(Paths.get(filename))
                    .mapToInt(Integer::parseInt)
                    .sum();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
