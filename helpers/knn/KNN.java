package helpers.knn;

public class KNN {
    double[][] coords;
    String[] classes;

    /*
     * coords
     * {
     * [1,2,6,3,5]
     * [2,6,6,2,9]
     * [2,7,3,4,8]
     * }
     * classes:
     * {
     * "Pears",
     * "Apples",
     * "Carrots"
     * }
     */
    public KNN(double[][] coords, String[] classes) {
        this.coords = coords;
        this.classes = classes;
    }

    
}
