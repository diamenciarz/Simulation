package helpers;

import helpers.math.Vector2;
import simulation.city.City;
import simulation.city.Hex;

public class RandGenerator {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(drawRandomExponential(33));
        }
    }

    public static double drawRandomExponential(double mean) {
        // draw a [0,1] uniform distributed number
        double u = Math.random();
        // Convert it into a exponentially distributed random variate with given mean
        double res = -mean * Math.log(u);
        double rounded = (double) (Math.round(res * 10)) / 10;
        return rounded;
    }

    public static double[] generateDelays(int valueCount, double min, double max) {
        double[] delays = new double[valueCount];
        for (int i = 0; i < valueCount; i++) {
            delays[i] = generateDelay(min, max);
        }
        return delays;
    }

    /**
     * @return a double rounded up to one digit
     */
    private static double generateDelay(double min, double max) {
        double rand = Math.random() * (max - min) + min;
        return (double) (Math.round(rand * 10)) / 10;
    }

    public static Vector2 generateRandomPosition(){
        Vector2 randomPosition = Vector2.zeroVector();
        do {
            randomPosition.x = Math.random() * 6 * Hex.RADIUS;
            randomPosition.y = Math.random() * 6 * Hex.APHOTHEMA;
        } while (!City.isPositionOnMap(randomPosition));
        return randomPosition;
    }

}
