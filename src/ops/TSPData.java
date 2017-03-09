package ops;

import brutetsp.Datum;
import brutetsp.Node;
import brutetsp.NodeFactory;
import util.Permutation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Simon on 1/6/2017.
 * Data collection TSP runs brute force simulations multiple times and outputs to text file.
 */
public class TSPData {
    public static void main(String[] args) throws IOException {
        // Calibrate to get all packages and libraries initialized
        Datum calibrate = getTime(7, getList("CALIBRATION"));
        System.out.print(calibrate.getMinTime() + System.lineSeparator());
        for (int i = 5; i < 12; i++) { // Loop for all K graphs from K5 to K11
            List<String> nodeValues = new ArrayList<>();
            for (int j = 1; j < 11; j++) { // Run through all 10 input files
                String inputName = String.valueOf(i) + File.separator + String.valueOf(j);
                List<String> fileValues = new ArrayList<>();
                for (int k = 0; k < 100; k++) { // Execute each data file 100 times
                    List<String> list = getList(inputName);
                    Datum result = getTime(i, list);
                    double runTime = result.getRunTime();
                    double minTime = result.getMinTime();
                    if (k == 0) { // Add data heading before trial output
                        fileValues.add(String.valueOf(j));
                    }
                    System.out.print(i + " " + k + " " + runTime + " " + minTime + System.lineSeparator());
                    fileValues.add(String.valueOf(runTime) + " " + String.valueOf(minTime));
                }
                nodeValues.addAll(fileValues);
                nodeValues.add("-");
            }

            // Flush all data to data files
            String outputName = "datum" + String.valueOf(i) + "v2.txt";
            String path = System.getProperty("user.dir") + File.separator + "output" + File.separator + "v2" + File.separator + outputName;
            Path file = Paths.get(path);
            Files.write(file, nodeValues, Charset.forName("UTF-8"));
        }
    }

    // Reads text file from input list and converts it into a list of strings
    static List<String> getList(String f) throws IOException {
        String fileName = System.getProperty("user.dir") + File.separator + "input" + File.separator + f + ".txt";
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        List<String> lines = new ArrayList<>();
        String line = reader.readLine();

        while(line != null) {
            lines.add(line);
            line = reader.readLine();
        }
        reader.close();

        return lines;
    }

    // Takes a list of strings for n vertices and returns the time it takes for the program to brute force this TSP
    private static Datum getTime(int n, List<String> L) {
        List<List<Node>> nodes;
        NodeFactory nodeFactory;
        Permutation perm = new Permutation();
        double startTime = System.nanoTime();
        nodeFactory = new NodeFactory(L);
        nodes = nodeFactory.getNodes();
        List<List<Integer>> permutations = perm.permutate(n);
        List<Double> times = new ArrayList<>();

        //Runs through each permutation of paths to find minimum time path
        for (List<Integer> row : permutations) {
            double currentTime = 0;
            double finalTime = 0;
            for (int j = 0; j < row.size() - 1; j++) {
                int rowVal;
                int columnVal;
                if (row.get(j) > row.get(j + 1)) {
                    rowVal = row.get(j);
                    columnVal = row.get(j + 1);
                } else {
                    rowVal = row.get(j + 1);
                    columnVal = row.get(j);
                }
                finalTime += nodes.get(rowVal).get(columnVal).time(currentTime);
                currentTime = finalTime;
            }
            times.add(finalTime);
        }
        double minTime = Collections.min(times);
        double runtime =(System.nanoTime() - startTime)/1000000;
        return new Datum(minTime, runtime); // Return minimum time and total run time in Datum object form
    }
}
