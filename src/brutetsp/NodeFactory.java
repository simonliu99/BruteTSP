package brutetsp;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Simon on 1/5/17.
 * The NodeFactory class is used to generate nodes either randomly or from an imported list of node speeds.
 */
public class NodeFactory {

    private Random rand = new Random();
    private List<List<Integer>> ntype = new ArrayList<>();
    private List<List<Node>> nodes = new ArrayList<>();

    // Create a n-size list of nodes with randomized types, either empty, local, and highway
    public NodeFactory(int n) throws IOException {
        for (int i = 0; i < n; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < i + 1; j++) {
                if (i == j) {
                    row.add(0);
                } else {
                    row.add(rand.nextInt(1) + 1);
                }
            }
            ntype.add(row);
        }

        // Convert list of node types into list of nodes with randomized speeds
        for (List<Integer> k : ntype) {
            List<Node> row = new ArrayList<>();
            for (int l : k) {
                switch (l) {
                    case 0: // empty node represents edge connecting a vertex to itself
                        row.add(new Node());
                        break;
                    case 1: // highway node representing fast speeds and traffic effects
                        row.add(new Node(rand.nextInt(200) + 1, 100));
                        break;
                    case 2: // local node representing small roads with relatively constant traffic flow
                        row.add(new Node(rand.nextInt(200) + 1, 15, 40));
                        break;
                    default:
                        row.add(new Node());
                }
            }
            nodes.add(row);
        }

        // Grab speed values from node object and into
        List<String> nodeSpeeds = new ArrayList<>();
        for (List<Node> row : nodes) {
            List<String> rowSpeedStr = new ArrayList<>();
            for (Node node : row) {
                List<String> nodeSpeedStr = new ArrayList<>();
                for (Double d : node.speeds) {
                    nodeSpeedStr.add(Double.toString(d));
                }
                if (!node.ignore) {
                    nodeSpeedStr.add(String.valueOf(node.distance));
                }
                rowSpeedStr.add(String.join(",", nodeSpeedStr));
            }
            nodeSpeeds.add(String.join(";", rowSpeedStr));
        }
        String fileName = "tsp" + String.valueOf(n) + ".txt";
        Path file = Paths.get(fileName);
        Files.write(file, nodeSpeeds, Charset.forName("UTF-8"));
    }

    // Create list of nodes from list of speeds and distance for each node
    public NodeFactory(List<String> d) {
        List<List<List<Double>>> nodeSpeeds = new ArrayList<>();

        // Convert list of strings to list of node speeds
        for (String s : d) {
            String[] row = s.split(";");
            List<List<Double>> rowLists = new ArrayList<>();
            for (String t : row) {
                String[] rowListStr = t.split(",");
                List<Double> rowListDouble = new ArrayList<>();
                for (String u : rowListStr) {
                    if (u != null && !u.isEmpty()) {
                        rowListDouble.add(Double.parseDouble(u));
                    }
                }
                rowLists.add(rowListDouble);
            }
            nodeSpeeds.add(rowLists);
        }

        // Construct nodes from list of node speeds
        for (List<List<Double>> l : nodeSpeeds) {
            List<Node> row = new ArrayList<>();
            for (List<Double> m : l) {
                if (m.size() != 0) {
                    double distance = m.get(m.size() - 1);
                    m.remove(m.size() - 1);
                    Node node = new Node(m, distance);
                    row.add(node);
                } else {
                    row.add(new Node());
                }
            }
            nodes.add(row);
        }
    }

    // Return list of nodes from object
    public List<List<Node>> getNodes() {
        return nodes;
    }

}
