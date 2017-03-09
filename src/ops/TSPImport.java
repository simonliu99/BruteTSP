package ops;

import brutetsp.Node;
import brutetsp.NodeFactory;
import util.Permutation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 1/5/2017.
 */
public class TSPImport {
    public static void main(String[] args) throws IOException {
        List<List<Node>> nodes;
        NodeFactory nodeFactory;
        double runTime = System.nanoTime();
        int n = 5;
        nodeFactory = new NodeFactory(getList("CALIBRATE"));
        nodes = nodeFactory.getNodes();
        List<List<Integer>> permutations = Permutation.permutate(n);
        for (List<Integer> row : permutations) {
            double startTime = 0;
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
                finalTime += nodes.get(rowVal).get(columnVal).time(startTime);
                startTime = finalTime;
                if (j == row.size() - 1) {
                    if (row.get(0) > row.get(row.size() - 1)) {
                        rowVal = row.get(0);
                        columnVal = row.get(row.size() - 1);
                    } else {
                        rowVal = row.get(row.size() - 1);
                        columnVal = row.get(0);
                    }
                    finalTime += nodes.get(rowVal).get(columnVal).time(startTime);
                }
            }
        }
        System.out.print((System.nanoTime() - runTime)/1000000);
    }

    static List<String> getList(String f) throws IOException {
        String fileName = System.getProperty("user.dir") + File.separator + "inputs" + File.separator + f + ".txt";
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
}
