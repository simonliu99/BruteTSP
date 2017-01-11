package ops;

import brutetsp.Node;
import brutetsp.NodeFactory;
import util.Combine;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Simon on 1/5/17.
 */
public class TSP {
    public static void main(String[] args) throws IOException {
        List<List<Node>> nodes;
        NodeFactory nodeFactory;
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter number of nodes: ");
        int n = reader.nextInt();
        double runTime = System.currentTimeMillis();
        nodeFactory = new brutetsp.NodeFactory(n);
        nodes = nodeFactory.getNodes();
        List<List<Integer>> combinations = Combine.combinate(n);
        for (List<Integer> row : combinations) {
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
        System.out.print((System.currentTimeMillis() - runTime));
    }
}
