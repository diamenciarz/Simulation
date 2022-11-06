package simulation;

public class RandGenerator {
    public static double drawRandomExponential(double mean) {
        // draw a [0,1] uniform distributed number
        double u = Math.random();
        // Convert it into a exponentially distributed random variate with given mean
        double res = -mean * Math.log(u);
        double rounded = (double) (Math.round(res * 10)) / 10;
        return rounded;
    }
}
