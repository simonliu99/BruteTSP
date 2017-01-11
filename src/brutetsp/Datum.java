package brutetsp;

/**
 * Created by Simon on 1/7/2017.
 * This object holds return values for the getTime() function in a Node object.
 */
public class Datum {
    private double minTime;
    private double runTime;
    public Datum(double l, double t) {
        minTime = l;
        runTime = t;
    }

    public double getMinTime() {
        return minTime;
    }
    public double getRunTime() {
        return runTime;
    }
}
