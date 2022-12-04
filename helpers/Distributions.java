package helpers;

import Simulation.Simulation;

public class Distributions {


    public static double Erlang3PDF(){

        double x = Math.random();
        double lambda = 1;
        double n = Simulation.N_MACHINES;
        return (lambda * Math.pow((lambda * x), (n - 1)) * Math.pow(Math.E, (-lambda*x)))
                /
                factorial(n - 1);
    }

    public static double factorial(double n){
        if(n == 0){
            return 1;
        }else{
            return n * factorial(n - 1);
        }
    }

    public static double Erlang3CFD(double x){

        double n = 3;
        double lambda = 1;
        double CFD = 0;

        for(int k = 0; k <= (n-1); k++){
           CFD += (Math.pow((lambda * x), k) * Math.pow(Math.E, (-lambda*x))) / (factorial(k));
        }

        return 1 - CFD;
    }

    public static double Erlang3CFD_ConCentrao(double x, double X){

        double n = 3;
        double lambda = 1;
        double CFD = 0;

        for(int k = 0; k <= (n-1); k++){
            CFD += (Math.pow((lambda * x), k) * Math.pow(Math.E, (-lambda*x))) / (factorial(k));
        }

        return (1 - CFD) - X;
    }


    public static double bisection(double x){

        double a = 0;
        double b = 45;
        double error = Integer.MIN_VALUE;
        double c = 0;

        while(error < -0.00000000000000001){

            double fa = Erlang3CFD_ConCentrao(a, x);
            double fb = Erlang3CFD_ConCentrao(b, x);

            error = fa * fb;
            c = (a + b) / 2;

            double fc = Erlang3CFD_ConCentrao(c, x);

            if(fc < 0){
                a = c;
            }else{
                b = c;
            }
        }

        return c;
    }
    public static void main(String[] args) {

        double x = Erlang3CFD(10);

        System.out.println(bisection(x));

    }

}