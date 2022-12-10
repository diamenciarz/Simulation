package helpers.math;

import java.awt.*;

/**
 * A class with useful mathematical and geometrical methods for efficient
 * calculation
 * 
 * @author Stan
 */
public class UtilityClass {

    private UtilityClass() {
        // Cannot instantiate this class - it's just static
    }

    public final static double DEGREES_TO_RADIANS = (Math.PI * 2) / 360;

    public static class LineMethods {
        /**
         *
         * @param p1 first point of the first line
         * @param p2 second point of the first line
         * @param p3 first point of the second line
         * @param p4 second point of the second line
         * @return a position the cross point of these two lines, if it is a part of one
         *         of the episodes.
         *         If it not, returns null
         */
        public static Vector2 findEpisodeIntersection(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4) {
            Vector2 crossPoint = findLineIntersection(p1, p2, p3, p4);
            if (crossPoint == null) {
                return null;
            }
            if (isPointInEpisode(crossPoint, p1, p2) && isPointInEpisode(crossPoint, p3, p4)) {
                return crossPoint;
            }
            return null;
        }

        /**
         *
         * @param point
         * @param firstPos
         * @param secondPos
         * @return true, if the position is a part of an episode
         */
        public static boolean isPointInEpisode(Vector2 point, Vector2 firstPos, Vector2 secondPos) {
            double distanceA = point.distanceTo(firstPos);
            double distanceB = point.distanceTo(secondPos);
            float distanceC = (float) firstPos.distanceTo(secondPos);

            float sum = (float) (distanceA + distanceB);
            if (sum == distanceC)
                return true; // C is on the line.
            return false;
        }

        /**
         *
         * @param p1 first point of the first line
         * @param p2 second point of the first line
         * @param p3 first point of the second line
         * @param p4 second point of the second line
         * @return the cross point of these two lines
         */
        public static Vector2 findLineIntersection(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4) {
            if (p1 == null || p2 == null || p3 == null || p4 == null) {
                return null;
            }

            double detD = ValueMethods.getDeterminant(
                    ValueMethods.getDeterminant(p1.x, 1, p2.x, 1),
                    ValueMethods.getDeterminant(p1.y, 1, p2.y, 1),
                    ValueMethods.getDeterminant(p3.x, 1, p4.x, 1),
                    ValueMethods.getDeterminant(p3.y, 1, p4.y, 1));

            if (detD == 0) {
                return null;
            }
            double detX = ValueMethods.getDeterminant(
                    ValueMethods.getDeterminant(p1.x, p1.y, p2.x, p2.y),
                    ValueMethods.getDeterminant(p1.x, 1, p2.x, 1),
                    ValueMethods.getDeterminant(p3.x, p3.y, p4.x, p4.y),
                    ValueMethods.getDeterminant(p3.x, 1, p4.x, 1));
            double detY = ValueMethods.getDeterminant(
                    ValueMethods.getDeterminant(p1.x, p1.y, p2.x, p2.y),
                    ValueMethods.getDeterminant(p1.y, 1, p2.y, 1),
                    ValueMethods.getDeterminant(p3.x, p3.y, p4.x, p4.y),
                    ValueMethods.getDeterminant(p3.y, 1, p4.y, 1));

            return new Vector2(detX / detD, detY / detD);
        }
    }

    public static class VectorMethods {
        /**
         *
         * @param closestTo the point that the distance is measured to
         * @param points    a list of points
         * @return the closest point or null if all given points are null or closestTo
         *         is null
         */
        public static Vector2 getClosestPoint(Vector2 closestTo, Vector2[] points) {
            if (closestTo == null || points == null || points.length == 0) {
                return null;
            }
            Vector2 closestPoint = getFirstNotNullPoint(points);
            if (points.length == 1 || closestPoint == null) {
                return closestPoint;
            }
            for (int i = 1; i < points.length; i++) {
                if (points[i] == null) {
                    continue;
                }
                if (closestTo.distanceTo(points[i]) < closestTo.distanceTo(closestPoint)) {
                    closestPoint = points[i];
                }
            }
            return closestPoint;
        }

        public static Vector2 getFirstNotNullPoint(Vector2[] points) {
            if (points == null) {
                return null;
            }
            for (Vector2 vector2 : points) {
                if (vector2 != null) {
                    return vector2;
                }
            }
            return null;
        }

        public static Vector2 clamp(Vector2 vector, double minLength, double maxLength) {
            if (vector.length() > maxLength) {
                return vector.normalized().scale(maxLength);
            }
            if (vector.length() < minLength) {
                return vector.normalized().scale(minLength);
            }
            return vector;
        }
    }

    public static class ValueMethods {
        private static double getDeterminant(double p11, double p12, double p21, double p22) {
            return p11 * p22 - p12 * p21;
        }

        public static double clamp(double value, double minLimit, double topLimit) {
            if (value > topLimit) {
                return topLimit;
            }
            if (value < minLimit) {
                return minLimit;
            }
            return value;
        }

        public static double getMaxValue(double[] values) {
            double max = Double.NEGATIVE_INFINITY;
            for (double value : values) {
                if (value > max) {
                    max = value;
                }
            }
            return max;
        }

        public static double getMinValue(double[] values) {
            double min = Double.POSITIVE_INFINITY;
            for (double value : values) {
                if (value < min) {
                    min = value;
                }
            }
            return min;
        }

        /**
         * @param startingValue
         * @param targetValue
         * @param percentage    a value from 0 to 1. If outside of specified range, it
         *                      will be clamped
         * @return
         */
        public static double lerp(double startingValue, double targetValue, double percentage) {
            percentage = clamp(percentage, -1, 1);

            return (targetValue - startingValue) * percentage + startingValue;
        }

        public static int lerp(int startingValue, int targetValue, double percentage) {
            return (int) lerp((double) startingValue, (double) targetValue, percentage);
        }

        public static Color lerp(Color firstColor, Color secondColor, double percentage) {
            int redValue = UtilityClass.ValueMethods.lerp(firstColor.getRed(), secondColor.getRed(), percentage);
            int greenValue = UtilityClass.ValueMethods.lerp(firstColor.getGreen(), secondColor.getGreen(), percentage);
            int blueValue = UtilityClass.ValueMethods.lerp(firstColor.getBlue(), secondColor.getBlue(), percentage);
            return new Color(redValue, greenValue, blueValue);
        }
    }

}