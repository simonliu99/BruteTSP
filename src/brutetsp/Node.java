package brutetsp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Simon on 1/5/17.
 * This object is the primary component of the TSP process. It holds a randomly generated set of 24 speed values (one
 * for each hour) and a distance value for how far the two vertices are apart. A node in this case represents an edge.
 */
public class Node {
    List<Double> speeds = new ArrayList<>();
    double distance;
    boolean ignore; // Used to

    // Create an empty node: used for loop edges
    Node() {
        distance = 0;
        ignore = true;
    }

    // Create a node using a given list of speeds n and distance d
    Node(List<Double> n, double d) {
        distance = d;
        speeds = n;
        ignore = false;
    }

    // Create a node with distance d and randomized speeds with rush hour traffic incorporated
    Node(double d, int highway) {
        distance = d;
        Random random = new Random();
        List<Double> n = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            if (Math.abs(i - 8) <= 2 || Math.abs(i-18) <= 2) {
                n.add(highway - (double)random.nextInt(30) - 15);
            } else {
                n.add(highway + (double)random.nextInt(30) - 15);
            }
        }
        speeds = n;
        ignore = false;
    }

    // Create a node with distance d and randomized speeds between min and max
    Node(double d, int min, int max) {
        distance = d;
        Random random = new Random();
        List<Double> n = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            n.add((double)random.nextInt(max - min + 1) + min);
        }
        speeds = n;
        ignore = false;
    }

    // Return time it takes to traverse the distance of a node given a certain start time
    public double time(double start) {
        if (!ignore) { // filters out null nodes
            int startModInt = (int) (start % 24);
            int curModInt = startModInt % 24;
            double curModDouble = start % 24;
            double distanceMoved = 0;
            double distanceLeft = distance - distanceMoved;
            double timeInc;
            double finalTime;
            double timeTotal = 0;
            boolean done = false;

            // Constantly refreshes finalTime until distance of node has been traveled
            while (!done) {
                if (curModInt == 24) {
                    curModInt = 0;
                }
                if (curModDouble == 24) {
                    curModDouble = 0;
                }
                if (speeds.get(curModInt) * ((curModInt + 1) - curModDouble) >= distanceLeft) {
                    timeInc = distanceLeft / speeds.get(curModInt);
                    timeTotal += timeInc;
                    distanceMoved = distance;
                    distanceLeft = 0;
                    done = true;
                } else {
                    timeInc = curModInt + 1 - curModDouble;
                    timeTotal += timeInc;
                    distanceMoved += timeInc * speeds.get(curModInt);
                    distanceLeft = distance - distanceMoved;
                    curModInt++;
                    curModDouble = curModInt;
                }
            }
            finalTime = start + timeTotal;
            return finalTime;
        }
        return 0;
    }
}
