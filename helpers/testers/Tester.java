package helpers.testers;

public class Tester {
    public static TestResults test(ITestable method) {
        final long MAX_RUNTIME = 60000; // Minute
        final int MAX_REPETITIONS = 10;

        long startingTime = System.currentTimeMillis();
        int repetitions = 0;
        long elapsedTime;

        while (true) {
            method.execute();
            repetitions++;

            elapsedTime = System.currentTimeMillis() - startingTime;
            boolean maxTimePassed = elapsedTime > MAX_RUNTIME;
            if (repetitions > MAX_REPETITIONS || maxTimePassed) {
                break;
            }
        }
        return new TestResults(repetitions, elapsedTime);
    }

    public static void compare(ITestable method1, ITestable method2){
        
    }

    public static void main(String[] args) {
        test(new ITestable() {
           public void execute(){
            System.out.println("s");
           } 
        });
    }
}
